package org.xmlcml.norma.document.sedar;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;
import org.xmlcml.euclid.Real2Range;
import org.xmlcml.graphics.svg.SVGElement;
import org.xmlcml.graphics.svg.SVGG;
import org.xmlcml.graphics.svg.SVGLine;
import org.xmlcml.graphics.svg.SVGPath;
import org.xmlcml.graphics.svg.SVGPolyline;
import org.xmlcml.graphics.svg.SVGRect;
import org.xmlcml.graphics.svg.SVGSVG;
import org.xmlcml.graphics.svg.SVGShape;
import org.xmlcml.graphics.svg.SVGText;
import org.xmlcml.html.HtmlElement;
import org.xmlcml.norma.Fixtures;
import org.xmlcml.norma.input.pdf.PDF2XHTMLConverter;
import org.xmlcml.svgbuilder.geom.Path2ShapeConverter;
import org.xmlcml.xml.XMLUtil;

public class SedarTest {

	private static final Logger LOG = Logger.getLogger(SedarTest.class);
	static {
		LOG.setLevel(Level.DEBUG);
}

	@Test
	@Ignore // too long
	public void testReadPDF() throws Exception {
		PDF2XHTMLConverter converter = new PDF2XHTMLConverter();
		HtmlElement htmlElement = converter.readAndConvertToXHTML(new File(Fixtures.TEST_SEDAR_DIR, "WesternZagros.pdf"));
		new File("target/sedar/").mkdirs();
		XMLUtil.debug(htmlElement, new FileOutputStream("target/sedar/WesternZagros.html"), 1);
	}
	
	@Test
	public void testCreateShapes() {
		SVGElement chart = SVGElement.readAndCreateSVG(new File(Fixtures.TEST_SEDAR_DIR, "image.g.11.7.svg"));
		List<SVGPath> svgPathList = SVGPath.extractPaths(chart);
		List<SVGText> svgTextList = SVGText.extractSelfAndDescendantTexts(chart);
		LOG.debug("text"+svgTextList.size());
		Path2ShapeConverter  converter = new Path2ShapeConverter();
		List<List<SVGShape>> shapeListList = converter.convertPathsToShapesAndSplitAtMoves(svgPathList);
		SVGG g = new SVGG();
		List<SVGG> rectGList = new ArrayList<SVGG>();
		for (List<SVGShape> shapeList : shapeListList) {
			for (SVGShape shape : shapeList) {
				shape.detach();
				if (shape instanceof SVGRect) {
					shape.setStroke("red");
					SVGG gRect = new SVGG();
					gRect.appendChild(shape);
					g.appendChild(gRect);
					rectGList.add(gRect);
				} else if (shape instanceof SVGLine) {
					shape.setStroke("cyan");
					shape.setStrokeWidth(3.);
					g.appendChild(shape.copy());
				} else if (shape instanceof SVGPolyline) {
					shape.setStroke("blue");
					shape.setStrokeWidth(0.1);
					shape.setFill("none");
					g.appendChild(shape.copy());
				} else if (shape instanceof SVGPath) {
					if (shape.getSignature().equals("M")) {
						System.err.println("omitted M");
					} else {
						shape.setStroke("orange");
						g.appendChild(shape.copy());
					}
				} else {
					System.err.println(shape);
					shape.setStroke("green");
					shape.setStrokeWidth(2.0);
					g.appendChild(shape.copy());
				}
			}
		}
		for (SVGText text : svgTextList) {
			Real2Range text2R = text.getBoundingBox();
			for (SVGG gRect : rectGList) {
				SVGRect rect = (SVGRect) gRect.getChild(0);
				Real2Range rect2R = rect.getBoundingBox();
				if (rect2R.includes(text2R)) {
					text.detach();
					gRect.appendChild(text);
				}
			}
		}
			
		SVGSVG.wrapAndWriteAsSVG(g, new File("target/sedar/shapes.svg"));
		
	}
}
