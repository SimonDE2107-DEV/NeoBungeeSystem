package net.neounity.neobungeesystem.listener;

import net.neounity.neobungeesystem.NeoBungeeSystem;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;


public class FriendSettingsManager {

    public static void registerIfNeeded(UUID user) {
        if (!isRegistered(user))
            NeoBungeeSystem.mySQL.update("INSERT INTO FriendSettings (UUID, Requests, Notify, FriendChat, ServerJumping) VALUES ('" + user.toString() + "', true, true, true, true)");
    }

    public static boolean isRegistered(UUID user) {
        ResultSet rs = NeoBungeeSystem.mySQL.getResult("SELECT * FROM FriendSettings WHERE UUID = '" + user.toString() + "'");
        try {
            if (rs != null &&
                    rs.next())
                return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void setGetRequests(UUID user, boolean b) {
        registerIfNeeded(user);
        NeoBungeeSystem.mySQL.update("UPDATE FriendSettings SET Requests = " + b + " WHERE UUID = '" + user + "'");
    }

    public static boolean isGettingRequests(UUID user) {
        ResultSet rs = NeoBungeeSystem.mySQL.getResult("SELECT * FROM FriendSettings WHERE UUID = '" + user.toString() + "'");
        if (rs != null)
            try {
                if (rs.next())
                    return rs.getBoolean("Requests");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return true;
    }

    public static void setGetNotified(UUID user, boolean b) {
        registerIfNeeded(user);
        NeoBungeeSystem.mySQL.update("UPDATE FriendSettings SET Notify = " + b + " WHERE UUID = '" + user + "'");
    }

    public static boolean isGettingNotified(UUID user) {
        ResultSet rs = NeoBungeeSystem.mySQL.getResult("SELECT * FROM FriendSettings WHERE UUID = '" + user.toString() + "'");
        if (rs != null)
            try {
                if (rs.next())
                    return rs.getBoolean("Notify");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return true;
    }

    public static void setUseFriendChat(UUID user, boolean b) {
        registerIfNeeded(user);
        NeoBungeeSystem.mySQL.update("UPDATE FriendSettings SET FriendChat = " + b + " WHERE UUID = '" + user + "'");
    }

    public static boolean isUsingFriendChat(UUID user) {
        ResultSet rs = NeoBungeeSystem.mySQL.getResult("SELECT * FROM FriendSettings WHERE UUID = '" + user.toString() + "'");
        if (rs != null)
            try {
                if (rs.next())
                    return rs.getBoolean("FriendChat");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return true;
    }

    public static void setServerJuming(UUID user, boolean b) {
        registerIfNeeded(user);
        NeoBungeeSystem.mySQL.update("UPDATE FriendSettings SET ServerJumping = " + b + " WHERE UUID = '" + user + "'");
    }

    public static boolean isAllowingServerJumping(UUID user) {
        ResultSet rs = NeoBungeeSystem.mySQL.getResult("SELECT * FROM FriendSettings WHERE UUID = '" + user.toString() + "'");
        if (rs != null)
            try {
                if (rs.next())
                    return rs.getBoolean("ServerJumping");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return true;
    }

}
