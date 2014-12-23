package org.xmlcml.nhtml.tagger;

import org.apache.log4j.Logger;

public class MetadataElement extends AbstractTElement {
	
	private static final Logger LOG = Logger.getLogger(MetadataElement.class);

	public final static String TAG = "metadata";
	
	private String name;
	private String xPath;
	
	public MetadataElement() {
		super(TAG);
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getxPath() {
		return xPath;
	}

	public void setxPath(String xPath) {
		this.xPath = xPath;
	}
	
}
