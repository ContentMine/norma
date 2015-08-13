package org.xmlcml.norma.editor;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nu.xom.Attribute;
import nu.xom.Element;
import nu.xom.Elements;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.xmlcml.graphics.svg.SVGRect;
import org.xmlcml.graphics.svg.text.SVGWord;
import org.xmlcml.xml.XMLUtil;

public class SubstitutionManager {

	public static final Logger LOG = Logger.getLogger(SubstitutionManager.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}

	private static final String EDITS = "edits";
	private static final String EDITED_COL = "pink";
	private static final String EDITED = "edited";
	private static final String SUBSTITUTION = "substitution";
	private static final String ORIGINAL = "original";
	private static final String ITALIC_GARBLES_XML = "/org/xmlcml/norma/images/ocr/italicGarbles.xml";
	private static final String SUBSTITUTIONS_XML = "/org/xmlcml/norma/images/ocr/universalSubstitutions.xml";


	
	private Map<String, Substitution> substitutionMap;
	private EditList editRecord;
	private List<String> originalList;

	public SubstitutionManager() {
		setup();
	}

	private void setup() {
//		this.readSubstitutionEdits(this.getClass().getResourceAsStream(ITALIC_GARBLES_XML));
		this.readSubstitutionEdits(this.getClass().getResourceAsStream(SUBSTITUTIONS_XML));
	}

	public void ensureSubstitutionMap() {
		if (substitutionMap == null) {
			substitutionMap = new HashMap<String, Substitution>();
			originalList = new ArrayList<String>();
		}
	}

	public void readSubstitutionEdits(InputStream substitutionsStream) {
		ensureSubstitutionMap();
		try {
			Element substitutionsElement = XMLUtil.parseQuietlyToDocument(substitutionsStream).getRootElement();
			Elements substitutions = substitutionsElement.getChildElements(SUBSTITUTION);
			for (int i = 0; i < substitutions.size(); i++) {
				Element substitutionElement = substitutions.get(i);
				String original = substitutionElement.getAttributeValue(ORIGINAL);
				String edited = substitutionElement.getAttributeValue(EDITED);
				substitutionMap.put(original, new Substitution(original, edited));
			}
		} catch (Exception e) {
			throw new RuntimeException("Cannot parse substitutionStream", e);
		}
	}

	public String applySubstitutions(SVGWord word, String wordValue, SVGRect rect) {
		ensureSubstitutionMap();
		String newWordValue = wordValue;
		editRecord = new EditList();
		for (String original : substitutionMap.keySet()) {
			newWordValue = substituteAllAndRecordEdits(newWordValue, original);
		}
		if (!newWordValue.equals(wordValue)) {
			recordEditsInWord(word, rect);
		}
		return newWordValue;
	}
	
	public String applySubstitutions(SVGWord word) {
		ensureSubstitutionMap();
		String wordValue = word.getSVGTextValue();
		String newWordValue = applySubstitutions(wordValue);
		return newWordValue;
	}

	String applySubstitutions(String targetString) {
		editRecord = new EditList();
		String newTarget = targetString;
		for (String original : originalList) {
			newTarget = substituteAllAndRecordEdits(newTarget, original);
		}
		return newTarget;
	}
	
	private String substituteAllAndRecordEdits(String targetString, String original) {
		ensureEditRecord();
		String edited = getEdited(original);
		// original not in map, abort.
		return substituteAllAndRecordEdits(targetString, original, edited, editRecord);
	}

	/** makes sequential edits and records in editRecord.
	 * 
	 * @param targetString
	 * @param original
	 * @param edited
	 * @param editRecord
	 * @return
	 */
	public static String substituteAllAndRecordEdits(String targetString, String original, String edited, EditList editRecord) {
		if(edited == null) {
			return targetString;
		}
		int el = edited.length();
		StringBuilder targetBuilder = new StringBuilder(targetString);
		int idx = 0;
		while (true) {
			int idx0 = targetBuilder.indexOf(original, idx);
			if (idx0 == -1) break;
			targetBuilder.replace(idx0,  idx0 + el, edited);
			idx = idx0 + el;
			editRecord.add(original+"=>"+edited);
			LOG.trace(">> "+editRecord);
		}
		return targetBuilder.toString().trim();
	}

	private void ensureEditRecord() {
		if (editRecord == null) {
			editRecord = new EditList();
		}
	}

	private String recordEditsInWord(SVGWord word, SVGRect rect) {
		String editString = editRecord.toString().trim();
		word.addAttribute(new Attribute(EDITS, editString));
		if (rect != null) {
			rect.setFill(EDITED_COL);
		}
		return editString;
	}

	private String getEdited(String original) {
		ensureSubstitutionMap();
		Substitution substitution = substitutionMap.get(original);
		return substitution == null ? null : substitution.getEdited();
	}

	public void addSubstitution(Substitution substitution) {
		ensureSubstitutionMap();
		substitutionMap.put(substitution.getOriginal(), substitution);
		originalList.add(substitution.getOriginal());
	}

	public Substitution get(String key) {
		ensureSubstitutionMap();
		return substitutionMap.get(key);
	}

	public EditList getEditRecord() {
		return editRecord;
	}



}
