package com.scizzr.bukkit.plugins.pksystem.effects;

import java.util.Random;

import org.bukkit.Effect;
import org.bukkit.Location;

public class PotionBomb {
    public static Random random = new Random();
    
    public static void potionSingle(Location location, int direction) {
        if (location == null) return;
        location.getWorld().playEffect(location.clone(), Effect.POTION_BREAK, direction);
    }
    
    public static void potionSingleRandom(Location location) {
        potionSingle(location, random.nextInt(9));
    }
    
    public static void potionCloudRandom(Location location, float thickness) {
        int s = (int)Math.floor(thickness * 9.0F);
        for (int i = 0; i < s; i++) {
            potionSingleRandom(location.clone());
        }
    }
}
