package org.xmlcml.nhtml.tagger;

import org.apache.log4j.Logger;

public class TagElement extends AbstractTElement {

	private static final Logger LOG = Logger.getLogger(TagElement.class);
	
	public static final String TAG_NAME = "name";
	public static final String TAG = "tag";

	public TagElement() {
		super(TAG);
	}

	public String getName() {
		return this.getAttributeValue(TAG_NAME);
	}

	public String getXPath() {
		return this.getValue();
	}
}
