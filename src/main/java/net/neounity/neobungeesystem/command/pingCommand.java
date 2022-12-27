package net.neounity.neobungeesystem.command;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.neounity.neobungeesystem.util.Data;

public class pingCommand extends Command {
    public pingCommand(String name) {
        super(name);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) {
            sender.sendMessage(Data.ONLY_INGAME);
            return;
        }
        ProxiedPlayer player = (ProxiedPlayer) sender;
        if (args.length == 0) {
            player.sendMessage("§8» ");
            player.sendMessage(Data.PREFIX + "§eDein Latenz §8➥ §b§l" + player.getPing() + "ms");
            player.sendMessage("§8» ");
        } else {
            player.sendMessage(Data.USAGE + "/ping");
        }
    }
}
