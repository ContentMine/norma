package org.xmlcml.norma.sections;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import nu.xom.Element;

public class JATSGivenNamesElement extends JATSElement {

	private static final Logger LOG = Logger.getLogger(JATSGivenNamesElement.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}
	
	static final String TAG = "given-names";

	public JATSGivenNamesElement(Element element) {
		super(element);
	}


}
