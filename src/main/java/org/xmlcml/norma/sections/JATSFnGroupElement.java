package org.xmlcml.norma.sections;

import java.util.Arrays;
import java.util.List;

import nu.xom.Element;

public class JATSFnGroupElement extends JATSElement {

	/**
		<journal-meta>
		...
		</journal-meta>
		<article-meta>
		</article-meta>
		
	 */
	static String TAG = "fn-group";

	public final static List<String> ALLOWED_CHILD_NAMES = Arrays.asList(new String[] {
			JATSDivFactory.FN,
			JATSDivFactory.TITLE,
	});
	
	@Override
	protected List<String> getAllowedChildNames() {
		return ALLOWED_CHILD_NAMES;
	}

//	private JATSJournalMetaElement journalMeta;
//	private JATSArticleMetaElement articleMeta;
	
	public JATSFnGroupElement(Element element) {
		super(element);
	}

	protected void applyNonXMLSemantics() {
//		journalMeta = (JATSJournalMetaElement) this.getSingleChild(JATSJournalMetaElement.TAG);
//		articleMeta = (JATSArticleMetaElement) this.getSingleChild(JATSArticleMetaElement.TAG);
	}
}
