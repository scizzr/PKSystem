package com.scizzr.bukkit.plugins.pksystem.threads;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.scizzr.bukkit.plugins.pksystem.Main;
import com.scizzr.bukkit.plugins.pksystem.config.Config;
import com.scizzr.bukkit.plugins.pksystem.util.Vault;

public class Update implements Runnable {
    private String act; Object par1, par2;
    
    public Update(String act, Object par1, Object par2) {
        this.act = act;
        this.par1 = par1;
        this.par2 = par2;
    }
    
    public void run() {
        if (act.equalsIgnoreCase("check")) {
            check((Player) par1);
        } else if (act.equalsIgnoreCase("update")) {
            update((Player) par1, (String) par2);
        }
    }
    
    public static void check(Player p) {
        try {
            URL url = new URL(
                String.format("http://www.scizzr.com/util/plugins/version.php?plug=" + Main.info.getName())
            );
            
            URLConnection conn = url.openConnection();
            InputStreamReader stream = new InputStreamReader(conn.getInputStream());
            BufferedReader buff = new BufferedReader(stream);
            String line = buff.readLine();
            
            while (line != null) {
                String verNew = line.split("<br/>")[1];
                String verCur = Main.info.getVersion().endsWith("+") ? Main.info.getVersion().substring(0, Main.info.getVersion().length()-1) : Main.info.getVersion();
                if (!verNew.equalsIgnoreCase(verCur)) {
                    if (p != null) {
                        if (Vault.hasPermission(p, "newver")) {
                            if (Config.genAutoUpdate == true) {
                                new Thread(new Update("update", p, verNew)).start();
                            } else {
                                p.sendMessage(Main.prefix + "Your version of " + Main.info.getName() + " is out of date.");
                                p.sendMessage(Main.prefix + "Version " + verNew + " can be downloaded from:");
                                p.sendMessage(ChatColor.YELLOW + "http://www.scizzr.com/plugins/" + Main.info.getName() + ".jar");
                            }
                        }
                    }
                }
                line = buff.readLine();
            }
            stream.close();
        } catch (Exception ex) {
            Main.suicide(ex);
        }
    }
    
    public static void update(Player p, String ver) {
        try {
            String url = "http://www.scizzr.com/plugins/" + Main.info.getName() + ".jar";
            
            java.io.BufferedInputStream in = new java.io.BufferedInputStream(new URL(url).openStream());
            
            //File tgt = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().getPath());
            File tgt = Main.filePlug;
            
            java.io.FileOutputStream fos = new java.io.FileOutputStream(tgt);
            
            java.io.BufferedOutputStream bout = new BufferedOutputStream(fos, 1024);
            byte[] data = new byte[1024];
            int x=0;
            
            while((x=in.read(data,0,1024))>=0) {
                bout.write(data,0,x);
            }
            
            bout.close();
            in.close();
            
            if (p != null) {
                p.sendMessage(Main.prefix + "Version " + ver + " has been downloaded.");
                p.sendMessage(Main.prefix + "Reload or restart the server to finish updating.");
            }
        } catch (Exception ex) {
            Main.suicide(ex);
        }
    }
}
