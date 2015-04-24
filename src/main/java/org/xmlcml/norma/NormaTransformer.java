package org.xmlcml.norma;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.xmlcml.cmine.args.ArgumentOption;
import org.xmlcml.cmine.files.CMDir;
import org.xmlcml.html.HtmlElement;
import org.xmlcml.norma.input.pdf.PDF2TXTConverter;
import org.xmlcml.norma.util.TransformerWrapper;

public class NormaTransformer {
	
	private static final Logger LOG = Logger.getLogger(NormaTransformer.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}
	private static final String XSL = "--xsl";
	private static final String PDF2HTML = "pdf2html";
	private static final String PDF2TXT = "pdf2txt";
	private static final String TXT2HTML = "txt2html";
	
	private NormaArgProcessor normaArgProcessor;
	private CMDir currentCMDir;
	private File inputFile;
	private Map<org.w3c.dom.Document, TransformerWrapper> transformerWrapperByStylesheetMap;
	String outputTxt;
	List<String> xmlStringList;
	HtmlElement htmlElement;

	public NormaTransformer(NormaArgProcessor argProcessor) {
		this.normaArgProcessor = argProcessor;
		this.currentCMDir = argProcessor.getCurrentCMDir();
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
		inputFile = normaArgProcessor.checkAndGetInputFile(currentCMDir);
		LOG.trace("TRANSFORM "+option.getVerbose());
		outputTxt = null;
		htmlElement = null;
		xmlStringList = null;
		if (option.getVerbose().equals(XSL)) {
			if (PDF2TXT.equals(option.getStringValue())) {
				outputTxt = applyPDF2TXTToCMLDir();
			} else if (TXT2HTML.equals(option.getStringValue())) {
				htmlElement = applyTXT2HTMLToCMDir();
			} else if (PDF2HTML.equals(option.getStringValue())) {
				applyPDF2TXTToCMLDir();
				htmlElement = convertToHTML(outputTxt);
			} else {
				xmlStringList = applyXSLDocumentListToCurrentCMDir();
			}
		}
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

	private HtmlElement applyTXT2HTMLToCMDir() {
		HtmlElement htmlElement = null;
		try {
			String txt = FileUtils.readFileToString(inputFile);
			htmlElement = convertToHTML(txt);
		} catch (IOException e) {
			throw new RuntimeException("Cannot transform TXT "+inputFile, e);
		}
		return htmlElement;
	}

	private HtmlElement convertToHTML(String txt) {
		HtmlElement element = null;
		return element;
	}

	private List<String> applyXSLDocumentListToCurrentCMDir() {
		List<org.w3c.dom.Document> xslDocumentList = normaArgProcessor.getXslDocumentList();
		xmlStringList = new ArrayList<String>();
		for (org.w3c.dom.Document xslDocument : xslDocumentList) {
			try {
				String xmlString = transform(xslDocument);
				xmlStringList.add(xmlString);
			} catch (IOException e) {
				LOG.error("Cannot transform "+currentCMDir+"; "+e);
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

	public HtmlElement getHtmlElement() {
		return htmlElement;
	}





}
