package org.xmlcml.norma.editor;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class SubstitutionElement extends AbstractEditorElement {

	public static final Logger LOG = Logger.getLogger(SubstitutionElement.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}

	private static final String ORIGINAL = "original";
	private static final String EDITED = "edited";

	public static final String TAG = "substitution";
	private String original;
	private String edited;
	private EditList editRecord;

	public SubstitutionElement() {
		super(TAG);
	}

	public String apply(String group) {
		if (original == null) {
			original = this.getAttributeValue(ORIGINAL);
			edited = this.getAttributeValue(EDITED);
		}
		SubstitutionEditor substitutionEditor = this.getSubstitutionEditor();
		if (substitutionEditor != null) {
			group = substitutionEditor.substituteAllAndRecordEdits(group, original, edited);
			editRecord = substitutionEditor.getEditRecord();
			if (editRecord.size() > 0) {
				LOG.trace("er "+editRecord);
			}
		}
		return group;
	}

	public EditList getEditRecord() {
		return editRecord;
	}



}
