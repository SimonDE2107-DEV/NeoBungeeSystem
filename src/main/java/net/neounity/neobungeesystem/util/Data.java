package net.neounity.neobungeesystem.util;

import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.ArrayList;
import java.util.HashMap;

public class Data {

    public static ArrayList<ProxiedPlayer> cmdspy = new ArrayList<>();
    public static HashMap<ProxiedPlayer, ProxiedPlayer> chats = new HashMap<>();
    public static HashMap<ProxiedPlayer, String> JOINME = new HashMap<>();



    public static String PREFIX = "§x§1§b§c§3§1§1N§x§3§3§b§6§2§ae§x§4§b§a§a§4§3o§x§6§2§9§d§5§cU§x§7§a§9§1§7§5n§x§9§2§8§4§8§ei§x§a§a§7§7§a§7t§x§c§1§6§b§c§0y §8✘ §7";
    public static String USAGE = PREFIX + "§cSyntax: §e";
    public static String NO_PERMISSIONS = PREFIX + "§cDu darfst diesen Befehl nicht ausführen!";
    public static String ONLY_INGAME = PREFIX + "§cDieseer Befehl funktioniert nur im Spiel!";


    public static String ALIASES = PREFIX + "§8§l∟§aAlias(e): ";
    public static String NOT_ONLINE = PREFIX + "§cDer angegebene Spieler ist nicht online!";
    public static String NOT_REGISTRED = PREFIX + "§cDieser Spieler war noch nie auf dem Netzwerk.";
    public static String DONT_TARGET_YOURSELF = PREFIX + "§cBitte verwende einen anderen Spieler und nicht dich selbst!";


    public static String sqlDatabase = "bungeesystem";
    public static String sqlUser = "bungeesystem";
    public static String sqlPassword = "SUPERduperGEHEIM";
}