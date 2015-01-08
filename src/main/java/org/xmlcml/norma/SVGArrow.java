package org.xmlcml.norma;

import nu.xom.Attribute;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.xmlcml.euclid.Real2;
import org.xmlcml.graphics.svg.SVGG;
import org.xmlcml.graphics.svg.SVGLine;

public class SVGArrow extends SVGLine {

private static final Logger LOG = Logger.getLogger(SVGArrow.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}

	private SVGLine subline;
	private int linePoint;
	private SVGTriangle triangle;
//	private Real2 head;
//	private Real2 tail;
	private int trianglePoint;
	
	public SVGArrow() {
		super();
		this.addAttribute(new Attribute("class", "arrow"));
	}

	public SVGArrow(SVGLine subline, int linePoint, SVGTriangle triangle, int trianglePoint) {
		this();
		this.subline = subline;
		this.linePoint = linePoint;
		this.setXY(subline.getXY(1 - linePoint), 0);
		
		this.triangle = triangle;
		this.trianglePoint = trianglePoint;
		this.setXY(triangle.getLineStartingFrom(trianglePoint).getXY(0), 0);
		
	}

	/** creates arrow if geometry is right.
	 * 
	 * @param subline
	 * @param triangle
	 * @param delta
	 * @return arrow if can create else null
	 */
	public static SVGArrow createArrow(SVGLine subline, SVGTriangle triangle, double delta) {
		SVGArrow arrow = createArrow(subline, 0, triangle, delta / 2);
		if (arrow == null) {
			arrow = createArrow(subline, 1, triangle, delta / 2);
		}
		return arrow;
	}
	
	private static SVGArrow createArrow(SVGLine subline, int lineEnd, SVGTriangle triangle, double delta) {
		SVGArrow arrow = null;
		int lineSerial = triangle.getLineTouchingPoint(subline.getXY(lineEnd), delta);
		if (lineSerial != -1) {
			int trianglePoint = (lineSerial + 2  ) % 3; // get opposite point
			LOG.debug("line serial "+lineSerial+" / "+trianglePoint);
			arrow = new SVGArrow(subline, lineEnd, triangle, trianglePoint);
		}
		return arrow;
 
	}

	public String toString() {
		String s = subline.toString();
		s += "/"+triangle.toString();
		s += "{"+getXY(1)+","+getXY(0)+"}";
		return s;
	}
}
