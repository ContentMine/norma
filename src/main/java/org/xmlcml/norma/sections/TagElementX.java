package org.xmlcml.norma.sections;

import java.util.ArrayList;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import nu.xom.Element;

public class TagElementX {

	private static final Logger LOG = Logger.getLogger(TagElementX.class);

	static {
		LOG.setLevel(Level.DEBUG);
	}

	private static final String ID = "id";
	private static final String REGEX = "regex";
	private static final String TITLE = "title";
	private static final String XPATH = "xpath";

	
	private String id;
	private ArrayList<String> regexList;
	private String xpath;
	private String tag;
	private String title;
	
	public TagElementX(Element rawElement) {
		this.regexList = new ArrayList<String>();
		this.title = rawElement.getAttributeValue(TITLE);
		this.id = rawElement.getAttributeValue(ID);
		this.tag = id.split(":")[1];
		for (int i = 0; i < rawElement.getChildElements().size(); i++) {
			Element child = rawElement.getChildElements().get(i);
			if (child.getLocalName().equals(REGEX)) {
				regexList.add(child.getValue());
			} else if (child.getLocalName().equals(XPATH)) {
				this.xpath = child.getValue();
			} else {
				LOG.error("unknown: "+child.getLocalName());
			}
		}
	}

	public String getTag() {
		return tag;
	}

	public String getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}
	
	public ArrayList<String> getRegexList() {
		return regexList;
	}

	public String getXpath() {
		return xpath;
	}

}
