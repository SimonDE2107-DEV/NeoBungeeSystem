package net.neounity.neobungeesystem.util;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.neounity.neobungeesystem.NeoBungeeSystem;
import net.neounity.neobungeesystem.listener.PlayerInfo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

public class OnlineTime {

    public void start(){
        ProxyServer.getInstance().getScheduler().schedule(NeoBungeeSystem.plugin, () -> {
            ProxyServer.getInstance().getPlayers().forEach(all -> {
                if(PlayerInfo.getMinutes(all) == 60){
                    PlayerInfo.addHours(all, 1);
                    PlayerInfo.setMinutes(all, 0);
                }else{
                    PlayerInfo.addMinutes(all, 1);
                }
            });
        }, 1, 1, TimeUnit.MINUTES);
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
}
