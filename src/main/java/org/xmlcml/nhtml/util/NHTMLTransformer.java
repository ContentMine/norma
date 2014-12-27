package org.xmlcml.nhtml.util;

import java.io.File;
import java.io.FileOutputStream;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class NHTMLTransformer {

	private static  Transformer createTransformer(File stylesheet) throws Exception {
		System.setProperty("javax.xml.transform.TransformerFactory",
	            "net.sf.saxon.TransformerFactoryImpl");
	    TransformerFactory tfactory = TransformerFactory.newInstance();
	    Transformer transformer = tfactory.newTransformer(new StreamSource(stylesheet));
		return transformer;
	}

	public static void transform(File infile, File stylesheet, File outfile) throws Exception {
		Transformer transformer = NHTMLTransformer.createTransformer(stylesheet);
	    File outputDir = outfile.getParentFile();
	    outputDir.mkdirs();
		transformer.transform(new StreamSource(infile),  
			new StreamResult(new FileOutputStream(outfile)));
	}

}
