package net.neounity.neobungeesystem.command;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.neounity.neobungeesystem.listener.PlayerInfo;
import net.neounity.neobungeesystem.util.Data;

public class playtimeCommand extends Command {
    public playtimeCommand(String name) {
        super(name, "", "pt");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        if (args.length == 0) {
            if (sender instanceof ProxiedPlayer) {
                ProxiedPlayer player = (ProxiedPlayer) sender;

                player.sendMessage("§8» ");
                player.sendMessage(Data.PREFIX + "§eDeine Spielzeit §8➥ §b§l" + PlayerInfo.getHours(player) + " Stunden,");
                player.sendMessage(Data.PREFIX + "§eDeine Spielzeit §8➥ §b§l" + PlayerInfo.getMinutes(player) + " Minuten.");
                player.sendMessage("§8» ");

            } else {
                sender.sendMessage(Data.PREFIX + "§cAls Konsole kannst du nur die Spielzeit eines anderen Spielers sehen, denn die Konsole besitzt keine Spielzeit.");
                sender.sendMessage(Data.USAGE + "/playtime [Spieler]");
                sender.sendMessage(Data.ALIASES + "/pt");
            }

        } else if (args.length == 1) {
            if (sender.hasPermission("condurobungeesystem.playtime.others")) {
                ProxiedPlayer target = ProxyServer.getInstance().getPlayer(args[0]);

                if (target != null) {

                    if (target != sender) {

                        sender.sendMessage("§8» ");
                        sender.sendMessage(Data.PREFIX + "§e" + target.getName() + "'s Spielzeit §8➥ §b§l" + PlayerInfo.getHours(target) + " Stunden,");
                        sender.sendMessage(Data.PREFIX + "§e" + target.getName() + "'s Spielzeit §8➥ §b§l" + PlayerInfo.getMinutes(target) + " Minuten.");
                        sender.sendMessage("§8» ");
                    } else {
                        sender.sendMessage(Data.DONT_TARGET_YOURSELF);
                    }


                } else {
                    sender.sendMessage(Data.NOT_ONLINE);
                }


            } else {
                sender.sendMessage(Data.NO_PERMISSIONS);
            }

        } else {
            sender.sendMessage(Data.USAGE + "/playtime <Spieler>");
            sender.sendMessage(Data.ALIASES + "/pt");
        }
    }
}