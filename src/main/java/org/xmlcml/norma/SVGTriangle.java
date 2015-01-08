package org.xmlcml.norma;

import nu.xom.Attribute;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.xmlcml.euclid.Real2;
import org.xmlcml.graphics.svg.SVGG;
import org.xmlcml.graphics.svg.SVGLine;
import org.xmlcml.graphics.svg.SVGPolyline;

public class SVGTriangle extends SVGG {
	
	private static final Logger LOG = Logger.getLogger(SVGTriangle.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}

	private SVGPolyline polyline;
	
	public SVGTriangle() {
		super();
		this.addAttribute(new Attribute("class", "triangle"));
	}

	public SVGTriangle(SVGPolyline polyline) {
		this();
		if (!(polyline.getLineList().size() == 3 && polyline.isClosed())) {
			throw new RuntimeException("Not a triangle");
		}
		this.polyline = polyline;
	}

	public SVGLine getLine(int serial) {
		return polyline.getLineList().get(serial % 3);
	}

	public int getLineTouchingPoint(Real2 point, double delta) {
		for (int iline = 0; iline < 3; iline++) {
			SVGLine line = polyline.getLineList().get(iline);
			if (line.getEuclidLine().getUnsignedDistanceFromPoint(point) < delta) {
				return iline;
			}
		}
		return -1;
	}

	public SVGLine getLineStartingFrom(int point) {
		return polyline.getLineList().get(point % 3);
	}

	public boolean hasEqualCoordinates(SVGTriangle triangle0, double delta) {
		return this.getPolyline().hasEqualCoordinates(triangle0.getPolyline(), delta);
	}

	private SVGPolyline getPolyline() {
		return polyline;
	}
	
	public String toString() {
		return polyline.getReal2Array().toString();
	}

}
