package org.xmlcml.norma.grobid.vec;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.xmlcml.euclid.Real2;
import org.xmlcml.graphics.svg.SVGCircle;
import org.xmlcml.graphics.svg.SVGElement;
import org.xmlcml.graphics.svg.SVGLine;

public class GrobidVecLElement extends GrobidVecElement {

	private static final Logger LOG = Logger.getLogger(GrobidVecLElement.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}
	public static final String TAG = "L";
	public GrobidVecLElement() {
		super(TAG);
	}

	public SVGElement createSVG(Real2 lastXY) {
		SVGElement element = null;
		xy = getXY(this);
		if (xy != null) {
			if (lastXY != null) {
				element = new SVGLine(lastXY, xy);
				element.setStrokeWidth(0.3);
			} else {
				element = new SVGCircle(xy, DEFAULT_RAD);
				element.setFill("red");
				element.setStrokeWidth(0.1);
			}
		}
		return element;
	}
}
