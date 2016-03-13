package org.xmlcml.norma.sections;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import nu.xom.Element;

public class JATSNameElement extends JATSElement {

	private static final Logger LOG = Logger.getLogger(JATSNameElement.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}
	
	private static final String TITLE = "name";

	public JATSNameElement(Element element) {
		super(element);
	}

	public static boolean matches(Element element) {
		if (element.getLocalName().equals(TITLE)) {
			return true;
		}
		return false;
	}
	

}
