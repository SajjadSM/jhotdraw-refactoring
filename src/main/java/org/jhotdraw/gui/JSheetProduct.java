package org.jhotdraw.gui;


import javax.swing.event.EventListenerList;
import org.jhotdraw.gui.event.SheetListener;
import javax.swing.JOptionPane;
import edu.umd.cs.findbugs.annotations.Nullable;
import org.jhotdraw.gui.event.SheetEvent;
import javax.swing.JFileChooser;
import java.io.Serializable;

public class JSheetProduct implements Serializable {
	private EventListenerList listenerList = new EventListenerList();

	/**
	* Adds a sheet listener.
	*/
	public void addSheetListener(SheetListener l) {
		listenerList.add(SheetListener.class, l);
	}

	/**
	* Removes a sheet listener.
	*/
	public void removeSheetListener(SheetListener l) {
		listenerList.remove(SheetListener.class, l);
	}

	/**
	* Notify all listeners that have registered interest for notification on this event type. The event instance is lazily created using the parameters passed into the fire method.
	*/
	public void fireOptionSelected(JOptionPane pane, int option, @Nullable Object value, @Nullable Object inputValue,
			JSheet jSheet) {
		SheetEvent sheetEvent = null;
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == SheetListener.class) {
				if (sheetEvent == null) {
					sheetEvent = new SheetEvent(jSheet, pane, option, value, inputValue);
				}
				((SheetListener) listeners[i + 1]).optionSelected(sheetEvent);
			}
		}
	}

	/**
	* Notify all listeners that have registered interest for notification on this event type. The event instance is lazily created using the parameters passed into the fire method.
	*/
	public void fireOptionSelected(JFileChooser pane, int option, JSheet jSheet) {
		SheetEvent sheetEvent = null;
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == SheetListener.class) {
				if (sheetEvent == null) {
					sheetEvent = new SheetEvent(jSheet, pane, option, null);
				}
				((SheetListener) listeners[i + 1]).optionSelected(sheetEvent);
			}
		}
	}

	/**
	* Notify all listeners that have registered interest for notification on this event type. The event instance is lazily created using the parameters passed into the fire method.
	*/
	public void fireOptionSelected(URIChooser pane, int option, JSheet jSheet) {
		SheetEvent sheetEvent = null;
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == SheetListener.class) {
				if (sheetEvent == null) {
					sheetEvent = new SheetEvent(jSheet, pane, option, null);
				}
				((SheetListener) listeners[i + 1]).optionSelected(sheetEvent);
			}
		}
	}
}