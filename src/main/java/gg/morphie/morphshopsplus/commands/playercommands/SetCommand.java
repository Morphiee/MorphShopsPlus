package gg.morphie.morphshopsplus.commands.playercommands;

import gg.morphie.morphshopsplus.MorphShopsPlus;
import gg.morphie.morphshopsplus.util.Color;
import gg.morphie.morphshopsplus.util.LocationUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.HashMap;

public class SetCommand {
    private MorphShopsPlus plugin;

    public SetCommand(MorphShopsPlus plugin) {
        this.plugin = plugin;
    }

    public HashMap<Player, Boolean> setshop = new HashMap<Player, Boolean>();

    public void runSetShop(Player player) {
        if (player.hasPermission("mshops.setshop")) {
            double bal = this.plugin.getEconomy().getBalance(player);
            Location loc = player.getLocation();
            if (new LocationUtils(plugin).checkLoc(loc)) {
                if (plugin.getConfig().getBoolean("Settings.SetShopPrice.Enabled")) {
                    if (plugin.getConfig().getBoolean("Settings.CommandConfirmation.SetShop")) {
                        if (!(setshop.containsKey(player))) {
                            player.sendMessage(Color.addColor(plugin.getMessage("ErrorPrefix") + plugin.getMessage("SetShop.ConfirmMessage")));
                            setshop.put(player, true);
                            BukkitScheduler schedule = Bukkit.getScheduler();
                            schedule.scheduleSyncDelayedTask(plugin, new Runnable() {
                                public void run() {
                                    setshop.remove(player);
                                    player.sendMessage(Color.addColor(plugin.getMessage("ErrorPrefix") + plugin.getMessage("SetShop.ConfirmCanceledMessage")));
                                }
                            }, 1000L);
                        } else {
                            setshop.remove(player);
                            player.sendMessage(Color.addColor(plugin.getMessage("Prefix") + plugin.getMessage("SetShop.Message")));
                            new LocationUtils(plugin).setPlayerShopLoc(player, loc);
                        }
                    }
                }
            }
        }
    }
}
