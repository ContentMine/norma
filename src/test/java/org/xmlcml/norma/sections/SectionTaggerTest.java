package org.xmlcml.norma.sections;

import java.io.IOException;
import java.util.Map;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.xmlcml.xml.XMLUtil;

import nu.xom.Element;

public class SectionTaggerTest {

	public static final Logger LOG = Logger.getLogger(SectionTaggerTest.class);

	static {
		LOG.setLevel(Level.DEBUG);
	}
	

	@Test
	public void testReadSectionTags() throws IOException {
		SectionTagger tagger = new SectionTagger();
		Element element = tagger.readSectionTags(SectionTagger.DEFAULT_SECTION_TAGGER_FILE);
		Assert.assertNotNull(element);
		Assert.assertEquals("child", 17, XMLUtil.getQueryElements(element, "./*[local-name()='tag']").size());
		
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
		Map<String, TagElementX> tagElementByTag = tagger.getOrCreateMap();
		LOG.debug("m: "+tagElementByTag.keySet());
		LOG.debug(SectionTagger.Tags.ABSTRACT.getTag());
		TagElementX tagElement = tagElementByTag.get(SectionTagger.Tags.ABSTRACT.getTag());
		Assert.assertNotNull(tagElement);
		Assert.assertEquals("id", "cm:ABSTRACT", tagElement.getId());
	}
	

}
