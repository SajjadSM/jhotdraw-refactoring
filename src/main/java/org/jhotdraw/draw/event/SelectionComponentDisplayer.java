/**
 * @(#)SelectionComponentDisplayer.java
 *
 * Copyright (c) 2006-2008 by the original authors of JHotDraw and all its
 * contributors. All rights reserved.
 *
 * You may not use, copy or modify this file, except in compliance with the 
 * license agreement you entered into with the copyright holders. For details
 * see accompanying license terms.
 */
package org.jhotdraw.draw.event;

import org.jhotdraw.draw.tool.SelectionTool;
import java.awt.Dimension;
import java.beans.*;
import java.lang.ref.WeakReference;
import javax.annotation.Nullable;
import javax.swing.*;
import org.jhotdraw.draw.*;

/**
 * Calls setVisible(true/false) on components, which show attributes of the 
 * drawing editor and of its views based on the current selection.
 * <p>
 * Holds a {@code WeakReference} on the component. Automatically disposes
 * itself if the component no longer exists.
 *
 * @author Werner Randelshofer
 * @version $Id$
 */
public class SelectionComponentDisplayer
        implements PropertyChangeListener, FigureSelectionListener {

    @Nullable protected DrawingView view;
    @Nullable protected DrawingEditor editor;
    protected WeakReference<JComponent> weakRef;
    protected int minSelectionCount = 1;
    protected boolean isVisibleIfCreationTool = true;

    public SelectionComponentDisplayer(DrawingEditor editor, JComponent component) {
        this.editor = editor;
        this.weakRef = new WeakReference<JComponent>(component);
        if (editor.getActiveView() != null) {
            view = editor.getActiveView();
            view.addPropertyChangeListener(this);
            view.addFigureSelectionListener(this);
        }
        editor.addPropertyChangeListener(this);
        updateVisibility();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String name = evt.getPropertyName();
        getName3Object(name).propertyChange(evt, this);
    }

    @Override
    public void selectionChanged(FigureSelectionEvent evt) {
        updateVisibility();
    }

    public void updateVisibility() {
        JComponent component = component();
		if (component == null) {
            dispose();
            return;
        }
    }

	private JComponent component() {
		boolean newValue = editor != null && editor.getActiveView() != null
				&& (isVisibleIfCreationTool && editor.getTool() != null && !(editor.getTool() instanceof SelectionTool)
						|| editor.getActiveView().getSelectionCount() >= minSelectionCount);
		JComponent component = weakRef.get();
		if (newValue != component.isVisible()) {
			component.setVisible(newValue);
			if (newValue) {
				component.setPreferredSize(null);
			} else {
				component.setPreferredSize(new Dimension(0, 0));
			}
			component.revalidate();
		}
		return component;
	}

    @Nullable
    protected JComponent getComponent() {
        return weakRef.get();
    }

    public void dispose() {
        if (editor != null) {
            editor.removePropertyChangeListener(this);
            editor = null;
        }
        if (view != null) {
            view.removePropertyChangeListener(this);
            view.removeFigureSelectionListener(this);
            view = null;
        }
    }

    public void setMinSelectionCount(int newValue) {
        minSelectionCount = newValue;
        updateVisibility();
    }

    public void setVisibleIfCreationTool(boolean newValue) {
        isVisibleIfCreationTool = newValue;
    }

	private Name3 getName3Object(String name) {
		switch (name) {
		case DrawingEditor.TOOL_PROPERTY:
			return new ToolProperty3();
		case DrawingEditor.ACTIVE_VIEW_PROPERTY:
			return new ActiveViewProperty3();
		}
		return null;
	}

	public DrawingView getView() {
		return view;
	}

	public void setView(DrawingView view) {
		this.view = view;
	}
}

