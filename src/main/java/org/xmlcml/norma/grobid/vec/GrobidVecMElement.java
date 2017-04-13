package org.xmlcml.norma.grobid.vec;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.xmlcml.euclid.Real2;
import org.xmlcml.graphics.svg.SVGCircle;
import org.xmlcml.graphics.svg.SVGElement;
import org.xmlcml.graphics.svg.SVGLine;

public class GrobidVecMElement extends GrobidVecElement {

	public static final String TAG = "M";
	private static final Logger LOG = Logger.getLogger(GrobidVecMElement.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}

	public GrobidVecMElement() {
		super(TAG);
	}

	public SVGElement createSVG(Real2 lastXy) {
		SVGElement element = null;
		xy = getXY(this);
		if (xy != null) {
			element = new SVGCircle(xy, DEFAULT_RAD * 0.2);
			element.setFill("blue");
			element.setStrokeWidth(0.1);
		}
		return element;
	}

}
