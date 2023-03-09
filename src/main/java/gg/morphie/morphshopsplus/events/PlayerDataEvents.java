package gg.morphie.morphshopsplus.events;

import gg.morphie.morphshopsplus.MorphShopsPlus;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.util.logging.Level;

public class PlayerDataEvents implements Listener {

    private MorphShopsPlus plugin;

    public PlayerDataEvents(MorphShopsPlus plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();

        new BukkitRunnable() {
            public void run() {
                File file = getData(uuid);
                FileConfiguration pd = YamlConfiguration.loadConfiguration(file);
                if (!pd.contains("Shop")) {
                    pd.set("Shop", null);
                    try {
                        pd.save(file);
                    } catch (IOException e) {
                        Bukkit.getServer().getLogger().log(Level.SEVERE, "Could not save " + uuid + "'s player file!");
                        e.printStackTrace();
                    }
                }
            }
        }.runTaskLater(this.plugin, 60L);
    }


    public File getData(UUID uuid) {
        File data = new File(this.plugin.getDataFolder() + File.separator + "PlayerData", uuid + ".yml");
        FileConfiguration dataFile = YamlConfiguration.loadConfiguration(data);
        if (!data.exists()) {
            try {
                dataFile.save(data);
            }
            catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return data;
    }
}
