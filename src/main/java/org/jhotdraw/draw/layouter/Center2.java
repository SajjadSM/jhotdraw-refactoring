package org.jhotdraw.draw.layouter;


import org.jhotdraw.draw.Figure;
import java.awt.geom.Rectangle2D;
import org.jhotdraw.geom.Insets2D;
import java.awt.geom.Point2D;

/**
 * @see org.jhotdraw.draw.AttributeKeys.Alignment#CENTER
 */
public class Center2 extends CompositeAlignment2 {
	public void y(Figure child, Rectangle2D.Double layoutBounds, Insets2D.Double layoutInsets, Insets2D.Double insets,
			double y, double width, double height) {
		child.setBounds(new Point2D.Double(layoutBounds.x + (layoutBounds.width - width) / 2d, y + insets.top),
				new Point2D.Double(layoutBounds.x + (layoutBounds.width + width) / 2d, y + insets.top + height));
	}
}