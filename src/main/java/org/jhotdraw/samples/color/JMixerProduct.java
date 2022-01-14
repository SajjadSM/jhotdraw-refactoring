/**
 * @(#)JMixer.java Copyright (c) 2008 by the original authors of JHotDraw and all its contributors. All rights reserved. You may not use, copy or modify this file, except in compliance with the license agreement you entered into with the copyright holders. For details see accompanying license terms.
 */
package org.jhotdraw.samples.color;


import javax.swing.JToggleButton;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.io.Serializable;

public class JMixerProduct implements Serializable {
	private javax.swing.JToggleButton disclosureButton;
	private javax.swing.JPanel mixerPanel;

	public javax.swing.JToggleButton getDisclosureButton() {
		return disclosureButton;
	}

	public void setDisclosureButton(javax.swing.JToggleButton disclosureButton) {
		this.disclosureButton = disclosureButton;
	}

	public javax.swing.JPanel getMixerPanel() {
		return mixerPanel;
	}

	public void setMixerPanel(javax.swing.JPanel mixerPanel) {
		this.mixerPanel = mixerPanel;
	}

	public void mixerDisclosurePerformed(java.awt.event.ActionEvent evt) {
		mixerPanel.setVisible(disclosureButton.isSelected());
		mixerPanel.getParent().validate();
	}
}