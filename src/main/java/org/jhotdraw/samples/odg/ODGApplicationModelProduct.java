package org.jhotdraw.samples.odg;


import javax.swing.JToolBar;
import org.jhotdraw.draw.DrawingEditor;
import javax.swing.JButton;
import org.jhotdraw.draw.action.PickAttributesAction;
import org.jhotdraw.draw.action.ApplyAttributesAction;
import org.jhotdraw.draw.action.ButtonFactory;
import org.jhotdraw.util.ResourceBundleUtil;
import java.util.HashMap;
import org.jhotdraw.draw.AttributeKey;
import java.io.Serializable;
import org.jhotdraw.draw.AttributeKeys;

public class ODGApplicationModelProduct implements Serializable {
	/**
	* Creates toolbar buttons and adds them to the specified JToolBar
	*/
	public void addAttributesButtonsTo(JToolBar bar, DrawingEditor editor) {
		JButton b;
		b = bar.add(new PickAttributesAction(editor));
		b.setFocusable(false);
		b = bar.add(new ApplyAttributesAction(editor));
		b.setFocusable(false);
		bar.addSeparator();
		addColorButtonsTo(bar, editor);
		bar.addSeparator();
		addStrokeButtonsTo(bar, editor);
		bar.addSeparator();
		ButtonFactory.addFontButtonsTo(bar, editor);
	}

	public void addStrokeButtonsTo(JToolBar bar, DrawingEditor editor) {
		bar.add(ButtonFactory.createStrokeWidthButton(editor));
		bar.add(ButtonFactory.createStrokeDashesButton(editor));
		bar.add(ButtonFactory.createStrokeCapButton(editor));
		bar.add(ButtonFactory.createStrokeJoinButton(editor));
	}

	public void addColorButtonsTo(JToolBar bar, DrawingEditor editor) {
		ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.draw.Labels");
		HashMap<AttributeKey, Object> defaultAttributes = new HashMap<AttributeKey, Object>();
		ODGAttributeKeys.STROKE_GRADIENT.put(defaultAttributes, (Gradient) null);
		bar.add(ButtonFactory.createEditorColorButton(editor, AttributeKeys.STROKE_COLOR, ButtonFactory.WEBSAVE_COLORS,
				ButtonFactory.WEBSAVE_COLORS_COLUMN_COUNT, "attribute.strokeColor", labels, defaultAttributes));
		defaultAttributes = new HashMap<AttributeKey, Object>();
		ODGAttributeKeys.FILL_GRADIENT.put(defaultAttributes, (Gradient) null);
		bar.add(ButtonFactory.createEditorColorButton(editor, AttributeKeys.FILL_COLOR, ButtonFactory.WEBSAVE_COLORS,
				ButtonFactory.WEBSAVE_COLORS_COLUMN_COUNT, "attribute.fillColor", labels, defaultAttributes));
	}
}