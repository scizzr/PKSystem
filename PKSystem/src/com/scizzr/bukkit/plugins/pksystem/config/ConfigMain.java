package com.scizzr.bukkit.plugins.pksystem.config;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.scizzr.bukkit.plugins.pksystem.Main;

public class ConfigMain extends JavaPlugin {
    File file = new File(getDataFolder() + "configMain.yml");
    
    static boolean changed = false;
    
    Main plugin;
    
    public ConfigMain (Main plugin) {
        this.plugin = plugin;
    }
    
    public static void main() {
        File file = new File(Main.fileConfigMain.getAbsolutePath());
        
        if (!file.exists()) {
            try {
                file.createNewFile();
                Main.log.info(Main.prefixConsole + "Blank configMain.yml created");
            } catch (IOException ex) {
                Main.log.info(Main.prefixConsole + "Failed to make configMain.yml");
                Main.suicide(ex);
            }
        }
        
        load();
    }
    
    static void load() {
        YamlConfiguration config = new YamlConfiguration();
        File file = Main.fileConfigMain;
        
        try {
            config.load(file);
        } catch (Exception ex) {
            Main.log.info(Main.prefixConsole + "There was a problem loading configMain.yml");
            Main.suicide(ex);
        }
        
        editOption(config, "general.uuid", null);
        editOption(config, "pvp.duration", "combat.duration");
        editOption(config, "pvp.noobs.enabled", "combat.noobs.enabled");
        editOption(config, "pvp.noobs.level", "combat.noobs.level");
        editOption(config, "pvp.notp", "combat.notp");
        editOption(config, "pvp.pkonly", "combat.pkonly");
        editOption(config, "pvp", null);
        
        checkOption(config, "general.prefix", Config.genPrefix);                Config.genPrefix = config.getBoolean("general.prefix");
        checkOption(config, "general.stats", Config.genStats);                  Config.genStats = config.getBoolean("general.stats");
        checkOption(config, "general.uniqid", Config.genUniqID);                Config.genUniqID = config.getString("general.uniqid");
        checkOption(config, "general.vercheck", Config.genVerCheck);            Config.genVerCheck = config.getBoolean("general.vercheck");
        checkOption(config, "general.autoupdate", Config.genAutoUpdate);        Config.genAutoUpdate = config.getBoolean("general.autoupdate");
        checkOption(config, "general.errorweb", Config.genErrorWeb);            Config.genErrorWeb = config.getBoolean("general.errorweb");
        
        checkOption(config, "format.combat.enabled", Config.fmtCombEnabled);    Config.fmtCombEnabled = config.getBoolean("format.combat.enabled");
        checkOption(config, "format.combat.enter", Config.fmtCombEnter);        Config.fmtCombEnter = config.getString("format.combat.enter");
        checkOption(config, "format.combat.exit", Config.fmtCombExit);          Config.fmtCombExit = config.getString("format.combat.exit");
        checkOption(config, "format.death.enabled", Config.fmtDeathEnabled);    Config.fmtDeathEnabled = config.getBoolean("format.death.enabled");
        checkOption(config, "format.death.good", Config.fmtDeathGood);          Config.fmtDeathGood = config.getString("format.death.good");
        checkOption(config, "format.death.evil", Config.fmtDeathEvil);          Config.fmtDeathEvil = config.getString("format.death.evil");
        checkOption(config, "format.pk.enter", Config.fmtPKEnter);              Config.fmtPKEnter = config.getString("format.pk.enter");
        checkOption(config, "format.pk.exit", Config.fmtPKExit);                Config.fmtPKExit = config.getString("format.pk.exit");
        checkOption(config, "format.displayname", Config.fmtDispName);          Config.fmtDispName = config.getBoolean("format.displayname");
        checkOption(config, "format.tablist", Config.fmtTabList);               Config.fmtTabList = config.getBoolean("format.tablist");
        
        checkOption(config, "kill.pkoff", Config.killPkOff);                    Config.killPkOff = config.getBoolean("kill.pkoff");
        checkOption(config, "kill.mobs.enabled", Config.killMobsEnabled);       Config.killMobsEnabled = config.getBoolean("kill.mobs.enabled");
        checkOption(config, "kill.mobs.points", Config.killMobsPoints);         Config.killMobsPoints = config.getInt("kill.mobs.points");
        
        checkOption(config, "permissions.allowops", Config.permAllowOps);       Config.permAllowOps = config.getBoolean("permissions.allowops");
        
        checkOption(config, "combat.duration", Config.combDuration);            Config.combDuration = config.getInt("combat.duration");
        checkOption(config, "combat.noobs.enabled", Config.combNoobEnabled);    Config.combNoobEnabled = config.getBoolean("combat.noobs.enabled");
        checkOption(config, "combat.noobs.level", Config.combNoobLevel);        Config.combNoobLevel = config.getInt("combat.noobs.level");
        checkOption(config, "combat.notp", Config.combNoTP);                    Config.combNoTP = config.getBoolean("combat.notp");
        checkOption(config, "combat.pkonly", Config.combPkOnly);                Config.combPkOnly = config.getBoolean("combat.pkonly");
        checkOption(config, "combat.spawn.enabled", Config.combSpawnEnabled);   Config.combSpawnEnabled = config.getBoolean("combat.spawn.enabled");
        checkOption(config, "combat.spawn.duration", Config.combSpawnDuration); Config.combSpawnDuration = config.getInt("combat.spawn.duration");
        
        
//        checkOption(config, "respawn.protection", Config.respawnProt);          Config.respawnProt = config.getBoolean("respawn.protection");
//        checkOption(config, "respawn.duration", Config.respawnDur);             Config.respawnDur = config.getInt("respawn.duration");
        
        Main.prefix = (Config.genPrefix == true) ? Main.prefixMain : "";
        
        if (changed) {
            config.options().header("PKSystem Configuration - Main");
            try {
                config.save(file);
            } catch (Exception ex) {
                Main.log.info(Main.prefixConsole + "Failed to save configMain.yml");
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
