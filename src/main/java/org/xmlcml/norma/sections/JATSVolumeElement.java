package org.xmlcml.norma.sections;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import nu.xom.Element;

public class JATSVolumeElement extends JATSElement {

	private static final Logger LOG = Logger.getLogger(JATSVolumeElement.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}
	
	private static final String TITLE = "volume";

	public JATSVolumeElement(Element element) {
		super(element);
	}

	public static boolean matches(Element element) {
		if (element.getLocalName().equals(TITLE)) {
			LOG.trace("VOL "+element.toXML());
			return true;
		}
		return false;
	}
	
	

}
