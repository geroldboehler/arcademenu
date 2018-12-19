/*
 * List.java
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

public class List implements Serializable {

	private ListItem root, next;
	public List prev;
	public JPanel pane;
	public JScrollPane sp;
	public int nOfItems = 1, pos = 0, oldpos = 0;

	public List() { }

	public void put(ListItem item) {
		ListItem p = root, q = null;

		if(p == null) { root = item; next = root; return; }
		while(p != null && p.name.compareTo(item.name) < 0) {
			q = p; p = p.next;
		}
		if(q == null) { item.next = p; root = item; }
		else if(p == null) { q.next = item; }
		else { item.next = p; q.next = item; }
		nOfItems++;
	}

	public ListItem get(String name) {
		ListItem p = root;

		while(p != null && !p.name.equals(name)) p = p.next;
		return p;
	}

	public ListItem get(int n) {
		int i = 0;
		ListItem p = root;

		while(p != null && i < n) { p = p.next; i++; }
		return p;
	}

	public ListItem getFirst() { return next = root; }

	public ListItem getNext() { return next != null ? (next = next.next ): next ;}

	public ListItem getCurrent() { return next ;}

	public ListItem setCurrent(int n) {
		int i = 0;
		ListItem oldnext = next, next = root;

		while(next != null && i < n) { next = next.next; i++; }
		if(next == null) { next = oldnext; }
		return next;
	}
}