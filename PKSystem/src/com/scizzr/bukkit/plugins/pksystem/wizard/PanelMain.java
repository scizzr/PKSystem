package com.scizzr.bukkit.plugins.pksystem.wizard;

import com.avaje.ebeaninternal.server.core.JndiDataSourceLookup;
import com.scizzr.bukkit.plugins.pksystem.config.Config;
import com.scizzr.bukkit.plugins.pksystem.util.MoreMath;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import org.bukkit.configuration.file.YamlConfiguration;

public class PanelMain extends JPanel {
    private static final long serialVersionUID = 1L;

    public PanelMain() {
        Dimension size = getPreferredSize();
        size.width = 250;
        size.height = 700;
        setPreferredSize(size);

        setBorder(new TitledBorder(
                BorderFactory.createTitledBorder("MAIN CONFIG")));

        String[] sTrueFalse = { "True", "False" };

        JLabel lblGenPreEnab = new JLabel("Prefix");
        JLabel lblGebStatsEnab = new JLabel("Stats");
        JLabel lblGenVerEnab = new JLabel("VerCheck");
        JLabel lblFmtCombEnab = new JLabel("FmtCombat");
        JLabel lblFmtCombEnt = new JLabel("CombatEnter");
        JLabel lblFmtCombExit = new JLabel("CombatExit");
        JLabel lblFmtDeathEnab = new JLabel("FmtDeath");
        JLabel lblFmtDeathGood = new JLabel("DeathGood");
        JLabel lblFmtDeathEvil = new JLabel("DeathEvil");
        JLabel lblFmtPKEnab = new JLabel("FmtPK");
        JLabel lblFmtPKEnter = new JLabel("PKEnter");
        JLabel lblFmtPKExit = new JLabel("PKExit");
        JLabel lblFmtDispEnab = new JLabel("FmtDisplay");
        JLabel lblFmtTabEnab = new JLabel("FmtTablist");
        JLabel lblKillPKoffEnab = new JLabel("KillPKOff");
        JLabel lblKillMobsEnab = new JLabel("KillMobs");
        JLabel lblKillMobsPts = new JLabel("KillMobsPts");
        JLabel lblPermOpsEnab = new JLabel("AllowOps");
        JLabel lblCombDur = new JLabel("CombatDur");
        JLabel lblCombNoobEnab = new JLabel("CombatNoobs");
        JLabel lblCombNoobLvl = new JLabel("CombatLevel");
        JLabel lblCombNoTP = new JLabel("CombatNoTP");
        JLabel lblCombPKOnly = new JLabel("CombatPKOnly");

        final JComboBox cmbGenPreEnab = new JComboBox(sTrueFalse); cmbGenPreEnab.setSelectedIndex(MainFrame.getIndex(String.valueOf(Config.genPrefix)).intValue());
        final JComboBox cmbGebStatsEnab = new JComboBox(sTrueFalse);
        final JComboBox cmbGenVerEnab = new JComboBox(sTrueFalse);
        final JComboBox cmbFmtCombEnab = new JComboBox(sTrueFalse);
        final JTextField tfFmtCombEnt = new JTextField(10);
        final JTextField tfFmtCombExit = new JTextField(50);
        final JComboBox cmbFmtDeathEnab = new JComboBox(sTrueFalse);
        final JTextField tfFmtDeathGood = new JTextField(50);
        final JTextField tfFmtDeathEvil = new JTextField(50);
        final JComboBox cmbFmtPKEnab = new JComboBox(sTrueFalse);
        final JTextField tfFmtPKEnter = new JTextField(50);
        final JTextField tfFmtPKExit = new JTextField(50);
        final JComboBox cmbFmtDispEnab = new JComboBox(sTrueFalse);
        final JComboBox cmbFmtTabEnab = new JComboBox(sTrueFalse);
        final JComboBox cmbKillPKoffEnab = new JComboBox(sTrueFalse);
        final JComboBox cmbKillMobsEnab = new JComboBox(sTrueFalse);
        final JTextField tfKillMobsPts = new JTextField(50);
        final JComboBox cmbPermOpsEnab = new JComboBox(sTrueFalse);
        final JTextField tfCombDur = new JTextField(50);
        final JComboBox cmbCombNoobEnab = new JComboBox(sTrueFalse);
        final JTextField tfCombNoobLvl = new JTextField(50);
        final JComboBox cmbCombNoTP = new JComboBox(sTrueFalse);
        final JComboBox cmbCombPKOnly = new JComboBox(sTrueFalse);
        
        JButton saveMain = new JButton("Save main config");
        
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        gbc.insets = new Insets(2, 2, 2, 2);
        
        gbc.anchor = GridBagConstraints.LINE_END;
        
        gbc.gridx = 0;
            gbc.gridy =  1; add(lblGenPreEnab, gbc);
            gbc.gridy =  2; add(lblGebStatsEnab, gbc);
            gbc.gridy =  3; add(lblGenVerEnab, gbc);
            gbc.gridy =  5; add(lblFmtCombEnab, gbc);
            gbc.gridy =  6; add(lblFmtCombEnt, gbc);
            gbc.gridy =  7; add(lblFmtCombExit, gbc);
            gbc.gridy =  8; add(lblFmtDeathEnab, gbc);
            gbc.gridy =  9; add(lblFmtDeathGood, gbc);
            gbc.gridy = 10; add(lblFmtDeathEvil, gbc);
            gbc.gridy = 11; add(lblFmtPKEnab, gbc);
            gbc.gridy = 12; add(lblFmtPKEnter, gbc);
            gbc.gridy = 13; add(lblFmtPKExit, gbc);
            gbc.gridy = 14; add(lblFmtDispEnab, gbc);
            gbc.gridy = 15; add(lblFmtTabEnab, gbc);
            gbc.gridy = 17; add(lblKillPKoffEnab, gbc);
            gbc.gridy = 18; add(lblKillMobsEnab, gbc);
            gbc.gridy = 19; add(lblKillMobsPts, gbc);
            gbc.gridy = 21; add(lblPermOpsEnab, gbc);
            gbc.gridy = 23; add(lblCombDur, gbc);
            gbc.gridy = 24; add(lblCombNoobEnab, gbc);
            gbc.gridy = 25; add(lblCombNoobLvl, gbc);
            gbc.gridy = 26; add(lblCombNoTP, gbc);
            gbc.gridy = 27; add(lblCombPKOnly, gbc);
        
        
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 1;
            gbc.gridy =  1; add(cmbGenPreEnab, gbc);
            gbc.gridy =  2; add(cmbGebStatsEnab, gbc);
            gbc.gridy =  3; add(cmbGenVerEnab, gbc);
            gbc.gridy =  5; add(cmbFmtCombEnab, gbc);
            gbc.gridy =  6; add(tfFmtCombEnt, gbc);
            gbc.gridy =  7; add(tfFmtCombExit, gbc);
            gbc.gridy =  8; add(cmbFmtDeathEnab, gbc);
            gbc.gridy =  9; add(tfFmtDeathGood, gbc);
            gbc.gridy = 10; add(tfFmtDeathEvil, gbc);
            gbc.gridy = 11; add(cmbFmtPKEnab, gbc);
            gbc.gridy = 12; add(tfFmtPKEnter, gbc);
            gbc.gridy = 13; add(tfFmtPKExit, gbc);
            gbc.gridy = 14; add(cmbFmtDispEnab, gbc);
            gbc.gridy = 15; add(cmbFmtTabEnab, gbc);
            gbc.gridy = 17; add(cmbKillPKoffEnab, gbc);
            gbc.gridy = 18; add(cmbKillMobsEnab, gbc);
            gbc.gridy = 19; add(tfKillMobsPts, gbc);
            gbc.gridy = 21; add(cmbPermOpsEnab, gbc);
            gbc.gridy = 23; add(tfCombDur, gbc);
            gbc.gridy = 24; add(cmbCombNoobEnab, gbc);
            gbc.gridy = 25; add(tfCombNoobLvl, gbc);
            gbc.gridy = 26; add(cmbCombNoTP, gbc);
            gbc.gridy = 27; add(cmbCombPKOnly, gbc);
        
        gbc.gridy = 28; add(saveMain, gbc);

        saveMain.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                JOptionPane.showMessageDialog(null, "This functionality is not yet finished :-)");
/*
                YamlConfiguration config = MainFrame.config;
                config.set("general.prefix", Boolean.valueOf(cmbGenPreEnab.getSelectedItem().toString()));
                config.set("general.stats", Boolean.valueOf(cmbGebStatsEnab.getSelectedItem().toString()));
                config.set("general.verchk", Boolean.valueOf(cmbGenVerEnab.getSelectedItem().toString()));
                config.set("format.combat.enabled", Boolean.valueOf(cmbFmtCombEnab.getSelectedItem().toString()));
                config.set("format.combat.enter", String.valueOf(tfFmtCombEnt.getText()));
                config.set("format.combat.exit", String.valueOf(tfFmtCombExit.getText()));
                config.set("format.death.enabled", Boolean.valueOf(cmbFmtDeathEnab.getSelectedItem().toString()));
                config.set("format.death.good", String.valueOf(tfFmtDeathGood.getText()));
                config.set("format.death.evil", String.valueOf(tfFmtDeathEvil.getText()));
                config.set("format.pk.enter", String.valueOf(tfFmtPKEnter.getText()));
                config.set("format.pk.exit", String.valueOf(tfFmtPKExit.getText()));
                config.set("format.displayname", Boolean.valueOf(cmbFmtDispEnab.getSelectedItem().toString()));
                config.set("format.tablist", Boolean.valueOf(cmbFmtTabEnab.getSelectedItem().toString()));
                config.set("kill.pkoff", Boolean.valueOf(cmbKillPKoffEnab.getSelectedItem().toString()));
                config.set("kill.mobs.enabled", Boolean.valueOf(cmbKillMobsEnab.getSelectedItem().toString()));
                if (MoreMath.isNum(tfKillMobsPts.getText())){
                    config.set("kill.mobs.points", Integer.valueOf(tfKillMobsPts.getText()));
                } else {
                    MainFrame.msg("KillMobsPts must be a number");
                }
                config.set("permissions.allowops", Boolean.valueOf(cmbPermOpsEnab.getSelectedItem().toString()));

                if (MoreMath.isNum(tfCombDur.getText())) {
                    config.set("combat.duration", Integer.valueOf(tfCombDur.getText()));
                } else {
                    MainFrame.msg("CombatDur must be a number");
                }
                config.set("combat.noobs.enabled", Boolean.valueOf(cmbCombNoobEnab.getSelectedItem().toString()));
                if (MoreMath.isNum(tfCombNoobLvl.getText())) {
                    config.set("combat.noobs.level", Integer.valueOf(tfCombNoobLvl.getText()));
                } else {
                    MainFrame.msg("CombatLevel must be a number");
                }
                config.set("combat.notp", Boolean.valueOf(cmbCombNoTP.getSelectedItem().toString()));
                config.set("combat.pkonly", Boolean.valueOf(cmbCombPKOnly.getSelectedItem().toString()));
                
                try {
                    config.save(MainFrame.fileConfigMain);
                } catch (Exception ex) {
                    MainFrame.msg("Could not save config file\n" + MainFrame.fileConfigMain.getAbsoluteFile()); System.exit(0);
                }
*/
            }
        });
    }
}
