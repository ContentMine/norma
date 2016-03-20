package org.xmlcml.norma.sections;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.xmlcml.html.HtmlTable;
import org.xmlcml.html.HtmlTd;
import org.xmlcml.html.HtmlTr;

import nu.xom.Attribute;
import nu.xom.Element;

public class JATSElement extends Element {

	private static final Logger LOG = Logger.getLogger(JATSElement.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}

	static final String CLASS = "class";
	
	public JATSElement(Element element) {
		super(element.getLocalName());
	}
	
	public JATSElement(String tag) {
		super(tag);
	}

	protected void setClassAttribute(String name) {
		this.addAttribute(new Attribute(CLASS, name));
	}
}
