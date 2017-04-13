package org.xmlcml.norma.grobid.vec;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.xmlcml.euclid.Real2;
import org.xmlcml.euclid.Real2Array;
import org.xmlcml.graphics.svg.SVGElement;
import org.xmlcml.graphics.svg.SVGPath;
import org.xmlcml.graphics.svg.SVGPathPrimitive;
import org.xmlcml.graphics.svg.path.CubicPrimitive;
import org.xmlcml.graphics.svg.path.MovePrimitive;
import org.xmlcml.graphics.svg.path.PathPrimitiveList;

public class GrobidVecCElement extends GrobidVecElement {

	private static final String Y1 = "y1";
	private static final String X1 = "x1";
	private static final String Y2 = "y2";
	private static final String X2 = "x2";
	private static final String Y3 = "y3";
	private static final String X3 = "x3";
	
	public static final String TAG = "C";
	private static final Logger LOG = Logger.getLogger(GrobidVecCElement.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}

	public GrobidVecCElement() {
		super(TAG);
	}

	public SVGElement createSVG(Real2 lastXY) {
		SVGElement element = null;
		Real2 xy1 = getXY(this, X1, Y1);
		Real2 xy2 = getXY(this, X2, Y2);
		xy = getXY(this, X3, Y3);
		if (xy != null && xy1 != null && xy2 != null) {
			PathPrimitiveList pathPrimitiveList = new PathPrimitiveList();
			pathPrimitiveList.add(new MovePrimitive(lastXY));
			Real2Array real2Array = new Real2Array();
			real2Array.add(xy1);
			real2Array.add(xy2);
			real2Array.add(xy);
			
			pathPrimitiveList.add(new CubicPrimitive(real2Array));
			element = new SVGPath(pathPrimitiveList, (SVGPath) null);
		}
		
		return element;
	}

}
