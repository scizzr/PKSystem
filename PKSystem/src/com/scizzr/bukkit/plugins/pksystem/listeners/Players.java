package com.scizzr.bukkit.plugins.pksystem.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import com.scizzr.bukkit.plugins.pksystem.Main;
import com.scizzr.bukkit.plugins.pksystem.config.Config;
import com.scizzr.bukkit.plugins.pksystem.config.PlayerOpt;
import com.scizzr.bukkit.plugins.pksystem.threads.Website;
import com.scizzr.bukkit.plugins.pksystem.util.Manager;
import com.scizzr.bukkit.plugins.pksystem.util.TombStone;
import com.scizzr.bukkit.plugins.pksystem.util.Vault;

public class Players implements Listener {
    Main plugin;
    
    public Players(Main instance) {
        plugin = instance;
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerLogin(final PlayerLoginEvent e) {
        PlayerOpt.checkAll(e.getPlayer());
        
        if (Config.fmtDispName == true) {
            for (Player pp : Bukkit.getOnlinePlayers()) {
                String dn = Manager.color.get(Manager.getIndex(Manager.getPoints(pp))) + pp.getName() + ChatColor.WHITE;
                pp.setDisplayName(dn);
            }
        }
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerJoin(final PlayerJoinEvent e) {
        Player p = e.getPlayer();
        
        if (Manager.getPoints(p) == null) { Manager.setPoints(p, 0); }
        if (Manager.getRepTime(p) == null) { Manager.setRepTime(p, 0); }
        
        if (Config.genVerCheck == true) {
            new Thread(new Website("checkVersion", p)).start();
        }
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerMove(final PlayerMoveEvent e) {
        Player p = e.getPlayer();
        
        if (Manager.getIndex(Manager.getPoints(p)) == -4 && Config.effSpecDemon == true) {
            p.getWorld().playEffect(p.getLocation(), Effect.MOBSPAWNER_FLAMES, 1);
        } else if (Manager.getIndex(Manager.getPoints(p)) == 4 && Config.effSpecHero == true) {
            p.getWorld().playEffect(p.getLocation(), Effect.ENDER_SIGNAL, 1);
        }
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerTeleport(final PlayerTeleportEvent e) {
        if (e.isCancelled()) { return; }
        
        Player p = e.getPlayer();
        if (Manager.isCombat(p)) {
            if (Config.pvpNoTP == true) {
                p.sendMessage(Main.prefix + "You cannot teleport while in combat.");
                e.setCancelled(true);
            }
        }
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerInteractEvent(final PlayerInteractEvent e) {
        Player p = e.getPlayer();
        
        if (e.getAction() == Action.LEFT_CLICK_BLOCK) {
            Integer rad = Bukkit.getSpawnRadius();
            Location spawn = p.getWorld().getSpawnLocation();
            Block b = e.getClickedBlock();
            
            if ((p.getLocation().distance(spawn) <= rad && !p.isOp()) || Config.behClaim.equalsIgnoreCase("click")) {
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
    }
}
