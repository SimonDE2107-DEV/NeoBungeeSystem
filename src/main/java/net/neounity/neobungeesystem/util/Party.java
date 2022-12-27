package net.neounity.neobungeesystem.util;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.neounity.neobungeesystem.listener.PartyManager;

import java.util.Collection;
import java.util.HashMap;

public class Party {
    private ProxiedPlayer leader;

    public HashMap<ProxiedPlayer, PartyRank> members = new HashMap<>();

    public Party(ProxiedPlayer leader) {
        this.leader = leader;
        this.members.put(leader, PartyRank.LEADER);
    }

    public void addMember(ProxiedPlayer member) {
        this.members.put(member, PartyRank.MEMBER);
    }

    public void promote(ProxiedPlayer p) {
        if (this.members.get(p) == PartyRank.MEMBER) {
            this.members.remove(p);
            this.members.put(p, PartyRank.MOD);
        } else if (this.members.get(p) == PartyRank.MOD) {
            setLeader(p);
        }
    }

    public void demote(ProxiedPlayer p) {
        if (this.members.get(p) == PartyRank.MOD) {
            this.members.remove(p);
            this.members.put(p, PartyRank.MEMBER);
        }
    }

    public void setLeader(ProxiedPlayer p) {
        this.members.remove(this.leader);
        this.members.put(this.leader, PartyRank.MOD);
        this.members.remove(p);
        this.members.put(p, PartyRank.LEADER);
        this.leader = p;
    }

    public void leaveParty(ProxiedPlayer p) {
        for (ProxiedPlayer member : getMembers())
            member.sendMessage(Data.PREFIX + "Der Spieler §e" + p.getName() + " §7hat die Party §c§lverlassen");
        this.members.remove(p);
        if (this.leader == p) {
            for (ProxiedPlayer member : getMembers())
                member.sendMessage(Data.PREFIX + "Die Party wurde §c§laufgelöst");
            p.sendMessage(Data.PREFIX + "Die Party wurde §c§laufgelöst");
            PartyManager.parties.remove(this);
        }
    }

    public ProxiedPlayer getLeader() {
        return this.leader;
    }

    public Collection<ProxiedPlayer> getMembers() {
        return this.members.keySet();
    }

    public boolean isMember(ProxiedPlayer p) {
        return getMembers().contains(p);
    }
}