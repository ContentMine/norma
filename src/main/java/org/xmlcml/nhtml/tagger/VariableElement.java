package org.xmlcml.nhtml.tagger;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

public class VariableElement extends AbstractTElement {
	
	private static final String NAME = "name";

	private static final Logger LOG = Logger.getLogger(VariableElement.class);

	public final static String TAG = "variable";

	public final static Pattern VARIABLE_REF = Pattern.compile("\\{\\$([^\\}]*)\\}");
	
	private String name;
	private String expandedValue;
	
	public VariableElement() {
		super(TAG);
	}

	public String getName() {
		if (name == null) {
			name = this.getAttributeValue(NAME);
		}
		return name;
	}

	public String getExpandedValue() {
		return expandedValue;
	}
	
	void expandVariablesInValue(List<VariableElement> variableList) {
		if (expandedValue == null) {
			String value = this.getValue();
			expandedValue = expandRefsInValue(variableList, value);
		}
	}

	static String expandRefsInValue(List<VariableElement> variableElementList, String value) {
		Matcher matcher = VARIABLE_REF.matcher(value);
		int current = 0;
		StringBuilder sb = new StringBuilder();
		while (matcher.find(current)) {
			int start = matcher.start();
			sb.append(value.substring(current, start));
			String ref = matcher.group(1);
			String expandedValue0 = null;
			for (VariableElement precedingVariable : variableElementList) {
				if (ref.equals(precedingVariable.getName())) {
					expandedValue0 = precedingVariable.getExpandedValue();
					break;
				}
			}
			if (expandedValue0 == null) {
				throw new RuntimeException("Cannot find value for :"+ref);
			}
			sb.append(expandedValue0);
			current = matcher.end();
		}
		sb.append(value.substring(current));
		return sb.toString();
	}
	
	public String toString() {
		return getName()+": "+this.getValue()+" => "+this.expandedValue;
	}

}
