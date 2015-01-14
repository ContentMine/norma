package org.xmlcml.norma;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.xmlcml.graphics.svg.SVGElement;
import org.xmlcml.graphics.svg.SVGLine;

public class SVGPlot extends SVGDiagram {
	
	static final Logger LOG = Logger.getLogger(SVGPlot.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}

	public SVGPlot(SVGElement diagram) {
		this.rawDiagram = diagram;
	}

	public void createPlot() {
		createPathsTextAndShapes();
		this.createAxisBox(eps);
		
	}

	private void createAxisBox(double delta) {
		SVGLine.normalizeAndMergeAxialLines(lineList, delta);
	}

}
