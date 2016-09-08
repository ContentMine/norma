package org.xmlcml.norma.pubstyle.stylesheet;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.NameFileFilter;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.xmlcml.norma.NormaFixtures;
import org.xmlcml.xml.XMLUtil;

import junit.framework.Assert;
import nu.xom.Element;

public class PublisherIndexTest {

	private static final Logger LOG = Logger.getLogger(PublisherIndexTest.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}
	
	@Test
	public void testFindPublisher() {
		PublisherIndex publisherIndex = new PublisherIndex();
		List<File> files = publisherIndex.readHtmlXslDirectory(NormaFixtures.MAIN_PUBSTYLE_DIR);
		Assert.assertTrue("htmlXsl", files.size() > 20);
		
		PublisherSelector publisherSelector = publisherIndex.getPublisherSelectorByPublisher("The Royal Society");
		Assert.assertNotNull("publisherSelector not hull", publisherSelector);
		Assert.assertEquals(publisherSelector.getOrCreatePrefix(), "10.1098");
		
		
		publisherSelector = publisherIndex.getPublisherSelectorByPrefix("10.1098");
		Assert.assertNotNull("publisherSelector not hull", publisherSelector);
		Assert.assertEquals(publisherSelector.getOrCreatePublisher(), "The Royal Society");
		
		publisherSelector = publisherIndex.getPublisherSelectorByPublisher("American Chemical Society");
		Assert.assertNotNull("publisherSelector not hull", publisherSelector);
		Assert.assertEquals(publisherSelector.getOrCreatePrefix(), "10.1021");
		
		publisherSelector = publisherIndex.getPublisherSelectorByPrefix("10.1021");
		Assert.assertNotNull("publisherSelector not hull", publisherSelector);
		Assert.assertEquals(publisherSelector.getOrCreatePublisher(), "American Chemical Society");
		

		
	}
	
	@Test
	public void testCreatePublisherIndex() {
		PublisherIndex publisherIndex = new PublisherIndex(NormaFixtures.MAIN_PUBSTYLE_DIR);
		List<PublisherSelector> publisherSelectors = publisherIndex.getPublisherSelectors();
		Assert.assertTrue("selectors"+publisherSelectors.size(), publisherSelectors.size() > 15);
	}
	
	@Test
	public void testIdentifyDocument() {
		File rsFulltext = new File(NormaFixtures.TEST_PUBSTYLE_DIR, "rs/ccby/rsos.160123/fulltext.xhtml");
		Element fulltextXml = XMLUtil.parseQuietlyToDocument(rsFulltext).getRootElement();
		PublisherIndex publisherIndex = new PublisherIndex(NormaFixtures.MAIN_PUBSTYLE_DIR);
		
		PublisherSelector publisherSelector = publisherIndex.getPublisherSelectorByPrefix("10.1098");
		boolean matches = publisherSelector.matches(fulltextXml);
		Assert.assertTrue(matches);
		publisherSelector = publisherIndex.getPublisherSelectorByPublisher("The Royal Society");
		matches = publisherSelector.matches(fulltextXml);
		Assert.assertTrue(matches);
		
	}
	
	@Test
	public void testIdentifyDocument1() {
		File fulltext = new File("src/test/resources/org/xmlcml/norma/pubstyle/acs/out/clean/jo402790x/fulltext.xhtml");
		PublisherIndex publisherIndex = new PublisherIndex(NormaFixtures.MAIN_PUBSTYLE_DIR);
		PublisherSelector publisherSelector = publisherIndex.getPublisherSelectorForFile(fulltext);
		Assert.assertNotNull(publisherSelector);
	}
	
	@Test
	public void testIdentifyDocuments() {
		List<File> fulltextXhtmlFiles = new ArrayList<File>(FileUtils.listFiles(
				NormaFixtures.TEST_PUBSTYLE_DIR, new NameFileFilter("fulltext.xhtml"), new WildcardFileFilter("*")));
		Assert.assertTrue("list files: "+fulltextXhtmlFiles.size(), fulltextXhtmlFiles.size() > 20);
		PublisherIndex publisherIndex = new PublisherIndex(NormaFixtures.MAIN_PUBSTYLE_DIR);
		for (File fulltextXhtmlFile : fulltextXhtmlFiles) {
			PublisherSelector publisherSelector = publisherIndex.getPublisherSelectorForFile(fulltextXhtmlFile);
			LOG.debug(fulltextXhtmlFile.getParentFile().toString().replace("src/test/resources/org/xmlcml/norma/pubstyle/", "")+"; "+publisherSelector);
		}
	}
	
	public void testHtml2Scholarly2StepConversionClean() throws IOException {
		File fulltext = new File("src/test/resources/org/xmlcml/norma/pubstyle/acs/out/clean/jo402790x/fulltext.xhtml");
		PublisherIndex publisherIndex = new PublisherIndex(NormaFixtures.MAIN_PUBSTYLE_DIR);
		PublisherSelector publisherSelector = publisherIndex.getPublisherSelectorForFile(fulltext);
		Assert.assertNotNull(publisherSelector);
		
//		NormaFixtures.tidyTransformAndClean(TEST1, TARGET1, PUB);
	}

	
}
