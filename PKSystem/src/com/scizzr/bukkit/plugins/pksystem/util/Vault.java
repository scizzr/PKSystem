package com.scizzr.bukkit.plugins.pksystem.util;

import net.milkbowl.vault.permission.Permission;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.scizzr.bukkit.plugins.pksystem.Main;

public class Vault extends JavaPlugin {
    public static Permission permissionHandler = null;
    
    public static boolean setupPermissions() {
        if (Main.pm.getPlugin("Vault") != null) {
            RegisteredServiceProvider<Permission> permissionProvider = Bukkit.getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
            
            if (permissionProvider != null) {
                permissionHandler = permissionProvider.getProvider();
            }
            
            return (permissionHandler != null);
        }
        return false;
    }
    
    public static boolean hasPermission(Player p, String perm) {
        if (p.isOp()) {
            return true;
        }
        
        if (permissionHandler != null) {
            if (permissionHandler.has(p, "pks." + perm)) { return true; } else { return false; }
        } else {
            if (p.hasPermission(perm)) { return true; } else { return false; }
        }
// Code below is for debugging; spams chat
/*
        boolean d = true;
        
        if(d) { p.sendMessage("pks." + perm); }
        
        if (p.isOp()) {
            if (Config.permAllowOps == true) { if (d) { p.sendMessage("y1"); } return true; }
        }
        
        if (permissionHandler != null) {
            if (permissionHandler.playerHas(p, "pks." + perm)) { if (d) { p.sendMessage("y2"); } return true; } else { if (d) { p.sendMessage("n2"); } return false; }
        } else {
            if (p.hasPermission(perm)) { if (d) { p.sendMessage("y3"); } return true; } else { if (d) { p.sendMessage("n3"); } return false; }
        }
*/
    }
}
