package org.xmlcml.norma.sections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.xmlcml.xml.XMLUtil;

import nu.xom.Element;

/**

 * 
 * @author pm286
 *
 */
public class JATSArticleMetaElement extends JATSElement {
	/**
		<article-meta>
			<article-id pub-id-type="pmcid">3289602</article-id>
			<article-id pub-id-type="publisher-id">PNTD-D-11-00811</article-id>
			<article-id pub-id-type="doi">10.1371/journal.pntd.0001477</article-id>
			<article-categories>
				<subj-group subj-group-type="heading">
					<subject>Research Article</subject>
				</subj-group>
				<subj-group subj-group-type="Discipline-v2">
					<subject>Medicine</subject>
					<subj-group> ...
					</subj-group>
				</subj-group>
			</article-categories>
			<title-group>
				<article-title>Genetic Characterization of Zika Virus Strains:
					Geographic Expansion of the Asian Lineage</article-title>
				<alt-title alt-title-type="running-head">Zika Virus Expansion in Asia</alt-title>
			</title-group>
			<contrib-group>
				<contrib contrib-type="author">
					<name>
						<surname>Haddow</surname>
						<given-names>Andrew D.</given-names>
					</name>
					<xref ref-type="aff" rid="aff1">
						<sup>1</sup>
					</xref>
					<xref ref-type="corresp" rid="cor1">
						<sup>&#x0002a;</sup>
					</xref>
				</contrib>
				...
			</contrib-group>
			<aff id="aff1">
				<label>1</label>
				<addr-line>Institute for Human Infections and Immunity, Center for
					Tropical Diseases, Department of Pathology, University of Texas
					Medical Branch, Galveston, Texas, United States of America</addr-line>
			</aff>
			<aff id="aff2">
				<label>2</label>
				<addr-line>United States Naval Medical Research Unit, No. 2, Phnom
					Penh, Cambodia</addr-line>
			</aff>
			<aff id="aff3">
				<label>3</label>
				<addr-line>National Dengue Control Program, Phnom Penh, Cambodia</addr-line>
			</aff>
			<contrib-group>
				<contrib contrib-type="editor">
					<name>
						<surname>Olson</surname>
						<given-names>Ken E.</given-names>
					</name>
					<role>Editor</role>
					<xref ref-type="aff" rid="edit1" />
				</contrib>
				...
			</contrib-group>
			<aff id="edit1">Colorado State University, United States of America</aff>
			<author-notes>
				<corresp id="cor1">
					&#x0002a; E-mail:
					<email>adhaddow@gmail.com</email>
				</corresp>
				<fn id="fn1" fn-type="current-aff">
					<p>
						<bold>&#x000a4;:</bold>
						Current address: United States Naval Medical Research Unit, No. 6,
						Lima, Peru
					</p>
				</fn>
				<fn fn-type="con">
					<p>Conceived and designed the experiments: ADH AJS. Performed the
						experiments: ADH AJS. Analyzed the data: ADH AJS. Contributed
						reagents/materials/analysis tools: AHD AJS CYY MRK VH RH HG RBT
						SCW. Wrote the paper: ADH AJS RBT SCW.</p>
				</fn>
			</author-notes>
			<pub-date pub-type="collection">
				<month>2</month>
				<year>2012</year>
			</pub-date>
			<pub-date pub-type="epub">
				<day>28</day>
				<month>2</month>
				<year>2012</year>
			</pub-date>
			<volume>6</volume>
			<issue>2</issue>
			<elocation-id>e1477</elocation-id>
			<history>
				<date date-type="received">
					<day>15</day>
					<month>8</month>
					<year>2011</year>
				</date>
				<date date-type="accepted">
					<day>3</day>
					<month>12</month>
					<year>2011</year>
				</date>
			</history>
			<permissions>
				<copyright-statement>This is an open-access article, free of all
					copyright, and may be freely reproduced, distributed, transmitted,
					modified, built upon, or otherwise used by anyone for any lawful
					purpose. The work is made available under the Creative Commons CC0
					public domain dedication.</copyright-statement>
				<copyright-year>2012</copyright-year>
			</permissions>
			<abstract>
				<sec>
					<title>Background</title>
					...
				</sec>
				<sec>
					<title>Methodology/Principal Findings</title>
					...
				</sec>
				...
			</abstract>
			<abstract abstract-type="summary">
				<title>Author Summary</title>
				...
			</abstract>
			<counts>
				<page-count count="7" />
			</counts>
		</article-meta>
	 */

	static final Logger LOG = Logger.getLogger(JATSArticleMetaElement.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}
	static final String TAG = "article-meta";
	
	public final static List<String> ALLOWED_CHILD_NAMES = Arrays.asList(new String[] {
		JATSDivFactory.ABSTRACT,
		JATSDivFactory.AFF,
		JATSDivFactory.AUTHOR_NOTES,
		JATSDivFactory.ARTICLE_CATEGORIES,
		JATSDivFactory.CONTRIB,
		JATSDivFactory.CONTRIB_GROUP,
		JATSDivFactory.COUNTS,
		JATSDivFactory.HISTORY,
		JATSDivFactory.PERMISSIONS,
		JATSDivFactory.PUB_DATE,
		JATSDivFactory.TITLE_GROUP,
		
		JATSSpanFactory.ARTICLE_ID,
		JATSSpanFactory.VOLUME,
		JATSSpanFactory.ISSUE,
		JATSSpanFactory.ELOCATION_ID,
	});

	private List<JATSAffElement> affList;
	
	public JATSArticleMetaElement(Element element) {
		super(element);
	}
	
	protected List<String> getAllowedChildNames() {
		return ALLOWED_CHILD_NAMES;
	}

	protected void applyNonXMLSemantics() {
		resolveAffReferences();
		JATSHistoryElement history = (JATSHistoryElement) this.getSingleChild(JATSHistoryElement.TAG);
	}

	private void resolveAffReferences() {
		affList = new ArrayList<JATSAffElement>();
		String tag = JATSAffElement.TAG;
		List<Element> elements = XMLUtil.getQueryElements(this, "*[local-name()='"+tag+"']");
		for (Element element : elements) {
			affList.add((JATSAffElement)element);
		}
		LOG.debug("affList: "+affList.size());
		
	}
}
