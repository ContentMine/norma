package org.xmlcml.norma.sections;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.xmlcml.html.HtmlDiv;
import org.xmlcml.html.HtmlElement;
import org.xmlcml.html.HtmlSpan;
import org.xmlcml.html.HtmlTable;
import org.xmlcml.norma.NormaFixtures;
import org.xmlcml.norma.sections.SectionTagger.SectionTag;
import org.xmlcml.xml.XMLUtil;

import nu.xom.Element;

public class SectionTest {

	private static final Logger LOG = Logger.getLogger(SectionTest.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}
	
	private final static File PMC3113902 = new File(NormaFixtures.TEST_SECTIONS_DIR, "zika10/PMC3113902");
	private final static File PMC3289602 = new File(NormaFixtures.TEST_SECTIONS_DIR, "zika10/PMC3289602");
	private final static File PMC3310194 = new File(NormaFixtures.TEST_SECTIONS_DIR, "zika10/PMC3310194");
	private final static File PMC3113902HTML = new File(PMC3113902, "scholarly.html");
	private final static File PMC3289602HTML = new File(PMC3289602, "scholarly.html");
	private final static File PMC3310194HTML = new File(PMC3310194, "scholarly.html");
	private final static File PMC3113902XML = new File(PMC3113902, "fulltext.xml");
	private final static File PMC3289602XML = new File(PMC3289602, "fulltext.xml");
	private final static File PMC3310194XML = new File(PMC3310194, "fulltext.xml");

	@Test
	public void testReadFile() {
		SectionTagger tagger = new SectionTagger();
		tagger.readScholarlyHtml(PMC3289602HTML);
	}
	
	@Test
	public void testAnalyzeRead() {
		SectionTagger tagger = new SectionTagger();
		tagger.readScholarlyHtml(PMC3289602HTML);
		HtmlElement htmlElement = tagger.getHtmlElement();
		Assert.assertNotNull(htmlElement);
	}
	
	@Test
	public void testAnalyzeDivs() {
		SectionTagger tagger = new SectionTagger();
		tagger.readScholarlyHtml(PMC3289602HTML);
		HtmlElement htmlElement = tagger.getHtmlElement();
		List<HtmlDiv> divs = HtmlDiv.extractSelfAndDescendantDivs(htmlElement);
		Assert.assertTrue("divs "+divs.size(), divs.size() > 115);
	}
	
	@Test
	public void testAnalyzeSpans() {
		SectionTagger tagger = new SectionTagger();
		tagger.readScholarlyHtml(PMC3289602HTML);
		List<HtmlSpan> spans = tagger.getSpans();
		Assert.assertEquals("spans "+spans.size(), 1222, spans.size());
	}
	
	// ===================================

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

//	@Test
//	@Ignore //NYI // difficult as no tag
//	public void testAbbreviations() {
//		SectionTagger tagger = new SectionTagger();
//		tagger.readScholarlyHtml(PMC3289602HTML);
//		List<HtmlDiv> abbreviations = tagger.getAbbreviations();
//		Assert.assertEquals("divs "+abbreviations.size(), 2, abbreviations.size());  // yes, there ARE 2!
//	}
//
//	@Test
//	public void testAbstracts() {
//		SectionTagger tagger = new SectionTagger();
//		tagger.readScholarlyHtml(PMC3289602HTML);
//		List<HtmlDiv> abstracts = tagger.getAbstracts();
//		// FIXME
////		Assert.assertEquals("divs "+abstracts.size(), 2, abstracts.size());  // yes, there ARE 2!
//	}
//
//	@Test
//	public void testAcknowledgements() {
//		SectionTagger tagger = new SectionTagger();
//		tagger.readScholarlyHtml(PMC3113902HTML);
//		List<HtmlDiv> acks = tagger.getAcknowledgements();
//		// FIXME
////		Assert.assertEquals("divs "+acks.size(), 1, acks.size());  
//	}
//
//	@Test
//	@Ignore // need regex
//	public void testAppendix() {
//		SectionTagger tagger = new SectionTagger();
//		tagger.readScholarlyHtml(PMC3289602HTML);
//		List<HtmlDiv> appendixs = tagger.getAppendix();
//		Assert.assertEquals("divs "+appendixs.size(), 2, appendixs.size());  
//	}
//
//	@Test
//	public void testArticleMeta() {
//		SectionTagger tagger = new SectionTagger();
//		tagger.readScholarlyHtml(PMC3289602HTML);
//		List<HtmlDiv> articleMeta = tagger.getArticleMeta();
//		// FIXME
////		Assert.assertEquals("divs "+articleMeta.size(), 1, articleMeta.size());  
//	}
//
//	@Test
//	@Ignore // NYI
//	public void testBackMatter() {
//		SectionTagger tagger = new SectionTagger();
//		tagger.readScholarlyHtml(PMC3289602HTML);
//		List<HtmlDiv> abstracts = tagger.getBackMatter();
//		Assert.assertEquals("divs "+abstracts.size(), 2, abstracts.size());  // yes, there ARE 2!
//	}
//
//	@Test
//	@Ignore // NYI
//	public void testCaseStudy() {
//		SectionTagger tagger = new SectionTagger();
//		tagger.readScholarlyHtml(PMC3289602HTML);
//		List<HtmlDiv> abstracts = tagger.getCaseStudies();
//		Assert.assertEquals("divs "+abstracts.size(), 2, abstracts.size());  // yes, there ARE 2!
//	}
//
//	@Test
//	public void testConflicts() {
//		SectionTagger tagger = new SectionTagger();
//		tagger.readScholarlyHtml(PMC3289602HTML);
//		List<HtmlDiv> discussions = tagger.getConflicts();
//		// FIXME
////		Assert.assertEquals("divs "+discussions.size(), 1, discussions.size()); 
//	}
//
//	@Test
//	@Ignore // NYI
//	public void testConclusions() {
//		SectionTagger tagger = new SectionTagger();
//		tagger.readScholarlyHtml(PMC3289602HTML);
//		List<HtmlDiv> discussions = tagger.getConclusions();
//		Assert.assertEquals("divs "+discussions.size(), 1, discussions.size()); 
//	}
//
//	@Test
//	public void testDiscussion() {
//		SectionTagger tagger = new SectionTagger();
//		tagger.readScholarlyHtml(PMC3289602HTML);
//		List<HtmlDiv> discussions = tagger.getDiscussions();
//		// FIXME
////		Assert.assertEquals("divs "+discussions.size(), 1, discussions.size()); 
//	}
//
//	/**
//	FIG,
//	FINANCIAL,
//	FRONT, // frontMatter (not title, article, authors, journal)
//	INTRODUCTION,
//	JOURNAL_META,
//	KEYWORDS,
//	METHODS,
//	OTHER,
//	REF_LIST,
//	RESULTS,
//	SUPPLEMENTAL,
//	TITLE,
//*/
//
//	@Test
//	public void testFigs() {
//		SectionTagger tagger = new SectionTagger();
//		tagger.readScholarlyHtml(PMC3289602HTML);
//		List<HtmlDiv> divs = tagger.getDivsForCSSClass(SectionTagger.SectionTag.FIG);
//		// FIXME
////		Assert.assertEquals("divs "+divs.size(), 2, divs.size());
//	}
//
//	@Test
//	public void testFinancials() {
//		SectionTagger tagger = new SectionTagger();
//		tagger.readScholarlyHtml(PMC3289602HTML);
//		List<HtmlDiv> intros = tagger.getFinancialSupport();
//		// FIXME
////		Assert.assertEquals("divs "+intros.size(), 1, intros.size());  // yes, there ARE 2!
//	}
//
//	@Test
//	public void testFrontMatter() {
//		SectionTagger tagger = new SectionTagger();
////		tagger.readScholarlyHtml(PMC3113902HTML);
//		tagger.readScholarlyHtml(PMC3289602HTML);
//		HtmlHead head = tagger.getFrontMatter();
//		Assert.assertNotNull("head ", head);  
//	}
//
//	@Test
//	public void testIntroductions() {
//		SectionTagger tagger = new SectionTagger();
//		tagger.readScholarlyHtml(PMC3289602HTML);
//		List<HtmlDiv> intros = tagger.getIntroductions();
//		// FIXME
////		Assert.assertEquals("divs "+intros.size(), 1, intros.size());  // yes, there ARE 2!
//	}
//
//	@Test
//	public void testJournalMeta() {
//		SectionTagger tagger = new SectionTagger();
//		tagger.readScholarlyHtml(PMC3289602HTML);
//		List<HtmlDiv> methods = tagger.getJournalMeta();
//		// FIXME
////		Assert.assertEquals("divs "+methods.size(), 1, methods.size()); 
//	}
//
//	@Test
//	@Ignore // NYI
//	public void testKeywords() {
//		SectionTagger tagger = new SectionTagger();
//		tagger.readScholarlyHtml(PMC3289602HTML);
//		List<HtmlDiv> methods = tagger.getKeywords();
//		Assert.assertEquals("divs "+methods.size(), 1, methods.size()); 
//	}
//
//	@Test
//	public void testMethods() {
//		SectionTagger tagger = new SectionTagger();
//		tagger.readScholarlyHtml(PMC3289602HTML);
//		List<HtmlDiv> methods = tagger.getMethods();
//		// FIXME
////		Assert.assertEquals("divs "+methods.size(), 1, methods.size()); 
//	}
//
//	@Test
//	public void testOther() {
//		SectionTagger tagger = new SectionTagger();
//		tagger.readScholarlyHtml(PMC3289602HTML);
//		List<HtmlDiv> methods = tagger.getOther();
//		// FIXME
////		Assert.assertEquals("divs "+methods.size(), 1, methods.size()); 
//	}
//
//	public void testRefList() {
//		SectionTagger tagger = new SectionTagger();
//		tagger.readScholarlyHtml(PMC3289602HTML);
//		List<HtmlDiv> discussions = tagger.getRefLists();
//		Assert.assertEquals("divs "+discussions.size(), 1, discussions.size()); 
//	}
//
//	@Test
//	public void testResults() {
//		SectionTagger tagger = new SectionTagger();
//		tagger.readScholarlyHtml(PMC3289602HTML);
//		List<HtmlDiv> results = tagger.getResults();
//		// FIXME
////		Assert.assertEquals("divs "+results.size(), 1, results.size()); 
//	}
//
//	@Test
//	//@Ignore // NYI
//	public void testSupplemental() {
//		SectionTagger tagger = new SectionTagger();
//		tagger.readScholarlyHtml(PMC3310194HTML);
//		List<HtmlDiv> results = tagger.getSupplemental();
//		// FIXME
////		Assert.assertEquals("divs "+results.size(), 1, results.size()); 
//	}
//
//	public void testSupport() {
//		SectionTagger tagger = new SectionTagger();
//		tagger.readScholarlyHtml(PMC3289602HTML);
//		List<HtmlDiv> discussions = tagger.getFinancialSupport();
//		Assert.assertEquals("divs "+discussions.size(), 1, discussions.size()); 
//	}

	
	@Test
	public void testCreateJATSElement() throws IOException {
		SectionTagger tagger = new SectionTagger();
		tagger.readJATS(PMC3289602XML);
		Element jatsElement = tagger.getJATSHtmlElement();
		LOG.debug(jatsElement.toString().length());
		XMLUtil.debug(jatsElement, new File("target/jats/PMC3289602.html"), 1);
	}

	@Test
	public void testCreateManyJATSElement() throws IOException {
		SectionTagger tagger = new SectionTagger();
		File root = new File("/Users/pm286/workspace/projects/std");
//		File root = new File("/Users/pm286/workspace/projects/trastuzumab");
		// has a broken file
//		File root = new File("/Users/pm286/workspace/projects/trials/trialsjournal");
//		File root = new File("/Users/pm286/workspace/projects/zika");
//		File root = new File(NormaFixtures.TEST_PUBSTYLE_DIR, "getpapers/anopheles");
//		File root = new File(NormaFixtures.TEST_SECTIONS_DIR, "zika10");
		LOG.debug(root+" || "+root.exists());
		for (File file : root.listFiles()) {
			if (!file.isDirectory()) continue;
			for (File file1 : file.listFiles()) {
				if (file1.toString().endsWith("fulltext.xml")) {
					try {
						tagger.readJATS(file1);
					} catch (Exception e) {
						LOG.debug("skipped "+file1+"  ||  "+e);
						continue;
//						throw new RuntimeException();
					}
					Element jatsElement = tagger.getJATSHtmlElement();
					File outfile = new File("target/jats/"+FilenameUtils.getPath(file1.toString())+"scholarly.html");
//					LOG.debug(outfile);
					XMLUtil.debug(jatsElement, outfile, 1);
				}
			}
		}
	}

	@Test
	public void testSectionJATS() throws IOException {
		SectionTagger tagger = new SectionTagger();
		tagger.readJATS(PMC3289602XML);
		LOG.debug(PMC3113902XML);
		TagElementX tagElement = tagger.getTagElement(SectionTag.ABSTRACT);
		List<String> regexList = tagElement.getRegexList();
		String xpath = tagElement.getXpath();
		LOG.debug(xpath);
		Element jatsElement = tagger.getJATSHtmlElement();
		List<Element> sections = XMLUtil.getQueryElements(jatsElement, xpath);
		Assert.assertEquals("abstracts", 2, sections.size()); // yes there are 2
		LOG.debug(sections);

	}

	@Test
	public void testGetSections() throws IOException {
		SectionTagger tagger = new SectionTagger();
		tagger.readJATS(PMC3289602XML);
		XMLUtil.debug(tagger.getJATSHtmlElement(),new FileOutputStream("target/jats/PMC3289602a.html"), 1);
		LOG.debug(PMC3113902XML);
		String xml = FileUtils.readFileToString(PMC3113902XML);
		List<Element> sections = tagger.getSections(SectionTag.INTRO);
		Assert.assertEquals("intro", 1, sections.size()); 
		sections = tagger.getSections(SectionTag.DISCUSS);
		Assert.assertEquals("intro", 1, sections.size()); 
		sections = tagger.getSections(SectionTag.SUBTITLE);
		Assert.assertEquals("intro", 25, sections.size()); 
		sections = tagger.getSections(SectionTag.ARTICLE_TITLE);
		Assert.assertEquals("articleTitle", 1, sections.size());
		String title = sections.get(0).getValue().replaceAll("\\s+", " ");
		Assert.assertEquals("title", "Genetic Characterization of Zika Virus Strains: Geographic Expansion of the Asian Lineage", title);
		sections = tagger.getSections(SectionTag.ARTICLE_META);
		Assert.assertEquals("articleMeta", 1, sections.size()); 
		sections = tagger.getSections(SectionTag.JOURNAL_META);
		Assert.assertEquals("journalMeta", 1, sections.size()); 
		sections = tagger.getSections(SectionTag.JOURNAL_TITLE);
		Assert.assertEquals("journalTitle", 1, sections.size());
		title = sections.get(0).getValue().replaceAll("\\s+", " ");
		Assert.assertEquals("title", "PLoS Neglected Tropical Diseases", title);
		sections = tagger.getSections(SectionTag.PMCID);
		Assert.assertEquals("pmcid", 1, sections.size());
		Assert.assertEquals("pmcid", "3289602", sections.get(0).getValue());
		sections = tagger.getSections(SectionTag.CONTRIB);
		// this includes an editor
		Assert.assertEquals("contrib", 10, sections.size());
		sections = tagger.getSections(SectionTag.TABLE);
		Assert.assertEquals("table", 3, sections.size());
		sections = tagger.getSections(SectionTag.FIG);
		Assert.assertEquals("fig", 2, sections.size());
		sections = tagger.getSections(SectionTag.METHODS);
		Assert.assertEquals("methods", 1, sections.size());
		sections = tagger.getSections(SectionTag.RESULTS);
		Assert.assertEquals("results", 1, sections.size());
		sections = tagger.getSections(SectionTag.ACK_FUND);
		Assert.assertEquals("fig", 1, sections.size());
		sections = tagger.getSections(SectionTag.REF);
		Assert.assertEquals("ref", 57, sections.size());
		
	}

	

	
	



	// ===========================================
	
	@Test
	public void testTables() {
		SectionTagger tagger = new SectionTagger();
		tagger.readScholarlyHtml(PMC3289602HTML);
		List<HtmlTable> tables = tagger.getTablesX();
		Assert.assertEquals("tables "+tables.size(), 3, tables.size());
	}

	@Test
	public void testAnalyzeSpanPubId() {
		SectionTagger tagger = new SectionTagger();
		tagger.readScholarlyHtml(PMC3289602HTML);
		List<HtmlSpan> spans = tagger.getSpansForCSSClass(SectionTagger.PUB_ID);
		Assert.assertEquals("spans "+spans.size(), 45, spans.size());
	}



}
