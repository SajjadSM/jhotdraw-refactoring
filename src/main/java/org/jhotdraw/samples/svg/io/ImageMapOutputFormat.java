/*
 * @(#)ImageMapOutputFormat.java
 *
 * Copyright (c) 2007-2010 by the original authors of JHotDraw and all its
 * contributors. All rights reserved.
 *
 * You may not use, copy or modify this file, except in compliance with the 
 * license agreement you entered into with the copyright holders. For details
 * see accompanying license terms.
 */
package org.jhotdraw.samples.svg.io;

import org.jhotdraw.gui.filechooser.ExtensionFileFilter;
import org.jhotdraw.draw.io.OutputFormat;
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.geom.*;
import java.io.*;
import java.net.URI;
import javax.swing.*;
import net.n3.nanoxml.*;
import org.jhotdraw.draw.*;
import org.jhotdraw.geom.*;
import org.jhotdraw.gui.datatransfer.*;
import org.jhotdraw.samples.svg.figures.*;
import static org.jhotdraw.samples.svg.SVGAttributeKeys.*;
import org.jhotdraw.util.*;

/**
 * ImageMapOutputFormat exports a SVG drawing as an HTML 4.01 <code>MAP</code>
 * element.
 * For more information see:
 * http://www.w3.org/TR/html401/struct/objects.html#h-13.6.2
 *
 *
 * @author Werner Randelshofer
 * @version $Id$
 */
public class ImageMapOutputFormat implements OutputFormat {

    private ImageMapOutputFormatProduct imageMapOutputFormatProduct = new ImageMapOutputFormatProduct();
	private static boolean DEBUG = true;
    /**
     * Set this to true, if AREA elements with <code>nohref="true"</code>
     * shall e included in the image map.
     */
    private boolean isIncludeNohref = false;
    /** Creates a new instance. */
    public ImageMapOutputFormat() {
    }

    @Override
    public javax.swing.filechooser.FileFilter getFileFilter() {
        return new ExtensionFileFilter("HTML Image Map", "html");
    }

    @Override
    public String getFileExtension() {
        return "html";
    }

    @Override
    public JComponent getOutputFormatAccessory() {
        return null;
    }
    @Override
    public void write(URI uri, Drawing drawing) throws IOException {
        write(new File(uri),drawing);
    }

    public void write(File file, Drawing drawing) throws IOException {
        BufferedOutputStream out = new BufferedOutputStream(
                new FileOutputStream(file));
        try {
            write(out, drawing);
        } finally {
            out.close();
        }
    }

    @Override
    public void write(OutputStream out, Drawing drawing) throws IOException {
        write(out, drawing.getChildren());
    }

    /**
     * Writes the drawing to the specified output stream.
     * This method applies the specified drawingTransform to the drawing, and draws
     * it on an image of the specified getChildCount.
     */
    public void write(OutputStream out, Drawing drawing,
            AffineTransform drawingTransform, Dimension imageSize) throws IOException {
        write(out, drawing.getChildren(), drawingTransform, imageSize);
    }

    /**
     * Writes the figures to the specified output stream.
     * This method applies the specified drawingTransform to the drawing, and draws
     * it on an image of the specified getChildCount.
     * 
     * All other write methods delegate their work to here.
     */
    public void write(OutputStream out, java.util.List<Figure> figures,
            AffineTransform drawingTransform, Dimension imageSize) throws IOException {

        imageMapOutputFormatProduct
				.setDrawingTransform((drawingTransform == null) ? new AffineTransform() : drawingTransform);
        imageMapOutputFormatProduct
				.setBounds((imageSize == null) ? new Rectangle(0, 0, Integer.MAX_VALUE, Integer.MAX_VALUE)
						: new Rectangle(0, 0, imageSize.width, imageSize.height));

        XMLElement document = new XMLElement("map");

        // Note: Image map elements need to be written from front to back
        for (Figure f : new ReversedList<Figure>(figures)) {
            writeElement(document, f);
        }

        // Strip AREA elements with "nohref" attributes from the end of the
        // map
        if (!isIncludeNohref) {
            for (int i = document.getChildrenCount() - 1; i >= 0; i--) {
                XMLElement child = (XMLElement) document.getChildAtIndex(i);
                if (child.hasAttribute("nohref")) {
                    document.removeChildAtIndex(i);
                }
            }
        }


        // Write XML content
        PrintWriter writer = new PrintWriter(
                new OutputStreamWriter(out, "UTF-8"));
        //new XMLWriter(writer).write(document);
        for (Object o : document.getChildren()) {
            XMLElement child = (XMLElement) o;
            new XMLWriter(writer).write(child);
        }

        // Flush writer
        writer.flush();
    }

    /**
     * All other write methods delegate their work to here.
     */
    public void write(OutputStream out, java.util.List<Figure> figures) throws IOException {
        Rectangle2D.Double drawingRect = null;

        for (Figure f : figures) {
            if (drawingRect == null) {
                drawingRect = f.getBounds();
            } else {
                drawingRect.add(f.getBounds());
            }
        }

        AffineTransform tx = new AffineTransform();
        tx.translate(
                -Math.min(0, drawingRect.x),
                -Math.min(0, drawingRect.y));

        write(out, figures, tx,
                new Dimension(
                (int) (Math.abs(drawingRect.x) + drawingRect.width),
                (int) (Math.abs(drawingRect.y) + drawingRect.height)));
    }

    @Override
    public Transferable createTransferable(Drawing drawing, java.util.List<Figure> figures, double scaleFactor) throws IOException {
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        write(buf, figures);
        return new InputStreamTransferable(new DataFlavor("text/html", "HTML Image Map"), buf.toByteArray());
    }

    protected void writeElement(IXMLElement parent, Figure f) throws IOException {
        if (f instanceof SVGEllipseFigure) {
            writeEllipseElement(parent, (SVGEllipseFigure) f);
        } else if (f instanceof SVGGroupFigure) {
            writeGElement(parent, (SVGGroupFigure) f);
        } else if (f instanceof SVGImageFigure) {
            writeImageElement(parent, (SVGImageFigure) f);
        } else if (f instanceof SVGPathFigure) {
            SVGPathFigure path = (SVGPathFigure) f;
            if (path.getChildCount() == 1) {
                boolean isLinear = path.isLinear();
				BezierFigure bezier = (BezierFigure) path.getChild(0);
                if (isLinear) {
                    if (bezier.isClosed()) {
                        writePolygonElement(parent, path);
                    } else {
                        if (bezier.getNodeCount() == 2) {
                            writeLineElement(parent, path);
                        } else {
                            writePolylineElement(parent, path);
                        }
                    }
                } else {
                    writePathElement(parent, path);
                }
            } else {
                writePathElement(parent, path);
            }
        } else if (f instanceof SVGRectFigure) {
            writeRectElement(parent, (SVGRectFigure) f);
        } else if (f instanceof SVGTextFigure) {
            writeTextElement(parent, (SVGTextFigure) f);
        } else if (f instanceof SVGTextAreaFigure) {
            writeTextAreaElement(parent, (SVGTextAreaFigure) f);
        } else {
            System.out.println("Unable to write: " + f);
        }
    }

	private void writePathElement(IXMLElement parent, SVGPathFigure f) throws IOException {
        GrowStroke growStroke = new GrowStroke( (getStrokeTotalWidth(f) / 2d),  getStrokeTotalWidth(f));
        BasicStroke basicStroke = new BasicStroke((float) getStrokeTotalWidth(f));
        for (Figure child : f.getChildren()) {
            SVGBezierFigure bezier = (SVGBezierFigure) child;
            IXMLElement elem = parent.createElement("area");
            if (bezier.isClosed()) {
                imageMapOutputFormatProduct.writePolyAttributes(elem, f, growStroke.createStrokedShape(bezier.getBezierPath()));
            } else {
                imageMapOutputFormatProduct.writePolyAttributes(elem, f, basicStroke.createStrokedShape(bezier.getBezierPath()));
            }
            parent.addChild(elem);
        }
    }

    private void writePolygonElement(IXMLElement parent, SVGPathFigure f) throws IOException {
        IXMLElement elem = parent.createElement("area");
        if (imageMapOutputFormatProduct.writePolyAttributes(elem, f, new GrowStroke( (getStrokeTotalWidth(f) / 2d),  getStrokeTotalWidth(f)).createStrokedShape(f.getChild(0).getBezierPath()))) {
            parent.addChild(elem);
        }
    }

    private void writePolylineElement(IXMLElement parent, SVGPathFigure f) throws IOException {
        IXMLElement elem = parent.createElement("area");

        if (imageMapOutputFormatProduct.writePolyAttributes(elem, f, new BasicStroke((float) getStrokeTotalWidth(f)).createStrokedShape(f.getChild(0).getBezierPath()))) {
            parent.addChild(elem);
        }
    }

    private void writeLineElement(IXMLElement parent, SVGPathFigure f) throws IOException {
        IXMLElement elem = parent.createElement("area");
        if (imageMapOutputFormatProduct.writePolyAttributes(elem, f, new GrowStroke( (getStrokeTotalWidth(f) / 2d),  getStrokeTotalWidth(f)).createStrokedShape(new Line2D.Double(
                f.getStartPoint(), f.getEndPoint())))) {
            parent.addChild(elem);
        }
    }

    private void writeRectElement(IXMLElement parent, SVGRectFigure f) throws IOException {
        IXMLElement elem = parent.createElement("AREA");
        boolean isContained;
        if (f.getArcHeight() == 0 && f.getArcWidth() == 0) {
            Rectangle2D.Double rect = f.getBounds();
            double grow = getPerpendicularHitGrowth(f);
            rect.x -= grow;
            rect.y -= grow;
            rect.width += grow;
            rect.height += grow;
            isContained = imageMapOutputFormatProduct.writeRectAttributes(elem, f, rect);
        } else {
            isContained = imageMapOutputFormatProduct.writePolyAttributes(elem, f,
                    new GrowStroke( (getStrokeTotalWidth(f) / 2d),  getStrokeTotalWidth(f)).createStrokedShape(new RoundRectangle2D.Double(
                    f.getX(), f.getY(), f.getWidth(), f.getHeight(),
                    f.getArcWidth(), f.getArcHeight())));
        }
        if (isContained) {
            parent.addChild(elem);
        }
    }

    private void writeTextElement(IXMLElement parent, SVGTextFigure f) throws IOException {
        IXMLElement elem = parent.createElement("AREA");
        Rectangle2D.Double rect = f.getBounds();
        double grow = getPerpendicularHitGrowth(f);
        rect.x -= grow;
        rect.y -= grow;
        rect.width += grow;
        rect.height += grow;
        if (imageMapOutputFormatProduct.writeRectAttributes(elem, f, rect)) {
            parent.addChild(elem);
        }
    }

    private void writeTextAreaElement(IXMLElement parent, SVGTextAreaFigure f) throws IOException {
        IXMLElement elem = parent.createElement("AREA");
        Rectangle2D.Double rect = f.getBounds();
        double grow = getPerpendicularHitGrowth(f);
        rect.x -= grow;
        rect.y -= grow;
        rect.width += grow;
        rect.height += grow;
        if (imageMapOutputFormatProduct.writeRectAttributes(elem, f, rect)) {
            parent.addChild(elem);
        }
    }

    private void writeEllipseElement(IXMLElement parent, SVGEllipseFigure f) throws IOException {
        IXMLElement elem = parent.createElement("area");
        Rectangle2D.Double r = f.getBounds();
        double grow = getPerpendicularHitGrowth(f);
        Ellipse2D.Double ellipse = new Ellipse2D.Double(r.x - grow, r.y - grow, r.width + grow, r.height + grow);
        if (imageMapOutputFormatProduct.writeCircleAttributes(elem, f, ellipse)) {
            parent.addChild(elem);
        }
    }

    private void writeGElement(IXMLElement parent, SVGGroupFigure f) throws IOException {
        // Note: Image map elements need to be written from front to back
        for (Figure child : new ReversedList<Figure>(f.getChildren())) {
            writeElement(parent, child);
        }
    }

    private void writeImageElement(IXMLElement parent, SVGImageFigure f) {
        IXMLElement elem = parent.createElement("area");
        Rectangle2D.Double rect = f.getBounds();
        imageMapOutputFormatProduct.writeRectAttributes(elem, f, rect);
        parent.addChild(elem);
    }
}
