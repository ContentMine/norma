package org.xmlcml.norma.sections;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import nu.xom.Element;

public class JATSRefElement extends JATSElement {

	private static final Logger LOG = Logger.getLogger(JATSRefElement.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}
	
	private static final String TITLE = "ref";

	public JATSRefElement(Element element) {
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
