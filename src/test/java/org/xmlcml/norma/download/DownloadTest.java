package org.xmlcml.norma.download;

import java.io.File;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;
import org.xmlcml.norma.Norma;

public class DownloadTest {
	
	private static final Logger LOG = Logger.getLogger(DownloadTest.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}
	
	@Test
	@Ignore //non-public content
	public void testTransformDownloads() {
		File targetDir = new File("xref/sage");
		File sageXsl = new File("src/main/resources/org/xmlcml/norma/pubstyle/sage/toHtml.xsl");
		LOG.debug(sageXsl.getAbsolutePath()+"; "+sageXsl.exists());
		String args = "--project "+targetDir.toString()+" -i fulltext.html --html jsoup -o fulltext.xhtml";
		Norma norma = new Norma();
		norma.run(args);
		args = "--project "+targetDir.toString()+" -i fulltext.xhtml --transform "+sageXsl+" -o scholarly.html";
		norma = new Norma();
		norma.run(args);
		
	}
	
}

