package net.neounity.neobungeesystem.listener;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.neounity.neobungeesystem.NeoBungeeSystem;
import net.neounity.neobungeesystem.util.Data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

public class FriendManager implements Listener {

    @EventHandler
    public void onConnect(ServerConnectEvent e) {
        ProxiedPlayer player = e.getPlayer();

        if (!e.isCancelled() && !FriendManager.isOnline(player.getUniqueId())) {
            FriendManager.registerIfNeeded(player.getUniqueId());
            FriendManager.setOnline(player.getUniqueId(), true);
            for (UUID uuid : FriendManager.getFriends(player.getUniqueId())) {
                ProxiedPlayer friend = NeoBungeeSystem.plugin.getProxy().getPlayer(uuid);
                if (friend != null && FriendSettingsManager.isGettingNotified(uuid)) {

                    friend.sendMessage(Data.PREFIX + "§e" + player.getName() + " §7ist nun §aOnline!");
                }
            }
        }
    }
    public static boolean isRequestOpen(UUID uuid, UUID requested) {
        return getRequests(requested).contains(uuid);
    }

    public static ArrayList<UUID> getRequests(UUID uuid) {
        ArrayList<UUID> list = new ArrayList<>();
        if (isRegistered(uuid)) {
            ResultSet rs = NeoBungeeSystem.mySQL.getResult("SELECT * FROM Friends WHERE UUID = '" + uuid.toString() + "'");
            if (rs != null) {
                try {
                    if (rs.next()) {
                        String s = rs.getString("FriendRequests");
                        if (s != null &&
                                !s.equalsIgnoreCase("[]")) {
                            byte b;
                            int i;
                            String[] arrayOfString;
                            for (i = (arrayOfString = s.replace("[", "").replace("]", "").split(", ")).length, b = 0; b < i; ) {
                                String string = arrayOfString[b];
                                list.add(UUID.fromString(string.trim()));
                                b++;
                            }
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return list;
            }
        }
        return list;
    }

    public static void addRequest(UUID uuid, UUID requested) {
        ArrayList<UUID> friends = getFriends(requested);
        ArrayList<UUID> requests = getRequests(requested);
        if (!requests.contains(uuid))
            requests.add(uuid);
        NeoBungeeSystem.mySQL.update("DELETE FROM Friends WHERE UUID = '" + requested.toString() + "'");
        NeoBungeeSystem.mySQL.updateWithBoolean("INSERT INTO Friends (UUID, FriendUUIDs, FriendRequests, OnlineStatus) VALUES ('" + requested.toString() + "', '" + friends.toString() + "', '" + requests.toString() + "', ?)", true);
    }

    public static void removeRequest(UUID uuid, UUID requested) {
        ArrayList<UUID> friends = getFriends(requested);
        ArrayList<UUID> requests = getRequests(requested);
        if (requests.contains(uuid))
            requests.remove(uuid);
        NeoBungeeSystem.mySQL.update("DELETE FROM Friends WHERE UUID = '" + requested.toString() + "'");
        NeoBungeeSystem.mySQL.updateWithBoolean("INSERT INTO Friends (UUID, FriendUUIDs, FriendRequests, OnlineStatus) VALUES ('" + requested.toString() + "', '" + friends.toString() + "', '" + requests.toString() + "', ?)", true);
    }

    public static void addFriend(UUID user, UUID friend) {
        if (!isFriend(user, friend)) {
            ArrayList<UUID> requests = getRequests(user);
            ArrayList<UUID> friends = getFriends(user);
            if (!friends.contains(friend))
                friends.add(friend);
            NeoBungeeSystem.mySQL.update("DELETE FROM Friends WHERE UUID = '" + user.toString() + "'");
            NeoBungeeSystem.mySQL.updateWithBoolean("INSERT INTO Friends (UUID, FriendUUIDs, FriendRequests, OnlineStatus) VALUES ('" + user.toString() + "', '" + friends.toString() + "', '" + requests.toString() + "', ?)", true);
        }
    }

    public static void removeFriend(UUID user, UUID friend) {
        if (isFriend(user, friend)) {
            ArrayList<UUID> requests = getRequests(user);
            ArrayList<UUID> friends = getFriends(user);
            if (friends.contains(friend))
                friends.remove(friend);
            NeoBungeeSystem.mySQL.update("DELETE FROM Friends WHERE UUID = '" + user.toString() + "'");
            NeoBungeeSystem.mySQL.updateWithBoolean("INSERT INTO Friends (UUID, FriendUUIDs, FriendRequests, OnlineStatus) VALUES ('" + user.toString() + "', '" + friends.toString() + "', '" + requests.toString() + "', ?)", true);
        }
    }

    public static boolean isFriend(UUID user, UUID friend) {
        return getFriends(user).contains(friend);
    }

    public static ArrayList<UUID> getFriends(UUID user) {
        ArrayList<UUID> list = new ArrayList<>();
        if (isRegistered(user)) {
            ResultSet rs = NeoBungeeSystem.mySQL.getResult("SELECT * FROM Friends WHERE UUID = '" + user.toString() + "'");
            if (rs != null) {
                try {
                    if (rs.next()) {
                        String s = rs.getString("FriendUUIDs");
                        if (s != null &&
                                !s.equalsIgnoreCase("[]")) {
                            byte b;
                            int i;
                            String[] arrayOfString;
                            for (i = (arrayOfString = s.replace("[", "").replace("]", "").split(", ")).length, b = 0; b < i; ) {
                                String string = arrayOfString[b];
                                list.add(UUID.fromString(string.trim()));
                                b++;
                            }
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return list;
            }
        }
        return list;
    }

    public static void setOnline(UUID user, boolean online) {
        registerIfNeeded(user);
        NeoBungeeSystem.mySQL.updateWithBoolean("UPDATE Friends SET OnlineStatus = ? WHERE UUID = '" + user + "'", online);
    }

    public static void registerIfNeeded(UUID user) {
        if (!isRegistered(user))
            NeoBungeeSystem.mySQL.updateWithBoolean("INSERT INTO Friends (UUID, FriendUUIDs, OnlineStatus) VALUES ('" + user.toString() + "', '" + (new ArrayList()).toString() + "', ?)", true);
    }

    public static boolean isRegistered(UUID user) {
        ResultSet rs = NeoBungeeSystem.mySQL.getResult("SELECT * FROM Friends WHERE UUID = '" + user.toString() + "'");
        try {
            if (rs != null &&
                    rs.next() &&
                    rs.getString("FriendUUIDs") != null)
                return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isOnline(UUID user) {
        ResultSet rs = NeoBungeeSystem.mySQL.getResult("SELECT * FROM Friends WHERE UUID = '" + user.toString() + "'");
        if (rs != null)
            try {
                if (rs.next())
                    return rs.getBoolean("OnlineStatus");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return false;
    }
}
