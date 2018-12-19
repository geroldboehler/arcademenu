/*
 * ParseRom.java
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

public class ParseRom {

	private static String next, item, file;
	private static String GF = "\"", SPACE = " ";

	private static boolean cmp(String s, String t) {
		return s.equals(t);
	}

	private static void next() { item = Scanner.next(); }

	private static void error(String s) {
		System.out.println(s + " missing at line " + Scanner.line + " in " + file);
		System.exit(-1);
	}

	public static void parse(String romfile) {
		file = romfile;
		if(!Scanner.open(romfile)) {
			System.out.println("Can't find romfile: " + romfile);
			System.exit(-1);
		}
		String file, desc, gen;
		StringBuffer k;
		next(); 
		next();
		do {
			ArcadeMenu.TOTALNOFGAMES++;
			StringBuffer l = new StringBuffer();
			boolean first = true;
			while(!cmp(item, GF)) {
				l.append(item);
				next();
				if(!cmp(item, GF)) { l.append(SPACE); }
			}
			file = new String(l.toString());
			if(!cmp(item, GF)) error(GF);
			next();
			k = new StringBuffer();
			while(!cmp(item, GF)) {
				k.append(item);
				next();
				if(!cmp(item, GF)) { k.append(SPACE); }
			}
			desc = new String(k.toString());
			next();
			if(!cmp(item, GF)) error(GF);
			next();
			k = new StringBuffer();
			while(!cmp(item, GF)) {
				k.append(item);
				next();
				if(!cmp(item, GF)) { k.append(SPACE); }
			}
			gen = new String(k.toString());
			if( ArcadeMenu.curConsole.getGenre(gen) == null) {
				if(ArcadeMenu.curConsole.getGenre("Unknown") == null)
					ArcadeMenu.curConsole.insertGenre(new Genre("Unknown"));
				ArcadeMenu.curConsole.getGenre("Unknown").insertGame(new Game(desc, file));
			}
			else {
				ArcadeMenu.curConsole.getGenre(gen).insertGame(new Game(desc, file));
			}
			next();
    } while(!cmp(item, Character.toString(Scanner.EOF)));
		Scanner.close();
	}
}