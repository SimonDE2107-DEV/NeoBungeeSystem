package net.neounity.neobungeesystem.command;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.neounity.neobungeesystem.listener.FriendManager;
import net.neounity.neobungeesystem.listener.FriendSettingsManager;
import net.neounity.neobungeesystem.util.Data;

public class msgCommand extends Command {
    public msgCommand(String name) {
        super(name);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) {
            sender.sendMessage(Data.ONLY_INGAME);
            return;
        }
        ProxiedPlayer player = (ProxiedPlayer) sender;
        if (args.length > 1) {
            ProxiedPlayer target = ProxyServer.getInstance().getPlayer(args[0]);
            if (target != null) {
                if (target != player) {

                    if (FriendManager.isFriend(player.getUniqueId(), target.getUniqueId())) {
                        if (FriendSettingsManager.isUsingFriendChat(target.getUniqueId())) {

                            String msg = "";
                            for (int i = 1; i < args.length; i++) {
                                String arg = (args[i] + " ");
                                msg = (msg + arg);
                            }
                            if (Data.chats.containsKey(player)) {
                                Data.chats.remove(player);
                            }
                            if (Data.chats.containsKey(target)) {
                                Data.chats.remove(target);
                            }
                            Data.chats.put(player, target);
                            Data.chats.put(target, player);
                            player.sendMessage(Data.PREFIX + "§eMSG §8➥ §b§l" + msg + " §8➥ §b§l" + target.getName());

                            target.sendMessage(Data.PREFIX + "§eMSG §8➥ §b§l" + player.getName() + " §8➥ §b§l" + msg);
                        } else {
                            player.sendMessage(Data.PREFIX + "§e" + target.getName() + " §chat seine privaten Nachrichten §edeaktiviert!");

                        }
                    } else {
                        player.sendMessage(Data.PREFIX + "§cDu bist nicht mit §e" + target.getName() + " §cbefreundet!");

                    }
                } else {
                    player.sendMessage(Data.DONT_TARGET_YOURSELF);
                }
            } else {
                player.sendMessage(Data.NOT_ONLINE);
            }

        } else {
            player.sendMessage(Data.USAGE + "/msg [Spieler] [Nachricht ...]");
        }
    }
}
