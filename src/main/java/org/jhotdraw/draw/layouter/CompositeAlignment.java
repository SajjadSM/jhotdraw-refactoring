package org.jhotdraw.draw.layouter;


import org.jhotdraw.draw.Figure;
import org.jhotdraw.geom.Insets2D;
import java.awt.geom.Rectangle2D;

public abstract class CompositeAlignment {
	public abstract void x(Figure child, double x, Insets2D.Double insets, Rectangle2D.Double layoutBounds,
			Insets2D.Double layoutInsets, double width, double height);
}