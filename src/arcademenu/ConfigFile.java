/*
 * ConfigFile.java
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

import java.awt.*;
import java.io.*;

public class ConfigFile implements Serializable {

	public List consoles;

	public int gap;

	public boolean fitQuad;
	public boolean systemHaltEnabled;
	public boolean monitorHaltEnabled;
	public boolean soundEnabled;
	
	public String baseDir;
	
	public int nOfCoins;
	
	public int rows;
	public int cols;
	
	public int rounded;
	
	public Font buttonFont;
	public Color buttonFontColor;

	public Font titleFont;
	public Color titleFontColor;

	public Font infoFont;
	public Color infoFontColor;

	public int keyUp;
	public int keyDown;
	public int keyLeft;
	public int keyRight;
	public int keySelect[] = new int[4];
	public int keyBack[] = new int[4];
	public int keyCoin;
	public int keyExit;
	
	public int dataLine;
	public String lptPort;
	public String logoFormat;
	public String images;
	public String mp3dir;
	public int resx;
	public Font font;
	public int iconBorder;
	
	public ConfigFile() {
	}
}