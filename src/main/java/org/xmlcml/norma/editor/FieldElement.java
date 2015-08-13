package org.xmlcml.norma.editor;

import java.util.ArrayList;
import java.util.List;

import nu.xom.Element;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.xmlcml.xml.XMLUtil;

public class FieldElement extends AbstractEditorElement implements RegexComponent {

	private static final String PATTERN = "pattern";
	public static final Logger LOG = Logger.getLogger(FieldElement.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}
	
	public static final String TAG = "field";
	private List<SubstitutionElement> substitutionList;
	private EditList editRecord;

	public FieldElement() {
		super(TAG);
	}

	public String createRegex() {
		String s = this.getAttributeValue(PATTERN);
		return s == null ? null : "("+s+")";
	}

	public List<SubstitutionElement> getOrCreateSubstitutionList() {
		if (substitutionList == null) {
			substitutionList = new ArrayList<SubstitutionElement>();
			List<Element> sList = XMLUtil.getQueryElements(this, "./substitution");
			for (Element s : sList) {
				substitutionList.add((SubstitutionElement)s);
			}
		}
		return substitutionList;
		
	}

	public String applySubstitutions(String group) {
		getOrCreateSubstitutionList();
		editRecord = new EditList();
		for (SubstitutionElement substitution : substitutionList) {
			group = substitution.apply(group);
			EditList substitutionRecord = substitution.getEditRecord();
			editRecord.add(substitutionRecord);
		}
		return group;
	}

	/** does this Field have a declared following spaceElement?
	 * 
	 * @return null if not
	 */
	public SpaceElement getFollowingSpace() {
		SpaceElement followingSpace = null;
		Element parent = (Element)this.getParent();
		if (parent != null) {
			List<Element> siblings = XMLUtil.getQueryElements(parent, "field | space");
			int idx = siblings.indexOf(this);
			if (idx < siblings.size() - 1) {
				Element following = siblings.get(idx + 1);
				if (following instanceof SpaceElement) {
					followingSpace = (SpaceElement) following;
				};
			}
		}
		return followingSpace;
	}

	public EditList getEditRecord() {
		return editRecord;
	}


}
