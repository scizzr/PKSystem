package com.scizzr.bukkit.plugins.pksystem.util;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class Misc {
    public static Location getSafe(Location loc) {
        Location diff = loc.clone();
        
        for (int i = (int) loc.getBlockY(); i <= 256; i++) {
            Block block1 = diff.getChunk().getBlock((int) diff.getBlockX(), (int) diff.getBlockY(), (int) diff.getBlockZ());
            Block block2 = diff.getChunk().getBlock((int) diff.getBlockX(), (int) diff.getBlockY()+1, (int) diff.getBlockZ());
            if (block1.getType() != Material.AIR) {
                diff.setY(diff.getBlockY()+1);
            } else if (block2.getType() != Material.AIR) {
                diff.setY(diff.getBlockY()+2);
            }
        }
        
        for (int i = (int) diff.getBlockY(); i >= 1; i--) {
            Block block = diff.getChunk().getBlock((int) diff.getBlockX(), (int) diff.getBlockY()-1, (int) diff.getBlockZ());
            if (block.getType() == Material.AIR) {
                diff.setY(diff.getBlockY()-1);
            }
        }
        
        if (diff.getBlockY() == 0) {
            diff.setY(1);
        }
        
        return diff;
    }
}
