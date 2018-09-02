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
import java.io.*;

  abstract class Chooser {
    protected JFileChooser chooser;
    public int res;
    public String path;
    private boolean runOnAprove, showTitle, edited;
    private JFrame parent;

    public Chooser(boolean open, boolean edited) {
      this(open, true, edited);
    }

    public Chooser(boolean open, int selectionMode, boolean showTitle, boolean edited) {
      this(open, true, selectionMode, showTitle, edited);
    }

    public Chooser(boolean open, boolean savefirst, boolean edited) {
      this(open, savefirst, JFileChooser.FILES_AND_DIRECTORIES, true, edited);
    }

    public Chooser(boolean open, boolean savefirst, int selectionMode, boolean showTitle, boolean edited) {
      this.showTitle = showTitle;
      this.edited = edited;
      chooser = new JFileChooser();
      chooser.setMultiSelectionEnabled(true);
      chooser.setFileSelectionMode(selectionMode);
      if(!showTitle) {
        chooser.setFileFilter(new javax.swing.filechooser.FileFilter() {

          public boolean accept(File f) {
            if(f.isDirectory()) return true;
            if(f.getName().endsWith("zip"))
              return true;
            return false;
          }

          public String getDescription() {
            return "Zip Files";
          }
        });

      }
      if (open) {
        if (edited && savefirst) {
          res = chooser.showSaveDialog(parent);
        }
        else { res = chooser.showOpenDialog(parent); }
      }
      else {
        if (!edited) return;
        res = chooser.showSaveDialog(parent);
      }
      if (res == JFileChooser.CANCEL_OPTION) {
        runOnAprove = false;
        return;
      }
      else { runOnAprove = true; }
      if (res == JFileChooser.APPROVE_OPTION) {
        path = chooser.getSelectedFile().getAbsolutePath();
        if(open && !fileExists()) { return; }
      }
    }

    public boolean start(JFrame parent) {
      if (!runOnAprove) { return edited; }
      this.parent = parent;
      onAprove();
      edited = false;
      if (showTitle) { parent.setTitle(parent.getName() + "[" + path + "]" ); }
      else { edited = true; }
      return edited;
    }

    public abstract void onAprove();

    public void showMessage(String msg) {
      JOptionPane.showMessageDialog(parent, msg);
    }

    public boolean fileExists() {
      if (!chooser.getSelectedFile().exists()) {
        showMessage("File doesn't exist!");
        return false;
      }
      else { return true; }
    }
  }
