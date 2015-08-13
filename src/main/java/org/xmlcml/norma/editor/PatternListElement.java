package org.xmlcml.norma.editor;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class PatternListElement extends AbstractEditorElement {

	public static final Logger LOG = Logger.getLogger(PatternListElement.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}
	
	public static final String TAG = "patternList";

	public PatternListElement() {
		super(TAG);
	}



}
