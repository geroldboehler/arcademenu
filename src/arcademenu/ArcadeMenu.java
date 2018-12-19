/*
 * ArcadeMenu.java
 * Copyright (C) 2002  Gerold Boehler gboehler@ntb.ch
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 * 
 */

package arcademenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class ArcadeMenu {
	private static ConfigFile cfgFile;
	private static int
		RESY = 0, 
		BUTHEIGHT, 
		BUTPERROW = 3, 
		ICONX, 
		ICONY, 
		coinsinserted;
	public static int TOTALNOFGAMES = 0;
	private static final int POSX = 0, POSY = 0;
	private static final double FACTOR = 0.411667;

	private static String keywiz = "x";
	private static boolean first = true;
	private static Color
		titlec = new Color(255, 23, 23),
		descc = new Color(255, 43, 43), 
		buttonalpha = new Color(0,0,0,220),
		alpha0 = new Color(0, 0, 0, 0)
		;
	private static List consoles = new List();
	private static Console k;
	private static Genre g;
	private static JLabel name, title, titleicon;
	private static JPanel titlepane;
	private static JFrame frame;
	private static Random rgen = new Random();
	private static GridBagLayout gridbag;
	public static GridBagConstraints c;
	private static int[] easterseq;
	private static int easterpos = 0;

	public static List list;
	public static Console curConsole;

	public static void main(String[] args) {
		JFrame.setDefaultLookAndFeelDecorated(false);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception ex) { System.out.println("Error setting look and feel"); }

        try {
            FileInputStream in = new FileInputStream("/arcade/config/arcademenu.conf");
            ObjectInputStream s = new ObjectInputStream(in);
            cfgFile = (ConfigFile)s.readObject();
			consoles = cfgFile.consoles;
		} catch(Exception ex) { System.out.println("Couldn't open config file"); }

		curConsole = (Console)consoles.getFirst();

		while(curConsole != null) {
			ParseRom.parse(curConsole.romfile);
			System.out.println("Parsing " + curConsole.romfile + " done...");
			curConsole = (Console)consoles.getNext();
		}
		
		RESY = (int)(0.75*cfgFile.resx);
		BUTHEIGHT = (int)(RESY * FACTOR);
		ICONX = cfgFile.resx/BUTPERROW-cfgFile.iconBorder;
		ICONY = BUTHEIGHT-cfgFile.iconBorder;
		easterseq = new int[] {
			cfgFile.keyUp, 
			cfgFile.keyDown, 
			cfgFile.keyUp, 
			cfgFile.keyDown, 
			cfgFile.keyLeft, 
			cfgFile.keyRight, 
			cfgFile.keyLeft, 
			cfgFile.keyRight,
			//cfgFile.keyBack,
			//cfgFile.keyBack
		};
		System.out.println("Building Surface...");

		list = consoles;
		buildPane();
		curConsole = (Console)consoles.getFirst();

		while(curConsole != null) {
			k = curConsole;
			list = curConsole.getGenreList();
			buildPane();
			Genre curGenre = curConsole.getFirstGenre();
			while(curGenre != null) {
				g = curGenre;
				list = curGenre.getGameList();
				buildPane();
				curGenre = curConsole.getNextGenre();
			}
			curConsole = (Console)consoles.getNext();
		}

		frame = new JFrame("Acade Menu");
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);


		title = new JLabel("Total Games: " + TOTALNOFGAMES);
		title.setFont(cfgFile.font);
		title.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		title.setForeground(titlec);
		title.setBackground(alpha0);

		name = new JLabel("Current Machine:");
		name.setFont(cfgFile.font);
		name.setBorder(BorderFactory.createEmptyBorder(0,10,10,10));
		name.setForeground(descc);

		File x = new File(cfgFile.images + "titlebkgnd.jpg");
		if(x.exists()) {
			titlepane = new JPanel() {
				public void paintComponent(Graphics g) {
					ImageIcon img = new ImageIcon(cfgFile.images + "titlebkgnd.jpg");
					g.drawImage(img.getImage(), 0, 0, cfgFile.resx, (RESY-2*BUTHEIGHT), null);
					super.paintComponent(g);
				}
			};
		}
		else
			titlepane = new JPanel();

		titlepane.setBackground(alpha0);
		titlepane.setPreferredSize(new Dimension(0, (RESY-2*BUTHEIGHT) ));

		titleicon = new JLabel(new ImageIcon(cfgFile.images + "decobkgnd.gif"));
		titleicon.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 0));
		gridbag = new GridBagLayout();
		c = new GridBagConstraints();
		titlepane.setLayout(gridbag);
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.WEST;
		c.gridwidth = 1;
		c.gridheight = 2;
		c.weightx = 0.5;
		c.weighty = 1.0;
		gridbag.setConstraints(titleicon, c);

		titlepane.add(titleicon);
		c.anchor = GridBagConstraints.EAST;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.gridheight = 1;
		gridbag.setConstraints(title, c);
		titlepane.add(title);
		gridbag.setConstraints(name, c);
		titlepane.add(name);		

		frame.getContentPane().add(titlepane, BorderLayout.NORTH);
		frame.addWindowListener(new WindowAdapter() { public void windowClosing(WindowEvent e) { } });
			frame.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) { }
			public void keyReleased(KeyEvent e) { }
			public void keyTyped(KeyEvent e) { }
		});

		frame.getContentPane().setBackground(Color.BLACK);
		frame.setResizable(false);
		frame.setUndecorated(true);
		frame.setSize(cfgFile.resx,RESY);
		frame.setLocation(POSX,POSY);
		frame.setFocusable(true);
		frame.getContentPane().add(consoles.sp, BorderLayout.CENTER);
		list = consoles;
		frame.setVisible(true);
		switchPane(consoles);
		File t = new File(cfgFile.images + "buttonbkgnd" + cfgFile.logoFormat);

		if(t.exists())
			new Fade().start();
		else {
			titlec = Color.BLACK;
			descc = Color.BLACK;
			alpha0 = Color.RED;
			buttonalpha = Color.BLACK;
		}
		/*
		try
		{
			Runtime.getRuntime().exec("/keywiz/KeyWiz_Uploader.exe /A ", null, new File("/keywiz"));
		}
		catch(Exception ex) { System.out.println("Error running keywiz uploader"); }
		*/
  }

	private static void shutDown() {
		if(!cfgFile.systemHaltEnabled) return;
		try {
			Process p = Runtime.getRuntime().exec("shutdown -s -f -t 0");
			BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
			while(in.readLine() != null) ;
		} catch(Exception ex) { }
	}

	private static boolean switchPane(List to) {
		List prev;
		if(to == null || to.getFirst() == null) return false;
		prev = first ? null : list;
		list = to;

		if(list.getCurrent() instanceof Console) {
			int nr;
			File img;
			nr = (int)Math.abs(rgen.nextDouble()*(new File(cfgFile.images).length()));
			img = new File(cfgFile.images + "decobkgnd" + nr + ".gif");
			
			if(img.exists())
				titleicon = new JLabel(new ImageIcon(cfgFile.images + "decobkgnd" + nr + ".gif"));
			else 
				titleicon = new JLabel(new ImageIcon(cfgFile.images + "decobkgnd.gif"));	
			titleicon.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 0));

			titlepane.remove(0);
			c.anchor = GridBagConstraints.WEST;
			c.gridwidth = 1;
			c.gridheight = 2;
			c.weighty = 1.0;
			gridbag.setConstraints(titleicon, c);
			titlepane.add(titleicon,0);
		}

		else if(list.getCurrent() instanceof Genre) {
			titleicon = new JLabel(new ImageIcon(consoles.get(consoles.pos).icon));
			titleicon.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 0));
			titlepane.remove(0);
			c.anchor = GridBagConstraints.WEST;
			c.gridwidth = 1;
			c.gridheight = 2;
			c.weighty = 1.0;
			gridbag.setConstraints(titleicon, c);
			titlepane.add(titleicon,0);
		}

		frame.getContentPane().setVisible(false);
		frame.getContentPane().remove(1);
		if(first) first = !first;
		else if(prev != null) {
			for(int i = 0; i < prev.nOfItems; i++) {
				if(((JButton)prev.pane.getComponent(i)).getIcon() != null)
					((JButton)prev.pane.getComponent(i)).setIcon(new ImageIcon());

				}
			} 

			ListItem icons = list.getFirst();
			ImageIcon icon = null;
			for(int i = 0; i < list.nOfItems; i++) {
				File tmp;
				if(icons instanceof Game) {
					if(icons.icon.indexOf("/") == 0 && icons.icon.indexOf(" ") > 0) {
						tmp = new File(icons.icon.substring(0, icons.icon.indexOf(" ")) + ".gif");
						if(tmp.exists())
							icon = new ImageIcon(icons.icon.substring(0, icons.icon.indexOf(" ")) + ".gif");
						else						
							icon = new ImageIcon(icons.icon.substring(0, icons.icon.indexOf(" ")) + cfgFile.logoFormat);
					}
					else if(icons.icon.indexOf("/") == 0) {
						tmp = new File(k.logopath + icons.icon.substring(icons.icon.lastIndexOf("/")+1, icons.icon.lastIndexOf(".") ) + ".gif");
						if(tmp.exists())
							icon = new ImageIcon(k.logopath + icons.icon.substring(icons.icon.lastIndexOf("/")+1, icons.icon.lastIndexOf(".") ) + ".gif");
						else
							icon = new ImageIcon(k.logopath + icons.icon.substring(icons.icon.lastIndexOf("/")+1, icons.icon.lastIndexOf(".") ) + cfgFile.logoFormat);
					}
					else if(icons.icon.indexOf(" ") > 0) {
						tmp = new File(icons.icon.substring(0, icons.icon.indexOf(" ")) + cfgFile.logoFormat);
						if(tmp.exists())
							icon = new ImageIcon(icons.icon.substring(0, icons.icon.indexOf(" ")) + ".gif");
						else
							icon = new ImageIcon(icons.icon.substring(0, icons.icon.indexOf(" ")) + cfgFile.logoFormat);
						if(cfgFile.fitQuad)
							icon = new ImageIcon(icon.getImage().getScaledInstance(ICONX,ICONY,0));
					}
					else {
						icon = new ImageIcon(icons.icon);
						if(cfgFile.fitQuad)
							icon = new ImageIcon(icon.getImage().getScaledInstance(ICONX,ICONY,0));
					}
				}
			else {
				icon = new ImageIcon(icons.icon);
				if(cfgFile.fitQuad)
					icon = new ImageIcon(icon.getImage().getScaledInstance(ICONX,ICONY,0));
				}

			while(icon.getImageLoadStatus() == MediaTracker.LOADING);
			if(icon.getImageLoadStatus() == MediaTracker.ERRORED) {
				icons.icon = new String(icons.icon.substring(0, icons.icon.indexOf(".")) + cfgFile.logoFormat);
				icon = new ImageIcon(icons.icon);
				if(cfgFile.fitQuad)
					icon = new ImageIcon(icon.getImage().getScaledInstance(ICONX,ICONY,0));
				while(icon.getImageLoadStatus() == MediaTracker.LOADING);
				if(icon.getImageLoadStatus() == MediaTracker.ERRORED)
					((JButton)list.pane.getComponent(i)).setText(icons.name);
				else
					((JButton)list.pane.getComponent(i)).setIcon(icon); 
		  }
		  else  
				((JButton)list.pane.getComponent(i)).setIcon(icon);

		  icons = list.getNext();
    }

		frame.getContentPane().add( list.sp, BorderLayout.CENTER , 1 );
		frame.getContentPane().setVisible(true);
		list.getFirst();
		list.pane.getComponent(0).requestFocus();
		return true;
	}

	public static void showEasterEgg() {
		name.setText("menu (c) by gboe");

		JButton b = new JButton();
		b.setIcon(new ImageIcon(cfgFile.images + "egg.jpg"));
		b.addKeyListener(new KeyListener() {
			public void keyReleased(KeyEvent e) { }
			public void keyTyped(KeyEvent e) { }
			public void keyPressed(KeyEvent e) { switchPane(list); }
		});

		JPanel p = new JPanel();
		p.setLayout(new FlowLayout());
		p.add(b);
		frame.getContentPane().setVisible(false);
		frame.getContentPane().remove(1);
		frame.getContentPane().add( p, BorderLayout.CENTER , 1 );
		frame.getContentPane().setVisible(true);
		b.requestFocus();		
		easterpos = 0;
	}

	public static void buttonKeyListener(KeyEvent e)  {

		if(easterseq[easterpos] == e.getKeyCode()) easterpos++;
			else easterpos = 0;
		if(easterseq.length == easterpos) { showEasterEgg(); return; }
		if(e.getKeyCode() == cfgFile.keyCoin) { 
			if(cfgFile.nOfCoins != 0) {
				coinsinserted++; 
				if(coinsinserted != 0 && coinsinserted % cfgFile.nOfCoins == 0) name.setText("Thanks: " + (coinsinserted / cfgFile.nOfCoins) + " game(s) left!");
				else if(cfgFile.nOfCoins - coinsinserted > 0) name.setText("Insert " + (cfgFile.nOfCoins - coinsinserted) + " more coin(s)"); 
			}
		}
		else if(e.getKeyCode() == cfgFile.keyUp) { if(list.pos >= BUTPERROW) { list.pos -= BUTPERROW; } }
		else if(e.getKeyCode() == cfgFile.keyLeft) { if(list.pos != 0) { list.pos -= 1; } }
		else if(e.getKeyCode() == cfgFile.keyRight) { if(list.pos < list.nOfItems-1) { list.pos += 1; } }
		else if(e.getKeyCode() == cfgFile.keyExit) { /*monitorOff();*/ shutDown(); System.exit(0); }
		else if(e.getKeyCode() == cfgFile.keyDown) {
			if(list.pos <= list.nOfItems - 1 - BUTPERROW) { list.pos += BUTPERROW; }
			else if(list.pos % BUTPERROW != 0) {
				if(list.pos + 2 == list.nOfItems-1) { list.pos += 2; }
				else if(list.pos + 1 == list.nOfItems-1 && (list.pos+1) % BUTPERROW == 0) { list.pos++; }
			}
		}
		else {
			int keychar = e.getKeyCode();
			int x = 0;
			while(x < cfgFile.keyBack.length && 
				cfgFile.keyBack[x] != keychar) { x++; }
		    if(x < cfgFile.keyBack.length) {

			if(list != null && list.prev != null) {
				switchPane(list.prev);
				if(list.getCurrent() instanceof Console) {
					title.setText("Games Total: " + TOTALNOFGAMES); }
				else if(list.getCurrent() instanceof Genre) {
					title.setText(list.nOfItems + " Genres"); }
				else if(list.getCurrent() instanceof Game) {
					title.setText(list.prev.get(list.prev.pos).name + ": " + list.nOfItems + " Games"); }
					if(list.nOfItems == 1) buttonKeyListener(e);
			}
 		  }
			
		else {

//		keychar = e.getKeyCode();
		x = 0;
		while(x < cfgFile.keySelect.length && 
			cfgFile.keySelect[x] != keychar) { x++; }
		    if(x < cfgFile.keySelect.length) {
			if(list.get(list.pos) instanceof Console) {
				k = ((Console)list.get(list.pos));
				if (switchPane(k.getGenreList())) { 
					list.prev = consoles;
					list.prev.setCurrent(list.prev.pos);
				}
				if(list.prev != null)
					title.setText(list.nOfItems + " Genres");
	
				if(list.nOfItems == 1) buttonKeyListener(e);
			}
			else if(list.getCurrent() instanceof Genre) {
				g = ((Genre)list.get(list.pos));
				if (switchPane(g.getGameList())) {
					list.prev = k.getGenreList();
					list.prev.setCurrent(list.prev.pos);
				}
				if(list.prev != null && list.prev.prev != null) {
					title.setText(list.prev.get(list.prev.pos).name + ": " + list.nOfItems + " Games"); 
				}
			}
			else if(list.getCurrent() instanceof Game) {
				if(cfgFile.nOfCoins != 0) {
					if(coinsinserted < cfgFile.nOfCoins) {
						name.setText("Insert " + (cfgFile.nOfCoins - coinsinserted) + " more coins");
						return;
					}
					else if(coinsinserted == cfgFile.nOfCoins) {
						coinsinserted -= cfgFile.nOfCoins;
					}
					else { coinsinserted -= cfgFile.nOfCoins; }
				}

				list.prev = k.getGenreList();
				String oldtext = name.getText();
				name.setText("Now loading, please wait...");

				Process p = null;
				Game gm = null;
				InputStream ins;

				if(!k.keywiz.equals(keywiz)) 
				{
					keywiz = k.keywiz;
					try 
					{
						p = Runtime.getRuntime().exec("/keywiz/KeyWiz_Uploader.exe /A /P " + k.keywiz, null, new File("/keywiz"));
						ins = p.getInputStream();
						int ch;
						while((ch = ins.read()) != -1);
					} 
					catch(Exception ex) { }
				}

					try {
						gm = (Game)list.get(list.pos);
						p = (k.binary.length() > 1 ?
							Runtime.getRuntime().exec(k.binary + " " + k.rompath + gm.rom, null,
								new File(k.binary.substring(0, k.binary.lastIndexOf("/"))))
							:
							Runtime.getRuntime().exec(((Game)list.get(list.pos)).rom, null,
								new File(((Game)list.get(list.pos)).rom.substring(0, ((Game)list.get(list.pos)).rom.lastIndexOf("/"))))
						);
						ins = p.getInputStream();
						int ch;
						while((ch = ins.read()) != -1);
					} catch(Exception ex) {
					    System.out.println(ex.toString());
        	        }
					Runtime.getRuntime().gc();
					name.setText(oldtext);
				}
		    }
		  }
		}
		list.pane.getComponent(list.pos).requestFocus();
		list.sp.getVerticalScrollBar().setValue(list.pos/(2*BUTPERROW)*2*BUTHEIGHT);
	}

	private static void buildPane() {
		if(list.getFirst() == null) return;
		ListItem item = list.getFirst();
		list.pane = new JPanel();
		list.pane.setDoubleBuffered(true);
		list.pane.setLayout(new FlowLayout());
		list.pane.setPreferredSize(new Dimension(0, (((list.nOfItems)/BUTPERROW+1)*BUTHEIGHT)));
		list.pane.setLayout(new GridLayout(list.nOfItems/BUTPERROW+1,BUTPERROW));
		list.pane.setBackground(Color.BLACK);
		list.pos = 0; list.oldpos = 0;

		while(item != null) {
			final String desc = item.name;
			if(item instanceof Console) {
				item.icon = new String(cfgFile.images + item.name + ".gif");
			}
			else if(item instanceof Genre) {
				item.icon = new String(cfgFile.images + item.name + ".gif");
			}
			else if(item instanceof Game) {
				if(((Game)item).rom.indexOf(".") >= 0)
					item.icon = new String(k.logopath + ((Game)item).rom.substring(0, ((Game)item).rom.indexOf(".")) + ".jpg");
				else
					item.icon = new String(k.logopath + ((Game)item).rom + ".jpg");
			}

			JButton button = new JButton() {
				public void paintComponent(Graphics g) {
					ImageIcon img = new ImageIcon(cfgFile.images + "buttonbkgnd.jpg");
					g.drawImage(img.getImage(), 0, 0, cfgFile.resx/BUTPERROW, BUTHEIGHT, null);
					super.paintComponent(g); 
				}
			};
			button.setFont(cfgFile.font);
			button.setFocusable(true);
			button.setFocusPainted(true);
			button.setVisible(true);
			button.setBackground(buttonalpha);
			button.setForeground(Color.WHITE);
			button.setDoubleBuffered(true);


			button.registerKeyboardAction( new ActionListener() {
				public void actionPerformed(ActionEvent e) { }
			}, KeyStroke.getKeyStroke (cfgFile.keyUp, 0), JComponent.WHEN_FOCUSED);
			button.registerKeyboardAction( new ActionListener() {
				public void actionPerformed(ActionEvent e) { }
			}, KeyStroke.getKeyStroke (cfgFile.keyDown, 0), JComponent.WHEN_FOCUSED);

			button.addFocusListener(new FocusListener() {
				public void focusGained(FocusEvent e) {
					list.pane.getComponent(list.pos).setBackground(alpha0);
					list.oldpos = list.pos;
					name.setText(desc.length() > 26 ? desc.substring(0, 26) + "..." : desc);
				}
				public void focusLost(FocusEvent e) {
					list.pane.getComponent(list.oldpos).setBackground(buttonalpha);
				}
			});

			button.addKeyListener(new KeyListener() {
				public void keyReleased(KeyEvent e) { }
				public void keyTyped(KeyEvent e) { }
				public void keyPressed(KeyEvent e) { buttonKeyListener(e); }
			});
			list.pane.add(button);
			item = list.getNext();
		} 

		list.sp = new JScrollPane(list.pane);
		list.sp.setPreferredSize(new Dimension(cfgFile.resx, RESY));
		JScrollBar bar = new JScrollBar();
		bar.setUnitIncrement(2*BUTHEIGHT);
		bar.setBlockIncrement(2*BUTHEIGHT);
		bar.setAutoscrolls(false);
		list.sp.setVerticalScrollBar(bar);
		list.sp.setDoubleBuffered(true);
	}
} /* END: arcademenu.ArcadeMenu */
