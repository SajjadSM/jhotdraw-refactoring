/*
 * @(#)PaletteListCellRenderer.java
 * 
 * Copyright (c) 2010 by the original authors of JHotDraw and all its
 * contributors. All rights reserved.
 * 
 * You may not use, copy or modify this file, except in compliance with the 
 * license agreement you entered into with the copyright holders. For details
 * see accompanying license terms.
 */

package org.jhotdraw.gui.plaf.palette;

import java.awt.Color;
import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.JList;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

/**
 * PaletteListCellRenderer.
 *
 * @author Werner Randelshofer
 * @version $Id$
 */
public class PaletteListCellRenderer extends DefaultListCellRenderer {
   /**
    * An empty <code>Border</code>. This field might not be used. To change the
    * <code>Border</code> used by this renderer override the
    * <code>getListCellRendererComponent</code> method and set the border
    * of the returned component directly.
    */
    private static final Border SAFE_NO_FOCUS_BORDER = new EmptyBorder(1, 1, 1, 1);
    private static final Border DEFAULT_NO_FOCUS_BORDER = new EmptyBorder(1, 1, 1, 1);
    protected Border noFocusBorder = DEFAULT_NO_FOCUS_BORDER;

    /**
     * Constructs a default renderer object for an item
     * in a list.
     */
    public PaletteListCellRenderer() {
	super();
	setOpaque(true);
        setBorder(getNoFocusBorder());
        setName("List.cellRenderer");
    }

    private Border getNoFocusBorder() {
        Border border = PaletteLookAndFeel.getInstance().getBorder("List.cellNoFocusBorder");
        if (System.getSecurityManager() != null) {
            if (border != null) return border;
            return SAFE_NO_FOCUS_BORDER;
        } else {
            if (border != null &&
                    (noFocusBorder == null ||
                    noFocusBorder == DEFAULT_NO_FOCUS_BORDER)) {
                return border;
            }
            return noFocusBorder;
        }
    }

    @Override
    public Component getListCellRendererComponent(
        JList list,
	Object value,
        int index,
        boolean isSelected,
        boolean cellHasFocus)
    {
        PaletteLookAndFeel plaf=PaletteLookAndFeel.getInstance();

        setComponentOrientation(list.getComponentOrientation());

        Color bg = null;
        Color fg = null;

        isSelected = isSelected(list, index, isSelected);
		JList.DropLocation dropLocation = list.getDropLocation();
        if (dropLocation != null
                && !dropLocation.isInsert()
                && dropLocation.getIndex() == index) {

            bg = plaf.getColor( "List.dropCellBackground");
            fg = plaf.getColor( "List.dropCellForeground");
        }

	if (isSelected) {
            setBackground(bg == null ? list.getSelectionBackground() : bg);
	    setForeground(fg == null ? list.getSelectionForeground() : fg);
	}
	else {
	    setBackground(list.getBackground());
	    setForeground(list.getForeground());
	}

	if (value instanceof Icon) {
	    setIcon((Icon)value);
	    setText("");
	}
	else {
	    setIcon(null);
	    setText((value == null) ? "" : value.toString());
	}

	setEnabled(list.isEnabled());
	setFont(list.getFont());

        Border border = border(isSelected, cellHasFocus, plaf);
		setBorder(border);

	return this;
    }

	private boolean isSelected(JList list, int index, boolean isSelected) {
		JList.DropLocation dropLocation = list.getDropLocation();
		if (dropLocation != null && !dropLocation.isInsert() && dropLocation.getIndex() == index) {
			isSelected = true;
		}
		return isSelected;
	}

	private Border border(boolean isSelected, boolean cellHasFocus, PaletteLookAndFeel plaf) {
		Border border = null;
		if (cellHasFocus) {
			if (isSelected) {
				border = plaf.getBorder("List.focusSelectedCellHighlightBorder");
			}
			if (border == null) {
				border = plaf.getBorder("List.focusCellHighlightBorder");
			}
		} else {
			border = getNoFocusBorder();
		}
		return border;
	}

}
