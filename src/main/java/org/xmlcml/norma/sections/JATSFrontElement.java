package org.xmlcml.norma.sections;

import java.util.Arrays;
import java.util.List;

import nu.xom.Element;

public class JATSFrontElement extends JATSElement {

	/**
		<journal-meta>
		...
		</journal-meta>
		<article-meta>
		</article-meta>
		
	 */
	static String TAG = "front";

	public final static List<String> ALLOWED_CHILD_NAMES = Arrays.asList(new String[] {
			JATSDivFactory.JOURNAL_META,
			JATSDivFactory.ARTICLE_META,
			JATSDivFactory.NOTES,
	});
	
	@Override
	protected List<String> getAllowedChildNames() {
		return ALLOWED_CHILD_NAMES;
	}

	private JATSJournalMetaElement journalMeta;
	private JATSArticleMetaElement articleMeta;
	
	public JATSFrontElement(Element element) {
		super(element);
	}

	protected void applyNonXMLSemantics() {
		journalMeta = (JATSJournalMetaElement) this.getSingleChild(JATSJournalMetaElement.TAG);
		articleMeta = (JATSArticleMetaElement) this.getSingleChild(JATSArticleMetaElement.TAG);
	}
	
	public String getPMCID() {
		return articleMeta == null ? null : articleMeta.getPMCID();
	}
}
