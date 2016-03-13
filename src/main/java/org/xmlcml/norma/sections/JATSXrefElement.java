package org.xmlcml.norma.sections;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import nu.xom.Element;

public class JATSXrefElement extends JATSElement {

	private static final Logger LOG = Logger.getLogger(JATSXrefElement.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}
	
	private static final String TITLE = "xref";

	public JATSXrefElement(Element element) {
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
