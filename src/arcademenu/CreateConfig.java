/*
 * Console.java
 * Copyright (C) 2002  Gerold Boehler gboehler@ntb.ch
 *
 * This file is part of ArcadeMenu.
 *
 * ArcadeMenu is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * ArcadeMenu is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with ArcadeMenu; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 * Created with metapad / http://liquidninja.com/metapad/
 *
 */

package arcademenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class CreateConfig extends JFrame {

    private static final int W = 600, H = 600;  // Width, Height
    private static final String TITLE = "Create Frontend Config File";
    private static final boolean open = true, save = false;
    private boolean edited  = false;
    private String[] fonts;
    private String[] fontsize = {"2","4","6","8","10","12","14","16","18","20", "22","24","26","28","30"};
    private ConfigFile cfgFile;
    private JCheckBox sound, scale, shutdown, monpower;
    private JButton basedirbutton;

    private static final String basedirtext = "Base directory is: ";
    private static JLabel fontText = new JLabel("Font: ");

    public CreateConfig() {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
        catch (Exception e) { }
        cfgFile = new ConfigFile();
        setSize(new Dimension(W, H));
        setName("Create Config file");
        int x = Toolkit.getDefaultToolkit().getScreenSize().width;
        int y = Toolkit.getDefaultToolkit().getScreenSize().height;
        setLocation(x/2-getWidth()/2, y/2-getHeight()/2);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) { closeApp(true); }
        });

        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        setJMenuBar(createMenus());
        getContentPane().add(createPanel());
        setTitle(TITLE);
        show();
    }

    protected void closeApp(boolean exit) {
        int res;
        if(edited) {
            res = JOptionPane.showConfirmDialog(null,
                    "Wollen Sie die �nderungen speichern?",
                    TITLE, JOptionPane.YES_NO_CANCEL_OPTION);

            switch(res) {
                case JOptionPane.YES_OPTION:
                    saveFile();
                    break;
                case JOptionPane.NO_OPTION:
                    if (exit) System.exit(0);
                    break;
                case JOptionPane.CANCEL_OPTION:
                    return;
            }
        }
        if(exit) System.exit(0);
    }

    protected void openFile() {
        edited = new Chooser(open,edited) {
            public void onAprove() {
                try {
                    FileInputStream in = new FileInputStream(path);
                    ObjectInputStream s = new ObjectInputStream(in);
                    cfgFile = (ConfigFile)s.readObject();
                    s.close();
                    in.close();
                } catch(Exception ex) {
                    showMessage(path + " is not a config file");
                };

                // Initialize UI
                sound.setSelected(cfgFile.soundEnabled);
                monpower.setSelected(cfgFile.monitorHaltEnabled);
                scale.setSelected(cfgFile.fitQuad);
                shutdown.setSelected(cfgFile.systemHaltEnabled);
                basedirbutton.setText(basedirtext + cfgFile.baseDir);
            }
        }.start(CreateConfig.this);
    }

    protected void saveFile() {
        edited = new Chooser(save, edited) {
            public void onAprove() {

                cfgFile.consoles = new List();
                cfgFile.lptPort = "LPT0";
                cfgFile.dataLine = 1;

                cfgFile.keyUp = 38;
                cfgFile.keyDown = 40;
                cfgFile.keyLeft = 37;
                cfgFile.keyRight = 39;
                cfgFile.keySelect[0] = KeyEvent.VK_CONTROL;
                cfgFile.keySelect[1] = KeyEvent.VK_CONTROL;
                cfgFile.keySelect[2] = KeyEvent.VK_CONTROL;
                cfgFile.keySelect[3] = KeyEvent.VK_CONTROL;
                cfgFile.keyBack[0] = KeyEvent.VK_SHIFT;
                cfgFile.keyBack[1] = KeyEvent.VK_SHIFT;
                cfgFile.keyBack[2] = KeyEvent.VK_SHIFT;
                cfgFile.keyBack[3] = KeyEvent.VK_SHIFT;

                cfgFile.keyExit = KeyEvent.VK_BACK_SPACE;
                cfgFile.keyCoin = KeyEvent.VK_6;

                cfgFile.logoFormat = ".jpg";
                cfgFile.nOfCoins = 0;
                cfgFile.images = "/Arcade/images/";
                cfgFile.mp3dir = "/Arcade/sound/";
                cfgFile.gap = 5;
                cfgFile.rounded = 150;
                cfgFile.rows = 2;
                cfgFile.cols = 3;
                cfgFile.resx = 640;
                cfgFile.font = new Font("Neverwinter", Font.BOLD, 30);
                /****************************************************************************/
                String binary, params, logos, roms, romfile, name, keywiz;
                String genres[] = new String[] { "Jump'n Run", "Action", "Beat'em Up", "Strategy", "RPG & Adventure", "Shooter", "Puzzle", "Sports", "Simulation"};
/*
		name = "Nintendo 64";
		binary = "/Arcade/emus/n64/Project64/Project64.exe";
		params = "";
		logos = "/Arcade/images/n64/";
		roms =  "/Arcade/roms/n64/";
		romfile = "/Arcade/romfiles/n64.dat";
	    keywiz = "x";

		cfgFile.consoles.put( new Console( name, roms, romfile, binary + " " + params, logos, keywiz));
		for(int i = 0; i < genres.length; i++) { ((Console)cfgFile.consoles.get(name)).insertGenre(new Genre(genres[i]));}


		name = "PC";
		binary = "";
		params = "";
		logos = "/Arcade/images/pc/";
		roms =  "";
		romfile = "/Arcade/romfiles/pc.dat";
		keywiz = "x";

		cfgFile.consoles.put( new Console( name, roms, romfile, binary + " " + params, logos, keywiz));
		for(int i = 0; i < genres.length; i++) { ((Console)cfgFile.consoles.get(name)).insertGenre(new Genre(genres[i]));}
*/
                name = "Mame";
                binary = "/Arcade/emus/mame/mame64.exe";
                params = "";
                logos = "/Arcade/images/mame/";
                roms =  "/Arcade/roms/mame/";
                romfile = "/Arcade/romfiles/mame.dat";
                keywiz = "e";

                cfgFile.consoles.put( new Console( name, roms, romfile, binary + " " + params, logos, keywiz));
                for(int i = 0; i < genres.length; i++) { ((Console)cfgFile.consoles.get(name)).insertGenre(new Genre(genres[i]));}

                name = "CPS 1";
                binary = "/Arcade/emus/mame/mame64.exe";
                params = "";
                logos = "/Arcade/images/cps1/";
                roms =  "/Arcade/roms/mame/";
                romfile = "/Arcade/romfiles/cps1.dat";
                keywiz = "e";

                cfgFile.consoles.put( new Console( name, roms, romfile, binary + " " + params, logos, keywiz));
                for(int i = 0; i < genres.length; i++) { ((Console)cfgFile.consoles.get(name)).insertGenre(new Genre(genres[i]));}

                name = "CPS 2";
                binary = "/Arcade/emus/mame/mame64.exe";
                params = "";
                logos = "/Arcade/images/cps2/";
                roms =  "/Arcade/roms/mame/";
                romfile = "/Arcade/romfiles/cps2.dat";
                keywiz = "e";

                cfgFile.consoles.put( new Console( name, roms, romfile, binary + " " + params, logos, keywiz));
                for(int i = 0; i < genres.length; i++) { ((Console)cfgFile.consoles.get(name)).insertGenre(new Genre(genres[i]));}

                name = "NeoGeo";
                binary = "/Arcade/emus/mame/mame64.exe";
                params = "";
                logos =  "/Arcade/images/ng/";
                roms =  "/Arcade/roms/mame/";
                romfile = "/Arcade/romfiles/ng.dat";
                keywiz = "e";

                cfgFile.consoles.put( new Console( name, roms, romfile, binary + " " + params, logos, keywiz));
                for(int i = 0; i < genres.length; i++) { ((Console)cfgFile.consoles.get(name)).insertGenre(new Genre(genres[i]));}

                name = "NES";
                binary = "/Arcade/emus/fceux-2.2.3-win32/fceux.exe";
                params = "";
                roms =  "/Arcade/roms/nes/";
                logos = "/Arcade/images/nes/";
                romfile = "/Arcade/romfiles/nes.dat";
                keywiz = "a";

                cfgFile.consoles.put( new Console( name, roms, romfile, binary + " " + params, logos, keywiz));
                for(int i = 0; i < genres.length; i++) { ((Console)cfgFile.consoles.get(name)).insertGenre(new Genre(genres[i]));}

                name = "SNES";
                binary = "/Arcade/emus/snes9x-1.57-win32-x64/snes9x-x64.exe";
                params = "-fullscreen";
                roms = "c:\\Arcade\\roms\\snes\\";
                logos = "/Arcade/images/snes/";
                romfile = "/Arcade/romfiles/snes.dat";
                keywiz = "d";

                cfgFile.consoles.put( new Console( name, roms, romfile, binary + " " + params, logos, keywiz));
                for(int i = 0; i < genres.length; i++) { ((Console)cfgFile.consoles.get(name)).insertGenre(new Genre(genres[i]));}

                name = "Genesis";
                binary = "/Arcade/emus/Fusion364/Fusion.exe";
                params = "";
                roms =  "/Arcade/roms/genesis/";
                logos = "/Arcade/images/genesis/";
                romfile = "/Arcade/romfiles/genesis.dat";
                keywiz = "b";

                cfgFile.consoles.put( new Console( name, roms, romfile, binary + " " + params, logos, keywiz));
                for(int i = 0; i < genres.length; i++) { ((Console)cfgFile.consoles.get(name)).insertGenre(new Genre(genres[i]));}






/*

		name = "Laser Disc";
		binary = "/Arcade/emus/ldisc/daphne0.99.6/daphne.exe";
		params = "";
		logos = "/Arcade/images/ldisc/";
		roms = "";
		romfile = "/Arcade/romfiles/ldisc.dat";
	    keywiz = "x";

		cfgFile.consoles.put( new Console( name, roms, romfile, binary + " " + params, logos, keywiz));
		for(int i = 0; i < genres.length; i++) { ((Console)cfgFile.consoles.get(name)).insertGenre(new Genre(genres[i]));}


		name = "Master System";
		binary = "/Arcade/emus/ms/dega109/dega.exe";
		params = "";
		roms =  "/Arcade/roms/ms/";
		logos = "/Arcade/images/ms/";
		romfile = "/Arcade/romfiles/ms.dat";
	    keywiz = "c";

		cfgFile.consoles.put( new Console( name, roms, romfile, binary + " " + params, logos, keywiz));
		for(int i = 0; i < genres.length; i++) { ((Console)cfgFile.consoles.get(name)).insertGenre(new Genre(genres[i]));}

		name = "TurboGrafx";
		binary = "/Arcade/emus/turbografx/me099-english-beta5/pce.exe";
		params = "";
		roms =  "/Arcade/roms/turbografx/";
		logos = "/Arcade/images/turbografx/";
		romfile = "/Arcade/romfiles/turbografx.dat";
	    keywiz = "x";

		cfgFile.consoles.put( new Console( name, roms, romfile, binary + " " + params, logos, keywiz));
		for(int i = 0; i < genres.length; i++) { ((Console)cfgFile.consoles.get(name)).insertGenre(new Genre(genres[i]));}

		name = "ZN1 ZN2 NS11";
		binary = "/Arcade/emus/zn1zn2ns11/zinc/zinc.exe";
		params ="";
		roms ="";
		logos = "/Arcade/images/zn1zn2ns11/";
		romfile = "/Arcade/romfiles/zn1zn2ns11.dat";
	    keywiz = "x";

		cfgFile.consoles.put( new Console( name, roms, romfile, binary + " " + params, logos, keywiz));
		for(int i = 0; i < genres.length; i++) { ((Console)cfgFile.consoles.get(name)).insertGenre(new Genre(genres[i]));}

		name = "System X";
	    binary = "/Arcade/emus/mame/mame.exe";
		params = "";
		roms =  "/Arcade/roms/systemx/";
		logos = "/Arcade/images/systemx/";
		romfile = "/Arcade/romfiles/systemx.dat";
	    keywiz = "e";

		cfgFile.consoles.put( new Console( name, roms, romfile, binary + " " + params, logos, keywiz));
		for(int i = 0; i < genres.length; i++) { ((Console)cfgFile.consoles.get(name)).insertGenre(new Genre(genres[i]));}

		name = "System 32";
		binary = "/Arcade/emus/system32/Modeler093a/modeler.exe";
		params = "";
		roms =  "/Arcade/roms/system32/";
		logos = "/Arcade/images/system32/";
		romfile = "/Arcade/romfiles/system32.dat";
	    keywiz = "x";

		cfgFile.consoles.put( new Console( name, roms, romfile, binary + " " + params, logos, keywiz));
		for(int i = 0; i < genres.length; i++) { ((Console)cfgFile.consoles.get(name)).insertGenre(new Genre(genres[i]));}

		name = "Ultra 64";
		binary = "/Arcade/emus/mame/mame.exe";
		params = "";
		roms =  "/Arcade/roms/u64/";
		logos = "/Arcade/images/u64/";
		romfile = "/Arcade/romfiles/u64.dat";
	    keywiz = "x";

		cfgFile.consoles.put( new Console( name, roms, romfile, binary + " " + params, logos, keywiz));
		for(int i = 0; i < genres.length; i++) { ((Console)cfgFile.consoles.get(name)).insertGenre(new Genre(genres[i]));}

		name = "Playstation";
		binary = "/Arcade/emus/psx/epsxe160/epsxe.exe";
		params = "-nogui -loadiso";
		logos = "/Arcade/images/psx/";
		roms = "/Arcade/roms/psx/";
		romfile = "/Arcade/romfiles/psx.dat";
	    keywiz = "x";

		cfgFile.consoles.put( new Console( name, roms, romfile, binary + " " + params, logos, keywiz));
		for(int i = 0; i < genres.length; i++) { ((Console)cfgFile.consoles.get(name)).insertGenre(new Genre(genres[i]));}

*/


                try {
                    FileOutputStream out = new FileOutputStream(path);
                    ObjectOutputStream s = new ObjectOutputStream(out);
                    s.writeObject(cfgFile);
                    s.flush();
                    out.close();
                    s.close();
                } catch(Exception ex) { ex.printStackTrace(); }
            }
        }.start(CreateConfig.this);
    }

    protected JScrollPane createPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.setPreferredSize(new Dimension(W-50, H-100));

        Font[] f = GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();
        fonts = new String[f.length];
        for(int i = 0; i < fonts.length; i++) {
            fonts[i] = f[i].getName();
        }

        sound = new JCheckBox("Sound");
        sound.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                edited = true;
                cfgFile.soundEnabled = !cfgFile.soundEnabled;
            }
        });
        sound.setSelected(cfgFile.soundEnabled);
        sound.setToolTipText("enable / disable Sound");
        panel.add(sound);

        scale = new JCheckBox("Scale");
        scale.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                edited = true;
                cfgFile.fitQuad = !cfgFile.fitQuad;
            }
        });
        scale.setSelected(cfgFile.fitQuad);
        scale.setToolTipText("scale / dont't scale logos");
        panel.add(scale);

        shutdown = new JCheckBox("Shutdown");
        shutdown.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                edited = true;
                cfgFile.systemHaltEnabled = !cfgFile.systemHaltEnabled;
            }
        });
        shutdown.setSelected(cfgFile.systemHaltEnabled);
        shutdown.setToolTipText("halt system on exit");
        panel.add(shutdown);

        monpower = new JCheckBox("Monitorpower");
        monpower.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                edited = true;
                cfgFile.monitorHaltEnabled = !cfgFile.monitorHaltEnabled;
            }
        });
        monpower.setSelected(cfgFile.monitorHaltEnabled);
        monpower.setToolTipText("shutdown monitor on exit");
        panel.add(monpower);

        basedirbutton = new JButton(basedirtext);
        basedirbutton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                edited = new Chooser(open, JFileChooser.DIRECTORIES_ONLY, false, edited) {
                    public void onAprove() { cfgFile.baseDir = path; }
                }.start(CreateConfig.this);
                if(cfgFile.baseDir != null)
                    basedirbutton.setText(basedirtext + cfgFile.baseDir);
            }
        });
        basedirbutton.setPreferredSize(new Dimension(W-70, 30));
        panel.add(basedirbutton);

        JScrollPane sp = new JScrollPane(panel);
        sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        return sp;
    }

    protected JMenuBar createMenus() {
        JMenuBar menuBar;
        JMenu menu;
        JMenuItem menuItem;
        ButtonGroup group;

        menuBar = new JMenuBar();

        menu = new JMenu("File", true);
        menu.setMnemonic('f');

        group = new ButtonGroup();

        menuItem = new JMenuItem("Open");
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                closeApp(false);
                openFile();
            }
        });
        menuItem.setMnemonic('o');
        group.add(menuItem);
        menu.add(menuItem);

        menuItem = new JMenuItem("Save");
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveFile();
            }
        });
        menuItem.setMnemonic('s');
        group.add(menuItem);
        menu.add(menuItem);

        menu.addSeparator();

        menuItem = new JMenuItem("Exit");
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                closeApp(true);
            }
        });
        menuItem.setMnemonic('x');
        group.add(menuItem);
        menu.add(menuItem);

        menuBar.add(menu);

        menu = new JMenu("Help", true);
        menu.setMnemonic('h');
        group = new ButtonGroup();

        menuItem = new JMenuItem("About");
        menuItem = new JMenuItem("About");
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showConfirmDialog(null, "[c] gboehler@cable.vol.at",
                        "Create Config", JOptionPane.PLAIN_MESSAGE);
            }
        });

        menuItem.setMnemonic('i');
        group.add(menuItem);
        menu.add(menuItem);

        menuBar.add(menu);

        return menuBar;
    }

    public static void main(String[] args) {
        new CreateConfig();
    }
}

