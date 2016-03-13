package org.xmlcml.norma.sections;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.xmlcml.html.HtmlElement;
import org.xmlcml.html.HtmlTable;
import org.xmlcml.html.HtmlTd;
import org.xmlcml.html.HtmlTr;
import org.xmlcml.xml.XMLUtil;

import nu.xom.Attribute;
import nu.xom.Element;
import nu.xom.Node;

public class JATSElement extends Element {

	private static final Logger LOG = Logger.getLogger(JATSElement.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}
	
	public JATSElement(Element element) {
		super(element.getLocalName());
	}
	
	public JATSElement(String tag) {
		super(tag);
	}

	/** creates subclassed elements.
	 * 
	 * 
	 * @param element
	 * @return
	 */
	public static Element create(Element element) {
//		JATSElement sectionElement = null;
		Element sectionElement = null;
		String tag = element.getLocalName();
		String namespaceURI = element.getNamespaceURI();
		if (false) {
		} else if(JATSPublisherElement.matches(element)) {
			sectionElement = new JATSPublisherElement(element);
		} else if(JATSTableElement.matches(element)) {
			sectionElement = new JATSTableElement(element);
		} else if(JATSAbbreviationElement.matches(element)) {
			sectionElement = new JATSAbbreviationElement(element);
		} else if(JATSAbstractElement.matches(element)) {
			sectionElement = new JATSAbstractElement(element);
		} else if(JATSAcknowledgementElement.matches(element)) {
			sectionElement = new JATSAcknowledgementElement(element);
		} else if(JATSAppendixElement.matches(element)) {
			sectionElement = new JATSAppendixElement(element);
		} else if(JATSArticleMetaElement.matches(element)) {
			sectionElement = new JATSArticleMetaElement(element);
		} else if(JATSAuthorContribElement.matches(element)) {
			sectionElement = new JATSAuthorContribElement(element);
		} else if(JATSAuthorMetaElement.matches(element)) {
			sectionElement = new JATSAuthorMetaElement(element);
		} else if(JATSBackMatterElement.matches(element)) {
			sectionElement = new JATSBackMatterElement(element);
		} else if(JATSCaseStudyElement.matches(element)) {
			sectionElement = new JATSCaseStudyElement(element);
		} else if(JATSConclusionsElement.matches(element)) {
			sectionElement = new JATSConclusionsElement(element);
		} else if(JATSConflictsElement.matches(element)) {
			sectionElement = new JATSConflictsElement(element);
		} else if(JATSDiscussionElement.matches(element)) {
			sectionElement = new JATSDiscussionElement(element);
		} else if(JATSElementCitationElement.matches(element)) {
			sectionElement = new JATSElementCitationElement(element);
		} else if(JATSFigureElement.matches(element)) {
			sectionElement = new JATSFigureElement(element);
		} else if(JATSFinancialSupportElement.matches(element)) {
			sectionElement = new JATSFinancialSupportElement(element);
		} else if(JATSFrontMatterElement.matches(element)) {
			sectionElement = new JATSFrontMatterElement(element);
		} else if(JATSFrontMatterElement.matches(element)) {
			sectionElement = new JATSFrontMatterElement(element);
		} else if(JATSGivenNamesElement.matches(element)) {
			sectionElement = new JATSGivenNamesElement(element);
		} else if(JATSJournalMetaElement.matches(element)) {
			sectionElement = new JATSJournalMetaElement(element);
		} else if(JATSKeywordElement.matches(element)) {
			sectionElement = new JATSKeywordElement(element);
		} else if(JATSMethodsElement.matches(element)) {
			sectionElement = new JATSMethodsElement(element);
		} else if(JATSNameElement.matches(element)) {
			sectionElement = new JATSNameElement(element);
		} else if(JATSOtherElement.matches(element)) {
			sectionElement = new JATSOtherElement(element);
		} else if(JATSPersonGroupElement.matches(element)) {
			sectionElement = new JATSPersonGroupElement(element);
		} else if(JATSReflistElement.matches(element)) {
			sectionElement = new JATSReflistElement(element);
		} else if(JATSRefElement.matches(element)) {
			sectionElement = new JATSRefElement(element);
		} else if(JATSResultsElement.matches(element)) {
			sectionElement = new JATSResultsElement(element);
		} else if(JATSSurnameElement.matches(element)) {
			sectionElement = new JATSSurnameElement(element);
		} else if(JATSSupplementalElement.matches(element)) {
			sectionElement = new JATSSupplementalElement(element);
		} else if(JATSTitleElement.matches(element)) {
			sectionElement = new JATSTitleElement(element);
		} else if(JATSVolumeElement.matches(element)) {
			sectionElement = new JATSVolumeElement(element);
		} else if(JATSXrefElement.matches(element)) {
			sectionElement = new JATSXrefElement(element);
		} else if(JATSYearElement.matches(element)) {
			sectionElement = new JATSYearElement(element);
		} else if(
				"etal".equals(tag) ||
				"fpage".equals(tag) ||
				"lpage".equals(tag) ||
				"label".equals(tag) ||
				"pub-id".equals(tag) ||
				"source".equals(tag)
			) {
			sectionElement = new JATSSpanElement(element);
			
		} else if(
				"".equals(tag) ||
				"fpage".equals(tag) ||
				"lpage".equals(tag) ||
				"label".equals(tag) ||
				"pub-id".equals(tag) ||
				"source".equals(tag)
			) {
			sectionElement = new JATSDivElement(element);

			// HTML
		} else if("th".equals(tag) ||
				"tr".equals(tag) ||
				"table".equals(tag) ||
				"td".equals(tag)
				) {
			sectionElement = HtmlElement.create(element);
			
		} else {
			String msg = "Unknown JATS tag "+tag;
			LOG.trace(msg);
			sectionElement = addUnknownTag(namespaceURI,tag);
		}
		XMLUtil.copyAttributes(element, sectionElement);
		for (int i = 0; i < element.getChildCount(); i++) {
			Node child = element.getChild(i);
			if (child instanceof Element) {
				Element jatsChild = JATSElement.create((Element)child);
				if (sectionElement != null) {	
					sectionElement.appendChild(jatsChild);
				}
			} else {
				if (sectionElement != null) {
					sectionElement.appendChild(child.copy());
				}
			}
		}
		return sectionElement;
		
	}

	private static JATSElement addUnknownTag(String namespaceURI, String tag) {
		JATSElement htmlElement;
		htmlElement = new JATSDivElement();
		htmlElement.addAttribute(new Attribute("class", namespaceURI+"_"+tag));
		return htmlElement;
	}
}
