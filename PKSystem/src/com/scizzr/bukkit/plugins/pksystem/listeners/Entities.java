package com.scizzr.bukkit.plugins.pksystem.listeners;


import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import com.scizzr.bukkit.plugins.pksystem.Main;
import com.scizzr.bukkit.plugins.pksystem.config.Config;
import com.scizzr.bukkit.plugins.pksystem.util.Manager;
import com.scizzr.bukkit.plugins.pksystem.util.MoreMath;
//import com.scizzr.bukkit.plugins.pksystem.util.MoreMath;
import com.scizzr.bukkit.plugins.pksystem.util.TombStone;

public class Entities implements Listener {
    Main plugin;
    
    public Entities(Main instance) {
        plugin = instance;
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onEntityDamageByEntityEvent(final EntityDamageByEntityEvent e) {
        if (e.isCancelled()) { return; }
        
        Entity def = e.getEntity();
        Entity att = e.getDamager();
        
        if (def instanceof Player && (att instanceof Player || att instanceof Projectile)) {
            Player pDef = (Player) def;
            Player pAtt = null;
            
            if (att instanceof Projectile) {
                pAtt = (Player) ((Projectile) att).getShooter();
            } else {
                pAtt = (Player) att;
            }
            
            if (pDef == pAtt) { pAtt.sendMessage(Main.prefix + "You can't harm yourself"); e.setCancelled(true); return; }
            
            if (Manager.isProtected(pDef) == true) {
                e.setCancelled(true); return;
            }
            
            if (Manager.getIndex(Manager.getPoints(pDef)) >= 0) {
                if (Manager.getPK(pAtt) == false && !Manager.isCombat(pDef) && Config.pvpPkOnly == true) {
                    pAtt.sendMessage(Main.prefix + "You must enter PK mode to kill players");
                    e.setCancelled(true); return;
                }
                
                if (Config.pvpNoobEnabled && (pDef.getLevel() < Config.pvpNoobLevel && Manager.isCombat(pDef) == false)) {
                    pAtt.sendMessage(Main.prefix + "You can't kill good players under level " + String.valueOf(Config.pvpNoobLevel));
                    e.setCancelled(true); return;
                }
            }
            
            if (Manager.getCrim(pDef) == false) {
                Manager.setCrim(pAtt, true);
            }
            
            Manager.setLastTarget(pAtt, pDef);
            Manager.setLastTarget(pDef, pAtt);
            
            Manager.setPvPTime(pAtt, Config.pvpDuration);
            Manager.setPvPTime(pDef, Config.pvpDuration);
        }
    }    
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onEntityDeathEvent(final EntityDeathEvent e) {
        Entity eDead = e.getEntity();
        EntityDamageEvent cause = eDead.getLastDamageCause();
        
        if (!(eDead instanceof Player)) {
            if (cause instanceof EntityDamageByEntityEvent) {
                EntityDamageByEntityEvent damageEvent = (EntityDamageByEntityEvent)cause;
                
                if (damageEvent.getDamager() instanceof Player && damageEvent.getEntity() instanceof Monster) {
                    Player pKill = (Player) damageEvent.getDamager();
                    
                    Integer pts = Manager.getPoints(pKill);
                    
                    if (pts < 0) {
                        if (Config.killMobsEnabled == true) {
// Award the player points for slaying a monster, only if they have a bad reputation
                            Manager.setPoints(pKill, (pts + Config.killMobsPoints <= 0) ? pts + Config.killMobsPoints : 0);
                        }
                    }
                }
            }
            return;
        }
        
        Player pDead = (Player) eDead;
        
        if (Manager.isCombat(pDead) == true) {
            Manager.setPvPTime(pDead, 0);
        }
        
        if (Config.killPkOff == true) {
            Manager.setPK(pDead, false);
        }
        
        Player pKill = null;
        
        if (cause instanceof EntityDamageByEntityEvent) {
            
            EntityDamageByEntityEvent damageEvent = (EntityDamageByEntityEvent)cause;
            Entity eKill = damageEvent.getDamager();
            
            if (damageEvent.isCancelled()) { return; }
            
            if (eKill instanceof Player) {
                pKill = ((Player) eKill).getPlayer();
            } else if (eKill instanceof Projectile) {
                Projectile projectile = (Projectile)damageEvent.getDamager();
                pKill = (Player) projectile.getShooter();
            }
            
            if (pKill != null) {
                
                Integer pKillPtsOld = Manager.getPoints(pKill);
                Integer pDeadPtsOld = Manager.getPoints(pDead);
                
                if (!(e instanceof PlayerDeathEvent)) {
                    if (pKillPtsOld != null) {
                        if (pKillPtsOld < 0) {
                            if (Config.killMobsEnabled == true) {
                                Manager.setPoints(pKill, pKillPtsOld+Config.killMobsPoints);
                            }
                        }
                    }
                    return;
                }
                
                Boolean avenged = (Manager.getIndex(Manager.getPoints(pDead)) < 0 ? true : false);

                int a = Manager.getIndex(Manager.getPoints(pKill));
                int d = Manager.getIndex(Manager.getPoints(pDead));
                
                Manager.setPoints(pKill, pKillPtsOld + (MoreMath.getKillPoints(a, d, true) * 1000));
                Manager.setPoints(pDead, pDeadPtsOld + (MoreMath.getKillPoints(d, a, false) * 1000));
                
                PlayerDeathEvent pde = (PlayerDeathEvent) e;
                
                if (Config.fmtDeathEnabled == true) {
                    String msg = (avenged) ? Config.fmtDeathGood : Config.fmtDeathEvil;
                    msg = msg.replace("+a", pKill.getDisplayName() + ChatColor.RESET);
                    msg = msg.replace("+d", pDead.getDisplayName() + ChatColor.RESET);
                    msg = msg.replace("+p", String.valueOf(Math.abs(MoreMath.getKillPoints(a, d, true)*1000))) + ChatColor.RESET;
                    msg = msg.replace("+r", String.valueOf(Math.abs(MoreMath.getKillPoints(a, d, true)))) + ChatColor.RESET;
                    
                    pde.setDeathMessage(msg);
                }
                
                if (Config.tombEnabled == true) {
                    TombStone.addTombstone(pDead, pKill, e.getDrops());
                    e.getDrops().clear();
                }
            }
        }
    }
}
