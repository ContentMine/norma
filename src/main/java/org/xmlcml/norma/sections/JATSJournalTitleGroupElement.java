package org.xmlcml.norma.sections;

import java.util.Arrays;
import java.util.List;

import nu.xom.Element;

public class JATSJournalTitleGroupElement extends JATSElement {

	static String TAG = "journal-title-group";
	public final static List<String> ALLOWED_CHILD_NAMES = Arrays.asList(new String[] {
			JATSSpanFactory.JOURNAL_TITLE,
	});

	@Override
	protected List<String> getAllowedChildNames() {
		return ALLOWED_CHILD_NAMES;
	}


	public JATSJournalTitleGroupElement(Element element) {
		super(element);
	}


}
