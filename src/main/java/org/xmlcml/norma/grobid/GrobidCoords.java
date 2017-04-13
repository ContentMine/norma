package org.xmlcml.norma.grobid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.xmlcml.euclid.Real2Range;
import org.xmlcml.euclid.RealRange;
import org.xmlcml.graphics.svg.SVGG;
import org.xmlcml.graphics.svg.SVGRect;
import org.xmlcml.graphics.svg.SVGShape;

public class GrobidCoords {
	private static final Logger LOG = Logger.getLogger(GrobidCoords.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}
	private static Map<String, String> colorByClassname = new HashMap<String, String>();
	static {
		colorByClassname.put("GrobidBiblStructElement", "orange");
		colorByClassname.put("GrobidRefElement", "yellow");
		colorByClassname.put("GrobidTableElement", "red");
		colorByClassname.put("GrobidFigureElement", "green");
		colorByClassname.put("GrobidFigDescElement", "cyan");
		colorByClassname.put("null", "gray");
	}

	private int page;
	private Real2Range bbox;
	private GrobidElement parentElement;

	public GrobidCoords() {
		
	}
	
	public GrobidCoords(String coordString, GrobidElement parentElement) {
		this.parentElement = parentElement;
		String[] fields = coordString.split(",");
		if (fields.length != 5) {
			throw new RuntimeException("not a rect");
		}
		page = Integer.parseInt(fields[0]);
		double x = Double.parseDouble(fields[1]);
		double y = Double.parseDouble(fields[2]);
		double w = Double.parseDouble(fields[3]);
		double h = Double.parseDouble(fields[4]);
		bbox = new Real2Range(new RealRange(x, x+w), new RealRange(y, y+h));
	}

	public static List<GrobidCoords> createCoordsList(GrobidElement element) {
		String attCoords = element.getAttributeValue(GrobidElement.COORDS);
		String[] coordStringList = attCoords.split(";");
		List<GrobidCoords> coordsList = new ArrayList<GrobidCoords>();
		for (String coordString : coordStringList) {
			GrobidCoords grobidCoords = new GrobidCoords(coordString, element);
			coordsList.add(grobidCoords);
		}
		return coordsList;
	}

	public static SVGG createSVG(List<GrobidCoords> coordsList) {
		SVGG g = new SVGG();
		for (GrobidCoords grobidCoords : coordsList) {
			SVGShape rect = grobidCoords.getSVGRect();
			g.appendChild(rect);
		}
		return g;
	}

	private SVGRect getSVGRect() {
		SVGRect rect = SVGRect.createFromReal2Range(bbox);
		String classname = parentElement.getClass().getSimpleName();
		String fill = colorByClassname.get(classname);
		if (fill == null) {
			LOG.debug("unknown class: "+classname);
		}
		rect.setFill(String.valueOf(colorByClassname.get(classname)));
		rect.setOpacity(0.2);
		rect.addTitle(classname);
		return rect;
	}

	public static int getPage(List<GrobidCoords> coordsList) {
		int page = 0;
		for (GrobidCoords grobidCoords : coordsList) {
			if (page == 0) {
				page = grobidCoords.getPage();
			} else if (page != grobidCoords.getPage()) {
				throw new RuntimeException("inconsistent page in coords");
			}
		}
		return page;
	}

	private int getPage() {
		return page;
	}

}
