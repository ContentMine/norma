package org.xmlcml.norma.image.ocr;

import java.awt.image.BufferedImage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

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
import org.xmlcml.graphics.svg.SVGUtil;
import org.xmlcml.graphics.svg.text.SVGPhrase;
import org.xmlcml.graphics.svg.text.SVGWord;
import org.xmlcml.graphics.svg.text.SVGWordBlock;
import org.xmlcml.graphics.svg.text.SVGWordLine;
import org.xmlcml.graphics.svg.text.SVGWordPage;
import org.xmlcml.graphics.svg.text.SVGWordPageList;
import org.xmlcml.graphics.svg.text.SVGWordPara;
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
import org.xmlcml.image.ImageUtil;
import org.xmlcml.norma.editor.SubstitutionEditor;
import org.xmlcml.norma.input.InputReader;
import org.xmlcml.xml.XMLUtil;

/** reads the HTML output from Tesseract and generates SVG including text and boxes.
 * 
 * @author pm286
 *
 */
public class HOCRReader extends InputReader {


	public static final Logger LOG = Logger.getLogger(HOCRReader.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}

	private static final String POTENTIAL_LABEL = "potential_label";

	private static final int TESSERACT_SLEEP = 200;
	private static final int TESSERACT_TRIES = 10;
	
	private static final String SEPARATOR = "~";
	private static final String HELVETICA = "helvetica";
	private static final String LOW_CONF_COL = "red";
	private static final String UNEDITED_COL = "green";
	private static final String LINE_COL = "yellow";
	
	private static final double DEFAULT_FONT_SIZE = 10.0;
	
	private static final String WORD = "word";
	private static final String LINE = "line";
	private static final String ITALIC = "italic";
	private static final String BOLD = "bold";

	private static final String OCR_CAREA = "ocr_carea";
	private static final String OCR_LINE = "ocr_line";
	private static final String OCR_PAGE = "ocr_page";
	private static final String OCR_PAR = "ocr_par";
	private static final String OCRX_WORD = "ocrx_word";
	private static final String CLASS = "class";

	public static final String HOCR = ".hocr";
	public static final String HOCR_HTML = ".hocr.html";
	public static final String HOCR_SVG = ".hocr.svg";

	private static final double MIN_WIDTH = 4.5;
	private static final double RECT_OPACITY = 0.2;
	private static final Double LOW_CONF_WIDTH = 3.0;
	private static final double MAX_FONT_SIZE = 30;

	private HtmlElement hocrHtmlElement;
	private SVGSVG svgSvg;
	private HtmlBody newBody;
	private HtmlHtml rawHtml;
	private HtmlHead rawHead;
	private HtmlBody rawBody;
	private String title;
	private List<HtmlMeta> metaList;

	private long tesseractSleep;
	private int tesseractTries;
	private double maxFontSize;
	private Pattern labelPattern;
	private int imageMarginX = 0;
	private int imageMarginY = 0;
	private int marginColor = 0xffffffff;
	private List<HOCRLabel> potentialLabelList;
	private List<HOCRText> potentialTextList;
	private List<HOCRPhrase> potentialPhraseList;

	private Real2Range wordJoiningBox;
	
	private List<SVGWordLine> wordLineList;
	private List<SVGPhrase> allPhraseList;

	private SubstitutionEditor substitutionManager;

	public int getImageMarginX() {
		return imageMarginX;
	}

	public void setImageMarginX(int imageMarginX) {
		this.imageMarginX = imageMarginX;
	}

	public int getImageMarginY() {
		return imageMarginY;
	}

	public void setImageMarginY(int imageMarginY) {
		this.imageMarginY = imageMarginY;
	}

	public int getMarginColor() {
		return marginColor;
	}

	public void setMarginColor(int marginColor) {
		this.marginColor = marginColor;
	}

	public HOCRReader() {
		setup();
	}
	
	private void setup() {
		clearVariables();
		setDefaults();
	}
	
	private void setDefaults() {
		tesseractSleep = TESSERACT_SLEEP;
		tesseractTries = TESSERACT_TRIES;
		maxFontSize = MAX_FONT_SIZE;
	}

	public void clearVariables() {
		hocrHtmlElement = null;
		svgSvg = null;
		newBody = null;
		rawHtml = null;
		rawHead = null;
		rawBody = null;
		title = null;
		metaList = null;
		potentialLabelList = null;
	}
	
	public void readHOCR(InputStream is) throws IOException {
		String s = IOUtils.toString(is, "UTF-8");
		readHOCR(HtmlElement.create(XMLUtil.stripDTDAndParse(s)));
		applyUniversalSubstitutions();
		processHTMLAndCreateSVG();
	}
	
	private void applyUniversalSubstitutions() {
		ensureUniversalSubstitutions();
	}

	private void ensureUniversalSubstitutions() {
//		if ()
	}

	public void readHOCR(HtmlElement hocrElement) {
		this.hocrHtmlElement = hocrElement;
		rawHtml = (hocrElement instanceof HtmlHtml) ? (HtmlHtml) hocrElement : null;
	}
	
	public HtmlElement getHocrElement() {
		return hocrHtmlElement;
	}

	public SVGElement getOrCreateSVG() {
		if (svgSvg == null && hocrHtmlElement != null) {
			processHTMLAndCreateSVG();
		}
		if (labelPattern != null) {
			getOrCreatePotentialLabelElements(svgSvg);
		}
		return svgSvg;
	}

	public HtmlBody getOrCreateHtmlBody() {
		getOrCreateSVG();
		return newBody;
	}

	private void processHTMLAndCreateSVG() {
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
		if (childs.size() > 0) {
			SVGWordPageList wordPageList = new SVGWordPageList();
			svgSvg.appendChild(wordPageList);
			for (int i = 0; i < childs.size(); i++) {
				Element child = childs.get(i);
				if (child instanceof HtmlDiv) {
					HtmlDiv htmlDiv = (HtmlDiv) child;
					if (OCR_PAGE.equals(htmlDiv.getClassAttribute())) {
						HtmlSVG page = this.createPageFromTesseract(htmlDiv);
						wordPageList.appendChild(page.svg);
						newBody.appendChild(page.html); 
					} else {
						throw new RuntimeException("unknown div "+htmlDiv.toXML());
					}
				} else {
					throw new RuntimeException("unknown element "+child.toXML());
				}
			}
		}
	}
	
	private HtmlSVG createPageFromTesseract(HtmlDiv wordPageDiv) {
		SVGWordPage svgPage = wordPageDiv == null ? null : new SVGWordPage();
		HtmlDiv htmlPage = new HtmlDiv();
		HOCRReader.copyAttributes(wordPageDiv, svgPage);
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
		HOCRReader.copyAttributes(wordBlockDiv, svgBlock);
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
		SVGWordPara svgPara = p == null ? null : new SVGWordPara();
		HOCRReader.copyAttributes(p, svgPara);
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
		SVGWordLine svgLine = new SVGWordLine();
		HOCRReader.copyAttributes(lineSpan, svgLine);
		HtmlSpan htmlLineSpan = new HtmlSpan();
		XMLUtil.copyAttributes(lineSpan, htmlLineSpan);
		HtmlSVG htmlSVG = new HtmlSVG(htmlLineSpan, svgLine);
		HOCRTitle hocrTitle = new HOCRTitle(lineSpan.getTitle());
		Real2Range bbox = hocrTitle.getBoundingBox();
		if (bbox.getXRange().getRange() > MIN_WIDTH && bbox.getYRange().getRange() > MIN_WIDTH) {
			boolean largeText = false;
			hocrTitle.addAttributes(svgLine);
			SVGRect rect = SVGRect.createFromReal2Range(bbox);
			rect.setFill(LINE_COL);
			rect.setOpacity(RECT_OPACITY);
			svgLine.appendChild(rect);
			Double fontSize = svgLine.getBoundingBox().getYRange().getRange();
			if (fontSize > getMaxFontSize()) {
				LOG.trace("largeText "+fontSize);
				fontSize = getMaxFontSize();
				largeText = true;
			}
			svgLine.setFontSize(fontSize);
			svgLine.setClassName(LINE);
			Elements childs = lineSpan.getChildElements();
			for (int i = 0; i < childs.size(); i++) {
				Element child = childs.get(i);
				if (child instanceof HtmlSpan) {
					HtmlSpan htmlSpan1 = (HtmlSpan) child;
					String classAttribute = htmlSpan1.getClassAttribute();
					if (OCRX_WORD.equals(classAttribute)) {
						addWord(svgLine, htmlLineSpan, largeText, htmlSpan1);
					} else {
						LOG.debug("omitted attribute: "+classAttribute);
					}
				}
			}
		}
		return htmlSVG;
	}

	private void addWord(SVGWordLine svgLine, HtmlSpan htmlLineSpan,
			boolean largeText, HtmlSpan htmlSpan1) {
		HtmlSVG word = createWordFromTesseract(htmlSpan1);
		word.setLargeText(largeText);
		svgLine.appendChild(word.svg);
		String htmlValue0 = htmlSpan1.getValue();
		if (htmlValue0.trim().length() == 0 && htmlValue0.length() > 0) {
			addSpaceMarker(svgLine, htmlSpan1, word);
		}
		String htmlValue = htmlValue0.trim();
		if (fitsRegex(htmlValue)) {
			String clazz = word.svg.getAttributeValue(CLASS);
			clazz = clazz == null ? POTENTIAL_LABEL : clazz+" "+POTENTIAL_LABEL;
			word.svg.addAttribute(new Attribute(CLASS, clazz));
		}
		htmlLineSpan.appendChild(word.html);
	}

	/** don't think this does anything useful.
	 * empty spaces have huge bounding boxes.
	 * 
	 * @param svgLine
	 * @param htmlSpan1
	 * @param word
	 */
	private void addSpaceMarker(SVGWordLine svgLine, HtmlSpan htmlSpan1, HtmlSVG word) {
		LOG.trace("SPACE...");
		LOG.trace("span "+htmlSpan1.toXML());
		LOG.trace(svgLine.toXML());
		Real2Range bbox1 = word.svg.getBoundingBox();
		if (bbox1 != null) {
			Real2 xy = bbox1.getCorners()[0];
			SVGText text = new SVGText(xy, "SPACE");
			text.setFontSize(15.);
			word.svg.appendChild(text);
			LOG.trace(word.svg.toXML());
		}
	}

	private boolean fitsRegex(String value) {
		boolean fitsRegex = (value == null || labelPattern == null) ? false :
			labelPattern.matcher(value).matches();
		if (fitsRegex) {
			LOG.trace("matches: "+value);
		}
		return fitsRegex;
	}

	private static void copyAttributes(HtmlElement from, SVGG to) {
		copyAttribute("id", from, to);
	}

	private static void copyAttribute(String attname, HtmlElement from, SVGG to) {
		String attval = from.getAttributeValue(attname);
		if (attval != null) {
			to.addAttribute(new Attribute(attname, attval));
		}
	}

	private HtmlSVG createWordFromTesseract(HtmlSpan htmlSpan0) {
		LOG.trace("createWordFromTesseract");
		SVGWord svgWord = new SVGWord();
		HOCRReader.copyAttributes(htmlSpan0, svgWord);
		HtmlSpan htmlSpan = new HtmlSpan(); 
		HtmlSVG htmlSVG = new HtmlSVG(htmlSpan, svgWord);
		HOCRTitle hocrTitle = new HOCRTitle(htmlSpan0.getTitle());
		ensureSubstitutionManager();
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
				wordValue = substitutionManager.applySubstitutions(svgWord, wordValue, rect);
				boolean lowConf = false;
				if (height > getMaxFontSize()) {
					height = getMaxFontSize();
					lowConf = true;
				}
				SVGText text = createTextElement(bbox, wordValue, height);
				htmlSpan.setValue(wordValue);
				svgWord.appendChild(text);
				if (hocrTitle.getWConf() != null && hocrTitle.getWConf() < 50) {
					lowConf = true;
				}
				if (lowConf) {
					Integer wConf = hocrTitle.getWConf();
					if (wConf == null) wConf = 100;
					text.setOpacity(wConf * 0.007);
					text.setFill(LOW_CONF_COL);
					rect.setStrokeWidth(LOW_CONF_WIDTH);
				}
				hocrTitle.addAttributes(svgWord);
				addStylesFromStrongEm(childs, nchild, text);
			}
		}
		return htmlSVG;
	}

	private void ensureSubstitutionManager() {
		if (substitutionManager == null) {
			substitutionManager = new SubstitutionEditor();
		}
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

	public void createHTMLandSVG(File imageDir, String imageSuffix, BufferedImage image, String id) throws Exception {
		File pngFile = new File(imageDir, id+"."+imageSuffix);
		this.clearVariables();
		createHTMLandSVG(imageDir, imageSuffix, image, id, pngFile);
	}

	public void createHTMLandSVG(File imageDir, String imageSuffix, NamedImage namedImage) throws Exception {
		String id = namedImage.getKey();
		File pngFile = new File(imageDir, id+"."+imageSuffix);
		this.clearVariables();
		createHTMLandSVG(imageDir, imageSuffix, namedImage.getImage(), id, pngFile);
	}

	private void createHTMLandSVG(File imageDir, String imageSuffix, BufferedImage rawImage0, String id, File pngFile) throws Exception {
		BufferedImage expandedImage = addMargins(rawImage0);
		ImageIO.write(expandedImage, imageSuffix, new FileOutputStream(pngFile));
		ImageToHOCRConverter converter = new ImageToHOCRConverter();
		File outfileRoot = new File(imageDir, id+HOCRReader.HOCR);
		File outputHtml = converter.convertImageToHOCR(pngFile, outfileRoot);
		if (outputHtml == null) {
			return;
		}
		readHOCR(new FileInputStream(outputHtml));
		SVGElement svgg = getOrCreateSVG();
		List<HOCRText> potentialTexts = this.getOrCreatePotentialTextElements(svgg);
		List<HOCRLabel> potentialLabels = this.getOrCreatePotentialLabelElements(svgg);
		List<HOCRPhrase> potentialPhrases = this.getOrCreatePotentialPhraseElements(svgg);
		
		GridExtractor gridExtractor = new GridExtractor(new Real2(8., 8.));
		gridExtractor.deduceGrid(potentialLabels);

		SVGSVG.wrapAndWriteAsSVG(svgg, new File(imageDir, id+HOCRReader.HOCR_SVG));
	}

	private BufferedImage addMargins(BufferedImage rawImage) {
		BufferedImage newImage = imageMarginX > 0 || imageMarginY > 0 ? 
				ImageUtil.addBorders(rawImage, imageMarginX, imageMarginY, marginColor) : rawImage;
		return newImage;
	}

	public List<HOCRLabel> getOrCreatePotentialLabelElements(SVGElement svgElement) {
		if (potentialLabelList == null) {
			List <SVGElement> labelledGs = SVGUtil.getQuerySVGElements(
					svgElement, "//*[local-name()='g' and contains(@class, '"+POTENTIAL_LABEL+"')]");
			potentialLabelList = new ArrayList<HOCRLabel>();
			for (SVGElement labelledG : labelledGs) {
				addDecorativeBoxToPotentialLabel(labelledG);
				if (!(labelledG instanceof SVGG)) {
					LOG.error("expected text, found: "+labelledG.toXML());
				}
				potentialLabelList.add(new HOCRLabel((SVGG)labelledG));
			}
		}
		return potentialLabelList;
	}

	
	public List<HOCRPhrase> getOrCreatePotentialPhraseElements(SVGElement svgElement) {
		List <SVGElement> lineGs = SVGUtil.getQuerySVGElements(
				svgElement, "//*[local-name()='g' and contains(@class, '"+LINE+"')]");
		potentialPhraseList = new ArrayList<HOCRPhrase>();
		for (SVGElement lineG : lineGs) {
			List<SVGElement> words = SVGUtil.getQuerySVGElements(
					lineG, "*[local-name()='g' and contains(@class,'"+WORD+"')]");
			List<HOCRPhrase> linePhraseList = new ArrayList<HOCRPhrase>();
			HOCRPhrase previous = null;
			HOCRPhrase currentPhrase = null;
			for (int i = 0; i < words.size(); i++) {
				SVGG word = (SVGG) words.get(i);
				String clazz = word.getAttributeValue(CLASS);
				if (clazz != null && clazz.contains(POTENTIAL_LABEL)) {
					continue;
				}
				HOCRText text = new HOCRText((SVGG)word);
				if (previous != null) {
					joinWords(previous, currentPhrase, word, text);
				} else {
					currentPhrase = new HOCRPhrase(word);
					LOG.trace("new: "+((text == null) ? null : ((text.getText() == null) ? null : (text.getText().getText()))));
					linePhraseList.add(currentPhrase);
					previous = currentPhrase;
					
				}
			}
			for (HOCRPhrase phrase : linePhraseList) {
				LOG.trace(">phrase>"+phrase.toString());
			}
			potentialPhraseList.addAll(linePhraseList);
		}
		return potentialPhraseList;
	}

	private void joinWords(HOCRPhrase previous, HOCRPhrase currentPhrase,
			SVGG word, HOCRText text) {
		Double boxEnd = previous.getBoxEnd();
		LOG.trace(">>"+((previous.getBboxRect() == null) ? null : previous.getBboxRect().toXML()));
		if (boxEnd != null) {
			LOG.trace(">>"+text.getBboxRect().toXML());
			double textStart = text.getBoxStart();
			Double separation = textStart - boxEnd;
			LOG.trace(textStart+" - "+boxEnd);
			if (separation < 0) {
				LOG.error("previous overlaps this");
			} else {
				Double previousSize = previous.getFontSize();
				Double textSize = text.getFontSize();
				Double meanTextSize = HOCRChunk.getMeanSize(previousSize, textSize);
				boolean joinWords = HOCRText.isWordInPhrase(separation, meanTextSize, 0,2);
				if (joinWords) {
					currentPhrase.add(word);
					LOG.trace("added "+text.getText().getText()+" :"+currentPhrase.getText().getText()+":");
					
				} else {
					LOG.trace("didn't add: "+text.getText().getText()+" to "+currentPhrase.getText().getText());
				}
			}
		}
	}

	
	public List<HOCRText> getOrCreatePotentialTextElements(SVGElement svgElement) {
		if (potentialTextList == null) {
			List <SVGElement> textGs = SVGUtil.getQuerySVGElements(
					svgElement, "//*[local-name()='g' and not(contains(@class, '"+POTENTIAL_LABEL+"')) and *[local-name()='text']]");
			potentialTextList = new ArrayList<HOCRText>();
			for (SVGElement textG : textGs) {
				SVGText text = SVGText.extractSelfAndDescendantTexts(textG).get(0);
				potentialTextList.add(new HOCRText((SVGG)textG));
			}
		}
		return potentialTextList;
	}

	private void addDecorativeBoxToPotentialLabel(SVGElement svgElement) {
		Real2Range bbox = svgElement.getBoundingBox();
		SVGRect rect = SVGRect.createFromReal2Range(bbox);
		if (rect != null) {
			rect.setFill("magenta");
			rect.setOpacity(0.2);
			svgElement.appendChild(rect);
		}
	}

//	private void setTesseractSleep(long tesseractSleep) {
//		this.tesseractSleep = tesseractSleep;
//	}
//
//	private void setTesseractTries(int tesseractTries) {
//		this.tesseractTries = tesseractTries;
//	}
//
//	private long getTesseractSleep() {
//		return tesseractSleep;
//	}
//
//	private int getTesseractTries() {
//		return tesseractTries;
//	}

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

	public void setMaxFontSize(double size) {
		this.maxFontSize = size;
	}

	public double getMaxFontSize() {
		return maxFontSize;
	}

	/** attempts to find subimages through labelling.
	 * 
	 * @param regex of form "[A-Za-z0-9]|ii|iii|iv|v|vi|vii|"
	 * still experimental
	 * 
	 */
	public void labelSubImages(String regex) {
		this.labelPattern = Pattern.compile(regex);
	}

	public void setJoiningBox(Real2Range joiningBox) {
		this.setWordJoiningBox(joiningBox);
	}

	public List<SVGWordLine> createWordLineList(File hocrFile) throws IOException, FileNotFoundException {
		readHOCR(new FileInputStream(hocrFile));
		SVGSVG svgSvg = (SVGSVG) getOrCreateSVG();
		return getOrCreateWordLineList(svgSvg);
	}

	public List<SVGPhrase> createPhraseList(File hocrFile) throws IOException, FileNotFoundException {
		createWordLineList(hocrFile);
		return createPhraseList(wordLineList);
	}

	public List<SVGWordLine> getOrCreateWordLineList() {
		SVGSVG svgSvg = (SVGSVG) getOrCreateSVG();
		return getOrCreateWordLineList(svgSvg);
	}

	private List<SVGWordLine> getOrCreateWordLineList(SVGSVG svgSvg) {
		wordLineList = svgSvg.getSingleSVGPage().getSVGLineList();
		for (SVGWordLine wordLine : wordLineList) {
			wordLine.makePhrasesFromWords();
		}
		return wordLineList;
	}
	
	public Real2Range getWordJoiningBox() {
		return wordJoiningBox;
	}

	public void setWordJoiningBox(Real2Range wordJoiningBox) {
		this.wordJoiningBox = wordJoiningBox;
	}

	public List<SVGPhrase> getOrCreatePhraseList() {
		if (allPhraseList == null) {
			this.getOrCreateWordLineList();
			allPhraseList = HOCRReader.createPhraseList(wordLineList);
		}
		return allPhraseList;
	}

	public static List<SVGPhrase> createPhraseList(List<SVGWordLine> wordLineList) {
		List<SVGPhrase> allPhraseList = new ArrayList<SVGPhrase>();
		for (SVGWordLine wordLine : wordLineList) {
			List<SVGPhrase> phrases = wordLine.getOrCreateSVGPhraseList();
			allPhraseList.addAll(phrases);
		}
		return allPhraseList;
	}

}
class HtmlSVG {

	public HtmlElement html;
	public SVGElement svg;
	private boolean isLargeText;
	
	public boolean isLargeText() {
		return isLargeText;
	}

	public void setLargeText(boolean isLargeText) {
		this.isLargeText = isLargeText;
	}

	public HtmlSVG(HtmlElement html, SVGElement svg) {
		this.html = html;
		this.svg = svg;
	}

}
