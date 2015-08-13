package org.xmlcml.norma.editor;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nu.xom.Element;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.xmlcml.xml.XMLUtil;

public class PatternElement extends AbstractEditorElement {

	public static final Logger LOG = Logger.getLogger(PatternElement.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}
	
	public static final String TAG = "pattern";
	private List<FieldElement> fieldList;
	private StringBuilder editedBuilder;
	private EditList editRecord;

	public PatternElement() {
		super(TAG);
	}

	public String createRegex() {
		StringBuilder regexString = new StringBuilder();
		for (int i = 0; i < this.getChildElements().size(); i++) {
			AbstractEditorElement child = (AbstractEditorElement) this.getChildElements().get(i);
			if (child instanceof RegexComponent) {
				String s = ((RegexComponent) child).createRegex();
				if (s != null) {
					regexString.append(s);
				}
			} else {
				throw new RuntimeException("unknown child of pattern: "+child.getLocalName());
			}
		}
		return regexString.toString();
	}

	public FieldElement getField(int i) {
		getOrCreateFieldList();
		return (i < 0 || i >= fieldList.size()) ? null : fieldList.get(i);
	}

	private List<FieldElement> getOrCreateFieldList() {
		if (fieldList == null) {
			List<Element> fList = XMLUtil.getQueryElements(this, "./field");
			fieldList = new ArrayList<FieldElement>();
			for (Element field : fList) {
				fieldList.add((FieldElement)field);
			}
		}
		return fieldList;
	}

	public Pattern createPattern() {
		return Pattern.compile(createRegex());
	}

	public String createEditedValueAndRecord(String value) {
		String newValue = null;
		Pattern pattern1 = createPattern();
		Matcher matcher = pattern1.matcher(value);
		editRecord = new EditList();
		if (matcher.matches()) {
			editedBuilder = new StringBuilder();
			for (int i = 1; i <= matcher.groupCount(); i++) {
				String group = matcher.group(i);
				FieldElement field = getField(i - 1);
				group = field.applySubstitutions(group);
				EditList fieldEditRecord = field.getEditRecord();
				this.editRecord.add(fieldEditRecord);
				insertSpace(field);
				editedBuilder.append(group);
			}
			newValue =  editedBuilder.toString();
		}
		return newValue;
	}

	private void insertSpace(FieldElement field) {
		SpaceElement space = field.getFollowingSpace();
		if (space != null) {
			String count = space.getCount();
			if (count != null && count.trim().length() > 0 && !(count.equals("*"))) {
				editedBuilder.append(" ");
			}
		}
	}
	
	public String getResultBuilderStrings() {
		return (editedBuilder == null) ? "" : editedBuilder.toString();
	}

	public EditList getEditRecord() {
		return editRecord;
	}



}
