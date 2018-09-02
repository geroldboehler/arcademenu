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

import java.io.*;

public class Console extends ListItem implements Serializable {
	private List genres = new List();
	public String rompath, romfile, binary, logopath, keywiz;

	public Console(String name, String rompath, String romfile, String binary, String logopath, String keywiz) {
		super(name);
		this.rompath = new String(rompath);
		this.romfile = new String(romfile);
		this.logopath = new String(logopath);
		this.binary = new String(binary);
		this.keywiz = new String(keywiz);
	}

	public Genre getGenre(String name) {
		return (Genre)genres.get(name);
	}

	public Genre getGenre(int n) {
		return (Genre)genres.get(n);
	}

	public List getGenreList() {
		return genres;
	}

	public void insertGenre(Genre genre) {
		genres.put(genre);
	}

	public Genre getFirstGenre() {
		return (Genre)genres.getFirst();
	}

	public Genre getNextGenre() {
		return (Genre)genres.getNext();
	}
}