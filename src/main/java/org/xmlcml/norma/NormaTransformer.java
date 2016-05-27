package org.xmlcml.norma;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletionService;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.xmlcml.cmine.files.CTree;
import org.xmlcml.graphics.svg.SVGElement;
import org.xmlcml.graphics.svg.SVGSVG;
import org.xmlcml.html.HtmlElement;
import org.xmlcml.html.HtmlFactory;
import org.xmlcml.html.HtmlTable;
import org.xmlcml.norma.image.ocr.HOCRReader;
import org.xmlcml.norma.image.ocr.NamedImage;
import org.xmlcml.norma.input.pdf.PDF2ImagesConverter;
import org.xmlcml.norma.input.pdf.PDF2TXTConverter;
import org.xmlcml.norma.input.tex.TEX2HTMLConverter;
import org.xmlcml.norma.table.CSVTransformer;
import org.xmlcml.norma.tagger.SectionTagger;
import org.xmlcml.norma.util.TransformerWrapper;
import org.xmlcml.svg2xml.pdf.PDFAnalyzer;
import org.xmlcml.xml.XMLUtil;

import nu.xom.Builder;
import nu.xom.Element;

public class NormaTransformer {

	/** manages types of transform in Norma.
	 * 
	 * @author pm286
	 *
	 */
	public enum Type {
		HTMLUNIT("htmlunit", null, CTree.FULLTEXT_HTML, null, CTree.FULLTEXT_XHTML),
		JSOUP("jsoup", null, CTree.FULLTEXT_HTML, null, CTree.FULLTEXT_XHTML),
		TIDY("tidy", null, CTree.FULLTEXT_HTML, null, CTree.FULLTEXT_XHTML),

		HOCR2SVG(    "hocr2svg",    "image/", CTree.HOCR,  "svg/",   CTree.HOCR_SVG),
		CSV2HTML(    "csv2html",    "table/", CTree.CSV,  "table/", CTree.HTML),
		CSV2TSV(     "csv2tsv",     "table/", CTree.CSV,  "table/", CTree.TSV),
		PDF2HTML(    "pdf2html",    null,     CTree.PDF,  null,     CTree.HTML),
		PDF2IMAGES(  "pdf2images",  null,     CTree.PDF,  "image/", CTree.PNG),
		PDF2SVG(     "pdf2svg",     null,     CTree.PDF,  "svg/",   CTree.SVG),
		PDF2TXT(     "pdf2txt",     null,     CTree.PDF,  null,     CTree.FULLTEXT_PDF_TXT),
		TEX2HTML(    "tex2html",    null,     CTree.TEX,  null,     CTree.HTML),
		TXT2HTML(    "txt2html",    null,     CTree.TXT,  null,     CTree.HTML),
		XML2HTML(    "xml2html",    null,     CTree.XML,  null,     CTree.SCHOLARLY_HTML);
	
		public String inputFilename;
		public String outputFilename;
		public String transform;
		public String inputDirName;
		public String outputDirName;
	
		private Type(String transform, String inputDir, String inputFilename, String outputDir, String outputFilename) {
			this.inputDirName = inputDir;
			this.outputDirName = outputDir;
			this.transform = transform;
			this.inputFilename = inputFilename;
			this.outputFilename = outputFilename;
		}
	
		public boolean matches(String transformTypeString) {
			return transform.equals(transformTypeString);
		}
	
		public static boolean contains(String token) {
			for (Type type : values()) {
				if (type.matches(token)) {
					return true;
				}
			}
			return false;
		}
		
		public File getDefaultInputFile(CTree cTree) {
			return new File(cTree.getDirectory(), inputFilename);
		}
		
		public File getDefaultOutputFile(CTree cTree) {
			return new File(cTree.getDirectory(), outputFilename);
		}
		
		public static Type createTransformType(String transformTypeString) {
			for (Type type : values()) {
				if (type.matches(transformTypeString)) {
					return type;
				}
			}
			if (transformTypeString.endsWith(CTree.DOT+CTree.XSL)) {
				return Type.XML2HTML;
			}
			return null;
		}
	}

	private static final Logger LOG = Logger.getLogger(NormaTransformer.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}
	private static final String TRANSFORM = "--transform";
	private static final String XSL = "--xsl";
	private static final String STYLESHEET_BY_NAME_XML = "/org/xmlcml/norma/pubstyle/stylesheetByName.xml";
	private static final String NAME = "name";

	private NormaArgProcessor normaArgProcessor;
	private Map<org.w3c.dom.Document, TransformerWrapper> transformerWrapperByStylesheetMap;

	String outputTxt;
	List<NamedImage> serialImageList;
	HtmlElement htmlElement;
	SVGElement svgElement;
	String tsvString;
	private CTree currentCTree;
	private Map<String, String> stylesheetByNameMap;
	
	private File inputFile;
	private String inputTxt; // text input // maybe move
	private File outputFile;	
	private Type type;
	private File outputDir;
	private File inputDir;
	private String transformTypeString;
	private org.w3c.dom.Document xslDocument;

	public NormaTransformer(NormaArgProcessor argProcessor) {
		this.normaArgProcessor = argProcessor;
		currentCTree = normaArgProcessor.getCurrentCMTree();
	}

	/** transforms currentCTree.
	 *
	 * output is either:
	 *   outputTxt (from PDF2TXT)
	 *   htmlElement (from PDF/TXT2HTML)
	 *   xmlStringList (from XSL transformation(s))
	 *
	 * @param option
	 */
	void runTransform(String transformTypeString) {
		this.transformTypeString = transformTypeString;
		type = Type.createTransformType(transformTypeString);
		if (type == null) {
			LOG.trace("trying : "+transformTypeString+" as stylesheet symbol");
			xslDocument = createW3CStylesheetDocument(transformTypeString);
			if (xslDocument != null) {
				type = Type.XML2HTML;
				LOG.trace("FOUND xsl");
			}
		}
		if (type == null) {
			LOG.debug("Cannot find transform: "+transformTypeString+"; allowed values: "+Arrays.asList(Type.values()));
			return;
		}
		try {
			parseOptionsAndCheckInputOutput();
		} catch (Exception e) {
			LOG.warn("Cannot create input/output "+e);
			return;
		}
		if (inputDir != null) {
			transformDirectory();
		} else if (inputFile != null) {
			transformSingleInput();
		}
		
	}

	private void transformDirectory() {
		if (type.inputDirName == null) {
			throw new RuntimeException("must have input directory for: "+type);
		}
		File[] files = inputDir.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name != null && name.endsWith(CTree.DOT + type.inputFilename);
			}
		});
		if (files != null) {
			outputDir.mkdirs();
			for (File file : files) {
				String basename = FilenameUtils.getBaseName(file.toString());
				inputFile = file;
				outputFile = new File(outputDir, basename+CTree.DOT+type.outputFilename);
				transformSingleInput();
				outputSpecifiedFormat();
			}
		}
		
	}

	private void parseOptionsAndCheckInputOutput() {
		String inputDirName = normaArgProcessor.getInputDirName();
		String outputDirName = normaArgProcessor.getOutputDirName();
		currentCTree = normaArgProcessor.getCurrentCMTree();
		outputFile = normaArgProcessor.getOutputFile();
		
		// create default directory name
		inputDirName = (type.inputDirName != null) ? type.inputDirName : inputDirName;
		outputDirName = (type.outputDirName != null) ? type.outputDirName : outputDirName;
		if (inputDirName != null) {
			parseInputDirectoryAndAddDefaults(inputDirName, outputDirName);
		} else {
			try {
				parseInputFile();
			} catch (Exception e) {
				LOG.warn("Cannot parse file: "+currentCTree.getDirectory());
			}
		} 
		if (inputFile == null && inputDir == null) {
			throw new RuntimeException("for transform: "+type+": --input or --inputDir must be given");
		}
		LOG.trace("inputFile: "+inputFile+"; outputFile: "+outputFile + "; inputDir: "+inputDirName+"; outputDir: "+outputDirName);
	}

	private void parseInputFile() {
		inputFile = normaArgProcessor.checkAndGetInputFile(currentCTree);
		if (inputFile == null) {
			inputFile = type.getDefaultInputFile(currentCTree);
			if (inputFile == null) {
				LOG.warn("cannot find defaultInputFile: "+currentCTree+" ? "+type);
			}
		}
		if (!inputFile.exists()) {
			throw new RuntimeException("Input file does not exist: "+inputFile);
		}
		if (outputFile == null) {
			outputFile = type.getDefaultOutputFile(currentCTree);
		}
	}

	private void parseInputDirectoryAndAddDefaults(String inputDirName, String outputDirName) {
		if (outputDirName == null) {
			throw new RuntimeException("Output Dir must be given");
		} else if (inputFile != null) {
			LOG.trace("inputFile must be not given with --inputDirName; found "+inputFile);
		}
		inputDir = new File(currentCTree.getDirectory(), inputDirName);
		if (!inputDir.exists()) {
			throw new RuntimeException("Input directory does not exist: "+inputDir);
		}
		outputDir = new File(currentCTree.getDirectory(), outputDirName);
		outputDir.mkdirs();
	}

	private void transformSingleInput() {
		// clear old outputs
		outputTxt = null;
		htmlElement = null;
		svgElement = null;
		tsvString = null;
		serialImageList = null;
		
		// check for NON-XSL transformation types
		if (false) {				
			//
		} else if (
			// alternate syntax
			Type.HTMLUNIT.equals(type) ||
			Type.JSOUP.equals(type) ||
			Type.TIDY.equals(type) 
			) {
			LOG.warn("Please use --html to clean HTML, and include *before* --transform");
			normaArgProcessor.runHtmlCleaner(type.transform);
		} else if (Type.HOCR2SVG.equals(type)) {
			svgElement = applyHOCR2SVGToInputFile();
		} else if (Type.CSV2HTML.equals(type)) {
			htmlElement = applyCSV2HtmlToInputFile();
		} else if (Type.CSV2TSV.equals(type)) {
			tsvString = applyCSV2TSVToInputFile();
		} else if (Type.PDF2HTML.equals(type)) {
			applyPDF2SVGToCurrentTree();
//				htmlElement = convertToHTML();
		} else if (Type.PDF2IMAGES.equals(type)) {
			serialImageList = applyPDF2ImagesToCTree();
		} else if (Type.PDF2SVG.equals(type)) {
			applyPDF2SVGToCurrentTree();
		} else if (Type.PDF2TXT.equals(type)) {
			outputTxt = applyPDF2TXTToCTree();
		} else if (Type.TEX2HTML.equals(type)) {
			String xmlString = convertTeXToHTML();
			createHtmlElement(xmlString);
		} else if (Type.TXT2HTML.equals(type)) {
			htmlElement = applyTXT2HTMLToCTree();
		} else if (normaArgProcessor.getCleanedHtmlElement() != null) {
			LOG.warn("Cannot process cleaned HTML element - use 2-step norma");
		} else if (Type.XML2HTML.equals(type)) {
			xslDocument = createW3CStylesheetDocument(transformTypeString);
			if (xslDocument == null) {
				throw new RuntimeException("null stylesheet for: "+transformTypeString);
			}
			String xmlString = applyXSLDocumentToCurrentCTree(xslDocument);
			if (xmlString == null) {
				LOG.trace("null content in: "+currentCTree.getDirectory());
			} else {
				createHtmlElement(xmlString);
			}
		} else {
			LOG.warn("Cannot interpret transform");
		}
	}

	private void createHtmlElement(String xmlString) {
		try {
			htmlElement = new HtmlFactory().parse(xmlString);
		} catch (Exception e) {
			throw new RuntimeException("cannot parse HTML string", e);
		}
	}

	private org.w3c.dom.Document createStylesheetFromArgs() {
		int tokenNum = Type.XML2HTML.equals(type) ? 0 : 1;
		String stylesheet = normaArgProcessor.transformTokenList.get(tokenNum);
		org.w3c.dom.Document xslDocument = createW3CStylesheetDocument(stylesheet);
		return xslDocument;
	}

	public static void listTransformOptions() {
		System.err.println("TRANSFORMATION OPTIONS");
		for (Type type : Type.values()) {
			System.err.println("  "+type.transform);
		}
		System.err.println();
	}
	
	private HtmlTable applyCSV2HtmlToInputFile() {
		HtmlTable table = null;
		CSVTransformer csvTransformer = new CSVTransformer();
		try {
			csvTransformer.readFile(inputFile);
			table = csvTransformer.createTable();
		} catch (IOException e) {
			throw new RuntimeException("Cannot convert TSV: ", e);
		}
		return table;
	}

	private String applyCSV2TSVToInputFile() {
		String tsvString = null;
		CSVTransformer csvTransformer = new CSVTransformer();
		try {
			csvTransformer.readFile(inputFile);
			tsvString = csvTransformer.createTSV();
		} catch (IOException e) {
			throw new RuntimeException("Cannot convert TSV: ", e);
		}
		return tsvString;
	}

	private SVGElement applyHOCR2SVGToInputFile() {
		HOCRReader hocrReader = new HOCRReader();
		try {
			hocrReader.readHOCR(new FileInputStream(inputFile));
		} catch (IOException e) {
			throw new RuntimeException("Cannot transform HOCR "+inputFile, e);
		}
		SVGSVG svgSvg = (SVGSVG) hocrReader.getOrCreateSVG();
		return svgSvg;
	}

	private String applyPDF2SVGToCurrentTree() {
		String txt = "NYI";
		PDFAnalyzer pdfAnalyzer = new PDFAnalyzer();
		try {
			pdfAnalyzer.setSkipOutput(false);
			pdfAnalyzer.analyzePDFFile(inputFile);
		} catch (Exception e) {
			throw new RuntimeException("Cannot transform PDF "+inputFile, e);
		}
		return txt;
	}

	private String applyPDF2TXTToCTree() {
		PDF2TXTConverter converter = new PDF2TXTConverter();
		String txt = null;
		try {
			txt = converter.readPDF(new FileInputStream(inputFile), true);
		} catch (IOException e) {
			throw new RuntimeException("Cannot transform PDF "+inputFile, e);
		}
		return txt;
	}

	private List<NamedImage> applyPDF2ImagesToCTree() {
		PDF2ImagesConverter converter = new PDF2ImagesConverter();
		List<NamedImage> namedImageList = null;
		try {
			namedImageList = converter.readPDF(new FileInputStream(inputFile), true);
		} catch (IOException e) {
			throw new RuntimeException("Cannot transform PDF "+inputFile, e);
		}
		return namedImageList;
	}

	private HtmlElement applyTXT2HTMLToCTree() {
		HtmlElement htmlElement = null;
		try {
			inputTxt = FileUtils.readFileToString(inputFile);
			htmlElement = convertToHTML();
		} catch (IOException e) {
			throw new RuntimeException("Cannot transform TXT "+inputFile, e);
		}
		return htmlElement;
	}

	/** requires LatexML to be present.
	 * 
	 * if fail, emits an error LOG and returns null
	 * 
	 * @param inputFile
	 * @return
	 */
	private String convertTeXToHTML() {
		TEX2HTMLConverter converter = new TEX2HTMLConverter();
		String result = null;
		try {
			result = converter.convertTeXToHTML(inputFile);
		} catch (InterruptedException e) {
			LOG.error("Failed to convert TeX to HTML"+e);
		} catch (IOException e) {
			LOG.error("Failed to convert TeX to HTML"+e);
		}
		return result;
	}

	private HtmlElement convertToHTML() {
		LOG.debug("convertToHTML NYI");
		HtmlElement element = null;
		return element;
	}

	private String applyXSLDocumentToCurrentCTree(org.w3c.dom.Document xslDocument) {
		String xmlString = null;
		try {
			xmlString = transform(xslDocument);
		} catch (IOException e) {
			LOG.error("Cannot transform "+normaArgProcessor.getCurrentCMTree().getDirectory()+"; "+e);
		}
		return xmlString;
	}

	private String transform(org.w3c.dom.Document xslDocument) throws IOException {
		TransformerWrapper transformerWrapper = getOrCreateTransformerWrapperForStylesheet(xslDocument);
		String xmlString = null;
		try {
			xmlString = transformerWrapper.transformToXML(inputFile);
		} catch (TransformerException e) {
			throw new RuntimeException("cannot transform "+inputFile, e);
		}
		return xmlString;
	}

	private TransformerWrapper getOrCreateTransformerWrapperForStylesheet(org.w3c.dom.Document xslDocument) {
		if (transformerWrapperByStylesheetMap == null) {
			transformerWrapperByStylesheetMap = new HashMap<org.w3c.dom.Document, TransformerWrapper>();
		}
		TransformerWrapper transformerWrapper = transformerWrapperByStylesheetMap.get(xslDocument);
//		Transformer javaxTransformer = transformerWrapperByStylesheetMap.get(xslDocument);
		if (transformerWrapper == null) {
			try {
				transformerWrapper = new TransformerWrapper(normaArgProcessor.isStandalone());
				if (xslDocument == null) {
					throw new RuntimeException("Null stylesheet");
				}
				Transformer javaxTransformer = transformerWrapper.createTransformer(xslDocument);
				transformerWrapperByStylesheetMap.put(xslDocument,  transformerWrapper);
			} catch (Exception e) {
				throw new RuntimeException("Cannot create transformer from xslDocument", e);
			}
		}
		return transformerWrapper;
	}

	// ============

	public String getOutputTxt() {
		return outputTxt;
	}

//	public String getXmlString() {
//		return xmlString;
//	}

	/** ordered list of title+image.
	 *
	 * title are of form "image<page>.<serial>.Img<img>"
	 * page is PD page number (base 1)
	 * serial is serial index of image (includes duplication)
	 * img is unique image serial
	 *
	 * @return
	 */
	public List<NamedImage> getImageList() {
		return serialImageList;
	}

	public HtmlElement getHtmlElement() {
		return htmlElement;
	}

	void outputSpecifiedFormat() {
		String output = null;
		if (outputFile != null) {
			String outname = outputFile.getAbsolutePath().toString();
			String treename = currentCTree.getDirectory().getAbsolutePath().toString();
			if (!outname.startsWith(treename)) {
				throw new RuntimeException("outname is not child of Ctree "+outname+" ; "+treename);
			}
			output = outname.substring(treename.length());
			// output is of form "table/table1.html"
		} else {
			output = normaArgProcessor.getOutput();
		}
		if (outputTxt != null) {
			currentCTree.writeFile(outputTxt, (output != null ? output : CTree.FULLTEXT_PDF_TXT));
		}
		if (htmlElement != null) {
			currentCTree.writeFile(htmlElement.toXML(), (output != null ? output : CTree.FULLTEXT_HTML));
		}
//		// this is not good
//		if (xmlString != null ) {
//			tagSections();
//			currentCTree.writeFile(xmlString, (output != null ? output : CTree.SCHOLARLY_HTML));
//		}
		if (svgElement != null && output != null) {
			currentCTree.writeFile(svgElement.toXML(), output);
		}
		if (serialImageList != null) {
			normaArgProcessor.writeImages();
		}
	}

	private org.w3c.dom.Document createW3CStylesheetDocument(String xslName) {
		DocumentBuilder db = createDocumentBuilder();
		String stylesheetResourceName = lookupStylesheetByName(xslName);
		org.w3c.dom.Document stylesheetDocument = readAsResource(db, stylesheetResourceName);
		// if fails, try as file
		if (stylesheetDocument == null) {
			try {
				stylesheetDocument = readAsStream(db, xslName, new FileInputStream(xslName));
			} catch (FileNotFoundException e) { /* hide exception*/}
		}
		if (stylesheetDocument == null) {
			LOG.trace("Cannot read stylesheet: "+xslName+"; "+stylesheetResourceName);
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
		} catch (Exception e) { 
			LOG.error("cannot parse stylesheet stream "+e);
		}
		return stylesheetDocument;
	}

	private String lookupStylesheetByName(String xslName) {
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
			LOG.trace("STYLESHEET"+stylesheetByNameMap);
		} catch (Exception e) {
			LOG.error("Cannot read "+STYLESHEET_BY_NAME_XML+"; "+e);
		}
	}

	private void tagSections() {
		LOG.debug("NYI");
		List<SectionTagger> sectionTaggers = normaArgProcessor.getSectionTaggers();
		for (SectionTagger sectionTagger : sectionTaggers) {
//			LOG.trace("section tagger:" + sectionTagger);
//			try {
//				Element xmlElement = XMLUtil.parseXML(xmlString);
//			} catch (RuntimeException e) {
//				throw new RuntimeException("failed to parse: "+xmlString.substring(0, Math.min(200, xmlString.length())), e);
//			}
		}
	}

}
