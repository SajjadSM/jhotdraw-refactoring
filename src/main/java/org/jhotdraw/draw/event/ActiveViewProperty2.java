/**
 * @(#)SelectionComponentRepainter.java Copyright (c) 2008-2010 by the original authors of JHotDraw and all its contributors. All rights reserved. You may not use, copy or modify this file, except in compliance with the  license agreement you entered into with the copyright holders. For details see accompanying license terms.
 */
package org.jhotdraw.draw.event;


import java.beans.PropertyChangeEvent;
import org.jhotdraw.draw.DrawingView;

/**
 * @see org.jhotdraw.draw.DrawingEditor#ACTIVE_VIEW_PROPERTY
 */
public class ActiveViewProperty2 extends Name2 {
	public void propertyChange(PropertyChangeEvent evt, SelectionComponentRepainter selectionComponentRepainter) {
		DrawingView view = selectionComponentRepainter.view(evt);
		if (view != null) {
			view.removePropertyChangeListener(selectionComponentRepainter);
			if (view.getDrawing() != null) {
				view.getDrawing().removeFigureListener(selectionComponentRepainter);
			}
		}
		view = (DrawingView) evt.getNewValue();
		if (view != null) {
			view.addPropertyChangeListener(selectionComponentRepainter);
			if (view.getDrawing() != null) {
				view.getDrawing().addFigureListener(selectionComponentRepainter);
			}
		}
		selectionComponentRepainter.getComponent().repaint();
	}
}