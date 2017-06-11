package org.xmlcml.norma;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.xmlcml.cproject.files.CTree;
import org.xmlcml.cproject.util.RectangularTable;
import org.xmlcml.cproject.util.Utils;
import org.xmlcml.graphics.svg.SVGElement;
import org.xmlcml.graphics.svg.SVGSVG;
import org.xmlcml.graphics.svg.plot.PlotBox;
import org.xmlcml.html.HtmlElement;
import org.xmlcml.html.HtmlFactory;
import org.xmlcml.html.HtmlTable;
import org.xmlcml.norma.image.ocr.HOCRReader;
import org.xmlcml.norma.image.ocr.NamedImage;
import org.xmlcml.norma.input.pdf.PDF2ImagesConverter;
import org.xmlcml.norma.input.pdf.PDF2TEIConverter;
import org.xmlcml.norma.input.pdf.PDF2TXTConverter;
import org.xmlcml.norma.input.tex.TEX2HTMLConverter;
import org.xmlcml.norma.table.CSVTransformer;
import org.xmlcml.norma.table.SVGTable2HTMLConverter;
import org.xmlcml.norma.tagger.SectionTaggerX;
import org.xmlcml.norma.util.TransformerWrapper;
import org.xmlcml.svg2xml.pdf.PDFAnalyzer;
import org.xmlcml.svg2xml.pdf.PDFAnalyzerIO;
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
		// HTMLCleaner

		HOCR2SVG(      "hocr2svg",      "image/", CTree.HOCR,  "svg/",   CTree.HOCR_SVG),
		CSV2HTML(      "csv2html",      "table/", CTree.CSV,  "table/", CTree.FULLTEXT_HTML),
		CSV2TSV(       "csv2tsv",       "table/", CTree.CSV,  "table/", CTree.TSV),
		PDF2HTML(      "pdf2html",      null,     CTree.FULLTEXT_PDF,  null,     CTree.FULLTEXT_HTML),
		PDF2IMAGES(    "pdf2images",    null,     CTree.FULLTEXT_PDF,  "image/", CTree.FULLTEXT_PDF_PNG),
		PDF2SVG(       "pdf2svg",       null,     CTree.FULLTEXT_PDF,  "svg/",   CTree.FULLTEXT_PDF_SVG),
		PDF2TEI(       "pdf2tei",       null,     CTree.FULLTEXT_PDF,  null,     CTree.FULLTEXT_TEI_XML),
		PDF2TXT(       "pdf2txt",       null,     CTree.FULLTEXT_PDF,  null,     CTree.FULLTEXT_PDF_TXT),
		SCATTER2CSV (  "scatter2csv",   null,     CTree.SVG,  null,     CTree.SVG+"."+"csv"),
		SVGTABLE2CSV ( "svgtable2csv",  null,     CTree.SVG,  null,     CTree.SVG+"."+"csv"),
		SVGTABLE2HTML( "svgtable2html", null,     CTree.SVG,  null,     CTree.SVG+"."+"html"),
		TEI2HTML(      "tei2html",      null,     CTree.FULLTEXT_TEI_XML,  null, CTree.FULLTEXT_HTML),
		TEX2HTML(      "tex2html",      null,     CTree.FULLTEXT_TEX,  null,     CTree.FULLTEXT_HTML),
		TXT2HTML(      "txt2html",      null,     CTree.FULLTEXT_TXT,  null,     CTree.FULLTEXT_HTML),
		XML2HTML(      "xml2html",      null,     CTree.FULLTEXT_XML,  null,     CTree.SCHOLARLY_HTML);
	
		public String inputSuffix;
		public String outputSuffix;
		public String transform;
		public String inputDirName;
		public String outputDirName;
	
		private Type(String transform, String inputDir, String inputSuffix, String outputDir, String outputSuffix) {
			this.inputDirName = inputDir;
			this.outputDirName = outputDir;
			this.transform = transform;
			this.inputSuffix = inputSuffix;
			this.outputSuffix = outputSuffix;
		}
	
		public boolean matches(String transformTypeString) {
			return transform.equalsIgnoreCase(transformTypeString);
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
			return new File(cTree.getDirectory(), inputSuffix);
		}
		
		public File getDefaultOutputFile(CTree cTree) {
			File file =  new File(cTree.getDirectory(), outputSuffix);
			return file;
		}
		
		public static Type createTransformType(String transformTypeString) {
			for (Type type : values()) {
				if (type.matches(transformTypeString)) {
					return type;
				}
			}
			if (transformTypeString.endsWith(CTree.DOT+CTree.XSL)) {
				LOG.trace("transform type: "+Type.XML2HTML);
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
	SVGElement svgAnnotElement;
	File teiFile;
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
	private IOFileFilter ioFileFilter;
	private RectangularTable rectangularTable;

	public NormaTransformer(NormaArgProcessor argProcessor) {
		this.normaArgProcessor = argProcessor;
		currentCTree = normaArgProcessor.getCurrentCMTree();
		LOG.trace("current CTREE "+currentCTree);
	}

	public void clearVariables() {
		inputTxt = null;
		inputFile = null;
		inputDir = null;
		outputTxt = null;
		outputFile = null;
		outputDir = null;
		htmlElement = null;
		svgElement = null;
		transformTypeString = null;
		xslDocument = null;
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
			e.printStackTrace();
			return;
		}
		if (inputDir != null) {
			transformDirectory();
		} else if (normaArgProcessor.getIOFileFilter() != null) {
			transformFilteredFiles();
		} else {
			transformSingleInputFile();
		}
		
	}

	private void transformSingleInputFile() {
		if (inputFile != null && inputFile.exists()) {
			try {
				transformSingleInput(inputFile);
			} catch (RuntimeException e) {
				if (Level.ERROR.equals(normaArgProcessor.getExceptionLevel())) {
					throw e;
				} else if (Level.INFO.equals(normaArgProcessor.getExceptionLevel())) {
					System.out.print("?@?");
				} else {
					e.printStackTrace();
					LOG.error("BAD TRANSFORM ("+e.toString()+") "+inputFile);
				}
			}
		}
	}

	private void transformFilteredFiles() {
		File dir = currentCTree.getDirectory();
		IOFileFilter ioFileFilter1 = ioFileFilter;
		File[] files = Utils.getFilesWithFilter(dir, ioFileFilter1);
		transformFiles(files);
	}

	private void transformDirectory() {
		if (type.inputDirName == null) {
			throw new RuntimeException("must have input directory for: "+type);
		}
		File[] files = inputDir.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name != null && name.endsWith(CTree.DOT + type.inputSuffix);
			}
		});
		transformFiles(files);
		
	}

	private void transformFiles(File[] files) {
		if (files != null) {
			if (outputDir != null) {
				outputDir.mkdirs();
			}
			for (File file : files) {
				String basename = FilenameUtils.getBaseName(file.toString());
				setInputFile(file);
				String filename = basename+CTree.DOT+type.outputSuffix;
				if (outputDir == null) {
					outputFile = new File(file.getParentFile(), filename);
				} else {
					outputFile = new File(outputDir, filename);
				}
				LOG.trace("writing to "+outputFile);
				transformSingleInput(file);
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
		ioFileFilter = normaArgProcessor.getIOFileFilter();
		if (inputDirName != null) {
			parseInputDirectoryAndAddDefaults(inputDirName, outputDirName);
		} else if (ioFileFilter != null) {
			// not sure what we should do here. Ignore at present
		} else {
			try {
				parseInputFile();
			} catch (Exception e) {
				LOG.warn("Cannot parse file: "+currentCTree.getDirectory()+"; "+e);
			}
		} 
		LOG.trace("inputFile: "+inputFile+"; outputFile: "+outputFile + "; fileFilter: " + ioFileFilter + "; inputDir: "+inputDirName+"; outputDir: "+outputDirName);
	}

	private void parseInputFile() {
		setInputFile(normaArgProcessor.checkAndGetInputFile(currentCTree));
		if (inputFile == null) {
			setInputFile(type.getDefaultInputFile(currentCTree));
			if (inputFile == null) {
				LOG.warn("cannot find inputFile: "+currentCTree.getDirectory()+" ? "+type);
			}
			return;
		}
		if (!inputFile.exists()) {
			warnNoFile();
			setInputFile(null);
//			throw new RuntimeException("Input file does not exist: "+inputFile);
		} else {
			if (outputFile == null) {
				outputFile = type.getDefaultOutputFile(currentCTree);
			}
		}
		if (outputDir == null) {
			if (normaArgProcessor.getOutputDirName() != null) {
				outputDir = new File(normaArgProcessor.getOutputDirName());
			} else {
				outputDir = inputDir;
			}

		}
	}

	private void warnNoFile() {
		System.out.print("!");
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

	private void transformSingleInput(File inputFile) {
		try {
			transformSingleInput0(inputFile);
		} catch (RuntimeException e) {
			e.printStackTrace();
			if (Level.ERROR.equals(normaArgProcessor.getExceptionLevel())) {
				throw e;
			} else if (Level.INFO.equals(normaArgProcessor.getExceptionLevel())) {
				System.out.print("?@?");
			} else {
				LOG.error("BAD TRANSFORM ("+e.getMessage()+") "+inputFile);
			}
		}

	}

	private void transformSingleInput0(File inputFile) {
		// clear old outputs
		outputTxt = null;
		htmlElement = null;
		svgElement = null;
		tsvString = null;
		serialImageList = null;
		teiFile = null;
		
		if (inputFile == null || !inputFile.exists()) {
			return;
		}
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
			svgElement = applyHOCR2SVGToInputFile(inputFile);
		} else if (Type.CSV2HTML.equals(type)) {
			htmlElement = applyCSV2HtmlToInputFile(inputFile);
		} else if (Type.CSV2TSV.equals(type)) {
			tsvString = applyCSV2TSVToInputFile(inputFile);
		} else if (Type.PDF2HTML.equals(type)) {
			applyPDF2SVGToCurrentInputFile(inputFile, outputDir);
//				htmlElement = convertToHTML();
		} else if (Type.PDF2IMAGES.equals(type)) {
			serialImageList = applyPDF2ImagesToInputFile(inputFile);
		} else if (Type.PDF2SVG.equals(type)) {
			applyPDF2SVGToCurrentInputFile(inputFile, outputDir);
		} else if (Type.PDF2TEI.equals(type)) {
			teiFile = applyPDF2TEIToInputFile(inputFile);
			htmlElement = applyTEI2HTMLToInputFile(teiFile);
		} else if (Type.PDF2TXT.equals(type)) {
			outputTxt = applyPDF2TXTToInputFile(inputFile);
		} else if (Type.SCATTER2CSV.equals(type)) {
			tsvString = applyPlotBoxCSVToInput(inputFile);
//			HtmlTable htmlTable = HtmlTable.getFirstDescendantTable(htmlElement);
//			rectangularTable = RectangularTable.createRectangularTable(htmlTable);
		} else if (Type.SVGTABLE2HTML.equals(type)) {
			htmlElement = applySVGTable2HTMLToInputFile(inputFile);
		} else if (Type.SVGTABLE2CSV.equals(type)) {
			htmlElement = applySVGTable2HTMLToInputFile(inputFile);
			HtmlTable htmlTable = HtmlTable.getFirstDescendantTable(htmlElement);
			rectangularTable = RectangularTable.createRectangularTable(htmlTable);
		} else if (Type.TEI2HTML.equals(type)) {
			htmlElement = applyTEI2HTMLToInputFile(inputFile);
		} else if (Type.TEX2HTML.equals(type)) {
			String xmlString = convertTeXToHTML(inputFile);
			createHtmlElement(xmlString);
		} else if (Type.TXT2HTML.equals(type)) {
			htmlElement = applyTXT2HTMLToInputFile(inputFile);
		} else if (normaArgProcessor.getCleanedHtmlElement() != null) {
			LOG.warn("Cannot process cleaned HTML element - use 2-step norma");
		} else if (Type.XML2HTML.equals(type)) {
			xslDocument = createW3CStylesheetDocument(transformTypeString);
			if (xslDocument == null) {
				throw new IllegalArgumentException("null stylesheet for: "+transformTypeString);
			}
			String xmlString = applyXSLDocumentToCurrentCTree(xslDocument);
			if (xmlString == null) {
				LOG.trace("null content in: "+currentCTree.getDirectory());
			} else {
				debug0(xmlString);
				createHtmlElement(xmlString);
				debug1();
			}
		} else {
			LOG.warn("Cannot interpret transform: "+type);
		}
	}

	private void debug0(String xmlString) {
		File file = new File("target/fulltext.xhtml.xml");
		try {
			FileUtils.writeStringToFile(file, xmlString);
			LOG.trace("wrote: html "+file);
		} catch (IOException e) {
			throw new RuntimeException("Cannot write transformer output");
		}
	}

	private void debug1() {
		File file = new File("target/fulltext.xhtml");
			try {
			XMLUtil.debug(htmlElement, new FileOutputStream(file),  1);
			LOG.trace("wrote: xhtml "+file);
		} catch (IOException e) {
			throw new RuntimeException("Cannot write transformer output");
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
	
	private HtmlTable applyCSV2HtmlToInputFile(File inputFile) {
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

	private String applyCSV2TSVToInputFile(File inputFile) {
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

	private SVGElement applyHOCR2SVGToInputFile(File inputFile) {
		HOCRReader hocrReader = new HOCRReader();
		try {
			hocrReader.readHOCR(new FileInputStream(inputFile));
		} catch (IOException e) {
			throw new RuntimeException("Cannot transform HOCR "+inputFile, e);
		}
		SVGSVG svgSvg = (SVGSVG) hocrReader.getOrCreateSVG();
		return svgSvg;
	}

	private String applyPDF2SVGToCurrentInputFile(File inputFile, File outputDirectory) {
		if (outputDirectory == null) {
			throw new RuntimeException("Must give output directory");
		}
		String txt = "dummy";
		PDFAnalyzer pdfAnalyzer = new PDFAnalyzer();
		try {
			this.outputDir = outputDirectory;
			LOG.debug("outputDir: "+outputDir);
			// this is the proper way
//			File svgDir = currentCTree.getAllowedChildDirectory(CTree.SVG); 
			// this is a kludge
			File svgDir = new File(currentCTree.getDirectory(), CTree.SVG);
			File imagesDir = new File(currentCTree.getDirectory(), CTree.IMAGES);
			PDFAnalyzerIO pdfIo = pdfAnalyzer.getPDFIO();
			pdfIo.setOutputDirectory(outputDirectory);
			pdfIo.setOutputDirectory(new File(svgDir, "outputTop"));
			pdfIo.setSvgDir(svgDir);
			pdfIo.setImageDirectory(imagesDir);
			pdfIo.setSkipOutput(true);
			pdfAnalyzer.analyzePDFFile(inputFile);
		} catch (Exception e) {
			throw new RuntimeException("Cannot transform PDF "+inputFile, e);
		}
		return txt;
	}

	private String applyPDF2TXTToInputFile(File inputFile) {
		PDF2TXTConverter converter = new PDF2TXTConverter();
		String txt = null;
		try {
			txt = converter.readPDF(new FileInputStream(inputFile), true);
		} catch (IOException e) {
			throw new RuntimeException("Cannot transform PDF "+inputFile, e);
		}
		return txt;
	}

	private String applyPlotBoxCSVToInput(File inputSVGFile) {
		String csv = null;
		PlotBox plotBox = new PlotBox();
		try {
//			plotBox.setCsvOutFile(new File("target/testplot/"));
//			plotBox.setSvgOutFile(new File("target/plot/bakker1.svg"));
			plotBox.readAndCreatePlot(new FileInputStream(inputSVGFile));
			csv = plotBox.getCSV();
			svgAnnotElement = plotBox.createSVGElement();
		} catch (FileNotFoundException e) {
			throw new RuntimeException("Cannot read svg file: "+inputSVGFile);
		}
		csv = plotBox.getCSV();
		return csv;
	}

	private String applyScatter2CSVToInputFile(File inputFile) {
		String csvString = null;
		throw new RuntimeException("Scatter NYI");
//		PlotBox plotBox = new PlotBox();
//		try {
//			csvTransformer.readFile(inputFile);
//			csvString = csvTransformer.createTSV();
//		} catch (IOException e) {
//			throw new RuntimeException("Cannot convert scatterplot: ", e);
//		}
//		return csvString;
	}


	private File applyPDF2TEIToInputFile(File inputFile) {
		PDF2TEIConverter converter = new PDF2TEIConverter();
		File teiFile = null;
		try {
			teiFile = converter.convertFulltextPDFToTEI(inputFile);
		} catch (Exception e) {
			throw new RuntimeException("Cannot transform PDF "+inputFile, e);
		}
		return teiFile;
	}

	private HtmlElement applyTEI2HTMLToInputFile(File teiFile) {
//		TEI2HTMLConverter converter = new TEI2HTMLConverter();
		HtmlElement htmlElement = null;
//		try {
//			htmlElement = converter.convertTEI2HtmlElement(teiFile);
//		} catch (Exception e) {
//			throw new RuntimeException("Cannot transform TEI "+teiFile, e);
//		}
		return htmlElement;
	}

	private HtmlElement applySVGTable2HTMLToInputFile(File inputFile) {
		LOG.debug("==========="+inputFile+"============");
		SVGTable2HTMLConverter converter = new SVGTable2HTMLConverter();
		converter.readInput(inputFile);
		HtmlElement htmlElement = converter.convert();
		return htmlElement;
	}

	private List<NamedImage> applyPDF2ImagesToInputFile(File inputFile) {
		PDF2ImagesConverter converter = new PDF2ImagesConverter();
		List<NamedImage> namedImageList = null;
		try {
			namedImageList = converter.readPDF(new FileInputStream(inputFile), true);
		} catch (IOException e) {
			throw new RuntimeException("Cannot transform PDF "+inputFile, e);
		}
		return namedImageList;
	}

	private HtmlElement applyTXT2HTMLToInputFile(File inputFile) {
		HtmlElement htmlElement = null;
		try {
			inputTxt = FileUtils.readFileToString(inputFile);
			htmlElement = convertToHTML(inputFile);
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
	private String convertTeXToHTML(File inputFile) {
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

	private HtmlElement convertToHTML(File inputFile) {
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
		String xmlString = null;
		if (inputFile != null) { 
			TransformerWrapper transformerWrapper = getOrCreateTransformerWrapperForStylesheet(xslDocument);
			try {
				xmlString = transformerWrapper.transformToXML(inputFile);
			} catch (NullPointerException npe) {
				throw new RuntimeException("cannot transform (NPE) "+inputFile, npe);
			} catch (Exception e) {
				throw new RuntimeException("cannot transform "+inputFile, e);
			}
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
					throw new IllegalArgumentException("Null stylesheet");
				}
				// FIXME
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
		if (svgElement != null && output != null) {
			currentCTree.writeFile(svgElement.toXML(), output);
		}
		if (svgAnnotElement != null && output != null) {
			String outputAnnot = output.replaceAll("\\.[^\\.]+$", ".annot.svg");
			File outputSvgFile = new File(currentCTree.getDirectory(), outputAnnot);
			SVGSVG.wrapAndWriteAsSVG(svgAnnotElement, outputSvgFile);
		}
		if (serialImageList != null) {
			normaArgProcessor.writeImages();
		}
		if (rectangularTable != null) {
			try {
				rectangularTable.writeCsvFile(outputFile);
			} catch (IOException e) {
				throw new RuntimeException("Cannot write: "+outputFile, e);
			}
		}
		if (teiFile != null) {
			LOG.debug("wrote TEI: "+teiFile);
//			currentCTree.writeFile(teiFile.toXML(), (output != null ? output : CTree.FULLTEXT_XML));
		}
		if (tsvString != null && output != null) {
			try {
				IOUtils.write(tsvString, new FileOutputStream(new File(currentCTree.getDirectory(), output)));
			} catch (IOException e) {
				throw new RuntimeException("cannot write CSV: ", e);
			}
		}

	}

	private org.w3c.dom.Document createW3CStylesheetDocument(String xslName) {
		DocumentBuilder db = createDocumentBuilder();
		String stylesheetResourceName = lookupStylesheetByName(xslName);
		org.w3c.dom.Document stylesheetDocument = readAsResource(db, stylesheetResourceName);
		if (stylesheetDocument == null) {
			LOG.warn("cannout find resource: "+xslName+"; "+stylesheetResourceName);
		}
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
		if (is == null) {
			throw new IllegalArgumentException("Null stylesheet input");
		}
		org.w3c.dom.Document stylesheetDocument = null;
		try {
			stylesheetDocument = db.parse(is);
		} catch (Exception e) { 
			LOG.error("cannot parse stylesheet stream "+e+"; "+xslName);
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
		List<SectionTaggerX> sectionTaggers = normaArgProcessor.getSectionTaggers();
		for (SectionTaggerX sectionTagger : sectionTaggers) {
//			LOG.trace("section tagger:" + sectionTagger);
//			try {
//				Element xmlElement = XMLUtil.parseXML(xmlString);
//			} catch (RuntimeException e) {
//				throw new RuntimeException("failed to parse: "+xmlString.substring(0, Math.min(200, xmlString.length())), e);
//			}
		}
	}

	public File getInputFile() {
		return inputFile;
	}

	public void setInputFile(File inputFile) {
		this.inputFile = inputFile;
	}

	public void setCurrentCTree(CTree currentCTree) {
		this.currentCTree = currentCTree;
	}

}
