package org.xmlcml.norma;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;

import nu.xom.Builder;
import nu.xom.Element;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.xmlcml.args.ArgIterator;
import org.xmlcml.args.ArgumentOption;
import org.xmlcml.args.DefaultArgProcessor;
import org.xmlcml.args.StringPair;
import org.xmlcml.files.QuickscrapeDirectory;
import org.xmlcml.norma.util.SHTMLTransformer;
import org.xmlcml.xml.XMLUtil;

/** 
 * Processes commandline arguments.
 * for Norma
 * 
 * @author pm286
 */
public class NormaArgProcessor extends DefaultArgProcessor{
	
	public static final Logger LOG = Logger.getLogger(NormaArgProcessor.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}
	
	private static final String STYLESHEET_BY_NAME_XML = "/org/xmlcml/norma/pubstyle/stylesheetByName.xml";
	private static final String NAME = "name";
	private static final String XML = "xml";
	
	public final static String HELP_NORMA = "Norma help";
	
	private static String RESOURCE_NAME_TOP = "/org/xmlcml/norma";
	private static String ARGS_RESOURCE = RESOURCE_NAME_TOP+"/"+"args.xml";
	
	public final static String DOCTYPE = "!DOCTYPE";
		
	// options
	private List<StringPair> charPairList;
	private List<String> divList;
	private List<StringPair> namePairList;
	private Pubstyle pubstyle;
	private List<String> stripList;
	private String tidyName;
	private List<String> xslNameList;
	private Map<String, String> stylesheetByNameMap;
	private List<org.w3c.dom.Document> xslDocumentList;
	private Map<org.w3c.dom.Document, Transformer> transformerByStylesheetMap;

	public NormaArgProcessor() {
		super();
		this.readArgumentOptions(ARGS_RESOURCE);
	}

	public NormaArgProcessor(String[] args) {
		this();
		parseArgs(args);
	}

	// ============= METHODS =============
	
 	public void parseChars(ArgumentOption charOption, ArgIterator argIterator) {
		List<String> inputs = argIterator.createTokenListUpToNextMinus(charOption);
		charPairList = charOption.processArgs(inputs).getStringPairValues();
	}

	public void parseDivs(ArgumentOption divOption, ArgIterator argIterator) {
		divList = argIterator.createTokenListUpToNextMinus(divOption);
	}

	public void parseNames(ArgumentOption nameOption, ArgIterator argIterator) {
		List<String> inputs = argIterator.createTokenListUpToNextMinus(nameOption);
		namePairList = nameOption.processArgs(inputs).getStringPairValues();
	}
	
	public void parsePubstyle(ArgumentOption pubstyleOption, ArgIterator argIterator) {
		List<String> inputs = argIterator.createTokenListUpToNextMinus(pubstyleOption);
		if (inputs.size() == 0) {
			stripList = new ArrayList<String>();
			Pubstyle.help();
		} else {
			String name = pubstyleOption.processArgs(inputs).getStringValue();
			pubstyle = Pubstyle.getPubstyle(name);
		}
	}

	public void parseStrip(ArgumentOption stripOption, ArgIterator argIterator) {
		List<String> inputs = argIterator.createTokenListUpToNextMinus(stripOption);
		if (inputs.size() == 0) {
			stripList = new ArrayList<String>();
		} else {
			stripList = inputs;
		}
	}

	public void parseTidy(ArgumentOption tidyOption, ArgIterator argIterator) {
		List<String> inputs = argIterator.createTokenListUpToNextMinus(tidyOption);
		tidyName = tidyOption.processArgs(inputs).getStringValue();
	}

	public void parseXsl(ArgumentOption xslOption, ArgIterator argIterator) {
		List<String> inputs = argIterator.createTokenListUpToNextMinus(xslOption);
		xslNameList = xslOption.processArgs(inputs).getStringValues();
		xslDocumentList = new ArrayList<org.w3c.dom.Document>();
		for (String xslName : xslNameList) {
			org.w3c.dom.Document xslDocument = createW3CStylesheetDocument(xslName);
			xslDocumentList.add(xslDocument);
		}
	}

	public void printHelp() {
		System.err.println(
				"\n"
				+ "====NORMA====\n"
				+ "Norma converters raw files into scholarlyHTML, and adds tags to sections.\n"
				+ "Some of the conversion is dependent on publication type (--pubstyle) while some\n"
				+ "is constant for all documents. Where possible Norma guesses the input type, but can\n"
				+ "also be guided with the --extensions flag where the file/URL has no extension. "
				+ ""
				);
		super.printHelp();
	}
	
	// ==========================
	
	void normalizeAndTransform() {
		ensureXslDocumentList();
		if (xslDocumentList.size() > 0) {
			if (quickscrapeDirectoryList!= null) {
				for (QuickscrapeDirectory quickscrapeDirectory : quickscrapeDirectoryList) {
					transform(quickscrapeDirectory, xslDocumentList.get(0));
				}
			} else if (inputList != null) {
				for (String filename : inputList) {
					transform(new File(filename), xslDocumentList.get(0));
				}
			}
		}
	}

	private void transform(File file, org.w3c.dom.Document xslDocument) {
		Transformer transformer = cacheTransformer(xslDocument);
		try {
			String xmlString = SHTMLTransformer.transformToXML(file, transformer);
			LOG.error("output not bound in");
		} catch (TransformerException e) {
			throw new RuntimeException("cannot transform: ", e);
		}
	}

	private void transform(QuickscrapeDirectory quickscrapeDirectory, org.w3c.dom.Document xslDocument) {
		if (extensionList == null || extensionList.size() == 0) {
			LOG.debug("No extensions, so no input files");
		} else if (XML.equals(extensionList.get(0))) {
		    if (quickscrapeDirectory.hasFulltextXML()) {
				Transformer transformer = cacheTransformer(xslDocument);
				File fulltextXML = quickscrapeDirectory.getFulltextXML();
				try {
					String xmlString = SHTMLTransformer.transformToXML(fulltextXML, transformer);
					quickscrapeDirectory.writeFile(xmlString, QuickscrapeDirectory.SCHOLARLY_HTML);
				} catch (TransformerException e) {
					throw new RuntimeException("cannot transform: ", e);
				}
		    }
		} else {
			LOG.debug("only XML transformations supported");
		}
	}

	private Transformer cacheTransformer(org.w3c.dom.Document xslDocument) {
		if (transformerByStylesheetMap == null) {
			transformerByStylesheetMap = new HashMap<org.w3c.dom.Document, Transformer>();
		}
		Transformer transformer = transformerByStylesheetMap.get(xslDocument);
		if (transformer == null) {
			try {
				transformer = SHTMLTransformer.createTransformer(xslDocument);
				transformerByStylesheetMap.put(xslDocument,  transformer);
			} catch (Exception e) {
				throw new RuntimeException("Cannot create transformer from xslDocument", e);
			}
		}
		return transformer;
	}

	private void ensureXslDocumentList() {
		if (xslDocumentList == null) {
			xslDocumentList = new ArrayList<org.w3c.dom.Document>();
		}
	}

	org.w3c.dom.Document createW3CStylesheetDocument(String xslName) {
		DocumentBuilder db = createDocumentBuilder(); 
		String stylesheetResourceName = replaceCodeIfPossible(xslName);
		org.w3c.dom.Document stylesheetDocument = readAsResource(db, stylesheetResourceName);
		// if fails, try as file
		if (stylesheetDocument == null) {
			try {
				stylesheetDocument = readAsStream(db, xslName, new FileInputStream(xslName));
			} catch (FileNotFoundException e) { /* hide exception*/}
		}
		if (stylesheetDocument == null) {
			LOG.debug("Cannot read stylesheet: "+xslName+"; "+stylesheetResourceName);
		}
		return stylesheetDocument;
	}

	private DocumentBuilder createDocumentBuilder() {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = null;
		try {
			db = dbf.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			throw new RuntimeException("Serious BUG in JavaXML:", e);
		}
		return db;
	}

	private org.w3c.dom.Document readAsResource(DocumentBuilder db, String stylesheetResourceName) {
		InputStream is = this.getClass().getResourceAsStream(stylesheetResourceName);
		return readAsStream(db, stylesheetResourceName, is);
	}

	private org.w3c.dom.Document readAsStream(DocumentBuilder db, String xslName, InputStream is) {
		org.w3c.dom.Document stylesheetDocument = null;
		try {
			stylesheetDocument = db.parse(is);
		} catch (Exception e) { /* hide error */ }
		return stylesheetDocument;
	}

	private String replaceCodeIfPossible(String xslName) {
		createStylesheetByNameMap();
		String stylesheetResourceName = stylesheetByNameMap.get(xslName);
		stylesheetResourceName = (stylesheetResourceName == null) ? xslName : stylesheetResourceName;
		return stylesheetResourceName;
	}

	private void createStylesheetByNameMap() {
		stylesheetByNameMap = new HashMap<String, String>();
		try {
			nu.xom.Document stylesheetByNameDocument = new Builder().build(this.getClass().getResourceAsStream(STYLESHEET_BY_NAME_XML));
			List<Element> stylesheetList = XMLUtil.getQueryElements(stylesheetByNameDocument, "/stylesheetList/stylesheet");
			for (Element stylesheet : stylesheetList) {
				stylesheetByNameMap.put(stylesheet.getAttributeValue(NAME), stylesheet.getValue());
			}
			LOG.trace(stylesheetByNameMap);
		} catch (Exception e) {
			LOG.error("Cannot read "+STYLESHEET_BY_NAME_XML+"; "+e);
		}
	}



	// ==========================

	public Pubstyle getPubstyle() {
		return pubstyle;
	}

	public List<String> getStylesheetNameList() {
		return xslNameList;
	}

	public List<StringPair> getCharPairList() {
		return charPairList;
	}

	public List<String> getDivList() {
		return divList;
	}

	public List<StringPair> getNamePairList() {
		return namePairList;
	}

	public List<String> getStripList() {
		return stripList;
	}

	public String getTidyName() {
		return tidyName;
	}


}
