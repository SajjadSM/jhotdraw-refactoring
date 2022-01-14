package org.jhotdraw.draw.layouter;


import org.jhotdraw.draw.Figure;
import org.jhotdraw.geom.Insets2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Point2D;

/**
 * @see org.jhotdraw.draw.AttributeKeys.Alignment#CENTER
 */
public class Center extends CompositeAlignment {
	public void x(Figure child, double x, Insets2D.Double insets, Rectangle2D.Double layoutBounds,
			Insets2D.Double layoutInsets, double width, double height) {
		child.setBounds(
				new Point2D.Double(x + insets.left,
						layoutBounds.y + layoutInsets.top + (layoutBounds.height - height) / 2d),
				new Point2D.Double(x + insets.left + width,
						layoutBounds.y + layoutInsets.top + (layoutBounds.height + height) / 2d));
	}
}