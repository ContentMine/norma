package org.xmlcml.norma.input.pdf;

import java.io.File;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

import org.apache.log4j.Logger;
import org.xmlcml.graphics.svg.SVGSVG;
import org.xmlcml.html.HtmlElement;
import org.xmlcml.pdf2svg.PDF2SVGConverter;
import org.xmlcml.svg2xml.pdf.PDFAnalyzer;

/** 
 * Convert PDF to XHTML, SVG and PNG.
 * 
 * @author pm286
 */
public class PDF2XHTMLConverter {

	private final static Logger LOG = Logger.getLogger(PDF2XHTMLConverter.class);
	
	public PDF2XHTMLConverter() {
		
	}

	public HtmlElement readAndConvertToXHTML(File infile) throws Exception {
	     List<SVGSVG> svgList = readAndConvertToSVGList(infile);
	 	 HtmlElement htmlElement = readAndConvertToXHTML(svgList);
	 	 return htmlElement;
	}

	public HtmlElement readAndConvertToXHTML(URL url) throws Exception {
	     List<SVGSVG> svgList = readAndConvertToSVGList(url);
	 	 HtmlElement htmlElement = readAndConvertToXHTML(svgList);
	 	 return htmlElement;
	}

	public HtmlElement readAndConvertToXHTML(InputStream is) throws Exception {
	     List<SVGSVG> svgList = readAndConvertToSVGList(is);
	 	 HtmlElement htmlElement = readAndConvertToXHTML(svgList);
	 	 return htmlElement;
	}

	public List<SVGSVG> readAndConvertToSVGList(File infile) throws Exception {
		PDF2SVGConverter converter = createAndSetConverter();
		converter.openPDFFile(infile);
		List<SVGSVG> svgList = converter.getPageList();
		return svgList;
	}

	public List<SVGSVG> readAndConvertToSVGList(URL url) throws Exception {
		PDF2SVGConverter converter = createAndSetConverter();
		converter.openPDFURL(url);
		List<SVGSVG> svgList = converter.getPageList();
		return svgList;
	}

	public List<SVGSVG> readAndConvertToSVGList(InputStream is) throws Exception {
		PDF2SVGConverter converter = createAndSetConverter();
		converter.openPDFInputStream(is);
		List<SVGSVG> svgList = converter.getPageList();
		return svgList;
	}

	private PDF2SVGConverter createAndSetConverter() {
		PDF2SVGConverter converter = new PDF2SVGConverter();
		converter.setWriteFile(false);
		converter.setStoreSVG(true);
		return converter;
	}

	public HtmlElement readAndConvertToXHTML(List<SVGSVG> svgList) throws Exception {
		HtmlElement element = null;
		PDFAnalyzer pdfAnalyzer = new PDFAnalyzer();
		pdfAnalyzer.createAndFillPageAnalyzers(svgList);
		element = pdfAnalyzer.forceCreateRunningHtml();
		return element;
	}
}
