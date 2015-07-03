package org.xmlcml.norma.image.ocr;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.xmlcml.graphics.svg.SVGG;

/** a <g><rect/><text>val</text></g>
 * where the rect is a bounding box from HOCR
 * 
 * @author pm286
 *
 */
public class HOCRLabel extends HOCRChunk {

	final static Logger LOG = Logger.getLogger(HOCRLabel.class);
	static {LOG.setLevel(Level.DEBUG);}

	public HOCRLabel(SVGG g) {
		super(g);
	}

}
