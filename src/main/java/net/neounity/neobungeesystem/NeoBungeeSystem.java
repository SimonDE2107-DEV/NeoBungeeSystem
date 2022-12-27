package net.neounity.neobungeesystem;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;
import net.neounity.neobungeesystem.command.*;
import net.neounity.neobungeesystem.listener.FriendManager;
import net.neounity.neobungeesystem.listener.PartyManager;
import net.neounity.neobungeesystem.listener.PlayerInfo;
import net.neounity.neobungeesystem.listener.cmdspy;
import net.neounity.neobungeesystem.util.Data;
import net.neounity.neobungeesystem.util.MySQL;
import net.neounity.neobungeesystem.util.OnlineTime;

public final class NeoBungeeSystem extends Plugin {


    public static NeoBungeeSystem plugin;
    public static MySQL mySQL;


    @Override
    public void onEnable() {
        plugin = this;
        mySQL = new MySQL("127.0.0.1", "3306", Data.sqlDatabase, Data.sqlUser, Data.sqlPassword);

        new OnlineTime().start();
        this.registerListener(new cmdspy(), new FriendManager(), new PartyManager(), new PlayerInfo());
        this.registerCommand();
    }

    @Override
    public void onDisable() {
        plugin = null;
    }

    void registerCommand() {
        PluginManager pluginManager = ProxyServer.getInstance().getPluginManager();
        pluginManager.registerCommand(this, new adminchatCommand("adminchat"));
        pluginManager.registerCommand(this, new broadcastCommand("broadcast"));
        pluginManager.registerCommand(this, new cmdspyCommand("cmdspy"));
        pluginManager.registerCommand(this, new discordCommand("discord"));
        pluginManager.registerCommand(this, new friendCommand("friend"));
        pluginManager.registerCommand(this, new joinmeCommand("joinme"));
        pluginManager.registerCommand(this, new msgCommand("msg"));
        pluginManager.registerCommand(this, new partyCommand("party"));
        pluginManager.registerCommand(this, new pingCommand("ping"));
        pluginManager.registerCommand(this, new playtimeCommand("playtime"));
        pluginManager.registerCommand(this, new reportCommand("report"));
        pluginManager.registerCommand(this, new teamchatCommand("teamchat"));
    }

    void registerListener(Listener... listeners) {
        for (Listener listener : listeners) {
            ProxyServer.getInstance().getPluginManager().registerListener(this, listener);
        }
    }
}
