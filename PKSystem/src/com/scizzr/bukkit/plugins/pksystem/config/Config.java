package com.scizzr.bukkit.plugins.pksystem.config;

import com.scizzr.bukkit.plugins.pksystem.util.UniqID;

public class Config {
// Config - Main
    public static boolean genPrefix = true;
    public static boolean genStats = true;
    public static String  genUniqID = UniqID.getUniqID();
    public static boolean genVerCheck = true;
    public static boolean genAutoUpdate = false;
    public static boolean genErrorWeb = true;
    public static boolean fmtCombEnabled = false;
    public static String  fmtCombEnter = "You entered combat";
    public static String  fmtCombExit = "You exited combat";
    public static boolean fmtDeathEnabled = false;
    public static String  fmtDeathGood = "+a killed +d";
    public static String  fmtDeathEvil = "+a murdered +d";
    public static String  fmtPKEnter = "You are now in PK mode";
    public static String  fmtPKExit = "You have left PK mode";
    public static String  fmtPtsNoRew = "You earned no reputation for killing +d.";
    public static boolean fmtDispName = false;
    public static boolean fmtTabList = false;
    public static boolean killMobsEnabled = false;
    public static Integer killMobsPoints = 1;
    public static boolean killPkOff = false;
    public static boolean permAllowOps = true;
    public static Integer combDuration = 30;
    public static boolean combNoobEnabled = false;
    public static Integer combNoobLevel = 5;
    public static boolean combNoTP = false;
    public static boolean combPkOnly = false;
    public static boolean combSpawnEnabled = false;
    public static Integer combSpawnDuration = 5;
    
// Config - Effects
    public static boolean effInvisEnabled = false;
    public static Integer effInvisMin = 3;
    public static Integer effInvisMax = 16;
    public static boolean effLightEnabled = false;
    public static boolean effPotsEnabled = false;
    public static boolean effSmokeCombat = false;
    public static boolean effSmokeInPK = false;
    public static boolean effSmokeInvis = false;
    public static boolean effSpecHero = false;
    public static boolean effSpecDemon = false;
    public static boolean effSpecNeutral = false;
    public static boolean effNameplates = false;
    
// Config - Tombstones
    public static boolean tombEnabled = false;
    public static boolean tombLocked = true;
    public static String  behClaim = "break";
    public static boolean behPreserve = false;
    
// Config - Reputation
    public static String  repGuide  = " dem, red, ora, yel, whi, ltb, blu, pur, her";
    public static String  repAtkDem = "   0,   0,   0,   0, -10, -15, -20, -25, -40";
    public static String  repAtkRed = "   0,   0,   0,   0, -10, -15, -20, -25, -40";
    public static String  repAtkOra = "   0,   0,   0,   0, -10, -15, -20, -25, -40";
    public static String  repAtkYel = "   0,   0,   0,   0, -10, -15, -20, -25, -40";
    public static String  repAtkWhi = "  10,   3,   2,   1, -10, -15, -20, -25, -40";
    public static String  repAtkLtB = "  10,   3,   2,   1, -10, -15, -20, -25, -40";
    public static String  repAtkBlu = "  10,   3,   2,   1, -10, -15, -20, -25, -40";
    public static String  repAtkPur = "  10,   3,   2,   1, -10, -15, -20, -25, -40";
    public static String  repAtkHer = "  10,   3,   2,   1, -10, -15, -20, -25,   0";
    public static String  repDefDem = "   0,   0,   0,   0,   0,   0,   0,   0,   0";
    public static String  repDefRed = "   0,   0,   0,   0,   0,   0,   0,   0,   0";
    public static String  repDefOra = "   0,   0,   0,   0,   0,   0,   0,   0,   0";
    public static String  repDefYel = "   0,   0,   0,   0,   0,   0,   0,   0,   0";
    public static String  repDefWhi = "   0,   0,   0,   0,   0,   0,   0,   0,   0";
    public static String  repDefLtB = "  -4,  -4,  -4,  -4,   0,   0,   0,   0,   0";
    public static String  repDefBlu = "  -6,  -6,  -6,  -6,   0,   0,   0,   0,   0";
    public static String  repDefPur = "  -8,  -8,  -8,  -8,   0,   0,   0,   0,   0";
    public static String  repDefHer = " -10, -10, -10, -10,   0,   0,   0,   0,   0";
    public static boolean repSpecEnabled = false;
    public static boolean repSpecReach = false;
    public static boolean repLimitEnabled = false;
    public static Integer repLimitAmount = 5;
    public static Integer repLimitDuration = 30;
}
