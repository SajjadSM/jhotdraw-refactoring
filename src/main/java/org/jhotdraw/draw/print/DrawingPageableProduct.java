package org.jhotdraw.draw.print;


import org.jhotdraw.draw.Drawing;
import java.awt.Graphics;
import java.awt.print.PageFormat;
import java.awt.print.PrinterException;
import java.awt.print.Printable;
import java.awt.geom.AffineTransform;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import org.jhotdraw.draw.Figure;

public class DrawingPageableProduct {
	private Drawing drawing;
	private boolean isAutorotate = false;

	public void setDrawing(Drawing drawing) {
		this.drawing = drawing;
	}

	public int printPage(Graphics graphics, PageFormat pageFormat, int pageIndex, DrawingPageable drawingPageable)
			throws PrinterException {
		if (pageIndex < 0 || pageIndex >= drawingPageable.getNumberOfPages()) {
			return Printable.NO_SUCH_PAGE;
		}
		if (drawing.getChildCount() > 0) {
			AffineTransform tx = tx(graphics, pageFormat);
			Graphics2D g = (Graphics2D) graphics;
			drawingPageable.setRenderingHints(g);
			g.transform(tx);
		}
		return Printable.PAGE_EXISTS;
	}

	public AffineTransform tx(Graphics graphics, PageFormat pageFormat) {
		drawing(graphics);
		Rectangle2D.Double drawBounds = null;
		for (Figure f : drawing.getChildren()) {
			if (drawBounds == null) {
				drawBounds = f.getDrawingArea();
			} else {
				drawBounds.add(f.getDrawingArea());
			}
		}
		AffineTransform tx = new AffineTransform();
		tx.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
		if (isAutorotate && drawBounds.width > drawBounds.height
				&& pageFormat.getImageableWidth() < pageFormat.getImageableHeight()) {
			double scaleFactor = Math.min(pageFormat.getImageableWidth() / drawBounds.height,
					pageFormat.getImageableHeight() / drawBounds.width);
			tx.scale(scaleFactor, scaleFactor);
			tx.translate(drawBounds.height, 0d);
			tx.rotate(Math.PI / 2d, 0, 0);
			tx.translate(-drawBounds.x, -drawBounds.y);
		} else {
			double scaleFactor = Math.min(pageFormat.getImageableWidth() / drawBounds.width,
					pageFormat.getImageableHeight() / drawBounds.height);
			tx.scale(scaleFactor, scaleFactor);
			tx.translate(-drawBounds.x, -drawBounds.y);
		}
		return tx;
	}

	public void drawing(Graphics graphics) {
		Graphics2D g = (Graphics2D) graphics;
		drawing.draw(g);
	}
}