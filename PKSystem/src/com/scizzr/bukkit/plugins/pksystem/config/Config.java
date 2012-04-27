package com.scizzr.bukkit.plugins.pksystem.config;

public class Config {
// Config - Main
    public static boolean genPrefix = true;
    public static boolean genStats = true;
    public static boolean genVerCheck = true;
    public static boolean fmtCombEnabled = true;
    public static String  fmtCombEnter = "You entered combat";
    public static String  fmtCombExit = "You exited combat";
    public static boolean fmtDeathEnabled = true;
    public static String  fmtDeathGood = "+a killed +d";
    public static String  fmtDeathEvil = "+a murdered +d";
    public static String  fmtPKEnter = "You are now in PK mode";
    public static String  fmtPKExit = "You have left PK mode";
    public static boolean fmtDispName = true;
    public static boolean fmtTabList = true;
    public static boolean killMobsEnabled = true;
    public static Integer killMobsPoints = 1;
    public static boolean killPkOff = true;
    public static boolean permAllowOps = true;
    public static Integer pvpDuration = 30;
    public static boolean pvpNoobEnabled = false;
    public static Integer pvpNoobLevel = 5;
    public static boolean pvpNoTP = true;
    public static boolean pvpPkOnly = true;
    public static boolean repSpecEnabled = true;
    public static boolean repSpecReach = true;
// + TODO
//    public static boolean respawnProt = true;
//    public static Integer respawnDur = 5;
// - TODO
    
// Config - Effects
    public static boolean effInvisEnabled = false;
    public static Integer effInvisMin = 3;
    public static Integer effInvisMax = 16;
    public static boolean effPotsEnabled = true;
    public static boolean effSmokeCombat = true;
    public static boolean effSmokeInPK = true;
    public static boolean effSmokeInvis = true;
    public static boolean effSpecHero = true;
    public static boolean effSpecDemon = true;
    public static boolean effSpecNeutral = true;
    
// Config - Tombstones
    public static boolean tombEnabled = true;
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
}
