package gg.morphie.morphshopsplus;

import gg.morphie.morphshopsplus.commands.CommandsManager;
import gg.morphie.morphshopsplus.events.PlayerDataEvents;
import gg.morphie.morphshopsplus.util.Color;
import gg.morphie.morphshopsplus.util.files.PlayerDataManager;
import gg.morphie.morphshopsplus.util.files.messages.BuildManager;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class MorphShopsPlus extends JavaPlugin implements Listener {

    public String Version;
    public BuildManager messagescfg;
    private static Economy econ = null;
    private static Permission perms = null;
    private static Chat chat = null;

    public HashMap<Player, Boolean> setshop = new HashMap<Player, Boolean>();

    PluginManager pm = Bukkit.getServer().getPluginManager();

    public void onEnable() {
        Objects.requireNonNull(getCommand("ps")).setExecutor(new CommandsManager(this));
        pm.registerEvents(new PlayerDataEvents(this), this);
        loadConfigManager();
        Version = this.getDescription().getVersion();
        getServer().getConsoleSender().sendMessage(Color.addColor(getMessage("ServerStart.Header")));
        getServer().getConsoleSender().sendMessage(Color.addColor(getMessage("ServerStart.TitleVersion").replace("%VERSION%", this.Version)));
        getServer().getConsoleSender().sendMessage(Color.addColor(getMessage("ServerStart.Author")));
        createConfig();
        if (!setupEconomy()) {
            getServer().getConsoleSender().sendMessage(Color.addColor("&cPlugin disabled due to no Vault dependency found!"));
            getServer().getConsoleSender().sendMessage(Color.addColor(" "));
            getServer().getConsoleSender().sendMessage(Color.addColor(getMessage("ServerStart.Footer")));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        setupPermissions();
        getServer().getConsoleSender().sendMessage(Color.addColor("&3Plugin Status&8: &aEnabled!"));
        if (this.getConfig().getBoolean("Settings.AutoDeletePlayerFiles.Enabled") == true) {
            getServer().getConsoleSender().sendMessage(Color.addColor(" "));
            getServer().getConsoleSender().sendMessage(Color.addColor(getMessage("ServerStart.CleanerCheck")));
            new PlayerDataManager(this).cleanPlayerData();
        }
        getServer().getConsoleSender().sendMessage(Color.addColor(" "));
        getServer().getConsoleSender().sendMessage(Color.addColor(getMessage("ServerStart.Footer")));

    }

    public void onDisable() {
        getServer().getConsoleSender().sendMessage(Color.addColor(getMessage("ServerStart.Header")));
        getServer().getConsoleSender().sendMessage(Color.addColor(getMessage("ServerStart.TitleVersion").replace("%VERSION%", this.Version)));
        getServer().getConsoleSender().sendMessage(Color.addColor(getMessage("ServerStart.Author")));
        getServer().getConsoleSender().sendMessage(Color.addColor("&3Plugin Status&8: &cDisabled!"));
        getServer().getConsoleSender().sendMessage(Color.addColor(" "));
        getServer().getConsoleSender().sendMessage(Color.addColor(getMessage("ServerStart.Footer")));

    }

    private void createConfig() {
        try {
            if (!getDataFolder().exists()) {
                getDataFolder().mkdirs();
            }
            File file = new File(getDataFolder(), "config.yml");
            if (!file.exists()) {
                getServer().getConsoleSender().sendMessage(Color.addColor(getMessage("ServerStart.GenConfig")));
                getConfig().options().copyDefaults(true);
                saveDefaultConfig();
            } else {
                getServer().getConsoleSender().sendMessage(Color.addColor(getMessage("ServerStart.Config")));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
        return perms != null;
    }

    public Economy getEconomy() {
        return econ;
    }

    public Permission getPermissions() {
        return perms;
    }

    public Chat getChat() {
        return chat;
    }

    public void loadConfigManager() {
        this.messagescfg = new BuildManager(this);
        this.messagescfg.setup();
    }

    public String getMessage(String string) {
        String gotString = this.messagescfg.messagesCFG.getString(string);
        if (gotString != null) return gotString;
        return "Null message";
    }

    public List<String> getMessageList(String string) {
        return this.messagescfg.messagesCFG.getStringList(string);
    }

    public String getVersion() {
        return this.Version;
    }
}
