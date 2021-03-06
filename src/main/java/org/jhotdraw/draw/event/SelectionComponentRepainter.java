/**
 * @(#)SelectionComponentRepainter.java
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
 * Calls repaint on components, which show attributes of the drawing editor
 * and of its views based on the current selection.
 *
 * @author Werner Randelshofer
 * @version $Id$
 */
public class SelectionComponentRepainter extends FigureAdapter
        implements PropertyChangeListener, FigureSelectionListener, Disposable {

    @Nullable private DrawingEditor editor;
    @Nullable private JComponent component;

    public SelectionComponentRepainter(DrawingEditor editor, JComponent component) {
        this.editor = editor;
        this.component = component;
        if (editor != null) {
            if (editor.getActiveView() != null) {
                DrawingView view = editor.getActiveView();
                view.addPropertyChangeListener(this);
                view.addFigureSelectionListener(this);
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
        getName2Object(name).propertyChange(evt, this);
    }

	public DrawingView view(PropertyChangeEvent evt) {
		DrawingView view = (DrawingView) evt.getOldValue();
		if (view != null) {
			view.removeFigureSelectionListener(this);
		}
		view = (DrawingView) evt.getNewValue();
		if (view != null) {
			view.addFigureSelectionListener(this);
		}
		return view;
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
    public void selectionChanged(FigureSelectionEvent evt) {
        component.repaint();
    }

    @Override
    public void dispose() {
        if (editor != null) {
            if (editor.getActiveView() != null) {
                DrawingView view = editor.getActiveView();
                view.removePropertyChangeListener(this);
                view.removeFigureSelectionListener(this);
                if (view.getDrawing() != null) {
                    view.getDrawing().removeFigureListener(this);
                }
            }
            editor.removePropertyChangeListener(this);
            editor = null;
        }
        component = null;
    }

	private Name2 getName2Object(String name) {
		switch (name) {
		case DrawingView.DRAWING_PROPERTY:
			return new DrawingProperty2();
		case DrawingEditor.ACTIVE_VIEW_PROPERTY:
			return new ActiveViewProperty2();
		case DrawingEditor.TOOL_PROPERTY:
			return new ToolProperty2();
		}
		return null;
	}

	public JComponent getComponent() {
		return component;
	}
}

