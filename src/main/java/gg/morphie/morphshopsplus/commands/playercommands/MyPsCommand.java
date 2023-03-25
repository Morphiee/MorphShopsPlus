package gg.morphie.morphshopsplus.commands.playercommands;

import gg.morphie.morphshopsplus.MorphShopsPlus;
import gg.morphie.morphshopsplus.util.Color;
import gg.morphie.morphshopsplus.util.LocationUtils;
import gg.morphie.morphshopsplus.util.files.PlayerDataManager;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class MyPsCommand {

    private MorphShopsPlus plugin;

    public MyPsCommand(MorphShopsPlus plugin) {
        this.plugin = plugin;
    }

    public void runMyPs (Player player) {
        if (player.hasPermission("mshops.myps")) {
            if (new PlayerDataManager(plugin).getBoolean(player.getUniqueId(), "PlayerData.Shop")) {
                Location loc = new LocationUtils(plugin).getPlayerShopLoc(player);
                player.teleport(loc);
                player.sendMessage(Color.addColor(this.plugin.getMessage("Prefix") + this.plugin.getMessage("ToOwnShopMessage")));
            } else {
                player.sendMessage(Color.addColor(this.plugin.getMessage("ErrorPrefix") + this.plugin.getMessage("NoSetShop")));
            }
        } else {
            player.sendMessage(Color.addColor(this.plugin.getMessage("ErrorPrefix") + this.plugin.getMessage("NoPermission")));
        }
    }
}
