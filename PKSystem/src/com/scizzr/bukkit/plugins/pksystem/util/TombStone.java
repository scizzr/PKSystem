package com.scizzr.bukkit.plugins.pksystem.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.scizzr.bukkit.plugins.pksystem.Main;
import com.scizzr.bukkit.plugins.pksystem.config.Config;

@SuppressWarnings("unchecked")
public class TombStone {
    private static HashMap<String, String> stones = new HashMap<String, String> ();
    private static HashMap<Player, Integer> stoneTimer = new HashMap<Player, Integer> ();
    
    public static boolean loadStones() {
        if(!Main.fileStones.exists()) {
            try {
                Main.fileStones.createNewFile();
                Main.log.info(Main.prefixConsole + "Blank tombstones.txt created");
            } catch (Exception ex) {
                Main.log.info(Main.prefixConsole + "Failed to make tombstones.txt");
                Main.suicide(ex);
            }
        }
        
        HashMap<String, String> tmp = (HashMap<String, String>) stones.clone();
        try {
            stones.clear();
            BufferedReader reader = new BufferedReader(new FileReader(Main.fileStones.getAbsolutePath()));
            String line = reader.readLine();
            while (line != null) {
                String[] vals = line.split(";");
                stones.put(vals[0], vals[1] + ";" + vals[2]);
                line = reader.readLine();
            }
            return true;
        } catch (Exception ex) {
            stones = (HashMap<String, String>) tmp.clone();
            Main.suicide(ex);
            return false;
        }
    }
    
    public static boolean saveStones() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(Main.fileStones.getAbsolutePath()));
            for(Entry<String, String> entry : stones.entrySet()) {
                writer.write(entry.getKey() + ";" + entry.getValue());
                writer.newLine();
            }
            writer.close();
            return true;
        } catch (Exception ex) {
            Main.suicide(ex);
            return false;
        }
    }
    
    public static void addTombstone(Player pDead, Player pKill, List<ItemStack> list) {
        Location loc = pDead.getLocation();
        
        Location safe = Misc.getSafe(loc);
        
        String key = pDead.getWorld().getName() + ":" + safe.getBlockX() + ":" + safe.getBlockY() + ":" + safe.getBlockZ();
        String info = "false:" + pDead.getName() + ":" + pKill.getName();
        String drops = "";
        
        for (int i = 0; i < list.size(); i++) {
            ItemStack item = list.get(i);
            
            Integer id = item.getTypeId();
            Short dura = item.getDurability();
            Integer amt = item.getAmount();
            
            String enc = "";
            
            for (Map.Entry<Enchantment, Integer> ench : item.getEnchantments().entrySet()) {
                Integer encID = ench.getKey().getId();
                Integer encLvl = ench.getValue();
                enc += "&" + encID + "." + encLvl;
            }
            
            if (enc == "") { enc = "&"; }
            
            drops += "|" + id + ":" + dura + ":" + amt + ":" + enc.substring(1);
        }
        
        if (drops != "") {
            stones.put(key, info + ";" + drops.substring(1));
            saveStones();
        
            String calY = String.valueOf(Main.calY).substring(2);
            String calM = String.valueOf(Main.calM);
            String calD = String.valueOf(Main.calD);
            String calH = String.valueOf(Main.calH);
            String calI = String.valueOf(Main.calI);
            String calS = String.valueOf(Main.calS);
            String calA = String.valueOf(Main.calA).substring(0, 1);
            String date = calM + "/" + calD + "/" + calY + " " + calH + ":" + calI + ":" + calS + " " + calA;
            
            Block signBlock = safe.getWorld().getBlockAt(safe);
            signBlock.setType(Material.SIGN_POST);
            
            Sign sign = (Sign) signBlock.getState();
            sign.setLine(0, pDead.getName());
            sign.setLine(1, "was slain by");
            sign.setLine(2, pKill.getName());
            sign.setLine(3, date);
            sign.update();
            
            //Chunk chunk = w.getChunkAt(signBlock);
            //w.refreshChunk(chunk.getX(), chunk.getZ());
            
            pDead.sendMessage(Main.prefix + "Your tombstone was placed at [" + safe.getBlockX() + "," + safe.getBlockY() + "," + safe.getBlockZ() + "]");
        }
    }
    
    public static void delTombstone(Location loc) {
        World w = loc.getWorld();
        Integer x = loc.getBlockX();
        Integer y = loc.getBlockY();
        Integer z = loc.getBlockZ();
        
        String key = w.getName() + ":" + x + ":" + y + ":" + z;
        stones.remove(key);
        
        saveStones();
    }
    
    public static void claimStone(Player p, Location loc) {
        if (!isTombstone(loc)) { return; }
        
        World w = loc.getWorld();
        Integer x = loc.getBlockX();
        Integer y = loc.getBlockY();
        Integer z = loc.getBlockZ();
        
        String key = w.getName() + ":" + x + ":" + y + ":" + z;
        
        String[] data = stones.get(key).split(";");
        String[] info = data[0].split(":");
        String[] items = data[1].split("\\|");
        
        for (String item : items) {
            Integer id = Integer.valueOf(item.split(":")[0]);
            Short dur = Short.valueOf(item.split(":")[1]);
            Integer amt = Integer.valueOf(item.split(":")[2]);
            
            ItemStack stack = new ItemStack(id, amt, dur);
            
            if (item.split(":").length == 4) {
                String[] encs = item.split(":")[3].split("&");
                
                for (int i = 0; i < encs.length; i++) {
                    String[] encc = encs[i].split("\\.");
                    stack.addUnsafeEnchantment(Enchantment.getById(Integer.valueOf(encc[0])), Integer.valueOf(encc[1]));
                    //Map<Enchantment, Integer> ench = null;
                    //ench.put(Enchantment.getById(Integer.valueOf(encc[0])), Integer.valueOf(encc[1]));
                }
            }
            
            //p.getInventory().addItem(stack);
            p.getWorld().dropItem(p.getLocation(), stack);
        }
        
        if (Config.behPreserve == true) {
            stones.put(key, "true:" + info[1] + ":" + info[2] + ";" + data[1]);
            saveStones();
        } else {
            delTombstone(loc);
        }
    }
    
    public static boolean isTombstone(Location loc) {
        World w = loc.getWorld();
        Integer x = loc.getBlockX();
        Integer y = loc.getBlockY();
        Integer z = loc.getBlockZ();
        
        String key = w.getName() + ":" + x + ":" + y + ":" + z;
        
        if (stones.get(key) != null) {
            String[] data = stones.get(key).split(";");
            String[] info = data[0].split(":");
            
            if (info[0].equalsIgnoreCase("false")) {
                return true;
            }
        }
        
        return false;
    }
    
    public static String getOwner(Location loc) {
        if (!isTombstone(loc)) { return null; }
        
        World w = loc.getWorld();
        Integer x = loc.getBlockX();
        Integer y = loc.getBlockY();
        Integer z = loc.getBlockZ();
        
        String key = w.getName() + ":" + x + ":" + y + ":" + z;
        
        String[] data = stones.get(key).split(";");
        String[] info = data[0].split(":");
        
        return info[1];
    }
    
    @SuppressWarnings("null")
    public static List<ItemStack> getDrops(Location loc) {
        if (!isTombstone(loc)) { return null; }
        
        List<ItemStack> list = null;
        
        World w = loc.getWorld();
        Integer x = loc.getBlockX();
        Integer y = loc.getBlockY();
        Integer z = loc.getBlockZ();
        
        String key = w.getName() + ":" + x + ":" + y + ":" + z;
        
        String data = stones.get(key).split(";")[1];
        String[] items = data.split("|");
        
        for (String s : items) {
            Integer id = Integer.valueOf(s.split(":")[0]);
            Short dur = Short.valueOf(s.split(":")[1]);
            Integer amt = Integer.valueOf(s.split(":")[2]);
            ItemStack item = new ItemStack(id, amt, dur);
            list.add(item);
        }
        
        return list;
    }
    
    public static HashMap<String, String> getStones() {
        return stones;
    }
    
    public static void setTimer(Player p, Integer i) {
        if (i > 0) {
            stoneTimer.put(p, i);
        } else {
            stoneTimer.remove(p);
        }
    }
    
    public static Integer getTimer(Player p) {
        Integer time = stoneTimer.get(p);
        
        return time != null ? time : 0;
    }
    
    public static void listStones(Player p) {
        p.sendMessage(stones.toString());
    }
}
