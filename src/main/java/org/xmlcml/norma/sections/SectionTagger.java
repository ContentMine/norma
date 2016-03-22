package org.xmlcml.norma.sections;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.xmlcml.cmine.files.ResourceLocation;
import org.xmlcml.html.HtmlDiv;
import org.xmlcml.html.HtmlElement;
import org.xmlcml.html.HtmlFactory;
import org.xmlcml.html.HtmlHead;
import org.xmlcml.html.HtmlSpan;
import org.xmlcml.html.HtmlTable;
import org.xmlcml.norma.NormaArgProcessor;
import org.xmlcml.norma.sections.SectionTagger.SectionTag;
import org.xmlcml.xml.XMLUtil;

import nu.xom.Element;

public class SectionTagger {

	private static final Logger LOG = Logger.getLogger(SectionTagger.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}
	
	/**
	 * 
	 * SENAY KAFKAS tags
	 *  @ClassList=(
"Introduction&Background",
"Materials&Methods",
"Results",
"Discussion",
"Conclusion&FutureWork",
"CaseStudyReport",
"Acknowledgement&Funding",
"AuthorContribution",
"CompetingInterest",
"SupplementaryData",
"Abbreviations",
"Keywords",
"References",
"Appendix",
"Figures",
"Tables",
"Other",
"Back_NoRef"
);
	 * 
	 */

	public enum SectionTag {
		ABBR,
		ABSTRACT,
		ACK_FUND,
		APPENDIX,
		ARTICLE_META,
		   ARTICLE_TITLE,
		   CONTRIB,
		AUTH_CONT,
		BACK,
		CASE,
		CONCL,
		COMP_INT,
		DISCUSS,
		FINANCIAL,
		FIG,
		FRONT,
		INTRO,
		JOURNAL_META,
      		JOURNAL_TITLE,
      		PUBLISHER_NAME,
		KEYWORD,
		METHODS,
		OTHER,
		PMCID,
		REF,
		RESULTS,
		SUPPL,
		TABLE,
		SUBTITLE,
		TITLE,
		
		;
		private static Map<String, SectionTag> tagByTagName = new HashMap<String, SectionTag>();
		static {
			for (SectionTag sectionTag : values()) {
				tagByTagName.put(sectionTag.toString(), sectionTag);
			}
		}
		private SectionTag() {
		}

		public static SectionTag getSectionTag(String tagName) {
			return tagByTagName.get(tagName);
		}
	};
	
	private static final SectionTag[] MAJOR_SECTIONS_ARRAY =
		{
			SectionTag.ABBR,
			SectionTag.ABSTRACT,
			SectionTag.ACK_FUND,
			SectionTag.APPENDIX,
			SectionTag.ARTICLE_META,
				SectionTag.CONTRIB,
			SectionTag.AUTH_CONT,
			SectionTag.BACK,
			SectionTag.CASE,
			SectionTag.CONCL,
			SectionTag.COMP_INT,
			SectionTag.DISCUSS,
			SectionTag.FINANCIAL,
			SectionTag.FIG,
			SectionTag.FRONT, // frontMatter (not title, article, authors, journal)
			SectionTag.INTRO,
			SectionTag.JOURNAL_META,
			SectionTag.KEYWORD,
			SectionTag.METHODS,
			SectionTag.OTHER,
			SectionTag.PMCID,
			SectionTag.REF,
			SectionTag.RESULTS,
			SectionTag.SUPPL,
			SectionTag.TABLE,
			SectionTag.SUBTITLE,
		};
	
	public static final List<SectionTag> MAJOR_SECTIONS = Arrays.asList(MAJOR_SECTIONS_ARRAY);
	public static final String PUB_ID = "pub-id";
	public static final String HELP = "help";
	
	private HtmlElement htmlElement;
	private Element jatsHtmlElement;
	static final String DEFAULT_SECTION_TAGGER_FILE = NormaArgProcessor.RESOURCE_NAME_TOP+"/pubstyle/sectionTagger.xml";
	private Element tagsElement;
	private Map<SectionTag, TagElementX> tagElementsByTag;

	private JATSArticleElement jatsArticleElement;


	
	public SectionTagger() {
		
	}

	public void readScholarlyHtml(File scholarlyHtmlFile) {
		testNotNullAndExists(scholarlyHtmlFile);
		HtmlFactory htmlFactory = new HtmlFactory();
		htmlElement = htmlFactory.parse(XMLUtil.parseQuietlyToDocument(scholarlyHtmlFile).getRootElement());
	}

	private void testNotNullAndExists(File scholarlyHtmlFile) {
		if (scholarlyHtmlFile == null) {
			throw new RuntimeException("null scholarlyHtml");
		} else if (!scholarlyHtmlFile.exists()) {
			throw new RuntimeException(scholarlyHtmlFile+" is not an existing file");
		} else if (scholarlyHtmlFile.isDirectory()) {
			throw new RuntimeException(scholarlyHtmlFile+" is a directory");
		}
	}
	
	public HtmlElement getHtmlElement() {
		return htmlElement;
	}

	public List<HtmlTable> getTablesX() {
		HtmlElement htmlElement = getHtmlElement();
		return HtmlTable.extractSelfAndDescendantTables(htmlElement);
	}

	public List<HtmlDiv> getDivs() {
		HtmlElement htmlElement = getHtmlElement();
		return HtmlDiv.extractSelfAndDescendantDivs(htmlElement);
	}

	public List<HtmlSpan> getSpans() {
		HtmlElement htmlElement = getHtmlElement();
		return HtmlSpan.extractSelfAndDescendantSpans(htmlElement);
	}
	/**
			ABBREVIATIONS,
			ABSTRACT,
			ACKNOWLEDGEMENT,
			APPENDIX,
			ARTICLE_META,
			AUTHOR_CONTRIB,
			AUTHOR_META,
			BACK,
			CASE_STUDY,
			CONCLUSION,
			CONFLICT,
			DISCUSSION,
	 */

//	public List<HtmlDiv> getAbbreviations() {
//		return getDivsForCSSClass(SectionTag.ABBR);
//	}
//
//	public List<HtmlDiv> getAbstracts() {
//		return getDivsForCSSClass(SectionTag.ABSTRACT);
//	}
//
//	public List<HtmlDiv> getAcknowledgements() {
//		return getDivsForCSSClass(SectionTag.ACK_FUND);
//	}
//
//	public List<HtmlDiv> getAppendix() {
//		return getDivsForCSSClass(SectionTag.APPENDIX);
//	}
//
//	public List<HtmlDiv> getArticleMeta() {
//		return getDivsForCSSClass(SectionTag.ARTICLE_META);
//	}
//
//	public List<HtmlDiv> getAuthorContrib() {
//		return getDivsForCSSClass(SectionTag.AUTH_CONT);
//	}
//
//	public List<HtmlDiv> getAuthorMeta() {
////		return getDivsForCSSClass(Tags.AUTHOR_META);
//		return null;
//	}
//
//	public List<HtmlDiv> getBackMatter() {
//		return getDivsForCSSClass(SectionTag.BACK);
//	}
//	
//	public List<HtmlDiv> getCaseStudies() {
//		return getDivsForCSSClass(SectionTag.CASE);
//	}
//	
//	public List<HtmlDiv> getConclusions() {
//		return getDivsForCSSClass(SectionTag.CONCL);
//	}
//
//	public List<HtmlDiv> getConflicts() {
//		return getDivsForCSSClass(SectionTag.COMP_INT);
//	}
//
//	public List<HtmlDiv> getDiscussions() {
//		return getDivsForCSSClass(SectionTag.DISCUSS);
//	}
//	
//	/**
//			FIG,
//			FINANCIAL,
//			FRONT, // frontMatter (not title, article, authors, journal)
//			INTRODUCTION,
//			JOURNAL_META,
//			KEYWORDS,
//			METHODS,
//			OTHER,
//			REF_LIST,
//			RESULTS,
//			SUPPLEMENTAL,
//			TABLE,
//			TITLE,
//	 */
//
//	public List<HtmlDiv> getFigures() {
//		return getDivsForCSSClass(SectionTag.FIG);
//	}
//
//	public List<HtmlDiv> getFinancialSupport() {
//		return getDivsForCSSClass(SectionTag.FINANCIAL);
//	}
//
//	public HtmlHead getFrontMatter() {
//		HtmlHead head = (HtmlHead) HtmlElement.getSingleChildElement(htmlElement, HtmlHead.TAG);
//		return head;
//	}
//
//	public List<HtmlDiv> getIntroductions() {
//		return getDivsForCSSClass(SectionTag.INTRO);
//	}
//
//	public List<HtmlDiv> getJournalMeta() {
//		return getDivsForCSSClass(SectionTag.JOURNAL_META);
//	}
//
//	public List<HtmlDiv> getKeywords() {
//		return getDivsForCSSClass(SectionTag.KEYWORD);
//	}
//
//	public List<HtmlDiv> getMethods() {
//		return getDivsForCSSClass(SectionTag.METHODS);
//	}
//
//	public List<HtmlDiv> getOther() {
//		return getDivsForCSSClass(SectionTag.OTHER);
//	}
//
//	public List<HtmlDiv> getRefLists() {
//		return getDivsForCSSClass(SectionTag.REF);
//	}
//
//	public List<HtmlDiv> getResults() {
//		return getDivsForCSSClass(SectionTag.RESULTS);
//	}
//
//	public List<HtmlDiv> getSubtitles() {
//		return getDivsForCSSClass(SectionTag.SUBTITLE);
//	}
//
//	public List<HtmlDiv> getSupplemental() {
//		return getDivsForCSSClass(SectionTag.SUPPL);
//	}
//
//	public List<HtmlDiv> getTables() {
//		return getDivsForCSSClass(SectionTag.TABLE);
//	}
//
//	public List<HtmlDiv> getTitles() {
//		return getDivsForCSSClass(SectionTag.TITLE);
//	}

//	public List<HtmlDiv> getDivsForCSSClass(SectionTag sectionTag) {
//		return getDivsForCSSClass(sectionTag.getNames());
//	}
	
	public List<HtmlDiv> getDivsForCSSClass(String ... names) {
		String xpath = createXPath("div", names);
		List<HtmlDiv> divs = HtmlDiv.extractDivs(getHtmlElement(), xpath);
		return divs;
	}

//	public List<HtmlSpan> getSpansForCSSClass(SectionTag sectionTag) {
//		return getSpansForCSSClass(sectionTag.getNames());
//	}
	
	public List<HtmlSpan> getSpansForCSSClass(String ... names) {
		String xpath = createXPath("span", names);
		List<HtmlSpan> spans = HtmlSpan.extractSpans(htmlElement, xpath);
		return spans;
	}
	
	// ==============================
	
	private String createXPath(String tag, String ...names) {
		if (names == null || names.length == 0) {
			throw new RuntimeException("get"+tag+" forCSSClass must have at least one arg");
		}
		String xpath = "//*[local-name()='"+tag+"' and (@class='"+names[0]+"'";

		for (int i = 1; i < names.length; i++) {
			xpath += " or @class='"+names[i]+"'";
		}
		xpath +=")]";
		LOG.trace("XPATH: "+xpath);
		return xpath;
	}

	public void readJATS(File jatsXml) {
		Element rawElement = XMLUtil.parseQuietlyToDocumentWithoutDTD(jatsXml).getRootElement();
		JATSFactory jatsFactory = new JATSFactory();
		jatsHtmlElement = jatsFactory.createHtml(rawElement);
		HtmlElement bodyHtmlElement = (HtmlElement) jatsHtmlElement.getChild(1);
		jatsArticleElement = (JATSArticleElement) bodyHtmlElement.getChild(0);
	}

	public Element getJATSHtmlElement() {
		return jatsHtmlElement;
	}

	public JATSArticleElement getJATSArticleElement() {
		return jatsArticleElement;
	}

	public Element readSectionTags(String resource) {
		ResourceLocation location = new ResourceLocation();
		InputStream is = location.getInputStreamHeuristically(resource);
		tagsElement = XMLUtil.parseQuietlyToDocument(is).getRootElement();
		return tagsElement;
	}

	public Element readSectionTags() {
		return readSectionTags(DEFAULT_SECTION_TAGGER_FILE);
	}

	public Map<SectionTag, TagElementX> getOrCreateMap() {
		Element root = readSectionTags();
		tagElementsByTag = new HashMap<SectionTag, TagElementX>();
		for (int i = 0; i < root.getChildElements().size(); i++) {
			Element child = root.getChildElements().get(i);
			String localName = child.getLocalName();
			if (localName.equals(SectionTagger.HELP)) {
				continue;
			} else if (!localName.equals("tag")) {
				LOG.error("Bad tag: "+localName);
			}
			TagElementX tagElement = new TagElementX(root.getChildElements().get(i));
			SectionTag tag = tagElement.getTag();
			tagElementsByTag.put(tag, tagElement);
		}
		return tagElementsByTag;
	}

	public TagElementX get(SectionTag tag) {
		return (tagElementsByTag == null || tag == null) ? null : tagElementsByTag.get(tag.toString());
	}

	public TagElementX getTagElement(SectionTag tag) {
		Map<SectionTag, TagElementX> tagElementByTag = getOrCreateMap();
		LOG.trace(tagElementByTag);
		TagElementX tagElement = tagElementByTag.get(tag);
		return tagElement;
	}

	public String getXPath(SectionTag tag) {
		TagElementX tagElement = getTagElement(tag);
		String xpath = tagElement == null ? null : tagElement.getXpath();
		return xpath;
	}

	public List<Element> getSections(SectionTag tag) {
		List<Element> sections = new ArrayList<Element>();
		if (tag != null) {
			String xpath = getXPath(tag);
			LOG.trace(xpath);
			if (xpath != null) {
				Element jatsElement = getJATSHtmlElement();
				sections = XMLUtil.getQueryElements(jatsElement, xpath);
				for (Element section : sections) {
					String sectionS = section.toXML();
//					System.out.println("CLASS "+section.getAttributeValue("class")+" || "+sectionS.substring(0, Math.min(100, sectionS.length())));
				}
			}
		}
		return sections;
	}

}
