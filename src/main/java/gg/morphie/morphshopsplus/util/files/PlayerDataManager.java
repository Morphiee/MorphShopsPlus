package gg.morphie.morphshopsplus.util.files;

import gg.morphie.morphshopsplus.MorphShopsPlus;
import gg.morphie.morphshopsplus.util.Color;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class PlayerDataManager {
    private MorphShopsPlus plugin;
    public PlayerDataManager(MorphShopsPlus plugin) {
        this.plugin = plugin;
    }

    public void setBoolean(UUID uuid, String string, Boolean b) {
        File file = getPlayerFile(uuid);
        FileConfiguration fc = YamlConfiguration.loadConfiguration(file);
        fc.set(string, Boolean.valueOf(b));
        try
        {
            fc.save(file);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void setString(UUID uuid, String string, String string2) {
        File file = getPlayerFile(uuid);
        FileConfiguration fc = YamlConfiguration.loadConfiguration(file);
        fc.set(string, String.valueOf(string2));
        try
        {
            fc.save(file);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void removeShop(UUID uuid) {
        File file = getPlayerFile(uuid);
        FileConfiguration fc = YamlConfiguration.loadConfiguration(file);
        fc.set("PlayerData.location", null);
        fc.set("PlayerData.Shop", false);
        try
        {
            fc.save(file);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void addStringList(UUID uuid, String string, String lore) {
        File file = getPlayerFile(uuid);
        FileConfiguration fc = YamlConfiguration.loadConfiguration(file);
        List<String> list2 = getStringList(uuid, string);
        list2.add(lore);
        fc.set(string, list2);
        try
        {
            fc.save(file);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void setStringList(Player player, UUID uuid, String string, List<String> lore) {
        File file = getPlayerFile(player.getUniqueId());
        FileConfiguration fc = YamlConfiguration.loadConfiguration(file);
        fc.set(string, lore);
        try
        {
            fc.save(file);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void setDouble(UUID uuid, String string, Double i) {
        File file = getPlayerFile(uuid);
        FileConfiguration fc = YamlConfiguration.loadConfiguration(file);
        fc.set(string, Double.valueOf(i));
        try
        {
            fc.save(file);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void setFloat(UUID uuid, String string, Float i) {
        File file = getPlayerFile(uuid);
        FileConfiguration fc = YamlConfiguration.loadConfiguration(file);
        fc.set(string, Float.valueOf(i));
        try
        {
            fc.save(file);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public boolean getBoolean(UUID uuid, String string) {
        File file = getPlayerFile(uuid);
        FileConfiguration fc = YamlConfiguration.loadConfiguration(file);
        return fc.getBoolean(string);
    }

    public String getString(UUID uuid, String string) {
        File file = getPlayerFile(uuid);
        FileConfiguration fc = YamlConfiguration.loadConfiguration(file);
        return fc.getString(string);
    }

    public List<String> getStringList(UUID uuid, String string) {
        File file = getPlayerFile(uuid);
        FileConfiguration fc = YamlConfiguration.loadConfiguration(file);
        return fc.getStringList(string);
    }

    public double getDouble(UUID uuid, String string) {
        File file = getPlayerFile(uuid);
        FileConfiguration fc = YamlConfiguration.loadConfiguration(file);
        return fc.getDouble(string);
    }

    public float getFloat(UUID uuid, String string) {
        File file = getPlayerFile(uuid);
        FileConfiguration fc = YamlConfiguration.loadConfiguration(file);
        return fc.getInt(string);
    }

    public File getPlayerFile(UUID uuid) {
        File playerFile = new File(this.plugin.getDataFolder() + File.separator + "PlayerData", uuid + ".yml");
        FileConfiguration playerCFG = YamlConfiguration.loadConfiguration(playerFile);
        if (!playerFile.exists()) {
            try {
                playerCFG.save(playerFile);
            }
            catch (IOException e1) {
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
                    plugin.getServer().getConsoleSender().sendMessage(Color.addColor(plugin.getMessage("ServerStart.CleanerCleared").replace("%FILES_DELETED%", String.valueOf(filesDeleted))));
                } else {
                    plugin.getServer().getConsoleSender().sendMessage(Color.addColor(plugin.getMessage("ServerStart.CleanerNoneCleared")));
                }
            }
        }
    }
}
