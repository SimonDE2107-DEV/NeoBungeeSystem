package net.neounity.neobungeesystem.command;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.neounity.neobungeesystem.NeoBungeeSystem;
import net.neounity.neobungeesystem.listener.FriendManager;
import net.neounity.neobungeesystem.listener.FriendSettingsManager;
import net.neounity.neobungeesystem.util.Data;
import net.neounity.neobungeesystem.util.UUIDFetcher;

import java.util.UUID;

public class friendCommand extends Command {
    public friendCommand(String name) {
        super(name, "", "f");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) {
            sender.sendMessage(Data.ONLY_INGAME);
            return;
        }
        ProxiedPlayer player = (ProxiedPlayer) sender;
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("list")) {
                if (FriendManager.getFriends(player.getUniqueId()).size() >= 1) {
                    for (UUID uuid : FriendManager.getFriends(player.getUniqueId())) {
                        String name = UUIDFetcher.getNameByUUID(uuid);
                        String status = (NeoBungeeSystem.plugin.getProxy().getPlayer(uuid) != null) ? "§aOnline" : "§cOffline";
                        player.sendMessage("§6" + name + " §8» " + status);
                    }
                } else {
                    player.sendMessage("§cDu hast keine Freunde in deiner Freundesliste!");
                }
            } else if (args[0].equalsIgnoreCase("requests")) {
                if (FriendManager.getRequests(player.getUniqueId()).size() >= 1) {
                    for (UUID uuid : FriendManager.getRequests(player.getUniqueId())) {
                        String name = UUIDFetcher.getNameByUUID(uuid);
                        player.sendMessage("§6" + name);
                    }
                } else {
                    player.sendMessage("§cDu hast keine offenen Freundesanfragen!");
                }
            } else if (args[0].equalsIgnoreCase("acceptall")) {
                if (FriendManager.getRequests(player.getUniqueId()).size() >= 1) {
                    for (UUID uuid : FriendManager.getRequests(player.getUniqueId())) {
                        NeoBungeeSystem.plugin.getProxy().getPluginManager().dispatchCommand(player, "friend accept " + UUIDFetcher.getNameByUUID(uuid));
                    }
                } else {
                    player.sendMessage("§cDu hast keine offenen Freundesanfragen!");
                }
            } else if (args[0].equalsIgnoreCase("denyall")) {
                if (FriendManager.getRequests(player.getUniqueId()).size() >= 1) {
                    for (UUID uuid : FriendManager.getRequests(player.getUniqueId())) {
                        NeoBungeeSystem.plugin.getProxy().getPluginManager().dispatchCommand(player, "friend deny " + UUIDFetcher.getNameByUUID(uuid));
                    }
                } else {
                    player.sendMessage("§cDu hast keine offenen Freundesanfragen!");
                }
            } else if (args[0].equalsIgnoreCase("clear")) {
                if (FriendManager.getFriends(player.getUniqueId()).size() >= 1) {
                    for (UUID uuid : FriendManager.getFriends(player.getUniqueId())) {
                        NeoBungeeSystem.plugin.getProxy().getPluginManager().dispatchCommand(player, "friend remove " + UUIDFetcher.getNameByUUID(uuid));
                    }
                } else {
                    player.sendMessage("§cDu hast keine Freunde in deiner Freundesliste!");
                }
            } else {
                this.sendHelp(player);
            }
        } else if (args.length == 2) {

            ProxiedPlayer target = NeoBungeeSystem.plugin.getProxy().getPlayer(args[1]);

            if (args[0].equalsIgnoreCase("add")) {

                if (target != null) {
                    if (target != player) {
                        if (!FriendManager.isFriend(player.getUniqueId(), target.getUniqueId())) {
                            if (!FriendManager.isRequestOpen(player.getUniqueId(), target.getUniqueId())) {
                                if (FriendSettingsManager.isGettingRequests(target.getUniqueId())) {
                                    FriendManager.addRequest(player.getUniqueId(), target.getUniqueId());
                                    player.sendMessage("§aDu hast dem Spieler §e" + target.getName() + " §aeine §eFreundesanfrage §ageschickt");

                                    TextComponent message = new TextComponent();
                                    String acceptMSG = "§8● §aAkzeptieren";
                                    String denyMSG = "§8● §cAblehnen";
                                    TextComponent accept = new TextComponent(acceptMSG);
                                    accept.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/friend accept " + player.getName()));
                                    message.addExtra(accept);
                                    message.addExtra(new TextComponent(" §8│ "));
                                    TextComponent deny = new TextComponent(denyMSG);
                                    deny.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/friend deny " + player.getName()));
                                    message.addExtra(deny);
                                    target.sendMessage("§e" + player + " §7hat dir eine §eFreundesanfrage §7gesendet!");

                                    target.sendMessage(message);
                                } else {
                                    player.sendMessage("§e" + target.getName() + " §cmöchte keine §eFreundesanfragen §cbekommen!");
                                }
                            } else {
                                player.sendMessage("§cDu hast §e" + target.getName() + " §cbereits eine §eFreundesanfrage §cgeschickt!");
                            }
                        } else {
                            player.sendMessage("§cDu bist bereits mit §e" + target.getName() + " §cbefreundet!");
                        }
                    } else {
                        player.sendMessage(Data.DONT_TARGET_YOURSELF);
                    }
                } else {
                    player.sendMessage(Data.NOT_ONLINE);
                }

            } else if (args[0].equalsIgnoreCase("accept")) {

                UUID uuid;
                if (target != null) {
                    uuid = target.getUniqueId();
                } else {
                    uuid = UUIDFetcher.getUniqueID(args[1]);
                }

                if (FriendManager.isRequestOpen(uuid, player.getUniqueId())) {
                    FriendManager.removeRequest(uuid, player.getUniqueId());
                    FriendManager.addFriend(player.getUniqueId(), uuid);
                    FriendManager.addFriend(uuid, player.getUniqueId());
                    player.sendMessage("§aDu bist jetzt mit §e" + target.getName() + " §abefreundet!");
                        target.sendMessage("§aDu bist jetzt mit §e" + player.getName() + " §abefreundet!");
                } else {
                    player.sendMessage("§cDu hast keine Freundesanfrage von §e" + target.getName() + " §cerhalten!");
                }

            } else if (args[0].equalsIgnoreCase("deny")) {
                UUID uuid;
                if (target != null) {
                    uuid = target.getUniqueId();
                } else {
                    uuid = UUIDFetcher.getUniqueID(args[1]);
                }
                if (FriendManager.isRequestOpen(uuid, player.getUniqueId())) {
                    FriendManager.removeRequest(uuid, player.getUniqueId());
                    player.sendMessage("§7Du hast die §eFreundesanfrage §cabgelehnt!");
                    if (target != null) {
                        target.sendMessage("§e" + player.getName() + " §7hat deine §eFreundesanfrage §cabgelehnt!");
                    }
                } else {
                    player.sendMessage("§cDu hast keine Freundesanfrage von §e" + target.getName() + " §cerhalten!");
                }
            } else if (args[0].equalsIgnoreCase("remove")) {
                UUID uuid = UUIDFetcher.getUniqueID(args[1]);
                if (FriendManager.isFriend(player.getUniqueId(), uuid)) {
                    FriendManager.removeFriend(player.getUniqueId(), uuid);
                    FriendManager.removeFriend(uuid, player.getUniqueId());
                    player.sendMessage("§7Du bist nun nicht mehr mit §e" + args[1] + " §ebefreundet!");
                    if (target != null) {
                        target.sendMessage("§e" + player.getName() + " §7hat die §eFreundschaft §7mit dir §cbeendet!");
                    }
                } else {
                    player.sendMessage("§cDu bist nicht mit §e" + args[1] + " §cbefreundet!");
                }
            } else if (args[0].equalsIgnoreCase("jump")) {
                UUID uuid = UUIDFetcher.getUniqueID(args[1]);
                if (target != null) {
                    if (FriendManager.isFriend(player.getUniqueId(), target.getUniqueId())) {
                        if (FriendSettingsManager.isAllowingServerJumping(uuid)) {
                            player.connect(target.getServer().getInfo());
                            player.sendMessage("§aDu wirst nun auf den Server von §e" + target.getName() + " §ateleportiert!");
                        } else {
                            player.sendMessage("§cDu darfst §e" + target.getName() + " §cnicht §ehinterher springen!");
                        }
                    } else {
                        player.sendMessage("§cDu bist nicht mit §e" + args[1] + " §ebefreundet!");
                    }
                } else {
                    player.sendMessage(Data.NOT_ONLINE);
                }
            } else {
                sendHelp(player);
            }

        } else {
            sendHelp(player);
        }

    }

    private static void sendHelp(ProxiedPlayer p) {
        p.sendMessage("§6Freundschaftsverwaltung");
        p.sendMessage("§e/friend add <Spieler> §8» §7Fügt einen neuen Freund hinzu");
        p.sendMessage("§e/friend list §8» §7Zeigt eine Liste aller Freunde");
        p.sendMessage("§e/friend remove <Spieler> §8» §7Entfernt einen Freund");
        p.sendMessage("§e/friend jump <Spieler> §8» §7Springt auf den Server deines Freunds");
        p.sendMessage("§e/msg <Spieler> <Nachricht ...> §8» §7Versendet eine Nachricht");
        p.sendMessage("§e/friend clear §8» §7Leert die Freundesliste");
        p.sendMessage("§e/friend requests §8» §7Zeigt alle Freundschaftsanfragen anf");
        p.sendMessage("§e/friend accept <Spieler> §8» §7Nimmt eine offene Freundschatsanfrage an");
        p.sendMessage("§e/friend deny <Spieler> §8» §7Lehnt eine offene Freundschaftsanfrage ab");
        p.sendMessage("§e/friend denyall §8» §7Lehnt alle offenen Freundschaftsanfragen ab");
        p.sendMessage("§e/friend acceptall §8» §7Nimmt alle offenen Freundschaftsanfragen an");
    }
}