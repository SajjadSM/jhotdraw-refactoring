package org.jhotdraw.samples.svg.gui;


import java.util.ArrayList;
import javax.swing.Action;
import java.util.List;
import java.util.Collections;
import java.io.Serializable;

public class ActionsToolBarProduct implements Serializable {
	private ArrayList<Action> actions;

	/**
	* Sets the actions for the "Action" popup menu in the toolbar. <p> This list may contain null items which are used to denote a separator in the popup menu. <p> Set this to null to set the drop down menus to the default actions.
	*/
	public void setPopupActions(List<Action> actions) {
		if (actions == null) {
			this.actions = null;
		} else {
			this.actions = new ArrayList<Action>();
			this.actions.addAll(actions);
		}
	}

	/**
	* Gets the actions of the "Action" popup menu in the toolbar. This list may contain null items which are used to denote a separator in the popup menu.
	* @return  An unmodifiable list with actions.
	*/
	public List<Action> getPopupActions() {
		if (actions == null) {
			actions = new ArrayList<Action>();
		}
		return Collections.unmodifiableList(actions);
	}
}