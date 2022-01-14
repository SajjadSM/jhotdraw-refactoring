package org.jhotdraw.gui;


/**
 * @see org.jhotdraw.gui.ActivityModel#NOTE_PROPERTY
 */
public class NoteProperty extends Name {
	public void updateProperties(JActivityView jActivityView) {
		jActivityView.getJActivityViewProduct().updateNote(jActivityView.getModel());
	}
}