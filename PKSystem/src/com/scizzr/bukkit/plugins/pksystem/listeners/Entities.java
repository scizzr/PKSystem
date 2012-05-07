package com.scizzr.bukkit.plugins.pksystem.listeners;


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
import com.scizzr.bukkit.plugins.pksystem.managers.Manager;
import com.scizzr.bukkit.plugins.pksystem.util.MoreMath;
import com.scizzr.bukkit.plugins.pksystem.util.MoreString;
import com.scizzr.bukkit.plugins.pksystem.util.TombStone;
import com.scizzr.bukkit.plugins.pksystem.util.Vault;

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
                Entity entAtt = ((Projectile) att).getShooter();
                if (entAtt instanceof Player) {
                    pAtt = (Player) ((Projectile) att).getShooter();
                } else {
                    return;
                }
                
            } else {
                pAtt = (Player) att;
            }
            
            if (pDef == pAtt) { /*pAtt.sendMessage(Main.prefix + "You can't harm yourself");*/ e.setCancelled(true); return; }
            
            if (!Vault.hasPermission(pAtt, "override.combat")) {
                if (Config.combSpawnEnabled == true && Manager.isRespawn(pDef) == true) {
                    pAtt.sendMessage(Main.prefix + "You can't attack " + pDef.getName() + " yet. They just spawned.");
                    e.setCancelled(true); return;
                }
                
                if (Manager.getIndex(Manager.getPoints(pDef)) >= 0) {
                    if (Manager.isPK(pAtt) == false && !Manager.isCombat(pDef) && Config.combPkOnly == true) {
                        pAtt.sendMessage(Main.prefix + "You must enter PK mode to kill players");
                        e.setCancelled(true); return;
                    }
                    
                    if (Config.combNoobEnabled && (pDef.getLevel() < Config.combNoobLevel && Manager.isCombat(pDef) == false)) {
                        pAtt.sendMessage(Main.prefix + "You can't kill good players under level " + String.valueOf(Config.combNoobLevel));
                        e.setCancelled(true); return;
                    }
                }
            }
            
            if (Manager.getCrim(pDef) == false) {
                Manager.setCrim(pAtt, true);
            }
            
// Removed for now; might use this at a later time.
            //Manager.setLastTarget(pAtt, pDef);
            //Manager.setLastTarget(pDef, pAtt);
            
            if (Config.combDuration > 0) {
                Manager.setPvPTime(pAtt, Config.combDuration);
                Manager.setPvPTime(pDef, Config.combDuration);
            }
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
                
                
                if (Manager.isFarm(pKill) == false) {
                    Manager.setFarmTime(pKill, Manager.getFarmTime(pKill) + Config.repLimitDuration);
                    Manager.setPoints(pKill, pKillPtsOld + (MoreMath.getKillPoints(a, d, true) * 1000));
                } else {
                    pKill.sendMessage(Main.prefix + MoreString.replaceVars(Config.fmtPtsNoRew, pKill, pDead));
                }
                
                Manager.setPoints(pDead, pDeadPtsOld + (MoreMath.getKillPoints(d, a, false) * 1000));
                
                PlayerDeathEvent pde = (PlayerDeathEvent) e;
                
                if (Config.effLightEnabled == true) {
                    pDead.getWorld().strikeLightningEffect(pDead.getLocation().add(0.0, 8.0, 0.0));
                }
                
                if (Config.fmtDeathEnabled == true) {
                    String msg = (avenged) ? Config.fmtDeathGood : Config.fmtDeathEvil;
                    
                    msg = MoreString.replaceVars(msg, pKill, pDead);
                    
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
