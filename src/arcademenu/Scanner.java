/*
 * Scanner.java
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

import java.io.*;

/**
 * A very small scanner to scan a file.
 * @author Gerold Boehler
 */
public class Scanner {

	public static final char EOF = '\uffff';
	public static boolean done;
	public static int line;
	private static InputStream in;
	private static char prev = EOF, next = EOF;
	private static char[] tokens = {'/','\\','-','=','.','\''};
	
	public static boolean open(String name) {
		done = false; line = 1;
		try { in = new FileInputStream(name); return true; }
		catch (FileNotFoundException e) { return false; }
	}
	
	public static void close() {
		try { in.close(); } catch(IOException e) { } done = true;
	}
	
	private static boolean isToken() {
		if(Character.isLetterOrDigit(next)) return true;
		for(int i = 0; i < tokens.length; i++) { if(tokens[i] == next) return true; }
		return false;
	}
	
	private static char read() {
		char c = EOF;
		try { c = (char)in.read(); } catch (IOException e) {}
		return c;
	}
	
	public static String next() {
		StringBuffer s = new StringBuffer();
		while(Character.isWhitespace(next) || next == '#') {
			if(next == '#') { while(next != '\n') next = read(); }
			if(next == '\n') line++;
			next = read();
		}
	
		if(isToken()) {
			do { s.append(next); next = read(); } while(isToken());
			return s.toString();
		}
		else {
			while(next == '#') { while(next != '\n') next = read(); next = read(); }
			char c = next;
			next = read();
			if(c == EOF) done = true;
			return Character.toString(c); 
		}
	}
}