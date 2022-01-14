/**
 * @(#)ToolBarLayout.java Copyright (c) 2008 by the original authors of JHotDraw and all its contributors. All rights reserved. You may not use, copy or modify this file, except in compliance with the  license agreement you entered into with the copyright holders. For details see accompanying license terms.
 */
package org.jhotdraw.gui;


import java.awt.Container;

public abstract class Axis {
	public abstract int getAxis();

	public abstract int h(int h, Container parent);

	public abstract int w(int w, Container parent);

	public abstract float getLayoutAlignmentX();

	public abstract float getLayoutAlignmentY();
}