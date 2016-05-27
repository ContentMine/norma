package org.xmlcml.norma.pubstyle.util;

import java.io.File;
import java.util.List;

import org.xmlcml.html.HtmlElement;
import org.xmlcml.xml.XMLUtil;

import nu.xom.Element;

public class XMLCleaner {

	private Element element;

	public XMLCleaner() {
		
	}

	public XMLCleaner(Element element) {
		this.element = element;
	}

	public static XMLCleaner createCleaner(File file) {
		Element element = XMLUtil.parseQuietlyToDocument(file).getRootElement();
		return element == null ? null : new XMLCleaner(element);
	}

	public boolean remove(String xpath) {
		boolean removed = false;
		List<Element> elements = XMLUtil.getQueryElements(element, xpath);
		if (elements.size() != 0) {
			for (Element element : elements) {
				element.detach();
			}
			removed = true;
		}
		return removed;
	}
	
	public boolean removeRecursively(String xpath) {
		boolean removed = false;
		while (true) {
			if (!remove(xpath)) break;
			removed = true;
		}
		return removed;
	}

	public Element getElement() {
		return element;
	}
}
