package org.xmlcml.norma.sections;

import java.util.Arrays;
import java.util.List;

import nu.xom.Element;

public class JATSJournalMetaElement extends JATSElement {

	/**
		<journal-meta>
			<journal-id journal-id-type="nlm-ta">PLoS Negl Trop Dis</journal-id>
			<journal-id journal-id-type="publisher-id">plos</journal-id>
			<journal-id journal-id-type="pmc">plosntds</journal-id>
			<journal-title-group>
				<journal-title>PLoS Neglected Tropical Diseases</journal-title>
			</journal-title-group>
			<issn pub-type="ppub">1935-2727</issn>
			<issn pub-type="epub">1935-2735</issn>
			<publisher>
				<publisher-name>Public Library of Science</publisher-name>
				<publisher-loc>San Francisco, USA</publisher-loc>
			</publisher>
		</journal-meta>
	 */
	static String TAG = "journal-meta";
	public final static List<String> ALLOWED_CHILD_NAMES = Arrays.asList(new String[] {
			JATSSpanFactory.ISSN,
			JATSSpanFactory.JOURNAL_ID,
			JATSDivFactory.JOURNAL_TITLE_GROUP,
			JATSDivFactory.PUBLISHER,
	});


	@Override
	protected List<String> getAllowedChildNames() {
		return ALLOWED_CHILD_NAMES;
	}


	public JATSJournalMetaElement(Element element) {
		super(element);
	}


}
