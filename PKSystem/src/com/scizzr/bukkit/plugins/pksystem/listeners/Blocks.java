package com.scizzr.bukkit.plugins.pksystem.listeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import com.scizzr.bukkit.plugins.pksystem.Main;
import com.scizzr.bukkit.plugins.pksystem.util.TombStone;
import com.scizzr.bukkit.plugins.pksystem.util.Vault;

public class Blocks implements Listener {
    Main plugin;
    
    public Blocks(Main instance) {
        plugin = instance;
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onBlockBreak(final BlockBreakEvent e) {
        Player p = e.getPlayer();
        Block b = e.getBlock();
        Block b2 = e.getBlock().getLocation().clone().add(0.0, +1.0, 0.0).getBlock();
        
        if (TombStone.isTombstone(b2.getLocation())) {
            p.sendMessage(Main.prefix + "You can't break a block under a tombstone");
            e.setCancelled(true); return;
        }
        
        if (b.getType() == Material.SIGN_POST) {
            if (TombStone.isTombstone(b.getLocation()) == true) {
                String owner = TombStone.getOwner(b.getLocation());
                boolean brk = false;
                if (owner.equalsIgnoreCase(p.getName())) {
                    brk = true;
                } else {
                    if (Vault.hasPermission(p, "stone.break.other")) {
                        brk = true;
                    }
                }
                if (brk == true) {
                    TombStone.claimStone(p, b.getLocation());
                    p.sendMessage(Main.prefix + "You retrieved " + (owner.equalsIgnoreCase(p.getName()) ? "your" : owner + "'s") + " tombstone");
                    e.setCancelled(true);
                    b.setType(Material.AIR);
                } else {
                    p.sendMessage(Main.prefix + "You can't break " + owner + "'s tombstone");
                    e.setCancelled(true);
                }
            }
        }
    }
}
