package org.jhotdraw.app.action.view;


import java.lang.reflect.InvocationTargetException;
import org.jhotdraw.app.View;
import java.io.Serializable;

public class ViewPropertyActionProduct implements Serializable, Cloneable {
	private Object propertyValue;
	private String getterName;

	public Object getPropertyValue() {
		return propertyValue;
	}

	public void setPropertyValue(Object propertyValue) {
		this.propertyValue = propertyValue;
	}

	public void setGetterName(String getterName) {
		this.getterName = getterName;
	}

	public boolean isSelected(ViewPropertyAction viewPropertyAction) throws IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		boolean isSelected = false;
		View p = viewPropertyAction.getActiveView();
		if (p != null) {
			Object value = p.getClass().getMethod(getterName, (Class[]) null).invoke(p);
			isSelected = value == propertyValue
					|| value != null && propertyValue != null && value.equals(propertyValue);
			try {
				value = p.getClass().getMethod(getterName, (Class[]) null).invoke(p);
				isSelected = value == propertyValue
						|| value != null && propertyValue != null && value.equals(propertyValue);
			} catch (Throwable e) {
				InternalError error = new InternalError(
						"Method invocation failed. getter:" + getterName + " object:" + p);
				error.initCause(e);
				throw error;
			}
		}
		return isSelected;
	}

	public Object clone() throws CloneNotSupportedException {
		return (ViewPropertyActionProduct) super.clone();
	}
}