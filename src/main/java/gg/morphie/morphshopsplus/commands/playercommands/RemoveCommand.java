package gg.morphie.morphshopsplus.commands.playercommands;

import gg.morphie.morphshopsplus.MorphShopsPlus;
import gg.morphie.morphshopsplus.util.Color;
import gg.morphie.morphshopsplus.util.files.PlayerDataManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

public class RemoveCommand {
    private MorphShopsPlus plugin;
    public RemoveCommand(MorphShopsPlus plugin) {
        this.plugin = plugin;
    }

    public void runRemove(Player player) {
        if (player.hasPermission("mshops.remove")) {
            if (new PlayerDataManager(plugin).getBoolean(player.getUniqueId(), "PlayerData.Shop")) {
                if (plugin.getConfig().getBoolean("Settings.CommandConfirmation.RemoveShop")) {
                    CheckConfirmation(player);
                } else {
                    new PlayerDataManager(plugin).removeShop(player.getUniqueId());
                }
            } else {
                player.sendMessage(Color.addColor(this.plugin.getMessage("ErrorPrefix") + this.plugin.getMessage("NoSetShop")));
            }
        }
    }

    private void CheckConfirmation(Player player) {
        if (plugin.setshop.containsKey(player)) {
            plugin.setshop.remove(player);
            player.sendMessage(Color.addColor(plugin.getMessage("Prefix") + plugin.getMessage("RemoveShop.Message")));
            new PlayerDataManager(plugin).removeShop(player.getUniqueId());
        } else {
            player.sendMessage(Color.addColor(plugin.getMessage("ErrorPrefix") + plugin.getMessage("RemoveShop.ConfirmMessage")));
            plugin.setshop.put(player, true);
            BukkitScheduler schedule = Bukkit.getScheduler();
            schedule.scheduleSyncDelayedTask(plugin, new Runnable() {
                public void run() {
                    if (plugin.setshop.containsKey(player)) {
                        plugin.setshop.remove(player);
                        player.sendMessage(Color.addColor(plugin.getMessage("ErrorPrefix") + plugin.getMessage("RemoveShop.ConfirmCanceledMessage")));
                    }
                }
            }, 1000L);
        }
    }
}
