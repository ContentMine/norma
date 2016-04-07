package org.xmlcml.norma.sections;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.xmlcml.norma.sections.SectionTagger.SectionTag;
import org.xmlcml.xml.XMLUtil;

import nu.xom.Element;

public class NewSectionTaggerTest {

	public static final Logger LOG = Logger.getLogger(NewSectionTaggerTest.class);

	static {
		LOG.setLevel(Level.DEBUG);
	}
	

	@Test
	public void testReadSectionTags() throws IOException {
		SectionTagger tagger = new SectionTagger();
		Element element = tagger.readSectionTags(SectionTagger.DEFAULT_SECTION_TAGGER_FILE);
		Assert.assertNotNull(element);
		Assert.assertEquals("child", 26, XMLUtil.getQueryElements(element, "./*[local-name()='tag']").size());
		
	}
	
	@Test
	public void testReadDefaultSectionTags() throws IOException {
		SectionTagger tagger = new SectionTagger();
		Element element = tagger.readSectionTags();
		Assert.assertNotNull(element);
	}

	@Test
	public void testMakeTagMap() throws IOException {
		SectionTagger tagger = new SectionTagger();
		Map<SectionTag, TagElementX> tagElementByTag = tagger.getOrCreateMap();
		Assert.assertTrue("tag set", tagElementByTag.keySet().contains(SectionTag.ABSTRACT));
		TagElementX tagElement = tagElementByTag.get(SectionTag.ABSTRACT);
		Assert.assertNotNull(tagElement);
		Assert.assertEquals("id", "cm:ABSTRACT", tagElement.getId());
		tagElement = tagger.get(SectionTag.ABSTRACT);
	}
	
	@Test
	public void testSearchTagMap() throws IOException {
		SectionTagger tagger = new SectionTagger();
		TagElementX tagElement = tagger.getTagElement(SectionTag.ABSTRACT);
		Assert.assertNotNull(tagElement);
		Assert.assertEquals("id", "cm:ABSTRACT", tagElement.getId());
	}
	
	@Test
	public void testGetRegexListXpath() throws IOException {
		SectionTagger tagger = new SectionTagger();
		TagElementX tagElement = tagger.getTagElement(SectionTag.ABSTRACT);
		List<String> regexList = tagElement.getRegexList();
//		Assert.assertEquals("[(abstract)]", regexList.toString());
		String xpath = tagElement.getXpath();
		Assert.assertEquals("//*[@class='abstract' and not(*[@abstract-type='summary'])]", xpath);
	}

	@Test
	public void testGetMajorSections() throws IOException {
		SectionTagger tagger = new SectionTagger();
		TagElementX tagElement = tagger.getTagElement(SectionTag.ABSTRACT);
		String xpath = tagElement.getXpath();
		Assert.assertEquals("//*[@class='abstract' and not(*[@abstract-type='summary'])]", xpath);
	}
	

}
