package org.xmlcml.norma.svg;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.xmlcml.euclid.Angle;
import org.xmlcml.graphics.svg.SVGElement;

/** converts SVG files.
 * initially transforms SVG into other SVG
 * 
 * @author pm286
 *
 */
public class SVG2SVGConverter {
	private static final Logger LOG = Logger.getLogger(SVG2SVGConverter.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}
	private static final String ANTICLOCK90 = "anticlock90";
	private static final String CLOCK90 = "clock90";
	private static final String ROTATE = "rotate";
	
	private SVGElement svgElementIn;
	private List<String> transformTokenList;
	private SVGElement svgElementOut;
	private List<String> args;

	public SVG2SVGConverter() {
		
	}
	
	public void readFile(File inputFile) throws IOException {
		svgElementIn = SVGElement.readAndCreateSVG(inputFile);
	}

	public void setTransformTokenList(List<String> transformTokenList) {
		if (transformTokenList == null || transformTokenList.size() == 0) {
			throw new RuntimeException("empty token list");
		}
		this.transformTokenList = transformTokenList;
		this.transformTokenList.remove(0);  // remove command;
		LOG.debug(">> "+this.transformTokenList);
	}

	public SVGElement createSVGElement() {
		processTransforms();
		return svgElementOut;
	}

	private void processTransforms() {
		if (transformTokenList != null && transformTokenList.size() > 0) {
			String command = transformTokenList.get(0);
			args = new ArrayList<String>(transformTokenList);
			args.remove(0);
			if (ROTATE.equals(command)) {
				rotate();
			}
		}
	}

	private void rotate() {
		Angle angle = parseAngle();
		svgElementOut = svgElementIn.createElementWithRotatedDescendants(angle);
	}

	private Angle parseAngle() {
		Angle angle = null;
		if (args.size() == 1) {
			angle = new Angle(0.);
			String angleS = args.get(0);
			if (CLOCK90.equals(angleS)) {
				angle = new Angle(-Math.PI/2.0);
			} else {
				throw new RuntimeException("unknown rotation angle: "+angleS);
			}
		} else if (args.size() == 2) {
			angle = Angle.createAngle(args.get(0), args.get(1));
		}
		return angle;
	}

}
