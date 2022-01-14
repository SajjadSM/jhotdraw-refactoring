package org.jhotdraw.gui;


/**
 * @see org.jhotdraw.gui.ActivityModel#INDETERMINATE_PROPERTY
 */
public class IndeterminateProperty extends Name {
	public void updateProperties(JActivityView jActivityView) {
		jActivityView.updateIndeterminate();
	}
}