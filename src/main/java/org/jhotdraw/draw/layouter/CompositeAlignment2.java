package org.jhotdraw.draw.layouter;


import org.jhotdraw.draw.Figure;
import java.awt.geom.Rectangle2D;
import org.jhotdraw.geom.Insets2D;

public abstract class CompositeAlignment2 {
	public abstract void y(Figure child, Rectangle2D.Double layoutBounds, Insets2D.Double layoutInsets,
			Insets2D.Double insets, double y, double width, double height);
}