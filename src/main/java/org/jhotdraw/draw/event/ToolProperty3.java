/**
 * @(#)SelectionComponentDisplayer.java Copyright (c) 2006-2008 by the original authors of JHotDraw and all its contributors. All rights reserved. You may not use, copy or modify this file, except in compliance with the  license agreement you entered into with the copyright holders. For details see accompanying license terms.
 */
package org.jhotdraw.draw.event;


import java.beans.PropertyChangeEvent;

/**
 * @see org.jhotdraw.draw.DrawingEditor#TOOL_PROPERTY
 */
public class ToolProperty3 extends Name3 {
	public void propertyChange(PropertyChangeEvent evt, SelectionComponentDisplayer selectionComponentDisplayer) {
		selectionComponentDisplayer.updateVisibility();
	}
}