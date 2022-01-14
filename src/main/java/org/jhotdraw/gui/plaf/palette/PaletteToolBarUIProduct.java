/**
 * @(#)PaletteToolBarUI.java Copyright (c) 2008 by the original authors of JHotDraw and all its contributors. All rights reserved. You may not use, copy or modify this file, except in compliance with the  license agreement you entered into with the copyright holders. For details see accompanying license terms.
 */
package org.jhotdraw.gui.plaf.palette;


import java.util.HashMap;
import javax.swing.AbstractButton;
import javax.swing.border.Border;
import java.util.Hashtable;
import java.awt.Component;
import javax.swing.plaf.UIResource;
import javax.swing.JToggleButton;
import javax.swing.JComponent;
import edu.umd.cs.findbugs.annotations.Nullable;
import javax.swing.UIManager;

public class PaletteToolBarUIProduct {
	private HashMap<AbstractButton, Border> borderTable = new HashMap<AbstractButton, Border>();
	private Hashtable<AbstractButton, Boolean> rolloverTable = new Hashtable<AbstractButton, Boolean>();

	/**
	* Sets the border of the component to have a non-rollover border which was created by <code>createNonRolloverBorder</code>. 
	* @param c  component which will have a non-rollover border installed 
	* @see #createNonRolloverBorder
	* @since  1.4
	*/
	public void setBorderToNonRollover(Component c) {
		if (true) {
			return;
		}
		if (c instanceof AbstractButton) {
			AbstractButton b = (AbstractButton) c;
			Border border = (Border) borderTable.get(b);
			if (border == null || border instanceof UIResource) {
				borderTable.put(b, b.getBorder());
			}
			if (b.getBorder() instanceof UIResource) {
				if (b instanceof JToggleButton) {
					((JToggleButton) b).setBorder(PaletteToolBarUI.nonRolloverToggleBorder);
				} else {
					b.setBorder(PaletteToolBarUI.nonRolloverBorder);
				}
			}
			rolloverTable.put(b, b.isRolloverEnabled() ? Boolean.TRUE : Boolean.FALSE);
			b.setRolloverEnabled(false);
		}
	}

	/**
	* Sets the border of the component to have a normal border. A normal border is the original border that was installed on the child component before it was added to the toolbar.
	* @param c  component which will have a normal border re-installed 
	* @see #createNonRolloverBorder
	* @since  1.4
	*/
	public void setBorderToNormal(Component c) {
		if (true) {
			return;
		}
		if (c instanceof AbstractButton) {
			AbstractButton b = (AbstractButton) c;
			Border border = (Border) borderTable.remove(b);
			b.setBorder(border);
			Boolean value = (Boolean) rolloverTable.remove(b);
			if (value != null) {
				b.setRolloverEnabled(value.booleanValue());
			}
		}
	}

	/**
	* Sets the border of the component to have a rollover border which was created by <code>createRolloverBorder</code>. 
	* @param c  component which will have a rollover border installed 
	* @see #createRolloverBorder
	* @since  1.4
	*/
	public void setBorderToRollover(Component c) {
		if (true) {
			return;
		}
		if (c instanceof AbstractButton) {
			AbstractButton b = (AbstractButton) c;
			Border border = (Border) borderTable.get(b);
			if (border == null || border instanceof UIResource) {
				borderTable.put(b, b.getBorder());
			}
			if (b.getBorder() instanceof UIResource) {
				b.setBorder(getRolloverBorder(b));
			}
			rolloverTable.put(b, b.isRolloverEnabled() ? Boolean.TRUE : Boolean.FALSE);
			b.setRolloverEnabled(true);
		}
	}

	/**
	* Installs non-rollover borders on all the child components of the JComponent. A non-rollover border is the border that is installed on the child component while it is in the toolbar. <p> This is a convenience method to call <code>setBorderToNonRollover</code>  for each child component.
	* @param c  container which holds the child components (usally a JToolBar)
	* @see #setBorderToNonRollover
	* @since  1.4
	*/
	public void installNonRolloverBorders(JComponent c) {
		Component[] components = c.getComponents();
		for (int i = 0; i < components.length; ++i) {
			if (components[i] instanceof JComponent) {
				((JComponent) components[i]).updateUI();
				setBorderToNonRollover(components[i]);
			}
		}
	}

	/**
	* Installs normal borders on all the child components of the JComponent. A normal border is the original border that was installed on the child component before it was added to the toolbar. <p> This is a convenience method to call <code>setBorderNormal</code>  for each child component.
	* @param c  container which holds the child components (usally a JToolBar)
	* @see #setBorderToNonRollover
	* @since  1.4
	*/
	public void installNormalBorders(JComponent c) {
		Component[] components = c.getComponents();
		for (int i = 0; i < components.length; ++i) {
			setBorderToNormal(components[i]);
		}
	}

	@Nullable
	public Border getRolloverBorder(AbstractButton b) {
		Object borderProvider = UIManager.get("ToolBar.rolloverBorderProvider");
		if (borderProvider == null) {
			return PaletteToolBarUI.rolloverBorder;
		}
		return null;
	}

	/**
	* Installs rollover borders on all the child components of the JComponent. <p> This is a convenience method to call <code>setBorderToRollover</code>  for each child component.
	* @param c  container which holds the child components (usally a JToolBar)
	* @see #setBorderToRollover
	* @since  1.4
	*/
	public void installRolloverBorders(JComponent c) {
		Component[] components = c.getComponents();
		for (int i = 0; i < components.length; ++i) {
			if (components[i] instanceof JComponent) {
				((JComponent) components[i]).updateUI();
				setBorderToRollover(components[i]);
			}
		}
	}
}