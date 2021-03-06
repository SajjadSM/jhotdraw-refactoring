/*
 * @(#)AbstractApplicationModel.java
 * 
 * Copyright (c) 2009-2010 by the original authors of JHotDraw and all its
 * contributors. All rights reserved.
 * 
 * You may not use, copy or modify this file, except in compliance with the 
 * license agreement you entered into with the copyright holders. For details
 * see accompanying license terms.
 */
package org.jhotdraw.app;

import edu.umd.cs.findbugs.annotations.Nullable;
import org.jhotdraw.beans.*;
import java.util.*;
import javax.swing.*;
import org.jhotdraw.gui.JFileURIChooser;
import org.jhotdraw.gui.URIChooser;

/**
 * This abstract class can be extended to implement an {@link ApplicationModel}.
 *
 * @author Werner Randelshofer
 * @version $Id$
 */
public abstract class AbstractApplicationModel extends AbstractBean
        implements ApplicationModel {

    private AbstractApplicationModelProduct abstractApplicationModelProduct = new AbstractApplicationModelProduct();
	protected Class viewClass;
    protected String viewClassName;
    protected boolean allowMultipleViewsForURI = true;
    protected boolean openLastURIOnLaunch = false;
    public final static String NAME_PROPERTY = "name";
    public final static String VERSION_PROPERTY = "version";
    public final static String COPYRIGHT_PROPERTY = "copyright";
    public final static String VIEW_CLASS_NAME_PROPERTY = "viewClassName";
    public final static String VIEW_CLASS_PROPERTY = "viewClass";

    /** Creates a new instance. */
    public AbstractApplicationModel() {
    }

    public void setName(String newValue) {
        abstractApplicationModelProduct.setName(newValue, this);
    }

    @Override
    public String getName() {
        return abstractApplicationModelProduct.getName();
    }

    public void setVersion(String newValue) {
        abstractApplicationModelProduct.setVersion(newValue, this);
    }

    @Override
    public String getVersion() {
        return abstractApplicationModelProduct.getVersion();
    }

    public void setCopyright(String newValue) {
        abstractApplicationModelProduct.setCopyright(newValue, this);
    }

    @Override
    public String getCopyright() {
        return abstractApplicationModelProduct.getCopyright();
    }

    /**
     * Use this method for best application startup performance.
     */
    public void setViewClassName(String newValue) {
        String oldValue = viewClassName;
        viewClassName = newValue;
        firePropertyChange(VIEW_CLASS_NAME_PROPERTY, oldValue, newValue);
    }

    /**
     * Use this method only, if setViewClassName() does not suit you.
     */
    public void setViewClass(Class newValue) {
        Class oldValue = viewClass;
        viewClass = newValue;
        firePropertyChange(VIEW_CLASS_PROPERTY, oldValue, newValue);
    }

    public Class getViewClass() {
        if (viewClass == null) {
            if (viewClassName != null) {
                try {
                    viewClass = Class.forName(viewClassName);
                } catch (Exception e) {
                    InternalError error = new InternalError("unable to get view class");
                    error.initCause(e);
                    throw error;
                }
            }
        }
        return viewClass;
    }

    @Override
    public View createView() {
        try {
            return (View) getViewClass().newInstance();
        } catch (Exception e) {
            InternalError error = new InternalError("unable to create view");
            error.initCause(e);
            throw error;
        }
    }

    /**
     * Creates toolbars for the application.
     */
    @Override
    public abstract List<JToolBar> createToolBars(Application a, @Nullable View p);

    /** This method is empty. */
    @Override
    public void initView(Application a, View p) {
    }

    /** This method is empty. */
    @Override
    public void destroyView(Application a, View p) {
    }

    /** This method is empty. */
    @Override
    public void initApplication(Application a) {
    }

    /** This method is empty. */
    @Override
    public void destroyApplication(Application a) {
    }

    @Override
    public URIChooser createOpenChooser(Application a, @Nullable View v) {
        URIChooser c = new JFileURIChooser();

        return c;
    }

    @Override
    public URIChooser createOpenDirectoryChooser(Application a, @Nullable View v) {
        JFileURIChooser c = new JFileURIChooser();
        c.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        return c;
    }

    @Override
    public URIChooser createSaveChooser(Application a, @Nullable View v) {
        JFileURIChooser c = new JFileURIChooser();
        return c;
    }

    /** Returns createOpenChooser. */
    @Override
    public URIChooser createImportChooser(Application a, @Nullable View v) {
        return createOpenChooser(a, v);
    }

    /** Returns createSaveChooser. */
    @Override
    public URIChooser createExportChooser(Application a, @Nullable View v) {
        return createSaveChooser(a, v);
    }

    /** 
     * {@inheritDoc}
     * The default value is true.
     */
    @Override
    public boolean isOpenLastURIOnLaunch() {
        return openLastURIOnLaunch;
    }

    /**
     * {@inheritDoc}
     * The default value is true.
     */
    @Override
    public boolean isAllowMultipleViewsPerURI() {
        return allowMultipleViewsForURI;
    }

    /** Whether the application may open multiple views for the same URI.
     * <p>
     * The default value is true.
     *
     * @param allowMultipleViewsForURI
     */
    public void setAllowMultipleViewsForURI(boolean allowMultipleViewsForURI) {
        this.allowMultipleViewsForURI = allowMultipleViewsForURI;
    }

    /** Whether the application should open the last opened URI on launch.
     * <p>
     * The default value is false.
     *
     * @param openLastURIOnLaunch
     */
    public void setOpenLastURIOnLaunch(boolean openLastURIOnLaunch) {
        this.openLastURIOnLaunch = openLastURIOnLaunch;
    }
}
