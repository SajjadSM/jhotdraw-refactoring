/**
 * @(#)DrawingComponentRepainter.java Copyright (c) 2008-2010 by the original authors of JHotDraw and all its contributors. All rights reserved. You may not use, copy or modify this file, except in compliance with the  license agreement you entered into with the copyright holders. For details see accompanying license terms.
 */
package org.jhotdraw.draw.event;


import java.beans.PropertyChangeEvent;
import org.jhotdraw.draw.DrawingView;

/**
 * @see org.jhotdraw.draw.DrawingEditor#ACTIVE_VIEW_PROPERTY
 */
public class ActiveViewProperty extends Name {
	public void propertyChange(PropertyChangeEvent evt, DrawingComponentRepainter drawingComponentRepainter) {
		DrawingView view = (DrawingView) evt.getOldValue();
		if (view != null) {
			view.removePropertyChangeListener(drawingComponentRepainter);
			if (view.getDrawing() != null) {
				view.getDrawing().removeFigureListener(drawingComponentRepainter);
			}
		}
		view = (DrawingView) evt.getNewValue();
		if (view != null) {
			view.addPropertyChangeListener(drawingComponentRepainter);
			if (view.getDrawing() != null) {
				view.getDrawing().addFigureListener(drawingComponentRepainter);
			}
		}
		drawingComponentRepainter.getComponent().repaint();
	}
}