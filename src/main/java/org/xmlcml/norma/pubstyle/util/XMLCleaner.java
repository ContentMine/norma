package org.xmlcml.norma.pubstyle.util;

import java.io.File;
import java.util.List;

import org.xmlcml.cmine.args.DefaultArgProcessor;
import org.xmlcml.cmine.files.CProject;
import org.xmlcml.norma.NormaArgProcessor;
import org.xmlcml.xml.XMLUtil;

import nu.xom.Element;

public class XMLCleaner {

	private static final String XMLNS_NAMESPACE = "xmlns=\"http://www.w3.org/1999/xhtml\"";
	
	private Element element;
	private CProject cProject;

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

	/**  removes empty div, i, b, p, span, em, etc.
	 * 
	 */
	public void removeCommonEmptyElements() {
		remove("//*[(local-name()='div' "
				+ "       or local-name()='i'"
				+ "       or local-name()='b'"
				+ "       or local-name()='p'"
				+ "       or local-name()='span'"
				+ "       or local-name()='em'"
				+ " ) and normalize-space(text())='']");
	}

	public void removeXMLNSNamespace() {
		String xmlString = this.element.toXML().replaceAll(XMLNS_NAMESPACE, "");
		this.element = XMLUtil.parseXML(xmlString);
	}

	public void setProject(CProject cProject) {
		this.cProject = cProject;
	}

	public void tidyHtmlToXHtml() {
		if (cProject != null && cProject.getDirectory() != null) {
			String args = "--project "+cProject.getDirectory()+" -i fulltext.html -o fulltext.xhtml --html jsoup";
			DefaultArgProcessor argProcessor = new NormaArgProcessor(args); 
			argProcessor.runAndOutput();
		}
	}
	
	
}
