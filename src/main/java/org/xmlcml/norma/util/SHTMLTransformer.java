package org.xmlcml.norma.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import nu.xom.Document;
import nu.xom.Element;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.xmlcml.files.QuickscrapeDirectory;
import org.xmlcml.html.HtmlElement;
import org.xmlcml.html.HtmlFactory;
import org.xmlcml.xml.XMLUtil;

public class SHTMLTransformer {
	
	
	private static final Logger LOG = Logger.getLogger(SHTMLTransformer.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}

	public static  Transformer createTransformer(File stylesheet) throws Exception {
		return SHTMLTransformer.createTransformer(new FileInputStream(stylesheet));
	}

	public static  Transformer createTransformer(org.w3c.dom.Document xslStylesheet) throws Exception {
		System.setProperty("javax.xml.transform.TransformerFactory",
	            "net.sf.saxon.TransformerFactoryImpl");
	    TransformerFactory tfactory = TransformerFactory.newInstance();
	    Transformer transformer = tfactory.newTransformer(new DOMSource(xslStylesheet));
		return transformer;
	}

	private static  Transformer createTransformer(InputStream is) throws Exception {
		System.setProperty("javax.xml.transform.TransformerFactory",
	            "net.sf.saxon.TransformerFactoryImpl");
	    TransformerFactory tfactory = TransformerFactory.newInstance();
	    Transformer transformer = tfactory.newTransformer(new StreamSource(is));
		return transformer;
	}

	public static HtmlElement transform(File infile, File stylesheet, File outfile) throws Exception {
		Transformer transformer = SHTMLTransformer.createTransformer(new FileInputStream(stylesheet));
		return SHTMLTransformer.transform(infile, transformer, outfile);
	}
	
	public static HtmlElement transform(File infile, Transformer transformer, File outfile) throws Exception {
		HtmlElement htmlElement = null;
		if (infile == null) {
			throw new RuntimeException("null input file");
		}
		if (transformer == null) {
			throw new RuntimeException("null stylesheet");
		}
		if (outfile == null) {
			throw new RuntimeException("null output file");
		}
		
//	    File outputDir = outfile.getParentFile();
//	    outputDir.mkdirs();
//	    LOG.debug("transforming: "+infile+"; wrote HTML to "+outfile);
	    String xmlString = transformToXML(infile, transformer);
		FileUtils.write(new File("target/debug/transform.xml"), xmlString);
		Element xmlElement = XMLUtil.parseXML(xmlString);
		htmlElement = new HtmlFactory().parse(xmlElement);
		XMLUtil.debug(xmlElement, new FileOutputStream("target/firstpass.html"), 1);
		
		return htmlElement;
	}

	public static String transformToXML(File infile, Transformer transformer) throws TransformerException {
		return transformToXML(new StreamSource(infile), transformer);
	}

	public static String transformToXML(InputStream inputStream, Transformer transformer) throws TransformerException {
		return transformToXML(new StreamSource(inputStream), transformer);
	}

	private static String transformToXML(StreamSource streamSource, Transformer transformer) throws TransformerException {
// use this later		
		/**
SAXParserFactory factory = SAXParserFactory.newInstance();
factory.setValidating(false);
factory.setNamespaceAware(true);

SAXParser parser = factory.newSAXParser();

XMLReader reader = parser.getXMLReader();
reader.setErrorHandler(new SimpleErrorHandler());

Builder builder = new Builder(reader);
builder.build("contacts.xml");

OR

XMLReader xmlReader = XMLReaderFactory.createXMLReader();
xmlReader.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
Builder builder = new Builder(xmlReader);
nu.xom.Document doc = builder.build(fXmlFile);
		 */
		OutputStream baos = new ByteArrayOutputStream();
		transformer.transform(streamSource,  new StreamResult(baos));
		return baos.toString();
	}

	public static HtmlElement transform(QuickscrapeDirectory qd, Document xslDocument) throws Exception {
		HtmlElement htmlElement = null;
		if (qd == null) {
			throw new RuntimeException("null QD");
		}
		if (xslDocument == null) {
			throw new RuntimeException("null stylesheet");
		}
		
//		Transformer transformer = SHTMLTransformer.createTransformer(stylesheet);
//	    OutputStream baos = new ByteArrayOutputStream();
//		transformer.transform(new StreamSource(infile),  new StreamResult(baos));
//		String xmlString = baos.toString();
//		FileUtils.write(new File("target/debug/transform.xml"), xmlString);
//		Element xmlElement = XMLUtil.parseXML(xmlString);
//		htmlElement = new HtmlFactory().parse(xmlElement);
//		XMLUtil.debug(xmlElement, new FileOutputStream("target/firstpass.html"), 1);
		
		return htmlElement;
	}

}
