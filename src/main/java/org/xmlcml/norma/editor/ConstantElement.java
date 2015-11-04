package org.xmlcml.norma.editor;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class ConstantElement extends AbstractEditorElement implements IRegexComponent {

	public static final Logger LOG = Logger.getLogger(ConstantElement.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}
	
	public static final String TAG = "constant";

	public ConstantElement() {
		super(TAG);
	}

	public String createRegex() {
		String pattern = this.getAttributeValue(PATTERN);
		return pattern;
	}

}
