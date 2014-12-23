package org.xmlcml.nhtml.document.hindawi;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import junit.framework.Assert;
import nu.xom.Element;

import org.junit.Test;
import org.xmlcml.nhtml.Fixtures;
import org.xmlcml.nhtml.tagger.MetadataElement;
import org.xmlcml.nhtml.tagger.TagElement;
import org.xmlcml.nhtml.tagger.hindawi.HindawiTagger;

public class HindawiTaggerTest {

	@Test
	public void testMetadataDefinitions() {
		HindawiTagger hindawiTagger = new HindawiTagger();
		List<MetadataElement> metadataElements = hindawiTagger.getMetadataElements();
		Assert.assertEquals("metadata", 37, metadataElements.size());
	}
	
	@Test
	public void testTagDefinitions() {
		HindawiTagger hindawiTagger = new HindawiTagger();
		List<TagElement> tagElements = hindawiTagger.getTagElements();
		Assert.assertEquals("tag", 12, tagElements.size());
	}
	
	@Test
	public void testXSLStylsheets() {
		HindawiTagger hindawiTagger = new HindawiTagger();
		List<Element> xslElements = hindawiTagger.getStyleSheetElements();
		Assert.assertEquals("xsl", 1, xslElements.size());
	}
	
	@Test
	public void testGrouping() throws Exception {
	    System.setProperty("javax.xml.transform.TransformerFactory",
	            "net.sf.saxon.TransformerFactoryImpl");
	    TransformerFactory tfactory = TransformerFactory.newInstance();
	    
	    Transformer transformer = tfactory.newTransformer(new StreamSource(Fixtures.GROUP_MAJOR_SECTIONS_XSL));
	    File saxonDir = new File("target/saxon/");
	    saxonDir.mkdirs();
    	transformer.transform(new StreamSource(Fixtures.F507405_XML),  
    		new StreamResult(new FileOutputStream(new File(saxonDir, "groupTest.xml"))));

	}
}
