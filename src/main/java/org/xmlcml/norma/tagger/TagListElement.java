package org.xmlcml.norma.tagger;

import java.util.ArrayList;
import java.util.List;

import nu.xom.Element;

import org.apache.log4j.Logger;
import org.xmlcml.xml.XMLUtil;

public class TagListElement extends AbstractTElement {

	private static final Logger LOG = Logger.getLogger(TagListElement.class);
	
	public static final String TAG_DEFINITION_NAME = "name";
	public static final String TAG = "tagList";

	private List<TagElement> tagList;
	
	public TagListElement() {
		super(TAG);
	}

	public String getName() {
		return this.getAttributeValue(TAG_DEFINITION_NAME);
	}
	
	public List<TagElement> getTagElements() {
		List<Element> taggerList0 = XMLUtil.getQueryElements(this, TagElement.TAG);
		tagList = new ArrayList<TagElement>();
		for (Element tag : taggerList0) {
			tagList.add((TagElement) tag);
		}
		return tagList;
	}
	

	public List<String> getAllTagNames() {
		List<String> allTagNames = new ArrayList<String>();
		for (TagElement tagElement : this.getTagElements()) {
			allTagNames.add(tagElement.getName());
		}
		return allTagNames;
	}


}
