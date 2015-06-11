package org.xmlcml.norma.pubstyle.metabolomics;

import java.io.File;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import nu.xom.Element;

import org.junit.Test;
import org.xmlcml.norma.Fixtures;
import org.xmlcml.xml.XMLUtil;

public class MZMLTest {

	@Test
	public void testReadMZML() throws TransformerFactoryConfigurationError, TransformerException {
		File mzDir = new File(Fixtures.TEST_PUBSTYLE_DIR, "metabolomics/");
		File mzFile = new File(mzDir, "small.pwiz.1.1.mzML.xml");
		Source mzSource = new StreamSource(mzFile);
		Element mzElement = XMLUtil.parseQuietlyToDocument(mzFile).getRootElement();
		File xslFile = new File(mzDir, "mz2tom.xsl");
		Source xslSource = new StreamSource(xslFile);
		Transformer transformer = TransformerFactory.newInstance().newTransformer(xslSource);
		File mzOutFile = new File("target/mz/small.xml");
		mzOutFile.getParentFile().mkdirs();
		Result mzResultSource = new StreamResult(mzOutFile);
		transformer.transform(mzSource, mzResultSource);
		
	}
}
