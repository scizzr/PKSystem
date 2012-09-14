package com.scizzr.bukkit.plugins.pksystem.util;

import java.text.DecimalFormat;

import com.scizzr.bukkit.plugins.pksystem.config.Config;

public class MoreMath {
    public static boolean between(double num, double d, double e) {
        return between(num, d, e, false);
    }
    
    public static boolean between(double num, double d, double e, boolean inclusive) {
// inclusive means that numbers can be = to the min and max
        if (inclusive) {
            if (d <= num && num <= e) {
                return true;
            }
            return false;
        } else {
            if (d < num && num < e) {
                return true;
            }
            return false;
        }
    }
    
    public static boolean isNum(String s) {
        try {
            Double.parseDouble(s);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
    
    public static Integer intToColor(Integer i) {
        if (i == -4)    return 0xaa0000;
        if (i == -3)    return 0xff3333;
        if (i == -2)    return 0xff7700;
        if (i == -1)    return 0xffff00;
        if (i ==  0)    return 0xffffff;
        if (i ==  1)    return 0x00ffff;
        if (i ==  2)    return 0x0000ff;
        if (i ==  3)    return 0xff00ff;
        if (i ==  4)    return 0x770077;
        return null;
    }
    
    public static Double pointsToRep(Integer i) {
        DecimalFormat twoDecForm = new DecimalFormat("#.##");
        Double r = Double.valueOf(twoDecForm.format((double)i/1000));
        
        return r;
    }
    
    public static Integer repToPoints(Double d) {
        return Integer.valueOf((int) (d*1000));
    }
    
    public static Integer getKillPoints(Integer k, Integer d, boolean atk) {
        String s = "10,10,10,10,10,10,10,10,10";
        
        if (atk == true) {
            if (k == -4) { s = Config.repAtkDem; }
            if (k == -3) { s = Config.repAtkRed; }
            if (k == -2) { s = Config.repAtkOra; }
            if (k == -1) { s = Config.repAtkYel; }
            if (k ==  0) { s = Config.repAtkWhi; }
            if (k ==  1) { s = Config.repAtkLtB; }
            if (k ==  2) { s = Config.repAtkBlu; }
            if (k ==  3) { s = Config.repAtkPur; }
            if (k ==  4) { s = Config.repAtkHer; }
        } else {
            if (k == -4) { s = Config.repDefDem; }
            if (k == -3) { s = Config.repDefRed; }
            if (k == -2) { s = Config.repDefOra; }
            if (k == -1) { s = Config.repDefYel; }
            if (k ==  0) { s = Config.repDefWhi; }
            if (k ==  1) { s = Config.repDefLtB; }
            if (k ==  2) { s = Config.repDefBlu; }
            if (k ==  3) { s = Config.repDefPur; }
            if (k ==  4) { s = Config.repDefHer; }
        }
        
        s = s.replaceAll(" ", "");
        String[] spl = s.split(",");
        
        return (Integer.valueOf(spl[d+4]));
    }
    
    public static Double setDec(Double d, Integer n) {
        String s = "";
        for (int i = 0; i < n; i++) { s+= "#"; }
        
        DecimalFormat decForm = new DecimalFormat("#." + s);
        Double r = Double.valueOf(decForm.format(d));
        
        return r;
    }
}
