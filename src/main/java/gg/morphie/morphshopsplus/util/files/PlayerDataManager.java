package gg.morphie.morphshopsplus.util.files;

import gg.morphie.morphshopsplus.MorphShopsPlus;
import gg.morphie.morphshopsplus.util.Color;
import gg.morphie.morphshopsplus.util.files.messages.BuildManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

public class PlayerDataManager {
    private MorphShopsPlus plugin;
    public PlayerDataManager(MorphShopsPlus plugin) {
        this.plugin = plugin;
    }

    public void setString(UUID uuid, String string, String string2) {
        File file = getPlayerFile(uuid);
        FileConfiguration fc = YamlConfiguration.loadConfiguration(file);
        fc.set(string, String.valueOf(string2));
        try {
            fc.save(file);
            Bukkit.getServer().getConsoleSender().sendMessage(string2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getString(UUID uuid, String string) {
        File file = getPlayerFile(uuid);
        FileConfiguration fc = YamlConfiguration.loadConfiguration(file);
        return fc.getString(string);
    }

    public File getPlayerFile(UUID uuid) {
        File playerFile = new File(this.plugin.getDataFolder() + File.separator + "PlayerData", uuid + ".yml");
        FileConfiguration playerCFG = YamlConfiguration.loadConfiguration(playerFile);
        if (!playerFile.exists()) {
            try {
                playerCFG.save(playerFile);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return playerFile;
    }

    public void cleanPlayerData() {
        if (plugin.getConfig().getBoolean("Settings.AutoDeletePlayerFiles.Enabled")) {
            File data = new File(plugin.getDataFolder() + File.separator + "PlayerData");
            File path = new File(data.getPath());
            File dir = new File(path.toString());
            File[] directoryListing = dir.listFiles();

            if (directoryListing != null) {
                int filesDeleted = 0;
                for (File child : directoryListing) {
                    String[] childUUID = child.getName().split("[.]");
                    UUID uuid1 = UUID.fromString(childUUID[0]);
                    OfflinePlayer player1 = Bukkit.getOfflinePlayer(uuid1);
                    Date date = new Date(player1.getLastPlayed());
                    Date systemDate = new Date(System.currentTimeMillis());
                    long startTime = date.getTime();
                    long endTime = systemDate.getTime();
                    long diffTime = endTime - startTime;
                    long diffDays = diffTime / (1000 * 60 * 60 * 24);
                    if (diffDays >= plugin.getConfig().getInt("Settings.AutoDeletePlayerFiles.DaysTillDeletion")) {
                        child.delete();
                        filesDeleted++;
                    }
                }
                if (filesDeleted != 0) {
                    plugin.getServer().getConsoleSender().sendMessage(Color.addColor(new BuildManager(plugin).messagesCFG.getString("ServerStart.CleanerCleared").replace("%FILES_DELETED%", String.valueOf(filesDeleted))));
                } else {
                    plugin.getServer().getConsoleSender().sendMessage(Color.addColor(new BuildManager(plugin).messagesCFG.getString("ServerStart.CleanerNoneCleared")));
                }
            }
        }
    }
}
