package org.xmlcml.norma.sections;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import nu.xom.Element;

public class JATSElementCitationElement extends JATSElement {

	private static final Logger LOG = Logger.getLogger(JATSElementCitationElement.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}
	
	private static final String TITLE = "element-citation";

	public JATSElementCitationElement(Element element) {
		super(element);
	}

	public static boolean matches(Element element) {
		if (element.getLocalName().equals(TITLE)) {
			LOG.trace(TITLE+": "+element.toXML());
			return true;
		}
		return false;
	}
	

}
