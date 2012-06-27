package com.scizzr.bukkit.plugins.pksystem.effects;

import java.util.Random;

import org.bukkit.Effect;
import org.bukkit.Location;

public class SmokeBomb {
    public static Random random = new Random();
    
    public static void smokeSingle(Location location, int direction) {
      if (location == null) return;
      location.getWorld().playEffect(location.clone(), Effect.SMOKE, direction);
    }
    
    public static void smokeSingleRandom(Location location) {
      smokeSingle(location, random.nextInt(9));
    }
    
    public static void smokeCloudRandom(Location location, float thickness) {
      int singles = (int)Math.floor(thickness * 9.0F);
      for (int i = 0; i < singles; i++) {
        smokeSingleRandom(location.clone());
      }
    }

}
