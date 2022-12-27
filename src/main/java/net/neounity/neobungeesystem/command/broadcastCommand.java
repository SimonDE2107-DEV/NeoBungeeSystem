package net.neounity.neobungeesystem.command;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.neounity.neobungeesystem.util.Data;

public class broadcastCommand extends Command {
    public broadcastCommand(String name) {
        super(name, "", "bc");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender.hasPermission("neobungeesystem.broadcast")) {
            if (args.length > 0) {

                String sm = "";
                for (int i = 0; i < args.length; i++) {
                    String arg = (args[i] + " ");
                    sm = (sm + arg);
                }
                for (ProxiedPlayer all : ProxyServer.getInstance().getPlayers()) {
                        all.sendMessage("§8» ");
                        all.sendMessage(Data.PREFIX + "§bRund§fRuf §8➥ §b§l" + sm);
                        all.sendMessage("§8» ");

                        ProxyServer.getInstance().getConsole().sendMessage(Data.PREFIX + "§bRund§fRuf §8➥ §b§l" + sm);
                }

            } else {
                    sender.sendMessage(Data.USAGE + "/broadcast [Nachricht ...]");
                    sender.sendMessage(Data.ALIASES+"/bc");
            }
        } else {
                sender.sendMessage(Data.NO_PERMISSIONS);
        }
    }
}
