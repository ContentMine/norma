package org.xmlcml.norma.output;

import java.io.File;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.xmlcml.html.HtmlButton;
import org.xmlcml.html.HtmlElement;
import org.xmlcml.html.HtmlTitle;
import org.xmlcml.xml.XMLUtil;

import nu.xom.Element;

/** a ButtonTab representing and linking to an HtmlFile/Element
 * 
 * @author pm286
 *
 */
public class HtmlTabbedButton extends HtmlButton {
	private static final Logger LOG = Logger.getLogger(HtmlTabbedButton.class);
	// this has to be consistent with the openTab() function.
	private static final String TABLINKS = "tablinks";
	static final String TABCONTENT = "tabcontent";
	
	static {
		LOG.setLevel(Level.DEBUG);
	}

	private String idRef;
	private File file;
	
	public HtmlTabbedButton() {
		super();
	}

	public static HtmlTabbedButton createButtonFromHtmlElement(HtmlElement htmlElement) {
		HtmlTabbedButton htmlButtonTab = null;
		if (htmlElement != null) {
			LOG.trace(htmlElement.toXML());
			String id = htmlElement.getId();
			String title = getTitle(htmlElement);
			if (id == null) {
				LOG.error("html element must have (unique) ID");
			} else {
				htmlButtonTab = new HtmlTabbedButton();
				htmlButtonTab.setIdRef(id);
				htmlButtonTab.setTitle(title);
				htmlButtonTab.setClassAttribute(TABLINKS);
			}
		}
		return htmlButtonTab;
	}
	
	private void setIdRef(String id) {
		this.idRef = id;
	}
	
	public String getIdRef() {
		return idRef;
	}

	/*
	 * class="tablinks" onclick="openTab(event, 'table1', 'tabcontent')">Table1</button>
	 */
	public static HtmlTabbedButton createButtonFromHtmlFile(File file, HtmlElement htmlElement) {
		LOG.debug("file "+file.getAbsolutePath());
		HtmlTabbedButton buttonTab = HtmlTabbedButton.createButtonFromHtmlElement(htmlElement);
		if (buttonTab == null) {
			LOG.error("Cannot create buttonTab");
		} else {
			buttonTab.createFromFile(file);
		}
		return buttonTab;
	}

	private void createFromFile(File file) {
		this.file = file;
		this.setClassAttribute(TABLINKS);
		this.setOnClick("openTab(event, '"+this.getIdRef()+"', '" + TABCONTENT + "'"/*, '"+TABLINKS+"'*/+")");
		this.setContent(this.getTitle());

	}

	private static String getTitle(HtmlElement htmlElement) {
		String title = htmlElement.getTitle();
		if (title == null) {
			Element titleElement = htmlElement.getFirstChildElement(HtmlTitle.TAG);
			if (titleElement != null) {
				title = titleElement.getValue();
			}
		}
		if (title == null) {
		}
		return title;
	}
	


}
