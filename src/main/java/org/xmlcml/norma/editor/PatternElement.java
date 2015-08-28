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
	
	public final static String LEVEL = "level";
	
	private List<FieldElement> fieldList;
	private StringBuilder editedBuilder;
	private EditList editRecord;
	private StringBuilder regexStringBuilder;
	private List<Extraction> extractionList;

	public PatternElement() {
		super(TAG);
	}

	public String createRegex() {
		if (regexStringBuilder == null) {
			regexStringBuilder = new StringBuilder();
			for (int i = 0; i < this.getChildElements().size(); i++) {
				AbstractEditorElement child = (AbstractEditorElement) this.getChildElements().get(i);
				if (child instanceof IRegexComponent) {
					String s = ((IRegexComponent) child).createRegex();
					LOG.trace(">icomp> "+s);
					if (s != null) {
						regexStringBuilder.append(s);
					}
				} else if (child instanceof CombineElement) {
					// skip
				} else {
					LOG.error("unknown child of pattern: "+child.getLocalName());
				}
			}
		}
		return regexStringBuilder.toString();
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

	/** matches string and applies edits
	 * 
	 * Also builds editRecord (use getEditRecord()) as record of edits
	 * Also builds extractionList (use getExtractionList()) for matcher components after editing
	 * 
	 * @param value
	 * @return edited string (null if fails match)
	 */
	public String createEditedValueAndRecord(String value) {
		LOG.trace("analysing: "+value);
		String newValue = null;
		Pattern pattern1 = createPattern();
		LOG.trace("pattern: "+pattern1);
		Matcher matcher = pattern1.matcher(value);
		editRecord = new EditList();
		extractionList = new ArrayList<Extraction>();
		if (matcher.matches()) {
			LOG.trace("matches "+matcher.groupCount());
			editedBuilder = new StringBuilder();
			for (int i = 1; i <= matcher.groupCount(); i++) {
				String group = matcher.group(i);
				FieldElement field = getField(i - 1);
				group = field.applySubstitutions(group);
				Extraction extraction = new Extraction(field.getNameAttribute(), group);
				getExtractionList().add(extraction);
				EditList fieldRecord = field.getEditRecord();
				if (fieldRecord.size() > 0) {
					LOG.trace("fr1 "+fieldRecord);
					this.editRecord.add(fieldRecord);
				}
				editedBuilder.append(group);
				insertFollowingSpace(field);
			}
			newValue =  editedBuilder.toString();
			LOG.trace("new "+newValue);
			combineExtractionListComponents();
			LOG.trace(this.getAttributeValue(LEVEL)+">new>"+extractionList);
		} else {
			LOG.trace("failed match");
		}
 		return newValue;
	}

	private void combineExtractionListComponents() {
		LOG.trace(this.getAttributeValue(LEVEL)+">orig>"+getExtractionList());
		List<Element> combines = XMLUtil.getQueryElements(this, "*[local-name()='"+CombineElement.TAG+"']");
		for (Element combine : combines) {
			((CombineElement)combine).combine(extractionList);
		}
	}

	public boolean validate(String value) {
		boolean validate = false;
		if (value != null) {
			Pattern pattern1 = createPattern();
			Matcher matcher = pattern1.matcher(value);
			validate = matcher.matches();
			if (!validate) {
				throw new RuntimeException(">failed to validate>\n"+pattern1+": \n"+value);
			}
		}
		return validate;
	}

	private void insertFollowingSpace(FieldElement field) {
		SpaceElement space = field.getFollowingSpace();
		if (space != null) {
			String count = space.getCount();
			if (count != null && count.trim().length() > 0 && !(count.equals("*"))) {
				editedBuilder.append(" ");
			}
			LOG.trace("sp> "+field.getNameAttribute()+" "+count);
		} else {
			LOG.trace("no following space: "+field.getNameAttribute());
		}
	}
	
	public String getResultBuilderStrings() {
		return (editedBuilder == null) ? "" : editedBuilder.toString();
	}

	public EditList getEditRecord() {
		LOG.trace("pe "+editRecord);
		return editRecord;
	}

	public List<Extraction> getExtractionList() {
		return extractionList;
	}


}
