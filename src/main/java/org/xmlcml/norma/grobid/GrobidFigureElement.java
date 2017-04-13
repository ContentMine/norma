package org.xmlcml.norma.grobid;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class GrobidFigureElement extends GrobidElement {

	public static final String TAG = "figure";
	private static final Logger LOG = Logger.getLogger(GrobidFigureElement.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}

	public GrobidFigureElement() {
		super(TAG);
	}

}
