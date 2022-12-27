package net.neounity.neobungeesystem.command;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.neounity.neobungeesystem.util.Data;

public class adminchatCommand extends Command {
    public adminchatCommand(String name) {
        super(name, "", "ac");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender.hasPermission("neobungeesystem.adminchat")) {
            if (args.length > 0) {

                String sm = "";
                for (int i = 0; i < args.length; i++) {
                    String arg = (args[i] + " ");
                    sm = (sm + arg);
                }
                for (ProxiedPlayer all : ProxyServer.getInstance().getPlayers()) {
                    if (all.hasPermission("neobungeesystem.adminchat")) {
                        all.sendMessage("§8» ");
                        all.sendMessage(Data.PREFIX + "§4Admin§eChat §8➥ §b§l" + sender.getName() + " §8➥ §c§l" + sm);
                        all.sendMessage("§8» ");

                        ProxyServer.getInstance().getConsole().sendMessage(Data.PREFIX + "§4Admin§eChat §8➥ §b§l" + sender.getName() + " §8➥ §c§l" + sm);
                    }
                }

            } else {
                    sender.sendMessage(Data.USAGE + "/adminchat [Nachricht ...]");
                    sender.sendMessage(Data.ALIASES+"/ac");
            }
        } else {
                sender.sendMessage(Data.NO_PERMISSIONS);
        }
    }
}
