package dev.denismasterherobrine.twitchwarriors.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandEnablePlugin implements CommandExecutor {

    public boolean isPluginEnabled;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            isPluginEnabled = true;
        }

        return true;
    }
}
