/**
 * @(#)SelectionComponentRepainter.java Copyright (c) 2008-2010 by the original authors of JHotDraw and all its contributors. All rights reserved. You may not use, copy or modify this file, except in compliance with the  license agreement you entered into with the copyright holders. For details see accompanying license terms.
 */
package org.jhotdraw.draw.event;


import java.beans.PropertyChangeEvent;

/**
 * @see org.jhotdraw.draw.DrawingEditor#TOOL_PROPERTY
 */
public class ToolProperty2 extends Name2 {
	public void propertyChange(PropertyChangeEvent evt, SelectionComponentRepainter selectionComponentRepainter) {
		selectionComponentRepainter.getComponent().repaint();
	}
}