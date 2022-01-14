package org.jhotdraw.gui;


/**
 * @see org.jhotdraw.gui.ActivityModel#CANCELED_PROPERTY
 */
public class CanceledProperty extends Name {
	public void updateProperties(JActivityView jActivityView) {
		jActivityView.updateCanceled();
		jActivityView.updateCancelable();
	}
}