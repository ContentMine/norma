package org.xmlcml.norma.tagger;

import nu.xom.Attribute;

import org.apache.log4j.Logger;

public class MetadataElement extends AbstractTElement {
	
	private static final String NAME = "name";

	private static final Logger LOG = Logger.getLogger(MetadataElement.class);

	public final static String TAG = "metadata";
	
	private String name;
	private String xPath;

	private String value;
	
	public MetadataElement() {
		super(TAG);
		
	}

	public MetadataElement(String name, String value) {
		this();
		this.name = name;
		this.value = value;
		this.addAttribute(new Attribute(NAME, name));
		this.appendChild(value);
	}

	public String getName() {
		if (name == null) {
			name = this.getAttributeValue(NAME);
		}
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getXPath() {
		if (xPath == null) {
			xPath = this.getValue();
		}
		return xPath;
	}

	public void setXPath(String xPath) {
		this.xPath = xPath;
	}
	
}
