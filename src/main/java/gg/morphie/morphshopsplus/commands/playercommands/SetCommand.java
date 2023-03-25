package gg.morphie.morphshopsplus.commands.playercommands;

import gg.morphie.morphshopsplus.MorphShopsPlus;
import gg.morphie.morphshopsplus.util.Color;
import gg.morphie.morphshopsplus.util.LocationUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.HashMap;
import java.util.List;

public class SetCommand {
    private MorphShopsPlus plugin;

    public SetCommand(MorphShopsPlus plugin) {
        this.plugin = plugin;
    }

    public void runSetShop(Player player) {
        if (player.hasPermission("mshops.setshop")) {
            double bal = this.plugin.getEconomy().getBalance(player);
            Location loc = player.getLocation();
            List<String> allowedWorlds = plugin.getConfig().getStringList("Settings.AllowedWorlds");
            if (new LocationUtils(plugin).checkLoc(loc)) {
                if (allowedWorlds.contains(loc.getWorld().getName())) {
                    if (plugin.getConfig().getBoolean("Settings.CommandConfirmation.SetShop")) {
                        if (!plugin.getConfig().getBoolean("Settings.SetShopPrice.Enabled")) {
                            CheckConfirmation(player, loc);
                        } else {
                            if (bal >= plugin.getConfig().getDouble("Settings.SetShopPrice.Price")) {
                                CheckConfirmation(player, loc);
                            } else {
                                player.sendMessage(Color.addColor(plugin.getMessage("ErrorPrefix") + plugin.getMessage("NoMoney").replaceAll("COST", plugin.getConfig().getString("Settings.SetShopPrice.Price"))));
                            }
                        }
                    } else {
                        if (!plugin.getConfig().getBoolean("Settings.SetShopPrice.Enabled")) {
                            player.sendMessage(Color.addColor(plugin.getMessage("Prefix") + plugin.getMessage("SetShop.Message")));
                            new LocationUtils(plugin).setPlayerShopLoc(player, loc);
                        } else {
                            if (bal >= plugin.getConfig().getDouble("Settings.SetShopPrice.Price")) {
                                plugin.getEconomy().withdrawPlayer(player, plugin.getConfig().getDouble("Settings.SetShopPrice.Price"));
                                player.sendMessage(Color.addColor(plugin.getMessage("Prefix") + plugin.getMessage("SetShop.Message")));
                                new LocationUtils(plugin).setPlayerShopLoc(player, loc);
                            } else {
                                player.sendMessage(Color.addColor(plugin.getMessage("ErrorPrefix") + plugin.getMessage("NoMoney").replaceAll("COST", plugin.getConfig().getString("Settings.SetShopPrice.Price"))));
                            }
                        }
                    }
                } else {
                    player.sendMessage(Color.addColor(plugin.getMessage("ErrorPrefix") + plugin.getMessage("NotAllowedWorld")));
                }
            } else {
                player.sendMessage(Color.addColor(plugin.getMessage("ErrorPrefix") + plugin.getMessage("InvalidLocation")));
            }
        } else {
            player.sendMessage(Color.addColor(plugin.getMessage("ErrorPrefix") + plugin.getMessage("NoPermission")));
        }
    }

    private void CheckConfirmation(Player player, Location loc) {
        if (plugin.setshop.containsKey(player)) {
            plugin.setshop.remove(player);
            player.sendMessage(Color.addColor(plugin.getMessage("Prefix") + plugin.getMessage("SetShop.Message")));
            new LocationUtils(plugin).setPlayerShopLoc(player, loc);
        } else {
            player.sendMessage(Color.addColor(plugin.getMessage("ErrorPrefix") + plugin.getMessage("SetShop.ConfirmMessage")));
            putPlayer(player);
        }
    }

    private void putPlayer(Player player) {
        plugin.setshop.put(player, true);
        BukkitScheduler schedule = Bukkit.getScheduler();
        schedule.scheduleSyncDelayedTask(plugin, new Runnable() {
            public void run() {
                if (plugin.setshop.containsKey(player)) {
                    plugin.setshop.remove(player);
                    player.sendMessage(Color.addColor(plugin.getMessage("ErrorPrefix") + plugin.getMessage("SetShop.ConfirmCanceledMessage")));
                }
            }
        }, 1000L);
    }
}
