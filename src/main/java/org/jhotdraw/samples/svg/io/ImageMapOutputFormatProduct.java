package org.jhotdraw.samples.svg.io;


import java.awt.geom.AffineTransform;
import java.awt.Rectangle;
import net.n3.nanoxml.IXMLElement;
import org.jhotdraw.samples.svg.figures.SVGFigure;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.Shape;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Rectangle2D;
import org.jhotdraw.draw.AttributeKeys;
import org.jhotdraw.samples.svg.SVGAttributeKeys;

public class ImageMapOutputFormatProduct {
	private AffineTransform drawingTransform = new AffineTransform();
	private Rectangle bounds = new Rectangle(0, 0, Integer.MAX_VALUE, Integer.MAX_VALUE);

	public void setDrawingTransform(AffineTransform drawingTransform) {
		this.drawingTransform = drawingTransform;
	}

	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}

	/**
	* Writes the <code>shape</code>, <code>coords</code>, <code>href</code>, <code>nohref</code> Attribute for the specified figure and ellipse.
	* @return  Returns true, if the circle is inside of the image bounds.
	*/
	public boolean writeCircleAttributes(IXMLElement elem, SVGFigure f, java.awt.geom.Ellipse2D.Double ellipse) {
		AffineTransform t = AttributeKeys.TRANSFORM.getClone(f);
		if (t == null) {
			t = drawingTransform;
		} else {
			t.preConcatenate(drawingTransform);
		}
		if ((t.getType() & (AffineTransform.TYPE_UNIFORM_SCALE | AffineTransform.TYPE_TRANSLATION)) == t.getType()
				&& ellipse.width == ellipse.height) {
			Point2D.Double start = new Point2D.Double(ellipse.x, ellipse.y);
			Point2D.Double end = new Point2D.Double(ellipse.x + ellipse.width, ellipse.y + ellipse.height);
			t.transform(start, start);
			t.transform(end, end);
			ellipse.x = Math.min(start.x, end.x);
			ellipse.y = Math.min(start.y, end.y);
			ellipse.width = Math.abs(start.x - end.x);
			ellipse.height = Math.abs(start.y - end.y);
			elem.setAttribute("shape", "circle");
			elem.setAttribute("coords", (int) (ellipse.x + ellipse.width / 2d) + ","
					+ (int) (ellipse.y + ellipse.height / 2d) + "," + (int) (ellipse.width / 2d));
			writeHrefAttribute(elem, f);
			return bounds.intersects(ellipse.getBounds());
		} else {
			return writePolyAttributes(elem, f, (Shape) ellipse);
		}
	}

	/**
	* Writes the <code>shape</code>, <code>coords</code>, <code>href</code>, <code>nohref</code> Attribute for the specified figure and shape.
	* @return  Returns true, if the polygon is inside of the image bounds.
	*/
	public boolean writePolyAttributes(IXMLElement elem, SVGFigure f, Shape shape) {
		AffineTransform t = AttributeKeys.TRANSFORM.getClone(f);
		if (t == null) {
			t = drawingTransform;
		} else {
			t.preConcatenate(drawingTransform);
		}
		StringBuilder buf = new StringBuilder();
		float[] coords = new float[6];
		Path2D.Double path = new Path2D.Double();
		for (PathIterator i = shape.getPathIterator(t, 1.5f); !i.isDone(); i.next()) {
			switch (i.currentSegment(coords)) {
			case PathIterator.SEG_MOVETO:
				if (buf.length() != 0) {
					throw new IllegalArgumentException("Illegal shape " + shape);
				}
				if (buf.length() != 0) {
					buf.append(',');
				}
				buf.append((int) coords[0]);
				buf.append(',');
				buf.append((int) coords[1]);
				path.moveTo(coords[0], coords[1]);
				break;
			case PathIterator.SEG_LINETO:
				if (buf.length() != 0) {
					buf.append(',');
				}
				buf.append((int) coords[0]);
				buf.append(',');
				buf.append((int) coords[1]);
				path.lineTo(coords[0], coords[1]);
				break;
			case PathIterator.SEG_CLOSE:
				path.closePath();
				break;
			default:
				throw new InternalError("Illegal segment type " + i.currentSegment(coords));
			}
		}
		elem.setAttribute("shape", "poly");
		elem.setAttribute("coords", buf.toString());
		writeHrefAttribute(elem, f);
		return path.intersects(new Rectangle2D.Float(bounds.x, bounds.y, bounds.width, bounds.height));
	}

	/**
	* Writes the <code>shape</code>, <code>coords</code>, <code>href</code>, <code>nohref</code> Attribute for the specified figure and rectangle.
	* @return  Returns true, if the rect is inside of the image bounds.
	*/
	public boolean writeRectAttributes(IXMLElement elem, SVGFigure f, java.awt.geom.Rectangle2D.Double rect) {
		AffineTransform t = AttributeKeys.TRANSFORM.getClone(f);
		if (t == null) {
			t = drawingTransform;
		} else {
			t.preConcatenate(drawingTransform);
		}
		if ((t.getType() & (AffineTransform.TYPE_UNIFORM_SCALE | AffineTransform.TYPE_TRANSLATION)) == t.getType()) {
			Point2D.Double start = new Point2D.Double(rect.x, rect.y);
			Point2D.Double end = new Point2D.Double(rect.x + rect.width, rect.y + rect.height);
			t.transform(start, start);
			t.transform(end, end);
			Rectangle r = new Rectangle((int) Math.min(start.x, end.x), (int) Math.min(start.y, end.y),
					(int) Math.abs(start.x - end.x), (int) Math.abs(start.y - end.y));
			elem.setAttribute("shape", "rect");
			elem.setAttribute("coords", r.x + "," + r.y + "," + (r.x + r.width) + "," + (r.y + r.height));
			writeHrefAttribute(elem, f);
			return bounds.intersects(r);
		} else {
			return writePolyAttributes(elem, f, (Shape) rect);
		}
	}

	public void writeHrefAttribute(IXMLElement elem, SVGFigure f) {
		String link = f.get(SVGAttributeKeys.LINK);
		if (link != null && link.trim().length() > 0) {
			elem.setAttribute("href", link);
			elem.setAttribute("title", link);
			elem.setAttribute("alt", link);
			String linkTarget = f.get(SVGAttributeKeys.LINK_TARGET);
			if (linkTarget != null && linkTarget.trim().length() > 0) {
				elem.setAttribute("target", linkTarget);
			}
		} else {
			elem.setAttribute("nohref", "true");
		}
	}
}