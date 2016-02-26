package org.xmlcml.norma.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import nu.xom.Document;
import nu.xom.Element;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.xmlcml.cmine.files.CTree;
import org.xmlcml.html.HtmlElement;
import org.xmlcml.html.HtmlFactory;
import org.xmlcml.xml.XMLUtil;

public class TransformerWrapper {
	
	
	private static final Logger LOG = Logger.getLogger(TransformerWrapper.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}

	private static final String NET_SF_SAXON_TRANSFORMER_FACTORY_IMPL = "net.sf.saxon.TransformerFactoryImpl";
	private static final String JAVAX_XML_TRANSFORM_TRANSFORMER_FACTORY = "javax.xml.transform.TransformerFactory";
	private static final String DOCTYPE = "<!DOCTYPE";
	
	private boolean standalone;
	private Transformer javaxTransformer;
	
	public TransformerWrapper() {
		setDefaults();
	}
	
	private void setDefaults() {
		setStandalone(true);
	}

	public TransformerWrapper(boolean standalone) {
		this();
		setStandalone(standalone);
	}
	
	public Transformer createTransformer(File stylesheet) throws Exception {
		return this.createTransformer(new FileInputStream(stylesheet));
	}

	public Transformer createTransformer(org.w3c.dom.Document xslStylesheet) throws Exception {
		System.setProperty(JAVAX_XML_TRANSFORM_TRANSFORMER_FACTORY,
	            NET_SF_SAXON_TRANSFORMER_FACTORY_IMPL);
	    TransformerFactory tfactory = TransformerFactory.newInstance();
	    DOMSource domSource = new DOMSource(xslStylesheet);
	    javaxTransformer = tfactory.newTransformer(domSource);
		return javaxTransformer;
	}

	private Transformer createTransformer(InputStream is) throws Exception {
		System.setProperty(JAVAX_XML_TRANSFORM_TRANSFORMER_FACTORY,
	            NET_SF_SAXON_TRANSFORMER_FACTORY_IMPL);
	    TransformerFactory tfactory = TransformerFactory.newInstance();
	    javaxTransformer = tfactory.newTransformer(new StreamSource(is));
		return javaxTransformer;
	}

	public HtmlElement transform(File infile, File stylesheet, File outfile) throws Exception {
		javaxTransformer = this.createTransformer(new FileInputStream(stylesheet));
		return this.transform(infile, outfile);
	}
	
	public HtmlElement transform(File infile, File outfile) throws Exception {
		HtmlElement htmlElement = null;
		if (infile == null) {
			throw new RuntimeException("null input file");
		}
		if (outfile == null) {
			throw new RuntimeException("null output file");
		}
		
	    String xmlString = transformToXML(infile);
	    // debug output
		FileUtils.write(new File("target/debug/transform.xml"), xmlString, Charset.forName("UTF-8"));
		Element xmlElement = XMLUtil.parseXML(xmlString);
		htmlElement = new HtmlFactory().parse(xmlElement);
		XMLUtil.debug(xmlElement, new FileOutputStream("target/firstpass.html"), 1);
		
		return htmlElement;
	}

	public String transformToXML(File infile) throws IOException, TransformerException {
		FileInputStream fis = new FileInputStream(infile);
		String ss = transformToXML(fis);
		return ss;
	}

	public String transformToXML(InputStream inputStream) throws TransformerException {
		// remove doctype
		if (standalone) {
			try {
				inputStream = removeDoctype(inputStream);
				IOUtils.closeQuietly(inputStream);
			} catch (IOException e) {
				throw new RuntimeException("Unepected Exception while removing "+DOCTYPE+" ... >");
			}
		}
		String ss = transformToXML(new StreamSource(inputStream));
		return ss;

	}

	/** removes <!DOCTYPE ... > from inputStream.
	 * 
	 * creates fresh inputStream.
	 * 
	 * @param inputStream
	 * @return
	 * @throws IOException
	 */
	private InputStream removeDoctype(InputStream inputStream) throws IOException {
		String inputString = IOUtils.toString(inputStream);
		IOUtils.closeQuietly(inputStream);
		int idx = inputString.indexOf(DOCTYPE);
		if (idx != -1) {
			String start = inputString.substring(0, idx);
			String last = inputString.substring(idx);
			int idx1 = last.indexOf(">");
			if (idx1 == -1) {
				throw new RuntimeException(DOCTYPE+" not balanced by >");
			}
			inputString = start + last.substring(idx1 + 1);
		}
		return IOUtils.toInputStream(inputString);
	}

	private String transformToXML(StreamSource streamSource) throws TransformerException {
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
		javaxTransformer.transform(streamSource,  new StreamResult(baos));
		String ss = baos.toString();
		return ss;
	}

	public HtmlElement transform(CTree qd, Document xslDocument) throws Exception {
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
//		FileUtils.write(new File("target/debug/transform.xml"), xmlString, Charset.forName("UTF-8"));
//		Element xmlElement = XMLUtil.parseXML(xmlString);
//		htmlElement = new HtmlFactory().parse(xmlElement);
//		XMLUtil.debug(xmlElement, new FileOutputStream("target/firstpass.html"), 1);
		
		return htmlElement;
	}

	public HtmlElement transform(File inputFile, org.w3c.dom.Document stylesheetDocument, String outputFile) throws Exception {
		javaxTransformer = createTransformer(stylesheetDocument);
		return transform(inputFile, new File(outputFile));
	}

	public boolean isStandalone() {
		return standalone;
	}

	public void setStandalone(boolean standalone) {
		this.standalone = standalone;
	}

}
