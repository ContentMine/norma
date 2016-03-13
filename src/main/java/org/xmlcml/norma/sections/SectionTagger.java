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

	public enum Tags {
		ABBREVIATIONS("ABBREV", "abbrev"),
		ABSTRACT("ABSTRACT", "abstract"),
		ACK("ACK", "ack"),
		ACKNOWLEDGEMENT("ACK", "acknowledgement"),
		ACKNOWLEDGMENT("ACK", "acknowledgment"),
		APPENDIX("APPEND", "appendix"),
		ARTICLE_META("ART_META", "article-meta"),
		AUTHOR_CONTRIB("CONTRIB", "authorContribution"),
	//	AUTHOR_META("", "author-meta"),
		BACK("BACK", "backMatter"),
		CASE_STUDY("CASE", "caseStudy"),
		CONCLUSION("CONCL", "conclusion"),
		CONFLICT("CONFLICT", "fn-type-conflict"),
		DISCUSSION("DISC", "discussion"),
		FINANCIAL("SUPPORT", "fn-type-financial-disclosure"),
		FIG("FIG", "fig"),
		FRONT("FRONT", "head"),
		INTRODUCTION("INTRO", "introduction"),
		JOURNAL_META("JOUR_META", "journal-meta"),
		KEYWORDS("KEYWORD", "keywords"),
		METHODS("METH", "methods"),
		OTHER("OTHER", "methods"),
		REF_LIST("REFS", "ref-list"),
		RESULTS("RESULTS", "results"),
		SUPPLEMENTAL("SUPP", "supplementalData"),
		SUPPLEMENTARY_MATERIAL("SUPP", "supplementary-material"),
		TITLE("TITLE", "articleTitle");
		private String tag;
		private String desc;
		private Tags(String tag, String desc) {
			this.tag = tag;
			this.desc = desc;
		}
		public String getTag() {return tag;}
	};
	
	private static final String[] MAJOR_SECTIONS_ARRAY =
		{
			Tags.ABBREVIATIONS.getTag(),
			Tags.ABSTRACT.getTag(),
			Tags.ACKNOWLEDGEMENT.getTag(),
			Tags.APPENDIX.getTag(),
			Tags.ARTICLE_META.getTag(),
			Tags.AUTHOR_CONTRIB.getTag(),
//			AUTHOR_META.getTag(),
			Tags.BACK.getTag(),
			Tags.CASE_STUDY.getTag(),
			Tags.CONCLUSION.getTag(),
			Tags.CONFLICT.getTag(),
			Tags.DISCUSSION.getTag(),
			Tags.FINANCIAL.getTag(),
			Tags.FIG.getTag(),
			Tags.FRONT.getTag(), // frontMatter (not title.getTag(), article.getTag(), authors.getTag(), journal)
			Tags.INTRODUCTION.getTag(),
			Tags.JOURNAL_META.getTag(),
			Tags.KEYWORDS.getTag(),
			Tags.METHODS.getTag(),
			Tags.OTHER.getTag(),
			Tags.REF_LIST.getTag(),
			Tags.RESULTS.getTag(),
			Tags.SUPPLEMENTAL.getTag(),
			Tags.TITLE.getTag(),
		};
	
	public static final List<String> MAJOR_SECTIONS = new ArrayList<String>(Arrays.asList(MAJOR_SECTIONS_ARRAY));
	public static final String PUB_ID = "pub-id";
	
	private HtmlElement htmlElement;
	private JATSElement jatsElement;
	static final String DEFAULT_SECTION_TAGGER_FILE = NormaArgProcessor.RESOURCE_NAME_TOP+"/pubstyle/sectionTagger.xml";
	private Element tagsElement;
	private Map<String, TagElementX> tagElementsByTag;

	public static final String HELP = "help";
	
	
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

	public List<HtmlTable> getTables() {
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

	public List<HtmlDiv> getAbbreviations() {
		return getDivsForCSSClass(Tags.ABBREVIATIONS.getTag());
	}

	public List<HtmlDiv> getAbstracts() {
		return getDivsForCSSClass(Tags.ABSTRACT.getTag());
	}

	public List<HtmlDiv> getAcknowledgements() {
		return getDivsForCSSClass(Tags.ACKNOWLEDGEMENT.getTag(), Tags.ACKNOWLEDGMENT.getTag(), Tags.ACK.getTag());
	}

	public List<HtmlDiv> getAppendix() {
		return getDivsForCSSClass(Tags.APPENDIX.getTag());
	}

	public List<HtmlDiv> getArticleMeta() {
		return getDivsForCSSClass(Tags.ARTICLE_META.getTag());
	}

	public List<HtmlDiv> getAuthorContrib() {
		return getDivsForCSSClass(Tags.AUTHOR_CONTRIB.getTag());
	}

	public List<HtmlDiv> getAuthorMeta() {
//		return getDivsForCSSClass(Tags.AUTHOR_META.getTag());
		return null;
	}

	public List<HtmlDiv> getBackMatter() {
		return getDivsForCSSClass(Tags.BACK.getTag());
	}
	
	public List<HtmlDiv> getCaseStudies() {
		return getDivsForCSSClass(Tags.CASE_STUDY.getTag());
	}
	
	public List<HtmlDiv> getConclusions() {
		return getDivsForCSSClass(Tags.CONCLUSION.getTag());
	}

	public List<HtmlDiv> getConflicts() {
		return getDivsForCSSClass(Tags.CONFLICT.getTag());
	}

	public List<HtmlDiv> getDiscussions() {
		return getDivsForCSSClass(Tags.DISCUSSION.getTag());
	}
	
	/**
			FIG,
			FINANCIAL,
			FRONT, // frontMatter (not title, article, authors, journal)
			INTRODUCTION,
			JOURNAL_META,
			KEYWORDS,
			METHODS,
			OTHER,
			REF_LIST,
			RESULTS,
			SUPPLEMENTAL,
			TITLE,
	 */

	public List<HtmlDiv> getFigures() {
		return getDivsForCSSClass(Tags.FIG.getTag());
	}

	public List<HtmlDiv> getFinancialSupport() {
		return getDivsForCSSClass(Tags.FINANCIAL.getTag());
	}

	public HtmlHead getFrontMatter() {
		HtmlHead head = (HtmlHead) HtmlElement.getSingleChildElement(htmlElement, HtmlHead.TAG);
		return head;
	}

	public List<HtmlDiv> getIntroductions() {
		return getDivsForCSSClass(Tags.INTRODUCTION.getTag());
	}

	public List<HtmlDiv> getJournalMeta() {
		return getDivsForCSSClass(Tags.JOURNAL_META.getTag());
	}

	public List<HtmlDiv> getKeywords() {
		return getDivsForCSSClass(Tags.KEYWORDS.getTag());
	}

	public List<HtmlDiv> getMethods() {
		return getDivsForCSSClass(Tags.METHODS.getTag());
	}

	public List<HtmlDiv> getOther() {
		return getDivsForCSSClass(Tags.OTHER.getTag());
	}

	public List<HtmlDiv> getRefLists() {
		return getDivsForCSSClass(Tags.REF_LIST.getTag());
	}

	public List<HtmlDiv> getResults() {
		return getDivsForCSSClass(Tags.RESULTS.getTag());
	}

	public List<HtmlDiv> getSupplemental() {
		return getDivsForCSSClass(Tags.SUPPLEMENTAL.getTag(), Tags.SUPPLEMENTARY_MATERIAL.getTag());
	}

	public List<HtmlDiv> getTitles() {
		return getDivsForCSSClass(Tags.TITLE.getTag());
	}

	public List<HtmlDiv> getDivsForCSSClass(String ... classNames) {
		String xpath = createXPath("div", "Divs", classNames);
		List<HtmlDiv> divs = HtmlDiv.extractDivs(getHtmlElement(), xpath);
		return divs;
	}

	public List<HtmlSpan> getSpansForCSSClass(String ... classNames) {
		String xpath = createXPath("span", "Spans", classNames);
		List<HtmlSpan> spans = HtmlSpan.extractSpans(htmlElement, xpath);
		return spans;
	}
	
	// ==============================
	
	private String createXPath(String tag, String tags, String... classNames) {
		if (classNames == null || classNames.length == 0) {
			throw new RuntimeException("get"+tags+"ForCSSClass must have at least one arg");
		}
		String xpath = "//*[local-name()='"+tag+"' and (@class='"+classNames[0]+"'";

		for (int i = 1; i < classNames.length; i++) {
			xpath += " or @class='"+classNames[i]+"'";
		}
		xpath +=")]";
		LOG.trace("XPATH: "+xpath);
		return xpath;
	}

	public void readJATS(File jatsXml) {
		Element jatElement = XMLUtil.parseQuietlyToDocumentWithoutDTD(jatsXml).getRootElement();
		jatsElement = (JATSElement) JATSElement.create(jatElement);
	}

	public Element getJATSElement() {
		return jatsElement;
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

	public Map<String, TagElementX> getOrCreateMap() {
		Element root = readSectionTags();
		tagElementsByTag = new HashMap<String, TagElementX>();
		for (int i = 0; i < root.getChildElements().size(); i++) {
			Element child = root.getChildElements().get(i);
			String localName = child.getLocalName();
			if (localName.equals(SectionTagger.HELP)) {
				continue;
			} else if (!localName.equals("tag")) {
				LOG.error("Bad tag: "+localName);
			}
			TagElementX tagElement = new TagElementX(root.getChildElements().get(i));
			String tag = tagElement.getTag();
			tagElementsByTag.put(tag, tagElement);
		}
		return tagElementsByTag;
	}

}
