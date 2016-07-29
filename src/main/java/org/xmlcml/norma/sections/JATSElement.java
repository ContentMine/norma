package org.xmlcml.norma.sections;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.xmlcml.xml.XMLUtil;

import nu.xom.Attribute;
import nu.xom.Element;
import nu.xom.Node;

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

	protected void applyNonXMLSemantics() {
		// no-op override
	}

	public void recurse(Element element, JATSFactory jatsFactory) {
		XMLUtil.copyAttributes(element, this);
		this.setClassAttribute(element.getLocalName());
		for (int i = 0; i < element.getChildCount(); i++) {
			Node childNode = element.getChild(i);
			if (childNode instanceof Element) {
				Element childElement = (Element) childNode;
				String tag = childElement.getLocalName();
				List<String> allowedChildNames = getAllowedChildNames();
				if (allowedChildNames.size() > 0 && !allowedChildNames.contains(tag)) {
					String xml = childElement.toXML();
					LOG.debug(this.getClass().getName()+" unprocessed child: "+tag+" "+xml.substring(0, Math.min(80, xml.length())));
				}
				this.appendChild(jatsFactory.create(childElement));
			} else {
				this.appendChild(childNode.copy());
			}
		}
		applyNonXMLSemantics();
	}
	/** overridden by anything that wants to check children
	 * 
	 * @return
	 */
	protected List<String> getAllowedChildNames() {
		return new ArrayList<String>();
	}

	protected String getSingleChildValue(String tag) {
		return XMLUtil.getSingleValue(this, "*[local-name()='"+tag+"']");
	}
	
	protected JATSElement getSingleChild(String tag) {
		return (JATSElement) XMLUtil.getSingleElement(this,"*[local-name()='"+tag+"']");
	}
	
	protected String getSingleValueByClassAttributeValue(String attName) {
		Element element = XMLUtil.getSingleElement(this,"*[@class='"+attName+"']");
		return element == null ? null : element.getValue();
	}

	protected String getSingleChildValueByAttribute(String attName) {
		return XMLUtil.getSingleValue(this,"*[@"+attName+"]");
	}


}
