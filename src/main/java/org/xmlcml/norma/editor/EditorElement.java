package org.xmlcml.norma.editor;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class EditorElement extends AbstractEditorElement {

	public static final Logger LOG = Logger.getLogger(EditorElement.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}
	
	public static final String TAG = "editor";

	public EditorElement() {
		super(TAG);
	}

}
