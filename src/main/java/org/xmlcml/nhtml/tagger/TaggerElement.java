package org.xmlcml.nhtml.tagger;

import java.util.List;

import nu.xom.Element;

import org.apache.log4j.Logger;
import org.xmlcml.xml.XMLUtil;

public class TaggerElement extends AbstractTElement {

	private static final Logger LOG = Logger.getLogger(TaggerElement.class);
	
	public static final String TAG = "tagger";

	public TaggerElement() {
		super(TAG);
	}

	public MetadataListElement getMetadataListElement() {
		List<Element> elements = XMLUtil.getQueryElements(this, "./*[local-name()='"+MetadataListElement.TAG+"']");
		return elements.size() != 1 ? null : (MetadataListElement) elements.get(0);
	}

	public TagListElement getTagListElement() {
		List<Element> elements = XMLUtil.getQueryElements(this, "./*[local-name()='"+TagListElement.TAG+"']");
		return elements.size() != 1 ? null : (TagListElement) elements.get(0);
	}

}
