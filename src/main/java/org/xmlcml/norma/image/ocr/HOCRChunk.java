package org.xmlcml.norma.image.ocr;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.xmlcml.euclid.Real2;
import org.xmlcml.euclid.Util;
import org.xmlcml.graphics.svg.SVGConstants;
import org.xmlcml.graphics.svg.SVGG;
import org.xmlcml.graphics.svg.SVGRect;
import org.xmlcml.graphics.svg.SVGText;
import org.xmlcml.norma.image.ocr.GridExtractor.Coord;

public class HOCRChunk {

	final static Logger LOG = Logger.getLogger(HOCRChunk.class);
	static {LOG.setLevel(Level.DEBUG);}
	
	protected SVGRect bboxRect;
	protected SVGText text;
	
	public HOCRChunk() {
	}
	
	public HOCRChunk(SVGG g) {
		extractBoxAndText(g);
	}
	
	protected void extractBoxAndText(SVGG svgg) {
		bboxRect = (SVGRect) svgg.getFirstChildElement(SVGRect.TAG, SVGConstants.SVG_NAMESPACE);
		text = (SVGText) svgg.getFirstChildElement(SVGText.TAG, SVGConstants.SVG_NAMESPACE);
		LOG.trace(bboxRect == null ? null : bboxRect.getBoundingBox()+"; "+((text !=null) ? text.getValue() : null));
	}

	public Double getAbsDiff(HOCRLabel label, Coord XY) {
		Double xy0 = this.get(XY);
		Double xy1 = label.get(XY);
		return (xy0 != null && xy1 != null) ? Math.abs(xy0 - xy1) : null;
	}

	protected Double get(Coord XY) {
		return (text == null) ? null : (XY == Coord.X) ? text.getX() : text.getY();
	}

	protected boolean hasSame(HOCRLabel label1, Real2 maxDelta, Coord XY) {
		double d = this.getAbsDiff(label1, XY);
		return (XY == Coord.X) ? d <  maxDelta.getX() : d < maxDelta.getY();
	}

	public String toString() {
		return text == null ? null : Util.format(text.getFontSize(), 0)+"; "+text.getXY().format(0)+"; "+text.getValue();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((bboxRect == null) ? 0 : bboxRect.hashCode());
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HOCRChunk other = (HOCRChunk) obj;
		if (bboxRect == null) {
			if (other.bboxRect != null)
				return false;
		} else if (!bboxRect.equals(other.bboxRect))
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		return true;
	}

	public Double getBoxEnd() {
		return (bboxRect == null) ? null : bboxRect.getX() + bboxRect.getWidth(); 
	}

	public SVGRect getBboxRect() {
		return bboxRect;
	}

	public void setBboxRect(SVGRect bboxRect) {
		this.bboxRect = bboxRect;
	}

	public SVGText getText() {
		return text;
	}

	public void setText(SVGText text) {
		this.text = text;
	}

	public Double getBoxStart() {
		return (bboxRect == null) ? null : bboxRect.getX(); 
	}

	public Double getFontSize() {
		return (text == null) ? null : text.getFontSize(); 
	}

	public static Double getMeanSize(Double previousSize, Double textSize) {
		if (previousSize == null) {
			return (textSize == null) ? null : textSize;
		} else {
			return (textSize == null) ? previousSize : (textSize + previousSize) / 2.;
		}
	}
}
