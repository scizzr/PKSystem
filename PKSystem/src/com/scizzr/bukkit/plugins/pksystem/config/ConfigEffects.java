package com.scizzr.bukkit.plugins.pksystem.config;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.scizzr.bukkit.plugins.pksystem.Main;

public class ConfigEffects extends JavaPlugin {
    File file = new File(getDataFolder() + "configEffects.yml");
    
    static boolean changed = false;
    
    Main plugin;
    
    public ConfigEffects (Main plugin) {
        this.plugin = plugin;
    }
    
    public static void main() {
        File file = new File(Main.fileConfigEffects.getAbsolutePath());
        
        if (!file.exists()) {
            try {
                file.createNewFile();
                Main.log.info(Main.prefixConsole + "Blank configEffects.yml created");
            } catch (IOException ex) {
                Main.log.info(Main.prefixConsole + "Failed to make configEffects.yml");
                Main.suicide(ex);
            }
        }
        
        load();
    }
    
    static void load() {
        YamlConfiguration config = new YamlConfiguration();
        File file = Main.fileConfigEffects;
        
        try {
            config.load(file);
        } catch (Exception ex) {
            Main.log.info(Main.prefixConsole + "There was a problem loading configEffects.yml");
            Main.suicide(ex);
        }
        
        checkOption(config, "effects.invis.enabled", Config.effInvisEnabled);       Config.effInvisEnabled = config.getBoolean("effects.invis.enabled");
        checkOption(config, "effects.invis.min", Config.effInvisMin);               Config.effInvisMin = config.getInt("effects.invis.min");
        checkOption(config, "effects.invis.max", Config.effInvisMax);               Config.effInvisMax = config.getInt("effects.invis.max");
        checkOption(config, "effects.lightning.enabled", Config.effLightEnabled);   Config.effLightEnabled = config.getBoolean("effects.lightning.enabled");
        checkOption(config, "effects.potions.enabled", Config.effPotsEnabled);      Config.effPotsEnabled = config.getBoolean("effects.potions.enabled");
        checkOption(config, "effects.smoke.incombat", Config.effSmokeCombat);       Config.effSmokeCombat = config.getBoolean("effects.smoke.incombat");
        checkOption(config, "effects.smoke.inpk", Config.effSmokeInPK);             Config.effSmokeInPK = config.getBoolean("effects.smoke.inpk");
        checkOption(config, "effects.smoke.invis", Config.effSmokeInvis);           Config.effSmokeInvis = config.getBoolean("effects.smoke.invis");
        checkOption(config, "effects.special.demon", Config.effSpecDemon);          Config.effSpecDemon = config.getBoolean("effects.special.demon");
        checkOption(config, "effects.special.hero", Config.effSpecHero);            Config.effSpecHero = config.getBoolean("effects.special.hero");
        checkOption(config, "effects.special.neutral", Config.effSpecNeutral);      Config.effSpecNeutral = config.getBoolean("effects.special.neutral");
        
        if (changed) {
            config.options().header("PKSystem Configuration - Effects");
            try {
                config.save(file);
            } catch (Exception ex) {
                Main.log.info(Main.prefixConsole + "Failed to save configEffects.yml");
                Main.suicide(ex);
            }
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
