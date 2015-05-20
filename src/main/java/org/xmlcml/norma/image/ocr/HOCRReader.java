package org.xmlcml.norma.image.ocr;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nu.xom.Attribute;
import nu.xom.Element;
import nu.xom.Elements;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.xmlcml.euclid.Real2;
import org.xmlcml.euclid.Real2Range;
import org.xmlcml.graphics.svg.SVGElement;
import org.xmlcml.graphics.svg.SVGG;
import org.xmlcml.graphics.svg.SVGRect;
import org.xmlcml.graphics.svg.SVGSVG;
import org.xmlcml.graphics.svg.SVGText;
import org.xmlcml.graphics.svg.text.SVGPara;
import org.xmlcml.graphics.svg.text.SVGWord;
import org.xmlcml.graphics.svg.text.SVGWordBlock;
import org.xmlcml.graphics.svg.text.SVGWordPage;
import org.xmlcml.html.HtmlBody;
import org.xmlcml.html.HtmlDiv;
import org.xmlcml.html.HtmlElement;
import org.xmlcml.html.HtmlEm;
import org.xmlcml.html.HtmlHead;
import org.xmlcml.html.HtmlHtml;
import org.xmlcml.html.HtmlMeta;
import org.xmlcml.html.HtmlP;
import org.xmlcml.html.HtmlSpan;
import org.xmlcml.html.HtmlStrong;
import org.xmlcml.norma.input.InputReader;
import org.xmlcml.xml.XMLUtil;

/** reads the HTML output from Tesseract and generates SVG including text and boxes.
 * 
 * @author pm286
 *
 */
public class HOCRReader extends InputReader {


	private static final String SEPARATOR = "~";
	private static final String HELVETICA = "helvetica";
	private static final String LOW_CONF_COL = "red";
	private static final String UNEDITED_COL = "green";
	private static final String EDITED_COL = "pink";
	private static final String LINE_COL = "yellow";
	
	private static final Logger LOG = Logger.getLogger(HOCRReader.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}
	
	private static final double DEFAULT_FONT_SIZE = 10.0;
	private static final String EDITED = "edited";
	private static final String GARBLE = "garble";
	private static final String ORIGINAL = "original";
	
	private static final String WORD = "word";
	private static final String LINE = "line";
	private static final String ITALIC = "italic";
	private static final String BOLD = "bold";

	private static final String OCR_CAREA = "ocr_carea";
	private static final String OCR_LINE = "ocr_line";
	private static final String OCR_PAGE = "ocr_page";
	private static final String OCR_PAR = "ocr_par";
	private static final String OCRX_WORD = "ocrx_word";

	private static final double MIN_WIDTH = 4.5;
	private static final double RECT_OPACITY = 0.2;
	private static final Double LOW_CONF_WIDTH = 3.0;

	String ITALIC_GARBLES_XML = "/org/xmlcml/norma/images/ocr/italicGarbles.xml";

	private HtmlElement hocrElement;
	private SVGSVG svgSvg;
	private HtmlBody newBody;
	private HtmlHtml rawHtml;
	private HtmlHead rawHead;
	private HtmlBody rawBody;
	private String title;
	private List<HtmlMeta> metaList;

	private Map<String, String> garbleMap;
	private String garbleCharacters;
	private HtmlHtml htmlHtml;

	public HOCRReader() {
		setup();
	}
	
	private void setup() {
		this.readGarbleEdits(this.getClass().getResourceAsStream(ITALIC_GARBLES_XML));
	}
	
	public void readHOCR(InputStream is) throws IOException {
		String s = IOUtils.toString(is);
		readHOCR(HtmlElement.create(XMLUtil.stripDTDAndParse(s)));
		processHTML();
	}
	
	public void readHOCR(HtmlElement hocrElement) {
		this.hocrElement = hocrElement;
		rawHtml = (hocrElement instanceof HtmlHtml) ? (HtmlHtml) hocrElement : null;
	}
	
	public HtmlElement getHocrElement() {
		return hocrElement;
	}

	public SVGElement getOrCreateSVG() {
		if (svgSvg == null && hocrElement != null) {
			processHTML();
		}
		return svgSvg;
	}

	public HtmlBody getOrCreateHtmlBody() {
		getOrCreateSVG();
		return newBody;
	}

	private void processHTML() {
		processHead();
		processBody();
		createSVGAndHTML();
	}

	/**
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
 <head>
  <title>
</title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
  <meta name='ocr-system' content='tesseract 3.03' />
  <meta name='ocr-capabilities' content='ocr_page ocr_carea ocr_par ocr_line ocrx_word'/>
</head>	 */
	private void processHead() {
		rawHead = rawHtml == null ? null : rawHtml.getHead();
		if (rawHead != null) {
			title = rawHead.getTitle();
			metaList = rawHead.getMetaElements();
		}
	}
	
	private void processBody() {
		rawBody = rawHtml.getBody();
	}

	public HtmlHead getHead() {
		return rawHead;
	}
	public List<HtmlMeta> getMetaElements() {
		return metaList;
	}
	
	public String getTesseractVersion() {
		for (HtmlMeta meta : metaList) {
			if ("ocr-system".equals(meta.getName())) {
				return meta.getContent();
			}
		}
		return null;
	}
	
	/** get pages
<body>
  <div class='ocr_page' id='page_1' title='image "ijs.0.003749-0-000.pbm.png"; bbox 0 0 1243 557; ppageno 0'>
   <div class='ocr_carea' id='block_1_1' title="bbox 72 3 113 20">
    <p class='ocr_par' dir='ltr' id='par_1_1' title="bbox 72 3 113 20">
     <span class='ocr_line' id='line_1_1' title="bbox 72 3 113 20; baseline 0 0"><span class='ocrx_word' id='word_1_1' title='bbox 72 3 113 20; x_wconf 85' lang='eng'><em>002</em></span> 
     </span>
    </p>
   </div>
   <div class='ocr_carea' id='block_1_2' title="bbox 347 233 366 246">
    <p class='ocr_par' dir='ltr' id='par_1_2' title="bbox 347 233 366 246">
     <span class='ocr_line' id='line_1_2' title="bbox 347 233 366 246; baseline 0 0"><span class='ocrx_word' id='word_1_2' title='bbox 347 233 366 246; x_wconf 68' lang='eng'><strong>47</strong></span> 
     </span>
    </p>
   </div>
	 */
	
	private void createSVGAndHTML() {
		svgSvg = new SVGSVG();
		svgSvg.setFontFamily(HELVETICA);
		svgSvg.setFontSize(DEFAULT_FONT_SIZE);
		newBody= new HtmlBody();
		String tVersion = getTesseractVersion();
		svgSvg.addAttribute(new Attribute("tesseractVersion", tVersion));
		Elements childs = rawBody.getChildElements();
		for (int i = 0; i < childs.size(); i++) {
			Element child = childs.get(i);
			if (child instanceof HtmlDiv) {
				HtmlDiv htmlDiv = (HtmlDiv) child;
				if (OCR_PAGE.equals(htmlDiv.getClassAttribute())) {
					HtmlSVG page = this.createPageFromTesseract(htmlDiv);
					svgSvg.appendChild(page.svg);
					newBody.appendChild(page.html); 
				} else {
					throw new RuntimeException("unknown div "+htmlDiv.toXML());
				}
			} else {
				throw new RuntimeException("unknown element "+child.toXML());
			}
		}
	}
	
	private HtmlSVG createPageFromTesseract(HtmlDiv wordPageDiv) {
		SVGWordPage svgPage = wordPageDiv == null ? null : new SVGWordPage();
		HtmlDiv htmlPage = new HtmlDiv();
		XMLUtil.copyAttributes(wordPageDiv, htmlPage);
		HtmlSVG htmlSVG = new HtmlSVG(htmlPage, svgPage);
		Elements childs = wordPageDiv.getChildElements();
		for (int i = 0; i < childs.size(); i++) {
			Element child = childs.get(i);
			if (child instanceof HtmlDiv) {
				HtmlDiv htmlDiv = (HtmlDiv) child;
				if (OCR_CAREA.equals(htmlDiv.getClassAttribute())) {
					HtmlSVG block = this.createBlockFromTesseract(htmlDiv);
					svgPage.appendChild(block.svg);
					htmlPage.appendChild(block.html);
				} else {
					throw new RuntimeException("unknown div "+htmlDiv.toXML());
				}
			} else {
				throw new RuntimeException("unknown element "+child.toXML());
			}
		}
		return htmlSVG;
	}

	private HtmlSVG createBlockFromTesseract(HtmlDiv wordBlockDiv) {
		HtmlDiv htmlBlock = new HtmlDiv();
		XMLUtil.copyAttributes(wordBlockDiv, htmlBlock);
		htmlBlock.setClassAttribute("block");
		SVGWordBlock svgBlock = wordBlockDiv == null ? null : new SVGWordBlock();
		HtmlSVG htmlSVG = new HtmlSVG(htmlBlock, svgBlock);
		Elements childs = wordBlockDiv.getChildElements();
		for (int i = 0; i < childs.size(); i++) {
			Element child = childs.get(i);
			if (child instanceof HtmlP) {
				HtmlP htmlP = (HtmlP) child;
				if (OCR_PAR.equals(htmlP.getClassAttribute())) {
					HtmlSVG para = this.createParFromTesseract(htmlP);
					svgBlock.appendChild(para.svg);
					htmlBlock.appendChild(para.html);
				} else {
					throw new RuntimeException("unknown div "+htmlP.toXML());
				}
			} else {
				throw new RuntimeException("unknown element "+child.toXML());
			}
		}
		return htmlSVG;
	}

	private HtmlSVG createParFromTesseract(HtmlP p) {
		SVGPara svgPara = p == null ? null : new SVGPara();
		HtmlP htmlP = new HtmlP();
		XMLUtil.copyAttributes(p, htmlP);
		HtmlSVG htmlSVG = new HtmlSVG(htmlP, svgPara);
		Elements childs = p.getChildElements();
		for (int i = 0; i < childs.size(); i++) {
			Element child = childs.get(i);
			if (child instanceof HtmlSpan) {
				HtmlSpan htmlSpan = (HtmlSpan) child;
				if (OCR_LINE.equals(htmlSpan.getClassAttribute())) {
					HtmlSVG line = this.createLineFromTesseract(htmlSpan);
					svgPara.appendChild(line.svg);
					htmlP.appendChild(line.html);
				} else {
					throw new RuntimeException("unknown span "+htmlSpan.toXML());
				}
			} else {
				throw new RuntimeException("unknown element "+child.getClass()+" / "+child.toXML());
			}
		}
		return htmlSVG;
	}

	private HtmlSVG createLineFromTesseract(HtmlSpan lineSpan) {
		SVGG svgLine = new SVGG();
		HtmlSpan htmlLineSpan = new HtmlSpan();
		XMLUtil.copyAttributes(lineSpan, htmlLineSpan);
		HtmlSVG htmlSVG = new HtmlSVG(htmlLineSpan, svgLine);
		HOCRTitle hocrTitle = new HOCRTitle(lineSpan.getTitle());
		Real2Range bbox = hocrTitle.getBoundingBox();
		if (bbox.getXRange().getRange() > MIN_WIDTH && bbox.getYRange().getRange() > MIN_WIDTH) {

			hocrTitle.addAttributes(svgLine);
			SVGRect rect = SVGRect.createFromReal2Range(bbox);
			rect.setFill(LINE_COL);
			rect.setOpacity(RECT_OPACITY);
			svgLine.appendChild(rect);
			svgLine.setFontSize(svgLine.getHeight());
			svgLine.setClassName(LINE);
			Elements childs = lineSpan.getChildElements();
			for (int i = 0; i < childs.size(); i++) {
				Element child = childs.get(i);
				if (child instanceof HtmlSpan) {
					HtmlSpan htmlSpan1 = (HtmlSpan) child;
					if (OCRX_WORD.equals(htmlSpan1.getClassAttribute())) {
						HtmlSVG word = createWordFromTesseract(htmlSpan1);
						svgLine.appendChild(word.svg);
						htmlLineSpan.appendChild(word.html);
					}
				}
			}
		}
		return htmlSVG;
	}

	private HtmlSVG createWordFromTesseract(HtmlSpan htmlSpan0) {
		SVGWord svgWord = new SVGWord();
		HtmlSpan htmlSpan = new HtmlSpan(); 
		HtmlSVG htmlSVG = new HtmlSVG(htmlSpan, svgWord);
		HOCRTitle hocrTitle = new HOCRTitle(htmlSpan0.getTitle());
		Real2Range bbox = hocrTitle.getBoundingBox();
		if (bbox.getXRange().getRange() > MIN_WIDTH && bbox.getYRange().getRange() > MIN_WIDTH) {
			String wordValue = htmlSpan0.getValue();
			// exclude traces of lines
			if (wordValue.trim().length() > 0) {
				double height = bbox.getYRange().getRange();
				SVGRect rect = SVGRect.createFromReal2Range(bbox);
				rect.setFill(UNEDITED_COL);
				rect.setOpacity(RECT_OPACITY);
				svgWord.appendChild(rect);
				svgWord.setClassName(WORD);
				Elements childs = htmlSpan0.getChildElements();
				int nchild = childs.size();
				if (nchild > 1) {
					throw new RuntimeException("multiple styles in word");
				}
				wordValue = editGarbles(wordValue, rect);
				SVGText text = createTextElement(bbox, wordValue, height);
				htmlSpan.setValue(wordValue);
				svgWord.appendChild(text);
				if (hocrTitle.getWConf() < 50) {
//					text.setOpacity(hocrTitle.getWConf() * 0.007);
//					text.setFill(LOW_CONF_COL);
					rect.setStrokeWidth(LOW_CONF_WIDTH);
				}
				hocrTitle.addAttributes(svgWord);
				addStylesFromStrongEm(childs, nchild, text);
			}
		}
		return htmlSVG;
	}

	private String editGarbles(String wordValue, SVGRect rect) {
		ensureGarbleMap();
		if (containsPotentialGarble(wordValue)) {
			String newWordValue = wordValue;
			for (String original : garbleMap.keySet()) {
				String edited = garbleMap.get(original);
				newWordValue = newWordValue.replaceAll(original, edited);
			}
			if (!newWordValue.equals(wordValue)) {
				LOG.trace("edited "+wordValue+"=>"+newWordValue);
				wordValue = newWordValue;
				rect.setFill(EDITED_COL);
			}
		}
		return wordValue;
	}

	/** does wordValue contain potential garble characters?
	 * to speed up searching
	 * 
	 * @param wordValue
	 * @return
	 */
	private boolean containsPotentialGarble(String wordValue) {
		for (int i = 0; i < wordValue.length(); i++) {
			if (wordValue.contains(String.valueOf(wordValue.charAt(i)))) return true;
		}
		return false;
	}

	private static SVGText createTextElement(Real2Range bbox, String word, double height) {
		SVGText text = new SVGText();
		text.removeAttributes();
		// this is empirical but seems to more or less work
		SimpleFontMetrics fontMetrics = new SimpleFontMetrics(word);
		double yOffset = fontMetrics.getDescenderFraction();
		double ratio = 1.0;
		// descenders only
		if (fontMetrics.hasDescenders()) {
			ratio = 1.0 + fontMetrics.getDescenderFraction();
		} else if (!fontMetrics.hasAscenders() && !fontMetrics.hasDescenders()) {
		// no descenders or ascenders
//			LOG.debug("no asc desc "+word);
			ratio = 1.0 / (1.0 + SimpleFontMetrics.DEFAULT_DESCENDER_FRACTION);
		}
		text.setFontSize(height / ratio);
		Real2 xy = bbox.getCorners()[0];
		xy = xy.plus(new Real2(0., height * (1 - yOffset))); // to offset the y-direction 
		text.setXY(xy);
		text.appendChild(word);
		return text;
	}

	private static void addStylesFromStrongEm(Elements childs, int nchild, SVGText text) {
		if (nchild > 0) {
			Element child = childs.get(0);
			if (child instanceof HtmlStrong) {
				text.setFontStyle(BOLD);
			} else if (child instanceof HtmlEm) {
				text.setFontStyle(ITALIC);
			} else {
				throw new RuntimeException("unknown element "+child.getClass()+" / "+child.toXML());
			}
		}
	}
	
	public void readGarbleEdits(InputStream garblesStream) {
		ensureGarbleMap();
		try {
			Element garblesElement = XMLUtil.parseQuietlyToDocument(garblesStream).getRootElement();
			garbleCharacters = garblesElement.getAttributeValue("characters");
			Elements garbles = garblesElement.getChildElements(GARBLE);
			for (int i = 0; i < garbles.size(); i++) {
				Element garble = garbles.get(i);
				String original = garble.getAttributeValue(ORIGINAL);
				String edited = garble.getAttributeValue(EDITED);
				garbleMap.put(original, edited);
			}
		} catch (Exception e) {
			throw new RuntimeException("Cannot parse garblesStream", e);
		}
	}

	private void ensureGarbleMap() {
		if (garbleMap == null) {
			garbleMap = new HashMap<String, String>();
		}
	}

	public List<HtmlSpan> getNonEmptyLines() {
		HtmlBody body = getOrCreateHtmlBody();
//		try {
//			XMLUtil.debug(body, new FileOutputStream("target/hocr/newBody.html"), 1);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		List<Element> lines = XMLUtil.getQueryElements(body, ".//*[local-name()='span' and @class='ocr_line']");
		List<HtmlSpan> htmlLines = new ArrayList<HtmlSpan>();
		for (Element line : lines) {
			if (line.getValue().trim().length() != 0) {
				htmlLines.add((HtmlSpan)line);
			}
		}
		return htmlLines;
	}

	public static List<HtmlSpan> getWords(HtmlSpan line) {
		List<HtmlSpan> wordList = new ArrayList<HtmlSpan>();
		List<Element> words = XMLUtil.getQueryElements(line, "*[local-name()='span' and not(normalize-space(.)='')]");
		for (Element word : words) {
			wordList.add((HtmlSpan)word);
		}
		return wordList;
	}

	public static List<String> matchPattern(HtmlSpan line, Pattern pattern) {
		List<String> matchList = new ArrayList<String>();
		List<HtmlSpan> words = HOCRReader.getWords(line);
		StringBuilder sb = new StringBuilder();
		for (HtmlSpan word : words) {
			sb.append(word.getValue());
			sb.append(SEPARATOR);
		}
		String s = sb.toString();
		Matcher matcher = pattern.matcher(s);
		if (matcher.matches()) {
			for (int i = 1; i <= matcher.groupCount(); i++) {
				String match = matcher.group(i);
				matchList.add((match == null ? null : match.replaceAll(SEPARATOR, "")));
			}
		}
		return matchList;
	}

	public static String getSpacedValue(HtmlSpan line) {
		StringBuilder sb = new StringBuilder();
		List<HtmlSpan> spans = HOCRReader.getWords(line);
		for (HtmlSpan span : spans) {
			sb.append(span.getValue()+" ");
		}
		return sb.toString().trim();
	}
	
}
class HtmlSVG {

	public HtmlElement html;
	public SVGElement svg;
	
	public HtmlSVG(HtmlElement html, SVGElement svg) {
		this.html = html;
		this.svg = svg;
	}
}
