/*
 * @(#)DrawingPageable.java
 *
 * Copyright (c) 1996-2010 by the original authors of JHotDraw and all its
 * contributors. All rights reserved.
 *
 * You may not use, copy or modify this file, except in compliance with the 
 * license agreement you entered into with the copyright holders. For details
 * see accompanying license terms.
 */
package org.jhotdraw.draw.print;

import org.jhotdraw.draw.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.print.*;

/**
 * {@code DrawingPageable} can be used to print a {@link Drawing} using the
 * java.awt.print API.
 * <p>
 * Usage:
 * <pre>
 * Pageable pageable = new DrawingPageable(aDrawing);
 * PrinterJob job = PrinterJob.getPrinterJob();
 * job.setPageable(pageable);
 * if (job.printDialog()) {
 *     try {
 *         job.print();
 *      } catch (PrinterException e) {
 *          ...inform the user that we couldn't print...
 *      }
 * }
 * </pre>
 * 
 * @author Werner Randelshofer
 * @version $Id$
 * @see org.jhotdraw.app.action.file.PrintFileAction
 */
public class DrawingPageable implements Pageable {

    private DrawingPageableProduct drawingPageableProduct = new DrawingPageableProduct();
	private PageFormat pageFormat;
    /** Creates a new instance. */
    public DrawingPageable(Drawing drawing) {
        drawingPageableProduct.setDrawing(drawing);
        Paper paper = new Paper();
        pageFormat = new PageFormat();
        pageFormat.setPaper(paper);
    }

    @Override
    public int getNumberOfPages() {
        return 1;
    }

    @Override
    public PageFormat getPageFormat(int pageIndex) throws IndexOutOfBoundsException {
        return pageFormat;
    }

    @Override
    public Printable getPrintable(int pageIndex) throws IndexOutOfBoundsException {
        if (pageIndex < 0 || pageIndex >= getNumberOfPages()) {
            throw new IndexOutOfBoundsException("Invalid page index:" + pageIndex);
        }
        return new Printable() {

            @Override
            public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
                return drawingPageableProduct.printPage(graphics, pageFormat, pageIndex, DrawingPageable.this);
            }
        };
    }

    public int printPage(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        return drawingPageableProduct.printPage(graphics, pageFormat, pageIndex, this);
    }

	protected void setRenderingHints(Graphics2D g) {
        g.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,
                RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING,
                RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS,
                RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,
                RenderingHints.VALUE_STROKE_NORMALIZE);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    }
}

