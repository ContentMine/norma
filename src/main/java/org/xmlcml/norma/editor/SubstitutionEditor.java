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

/** manages and carries out edits.
 * 
 * Needs tidying.
 * 
 * @author pm286
 *
 */
public class SubstitutionEditor {

	public static final Logger LOG = Logger.getLogger(SubstitutionEditor.class);
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
	private List<Element> editorPatterns;
	private PatternElement editorPattern;
	private List<Element> validatorPatterns;
	private PatternElement validatorPattern;
	private List<Extraction> extractionList;

	public SubstitutionEditor() {
		setup();
	}

	private void setup() {
//		this.readSubstitutionEdits(this.getClass().getResourceAsStream(ITALIC_GARBLES_XML));
		this.readSubstitutionEdits(this.getClass().getResourceAsStream(SUBSTITUTIONS_XML));
	}

	private void ensureSubstitutionMap() {
		if (substitutionMap == null) {
			substitutionMap = new HashMap<String, Substitution>();
			originalList = new ArrayList<String>();
		}
	}

	public void readSubstitutionEdits(InputStream substitutionsStream) {
		LOG.debug("readSubstitutionEdits");
		ensureSubstitutionMap();
		try {
			Element substitutionsElement = XMLUtil.parseQuietlyToDocument(substitutionsStream).getRootElement();
			readSubstitutionEdits(substitutionsElement);
		} catch (Exception e) {
			throw new RuntimeException("Cannot parse substitutionStream", e);
		}
	}

	public void readSubstitutionEdits(Element substitutionsElement) {
		Elements substitutions = substitutionsElement.getChildElements(SUBSTITUTION);
		for (int i = 0; i < substitutions.size(); i++) {
			Element substitutionElement = substitutions.get(i);
			String original = substitutionElement.getAttributeValue(ORIGINAL);
			String edited = substitutionElement.getAttributeValue(EDITED);
			substitutionMap.put(original, new Substitution(original, edited));
		}
	}

	/** used by HOCRReader.
	 * 
	 * Need to rationalize.
	 * 
	 * @param word
	 * @param wordValue
	 * @param rect
	 * @return
	 */
	public String applySubstitutions(SVGWord word, String wordValue, SVGRect rect) {
		LOG.trace("applySubstitutionsWordValue");
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
	
	private String substituteAllAndRecordEdits(String targetString, String original) {
		LOG.trace("substituteAllAndRecordEdits");
		ensureEditRecord();
		String edited = getEdited(original);
		// original not in map, abort.
		return substituteAllAndRecordEdits(targetString, original, edited);
	}

	/** makes sequential edits and records in editRecord.
	 * 
	 * @param targetString
	 * @param original
	 * @param edited
	 * @param editRecord
	 * @return
	 */
	public String substituteAllAndRecordEdits(String targetString, String original, String edited) {
		LOG.trace("substituteAllAndRecordEdits00");
		if(edited == null) {
			return targetString;
		}
		editRecord = new EditList();
		int el = edited.length();
		StringBuilder targetBuilder = new StringBuilder(targetString);
		int idx = 0;
		while (true) {
			int idx0 = targetBuilder.indexOf(original, idx);
			if (idx0 == -1) break;
			targetBuilder.replace(idx0,  idx0 + el, edited);
			idx = idx0 + el;
			editRecord.add(original+"__"+edited);
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
		LOG.debug("recordEditsInWord");
		String editString = editRecord.toString().trim();
		word.addAttribute(new Attribute(EDITS, editString));
		if (rect != null) {
			rect.setFill(EDITED_COL);
		}
		return editString;
	}

	private String getEdited(String original) {
		LOG.trace("getEdited");
		ensureSubstitutionMap();
		Substitution substitution = substitutionMap.get(original);
		return substitution == null ? null : substitution.getEdited();
	}

	public void addSubstitution(Substitution substitution) {
		LOG.debug("addSubstitution");
		ensureSubstitutionMap();
		substitutionMap.put(substitution.getOriginal(), substitution);
		originalList.add(substitution.getOriginal());
	}

	public Substitution get(String key) {
		LOG.debug("get key");
		ensureSubstitutionMap();
		return substitutionMap.get(key);
	}


	public void addEditor(InputStream is) {
		EditorElement editorElement = (EditorElement) AbstractEditorElement.createEditorElement(is);
		editorElement.setSubstitutionEditor(this);
		validatorPatterns = XMLUtil.getQueryElements(editorElement, "/editor/patternList/pattern[@level='validator']");
		validatorPattern = validatorPatterns.size() == 0 ? null : (PatternElement) validatorPatterns.get(0);
		editorPatterns = XMLUtil.getQueryElements(editorElement, "/editor/patternList/pattern[@level='editor']");
		editorPattern = editorPatterns.size() == 0 ? null : (PatternElement) editorPatterns.get(0);
	}
	
	public String createEditedValueAndRecord(String value) {
		String newValue = null;
		if (editorPattern != null) {
			newValue =  editorPattern.createEditedValueAndRecord(value);
			this.editRecord = editorPattern.getEditRecord();
			this.extractionList = editorPattern.getExtractionList();
		}
		return newValue;
	}
	
	public EditList getEditRecord() {
//		editRecord = editorPattern == null ? null : editorPattern.getEditRecord();
		LOG.trace("se "+editRecord);
		return editRecord;
	}

	// FIXME
	public boolean validate(String editedValue) {
		return validatorPattern.validate(editedValue);
	}

	public List<Extraction> getExtractionList() {
		return extractionList;
	}

	public boolean validate(List<Extraction> extractionList) {
		LOG.error("*****NYI");
		return false;
	}

}
