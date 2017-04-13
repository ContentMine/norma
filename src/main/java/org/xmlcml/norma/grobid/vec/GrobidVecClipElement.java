package org.xmlcml.norma.grobid.vec;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.xmlcml.graphics.svg.SVGElement;
import org.xmlcml.graphics.svg.SVGG;

public class GrobidVecClipElement extends GrobidVecElement {

	public static final String TAG = "CLIP";
	private static final Logger LOG = Logger.getLogger(GrobidVecClipElement.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}

	public GrobidVecClipElement() {
		super(TAG);
	}

	public SVGElement createSVG() {
		return null;
	}
}
