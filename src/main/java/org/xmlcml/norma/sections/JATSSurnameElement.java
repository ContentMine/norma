package org.xmlcml.norma.sections;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import nu.xom.Element;

public class JATSSurnameElement extends JATSElement {

	private static final Logger LOG = Logger.getLogger(JATSSurnameElement.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}
	
	private static final String SURNAME = "surname";

	public JATSSurnameElement(Element element) {
		super(element);
	}

	public static boolean matches(Element element) {
		if (element.getLocalName().equals(SURNAME)) {
			LOG.trace("SURNAME "+element.toXML());
			return true;
		}
		return false;
	}
	
	

}
