package org.xmlcml.norma.grobid.vec;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.xmlcml.graphics.svg.SVGElement;
import org.xmlcml.graphics.svg.SVGG;

public class GrobidVecGroupElement extends GrobidVecElement {

	public static final String TAG = "GROUP";
	private static final Logger LOG = Logger.getLogger(GrobidVecGroupElement.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}

	public GrobidVecGroupElement() {
		super(TAG);
	}

	public SVGElement createSVG() {
		SVGG g = new SVGG();
		super.transferAttributesAndChildren(g);
		return g;
	}
}
