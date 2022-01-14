/**
 * @(#)ToolBarLayout.java Copyright (c) 2008 by the original authors of JHotDraw and all its contributors. All rights reserved. You may not use, copy or modify this file, except in compliance with the  license agreement you entered into with the copyright holders. For details see accompanying license terms.
 */
package org.jhotdraw.gui;


import java.awt.Container;
import java.awt.Component;
import java.awt.Dimension;

/**
 * @see org.jhotdraw.gui.ToolBarLayout#X_AXIS
 */
public class XAxis extends Axis {
	public int getAxis() {
		return ToolBarLayout.X_AXIS;
	}

	public int h(int h, Container parent) {
		for (Component c : parent.getComponents()) {
			Dimension ps = c.getPreferredSize();
			h = Math.max(h, ps.height);
		}
		return h;
	}

	public int w(int w, Container parent) {
		for (Component c : parent.getComponents()) {
			Dimension ps = c.getPreferredSize();
			w += ps.width;
		}
		return w;
	}

	public float getLayoutAlignmentX() {
		return 0f;
	}

	public float getLayoutAlignmentY() {
		return 0f;
	}
}