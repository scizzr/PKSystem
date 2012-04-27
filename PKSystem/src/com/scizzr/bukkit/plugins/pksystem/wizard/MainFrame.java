package com.scizzr.bukkit.plugins.pksystem.wizard;

import com.scizzr.bukkit.plugins.pksystem.Main;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import org.bukkit.configuration.file.YamlConfiguration;

public class MainFrame extends JFrame {
    private static final long serialVersionUID = 1L;
    
    PanelMain panelMain;
    JButton buttSave;
    
    public static YamlConfiguration config = new YamlConfiguration();
    
    public static File fileFolder = new File("PKSystem" + Main.slash);
    public static File fileConfigMain = new File("PKSystem" + Main.slash + "configMain.yml");
    public static File fileConfigEff = new File("PKSystem" + Main.slash + "configEffects.yml");
    public static File fileConfigRep = new File("PKSystem" + Main.slash + "configReputation.yml");
    public static File fileConfigTomb = new File("PKSystem" + Main.slash + "configTomb.yml");

    @SuppressWarnings("unused")
    public static void main(String[] args) {
/*
        msg("Configuration GUI is in development. Please be patient.");
        System.exit(0);

        File cd = new File("");
        String dir = cd.getAbsolutePath().split("\\" + Main.slash)[(cd.getAbsolutePath().split("\\" + Main.slash).length - 1)];
        
        if (!dir.equalsIgnoreCase("plugins")) { msg("You must put this plugin in the plugins folder to configure it properly."); System.exit(0); }

        if (!fileFolder.exists())
            try { fileFolder.mkdir(); } catch (Exception ex) { msg("Could not create config folder.\n" + fileConfigMain.getAbsoluteFile()); System.exit(0); }

        if (!fileConfigMain.exists())
            try { fileConfigMain.createNewFile(); } catch (Exception ex) { msg("Could not create config file.\n" + fileConfigMain.getAbsoluteFile()); System.exit(0); }

        if (!fileConfigEff.exists())
            try { fileConfigEff.createNewFile(); } catch (Exception ex) { msg("Could not create config file.\n" + fileConfigEff.getAbsoluteFile()); System.exit(0); }

        if (!fileConfigRep.exists())
            try { fileConfigRep.createNewFile(); } catch (Exception ex) { msg("Could not create config file.\n" + fileConfigRep.getAbsoluteFile()); System.exit(0); }

        if (!fileConfigTomb.exists())
            try { fileConfigTomb.createNewFile(); } catch (Exception ex) { msg("Could not create config file.\n" + fileConfigTomb.getAbsoluteFile()); System.exit(0); }
*/
        MainFrame mf = new MainFrame();
    }

    public MainFrame() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                MainFrame.this.panelMain = new PanelMain();

                MainFrame.this.setTitle("PKSystem Configuration");
                MainFrame.this.setDefaultCloseOperation(3);
                MainFrame.this.setSize(1000, 720);

                MainFrame.this.setLocationRelativeTo(null);
                MainFrame.this.setVisible(true);

                GridBagConstraints gbc = new GridBagConstraints();
                MainFrame.this.setLayout(new GridBagLayout());

                JButton save = new JButton("Save settings");

                gbc.weightx = 0.25D;
                gbc.weighty = 1.0D;

                gbc.gridy = 0;
                gbc.gridx = 0;
                MainFrame.this.add(MainFrame.this.panelMain, gbc);

                save.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent arg0) {
                        JOptionPane.showMessageDialog(null, "asdf");
                    }
                });
            }
        });
    }

    public static Integer getIndex(String s) {
        if (s.equalsIgnoreCase("true")) { return Integer.valueOf(0); }
        if (s.equalsIgnoreCase("false")) { return Integer.valueOf(1); }
        if (s.equalsIgnoreCase("click")) { return Integer.valueOf(0); }
        if (s.equalsIgnoreCase("break")) { return Integer.valueOf(1); }

        return Integer.valueOf(0);
    }

    public static void msg(String s) {
        JOptionPane.showMessageDialog(null, s);
    }
}
