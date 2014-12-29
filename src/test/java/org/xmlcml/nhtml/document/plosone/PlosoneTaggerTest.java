package org.xmlcml.nhtml.document.plosone;

import java.io.File;
import java.io.FileOutputStream;

import junit.framework.Assert;
import nu.xom.Element;

import org.apache.log4j.Logger;
import org.eclipse.jetty.util.log.Log;
import org.junit.Test;
import org.xmlcml.nhtml.Fixtures;
import org.xmlcml.nhtml.tagger.DocumentTagger;
import org.xmlcml.nhtml.tagger.plosone.HTMLPlosoneTagger;
import org.xmlcml.nhtml.tagger.plosone.XMLPlosoneTagger;
import org.xmlcml.xml.XMLUtil;

public class PlosoneTaggerTest {

	private static final Logger LOG = Logger.getLogger(PlosoneTaggerTest.class);
	
	public final static String[] HTML_TAG_LIST = {
		"html",
		"head",
		"body",
		"page_wrap",
		"topbanner",
		"center",
		"pagebdy_wrap",
		"pagebdy",
//		"article-block",
		"header",
		"authors",
		"author",
		"date_doi_line",
		"publication_date",
		"doi",
		"main_cf",
		"article",
		"abstract",
		"articleinfo",
		"citation",
		"received",
		"accepted",
		"published",
		"copyright licence",
		"data_availability",
		"funding",
		"competing",
//		"section",
		"introduction",
		"materials methods",
		"results discussion",
		"conclusions",
		"acknowledgments",
		"contributions",
		"references",
		"reference",
	};

	
	@Test
	/** tags most sections of a typical PLOSONE paper.
	 * 
	 * SHOWCASE
	 * 
	 * @throws Exception
	 */
	public void test0113556XML() throws Exception {
		DocumentTagger plosoneTagger = new XMLPlosoneTagger();
		Element taggedElement = plosoneTagger.addTagsToSections(Fixtures.F0113556_XML);
		new File("target/plosone/").mkdirs();
		XMLUtil.debug(taggedElement, new FileOutputStream("target/plosone/tagged0113556.xml"), 0);
		String message = XMLUtil.equalsCanonically(Fixtures.F0113556_TAGGED_XML, taggedElement, true);
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
		DocumentTagger plosoneTagger = new HTMLPlosoneTagger();
		Element taggedElement = plosoneTagger.addTagsToSections(Fixtures.F0113556_HTML);
		new File("target/plosone/").mkdirs();
		File outputFile = new File("target/plosone/tagged0113556.html");
		XMLUtil.debug(taggedElement, new FileOutputStream(outputFile), 0);
		String message = XMLUtil.equalsCanonically(outputFile, taggedElement, true);
		Assert.assertNull("message: "+message, message);
		
		Assert.assertEquals("head", 1, XMLUtil.getQueryElements(taggedElement, "//*[@tag='head']").size());
		Assert.assertEquals("journaltitle", 0, XMLUtil.getQueryElements(taggedElement, "//*[@tag='journaltitle']").size());
		for (String tag : HTML_TAG_LIST) {
			int size = XMLUtil.getQueryElements(taggedElement, "//*[@tag='"+tag+"']").size();
			LOG.debug(tag+"="+size);
		}

	}


}
