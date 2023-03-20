package gg.morphie.morphshopsplus.commands;

import gg.morphie.morphshopsplus.MorphShopsPlus;
import gg.morphie.morphshopsplus.commands.playercommands.HelpCommand;
import gg.morphie.morphshopsplus.util.Color;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandsManager implements CommandExecutor {

    private MorphShopsPlus plugin;

    public CommandsManager(MorphShopsPlus plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("ps") || cmd.getName().equalsIgnoreCase("pshops") || cmd.getName().equalsIgnoreCase("ms") || cmd.getName().equalsIgnoreCase("msshops")) {
            if (args.length == 0) {
                // Player Only
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    new HelpCommand(plugin).runHelp(player);
                } else {
                    sender.sendMessage(Color.addColor(plugin.getMessage("ErrorPrefix") + " &cThis command can only be run by a player!"));
                }
                return true;
            } else if (args[0].equalsIgnoreCase("set")) {
                // Player Only
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    new HelpCommand(plugin).runHelp(player);
                } else {
                    sender.sendMessage(Color.addColor(plugin.getMessage("ErrorPrefix") + " &cThis command can only be run by a player!"));
                }
                return true;
            } else {
                sender.sendMessage(Color.addColor(plugin.getMessage("ErrorPrefix") + plugin.getMessage("InvalidArgsMessage")));
                return true;
            }
        }
        return false;
    }
}
