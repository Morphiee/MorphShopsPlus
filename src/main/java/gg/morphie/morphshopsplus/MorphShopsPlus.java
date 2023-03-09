package gg.morphie.morphshopsplus;

import gg.morphie.morphshopsplus.util.Color;
import gg.morphie.morphshopsplus.util.files.PlayerDataManager;
import gg.morphie.morphshopsplus.util.files.messages.BuildManager;
import gg.morphie.morphshopsplus.util.files.messages.GetMessages;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class MorphShopsPlus extends JavaPlugin {

    public String Version;

    public void onEnable() {
        new BuildManager(this).setup();
        Version = this.getDescription().getVersion();
        Bukkit.getConsoleSender().sendMessage(Color.addColor(new GetMessages(this).getMessage("ServerStart.Header")));
        Bukkit.getConsoleSender().sendMessage(Color.addColor(new GetMessages(this).getMessage("ServerStart.TitleVersion").replace("%VERSION%", this.Version)));
        Bukkit.getConsoleSender().sendMessage(Color.addColor(new GetMessages(this).getMessage("ServerStart.Author")));
        createConfig();
        if (this.getConfig().getBoolean("Settings.AutoDeletePlayerFiles.Enabled")) {
            getServer().getConsoleSender().sendMessage(" ");
            getServer().getConsoleSender().sendMessage(Color.addColor(new GetMessages(this).getMessage("ServerStart.CleanerCheck")));
            new PlayerDataManager(this).cleanPlayerData();
        }
        Bukkit.getConsoleSender().sendMessage(Color.addColor(new GetMessages(this).getMessage("ServerStart.Footer")));
    }

    public void onDisable() {

    }

    private void createConfig() {
        try {
            if (!getDataFolder().exists()) {
                getDataFolder().mkdirs();
            }
            File file = new File(getDataFolder(), "config.yml");
            if (!file.exists()) {
                getServer().getConsoleSender().sendMessage(Color.addColor(new GetMessages(this).getMessage("ServerStart.GenConfig")));
                getConfig().options().copyDefaults(true);
                saveDefaultConfig();
            } else {
                getServer().getConsoleSender().sendMessage(Color.addColor(new GetMessages(this).getMessage("ServerStart.Config")));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
