/*
 * @(#)HorizontalLayouter.java
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
 * in horizontal direction.
 * <p>
 * The preferred size of the figures is used to determine the layout.
 * This may cause some figures to resize.
 * <p>
 * The HorizontalLayouter honors the LAYOUT_INSETS and the COMPOSITE_ALIGNMENT
 * AttributeKey when laying out a CompositeFigure.
 * <p>
 * If COMPOSITE_ALIGNMENT is not set on the composite figure, 
 * the layout assigns the same height to all figures.
 * 
 * 
 * @author Werner Randelshofer
 * @version $Id$
 */
public class HorizontalLayouter extends AbstractLayouter {

    @Override
    public Rectangle2D.Double calculateLayout(CompositeFigure compositeFigure, Point2D.Double anchor, Point2D.Double lead) {
        Insets2D.Double layoutInsets = compositeFigure.get(LAYOUT_INSETS);

        Rectangle2D.Double layoutBounds = new Rectangle2D.Double(anchor.x, anchor.y, 0, 0);
        for (Figure child : compositeFigure.getChildren()) {
            if (child.isVisible()) {
                Dimension2DDouble preferredSize = child.getPreferredSize();
                Insets2D.Double ins = getInsets(child);
                layoutBounds.height = Math.max(layoutBounds.height, preferredSize.height + ins.top + ins.bottom);
                layoutBounds.width += preferredSize.width + ins.left + ins.right;
            }
        }
        layoutBounds.width += layoutInsets.left + layoutInsets.right;
        layoutBounds.height += layoutInsets.top + layoutInsets.bottom;

        return layoutBounds;
    }

    @Override
    public Rectangle2D.Double layout(CompositeFigure compositeFigure, Point2D.Double anchor, Point2D.Double lead) {
        double x = x(compositeFigure, anchor, lead);
		Rectangle2D.Double layoutBounds = calculateLayout(compositeFigure, anchor, lead);
        return layoutBounds;
    }

	private double x(CompositeFigure compositeFigure, Point2D.Double anchor, Point2D.Double lead) {
		Insets2D.Double layoutInsets = compositeFigure.get(LAYOUT_INSETS);
		Alignment compositeAlignment = compositeFigure.get(COMPOSITE_ALIGNMENT);
		Rectangle2D.Double layoutBounds = calculateLayout(compositeFigure, anchor, lead);
		double x = layoutBounds.x + layoutInsets.left;
		for (Figure child : compositeFigure.getChildren()) {
			if (child.isVisible()) {
				Insets2D.Double insets = getInsets(child);
				double width = child.getPreferredSize().width;
				double height = child.getPreferredSize().height;
				getCompositeAlignmentObject(compositeAlignment).x(child, x, insets, layoutBounds, layoutInsets, width,
						height);
				x += width + insets.left + insets.right;
			}
		}
		return x;
	}

	private CompositeAlignment getCompositeAlignmentObject(Alignment compositeAlignment) {
		switch (compositeAlignment) {
		case LEADING:
			return new Leading();
		case TRAILING:
			return new Trailing();
		case CENTER:
			return new Center();
		case BLOCK:
			return new Block();
		}
		return null;
	}
}
