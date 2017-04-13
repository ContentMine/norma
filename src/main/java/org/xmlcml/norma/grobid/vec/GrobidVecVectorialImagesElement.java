package org.xmlcml.norma.grobid.vec;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.xmlcml.graphics.svg.SVGElement;
import org.xmlcml.graphics.svg.SVGSVG;
import org.xmlcml.norma.grobid.GrobidFigDescElement;

public class GrobidVecVectorialImagesElement extends GrobidVecElement {

		public static final String TAG = "VECTORIALIMAGES";
		private static final Logger LOG = Logger.getLogger(GrobidFigDescElement.class);
		static {
			LOG.setLevel(Level.DEBUG);
		}

		public GrobidVecVectorialImagesElement() {
			super(TAG);
		}

		public SVGElement createSVG() {
			SVGSVG svg = new SVGSVG();
			super.transferAttributesAndChildren(svg);
			return svg;
		}


}
