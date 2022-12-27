package net.neounity.neobungeesystem.command;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.neounity.neobungeesystem.util.Data;

public class teamchatCommand extends Command {
    public teamchatCommand(String name) {
        super(name, "", "tc");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender.hasPermission("neobungeesystem.teamchat") || sender.hasPermission("neobungeesystem.adminchat")) {
            if (args.length > 0) {

                String sm = "";
                for (int i = 0; i < args.length; i++) {
                    String arg = (args[i] + " ");
                    sm = (sm + arg);
                }
                for (ProxiedPlayer all : ProxyServer.getInstance().getPlayers()) {
                    if (all.hasPermission("neobungeesystem.teamchat") || all.hasPermission("neobungeesystem.adminchat")) {
                        all.sendMessage("§8» ");
                        all.sendMessage(Data.PREFIX + "§9Team§eChat §8➥ §b§l" + sender.getName() + " §8➥ §9§l" + sm);
                        all.sendMessage("§8» ");

                        ProxyServer.getInstance().getConsole().sendMessage(Data.PREFIX + "§9Team§eChat §8➥ §b§l" + sender.getName() + " §8➥ §9§l" + sm);
                    }
                }

            } else {
                sender.sendMessage(Data.USAGE + "/teamchat [Nachricht ...]");
                sender.sendMessage(Data.ALIASES+"/tc");
            }
        } else {
            sender.sendMessage(Data.NO_PERMISSIONS);
        }
    }
}
