package org.xmlcml.norma.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import nu.xom.Element;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.xmlcml.html.HtmlElement;
import org.xmlcml.html.HtmlFactory;
import org.xmlcml.xml.XMLUtil;

public class SHTMLTransformer {
	
	
	private static final Logger LOG = Logger.getLogger(SHTMLTransformer.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}

	private static  Transformer createTransformer(File stylesheet) throws Exception {
		System.setProperty("javax.xml.transform.TransformerFactory",
	            "net.sf.saxon.TransformerFactoryImpl");
	    TransformerFactory tfactory = TransformerFactory.newInstance();
	    Transformer transformer = tfactory.newTransformer(new StreamSource(stylesheet));
		return transformer;
	}

	public static HtmlElement transform(File infile, File stylesheet, File outfile) throws Exception {
		HtmlElement htmlElement = null;
		if (infile == null) {
			LOG.debug("null input file");
		} else if (stylesheet == null) {
			LOG.debug("null stylesheet");
		} else if (outfile == null) {
			LOG.debug("null output file");
		} else {
			Transformer transformer = SHTMLTransformer.createTransformer(stylesheet);
		    File outputDir = outfile.getParentFile();
		    outputDir.mkdirs();
		    LOG.debug("transforming: "+infile+"; wrote HTML to "+outfile);
		    OutputStream baos = new ByteArrayOutputStream();
			transformer.transform(new StreamSource(infile),  new StreamResult(baos));
			String xmlString = baos.toString();
			FileUtils.write(new File("target/debug/transform.xml"), xmlString);
			Element xmlElement = XMLUtil.parseXML(xmlString);
			htmlElement = new HtmlFactory().parse(xmlElement);
			XMLUtil.debug(xmlElement, new FileOutputStream("target/firstpass.html"), 1);
		}
		return htmlElement;
	}

}
