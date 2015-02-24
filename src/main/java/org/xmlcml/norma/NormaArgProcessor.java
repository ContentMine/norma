package org.xmlcml.norma;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.xmlcml.args.ArgIterator;
import org.xmlcml.args.ArgumentOption;
import org.xmlcml.args.DefaultArgProcessor;
import org.xmlcml.args.StringPair;
import org.xmlcml.files.QuickscrapeNorma;
import org.xmlcml.norma.util.TransformerWrapper;
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
	private Map<org.w3c.dom.Document, TransformerWrapper> transformerWrapperByStylesheetMap;
	private boolean standalone;

	public NormaArgProcessor() {
		super();
		this.readArgumentOptions(ARGS_RESOURCE);
	}

	public NormaArgProcessor(String[] args) {
		this();
		parseArgs(args);
	}

	// ============= METHODS =============
	
 	public void parseChars(ArgumentOption option, ArgIterator argIterator) {
		List<String> tokens = argIterator.createTokenListUpToNextMinus(option);
		charPairList = option.processArgs(tokens).getStringPairValues();
	}

	public void parseDivs(ArgumentOption option, ArgIterator argIterator) {
		divList = argIterator.createTokenListUpToNextMinus(option);
	}

	public void parseNames(ArgumentOption option, ArgIterator argIterator) {
		List<String> tokens = argIterator.createTokenListUpToNextMinus(option);
		namePairList = option.processArgs(tokens).getStringPairValues();
	}
	
	public void parsePubstyle(ArgumentOption option, ArgIterator argIterator) {
		List<String> tokens = argIterator.createTokenListUpToNextMinus(option);
		if (tokens.size() == 0) {
			stripList = new ArrayList<String>();
			Pubstyle.help();
		} else {
			String name = option.processArgs(tokens).getStringValue();
			pubstyle = Pubstyle.getPubstyle(name);
		}
	}

	public void parseStandalone(ArgumentOption option, ArgIterator argIterator) {
		List<String> tokens = argIterator.createTokenListUpToNextMinus(option);
		try {
			standalone = tokens.size() == 1 ? new Boolean(tokens.get(0)) : false;
		} catch (Exception e) {
			throw new RuntimeException("bad boolean: "+tokens.get(0));
		}
	}

	public void parseStrip(ArgumentOption option, ArgIterator argIterator) {
		List<String> tokens = argIterator.createTokenListUpToNextMinus(option);
		if (tokens.size() == 0) {
			stripList = new ArrayList<String>();
		} else {
			stripList = tokens;
		}
	}

	public void parseTidy(ArgumentOption option, ArgIterator argIterator) {
		List<String> tokens = argIterator.createTokenListUpToNextMinus(option);
		tidyName = option.processArgs(tokens).getStringValue();
	}

	public void parseXsl(ArgumentOption option, ArgIterator argIterator) {
		List<String> tokens = argIterator.createTokenListUpToNextMinus(option);
		xslNameList = option.processArgs(tokens).getStringValues();
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
		LOG.debug("NORMALIZE and TRANSFORM - needs moving");
		if (xslDocumentList.size() > 0) {
			if (quickscrapeNormaList!= null) {
				transformQuickscrapeNormaList();
			} else if (inputList != null) {
				createQuickscrapeNormaListFromInputList();
				transformQuickscrapeNormaList();
//				transformInputList();
			}
		}
	}

	private void createQuickscrapeNormaListFromInputList() {
		if (inputList != null) {
			getOrCreateOutputDirectory();
			// assume that files are not within a quickscrapeNorma
			for (String filename : inputList) {
				File file = new File(filename);
				if (file.isDirectory()) {
					LOG.error("should not have any directories in inputList: "+file);
					continue;
				}
				String name = FilenameUtils.getName(filename);
				if (QuickscrapeNorma.isReservedFilename(name)) {
					LOG.error(name+" is reserved for QuickscrapeNorma: (check that inputs are not already in a QN) "+file.getAbsolutePath());
					continue;
				}
				String qnFilename = QuickscrapeNorma.getQNFilename(name);
			}
		}
	}

	private void getOrCreateOutputDirectory() {
		if (output == null) {
			throw new RuntimeException("[output] is required to create quickscrape diectories");
		}
		File outputDir = new File(output);
		if (outputDir.exists()) {
			if (!outputDir.isDirectory()) {
				throw new RuntimeException("quickscrapeNormaRoot "+outputDir+" must be a directory");
			}
		} else {
			outputDir.mkdirs();
		}
	}

	private void transformInputList() {
		for (String filename : inputList) {
			try {
				String xmlString = transform(new File(filename), xslDocumentList.get(0));
				LOG.debug("XML "+xmlString );
			} catch (IOException e) {
				LOG.error("Cannot transform "+filename+"; "+e);
			}
		}
	}

	private void transformQuickscrapeNormaList() {
		for (QuickscrapeNorma quickscrapeNorma : quickscrapeNormaList) {
			try {
				transform(quickscrapeNorma, xslDocumentList.get(0));
			} catch (IOException e) {
				LOG.error("Cannot transform "+quickscrapeNorma+"; "+e);
			}
		}
	}

	private String transform(File file, org.w3c.dom.Document xslDocument) throws IOException {
		TransformerWrapper transformerWrapper = getOrCreateTransformerWrapperForStylesheet(xslDocument);
		String xmlString = null;
		try {
//			TransformerWrapper transformerWrapper = new TransformerWrapper();
			xmlString = transformerWrapper.transformToXML(file);
			LOG.error("output not bound in transform // FIXME");
		} catch (TransformerException e) {
			throw new RuntimeException("cannot transform: ", e);
		}
		return xmlString;
	}

	private void transform(QuickscrapeNorma quickscrapeNorma, org.w3c.dom.Document xslDocument) throws IOException {
	    if (quickscrapeNorma.hasFulltextXML()) {
			TransformerWrapper transformerWrapper = getOrCreateTransformerWrapperForStylesheet(xslDocument);
			String extension = (extensionList == null || extensionList.size() != 1) ? null : extensionList.get(0);
			String filetype = QuickscrapeNorma.RESERVED_FILES_BY_EXTENSION.get(extension);
			File fulltext = (filetype == null) ? null :quickscrapeNorma.getFulltextXML();
			if (fulltext == null) {
				LOG.error("nothing to transform for fulltext.xml "+extensionList);
			} else {
				try {
					String xmlString = transformerWrapper.transformToXML(fulltext);
					quickscrapeNorma.writeFile(xmlString, QuickscrapeNorma.SCHOLARLY_HTML);
				} catch (TransformerException e) {
					throw new RuntimeException("cannot transform: ", e);
				}
			}
	    }
	}

	private TransformerWrapper getOrCreateTransformerWrapperForStylesheet(org.w3c.dom.Document xslDocument) {
		if (transformerWrapperByStylesheetMap == null) {
			transformerWrapperByStylesheetMap = new HashMap<org.w3c.dom.Document, TransformerWrapper>();
		}
		TransformerWrapper transformerWrapper = transformerWrapperByStylesheetMap.get(xslDocument);
//		Transformer javaxTransformer = transformerWrapperByStylesheetMap.get(xslDocument);
		if (transformerWrapper == null) {
			try {
				transformerWrapper = new TransformerWrapper(standalone);
				Transformer javaxTransformer = transformerWrapper.createTransformer(xslDocument);
				transformerWrapperByStylesheetMap.put(xslDocument,  transformerWrapper);
			} catch (Exception e) {
				throw new RuntimeException("Cannot create transformer from xslDocument", e);
			}
		}
		return transformerWrapper;
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

	public boolean isStandalone() {
		return standalone;
	}


}
