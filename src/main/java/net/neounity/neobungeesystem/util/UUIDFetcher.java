package net.neounity.neobungeesystem.util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class UUIDFetcher {
    public static String getUUID(String name) {
        try {
            PreparedStatement State = MySQL.con.prepareStatement("SELECT * FROM Infos WHERE NAME=?;");
            State.setString(1, name);
            ResultSet Result = State.executeQuery();
            Result.next();
            String uuid = Result.getString("UUID");
            Result.close();
            State.close();
            return uuid;
        } catch (SQLException e) {
            e.printStackTrace();
            return "null";
        }
    }

    public static UUID getUniqueID(String name) {
        try {
            PreparedStatement State = MySQL.con.prepareStatement("SELECT * FROM Infos WHERE NAME=?;");
            State.setString(1, name);
            ResultSet Result = State.executeQuery();
            Result.next();
            UUID uuid = UUID.fromString(Result.getString("UUID"));
            Result.close();
            State.close();
            return uuid;
        } catch (SQLException e) {
            e.printStackTrace();
            return UUID.randomUUID();
        }
    }

    public static String getName(String uuid){
        try {
            PreparedStatement State = MySQL.con.prepareStatement("SELECT * FROM Infos WHERE UUID=?;");
            State.setString(1, uuid);
            ResultSet Result = State.executeQuery();
            Result.next();
            String name = Result.getString("NAME");
            Result.close();
            State.close();
            return name;
        } catch (SQLException e) {
            e.printStackTrace();
            return "null";
        }
    }

    public static String getNameByUUID(UUID uuid){
        try {
            PreparedStatement State = MySQL.con.prepareStatement("SELECT * FROM Infos WHERE UUID=?;");
            State.setString(1, String.valueOf(uuid));
            ResultSet Result = State.executeQuery();
            Result.next();
            String name = Result.getString("NAME");
            Result.close();
            State.close();
            return name;
        } catch (SQLException e) {
            e.printStackTrace();
            return "null";
        }
    }
}
