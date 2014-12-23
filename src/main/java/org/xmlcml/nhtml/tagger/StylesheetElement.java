package org.xmlcml.nhtml.tagger;

import org.apache.log4j.Logger;

public class StylesheetElement extends AbstractTElement {

	private static final Logger LOG = Logger.getLogger(StylesheetElement.class);
	
	public static final String TAG = "stylesheet";

	public StylesheetElement() {
		super(TAG);
	}

}
