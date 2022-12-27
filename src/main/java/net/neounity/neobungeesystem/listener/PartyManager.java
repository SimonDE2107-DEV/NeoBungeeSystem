package net.neounity.neobungeesystem.listener;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.neounity.neobungeesystem.NeoBungeeSystem;
import net.neounity.neobungeesystem.util.Data;
import net.neounity.neobungeesystem.util.Party;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class PartyManager implements Listener {

    @EventHandler
    public void onServerSwitch(ServerSwitchEvent e) {
        ProxiedPlayer p = e.getPlayer();
        if (PartyManager.isInParty(p)) {
            Party party = PartyManager.getPlayerParty(p);
            if (party.getLeader() == p)
                for (ProxiedPlayer member : party.getMembers()) {
                    if (member != p)
                        member.connect(p.getServer().getInfo());

                    member.sendMessage(Data.PREFIX + "Die Party betritt den Server §e" + p.getServer().getInfo().getName());

                }
        }
    }

    @EventHandler
    public void onDisconnect(PlayerDisconnectEvent e) {
        final ProxiedPlayer p = e.getPlayer();
        NeoBungeeSystem.plugin.getProxy().getScheduler().schedule(NeoBungeeSystem.plugin, new Runnable() {
            public void run() {
                if ((p == null || !p.isConnected()) &&
                        PartyManager.isInParty(p))
                    PartyManager.getPlayerParty(p).leaveParty(p);
            }
        }, 1L, TimeUnit.SECONDS);
    }
    public static ArrayList<Party> parties = new ArrayList<>();

    public static HashMap<ProxiedPlayer, ProxiedPlayer> requests = new HashMap<>();

    public static boolean isRequestOpen(ProxiedPlayer player, ProxiedPlayer requested) {
        return (requests.containsKey(player) && requests.get(player) == requested);
    }

    public static boolean isInParty(ProxiedPlayer p) {
        boolean b = false;
        for (Party party : parties) {
            if (party.isMember(p))
                b = true;
        }
        return b;
    }

    public static Party getPlayerParty(ProxiedPlayer p) {
        if (isInParty(p))
            for (Party party : parties) {
                if (party.isMember(p))
                    return party;
            }
        return null;
    }
}

