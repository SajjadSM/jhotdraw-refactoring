/**
 * @(#)ToolBarLayout.java
 *
 * Copyright (c) 2008 by the original authors of JHotDraw and all its
 * contributors. All rights reserved.
 *
 * You may not use, copy or modify this file, except in compliance with the 
 * license agreement you entered into with the copyright holders. For details
 * see accompanying license terms.
 */
package org.jhotdraw.gui;

import java.awt.*;
import java.io.Serializable;

/**
 * A layout which lays out components horizontally or vertically according
 * to their preferred size.
 *
 * @author Werner Randelshofer
 * @version $Id$
 */
public class ToolBarLayout implements LayoutManager2, Serializable {

    /**
     * Specifies that components should be laid out left to right.
     */
    public static final int X_AXIS = 0;
    /**
     * Specifies that components should be laid out top to bottom.
     */
    public static final int Y_AXIS = 1;
    /**
     * Specifies the axis of the layout.
     */
    private Axis axis = new XAxis();

    /**
     * Creates a layout manager that will lay out components along the
     * X-axis.  
     */
    public ToolBarLayout() {
        this(X_AXIS);
    }
    /**
     * Creates a layout manager that will lay out components along the
     * given axis.  
     *
     * @param axis  the axis to lay out components along. Can be one of:
     *              <code>BoxLayout.X_AXIS</code>,
     *              <code>BoxLayout.Y_AXIS</code>,
     *
     * @exception AWTError  if the value of <code>axis</code> is invalid 
     */
    public ToolBarLayout(int axis) {
        switch (axis) {
		case Y_AXIS:
			this.axis = new YAxis();
			break;
		case X_AXIS:
			this.axis = new XAxis();
			break;
		default:
			this.axis = null;
			break;
		}
    }


    @Override
    public void addLayoutComponent(Component comp, Object constraints) {
    }

    @Override
    public Dimension maximumLayoutSize(Container target) {
        return preferredLayoutSize(target);
    }

    @Override
    public float getLayoutAlignmentX(Container target) {
        return axis.getLayoutAlignmentX();
    }

    @Override
    public float getLayoutAlignmentY(Container target) {
        return axis.getLayoutAlignmentY();
    }

    @Override
    public void invalidateLayout(Container target) {
    }

    @Override
    public void addLayoutComponent(String name, Component comp) {
    }

    @Override
    public void removeLayoutComponent(Component comp) {
    }

    @Override
    public Dimension preferredLayoutSize(Container parent) {
        int w = w(parent);
		int h = h(parent);
		Insets i = parent.getInsets();
        
        return new Dimension(w + i.left + i.right, h + i.top + i.bottom);
    }
	private int w(Container parent) {
		int w = 0;
		w = axis.w(w, parent);
		return w;
	}
	private int h(Container parent) {
		int h = 0;
		h = axis.h(h, parent);
		return h;
	}

    @Override
    public Dimension minimumLayoutSize(Container parent) {
        return preferredLayoutSize(parent);
    }

    @Override
    public void layoutContainer(Container parent) {
        Dimension ps = preferredLayoutSize(parent);
        Insets insets = parent.getInsets();
        
        int w = ps.width - insets.left - insets.right;
        int h = ps.height - insets.top - insets.bottom;
        int x = insets.left;
        int y = insets.top;
        switch (getAxis()) {
            case Y_AXIS:
                for (Component c : parent.getComponents()) {
                    ps = c.getPreferredSize();
                    c.setBounds(x, y, w, ps.height);
                    y += ps.height;
                }
                break;
            case X_AXIS:
            default:
                for (Component c : parent.getComponents()) {
                    ps = c.getPreferredSize();
                    c.setBounds(x, y, ps.width, h);
                    x += ps.width;
                }
        }
    }
	public int getAxis() {
		return axis.getAxis();
	}
}
