package gg.morphie.morphshopsplus.commands.playercommands;

import gg.morphie.morphshopsplus.MorphShopsPlus;
import gg.morphie.morphshopsplus.util.Color;
import org.bukkit.entity.Player;

public class HelpCommand {
    private MorphShopsPlus plugin;

    public HelpCommand(MorphShopsPlus plugin) {
        this.plugin = plugin;
    }

    public void runHelp(Player player) {

        if (player.hasPermission("mshops.ps")) {
            player.sendMessage("");
            player.sendMessage(Color.addColor(plugin.getMessage("Commands.Header")));
            player.sendMessage("");
            player.sendMessage(Color.addColor(plugin.getMessage("Commands.Title").replace("VERSION", plugin.getVersion())));
            player.sendMessage(Color.addColor(plugin.getMessage("Commands.Alias")));
            player.sendMessage("");
            player.sendMessage(Color.addColor(plugin.getMessage("Commands.PsSet")));
            player.sendMessage(Color.addColor(plugin.getMessage("Commands.PsMenu")));
            player.sendMessage(Color.addColor(plugin.getMessage("Commands.PsTp")));
            player.sendMessage(Color.addColor(plugin.getMessage("Commands.PsPlayer")));
            player.sendMessage(Color.addColor(plugin.getMessage("Commands.PsLock")));
            player.sendMessage(Color.addColor(plugin.getMessage("Commands.PsRemove")));
            player.sendMessage(Color.addColor(plugin.getMessage("Commands.PsAdvertise")));
            player.sendMessage(Color.addColor(plugin.getMessage("Commands.PsLore")));
            player.sendMessage(Color.addColor(plugin.getMessage("Commands.PsLoreDelete")));
            player.sendMessage("");
            if (player.hasPermission("mshops.admin") || player.hasPermission("mshops.reload")) {
                player.sendMessage(Color.addColor(plugin.getMessage("Commands.PsReset")));
                player.sendMessage(Color.addColor(plugin.getMessage("Commands.PsReload")));
            }
            player.sendMessage("");
            player.sendMessage(Color.addColor(plugin.getMessage("Commands.Footer")));
            player.sendMessage("");
        } else {
            player.sendMessage(Color.addColor(plugin.getMessage("ErrorPrefix") + plugin.getMessage("NoPermsMessage")));
        }
    }
}