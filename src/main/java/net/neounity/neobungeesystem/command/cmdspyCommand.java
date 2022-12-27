package net.neounity.neobungeesystem.command;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.neounity.neobungeesystem.util.Data;

public class cmdspyCommand extends Command {

    public cmdspyCommand(String name) {
        super(name);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) {
            sender.sendMessage(Data.ONLY_INGAME);
            return;
        }
        ProxiedPlayer player = (ProxiedPlayer) sender;
        if (player.hasPermission("neobungeesystem.cmdspy")) {
            if (args.length == 0) {
                if (Data.cmdspy.contains(player)) {
                    Data.cmdspy.remove(player);
                    player.sendMessage(Data.PREFIX + "§7Du hast den §eCommandSpy §cdeaktiviert§7!");
                } else {
                    Data.cmdspy.add(player);
                    player.sendMessage(Data.PREFIX + "§7Du hast den §eCommandSpy §aaktiviert§7!");
                }

            } else {
                player.sendMessage(Data.USAGE + "/cmdspy");
            }
        } else {
            player.sendMessage(Data.NO_PERMISSIONS);
        }
    }
}
