package org.xmlcml.norma.sections;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import nu.xom.Element;

public class JATSReflistElement {

	private static final Logger LOG = Logger.getLogger(JATSReflistElement.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}
	
	public final static String TAG = "ref";
	public final static JATSReflistElement NEW = new JATSReflistElement();
	
	private JATSReflistElement() {
	}

	public String getTag() {
		return TAG;
	}


}
