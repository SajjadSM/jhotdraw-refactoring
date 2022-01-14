package org.jhotdraw.samples.odg.io;


import java.util.LinkedList;
import org.jhotdraw.draw.Figure;
import net.n3.nanoxml.IXMLElement;
import java.io.IOException;
import org.jhotdraw.samples.odg.figures.ODGFigure;
import java.io.InputStream;
import org.jhotdraw.draw.Drawing;
import net.n3.nanoxml.IXMLParser;
import net.n3.nanoxml.XMLParserFactory;
import net.n3.nanoxml.IXMLReader;
import net.n3.nanoxml.StdXMLReader;
import net.n3.nanoxml.XMLException;
import java.util.Stack;
import java.util.Iterator;
import java.util.Iterator;
import org.jhotdraw.draw.CompositeFigure;
import org.jhotdraw.samples.odg.figures.ODGGroupFigure;
import org.jhotdraw.samples.odg.ODGConstants;

public class ODGInputFormatProduct {
	private LinkedList<Figure> figures;
	private IXMLElement document;

	/**
	* Reads an ODG "draw:page" element.
	*/
	public void readPageElement(IXMLElement elem, ODGInputFormat oDGInputFormat) throws IOException {
		for (IXMLElement child : elem.getChildren()) {
			ODGFigure figure = oDGInputFormat.readElement(child);
			if (figure != null) {
				figures.add(figure);
			}
		}
	}

	/**
	* Reads an ODG "office:drawing" element.
	*/
	public void readDrawingElement(IXMLElement elem, ODGInputFormat oDGInputFormat) throws IOException {
		for (IXMLElement child : elem.getChildren()) {
			if (child.getNamespace() == null || child.getNamespace().equals(ODGConstants.DRAWING_NAMESPACE)) {
				String name = child.getName();
				if ("page".equals(name)) {
					readPageElement(child, oDGInputFormat);
				}
			}
		}
	}

	/**
	* Reads figures from the content.xml file of an ODG open document drawing document.
	*/
	@SuppressWarnings("unchecked")
	public void readFiguresFromDocumentContent(InputStream in, Drawing drawing, boolean replace,
			ODGInputFormat oDGInputFormat) throws IOException {
		this.figures = new LinkedList<Figure>();
		IXMLParser parser;
		try {
			parser = XMLParserFactory.createDefaultXMLParser();
		} catch (Exception ex) {
			InternalError e = new InternalError("Unable to instantiate NanoXML Parser");
			e.initCause(ex);
			throw e;
		}
		IXMLReader reader = new StdXMLReader(in);
		parser.setReader(reader);
		try {
			document = (IXMLElement) parser.parse();
		} catch (XMLException ex) {
			IOException e = new IOException(ex.getMessage());
			e.initCause(ex);
			throw e;
		}
		if (oDGInputFormat.getStyles() == null) {
			oDGInputFormat.setStyles(new ODGStylesReader());
		}
		oDGInputFormat.getStyles().read(document);
		IXMLElement drawingElem = document;
		Stack<Iterator> stack = new Stack<Iterator>();
		LinkedList<IXMLElement> ll = new LinkedList<IXMLElement>();
		ll.add(document);
		stack.push(ll.iterator());
		while (!stack.empty() && stack.peek().hasNext()) {
			Iterator<IXMLElement> iter = stack.peek();
			IXMLElement node = iter.next();
			Iterator<IXMLElement> children = node.getChildren().iterator();
			if (!iter.hasNext()) {
				stack.pop();
			}
			if (children.hasNext()) {
				stack.push(children);
			}
			if (node.getName() != null && node.getName().equals("drawing")
					&& (node.getNamespace() == null || node.getNamespace().equals(ODGConstants.OFFICE_NAMESPACE))) {
				drawingElem = node;
				break;
			}
		}
		if (drawingElem.getName() == null || !drawingElem.getName().equals("drawing")
				|| (drawingElem.getNamespace() != null
						&& !drawingElem.getNamespace().equals(ODGConstants.OFFICE_NAMESPACE))) {
			throw new IOException("'office:drawing' element expected: " + drawingElem.getName());
		}
		readDrawingElement(drawingElem, oDGInputFormat);
		if (replace) {
			drawing.removeAllChildren();
		}
		drawing.addAll(figures);
	}

	public ODGFigure readGElement(IXMLElement elem, ODGInputFormat oDGInputFormat) throws IOException {
		CompositeFigure g = createGroupFigure();
		for (IXMLElement child : elem.getChildren()) {
			Figure childFigure = oDGInputFormat.readElement(child);
			if (childFigure != null) {
				g.basicAdd(childFigure);
			}
		}
		return (ODGFigure) g;
	}

	/**
	* Creates a ODGGroupFigure.
	*/
	public CompositeFigure createGroupFigure() throws IOException {
		ODGGroupFigure figure = new ODGGroupFigure();
		return figure;
	}
}