package org.jhotdraw.gui;


/**
 * @see org.jhotdraw.gui.ActivityModel#WARNING_PROPERTY
 */
public class WarningProperty extends Name {
	public void updateProperties(JActivityView jActivityView) {
		jActivityView.getJActivityViewProduct().updateWarning(jActivityView.getModel(), jActivityView);
	}
}