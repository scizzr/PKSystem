package com.scizzr.bukkit.plugins.pksystem.util;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.scizzr.bukkit.plugins.pksystem.managers.Manager;

public class MoreString {
    public static String replaceVars(String msg, Player pAtt, Player pDef) {
        int a = Manager.getIndex(Manager.getPoints(pAtt));
        int d = Manager.getIndex(Manager.getPoints(pDef));
        
        msg = msg.replace("+a", pAtt.getDisplayName() + ChatColor.RESET);
        msg = msg.replace("+d", pDef.getDisplayName() + ChatColor.RESET);
        msg = msg.replace("+p", String.valueOf(Math.abs(MoreMath.getKillPoints(a, d, true)*1000))) + ChatColor.RESET;
        msg = msg.replace("+r", String.valueOf(Math.abs(MoreMath.getKillPoints(a, d, true)))) + ChatColor.RESET;
        
        return msg;
    }
}
