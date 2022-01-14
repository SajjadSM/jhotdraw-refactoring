package org.jhotdraw.gui;


import javax.swing.JOptionPane;
import java.awt.Component;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import java.awt.Window;
import java.awt.Frame;
import java.awt.Dialog;
import javax.swing.JComponent;
import java.awt.BorderLayout;
import javax.swing.border.EmptyBorder;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.plaf.OptionPaneUI;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.io.Serializable;

public class JSheetProduct2 implements Serializable {
	private JSheetProduct jSheetProduct = new JSheetProduct();

	public JSheetProduct getJSheetProduct() {
		return jSheetProduct;
	}

	/**
	* Notify all listeners that have registered interest for notification on this event type. The event instance is lazily created using the parameters passed into the fire method.
	*/
	public void fireOptionSelected(JOptionPane pane, JSheet jSheet) {
		Object value = pane.getValue();
		int option;
		if (value == null) {
			option = JOptionPane.CLOSED_OPTION;
		} else {
			if (pane.getOptions() == null) {
				if (value instanceof Integer) {
					option = ((Integer) value).intValue();
				} else {
					option = JOptionPane.CLOSED_OPTION;
				}
			} else {
				option = JOptionPane.CLOSED_OPTION;
				Object[] options = pane.getOptions();
				for (int i = 0, n = options.length; i < n; i++) {
					if (options[i].equals(value)) {
						option = i;
						break;
					}
				}
				if (option == JOptionPane.CLOSED_OPTION) {
					value = null;
				}
			}
		}
		jSheetProduct.fireOptionSelected(pane, option, value, pane.getInputValue(), jSheet);
	}

	public static JSheet createSheet(final JOptionPane pane, Component parent, int style) {
		JPopupMenu popup = parent instanceof JPopupMenu ? (JPopupMenu) parent
				: (JPopupMenu) SwingUtilities.getAncestorOfClass(JPopupMenu.class, parent);
		if (popup != null) {
			parent = popup.getInvoker();
		}
		Window window = JSheet.getWindowForComponent(parent);
		final JSheet sheet;
		boolean isUndecorated;
		if (window instanceof Frame) {
			isUndecorated = ((Frame) window).isUndecorated();
			sheet = new JSheet((Frame) window);
		} else {
			isUndecorated = ((Dialog) window).isUndecorated();
			sheet = new JSheet((Dialog) window);
		}
		JComponent contentPane = (JComponent) sheet.getContentPane();
		contentPane.setLayout(new BorderLayout());
		if (JSheet.isNativeSheetSupported() && !isUndecorated) {
			contentPane.setBorder(new EmptyBorder(12, 0, 0, 0));
		}
		contentPane.add(pane, BorderLayout.CENTER);
		sheet.setResizable(false);
		sheet.addWindowListener(new WindowAdapter() {
			private boolean gotFocus = false;

			@Override
			public void windowClosing(WindowEvent we) {
				pane.setValue(null);
			}

			@Override
			public void windowClosed(WindowEvent we) {
				if (pane.getValue() == JOptionPane.UNINITIALIZED_VALUE) {
					sheet.fireOptionSelected(pane);
				}
			}

			@Override
			public void windowGainedFocus(WindowEvent we) {
				if (!gotFocus) {
					OptionPaneUI ui = pane.getUI();
					if (ui != null) {
						ui.selectInitialValue(pane);
					}
					gotFocus = true;
				}
			}
		});
		sheet.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent ce) {
				pane.setValue(JOptionPane.UNINITIALIZED_VALUE);
			}
		});
		pane.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent event) {
				if (sheet.isVisible() && event.getSource() == pane
						&& (event.getPropertyName().equals(JOptionPane.VALUE_PROPERTY)) && event.getNewValue() != null
						&& event.getNewValue() != JOptionPane.UNINITIALIZED_VALUE) {
					sheet.setVisible(false);
					sheet.fireOptionSelected(pane);
				}
			}
		});
		sheet.pack();
		return sheet;
	}
}