package org.jhotdraw.gui;


/**
 * @see org.jhotdraw.gui.ActivityModel#CANCELABLE_PROPERTY
 */
public class CancelableProperty extends Name {
	public void updateProperties(JActivityView jActivityView) {
		jActivityView.updateCancelable();
	}
}