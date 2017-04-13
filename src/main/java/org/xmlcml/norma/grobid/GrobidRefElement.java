package org.xmlcml.norma.grobid;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class GrobidRefElement extends GrobidElement {

	public static final String TAG = "ref";
	private static final Logger LOG = Logger.getLogger(GrobidRefElement.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}

	public GrobidRefElement() {
		super(TAG);
	}

}
