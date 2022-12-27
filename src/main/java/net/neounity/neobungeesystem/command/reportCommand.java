package net.neounity.neobungeesystem.command;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.neounity.neobungeesystem.util.Data;

public class reportCommand extends Command {
    public reportCommand(String name) {
        super(name);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) {
            sender.sendMessage(Data.ONLY_INGAME);
            return;
        }
        ProxiedPlayer proxiedPlayer = (ProxiedPlayer) sender;

        if (args.length != 2) {
            proxiedPlayer.sendMessage(Data.USAGE + "/report [Spieler] [Grund]");
            return;
        }

        if (ProxyServer.getInstance().getPlayer(args[0]) != null) {
            // TODO: Check if player is registered on the server, because he could just disconnect from the servcer to avoid getting repoirted
            ProxiedPlayer target = ProxyServer.getInstance().getPlayer(args[0]);
            String reason = args[1];

            if (target.hasPermission("neobungeesystem.report.staff")) {
                proxiedPlayer.sendMessage(Data.PREFIX+"§cDu kannst diesen Spieler nicht melden!");
                return;
            }


            if (target == proxiedPlayer) {
                proxiedPlayer.sendMessage(Data.DONT_TARGET_YOURSELF);
                return;
            }
            proxiedPlayer.sendMessage(Data.PREFIX + "§7Du hast den Spieler §c" + target.getName() + " §7für §4" + reason + " §7gemeldet!");

            ProxyServer.getInstance().getPlayers().forEach(all -> {
                if (all.hasPermission("neobungeesystem.report.staff")) {
                    TextComponent tc = new TextComponent();
                    tc.setText(Data.PREFIX + "§dServer §8» §d" + target.getServer().getInfo().getName());
                    tc.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/server " + target.getServer().getInfo().getName()));
                    tc.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§7Klicke, um Dich auf §d" + target.getServer().getInfo().getName() + " §7zu teleportieren.").create()));


                    all.sendMessage(Data.PREFIX + " ");
                    all.sendMessage(Data.PREFIX + "§7Eine neue §4Meldung §7wurde §aerstellt§7!");
                    all.sendMessage(Data.PREFIX + " ");
                    all.sendMessage(Data.PREFIX + "§aReporter §8» §a" + proxiedPlayer.getName());
                    all.sendMessage(Data.PREFIX + "§4Gemeldeter §8» §4" + target.getName());
                    all.sendMessage(Data.PREFIX + "§4Grund §8» §4" + reason);
                    all.sendMessage(tc);
                    all.sendMessage(Data.PREFIX + " ");
                }
            });

        } else {
            proxiedPlayer.sendMessage(Data.NOT_ONLINE);
        }
    }
}
