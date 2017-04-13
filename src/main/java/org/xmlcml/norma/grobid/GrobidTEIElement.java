package org.xmlcml.norma.grobid;

import java.util.ArrayList;

import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.xmlcml.html.HtmlElement;
import org.xmlcml.xml.XMLUtil;

import nu.xom.Element;

public class GrobidTEIElement extends GrobidElement {

	private static final Logger LOG = Logger.getLogger(GrobidTEIElement.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}
	public static String TAG = "TEI";

	public GrobidTEIElement() {
		super(TAG);
	}

	public GrobidTEIElement(String tag) {
		super(tag);
	}
	
	public List<GrobidElement> getCoordElements() {
		List<Element> coordElementList = XMLUtil.getQueryElements(this, "//*[@" + GrobidElement.COORDS + "]");
		List<GrobidElement> grobidCoordElementList = new ArrayList<GrobidElement>();
		for (Element coordElement : coordElementList) {
//			GrobidElement grobidCoordElement = (GrobidElement) GrobidElement.createElement(coordElement); 
			grobidCoordElementList.add((GrobidElement) coordElement);
		}
		return grobidCoordElementList;
	}

	public List<List<GrobidCoords>> getCoordListList() {
		List<GrobidElement> coordElements = getCoordElements();
		List<List<GrobidCoords>> coordsListList = new ArrayList<List<GrobidCoords>>();
		for (GrobidElement coordElement : coordElements) {
			List<GrobidCoords> coordsList = GrobidCoords.createCoordsList(coordElement);
			coordsListList.add(coordsList);
		}
		return coordsListList;
	}



	
}
