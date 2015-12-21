package org.xmlcml.norma;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.xmlcml.cmine.args.ArgumentOption;
import org.xmlcml.cmine.files.CTree;
import org.xmlcml.graphics.svg.SVGElement;
import org.xmlcml.graphics.svg.SVGSVG;
import org.xmlcml.html.HtmlElement;
import org.xmlcml.norma.image.ocr.HOCRReader;
import org.xmlcml.norma.image.ocr.NamedImage;
import org.xmlcml.norma.input.pdf.PDF2ImagesConverter;
import org.xmlcml.norma.input.pdf.PDF2TXTConverter;
import org.xmlcml.norma.input.tex.TEX2HTMLConverter;
import org.xmlcml.norma.tagger.SectionTagger;
import org.xmlcml.norma.util.TransformerWrapper;
import org.xmlcml.svg2xml.pdf.PDFAnalyzer;
import org.xmlcml.xml.XMLUtil;

import nu.xom.Builder;
import nu.xom.Element;

public class NormaTransformer {

	private static final Logger LOG = Logger.getLogger(NormaTransformer.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}
	private static final String TRANSFORM = "--transform";
	private static final String XSL = "--xsl";
	private static final String STYLESHEET_BY_NAME_XML = "/org/xmlcml/norma/pubstyle/stylesheetByName.xml";
	private static final String NAME = "name";

	public static final String HOCR2SVG = "hocr2svg";
	public static final String PDF2HTML = "pdf2html";
	public static final String PDF2SVG = "pdf2svg";
	public static final String PDF2TXT = "pdf2txt";
	public static final String PDF2IMAGES = "pdf2images";
	public static final String TXT2HTML = "txt2html";
	public static final String TEX2HTML = "tex2html";
	
	public static final List<String> TRANSFORM_OPTIONS = Arrays.asList(new String[]{
			HOCR2SVG,
			PDF2HTML,
			PDF2SVG,
			PDF2TXT,
			PDF2IMAGES,
			TXT2HTML,
			TEX2HTML,
	});



	private NormaArgProcessor normaArgProcessor;
	private File inputFile;
	private Map<org.w3c.dom.Document, TransformerWrapper> transformerWrapperByStylesheetMap;

	String outputTxt;
	List<String> xmlStringList;
	List<NamedImage> serialImageList;
	HtmlElement htmlElement;
	SVGElement svgElement;
	private CTree currentCMDir;
	private List<String> transformList;
	private List<org.w3c.dom.Document> xslDocumentList;
	private Map<String, String> stylesheetByNameMap;
	private String inputTxt;

	public NormaTransformer(NormaArgProcessor argProcessor) {
		this.normaArgProcessor = argProcessor;
		currentCMDir = normaArgProcessor.getCurrentCMDir();
	}

	/** transforms currentCMDir.
	 *
	 * output is either:
	 *   outputTxt (from PDF2TXT)
	 *   htmlElement (from PDF/TXT2HTML)
	 *   xmlStringList (from XSL transformation(s))
	 *
	 * @param option
	 */
	void transform(ArgumentOption option) {
		currentCMDir = normaArgProcessor.getCurrentCMDir();
		LOG.trace("CM "+currentCMDir);
		inputFile = normaArgProcessor.checkAndGetInputFile(currentCMDir);
		LOG.trace("TRANSFORM "+option.getVerbose()+"; "+currentCMDir);
		outputTxt = null;
		htmlElement = null;
		svgElement = null;
		xmlStringList = null;
		serialImageList = null;
		if (option.getVerbose().equals(XSL) || option.getVerbose().equals(TRANSFORM)) {
			String optionValue = option.getStringValue();
			if (false) {				
			} else if (HOCR2SVG.equals(optionValue)) {
				svgElement = applyHOCR2SVGToInputFile();
			} else if (PDF2TXT.equals(optionValue)) {
				outputTxt = applyPDF2TXTToCMLDir();
			} else if (PDF2IMAGES.equals(optionValue)) {
				serialImageList = applyPDF2ImagesToCMLDir();
			} else if (TXT2HTML.equals(optionValue)) {
				htmlElement = applyTXT2HTMLToCMDir();
			} else if (PDF2SVG.equals(optionValue)) {
				applyPDF2SVGToCMLDir();
			} else if (PDF2HTML.equals(optionValue)) {
				applyPDF2SVGToCMLDir();
//				htmlElement = convertToHTML();
			} else if (TEX2HTML.equals(option.getStringValue())) {
				xmlStringList = convertTeXToHTML();
			} else {
				xmlStringList = applyXSLDocumentListToCurrentCMDir();
			}
			// http://grepcode.com/file/repo1.maven.org/maven2/org.apache.pdfbox/pdfbox/1.6.0/org/apache/pdfbox/util/PDFTextStripper.java#PDFTextStripper.getSortByPosition%28%29
			// GitHub, ASPERA, Galaxy, Asana
			
		}
	}

	public static void listTransformOptions() {
		System.err.println("TRANSFORMATION OPTIONS");
		for (String transform : NormaTransformer.TRANSFORM_OPTIONS) {
			System.err.println("  "+transform);
		}
		System.err.println();
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

	private String applyPDF2SVGToCMLDir() {
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

	private String applyPDF2TXTToCMLDir() {
		PDF2TXTConverter converter = new PDF2TXTConverter();
		String txt = null;
		try {
			txt = converter.readPDF(new FileInputStream(inputFile), true);
		} catch (IOException e) {
			throw new RuntimeException("Cannot transform PDF "+inputFile, e);
		}
		return txt;
	}

	private List<NamedImage> applyPDF2ImagesToCMLDir() {
		PDF2ImagesConverter converter = new PDF2ImagesConverter();
		List<NamedImage> namedImageList = null;
		try {
			namedImageList = converter.readPDF(new FileInputStream(inputFile), true);
		} catch (IOException e) {
			throw new RuntimeException("Cannot transform PDF "+inputFile, e);
		}
		return namedImageList;
	}

	private HtmlElement applyTXT2HTMLToCMDir() {
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
	private List<String> convertTeXToHTML() {
		TEX2HTMLConverter converter = new TEX2HTMLConverter();
		try {
			List<String> result = new ArrayList<String>();
			result.add(converter.convertTeXToHTML(inputFile));
			return result;
		} catch (InterruptedException e) {
			LOG.error("Failed to convert TeX to HTML"+e);
		} catch (IOException e) {
			LOG.error("Failed to convert TeX to HTML"+e);
		}
		return null;
	}

	private HtmlElement convertToHTML() {
//		System.out.println(inputTxt);
		LOG.debug("convertToHTML NYI");
		HtmlElement element = null;
		return element;
	}

	private List<String> applyXSLDocumentListToCurrentCMDir() {
		List<org.w3c.dom.Document> xslDocumentList = this.getXslDocumentList();
		xmlStringList = new ArrayList<String>();
		for (org.w3c.dom.Document xslDocument : xslDocumentList) {
			try {
				String xmlString = transform(xslDocument);
//				try {
//					FileUtils.write(new File("target/junk2.xml"), xmlString, Charset.forName("UTF-8"));
//					System.exit(1);
//				} catch (IOException e) {
//					System.err.println(e);
//				}
				xmlStringList.add(xmlString);
			} catch (IOException e) {
				LOG.error("Cannot transform "+normaArgProcessor.getCurrentCMDir()+"; "+e);
			}
		}
		return xmlStringList;
	}

	private String transform(org.w3c.dom.Document xslDocument) throws IOException {
		TransformerWrapper transformerWrapper = getOrCreateTransformerWrapperForStylesheet(xslDocument);
		String xmlString = null;
		try {
			xmlString = transformerWrapper.transformToXML(inputFile);
		} catch (TransformerException e) {
			throw new RuntimeException("cannot transform: ", e);
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
				Transformer javaxTransformer = transformerWrapper.createTransformer(xslDocument);
				transformerWrapperByStylesheetMap.put(xslDocument,  transformerWrapper);
			} catch (Exception e) {
				throw new RuntimeException("Cannot create transformer from xslDocument", e);
			}
		}
		return transformerWrapper;
	}

	// from Input wrapper, maybe obsolete

	// FIXME
	/** this is not called and probably should be*/
	private HtmlElement transform(NormaArgProcessor argProcessor) throws Exception {
//		this. = argProcessor;
//		this.pubstyle = argProcessor.getPubstyle();
//		findInputFormat();
//		try {
//			normalizeToXHTML(); // creates htmlElement
//		} catch (Exception e) {
//			LOG.debug("Cannot convert file/s " + e);
//			return null;
//		}
//		XMLUtil.debug(htmlElement, new FileOutputStream("target/norma.html"), 1);
//		if (pubstyle == null) {
//			this.pubstyle = Pubstyle.deducePubstyle(htmlElement);
//		}
//		if (pubstyle != null) {
//			pubstyle.applyTagger(getInputFormat(), htmlElement);
//		} else {
//			LOG.debug("No pubstyle/s declared or deduced");
//		}
//		return htmlElement;
		return null;
	}


//	/** maybe move to Pubstyle later.
//	 *
//	 * @param pubstyle
//	 */
//	private void normalizeToXHTML() throws Exception {
//		ensurePubstyle();
//		try {
//			if (InputFormat.PDF.equals(getInputFormat())) {
//				PDF2XHTMLConverter converter = new PDF2XHTMLConverter();
//				htmlElement = converter.readAndConvertToXHTML(new File(inputName));
//			} else if (InputFormat.SVG.equals(getInputFormat())) {
//				LOG.error("cannot turn SVG into XHTML yet");
//			} else if (InputFormat.XML.equals(getInputFormat())) {
//				transformXmlToHTML();
//			} else if (InputFormat.HTML.equals(getInputFormat())) {
//				htmlElement = pubstyle.readRawHtmlAndCreateWellFormed(inputFormat, inputName);
//			} else if (InputFormat.XHTML.equals(getInputFormat())) {
//				LOG.debug("using XHTML; not yet  implemented");
//			} else {
//				LOG.error("no processor found to convert "+inputName+" ("+getInputFormat()+") into XHTML yet");
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new RuntimeException("cannot convert "+getInputFormat()+" "+inputName, e);
//		}
//	}


	private void transformXmlToHTML() throws Exception {
//		ensureXslDocumentList();
//		String outputFile = normaArgProcessor.getOutput();
//		if (inputName == null) {
//			throw new RuntimeException("No input file given");
//		} else if (stylesheetDocumentList.size() == 0) {
//			throw new RuntimeException("No stylesheet file/s given");
//		} else if (outputFile == null) {
//			throw new RuntimeException("No output file given");
//		}
//		if (stylesheetDocumentList.size() >1) {
//			throw new RuntimeException("Only one stylesheet allowed at present");
//		}
//		TransformerWrapper transformerWrapper = new TransformerWrapper(normaArgProcessor.isStandalone());
//		htmlElement = transformerWrapper.transform(new File(inputName), stylesheetDocumentList.get(0), outputFile);
	}

//	private void ensureXslDocumentList() {
//		if (stylesheetDocumentList == null) {
//			List<String> xslNameList = normaArgProcessor.getStylesheetNameList();
//			stylesheetDocumentList = new ArrayList<org.w3c.dom.Document>();
//			for (String xslName : xslNameList) {
//				org.w3c.dom.Document stylesheetDocument = argProcessor.createW3CStylesheetDocument(xslName);
//				stylesheetDocumentList.add(stylesheetDocument);
//			}
//		}
//	}

	// ============




	public String getOutputTxt() {
		return outputTxt;
	}

	public List<String> getXmlStringList() {
		return xmlStringList;
	}

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
		String output = normaArgProcessor.getOutput();
		if (outputTxt != null) {
			currentCMDir.writeFile(outputTxt, (output != null ? output : CTree.FULLTEXT_PDF_TXT));
		}
		if (htmlElement != null) {
			currentCMDir.writeFile(htmlElement.toXML(), (output != null ? output : CTree.FULLTEXT_HTML));
		}
		if (xmlStringList != null && xmlStringList.size() > 0) {
			tagSections();
			currentCMDir.writeFile(xmlStringList.get(0), (output != null ? output : CTree.SCHOLARLY_HTML));
		}
		if (svgElement != null && output != null) {
			currentCMDir.writeFile(svgElement.toXML(), output);
		}
		if (serialImageList != null) {
			normaArgProcessor.writeImages();
		}
	}

	void parseTransform(NormaArgProcessor normaArgProcessor, List<String> tokenList) {
		xslDocumentList = new ArrayList<org.w3c.dom.Document>();
		transformList = new ArrayList<String>();
		// at present we allow only one option
		if (tokenList.size() == 0) {
			NormaTransformer.listTransformOptions();
		} else if (tokenList.size() > 1) {
			NormaArgProcessor.LOG.error("only 0/1 args allowed for transform");
		} else {
			String token = tokenList.get(0);
			org.w3c.dom.Document xslDocument = createW3CStylesheetDocument(token);
			if (xslDocument != null) {
				xslDocumentList.add(xslDocument);
			} else if (NormaTransformer.TRANSFORM_OPTIONS.contains(token)) {
				transformList.add(token);
			} else {
				NormaArgProcessor.LOG.error("Cannot process transform token: "+token+"; allowed values: ");
				NormaTransformer.listTransformOptions();
			}
		}
		if (transformList.size() == 0) {
			LOG.error("no transforms given/parsed");
		}
	}

	private void ensureXslDocumentList() {
		if (xslDocumentList == null) {
			xslDocumentList = new ArrayList<org.w3c.dom.Document>();
		}
	}
	

	private org.w3c.dom.Document createW3CStylesheetDocument(String xslName) {
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
			// this could happen when we use "pdf2txt" , etc
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

	public List<Document> getXslDocumentList() {
		ensureXslDocumentList();
		return xslDocumentList;
	}
	
	private void tagSections() {
		List<SectionTagger> sectionTaggers = normaArgProcessor.getSectionTaggers();
		for (SectionTagger sectionTagger : sectionTaggers) {
			LOG.trace("section tagger:" + sectionTagger);
			for (String xmlString : xmlStringList) {
				Element xmlElement = XMLUtil.parseXML(xmlString);
			}
		}
	}

}
