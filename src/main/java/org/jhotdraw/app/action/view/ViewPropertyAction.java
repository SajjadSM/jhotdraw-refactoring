/*
 * @(#)ViewPropertyAction.java
 *
 * Copyright (c) 1996-2010 by the original authors of JHotDraw and all its
 * contributors. All rights reserved.
 *
 * You may not use, copy or modify this file, except in compliance with the 
 * license agreement you entered into with the copyright holders. For details
 * see accompanying license terms.
 */

package org.jhotdraw.app.action.view;

import edu.umd.cs.findbugs.annotations.Nullable;
import java.awt.event.*;
import java.beans.*;
import java.lang.reflect.InvocationTargetException;

import org.jhotdraw.app.Application;
import org.jhotdraw.app.View;
import org.jhotdraw.app.action.AbstractViewAction;
import org.jhotdraw.app.action.ActionUtil;

/**
 * ViewPropertyAction.
 * 
 * @author Werner Randelshofer.
 * @version $Id$
 */
public class ViewPropertyAction extends AbstractViewAction {
    private ViewPropertyActionProduct viewPropertyActionProduct = new ViewPropertyActionProduct();
	private String propertyName;
    private Class[] parameterClass;
    private String setterName;
    private PropertyChangeListener viewListener = new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            if (propertyName.equals(evt.getPropertyName())) { // Strings get interned
                updateSelectedState();
            }
        }
    };
    
    /** Creates a new instance. */
    public ViewPropertyAction(Application app, @Nullable View view, String propertyName, Object propertyValue) {
        this(app, view, propertyName, propertyValue.getClass(), propertyValue);
    }
    public ViewPropertyAction(Application app, @Nullable View view, String propertyName, Class propertyClass, Object propertyValue) {
        super(app, view);
        this.propertyName = propertyName;
        this.parameterClass = new Class[] { propertyClass };
        viewPropertyActionProduct.setPropertyValue(propertyValue);
        setterName = "set"+Character.toUpperCase(propertyName.charAt(0)) +
                propertyName.substring(1);
        viewPropertyActionProduct
				.setGetterName(((propertyClass == Boolean.TYPE || propertyClass == Boolean.class) ? "is" : "get")
						+ Character.toUpperCase(propertyName.charAt(0)) + propertyName.substring(1));
        updateSelectedState();
    }
    
    @Override
    public void actionPerformed(ActionEvent evt) {
        View p = getActiveView();
        try {
            p.getClass().getMethod(setterName, parameterClass).invoke(p, new Object[] {viewPropertyActionProduct.getPropertyValue()});
        } catch (Throwable e) {
                InternalError error = new InternalError("Method invocation failed. setter:"+setterName+" object:"+p);
            error.initCause(e);
            throw error;
        }
    }
    
   @Override protected void installViewListeners(View p) {
        super.installViewListeners(p);
        p.addPropertyChangeListener(viewListener);
        updateSelectedState();
    }
    /**
     * Installs listeners on the view object.
     */
   @Override protected void uninstallViewListeners(View p) {
        super.uninstallViewListeners(p);
        p.removePropertyChangeListener(viewListener);
    }
    
    private void updateSelectedState() {
        boolean isSelected;
		try {
			isSelected = viewPropertyActionProduct.isSelected(this);
			putValue(ActionUtil.SELECTED_KEY, isSelected);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	public Object clone() throws java.lang.CloneNotSupportedException {
		ViewPropertyAction clone = (ViewPropertyAction) super.clone();
		clone.viewPropertyActionProduct = (ViewPropertyActionProduct) this.viewPropertyActionProduct.clone();
		return clone;
	}
}
