package org.jhotdraw.gui;


import javax.swing.JLabel;
import java.io.Serializable;

public class JActivityViewProduct implements Serializable {
	private javax.swing.JLabel errorLabel;
	private javax.swing.JLabel warningLabel;
	private javax.swing.JLabel noteLabel;

	public javax.swing.JLabel getErrorLabel() {
		return errorLabel;
	}

	public void setErrorLabel(javax.swing.JLabel errorLabel) {
		this.errorLabel = errorLabel;
	}

	public javax.swing.JLabel getWarningLabel() {
		return warningLabel;
	}

	public void setWarningLabel(javax.swing.JLabel warningLabel) {
		this.warningLabel = warningLabel;
	}

	public javax.swing.JLabel getNoteLabel() {
		return noteLabel;
	}

	public void setNoteLabel(javax.swing.JLabel noteLabel) {
		this.noteLabel = noteLabel;
	}

	public void updateError(ActivityModel thisModel, JActivityView jActivityView) {
		String txt = thisModel.getError();
		errorLabel.setText(txt);
		updateLabelVisibility(thisModel, jActivityView);
	}

	public void updateLabelVisibility(ActivityModel thisModel, JActivityView jActivityView) {
		boolean isError = thisModel.getError() != null;
		boolean isWarning = thisModel.getWarning() != null;
		errorLabel.setVisible(isError);
		warningLabel.setVisible(!isError && isWarning);
		noteLabel.setVisible(!isError && !isWarning);
		jActivityView.revalidate();
	}

	public void updateWarning(ActivityModel thisModel, JActivityView jActivityView) {
		String txt = thisModel.getWarning();
		warningLabel.setText(txt);
		updateLabelVisibility(thisModel, jActivityView);
	}

	public void updateNote(ActivityModel thisModel) {
		String txt = thisModel.getNote();
		noteLabel.setText(thisModel.getNote());
	}
}