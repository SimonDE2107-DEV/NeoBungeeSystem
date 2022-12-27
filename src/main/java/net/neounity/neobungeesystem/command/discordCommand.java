package net.neounity.neobungeesystem.command;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import net.neounity.neobungeesystem.util.Data;

public class discordCommand extends Command {
    public discordCommand(String name) {
        super(name, "", "dc");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("§8» ");
            sender.sendMessage(Data.PREFIX + "§7Unser §eDiscord§7-§eServer §8➥ §b§ldiscord.NeoUnity.de");
            sender.sendMessage("§8» ");
        } else {
            sender.sendMessage(Data.USAGE + "/discord");
            sender.sendMessage(Data.ALIASES+"/dc");
        }
    }
}
