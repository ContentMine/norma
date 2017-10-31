package org.xmlcml.norma.output;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.xmlcml.euclid.Util;
import org.xmlcml.graphics.html.HtmlDiv;
import org.xmlcml.graphics.html.HtmlElement;
import org.xmlcml.graphics.html.HtmlH1;
import org.xmlcml.graphics.html.HtmlHtml;
import org.xmlcml.graphics.html.HtmlScript;
import org.xmlcml.graphics.html.HtmlStyle;
import org.xmlcml.norma.Norma;
import org.xmlcml.xml.XMLUtil;

import nu.xom.Attribute;
import nu.xom.Element;

/** creates a tabbed display for multiple HTML files using buttons
 * 
 * The HTML files MUST have:
 * a unique ID
 * a class attribute on the content to be displayed
 * and SHOULD have a unique title attribute
 * 
 * Example:
 * <div id="table1" class="tabcontent" title="Table 1">
     <table xmlns="http://www.w3.org/1999/xhtml">
     ...
     </table>
   </div>
 * 
 * I believe they can have any displayable HTML content
 * 
 * the tabDisplay must have a title or titleElement to be displayed above the tabs
 * 
 * For each file the class constructs:
 *  a list of buttons linked to the content files
 *  titles from the content using getTitle()
 *  
<h1>title (e.g. 10.1016_j.pain.2014.08.023)</h1>
 <div class="tab">
  <button class="tablinks" onclick="openTab(event, 'table1', 'tabcontent')">title (e.g. Table1)</button>
  <button class="tablinks" onclick="openTab(event, 'table2', 'tabcontent')">Table2</button>
  <button class="tablinks" onclick="openTab(event, 'table3', 'tabcontent')">Table3</button>
  <button class="tablinks" onclick="openTab(event, 'table4', 'tabcontent')">Table4</button>
 </div>

getTitle() will look for (in order)
 title attribute
 title element
 id

(Not yet fully tested)

 *  
 * 
 * 
 * The tool 
 * 
 * @author pm286
 *
 */
public class HtmlTabbedButtonDisplay extends HtmlHtml {
	private static final Logger LOG = Logger.getLogger(HtmlTabbedButtonDisplay.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}

	
//	public final static String BUTTON_STYLE_RESOURCE = Norma.NORMA_OUTPUT_RESOURCE+"/"+"tabButton.css.xml"; 
//	public final static String BUTTON_SCRIPT_RESOURCE = Norma.NORMA_OUTPUT_RESOURCE+"/"+"tabButton.js.xml";
	public final static String BUTTON_STYLE_RESOURCE = Norma.NORMA_OUTPUT_RESOURCE+"/"+"tabButton.css"; 
	public final static String BUTTON_SCRIPT_RESOURCE = Norma.NORMA_OUTPUT_RESOURCE+"/"+"tabButton.js";
	
	private static final String TAB = "tab";
	private static final String TABCONTENTDIV = "tabcontentdiv";
	
	private static HtmlStyle readButtonStyle(String resource) {
		HtmlStyle htmlStyle = null;
		String buttonStyleContent = readStringContent(resource);
		if (buttonStyleContent != null) {
			htmlStyle = new HtmlStyle();
			htmlStyle.addCss(buttonStyleContent);
		}
		return htmlStyle;
	}

	private static HtmlElement readHtmlElement(String resource) {
		Element element = readXMLContent(resource);
		HtmlElement htmlElement = HtmlElement.create(element);
		return htmlElement;
	}

	private static HtmlScript readButtonScript(String resource) {
		HtmlScript htmlScript = null;
		String buttonScriptContent = readStringContent(resource);
		if (buttonScriptContent != null) {
			htmlScript = new HtmlScript();
//			htmlScript.appendChild(buttonScriptContent);
			htmlScript.setSrc(resource); // this will be a placeholder
			htmlScript.setType(HtmlScript.TEXT_JAVASCRIPT);
		}
		return htmlScript;
	}

	private static String readStringContent(String resource) {
		String buttonStyleContent = null;
		InputStream buttonStyleStream = HtmlTabbedButtonDisplay.class.getResourceAsStream(resource);
		if (buttonStyleStream == null) {
			throw new RuntimeException("null input stream: "+resource);
		}
		try {
			buttonStyleContent = IOUtils.toString(buttonStyleStream);
		} catch (IOException e) {
			LOG.debug("Cannot read "+resource+" in "+HtmlTabbedButtonDisplay.class+"; "+e.getMessage());
		}
		return buttonStyleContent;
	}
	
	private static Element readXMLContent(String resource) {
		Element xmlContent = null;
		InputStream stream = HtmlTabbedButtonDisplay.class.getResourceAsStream(resource);
		xmlContent = XMLUtil.parseQuietlyToDocument(stream).getRootElement();
		return xmlContent;
	}
	
	private String title;
	private File parentFile;
	
	public HtmlTabbedButtonDisplay() {
		
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	/** add files to be displayed.
	 * 
	 * @param htmlFiles
	 */
	public HtmlTabbedButtonDisplay(String title, List<File> htmlFiles, File parentFile) {
		this.title = title;
		this.parentFile = parentFile;
		createButtonsFromHtmlFiles(htmlFiles);
	}

	/** add files to be displayed.
	 * 
	 * @param files
	 */
	public void createButtonsFromHtmlFiles(List<File> files) {
		getOrCreateH1Title();
		getOrCreateScript();
//		LOG.debug("SCRIPT "+this.getOrCreateHead().getOrCreateScript().toXML());
		getOrCreateStyle();
//		LOG.debug("STYLE "+this.getOrCreateHead().getOrCreateHtmlStyle().toXML());
		HtmlDiv tabDiv = getTabDiv();
		if (tabDiv == null) {
			tabDiv = getOrCreateTabDiv();
			getOrCreateBody().appendChild(tabDiv);
		}
		HtmlDiv contentDiv = getContentDiv();
		if (contentDiv == null) {
			contentDiv = getOrCreateContentDiv();
			getOrCreateBody().appendChild(contentDiv);
		}
		for (File file : files) {
			File fileParentFile = file.getParentFile();
			if (fileParentFile == null || parentFile == null) {
				continue;
			}
			String relative = Util.getRelativeFilename(parentFile, fileParentFile, null);
			HtmlElement htmlElement = HtmlElement.create(XMLUtil.parseQuietlyToDocument(file).getRootElement());
			offsetImgSrcPaths(htmlElement, relative);
			HtmlTabbedButton htmlButtonTab = HtmlTabbedButton.createButtonFromHtmlFile(file, htmlElement);
			if (htmlButtonTab != null) {
				tabDiv.appendChild(htmlButtonTab);
			}
			
			List<Element> tabElements = XMLUtil.getQueryElements(
					htmlElement, "//*[@class='"+HtmlTabbedButton.TABCONTENT+"']");
			HtmlElement contentElement = tabElements == null || tabElements.size() == 0 ? null : (HtmlElement) tabElements.get(0);
			if (contentElement == null) {
				contentElement = htmlElement;
			}
			contentDiv.appendChild(contentElement);
		}
	}

	/** horrible kludge when aggregated compaonents are at the wrong level. Maybe later record actual files hierachies
	 * 
	 * @param relative
	 */
	private void offsetImgSrcPaths(HtmlElement htmlElement, String relative) {
		List<Element> elements = XMLUtil.getQueryElements(htmlElement, ".//*[@src]");
		for (Element element : elements) {
			Attribute srcAttribute = element.getAttribute("src");
			String attValue = srcAttribute.getValue();
			attValue = relative+"/"+attValue;
			srcAttribute.setValue(attValue);
		}
		
	}

	public HtmlElement getOrCreateH1Title() {
		HtmlH1 h1 = null;
		Element element = XMLUtil.getSingleElement(getOrCreateBody(), ".//*[local-name()='"+HtmlH1.TAG+"']");
		if (element == null) {
			h1 = new HtmlH1();
			h1.setContent(title);
			getOrCreateBody().appendChild(h1);
		} else {
			h1 = (HtmlH1) HtmlElement.create(element); 
		}
		
		return h1;
	}

	private HtmlDiv getOrCreateTabDiv() {
		HtmlDiv tabDiv = getTabDiv();
		if (tabDiv == null) {
			tabDiv = new HtmlDiv();
			tabDiv.setClassAttribute(TAB);
		}
		return tabDiv;
	}

	private HtmlDiv getTabDiv() {
		Element element = XMLUtil.getSingleElement(getOrCreateBody(), ".//*[local-name()='"+HtmlDiv.TAG+"' and @class='"+TAB+"']");
		return element == null ? null : (HtmlDiv) HtmlElement.create(element);
	}

	private HtmlDiv getOrCreateContentDiv() {
		HtmlDiv contentDiv = getContentDiv();
		if (contentDiv == null) {
			contentDiv = new HtmlDiv();
			contentDiv.setClassAttribute(TABCONTENTDIV);
		}
		return contentDiv;
	}

	private HtmlDiv getContentDiv() {
		Element element = XMLUtil.getSingleElement(getOrCreateBody(), 
				".//*[local-name()='"+HtmlDiv.TAG+"' and @class='"+TABCONTENTDIV+"']");
		return element == null ? null : (HtmlDiv) HtmlElement.create(element);
	}

	/* this is crude - only one variant.
	 * 
	 */
	private HtmlScript getOrCreateScript() {
		HtmlScript script = null;
		Element element = XMLUtil.getSingleElement(getOrCreateHead(), "./*[local-name()='"+HtmlScript.TAG+"']");
		if (element != null) {
			script = (HtmlScript) HtmlElement.create(element);
		} else {
//			HtmlScript buttonScript = (HtmlScript) readHtmlElement(BUTTON_SCRIPT_RESOURCE);
			HtmlScript buttonScript = readButtonScript(BUTTON_SCRIPT_RESOURCE);
			LOG.debug(buttonScript.getValue());
			getOrCreateHead().appendChild(buttonScript);
			LOG.debug("BUTTON "+getOrCreateHead().getOrCreateScript().getValue());
			
		}
		return script;
	}
	
	/* this is crude - only one variant.
	 * 
	 */
	private HtmlStyle getOrCreateStyle() {
		HtmlStyle style = null;
		Element element = XMLUtil.getSingleElement(getOrCreateHead(), "./*[local-name()='"+HtmlStyle.TAG+"']");
		if (element != null) {
			style = (HtmlStyle) HtmlElement.create(element);
		} else {
			HtmlStyle buttonStyle = readButtonStyle(BUTTON_STYLE_RESOURCE);
//			HtmlStyle buttonStyle = (HtmlStyle) readHtmlElement(BUTTON_STYLE_RESOURCE);
			LOG.debug("STYLE "+buttonStyle.toXML());
			getOrCreateHead().appendChild(buttonStyle);
		}
		return style;
	}
	
}