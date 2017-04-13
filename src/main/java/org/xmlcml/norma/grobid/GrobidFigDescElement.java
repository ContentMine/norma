package org.xmlcml.norma.grobid;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class GrobidFigDescElement extends GrobidElement {

	public static final String TAG = "figDesc";
	private static final Logger LOG = Logger.getLogger(GrobidFigDescElement.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}

	public GrobidFigDescElement() {
		super(TAG);
	}

}
