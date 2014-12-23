package org.xmlcml.nhtml.tagger;

import java.util.ArrayList;

import java.util.List;

import nu.xom.Element;

import org.apache.log4j.Logger;
import org.xmlcml.nhtml.tagger.DocumentTagger.InputType;
import org.xmlcml.xml.XMLUtil;

public class MetadataListElement extends AbstractTElement {

	private static final Logger LOG = Logger.getLogger(TagListElement.class);
	
	public static final String TAG_DEFINITION_NAME = "name";
	public static final String TAG = "metadataList";

	private List<MetadataElement> metadataList;
	
	public MetadataListElement() {
		super(TAG);
	}

	public String getName() {
		return this.getAttributeValue(TAG_DEFINITION_NAME);
	}
	
	public List<MetadataElement> getMetadataElements() {
		List<Element> taggerList0 = XMLUtil.getQueryElements(this, MetadataElement.TAG);
		metadataList = new ArrayList<MetadataElement>();
		for (Element tag : taggerList0) {
			metadataList.add((MetadataElement) tag);
		}
		return metadataList;
	}
	
//	public List<String> getAllTagNames() {
//		List<String> allTagNames = new ArrayList<String>();
//		for (TagElement tagElement : this.getTagElements()) {
//			allTagNames.add(tagElement.getName());
//		}
//		return allTagNames;
//	}


}
