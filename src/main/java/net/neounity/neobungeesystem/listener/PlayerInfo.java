package net.neounity.neobungeesystem.listener;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.neounity.neobungeesystem.NeoBungeeSystem;
import net.neounity.neobungeesystem.util.MySQL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayerInfo implements Listener {

    @EventHandler
    public void onConnect(ServerConnectEvent event){
        if (!PlayerInfo.playerExists(event.getPlayer())) {
            PlayerInfo.createPlayer(event.getPlayer().getName(), event.getPlayer(), event.getPlayer().getAddress().getAddress().getHostAddress());
        } else {
            updateIPAdress(event.getPlayer());
        }
    }

    public static boolean playerExists(ProxiedPlayer proxiedPlayer){
        try {
            ResultSet rs = NeoBungeeSystem.mySQL.query("SELECT * FROM Infos WHERE UUID= '" + proxiedPlayer.getUniqueId().toString() + "'");
            if(rs.next()){
                return rs.getString("UUID") != null;
            }
            return false;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public static void createPlayer(String name, ProxiedPlayer proxiedPlayer, String ip){
        if(!(playerExists(proxiedPlayer))){
            NeoBungeeSystem.mySQL.update("INSERT INTO Infos (NAME, UUID, HOURS, MINUTES, IP) VALUES ('" + name + "', '" + proxiedPlayer.getUniqueId().toString() + "', '0', '0', '" + ip + "');");
        }
    }
    public static void updateIPAdress(ProxiedPlayer proxiedPlayer) {
        NeoBungeeSystem.mySQL.update("UPDATE Infos SET IP='" + proxiedPlayer.getAddress().getAddress().getHostAddress() + "' WHERE UUID='" + proxiedPlayer.getUniqueId().toString() + "'");
    }

    public static int getHours(ProxiedPlayer proxiedPlayer) {
        try {
            PreparedStatement State = MySQL.con.prepareStatement("SELECT * FROM Infos WHERE UUID=?;");
            State.setString(1, proxiedPlayer.getUniqueId().toString());
            ResultSet Result = State.executeQuery();
            Result.next();
            int i = Result.getInt("HOURS");
            Result.close();
            State.close();
            return i;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static int getMinutes(ProxiedPlayer proxiedPlayer) {
        try {
            PreparedStatement State = MySQL.con.prepareStatement("SELECT * FROM Infos WHERE UUID=?;");
            State.setString(1, proxiedPlayer.getUniqueId().toString());
            ResultSet Result = State.executeQuery();
            Result.next();
            int i = Result.getInt("MINUTES");
            Result.close();
            State.close();
            return i;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }




    public static void setHours(ProxiedPlayer proxiedPlayer, Integer value) {
        NeoBungeeSystem.mySQL.update("UPDATE Infos SET HOURS='" + value + "' WHERE UUID='" + proxiedPlayer.getUniqueId().toString() + "'");
    }

    public static void setMinutes(ProxiedPlayer proxiedPlayer, Integer value) {
        NeoBungeeSystem.mySQL.update("UPDATE Infos SET MINUTES='" + value + "' WHERE UUID='" + proxiedPlayer.getUniqueId().toString() + "'");
    }

    public static void addHours(ProxiedPlayer proxiedPlayer, Integer value) {
        setHours(proxiedPlayer, getHours(proxiedPlayer) + value);
    }

    public static void addMinutes(ProxiedPlayer proxiedPlayer, Integer value) {
        setMinutes(proxiedPlayer, getMinutes(proxiedPlayer) + value);
    }

}
