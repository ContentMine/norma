package org.xmlcml.norma.sections;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.xmlcml.html.HtmlA;
import org.xmlcml.html.HtmlB;
import org.xmlcml.html.HtmlBody;
import org.xmlcml.html.HtmlCol;
import org.xmlcml.html.HtmlColgroup;
import org.xmlcml.html.HtmlDiv;
import org.xmlcml.html.HtmlElement;
import org.xmlcml.html.HtmlHead;
import org.xmlcml.html.HtmlHtml;
import org.xmlcml.html.HtmlI;
import org.xmlcml.html.HtmlP;
import org.xmlcml.html.HtmlStyle;
import org.xmlcml.html.HtmlSub;
import org.xmlcml.html.HtmlSup;
import org.xmlcml.html.HtmlTable;
import org.xmlcml.html.HtmlTbody;
import org.xmlcml.html.HtmlTd;
import org.xmlcml.html.HtmlTh;
import org.xmlcml.html.HtmlThead;
import org.xmlcml.html.HtmlTr;
import org.xmlcml.xml.XMLUtil;

import nu.xom.Element;
import nu.xom.Node;

public class JATSFactory {

	private static final Logger LOG = Logger.getLogger(JATSFactory.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}

	public final static String P = "p";
	public final static String SEC = "sec";
	public final static List<String> STRUCTURE_LIST = Arrays.asList(
		new String[] {
				P,
				SEC,
		}
		);

	public final static String BOLD = "bold";
	public final static String EM = "em";
	public final static String ITALIC = "italic";
	public final static List<String> STYLE_LIST = Arrays.asList(
		new String[] {
				BOLD,
				EM,
				ITALIC,
		}
		);

	public final static String XREF = "xref";
	
	private HtmlHtml htmlElement;
	private JATSDivFactory divFactory;
	private JATSSpanFactory spanFactory;
	

	public JATSFactory() {
		divFactory = new JATSDivFactory(this);
		spanFactory = new JATSSpanFactory(this);
	}

	public HtmlElement createHtml(Element element) {
		htmlElement = new HtmlHtml();
		htmlElement.appendChild(createHead());
		HtmlBody body = new HtmlBody();
		Element bodyContent = create(element);
		body.appendChild(bodyContent);
		htmlElement.appendChild(body);
		return htmlElement;
		
		
	}

	private HtmlHead createHead() {
		HtmlHead head = new HtmlHead();
		HtmlStyle htmlStyle = new HtmlStyle();
		String divCssStyle = "div {border-style : solid;}";
		htmlStyle.addCss(divCssStyle);
		String spanCssStyle = 
		  "div {border-style : solid;} \n"+
		  "div.contrib {background : #ddf; display : inline;} \n"+
		  "div.contrib-group {background : #ddf; display : inline;} \n"+
		  "span {background : pink; margin : 1pt;} \n"+
		  "div.addr-line {display : inline ; background : cyan;} \n"+
		  "div.name {display : inline ; background : yellow;} \n"+
		  "div.name:before {content : \"name: \";} \n"+
		  "div.aff {display : inline ; background : green;} \n"+
		  "div.aff:before {content : \"aff: \";} \n"+
		  /** pub-date
		   * 	<pub-date pub-type="epub">
	<day>28</day>
	<month>2</month>
	<year>2012</year>
	</pub-date>

		  */
		  "div.pub-date {background : #eff;} \n"+
		  "div.pub-date:before {content : \"XXX \"attr(pub-type)\": \";} \n"+
		  "";
		htmlStyle.addCss(spanCssStyle);
		head.appendChild(htmlStyle);
		return head;
	}

	/** creates subclassed elements.
	 * 
	 * 
	 * @param element
	 * @return
	 */
	public Element create(Element element) {
		Element sectionElement = null;
		String tag = element.getLocalName();
		String namespaceURI = element.getNamespaceURI();
		if (false) {
			
		} else if(JATSArticleElement.TAG.equals(tag)) {
			sectionElement = new JATSArticleElement(element);
			((JATSElement)sectionElement).recurse(element, this);
			
		} else if(JATSJournalTitleGroupElement.TAG.equals(tag)) {
			sectionElement = new JATSJournalTitleGroupElement(element);
			((JATSElement)sectionElement).recurse(element, this);
			
		} else if(JATSArticleMetaElement.TAG.equals(tag)) {
			sectionElement = new JATSArticleMetaElement(element);
			((JATSElement)sectionElement).recurse(element, this);
			
		} else if(JATSJournalMetaElement.TAG.equals(tag)) {
			sectionElement = new JATSJournalMetaElement(element);
			((JATSElement)sectionElement).recurse(element, this);
			
		} else if(JATSFnGroupElement.TAG.equals(tag)) {
			sectionElement = new JATSFnGroupElement(element);
			((JATSElement)sectionElement).recurse(element, this);
			
		} else if(JATSFnElement.TAG.equals(tag)) {
			sectionElement = new JATSFnElement(element);
			((JATSElement)sectionElement).recurse(element, this);
			
		} else if(JATSSecElement.TAG.equals(tag)) {
			sectionElement = new JATSSecElement(element);
			((JATSElement)sectionElement).recurse(element, this);
			
		} else if(JATSFrontElement.TAG.equals(tag)) {
			sectionElement = new JATSFrontElement(element);
			((JATSElement)sectionElement).recurse(element, this);
			
		} else if(JATSBodyElement.TAG.equals(tag)) {
			sectionElement = new JATSBodyElement(element);
			((JATSElement)sectionElement).recurse(element, this);
			
		} else if(JATSBackElement.TAG.equals(tag)) {
			sectionElement = new JATSBackElement(element);
			((JATSElement)sectionElement).recurse(element, this);
			
		} else if(JATSContribGroupElement.TAG.equals(tag)) {
			sectionElement = new JATSContribGroupElement(element);
			((JATSElement)sectionElement).recurse(element, this);
			
		} else if(JATSContribElement.TAG.equals(tag)) {
			sectionElement = new JATSContribElement(element);
			((JATSElement)sectionElement).recurse(element, this);
			
		} else if(JATSPubDateElement.TAG.equals(tag)) {
			sectionElement = new JATSPubDateElement(element);
			((JATSElement)sectionElement).recurse(element, this);
			
		} else if(JATSDateElement.TAG.equals(tag)) {
			sectionElement = new JATSDateElement(element);
			((JATSElement)sectionElement).recurse(element, this);
			
		} else if(JATSHistoryElement.TAG.equals(tag)) {
			sectionElement = new JATSHistoryElement(element);
			((JATSElement)sectionElement).recurse(element, this);
			
		} else if(JATSPersonGroupElement.TAG.equals(tag)) {
			sectionElement = new JATSPersonGroupElement(element);
			((JATSElement)sectionElement).recurse(element, this);
			
		} else if(JATSElementCitationElement.TAG.equals(tag)) {
			sectionElement = new JATSElementCitationElement(element);
			((JATSElement)sectionElement).recurse(element, this);
			
		} else if(JATSReflistElement.TAG.equals(tag)) {
			sectionElement = new JATSReflistElement(element);
			((JATSElement)sectionElement).recurse(element, this);
			
		} else if(JATSArticleIdElement.TAG.equals(tag)) {
			sectionElement = new JATSArticleIdElement(element);
			((JATSElement)sectionElement).recurse(element, this);
			
		} else if(JATSArticleMetaElement.TAG.equals(tag)) {
			sectionElement = new JATSArticleMetaElement(element);
			((JATSElement)sectionElement).recurse(element, this);
			
		} else if(JATSAffElement.TAG.equals(tag)) {
			sectionElement = new JATSAffElement(element);
			((JATSElement)sectionElement).recurse(element, this);
			
		} else if(JATSNameElement.TAG.equals(tag)) {
			sectionElement = new JATSNameElement(element);
			((JATSElement)sectionElement).recurse(element, this);
			
		} else if(JATSRefElement.TAG.equals(tag)) {
			sectionElement = new JATSRefElement(element);
			((JATSElement)sectionElement).recurse(element, this);
			LOG.trace(((JATSRefElement)sectionElement).getPMID());
			
		} else if(JATSSpanFactory.isSpan(tag)) {
			sectionElement = spanFactory.setElement(element).createAndRecurse();
			
		} else if(JATSDivFactory.isDiv(tag)) {
			sectionElement = divFactory.setElement(element).createAndRecurse();
			
		} else if(isXref(tag)) {
			sectionElement = createXref(tag, element);
			
		} else if(isStructure(tag)) {
			sectionElement = createHtml(tag, element);
			
		} else if(isStyle(tag)) {
			sectionElement = createHtml(tag, element);
			
		} else if(isTable(tag)) {
			sectionElement = createTable(tag, element);
			
		} else if(isHtml(tag)) {
			sectionElement = createHtml(tag, element);
			
		} else {
			String msg = null;			
			if (element.getChildElements().size() == 0) {
				msg = "Unknown JATS Span "+tag;
				sectionElement = spanFactory.setElement(element).createAndRecurse();
			} else {
				msg = "Unknown JATS Div "+tag;
				sectionElement = divFactory.setElement(element).createAndRecurse();
			}
			if (msg != null) {
				LOG.warn(msg);
			}
		}
		if (sectionElement == null) {
			LOG.warn("NULL SECTION");
		}

		return sectionElement;
		
	}

	/**
	 * 
	 *        
       <div ref-type="aff" rid="aff1" class="xref">
        <sup>1</sup>
       </div>

	 * @param tag
	 * @param element
	 * @return
	 */
	private Element createXref(String tag, Element element) {
		HtmlA htmlA = null;
		
		if (XREF.equals(tag)) {
			htmlA = new HtmlA();
			if (element.getValue().trim().length() == 0) {
				htmlA.setValue("*");
			} else {
				processAttributesAndChildren(element, htmlA);
			}
			htmlA.setHref("#"+element.getAttributeValue("rid"));
		}
		return htmlA;
	}
	

	private Element createHtml(String tag, Element element) {
		Element htmlElement = null;
		
		if (BOLD.equals(tag)) {
			htmlElement = new HtmlB();
		} else if (EM.equals(tag)) {
			htmlElement = new HtmlI();
		} else  if (ITALIC.equals(tag)) {
			htmlElement = new HtmlI();
		} else  if (HtmlSub.TAG.equals(tag)) {
			htmlElement = new HtmlSub();
		} else  if (HtmlSup.TAG.equals(tag)) {
			htmlElement = new HtmlSup();
			
		} else  if (SEC.equals(tag)) {
			htmlElement = new HtmlDiv();
		} else  if (P.equals(tag)) {
			htmlElement = new HtmlP();
		}
		if (htmlElement == null) {
			LOG.warn("NULL HTML: "+element);
		}
		processAttributesAndChildren(element, htmlElement);
		return htmlElement;
	}

	private Element createTable(String tag, Element element) {
		Element htmlElement = null;
		
		if (HtmlTable.TAG.equals(tag)) {
			htmlElement = new HtmlTable();
		} else if (HtmlTbody.TAG.equals(tag)) {
			htmlElement = new HtmlTbody();
		} else if (HtmlCol.TAG.equals(tag)) {
			htmlElement = new HtmlCol();
		} else if (HtmlColgroup.TAG.equals(tag)) {
			htmlElement = new HtmlColgroup();
		} else if (HtmlTh.TAG.equals(tag)) {
			htmlElement = new HtmlTh();
		} else if (HtmlThead.TAG.equals(tag)) {
			htmlElement = new HtmlThead();
		} else if (HtmlTd.TAG.equals(tag)) {
			htmlElement = new HtmlTd();
		} else if (HtmlTr.TAG.equals(tag)) {
			htmlElement = new HtmlTr();
		}
		if (htmlElement == null) {
			LOG.warn("NULL TABLE");
		}
		processAttributesAndChildren(element, htmlElement);
		return htmlElement;
	}

	private void processAttributesAndChildren(Element element, Element htmlElement) {
		if (htmlElement != null) {
			XMLUtil.copyAttributes(element, htmlElement);
			processChildren(element, htmlElement);
		}
	}

	private static boolean isXref(String tag) {
		return 
				
		XREF.equals(tag) 
		;
		
	}
	
	private static boolean isHtml(String tag) {
		return 
				
		HtmlSub.TAG.equals(tag) ||
		HtmlSup.TAG.equals(tag) 
		;
		
	}

	private static boolean isTable(String tag) {
		return 
				
				HtmlCol.TAG.equals(tag) ||
				HtmlColgroup.TAG.equals(tag) ||
				HtmlTable.TAG.equals(tag) ||
				HtmlTbody.TAG.equals(tag) ||
				HtmlTd.TAG.equals(tag) ||
				HtmlTh.TAG.equals(tag) ||
				HtmlThead.TAG.equals(tag) ||
				HtmlTr.TAG.equals(tag)
				;
	}

	private static boolean isStructure(String tag) {
		return STRUCTURE_LIST.contains(tag);
	}

	private static boolean isStyle(String tag) {
		return STYLE_LIST.contains(tag);
	}

	void processChildren(Element element, Element sectionElement) {
		for (int i = 0; i < element.getChildCount(); i++) {
			Node child = element.getChild(i);
			if (child instanceof Element) {
				Element jatsChild = this.create((Element)child);
				if (jatsChild == null) {
					LOG.warn("NULL "+((Element)child).getLocalName());
				} else if (sectionElement != null) {	
					sectionElement.appendChild(jatsChild);
				}
			} else {
				if (sectionElement != null) {
					sectionElement.appendChild(child.copy());
				}
			}
		}
	}

}
