package net.neounity.neobungeesystem.command;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.neounity.neobungeesystem.NeoBungeeSystem;
import net.neounity.neobungeesystem.util.Data;

import java.util.concurrent.TimeUnit;

public class joinmeCommand extends Command {


    public joinmeCommand(String name) {
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
            if (player.hasPermission("neobungeesystem.joinme")) {
                if (!player.getServer().getInfo().getName().contains("Lobby")) {

                    NeoBungeeSystem.plugin.getProxy().getPlayers().forEach(all -> {
                        all.sendMessage(Data.PREFIX + " ");
                        all.sendMessage(Data.PREFIX + " ");
                        all.sendMessage(Data.PREFIX + "§e" + player.getName() + " §7spielt auf §e" + player.getServer().getInfo().getName());

                        TextComponent textComponent = new TextComponent();
                        textComponent.setText(Data.PREFIX + "§7§oKlicke, um den Server zu betreten§8!");
                        textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/joinme " + player.getName()));
                        textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§7§oKlicke, um zum §a§oSpieler §7§ozu gelangen").create()));

                        all.sendMessage(textComponent);
                        all.sendMessage(Data.PREFIX + " ");
                        all.sendMessage(Data.PREFIX + " ");
                    });

                    Data.JOINME.put(player, player.getServer().getInfo().getName());

                    NeoBungeeSystem.plugin.getProxy().getScheduler().schedule(NeoBungeeSystem.plugin, () -> {
                        Data.JOINME.remove(player);
                    }, 1, TimeUnit.MINUTES);
                } else {
                    player.sendMessage(Data.PREFIX + "§cDu darfst auf diesem Server §8[§b" + player.getServer().getInfo().getName() + "§8] §ckein §eJoinME §cerstellen!");
                }
            } else {
                player.sendMessage(Data.NO_PERMISSIONS);
            }
        } else if (args.length == 1) {
            ProxiedPlayer target = NeoBungeeSystem.plugin.getProxy().getPlayer(args[0]);

            if (Data.JOINME.containsKey(target)) {
                String server = Data.JOINME.get(target);
                player.connect(NeoBungeeSystem.plugin.getProxy().getServerInfo(server));
            } else {
                player.sendMessage(Data.PREFIX + "§cDas JoinMe ist bereits abgelaufen!");
            }
        } else {
            player.sendMessage(Data.USAGE + "/joinme");
        }
    }
}
