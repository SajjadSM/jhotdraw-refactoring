package org.jhotdraw.gui;


/**
 * @see org.jhotdraw.gui.Arrangeable.Arrangement#VERTICAL
 */
public class Vertical extends NewValue {
	public void setArrangement(JMDIDesktopPane jMDIDesktopPane) {
		jMDIDesktopPane.arrangeFramesVertically();
	}
}