package org.xmlcml.norma.input.pdf;

import java.io.File;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class PDF2TEIConverter {
	private static final Logger LOG = Logger.getLogger(PDF2TEIConverter.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}

	public PDF2TEIConverter() {
		
	}
	
	public File convertFulltextPDFToTEI(File pdfFile) throws Exception {
		
		throw new RuntimeException("USE Grobid outside Norma");

	}
		 
}
