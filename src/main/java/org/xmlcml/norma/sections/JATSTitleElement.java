package org.xmlcml.norma.sections;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import nu.xom.Element;

public class JATSTitleElement extends JATSElement {

	private static final Logger LOG = Logger.getLogger(JATSTitleElement.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}
	
	private static final String TITLE = "article-title";

	public JATSTitleElement(Element element) {
		super(element);
	}

	public static boolean matches(Element element) {
		if (element.getLocalName().equals(TITLE)) {
			LOG.trace("TIT "+element.toXML());
			return true;
		}
		return false;
	}
	
	

}
