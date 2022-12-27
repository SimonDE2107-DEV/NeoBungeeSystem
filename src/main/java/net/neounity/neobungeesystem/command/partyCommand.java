package net.neounity.neobungeesystem.command;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.neounity.neobungeesystem.listener.PartyManager;
import net.neounity.neobungeesystem.util.Data;
import net.neounity.neobungeesystem.util.Party;
import net.neounity.neobungeesystem.util.PartyRank;

import java.util.Objects;

public class partyCommand extends Command {
    public partyCommand(String name) {
        super(name);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) {
            sender.sendMessage(Data.ONLY_INGAME);
            return;
        }
        ProxiedPlayer proxiedPlayer = (ProxiedPlayer) sender;

        if (args.length >= 0) {
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("list")) {
                    if (PartyManager.isInParty(proxiedPlayer)) {
                        Party party = PartyManager.getPlayerParty(proxiedPlayer);
                        proxiedPlayer.sendMessage(Data.PREFIX + " ");
                        for (ProxiedPlayer members : party.getMembers()) {
                            proxiedPlayer.sendMessage(Data.PREFIX + "§e" + members.getName() + " §8(§6" + ((PartyRank) party.members.get(members)).name() + "§8)");
                        }
                        proxiedPlayer.sendMessage(Data.PREFIX + " ");
                    } else {
                        proxiedPlayer.sendMessage(Data.PREFIX + "§cDu bist in keiner Party!");
                    }
                } else if (args[0].equalsIgnoreCase("leave")) {
                    if (PartyManager.isInParty(proxiedPlayer)) {
                        PartyManager.getPlayerParty(proxiedPlayer).leaveParty(proxiedPlayer);
                    } else {
                        proxiedPlayer.sendMessage(Data.PREFIX + "§cDu bist in keiner Party!");
                    }
                } else {
                    sendHelp(proxiedPlayer);
                }
            } else if (args.length >= 1) {
                ProxiedPlayer target = ProxyServer.getInstance().getPlayer(args[1]);
                if (args[0].equalsIgnoreCase("accept")) {
                    if (target != null) {
                        if (PartyManager.isRequestOpen(target, proxiedPlayer)) {
                            PartyManager.requests.remove(target);
                            PartyManager.getPlayerParty(target).addMember(proxiedPlayer);
                            proxiedPlayer.sendMessage(Data.PREFIX + "§aDu hast die §4Einladung §aangenommen!");
                            for (ProxiedPlayer members : PartyManager.getPlayerParty(proxiedPlayer).getMembers()) {
                                members.sendMessage(Data.PREFIX + "§7Der Spieler §e" + proxiedPlayer.getName() + " §7ist der §eParty §abeigetreten");
                            }
                        } else {
                            proxiedPlayer.sendMessage(Data.PREFIX + "§cDu hast keine §ePartyanfrage §cerhalten!");
                        }
                    } else {
                        proxiedPlayer.sendMessage(Data.NOT_ONLINE);
                    }
                } else if (args[0].equalsIgnoreCase("deny")) {
                    if (target != null) {
                        if (PartyManager.isRequestOpen(target, proxiedPlayer)) {
                            PartyManager.requests.remove(target);
                            proxiedPlayer.sendMessage(Data.PREFIX + "§7Du hast die §ePartyanfrage §cabgelehnt!");
                            target.sendMessage(Data.PREFIX + "§e" + proxiedPlayer.getName() + " §7hat die §ePartyeinladung §cabgelehnt!");
                        } else {
                            proxiedPlayer.sendMessage(Data.PREFIX + "§cDu hast keine §eEinladung §czu einer §eParty!");
                        }
                    } else {
                        proxiedPlayer.sendMessage(Data.NOT_ONLINE);
                    }
                } else if (args[0].equalsIgnoreCase("kick")) {
                    if (PartyManager.isInParty(proxiedPlayer)) {
                        if (target != null) {
                            if (PartyManager.getPlayerParty(proxiedPlayer).isMember(target)) {
                                if ((Objects.requireNonNull(PartyManager.getPlayerParty(proxiedPlayer))).members.get(proxiedPlayer) == PartyRank.MOD || (Objects.requireNonNull(PartyManager.getPlayerParty(proxiedPlayer))).members.get(proxiedPlayer) == PartyRank.LEADER) {
                                    PartyManager.getPlayerParty(proxiedPlayer).leaveParty(target);
                                    proxiedPlayer.sendMessage(Data.PREFIX + "§aDu hast §e" + target.getName() + " §aaus der Party §cgekickt!");
                                    target.sendMessage(Data.PREFIX + "§cDu wurdest aus der §eParty §cgekickt!");
                                } else {
                                    proxiedPlayer.sendMessage(Data.PREFIX + "§cDu bist kein §eParty-Leader §coder §e- Mod!");
                                }
                            } else {
                                proxiedPlayer.sendMessage(Data.PREFIX + "§cDer Spieler befindet sich nicht in deiner Party!");
                            }
                        } else {
                            proxiedPlayer.sendMessage(Data.NOT_ONLINE);
                        }
                    }
                } else if (args[0].equalsIgnoreCase("promote")) {
                    if (PartyManager.isInParty(proxiedPlayer)) {
                        if (target != null) {
                            if (PartyManager.getPlayerParty(proxiedPlayer).isMember(target)) {
                                if ((Objects.requireNonNull(PartyManager.getPlayerParty(proxiedPlayer))).members.get(proxiedPlayer) == PartyRank.LEADER) {
                                    PartyManager.getPlayerParty(proxiedPlayer).promote(target);
                                    proxiedPlayer.sendMessage(Data.PREFIX + "§aDu hast §e" + target.getName() + " §apromoted!");
                                    target.sendMessage(Data.PREFIX + "§aDu wurdest von §e" + proxiedPlayer.getName() + " §apromoted!");
                                } else {
                                    proxiedPlayer.sendMessage(Data.PREFIX + "§cDu bist kein §eParty-Leader!");
                                }
                            } else {
                                proxiedPlayer.sendMessage(Data.PREFIX + "§cDer Spieler befindet sich nicht in deiner Party!");
                            }
                        } else {
                            proxiedPlayer.sendMessage(Data.NOT_ONLINE);
                        }
                    }
                } else if (args[0].equalsIgnoreCase("demote")) {
                    if (PartyManager.isInParty(proxiedPlayer)) {
                        if (target != null) {
                            if (PartyManager.getPlayerParty(proxiedPlayer).isMember(target)) {
                                if ((Objects.requireNonNull(PartyManager.getPlayerParty(proxiedPlayer))).members.get(proxiedPlayer) == PartyRank.LEADER) {
                                    PartyManager.getPlayerParty(proxiedPlayer).demote(target);
                                    proxiedPlayer.sendMessage(Data.PREFIX + "§aDu hast §e" + target.getName() + " §cdemoted!");
                                    target.sendMessage(Data.PREFIX + "§7Du wurdest von §e" + proxiedPlayer.getName() + " §cdemoted");
                                } else {
                                    proxiedPlayer.sendMessage(Data.PREFIX + "Du bist kein Party §eLeader");
                                }
                            } else {
                                proxiedPlayer.sendMessage(Data.PREFIX + "Der Spieler ist §c§lnicht §7in deiner Party");
                            }
                        } else {
                            proxiedPlayer.sendMessage(Data.PREFIX + "Der Spieler wurde §c§lnicht §7gefunden");
                        }
                    }
                } else if (args[0].equalsIgnoreCase("invite")) {
                    if (target != null) {
                        if (target != proxiedPlayer) {
                            if (!PartyManager.isInParty(target)) {
                                if (!PartyManager.requests.containsKey(proxiedPlayer)) {
                                    PartyManager.requests.put(proxiedPlayer, target);
                                    PartyManager.parties.add(new Party(proxiedPlayer));
                                    proxiedPlayer.sendMessage(Data.PREFIX + "Du hast §e" + target.getName() + " §7in deine Party §a§leingeladen");
                                    TextComponent message = new TextComponent(" ");
                                    TextComponent accept = new TextComponent("§a§lAkzeptieren");
                                    // TODO: Change the textcomp in the Language
                                    accept.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/party accept " + proxiedPlayer.getName()));
                                    message.addExtra(accept);
                                    message.addExtra(new TextComponent(" §8● §r"));
                                    TextComponent deny = new TextComponent("§c§lAblehnen");
                                    deny.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/party deny " + proxiedPlayer.getName()));
                                    message.addExtra(deny);
                                    target.sendMessage(Data.PREFIX + "Du wurdest von §e" + proxiedPlayer.getName() + " §7in eine Party §a§leingeladen");
                                    target.sendMessage(message);
                                } else {
                                    proxiedPlayer.sendMessage(Data.PREFIX + "Du hast bereits eine Anfrage gesendet");
                                }
                            } else {
                                proxiedPlayer.sendMessage(Data.PREFIX + "Der Spieler ist bereits in einer Party");
                            }
                        } else {
                            proxiedPlayer.sendMessage(Data.PREFIX + "Du kannst dich §c§lnicht §7selbst einladen");
                        }
                    } else {
                        proxiedPlayer.sendMessage(Data.PREFIX + "Der Spieler wurde §c§lnicht §7gefunden");
                    }
                } else {
                    sendHelp(proxiedPlayer);
                }
            } else {
                sendHelp(proxiedPlayer);
            }
        }
    }

    private static void sendHelp(ProxiedPlayer p) {
        p.sendMessage(Data.PREFIX + "§6Partyverwaltung");
        p.sendMessage(Data.PREFIX + "§e/party invite <Spieler> §8» §7Lädt Spieler in die Party ein");
        p.sendMessage(Data.PREFIX + "§e/party accept §8» §7Nimmt eine Anfrage an");
        p.sendMessage(Data.PREFIX + "§e/party deny §8» §7Lehnt eine Anfrage ab");
        p.sendMessage(Data.PREFIX + "§e/party list §8» §7Listet alle Party-Mitglieder  auf");
        p.sendMessage(Data.PREFIX + "§e/party leave §8» §7Verlässt die Party");
        p.sendMessage(Data.PREFIX + "§e/party kick <Spieler> §8» §7Kickt einen Spieler aus der Party");
        p.sendMessage(Data.PREFIX + "§e/party promote <Spieler> §8» §7Befördert einen Spieler");
        p.sendMessage(Data.PREFIX + "§e/party demote <Spieler> §8» §7Degradiert einen Spieler");
    }
}
