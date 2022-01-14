/**
 * @(#)SelectionComponentRepainter.java Copyright (c) 2008-2010 by the original authors of JHotDraw and all its contributors. All rights reserved. You may not use, copy or modify this file, except in compliance with the  license agreement you entered into with the copyright holders. For details see accompanying license terms.
 */
package org.jhotdraw.draw.event;


import java.beans.PropertyChangeEvent;
import org.jhotdraw.draw.Drawing;

/**
 * @see org.jhotdraw.draw.DrawingView#DRAWING_PROPERTY
 */
public class DrawingProperty2 extends Name2 {
	public void propertyChange(PropertyChangeEvent evt, SelectionComponentRepainter selectionComponentRepainter) {
		Drawing drawing = selectionComponentRepainter.drawing(evt);
		selectionComponentRepainter.getComponent().repaint();
	}
}