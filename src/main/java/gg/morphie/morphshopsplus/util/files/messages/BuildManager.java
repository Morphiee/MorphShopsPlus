package gg.morphie.morphshopsplus.util.files.messages;

import gg.morphie.morphshopsplus.MorphShopsPlus;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;

import java.io.File;
import java.io.IOException;

public class BuildManager implements Listener {
    private MorphShopsPlus plugin;
    public FileConfiguration messagesCFG;
    public File messagesFile;

    public BuildManager(MorphShopsPlus plugin) {
        this.plugin = plugin;
    }

    public void setup() {
        if (!this.plugin.getDataFolder().exists()) {
            this.plugin.getDataFolder().mkdir();
        }
        this.messagesFile = new File(this.plugin.getDataFolder(), "messages.yml");
        if (!this.messagesFile.exists()) {
            try {
                this.messagesFile.createNewFile();

                this.messagesCFG = YamlConfiguration.loadConfiguration(this.messagesFile);

                this.addDefaults(this.messagesCFG);

                this.messagesCFG.options().copyDefaults(true);
                saveMessages();
            } catch (IOException e) {
                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Could not create the messages.yml file");
            }
        }
        this.messagesCFG = YamlConfiguration.loadConfiguration(this.messagesFile);

        this.addDefaults(this.messagesCFG);
    }

    public void saveMessages() {
        try {
            this.messagesCFG.save(this.messagesFile);
        } catch (IOException e) {
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Could not save the messages.yml file");
        }
    }

    public void reloadMessages() {
        this.messagesCFG = YamlConfiguration.loadConfiguration(this.messagesFile);
        this.addDefaults(this.messagesCFG);
    }

    private void addDefaults(FileConfiguration cfg) {
        cfg.addDefault("ServerStart.Header", "&7‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾");
        cfg.addDefault("ServerStart.TitleVersion", "&3MorphShopsPlus&7: &a%VERSION%");
        cfg.addDefault("ServerStart.Author", "&aAuthor&7: &2Morphie#1738 &7(Discord)");
        cfg.addDefault("ServerStart.Config", "&aConfig&7: &2Loaded");
        cfg.addDefault("ServerStart.GenConfig", "&aConfig&7: &2Generating");
        cfg.addDefault("ServerStart.CleanerCheck", "&aPlayerData Cleaner&8: &2Checking for old files.");
        cfg.addDefault("ServerStart.CleanerNoneCleared", "&aPlayerData Cleaner&8: &2No files cleared.");
        cfg.addDefault("ServerStart.CleanerCleared", "&aPlayerData Cleaner&8: &2Successfully cleared %FILES_DELETED% files.");
        cfg.addDefault("ServerStart.Footer", "&7‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾");

        cfg.addDefault("Commands.Header", "&7‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾");
        cfg.addDefault("Commands.Title", "&3&lMorphPlayerShops &8» &aVERSION");
        cfg.addDefault("Commands.Alias", "&3&lAliases&8: &aps, pshop, ms, mshop");
        cfg.addDefault("Commands.Ps", "&8» &a/ps &8- &7Shows this text menu.");
        cfg.addDefault("Commands.PsSet", "&8» &a/ps set &8- &7Sets your player shop at your current location.");
        cfg.addDefault("Commands.PsMenu", "&8» &a/ps menu &8 - &7Opens the shop advertisement menu.");
        cfg.addDefault("Commands.PsTp", "&8» &a/ps tp &8- &7Teleports you to your shop. &8(&7If one is set.&8)");
        cfg.addDefault("Commands.PsPlayer", "&8» &a/ps <player> &8- &7Teleports you to another player's shop. &8(&7If one is set.&8)");
        cfg.addDefault("Commands.PsLock", "&8» &a/ps lock &8- &7Locks OR unlocks your shop. &8(&7Toggle&8)");
        cfg.addDefault("Commands.PsRemove", "&8» &a/ps remove &8- &7Deletes your shop &bAND &7advertisement!");
        cfg.addDefault("Commands.PsAdvertise", "&8» &a/ps advertise &8- &7Buy an advert spot for a set cost.");
        cfg.addDefault("Commands.PsLore", "&8» &a/ps lore <loreline> <lore> &8- &7Adds a custom message to your advert. &8(&7Only up to 3 lines!&8)");
        cfg.addDefault("Commands.PsLoreDelete", "&8» &a/ps lore delete &8- &7Reset all lore lines!");
        cfg.addDefault("Commands.PsReset", "&8» &3/ps reset &8- &7Resets the advert list!");
        cfg.addDefault("Commands.PsReload", "&8» &3/ps reload &8- &7Reloads all plugin files!");
        cfg.addDefault("Commands.Footer", "&7‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾");

        cfg.addDefault("Prefix", "&8[&a&l✔&8] &3&lMorphPShops &8&l➙ ");
        cfg.addDefault("ErrorPrefix", "&8[&c&l✕&8] &3&lMorphPShops &8&l➙ ");

        cfg.addDefault("NoPermission", "&7Invalid permissions!");
        cfg.addDefault("NoPlayer", "&7Player does not exist!");
        cfg.addDefault("NoSetShop", "&7You haven't set a shop yet! &8(&a/ps set&8)");
        cfg.addDefault("NoPlayerSetShop", "&7This player hasn't set a shop yet!");
        cfg.addDefault("NoMoney", "&7Invalid funds! &8(&3Cost&8: &aCOST&8)");
        cfg.addDefault("NoArgs", "&7Invalid args. Use &a/ps &7for commands!");
        cfg.addDefault("PlayerNameChange", "&7Player shop cannot be found! Name change?");
    }
}
