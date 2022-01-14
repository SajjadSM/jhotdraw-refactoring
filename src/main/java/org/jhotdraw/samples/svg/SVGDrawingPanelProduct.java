package org.jhotdraw.samples.svg;


import org.jhotdraw.draw.Drawing;
import org.jhotdraw.draw.QuadTreeDrawing;
import java.util.LinkedList;
import org.jhotdraw.draw.io.InputFormat;
import org.jhotdraw.samples.svg.io.SVGZInputFormat;
import org.jhotdraw.draw.io.ImageInputFormat;
import org.jhotdraw.samples.svg.figures.SVGImageFigure;
import org.jhotdraw.draw.io.TextInputFormat;
import org.jhotdraw.samples.svg.figures.SVGTextFigure;
import org.jhotdraw.draw.io.OutputFormat;
import org.jhotdraw.samples.svg.io.SVGOutputFormat;
import org.jhotdraw.samples.svg.io.SVGZOutputFormat;
import org.jhotdraw.draw.io.ImageOutputFormat;
import java.awt.image.BufferedImage;
import org.jhotdraw.samples.svg.io.ImageMapOutputFormat;
import java.net.URI;
import java.io.IOException;
import javax.swing.SwingUtilities;
import java.lang.reflect.InvocationTargetException;
import java.io.Serializable;

public class SVGDrawingPanelProduct implements Serializable {
	/**
	* Creates a new Drawing object which can be used with this {@code  SVGDrawingPanel} .
	*/
	public Drawing createDrawing() {
		Drawing drawing = new QuadTreeDrawing();
		LinkedList<InputFormat> inputFormats = new LinkedList<InputFormat>();
		inputFormats.add(new SVGZInputFormat());
		inputFormats.add(new ImageInputFormat(new SVGImageFigure(), "PNG", "Portable Network Graphics (PNG)", "png",
				"image/png"));
		inputFormats.add(new ImageInputFormat(new SVGImageFigure(), "JPG", "Joint Photographics Experts Group (JPEG)",
				"jpg", "image/jpg"));
		inputFormats.add(new ImageInputFormat(new SVGImageFigure(), "GIF", "Graphics Interchange Format (GIF)", "gif",
				"image/gif"));
		inputFormats.add(new TextInputFormat(new SVGTextFigure()));
		drawing.setInputFormats(inputFormats);
		LinkedList<OutputFormat> outputFormats = new LinkedList<OutputFormat>();
		outputFormats.add(new SVGOutputFormat());
		outputFormats.add(new SVGZOutputFormat());
		outputFormats.add(new ImageOutputFormat());
		outputFormats.add(new ImageOutputFormat("JPG", "Joint Photographics Experts Group (JPEG)", "jpg",
				BufferedImage.TYPE_INT_RGB));
		outputFormats.add(new ImageOutputFormat("BMP", "Windows Bitmap (BMP)", "bmp", BufferedImage.TYPE_BYTE_INDEXED));
		outputFormats.add(new ImageMapOutputFormat());
		drawing.setOutputFormats(outputFormats);
		return drawing;
	}

	/**
	* Reads a drawing from the specified file into the SVGDrawingPanel. <p> This method should be called from a worker thread. Calling it from the Event Dispatcher Thread will block the user interface, until the drawing is read.
	*/
	public void read(URI f, final SVGDrawingPanel sVGDrawingPanel) throws IOException {
		Drawing newDrawing = createDrawing();
		if (newDrawing.getInputFormats().size() == 0) {
			throw new InternalError("Drawing object has no input formats.");
		}
		IOException firstIOException = null;
		for (InputFormat format : newDrawing.getInputFormats()) {
			try {
				format.read(f, newDrawing);
				final Drawing loadedDrawing = newDrawing;
				Runnable r = new Runnable() {
					@Override
					public void run() {
						sVGDrawingPanel.setDrawing(loadedDrawing);
					}
				};
				if (SwingUtilities.isEventDispatchThread()) {
					r.run();
				} else {
					try {
						SwingUtilities.invokeAndWait(r);
					} catch (InterruptedException ex) {
					} catch (InvocationTargetException ex) {
						InternalError ie = new InternalError("Error setting drawing.");
						ie.initCause(ex);
						throw ie;
					}
				}
				return;
			} catch (IOException e) {
				if (firstIOException == null) {
					firstIOException = e;
				}
			}
		}
		throw firstIOException;
	}

	/**
	* Reads a drawing from the specified file into the SVGDrawingPanel using the specified input format. <p> This method should be called from a worker thread. Calling it from the Event Dispatcher Thread will block the user interface, until the drawing is read.
	*/
	public void read(URI f, InputFormat format, final SVGDrawingPanel sVGDrawingPanel) throws IOException {
		if (format == null) {
			read(f, sVGDrawingPanel);
			return;
		}
		Drawing newDrawing = createDrawing();
		if (newDrawing.getInputFormats().size() == 0) {
			throw new InternalError("Drawing object has no input formats.");
		}
		format.read(f, newDrawing);
		final Drawing loadedDrawing = newDrawing;
		Runnable r = new Runnable() {
			@Override
			public void run() {
				sVGDrawingPanel.setDrawing(loadedDrawing);
			}
		};
		if (SwingUtilities.isEventDispatchThread()) {
			r.run();
		} else {
			try {
				SwingUtilities.invokeAndWait(r);
			} catch (InterruptedException ex) {
			} catch (InvocationTargetException ex) {
				InternalError ie = new InternalError("Error setting drawing.");
				ie.initCause(ex);
				throw ie;
			}
		}
	}
}