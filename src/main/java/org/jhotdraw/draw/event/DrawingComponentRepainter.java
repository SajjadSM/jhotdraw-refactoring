/**
 * @(#)DrawingComponentRepainter.java
 *
 * Copyright (c) 2008-2010 by the original authors of JHotDraw and all its
 * contributors. All rights reserved.
 *
 * You may not use, copy or modify this file, except in compliance with the 
 * license agreement you entered into with the copyright holders. For details
 * see accompanying license terms.
 */
package org.jhotdraw.draw.event;

import edu.umd.cs.findbugs.annotations.Nullable;
import java.beans.*;
import javax.swing.*;
import org.jhotdraw.app.Disposable;
import org.jhotdraw.draw.*;

/**
 * Calls repaint on components, which show attributes of a drawing object
 * on the current view of the editor.
 *
 * @author Werner Randelshofer
 * @version $Id$
 */
public class DrawingComponentRepainter extends FigureAdapter
        implements PropertyChangeListener, Disposable {

    @Nullable private DrawingEditor editor;
    @Nullable private JComponent component;

    public DrawingComponentRepainter(DrawingEditor editor, JComponent component) {
        this.editor = editor;
        this.component = component;
        if (editor != null) {
            if (editor.getActiveView() != null) {
                DrawingView view = editor.getActiveView();
                view.addPropertyChangeListener(this);
                if (view.getDrawing() != null) {
                    view.getDrawing().addFigureListener(this);
                }
            }

            editor.addPropertyChangeListener(this);
        }
    }

    @Override
    public void attributeChanged(FigureEvent evt) {
        component.repaint();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String name = evt.getPropertyName();
        getNameObject(name).propertyChange(evt, this);
    }

	public Drawing drawing(PropertyChangeEvent evt) {
		Drawing drawing = (Drawing) evt.getOldValue();
		if (drawing != null) {
			drawing.removeFigureListener(this);
		}
		drawing = (Drawing) evt.getNewValue();
		if (drawing != null) {
			drawing.addFigureListener(this);
		}
		return drawing;
	}

    @Override
    public void dispose() {
        if (editor != null) {
            if (editor.getActiveView() != null) {
                DrawingView view = editor.getActiveView();
                view.removePropertyChangeListener(this);
                if (view.getDrawing() != null) {
                    view.getDrawing().removeFigureListener(this);
                }
            }
            editor.removePropertyChangeListener(this);
            editor = null;
        }
        component = null;
    }

	private Name getNameObject(String name) {
		switch (name) {
		case DrawingView.DRAWING_PROPERTY:
			return new DrawingProperty();
		case DrawingEditor.ACTIVE_VIEW_PROPERTY:
			return new ActiveViewProperty();
		case DrawingEditor.TOOL_PROPERTY:
			return new ToolProperty();
		}
		return null;
	}

	public JComponent getComponent() {
		return component;
	}
}

