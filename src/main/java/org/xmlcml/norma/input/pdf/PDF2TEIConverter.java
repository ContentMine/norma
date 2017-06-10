package org.xmlcml.norma.input.pdf;

import java.io.File;
import java.util.Arrays;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.xmlcml.cproject.files.CTree;

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
