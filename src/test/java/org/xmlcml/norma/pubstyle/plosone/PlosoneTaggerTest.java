package org.xmlcml.norma.pubstyle.plosone;

import java.io.File;
import java.io.FileOutputStream;

import junit.framework.Assert;
import nu.xom.Element;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;
import org.xmlcml.norma.NormaFixtures;
import org.xmlcml.norma.Norma;
import org.xmlcml.norma.tagger.PubstyleTagger;
import org.xmlcml.norma.tagger.Tags;
import org.xmlcml.norma.tagger.plosone.HTMLPlosoneTagger;
import org.xmlcml.norma.tagger.plosone.XMLPlosoneTagger;
import org.xmlcml.xml.XMLUtil;

public class PlosoneTaggerTest {
	
	private static final Logger LOG = Logger.getLogger(PlosoneTaggerTest.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}

	
//    
//    
//	public final static String ABSTRACT = "abstract";
//	public final static String ACCEPTED = "accepted";
//	public final static String ACKNOWKLEDGMENTS = "acknowledgments";
//	public final static String ARTICLE = "article";
//	public final static String ARTICLE_INFO = "articleinfo";
//	public final static String ARTICLE_BLOCK = "article_block";
//	public final static String AUTHOR = "author";
//	public final static String AUTHORS = "authors";
//	public final static String BODY = "body";
//	public final static String CENTER = "center";
//	public final static String CITATION = "citation";
//	public final static String CITATION_AUTHOR = "citation_author";
//	public final static String CITATION_AUTHORS = "citation_authors";
//	public final static String CITATION_AUTHOR_INSTITUTION = "citation_author_institution";
//	public final static String CITATION_DATE = "citation_date";
//	public final static String CITATION_DOI = "citation_doi";
//	public final static String CITATION_FIRSTPAGE = "citation_firstpage";
//	public final static String CITATIONS_ISSN = "citation_issn";
//	public final static String CITATION_ISSUE = "citation_issue";
//	public final static String CITATION_JOURNAL_ABBREV = "citation_journal_abbrev";
//	public final static String CITATION_PDF_URL = "citation_pdf_url";
//	public final static String CITATION_PUBLISHER = "citation_publisher";
//	public final static String CITATION_REFERENCE = "citation_reference";
//	public final static String COMPETING = "competing";
//	public final static String CONCLUSIONS = "conclusions";
//	public final static String CONTRIBUTIONS = "contributions";
//	public final static String COPYRIGHT_LICENCE = "copyright licence";
//	public final static String CURRENT_JOURNAL = "current_journal";
//	public final static String DATA_AVAILABILITY = "data_availability";
//	public final static String DATE = "date";
//	public final static String DATE_DOI_LINE = "date_doi_line";
//	public final static String DC_IDENTIFIER = "dc_identifier";
//	public final static String DOI = "doi";
//	public final static String FIGURE = "figure";
//	public final static String FIGURE_CAPTION = "figure_caption";
//	public final static String FIGURE_INLINE_DOWNLOAD = "figure_inline_download";
//	public final static String FIGURE_INLINE_DOWNLOAD_PNG = "figure_inline_download_png";
//	public final static String FIGURE_TITLE = "figure_title";
//	public final static String FORMAT = "format";
//	public final static String FUNDING = "funding";
//	public final static String HEAD = "head";
//	public final static String HEADER = "header";
//	public final static String HTML = "html";
//	public final static String INTRODUCTION = "introduction";
//	public final static String ISSN = "issn";
//	public final static String ISSUE = "issue";
//	public final static String JOURNAL = "journal";
//	public final static String LANGUAGE = "language";
//	public final static String MAIN_CF = "main_cf";
//	public final static String MATERIALS_METHODS = "materials methods";
//	public final static String META_ABSTRACT = "meta_abstract";
//	public final static String PAGEBDY = "pagebdy";
//	public final static String PAGEBDY_WRAP = "pagebdy_wrap";
//	public final static String PAGE_WRAP = "page_wrap";
//	public final static String PUBLICATION_DATE = "publication_date";
//	public final static String PUBLISHED = "published";
//	public final static String PUBLISHER = "publisher";
//	public final static String RECEIVED = "received";
//	public final static String REFERENCE = "reference";
//	public final static String REFERENCES = "references";
//	public final static String RESULTS_DISCUSSION = "results discussion";
//	public final static String START_PAGE = "start_page";
//	public final static String TABLE_TITLE = "table_title";
//	public final static String TITLE = "title";
//	public final static String TOPBANNER = "topbanner";
//	public final static String VOLUME = "volume";
//	public final static String YEAR = "year";
//
//	public final static String[] HTML_TAG_LIST = {
//		ABSTRACT,
//		ACCEPTED,
//		ACKNOWKLEDGMENTS,
//		ARTICLE,
//		ARTICLE_INFO,
//		ARTICLE_BLOCK,
//		AUTHOR,
//		AUTHORS,
//		BODY,
//		CENTER,
//		CITATION,
//		CITATION_AUTHOR,
//		CITATION_AUTHORS,
//		CITATION_AUTHOR_INSTITUTION,
//		CITATION_DATE,
//		CITATION_DOI,
//		CITATION_FIRSTPAGE,
//		CITATIONS_ISSN,
//		CITATION_ISSUE,
//		CITATION_JOURNAL_ABBREV,
//		CITATION_PDF_URL,
//		CITATION_PUBLISHER,
//		CITATION_REFERENCE,
//		COMPETING,
//		CONCLUSIONS,
//		CONTRIBUTIONS,
//		COPYRIGHT_LICENCE,
//		CURRENT_JOURNAL,
//		DATA_AVAILABILITY,
//		DATE,
//		DATE_DOI_LINE,
//		DC_IDENTIFIER,
//		DOI,
//		FIGURE,
//		FIGURE_CAPTION,
//		FIGURE_INLINE_DOWNLOAD,
//		FIGURE_INLINE_DOWNLOAD_PNG,
//		FIGURE_TITLE,
//		FORMAT,
//		FUNDING,
//		HEAD,
//		HEADER,
//		HTML,
//		INTRODUCTION,
//		ISSN,
//		ISSUE,
//		JOURNAL,
//		LANGUAGE,
//		MAIN_CF,
//		MATERIALS_METHODS,
//		META_ABSTRACT,
//		PAGEBDY,
//		PAGEBDY_WRAP,
//		PAGE_WRAP,
//		PUBLICATION_DATE,
//		PUBLISHED,
//		PUBLISHER,
//		RECEIVED,
//		REFERENCE,
//		REFERENCES,
//		RESULTS_DISCUSSION,
//		START_PAGE,
//		TABLE_TITLE,
//		TITLE,
//		TOPBANNER,
//		VOLUME,
//		YEAR,
//	};

	
	@Test
	/** tags most sections of a typical PLOSONE paper.
	 * 
	 * SHOWCASE
	 * 
	 * @throws Exception
	 */
	public void test0113556XML() throws Exception {
		PubstyleTagger plosoneTagger = new XMLPlosoneTagger();
		Element taggedElement = plosoneTagger.addTagsToSections(NormaFixtures.F0113556_XML);
		new File("target/plosone/").mkdirs();
		XMLUtil.debug(taggedElement, new FileOutputStream("target/plosone/tagged0113556.xml"), 0);
		String message = XMLUtil.equalsCanonically(NormaFixtures.F0113556_TAGGED_XML, taggedElement, true);
		Assert.assertNull("message: "+message, message);
		
		Assert.assertEquals("journaltitle", 1, XMLUtil.getQueryElements(taggedElement, "//*[@tag='journaltitle']").size());
		Assert.assertEquals("issn", 1, XMLUtil.getQueryElements(taggedElement, "//*[@tag='issn']").size());
		Assert.assertEquals("doi", 1, XMLUtil.getQueryElements(taggedElement, "//*[@tag='doi']").size());
		Assert.assertEquals("author", 6, XMLUtil.getQueryElements(taggedElement, "//*[@tag='author']").size());
		Assert.assertEquals("license", 1, XMLUtil.getQueryElements(taggedElement, "//*[@tag='license']").size());
		Assert.assertEquals("fundinggroup", 1, XMLUtil.getQueryElements(taggedElement, "//*[@tag='funding_group']").size());
		Assert.assertEquals("datavailable", 1, XMLUtil.getQueryElements(taggedElement, "//*[@tag='data_availability']").size());
		
		Assert.assertEquals("article", 1, XMLUtil.getQueryElements(taggedElement, "//*[@tag='article']").size());
		Assert.assertEquals("abstract", 1, XMLUtil.getQueryElements(taggedElement, "//*[@tag='abstract']").size());
		Assert.assertEquals("copyright", 0, XMLUtil.getQueryElements(taggedElement, "//*[@tag='copyright']").size());
		Assert.assertEquals("background", 1, XMLUtil.getQueryElements(taggedElement, "//*[@tag='background']").size());
		Assert.assertEquals("results", 1, XMLUtil.getQueryElements(taggedElement, "//*[@tag='results']").size());
		Assert.assertEquals("discussion", 0, XMLUtil.getQueryElements(taggedElement, "//*[@tag='discussion']").size());
		Assert.assertEquals("conclusion", 1, XMLUtil.getQueryElements(taggedElement, "//*[@tag='conclusion']").size());
		Assert.assertEquals("abbreviations", 0, XMLUtil.getQueryElements(taggedElement, "//*[@tag='abbreviations']").size());
		Assert.assertEquals("disclosure", 0, XMLUtil.getQueryElements(taggedElement, "//*[@tag='disclosure']").size());
		Assert.assertEquals("acknowledgments", 1, XMLUtil.getQueryElements(taggedElement, "//*[@tag='acknowledgments']").size());
		Assert.assertEquals("competing", 0, XMLUtil.getQueryElements(taggedElement, "//*[@tag='competing']").size());
		Assert.assertEquals("references", 1, XMLUtil.getQueryElements(taggedElement, "//*[@tag='references']").size());
		Assert.assertEquals("methods", 1, XMLUtil.getQueryElements(taggedElement, "//*[@tag='methods']").size());
		
		Assert.assertEquals("figure",  4, XMLUtil.getQueryElements(taggedElement, "//*[@tag='figure']").size());
		Assert.assertEquals("figure_caption",  4, XMLUtil.getQueryElements(taggedElement, "//*[@tag='figure_caption']").size());
		Assert.assertEquals("table",  4, XMLUtil.getQueryElements(taggedElement, "//*[@tag='table']").size());
		Assert.assertEquals("table_caption",  4, XMLUtil.getQueryElements(taggedElement, "//*[@tag='table_caption']").size());
	}

	@Test
	public void test0113556HTML() throws Exception {
		PubstyleTagger plosoneTagger = new HTMLPlosoneTagger();
		Element taggedElement = plosoneTagger.addTagsToSections(NormaFixtures.F0113556_HTML);
		new File("target/plosone/").mkdirs();
		File outputFile = new File("target/plosone/tagged0113556.html");
		XMLUtil.debug(taggedElement, new FileOutputStream(outputFile), 0);
		String message = XMLUtil.equalsCanonically(outputFile, taggedElement, true);
		Assert.assertNull("message: "+message, message);
		
		Assert.assertEquals("head", 1, XMLUtil.getQueryElements(taggedElement, "//*[@tag='head']").size());
		Assert.assertEquals("journaltitle", 0, XMLUtil.getQueryElements(taggedElement, "//*[@tag='journaltitle']").size());
		for (String tag : Tags.TAG_LIST) {
			int size = XMLUtil.getQueryElements(taggedElement, "//*[@tag='"+tag+"']").size();
			LOG.trace(tag+"="+size);
		}

	}
	
	@Test
	@Ignore // NYI
	public void testReadPlosoneHTMLURL() {
		String[] args = {
			"-i", "http://www.plosone.org/article/info%3Adoi%2F10.1371%2Fjournal.pone.0111303",
			"-o", "target/pone/",
		};
		Norma.main(args);
			
	}

	@Test
	public void testReadPlosoneXMLURL() {
		
//		http://www.plosone.org/article/fetchObjectAttachment.action?uri=info%3Adoi%2F10.1371%2Fjournal.pone.0111303&representation=XML			
	}
	
	@Test
	@Ignore // NYI
	public void testIOCommands() throws Exception {

		File outputFile = new File("target/plosone/0113556.html");
		outputFile.getParentFile().mkdirs();
		String[] args = {
				"-i", NormaFixtures.F0113556_HTML.toString(),
				"-o", outputFile.toString(),
			};
			Norma.main(args);
	}

	@Test
	@Ignore // NYI
	public void testTaggingCommands() throws Exception {

		File outputFile = new File("target/plosone/0113556.tagged.html");
		outputFile.getParentFile().mkdirs();
		String[] args = {
				"-i", NormaFixtures.F0113556_HTML.toString(),
				"-p", // gives list of pubstyles to sysout
				"-o", outputFile.toString(),
			};
			Norma.main(args);
	}

	@Test
	@Ignore // NYI
	public void testIOAndTaggingCommands() throws Exception {

		File outputFile = new File("target/plosone/0113556.tagged.html");
		outputFile.getParentFile().mkdirs();
		String[] args = {
				"-i", NormaFixtures.F0113556_HTML.toString(),
				"-p", "plosone",
				"-o", outputFile.toString(),
			};
			Norma.main(args);
	}


}
