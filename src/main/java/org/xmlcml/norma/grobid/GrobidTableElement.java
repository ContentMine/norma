package org.xmlcml.norma.grobid;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class GrobidTableElement extends GrobidElement {

	public static final String TAG = "table";
	private static final Logger LOG = Logger.getLogger(GrobidTableElement.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}

	public GrobidTableElement() {
		super(TAG);
	}

}
