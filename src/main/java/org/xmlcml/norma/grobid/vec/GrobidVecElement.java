package org.xmlcml.norma.grobid.vec;

import java.io.File;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.xmlcml.euclid.Real2;
import org.xmlcml.graphics.svg.SVGElement;
import org.xmlcml.graphics.svg.SVGG;
import org.xmlcml.graphics.svg.SVGSVG;
import org.xmlcml.xml.XMLUtil;

import nu.xom.Comment;
import nu.xom.Element;
import nu.xom.Node;
import nu.xom.ProcessingInstruction;
import nu.xom.Text;

public class GrobidVecElement extends Element {

	private static final Logger LOG = Logger.getLogger(GrobidVecElement.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}
	public final static String TAG = "grobidVecElement";
	protected static final String Y = "y";
	protected static final String X = "x";
	protected static final double DEFAULT_RAD = 2.0;
	protected Real2 xy;
	
	public GrobidVecElement() {
		super(TAG);
	}

	public GrobidVecElement(String tag) {
		super(tag);
	}
	
	public static GrobidVecElement readFile(File grobidVecXml) {
		Element element = XMLUtil.parseQuietlyToDocument(grobidVecXml).getRootElement();
		return (GrobidVecElement) createElement(element);
	}
	
	private static GrobidVecElement createElement(Element element) {
		GrobidVecElement grobidVecElement = null;
		if (element == null) {
			return grobidVecElement;
		} 
		String tag = element.getLocalName();
		if (tag.equals(GrobidVecVectorialImagesElement.TAG)) {
			grobidVecElement = new GrobidVecVectorialImagesElement();
		} else if (tag.equals(GrobidVecClipElement.TAG)) {
			grobidVecElement = new GrobidVecClipElement();
		} else if (tag.equals(GrobidVecGroupElement.TAG)) {
			grobidVecElement = new GrobidVecGroupElement();
		} else if (tag.equals(GrobidVecCElement.TAG)) {
			grobidVecElement = new GrobidVecCElement();
		} else if (tag.equals(GrobidVecLElement.TAG)) {
			grobidVecElement = new GrobidVecLElement();
		} else if (tag.equals(GrobidVecMElement.TAG)) {
			grobidVecElement = new GrobidVecMElement();
		} else {
			grobidVecElement = new GrobidVecElement(tag);
			System.out.println("UNK "+tag);
		}
		if (grobidVecElement != null) {
			XMLUtil.copyAttributesFromTo(element, grobidVecElement);
	        createSubclassedChildren(element, grobidVecElement);
		} else {
			LOG.debug("NULLLL");
		}
		return grobidVecElement;
	}
	
	protected static void createSubclassedChildren(Element oldElement, GrobidVecElement newElement) {
		if (oldElement != null) {
			for (int i = 0; i < oldElement.getChildCount(); i++) {
				Node node = oldElement.getChild(i);
				Node newNode = null;
				if (node instanceof Text) {
					String value = node.getValue();
					newNode = new Text(value);
				} else if (node instanceof Comment) {
					newNode = new Comment(node.getValue());
				} else if (node instanceof ProcessingInstruction) {
					newNode = new ProcessingInstruction((ProcessingInstruction) node);
				} else if (node instanceof Element) {
					newNode = createElement((Element) node);
				} else {
					throw new RuntimeException("Cannot create new node: "+node.getClass());
				}
				if (newNode != null) {
					newElement.appendChild(newNode);
				}
			}
		}
	}

	public SVGElement createSVG(Real2 lastPoint) {
		SVGG g = new SVGG();
		g.setClassName(this.getLocalName());
		this.transferAttributesAndChildren(g);
		return g;
	}

	public void transferAttributesAndChildren(SVGElement svgParent) {
		XMLUtil.copyAttributes(this, svgParent);
		Real2 lastPoint = null;
		for (int i = 0; i < this.getChildCount(); i++) {
			Node child = this.getChild(i);
			Node svgChild = null;
			if (child instanceof Text) {
				String value = child.getValue();
				svgChild = new Text(value);
			} else if (child instanceof Comment) {
				svgChild = new Comment(child.getValue());
			} else if (child instanceof ProcessingInstruction) {
				svgChild = new ProcessingInstruction((ProcessingInstruction) child);
			} else if (child instanceof GrobidVecElement) {
				GrobidVecElement grobidVecElement = (GrobidVecElement)child;
				svgChild = grobidVecElement.createSVG(lastPoint);
				lastPoint = grobidVecElement.getXY();
			} else {
				throw new RuntimeException("Cannot create new node: "+child.getClass());
			}
			if (svgChild != null) {
				svgParent.appendChild(svgChild);
			}
		}
	}

	public Real2 getXY() {
		return xy;
	}

	public void setXY(Real2 xy) {
		this.xy = new Real2(xy);
	}

	protected Real2 getXY(Element element) {
		return getXY(element, X, Y);
	}

	protected Real2 getXY(Element element, String xS, String yS) {
		Double x = Double.valueOf(element.getAttributeValue(xS));
		Double y = Double.valueOf(element.getAttributeValue(yS));
		return x == null || y == null ? null : new Real2(x, y);
	}

	public SVGElement createSVG() {
		return createSVG((Real2)null);
	}

}
