package org.jhotdraw.gui;


/**
 * @see org.jhotdraw.gui.Arrangeable.Arrangement#HORIZONTAL
 */
public class Horizontal extends NewValue {
	public void setArrangement(JMDIDesktopPane jMDIDesktopPane) {
		jMDIDesktopPane.arrangeFramesHorizontally();
	}
}