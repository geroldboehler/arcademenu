/*
 * ListItem.java
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

public class ListItem implements Serializable {

	public String name;
	public ListItem next;
	public String icon;

	public ListItem(String name) { this.name = new String(name); }
}