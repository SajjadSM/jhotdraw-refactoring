package org.jhotdraw.gui;


import javax.swing.JFormattedTextField;
import java.io.Serializable;

public class JLifeFormattedTextAreaProduct implements Serializable {
	private JLifeFormattedTextField formattedTextFieldAdapter;

	public JLifeFormattedTextField getFormattedTextFieldAdapter() {
		return formattedTextFieldAdapter;
	}

	public void setFormattedTextFieldAdapter(JLifeFormattedTextField formattedTextFieldAdapter) {
		this.formattedTextFieldAdapter = formattedTextFieldAdapter;
	}

	public void setFormatterFactory(JFormattedTextField.AbstractFormatterFactory newValue) {
		formattedTextFieldAdapter.setFormatterFactory(newValue);
	}

	public JFormattedTextField.AbstractFormatterFactory getFormatterFactory() {
		return formattedTextFieldAdapter.getFormatterFactory();
	}
}