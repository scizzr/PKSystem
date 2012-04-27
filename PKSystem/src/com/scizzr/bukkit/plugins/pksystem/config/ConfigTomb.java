package com.scizzr.bukkit.plugins.pksystem.config;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.scizzr.bukkit.plugins.pksystem.Main;

public class ConfigTomb extends JavaPlugin {
    File file = new File(getDataFolder() + "configTomb.yml");
    
    static boolean changed = false;
    
    Main plugin;
    
    public ConfigTomb (Main plugin) {
        this.plugin = plugin;
    }
    
    public static void main() {
        File file = new File(Main.fileConfigTomb.getAbsolutePath());
        
        if (!file.exists()) {
            try {
                file.createNewFile();
                Main.log.info(Main.prefixConsole + "Blank configTomb.yml created");
            } catch (IOException ex) {
                Main.log.info(Main.prefixConsole + "Failed to make configTomb.yml");
                Main.suicide(ex);
            }
        }
        
        load();
    }
    
    static void load() {
        YamlConfiguration config = new YamlConfiguration();
        File file = Main.fileConfigTomb;
        
        try {
            config.load(file);
        } catch (Exception ex) {
            Main.log.info(Main.prefixConsole + "There was a problem loading configTomb.yml");
            Main.suicide(ex);
        }
        
        checkOption(config, "stones.enabled", Config.tombEnabled);              Config.tombEnabled = config.getBoolean("stones.enabled");
        checkOption(config, "stones.locked", Config.tombLocked);                Config.tombLocked = config.getBoolean("stones.locked");
        
        checkOption(config, "behavior.claimmode", Config.behClaim);             Config.behClaim = config.getString("behavior.claimmode");
        checkOption(config, "behavior.preserve", Config.behPreserve);           Config.behPreserve = config.getBoolean("behavior.preserve");
        
        if (changed) {
            config.options().header(
                "PKSystem Configuration - Tombstones"
            );
            try { config.save(file); } catch (Exception ex) { Main.log.info(Main.prefixConsole + "Failed to save configTomb.yml"); /*ex.printStackTrace();*/ }
        }
    }
    
    static void checkOption(YamlConfiguration config, String node, Object def) {
        if (!config.isSet(node)) {
            config.set(node, def);
            changed = true;
        }
    }
    
    static void editOption(YamlConfiguration config, String nodeOld, String nodeNew) {
        if (config.isSet(nodeOld)) {
            if (nodeNew != null) {
                config.set(nodeNew, config.get(nodeOld));
            }
            config.set(nodeOld, null);
            changed = true;
        }
    }
}
