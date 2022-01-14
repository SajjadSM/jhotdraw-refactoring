package org.jhotdraw.gui;


/**
 * @see org.jhotdraw.gui.ActivityModel#CLOSED_PROPERTY
 */
public class ClosedProperty extends Name {
	public void updateProperties(JActivityView jActivityView) {
		jActivityView.updateCancelable();
		jActivityView.updateCanceled();
		jActivityView.updateClosed();
	}
}