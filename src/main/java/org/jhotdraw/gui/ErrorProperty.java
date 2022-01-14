package org.jhotdraw.gui;


/**
 * @see org.jhotdraw.gui.ActivityModel#ERROR_PROPERTY
 */
public class ErrorProperty extends Name {
	public void updateProperties(JActivityView jActivityView) {
		jActivityView.getJActivityViewProduct().updateError(jActivityView.getModel(), jActivityView);
	}
}