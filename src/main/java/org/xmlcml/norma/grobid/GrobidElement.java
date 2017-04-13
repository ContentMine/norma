package org.xmlcml.norma.grobid;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.xmlcml.html.HtmlA;
import org.xmlcml.html.HtmlBody;
import org.xmlcml.html.HtmlDiv;
import org.xmlcml.html.HtmlElement;
import org.xmlcml.html.HtmlLi;
import org.xmlcml.html.HtmlP;
import org.xmlcml.html.HtmlSpan;
import org.xmlcml.html.HtmlUl;
import org.xmlcml.xml.XMLUtil;

import nu.xom.Comment;
import nu.xom.Element;
import nu.xom.Node;
import nu.xom.ProcessingInstruction;
import nu.xom.Text;

public class GrobidElement extends Element {

	public static final String COORDS = "coords";
	private static final Logger LOG = Logger.getLogger(GrobidElement.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}
	public final static String TAG = "grobidElement";
	
	public final static String TEI_ABSTRACT_TAG = "abstract";
	public final static String TEI_ANALYTIC_TAG = "analytic";
	public final static String TEI_AUTHOR_TAG = "author";
	public final static String TEI_BACK_TAG = "back";
	public final static String TEI_BIBL_SCOPE_TAG = "biblScope";
	public final static String TEI_BIBL_STRUCT_TAG = "biblStruct";
	public final static String TEI_BODY_TAG = "body";
	public final static String TEI_DIV_TAG = "div";
	public final static String TEI_ENCODING_TAG = "encodingDesc";
	public final static String TEI_FIGURE_TAG = "figure";
	public final static String TEI_FIGDESC_TAG = "figDesc";
	public final static String TEI_FILE_TAG = "fileDesc";
	public final static String TEI_FORENAME_TAG = "forename";
	public final static String TEI_HEADER_TAG = "teiHeader";
	public final static String TEI_HEAD_TAG = "head";
	public final static String TEI_LIST_BIBL_TAG = "listBibl";
	public final static String TEI_NOTE_TAG = "note";
	public final static String TEI_P_TAG = "p";
	public final static String TEI_PERS_NAME_TAG = "persName";
	public final static String TEI_PROFILE_TAG = "profileDesc";
	public final static String TEI_REF_TAG = "ref";
	public final static String TEI_SURNAME_TAG = "surname";
	public final static String TEI_TABLE_TAG = "table";
	public final static String TEI_TEI_TAG = "TEI";
	public final static String TEI_TEXT_TAG = "text";
	
	public final static Set<String> SPANSET = new HashSet<String>();
	
	static {
		SPANSET.add(TEI_AUTHOR_TAG);
		SPANSET.add(TEI_FORENAME_TAG);
		SPANSET.add(TEI_PERS_NAME_TAG);
		SPANSET.add(TEI_SURNAME_TAG);
	}
	public GrobidElement() {
		super(TAG);
	}

	public GrobidElement(String tag) {
		super(tag);
	}
	
	public static GrobidTEIElement readTEI(File grobidTEIFile) {
		Element element = XMLUtil.parseQuietlyToDocument(grobidTEIFile).getRootElement();
		return (GrobidTEIElement) createElement(element);
	}
	
	private static GrobidElement createElement(Element element) {
		GrobidElement grobidElement = null;
		if (element == null) {
			return grobidElement;
		} 
		String coords = element.getAttributeValue(COORDS);
		String tag = element.getLocalName();
		if (tag.equals(GrobidTEIElement.TAG)) {
			grobidElement = new GrobidTEIElement();
		} else if (tag.equals(GrobidBiblStructElement.TAG)) {
			grobidElement = new GrobidBiblStructElement();
		} else if (tag.equals(GrobidFigDescElement.TAG)) {
			grobidElement = new GrobidFigDescElement();
		} else if (tag.equals(GrobidFigureElement.TAG)) {
			grobidElement = new GrobidFigureElement();
		} else if (tag.equals(GrobidRefElement.TAG)) {
			grobidElement = new GrobidRefElement();
		} else if (tag.equals(GrobidTableElement.TAG)) {
			grobidElement = new GrobidTableElement();
		} else if (coords != null) {
			LOG.debug("COORD "+tag);
			grobidElement = new GrobidElement(tag);
		} else {
			grobidElement = new GrobidElement(tag);
			LOG.trace("UNK "+tag);
		}
		if (grobidElement != null) {
			XMLUtil.copyAttributesFromTo(element, grobidElement);
	        createSubclassedChildren(element, grobidElement);
		}
		return grobidElement;
	}
	
	protected static void createSubclassedChildren(Element oldElement, GrobidElement newElement) {
		if (oldElement != null) {
			for (int i = 0; i < oldElement.getChildCount(); i++) {
				Node node = oldElement.getChild(i);
				Node newNode = null;
				if (node instanceof Text) {
					String value = node.getValue();
					newNode = new Text(value);
				} else if (node instanceof Comment) {
					newNode = new Comment(node.getValue());
				} else if (node instanceof ProcessingInstruction) {
					newNode = new ProcessingInstruction((ProcessingInstruction) node);
				} else if (node instanceof Element) {
					newNode = createElement((Element) node);
				} else {
					throw new RuntimeException("Cannot create new node: "+node.getClass());
				}
				newElement.appendChild(newNode);
			}
		}
	}

	public HtmlElement createHTML() {
		String tag = this.getLocalName();
		HtmlElement htmlElement = null;
		if (false) {
		} else if (tag.equals(TEI_BACK_TAG)) {
			htmlElement = new HtmlDiv(); 
			htmlElement.setClassAttribute(tag);
		} else if (tag.equals(TEI_BIBL_SCOPE_TAG)) {
			htmlElement = processBiblScope();
		} else if (tag.equals(TEI_BIBL_STRUCT_TAG)) {
			htmlElement = new HtmlLi();
			htmlElement.setClassAttribute(tag);
			HtmlA a = new HtmlA();
			String id = this.getAttributeValue("id", "http://www.w3.org/XML/1998/namespace");
			if (id != null) {
				a.setName(id);
				htmlElement.appendChild(a);
			}
		} else if (tag.equals(TEI_BODY_TAG)) {
			htmlElement = new HtmlBody(); 
		} else if (tag.equals(TEI_DIV_TAG)) {
			htmlElement = new HtmlDiv(); 
		} else if (tag.equals(TEI_LIST_BIBL_TAG)) {
			htmlElement = new HtmlUl(); 
			htmlElement.setClassAttribute(tag);
		} else if (tag.equals(TEI_P_TAG)) {
			htmlElement = new HtmlP(); 
		} else if (tag.equals(TEI_REF_TAG)) {
			HtmlA a = new HtmlA();
			String target = this.getAttributeValue("target");
			if (target != null) {
				a.setHref(target);
			}
			htmlElement = a;
		} else if (SPANSET.contains(tag)){
			htmlElement = new HtmlSpan(); 
			htmlElement.setClassAttribute(tag);
		} else {
			htmlElement = new HtmlDiv(); 
			htmlElement.setClassAttribute(tag);
		}
		XMLUtil.copyAttributesFromTo(this, htmlElement);
        createSubclassedChildren(this, htmlElement);
		return htmlElement;
	}

	private HtmlElement processBiblScope() {
		HtmlElement htmlElement;
		htmlElement = new HtmlSpan();
		String unit = this.getAttributeValue("unit");
		htmlElement.setClassAttribute(unit);
		String from = this.getAttributeValue("from");
		String to = this.getAttributeValue("to");
		String volume = this.getAttributeValue("volume");
		if (from != null) {
			htmlElement.appendChild(from+"-"+to);
		} else if (volume != null) {
			htmlElement.appendChild(volume);
		}
		return htmlElement;
	}

	protected static void createSubclassedChildren(GrobidElement grobidElement, HtmlElement newElement) {
		if (grobidElement != null) {
			for (int i = 0; i < grobidElement.getChildCount(); i++) {
				Node grobidChild = grobidElement.getChild(i);
				Node htmlNode = null;
				if (grobidChild instanceof Text) {
					String value = grobidChild.getValue();
					htmlNode = new Text(value);
				} else if (grobidChild instanceof Comment) {
					htmlNode = new Comment(grobidChild.getValue());
				} else if (grobidChild instanceof ProcessingInstruction) {
					htmlNode = new ProcessingInstruction((ProcessingInstruction) grobidChild);
				} else if (grobidChild instanceof Element) {
					htmlNode = ((GrobidElement) grobidChild).createHTML();
				} else {
					throw new RuntimeException("Cannot create new node: "+grobidChild.getClass());
				}
				newElement.appendChild(htmlNode);
			}
		}
	}


}
