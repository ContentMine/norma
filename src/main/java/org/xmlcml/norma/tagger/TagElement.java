package org.xmlcml.norma.tagger;

import java.util.List;

import org.apache.log4j.Logger;

public class TagElement extends AbstractTElement {

	private static final Logger LOG = Logger.getLogger(TagElement.class);
	
	public static final String TAG_NAME = "name";
	public static final String TAG = "tag";

	public String expandedXPath;
	
	public TagElement() {
		super(TAG);
	}

	public String getName() {
		return this.getAttributeValue(TAG_NAME);
	}

	public String getXPath() {
		return this.getValue();
	}

	public void expandVariablesInValue(List<VariableElement> variableElementList) {
		if (expandedXPath == null) {
			String value = this.getXPath();
			expandedXPath = VariableElement.expandRefsInValue(variableElementList, value);
		}
	}
	
	public String getExpandedXPath() {
		return expandedXPath;
	}
}
