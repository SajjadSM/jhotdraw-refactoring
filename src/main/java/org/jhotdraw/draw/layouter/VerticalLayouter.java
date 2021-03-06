/*
 * @(#)VerticalLayouter.java
 *
 * Copyright (c) 1996-2010 by the original authors of JHotDraw and all its
 * contributors. All rights reserved.
 *
 * You may not use, copy or modify this file, except in compliance with the 
 * license agreement you entered into with the copyright holders. For details
 * see accompanying license terms.
 */
package org.jhotdraw.draw.layouter;

import org.jhotdraw.draw.*;
import org.jhotdraw.draw.CompositeFigure;
import java.awt.geom.*;
import org.jhotdraw.geom.*;
import static org.jhotdraw.draw.AttributeKeys.*;

/**
 * A {@link Layouter} which lays out all children of a {@link CompositeFigure}
 * in vertical direction.
 * <p>
 * The preferred size of the figures is used to determine the layout.
 * This may cause some figures to resize.
 * <p>
 * The VerticalLayouter honors the LAYOUT_INSETS and the COMPOSITE_ALIGNMENT
 * AttributeKey when laying out a CompositeFigure.
 * <p>
 * If COMPOSITE_ALIGNMENT is not set on the composite figure, 
 * the layout assigns the same width to all figures.
 * 
 * 
 * @author Werner Randelshofer
 * @version $Id$
 */
public class VerticalLayouter extends AbstractLayouter {

    /**
     * This alignment is used, when 
     */
    private Alignment defaultAlignment = Alignment.BLOCK;

    @Override
    public Rectangle2D.Double calculateLayout(CompositeFigure layoutable, Point2D.Double anchor, Point2D.Double lead) {
        Insets2D.Double layoutInsets = layoutable.get(LAYOUT_INSETS);
        if (layoutInsets == null) {
            layoutInsets = new Insets2D.Double(0, 0, 0, 0);
        }
        Rectangle2D.Double layoutBounds = new Rectangle2D.Double(anchor.x, anchor.y, 0, 0);
        for (Figure child : layoutable.getChildren()) {
            if (child.isVisible()) {
                Dimension2DDouble preferredSize = child.getPreferredSize();
                Insets2D.Double ins = getInsets(child);
                layoutBounds.width = Math.max(layoutBounds.width, preferredSize.width + ins.left + ins.right);
                layoutBounds.height += preferredSize.height + ins.top + ins.bottom;
            }
        }
        layoutBounds.width += layoutInsets.left + layoutInsets.right;
        layoutBounds.height += layoutInsets.top + layoutInsets.bottom;

        return layoutBounds;
    }

    @Override
    public Rectangle2D.Double layout(CompositeFigure layoutable, Point2D.Double anchor, Point2D.Double lead) {
        double y = y(layoutable, anchor, lead);
		Rectangle2D.Double layoutBounds = calculateLayout(layoutable, anchor, lead);
        return layoutBounds;
    }

	private double y(CompositeFigure layoutable, Point2D.Double anchor, Point2D.Double lead) {
		Insets2D.Double layoutInsets = layoutInsets(layoutable);
		Alignment compositeAlignment = layoutable.get(COMPOSITE_ALIGNMENT);
		Rectangle2D.Double layoutBounds = calculateLayout(layoutable, anchor, lead);
		double y = layoutBounds.y + layoutInsets.top;
		for (Figure child : layoutable.getChildren()) {
			if (child.isVisible()) {
				Insets2D.Double insets = getInsets(child);
				double height = child.getPreferredSize().height;
				double width = child.getPreferredSize().width;
				getCompositeAlignment2Object(compositeAlignment).y(child, layoutBounds, layoutInsets, insets, y, width,
						height);
				y += height + insets.top + insets.bottom;
			}
		}
		return y;
	}

	private Insets2D.Double layoutInsets(CompositeFigure layoutable) {
		Insets2D.Double layoutInsets = layoutable.get(LAYOUT_INSETS);
		if (layoutInsets == null) {
			layoutInsets = new Insets2D.Double();
		}
		return layoutInsets;
	}

	private CompositeAlignment2 getCompositeAlignment2Object(Alignment compositeAlignment) {
		switch (compositeAlignment) {
		case LEADING:
			return new Leading2();
		case TRAILING:
			return new Trailing2();
		case CENTER:
			return new Center2();
		case BLOCK:
			return new Block2();
		}
		return null;
	}
}
