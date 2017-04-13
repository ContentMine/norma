package org.xmlcml.norma.grobid;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class GrobidBiblStructElement extends GrobidElement {

	public static final String TAG = "biblStruct";
	private static final Logger LOG = Logger.getLogger(GrobidBiblStructElement.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}

	public GrobidBiblStructElement() {
		super(TAG);
	}

}
