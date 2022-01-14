package org.jhotdraw.gui;


/**
 * @see org.jhotdraw.gui.Arrangeable.Arrangement#CASCADE
 */
public class Cascade extends NewValue {
	public void setArrangement(JMDIDesktopPane jMDIDesktopPane) {
		jMDIDesktopPane.arrangeFramesCascading();
	}
}