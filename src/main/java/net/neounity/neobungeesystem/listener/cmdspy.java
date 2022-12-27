package net.neounity.neobungeesystem.listener;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.neounity.neobungeesystem.util.Data;

public class cmdspy implements Listener {

    @EventHandler
    public void onChat(ChatEvent event) {
        ProxiedPlayer player = (ProxiedPlayer) event.getSender();
        if (event.getMessage().startsWith("/")) {
            for (ProxiedPlayer cmdSpyers : ProxyServer.getInstance().getPlayers()) {
                if (Data.cmdspy.contains(cmdSpyers)) {
                    if (cmdSpyers.hasPermission("neobungeesystem.cmdspy")) {
                        if (player != cmdSpyers) {
                            String server = player.getServer().getInfo().getName();
                            TextComponent tc = new TextComponent();
                            tc.setText(Data.PREFIX + "§b§lCommand §8| §f§l" + player.getName() + " §7> §f§l" + event.getMessage() + " §7> §f§l" + server + " §8<- §a§lKLICK");
                            tc.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/server " + player.getServer().getInfo().getName()));
                            tc.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§7Klicke, um Dich zu §a" + player.getName() + "'s §7Server zu teleportieren.").create()));
                            cmdSpyers.sendMessage(tc);
                        }
                    } else {
                        Data.cmdspy.remove(player);
                    }
                }
            }
        }
    }
}
