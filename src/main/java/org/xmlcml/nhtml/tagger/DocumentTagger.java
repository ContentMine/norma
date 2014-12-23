package org.xmlcml.nhtml.tagger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nu.xom.Attribute;
import nu.xom.Element;

import org.apache.log4j.Logger;
import org.xmlcml.xml.XMLUtil;

/** reads a set of tag definitions and uses them to tag an Element and later search it.
 * 
 * @author pm286
 *
 */
public class DocumentTagger {

	/** prefix for ContentMine tags.
	 * 
	 */
	private static final String CM_PREFIX = "cm_";
	private static final String CM_SUFFIX = "_";
	private static Pattern CM_TAG_PATTERN = Pattern.compile(CM_PREFIX+"([^\\s]+)"+CM_SUFFIX);


	private static final String CLASS = "class";

	public enum InputType {
		HTML,
		SVG,
		XML;
	}

	private final static Logger LOG = Logger.getLogger(DocumentTagger.class);

	
	protected static final File NHTML_DIR = new File("src/main/resources/org/xmlcml/nhtml");
	
	public static final String ATTRIBUTE_TAG= "tag";
	public static final String NAME = "name";

	private static final String OR = " | ";
	private static final String OR1 = " or ";
	private static final String AND = " and ";
	private static final String NOT = "not";

	/** all the sections we might tag.
	 * 
	 */
	public static final String ABSTRACT = "abstract";
	public static final String ACKNOWLEDGEMENTS = "acknowledge";
	public static final String ADDITONALFILES = "additionfiles";
	public static final String AUTHORCONTRIB = "authorcontrib";
	public static final String BACKGROUND = "background";
	public static final String COMPETING = "competing";
	public static final String CONCLUSION = "conclusion";
	public static final String DISCUSSION = "discussion";
	public static final String METHODS = "methods";
	public static final String REFERENCES = "references";
	public static final String RESULTS = "results";

	public static final String[] TAGS = {
		ABSTRACT,
		ACKNOWLEDGEMENTS,
		ADDITONALFILES,
		AUTHORCONTRIB,
		BACKGROUND,
		COMPETING,
		CONCLUSION,
		DISCUSSION,
		METHODS,
		REFERENCES,
		RESULTS,
		
		
		};
	protected static final File TAGGER_DIR = new File(NHTML_DIR, "document");

	public static final String TAG = "tagger";
	private static final String STYLESHEET = "stylesheet";
	
	private List<String> tagNames;
	private List<TagElement> tagElementList;
	private List<MetadataElement> metadataElementList;
	private TaggerElement taggerElement;

	private InputType type;
	private List<Element> styleSheetList;
	private MetadataListElement metadataListElement;
	private TagListElement tagListElement;
	
	protected DocumentTagger() {
	}

	protected DocumentTagger(File taggerFile) {
		this();
		readAndParse(taggerFile);
	}

	private void readAndParse(File taggerFile) {
		taggerElement = (TaggerElement) AbstractTElement.createElement(taggerFile);
		metadataListElement = taggerElement.getMetadataListElement();
		tagListElement = taggerElement.getTagListElement();
	}

	protected TaggerElement getTaggerElement() {
		return taggerElement;
	}

	/** tagging through the class attribute.
	 * 
	 * his can have a whitespace separated list of values, so
	 * create it if it doesn't exists and append if it does.
	 * 
	 * @param tagName
	 * @param element
	 */
	static void addTag(String tagName, Element element) {
		Attribute attribute = element.getAttribute(CLASS);
		if (attribute == null) {
			attribute = new Attribute(CLASS, tagName);
		} else {
			String value = attribute.getValue().trim();
			if (value.length() > 0) {
				value += " ";
			}
			value += createTagValue(tagName);
			attribute = new Attribute(CLASS, value);
		}
		element.addAttribute(attribute);
	}
	
	static String getTagName(Element element) {
		String tag = null;
		String attValue = element.getAttributeValue(CLASS);
		if (attValue != null) {
			Matcher matcher = CM_TAG_PATTERN.matcher(attValue);
			if (matcher.matches()) {
				tag = matcher.group(1);
			}
		}
		return tag;
	}

	public static String createTagValue(String tagName) {
		return CM_PREFIX+tagName+CM_SUFFIX;
	}

	public List<Element> findSectionsFromTagDefinitions(Element elementToSearch, String tagName) {
		String xpath = getXpathForTag(tagName);
		if (xpath != null) {
			return XMLUtil.getQueryElements(elementToSearch, xpath);
		}
		return new ArrayList<Element>();
	}

	public String getName() {
		return taggerElement.getAttributeValue(NAME);
	}
	
	public List<MetadataElement> getMetadataList() {
		return null;
	}
	
	protected String getXpathForTag(String tagName) {
		TagElement tagElement = getTagWithName(tagName);
		return (tagElement == null) ? null : tagElement.getXPath();
	}

	public List<MetadataElement> getMetadataElements() {
		if (metadataElementList == null) {
			metadataElementList = new ArrayList<MetadataElement>();
			List<Element> tagElements = XMLUtil.getQueryElements(metadataListElement, "*[local-name()='"+MetadataElement.TAG+"']");
			for (Element tagElement : tagElements) {
				metadataElementList.add((MetadataElement)tagElement);
			}
		}
		return metadataElementList;
	}

	public List<Element> getStyleSheetElements() {
		if (styleSheetList == null) {
			styleSheetList = XMLUtil.getQueryElements(taggerElement, "*[local-name()='"+STYLESHEET+"']");
		}
		return styleSheetList;
	}

	public List<TagElement> getTagElements() {
		if (tagElementList == null) {
			tagElementList = new ArrayList<TagElement>();
			List<Element> tagElements = XMLUtil.getQueryElements(tagListElement, "*[local-name()='"+TagElement.TAG+"']");
			for (Element tagElement : tagElements) {
				tagElementList.add((TagElement)tagElement);
			}
		}
		return tagElementList;
	}

	/** return all tags with given name.
	 * 
	 * should only be one or none.
	 * 
	 * @param tagName
	 * @return
	 */
	public TagElement getTagWithName(String tagName) {
		List<Element> tagElements = XMLUtil.getQueryElements(taggerElement, "*[local-name()='"+TagElement.TAG+"' and @name='"+tagName+"']");
		return tagElements.size() != 1 ? null : (TagElement) tagElements.get(0);
	}
	
	protected List<String> getTagNames() {
		if (tagNames == null) {
			List<TagElement> tagElements = getTagElements();
			tagNames = new ArrayList<String>();
			for (Element tagElement : tagElements) {
				String tagName = ((TagElement)tagElement).getName();
				tagNames.add(tagName);
			}
		}
		return tagNames;
	}

	public void addTagsToSections(Element elementToTag) {
		getTagNames();
		int count = 0;
		for (String tagName : tagNames) {
			List<Element> sections = findSectionsFromMatchingTags(elementToTag, tagName);
			for (Element section : sections) {
				DocumentTagger.addTag(tagName, section);
				count++;
			}
		}
		LOG.debug("tagged "+count+" sections");
	}

	/** query with a single section name.
	 * 
	 * @param element containing sections
	 * @param tagName for xpath
	 * @return list of sections
	 */
	public List<Element> findSectionsFromMatchingTags(Element element, String tagName) {
		String xpath = getXpathForTag(tagName);
		if (xpath == null) {
			throw new RuntimeException("Cannot find xpath definition for: "+tagName);
		}
		List<Element> elements = XMLUtil.getQueryElements(element, xpath);
		return elements;
	}

	public void debug(String msg) {
		XMLUtil.debug(taggerElement, msg);
	}

}
