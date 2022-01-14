package org.jhotdraw.app.action;


import java.io.Serializable;

public class AbstractViewActionProduct implements Serializable, Cloneable {
	private String propertyName;

	public String getPropertyName() {
		return propertyName;
	}

	/**
	* Sets the property name.
	*/
	public void setPropertyName(String name) {
		this.propertyName = name;
		if (name != null) {
			updateView();
		}
	}

	/**
	* This method is invoked, when the property changed and when the view changed.
	*/
	public void updateView() {
	}

	public Object clone() throws CloneNotSupportedException {
		return (AbstractViewActionProduct) super.clone();
	}
}