package org.xmlcml.norma.image.ocr;

import org.xmlcml.graphics.svg.SVGG;

/** holds a chunk of g+text identified by HOCR.
 * 
 * @author pm286
 *
 */
public class HOCRText extends HOCRChunk {

	public HOCRText(SVGG g) {
		super(g);
	}

	public static boolean isWordInPhrase(Double separation, Double meanTextSize,
			double min, double max) {
		return (separation < max * meanTextSize && separation > min * meanTextSize);
	}

}
