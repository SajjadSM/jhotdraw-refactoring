/*
 * @(#)OSXClipboard.java
 * 
 * Copyright (c) 2009-2010 by the original authors of JHotDraw and all its
 * contributors. All rights reserved.
 * 
 * You may not use, copy or modify this file, except in compliance with the 
 * license agreement you entered into with the copyright holders. For details
 * see accompanying license terms.
 */
package org.jhotdraw.gui.datatransfer;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.Transferable;

/**
 * OSXClipboard.
 *
 * @author Werner Randelshofer
 * @version $Id$
 */
public class OSXClipboard extends AWTClipboard {

    public OSXClipboard(Clipboard target) {
        super(target);
    }

    @Override
    public Transferable getContents(Object requestor) {
        Transferable t = super.getContents(requestor);

            try {
                Class c = Class.forName("ch.randelshofer.quaqua.osx.OSXClipboardTransferable");
                @SuppressWarnings("unchecked")
                boolean isAvailable = (Boolean) c.getMethod("isNativeCodeAvailable").invoke(null);
                if (isAvailable) {
                   CompositeTransferable ct = ct(t, c);
				t = ct;
                }
            } catch (Throwable ex) {
                // silently suppress
            }

        return t;
    }

	private CompositeTransferable ct(Transferable t, Class c)
			throws java.lang.InstantiationException, java.lang.IllegalAccessException {
		CompositeTransferable ct = new CompositeTransferable();
		ct.add(t);
		ct.add((Transferable) c.newInstance());
		return ct;
	}
}
