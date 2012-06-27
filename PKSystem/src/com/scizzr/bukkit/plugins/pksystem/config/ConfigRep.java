package com.scizzr.bukkit.plugins.pksystem.config;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.scizzr.bukkit.plugins.pksystem.Main;

public class ConfigRep extends JavaPlugin {
    File file = new File(getDataFolder() + "configPoints.yml");
    
    static boolean changed = false;
    
    Main plugin;
    
    public ConfigRep (Main plugin) {
        this.plugin = plugin;
    }
    
    public static void main() {
        File file = new File(Main.fileConfigPoints.getAbsolutePath());
        
        if (!file.exists()) {
            try {
                file.createNewFile();
                Main.log.info(Main.prefixConsole + "Blank configPoints.yml created");
            } catch (IOException ex) {
                Main.log.info(Main.prefixConsole + "Failed to make configPoints.yml");
                Main.suicide(ex);
            }
        }
        
        load();
    }
    
    static void load() {
        YamlConfiguration config = new YamlConfiguration();
        File file = Main.fileConfigPoints;
        
        try {
            config.load(file);
        } catch (Exception ex) {
            Main.log.info(Main.prefixConsole + "There was a problem loading configPoints.yml");
            Main.suicide(ex);
        }
        
        checkOption(config, "attack.xxx", Config.repGuide);
        checkOption(config, "attack.dem", Config.repAtkDem);                    Config.repAtkDem = config.getString("attack.dem");
        checkOption(config, "attack.red", Config.repAtkRed);                    Config.repAtkRed = config.getString("attack.red");
        checkOption(config, "attack.ora", Config.repAtkOra);                    Config.repAtkOra = config.getString("attack.ora");
        checkOption(config, "attack.yel", Config.repAtkYel);                    Config.repAtkYel = config.getString("attack.yel");
        checkOption(config, "attack.whi", Config.repAtkWhi);                    Config.repAtkWhi = config.getString("attack.whi");
        checkOption(config, "attack.ltb", Config.repAtkLtB);                    Config.repAtkLtB = config.getString("attack.ltb");
        checkOption(config, "attack.blu", Config.repAtkBlu);                    Config.repAtkBlu = config.getString("attack.blu");
        checkOption(config, "attack.pur", Config.repAtkPur);                    Config.repAtkPur = config.getString("attack.pur");
        checkOption(config, "attack.her", Config.repAtkHer);                    Config.repAtkHer = config.getString("attack.her");
        
        checkOption(config, "defend.xxx", Config.repGuide);
        checkOption(config, "defend.dem", Config.repDefDem);                    Config.repDefDem = config.getString("defend.dem");
        checkOption(config, "defend.red", Config.repDefRed);                    Config.repDefRed = config.getString("defend.red");
        checkOption(config, "defend.ora", Config.repDefOra);                    Config.repDefOra = config.getString("defend.ora");
        checkOption(config, "defend.yel", Config.repDefYel);                    Config.repDefYel = config.getString("defend.yel");
        checkOption(config, "defend.whi", Config.repDefWhi);                    Config.repDefWhi = config.getString("defend.whi");
        checkOption(config, "defend.ltb", Config.repDefLtB);                    Config.repDefLtB = config.getString("defend.ltb");
        checkOption(config, "defend.blu", Config.repDefBlu);                    Config.repDefBlu = config.getString("defend.blu");
        checkOption(config, "defend.pur", Config.repDefPur);                    Config.repDefPur = config.getString("defend.pur");
        checkOption(config, "defend.her", Config.repDefHer);                    Config.repDefHer = config.getString("defend.her");
        
        checkOption(config, "rep.special.enabled", Config.repSpecEnabled);      Config.repSpecEnabled = config.getBoolean("rep.special.enabled");
        checkOption(config, "rep.special.reach", Config.repSpecReach);          Config.repSpecReach = config.getBoolean("rep.special.reach");
        checkOption(config, "rep.limit.enabled", Config.repLimitEnabled);       Config.repLimitEnabled = config.getBoolean("rep.limit.enabled");
        checkOption(config, "rep.limit.amount", Config.repLimitAmount);         Config.repLimitAmount = config.getInt("rep.limit.amount");
        checkOption(config, "rep.limit.duration", Config.repLimitDuration);     Config.repLimitDuration = config.getInt("rep.limit.duration");
        
        if (changed) {
            config.options().header("PKSystem Configuration - Points");
            try {
                config.save(file);
            } catch (Exception ex) {
                Main.log.info(Main.prefixConsole + "Failed to save configReputation.yml");
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
