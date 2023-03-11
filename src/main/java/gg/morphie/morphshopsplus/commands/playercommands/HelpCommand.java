package gg.morphie.morphshopsplus.commands.playercommands;

import gg.morphie.morphshopsplus.MorphShopsPlus;
import gg.morphie.morphshopsplus.util.Color;
import gg.morphie.morphshopsplus.util.files.messages.GetMessages;
import org.bukkit.entity.Player;

public class HelpCommand {
    private MorphShopsPlus plugin;

    public HelpCommand(MorphShopsPlus plugin) {
        this.plugin = plugin;
    }

    public void runHelp(Player player) {

        if (player.hasPermission("mshops.ps")) {
            player.sendMessage("");
            player.sendMessage(Color.addColor(new GetMessages(plugin).getMessage("Commands.Header")));
            player.sendMessage("");
            player.sendMessage(Color.addColor(new GetMessages(plugin).getMessage("Commands.Title").replace("VERSION", plugin.getVersion())));
            player.sendMessage(Color.addColor(new GetMessages(plugin).getMessage("Commands.Alias")));
            player.sendMessage("");
            player.sendMessage(Color.addColor(new GetMessages(plugin).getMessage("Commands.PsSet")));
            player.sendMessage(Color.addColor(new GetMessages(plugin).getMessage("Commands.PsMenu")));
            player.sendMessage(Color.addColor(new GetMessages(plugin).getMessage("Commands.PsTp")));
            player.sendMessage(Color.addColor(new GetMessages(plugin).getMessage("Commands.PsPlayer")));
            player.sendMessage(Color.addColor(new GetMessages(plugin).getMessage("Commands.PsLock")));
            player.sendMessage(Color.addColor(new GetMessages(plugin).getMessage("Commands.PsRemove")));
            player.sendMessage(Color.addColor(new GetMessages(plugin).getMessage("Commands.PsAdvertise")));
            player.sendMessage(Color.addColor(new GetMessages(plugin).getMessage("Commands.PsLore")));
            player.sendMessage(Color.addColor(new GetMessages(plugin).getMessage("Commands.PsLoreDelete")));
            player.sendMessage("");
            if (player.hasPermission("mshops.admin") || player.hasPermission("mshops.reload")) {
                player.sendMessage(Color.addColor(new GetMessages(plugin).getMessage("Commands.PsReset")));
                player.sendMessage(Color.addColor(new GetMessages(plugin).getMessage("Commands.PsReload")));
            }
            player.sendMessage("");
            player.sendMessage(Color.addColor(new GetMessages(plugin).getMessage("Commands.Footer")));
            player.sendMessage("");
        } else {
            player.sendMessage(Color.addColor(new GetMessages(plugin).getMessage("ErrorPrefix") + new GetMessages(plugin).getMessage("NoPermsMessage")));
        }
    }
}