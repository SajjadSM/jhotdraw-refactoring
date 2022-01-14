package org.jhotdraw.app.osx;


import java.awt.event.ActionListener;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;
import java.awt.event.ActionEvent;

public class OSXAdapterProduct {
	private ActionListener targetAction;
	private Object targetObject;
	private Method targetMethod;

	public ActionListener getTargetAction() {
		return targetAction;
	}

	public void setTargetAction(ActionListener targetAction) {
		this.targetAction = targetAction;
	}

	public void setTargetObject(Object targetObject) {
		this.targetObject = targetObject;
	}

	public void setTargetMethod(Method targetMethod) {
		this.targetMethod = targetMethod;
	}

	/**
	* Override this method to perform any operations on the event that comes with the various callbacks. See setOpenFileHandler above for an example.
	*/
	public boolean callTarget(Object appleEvent, String thisProxySignature, OSXAdapter oSXAdapter)
			throws InvocationTargetException, IllegalAccessException {
		if (targetAction != null) {
			targetAction.actionPerformed(new ActionEvent(oSXAdapter, ActionEvent.ACTION_PERFORMED, thisProxySignature));
			return true;
		} else {
			Object result = targetMethod.invoke(targetObject, (Object[]) null);
			if (result == null) {
				return true;
			}
			return Boolean.valueOf(result.toString()).booleanValue();
		}
	}
}