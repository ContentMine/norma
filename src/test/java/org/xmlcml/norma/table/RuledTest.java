package org.xmlcml.norma.table;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.xmlcml.euclid.Real2Range;
import org.xmlcml.euclid.RealRange.Direction;
import org.xmlcml.graphics.svg.SVGCircle;
import org.xmlcml.graphics.svg.SVGElement;
import org.xmlcml.graphics.svg.SVGG;
import org.xmlcml.graphics.svg.SVGLine;
import org.xmlcml.graphics.svg.SVGRect;
import org.xmlcml.graphics.svg.SVGSVG;
import org.xmlcml.graphics.svg.linestuff.LineMerger;
import org.xmlcml.graphics.svg.linestuff.LineMerger.MergeMethod;
import org.xmlcml.graphics.svg.store.SVGStore;
import org.xmlcml.norma.NormaFixtures;

import junit.framework.Assert;

public class RuledTest {
	private static final Logger LOG = Logger.getLogger(RuledTest.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}
	private static final String RULED = "ruled";
	private static final File RULED_DIR = new File(NormaFixtures.TEST_TABLE_DIR, RULED);
	private static final File RULED2001_1 = new File(RULED_DIR, "/_bmj.2001.323.1–5/tables/table1/table.svg");
	private static final File RULED2001_2 = new File(RULED_DIR, "/_bmj.2001.323.1–5/tables/table2/table.svg");
	private static final File RULED2001_3 = new File(RULED_DIR, "/_bmj.2001.323.1–5/tables/table3/table.svg");
	private static final File RULED2001_4 = new File(RULED_DIR, "/_bmj.2001.323.1–5/tables/table4/table.svg");
	private static final File RULED1007_1 = new File(RULED_DIR, "/10.1007.s13142-010-0006-y/tables/table1/table.svg");
	private static final File RULED1007_2 = new File(RULED_DIR, "/10.1007.s13142-010-0006-y/tables/table1/table.svg");
	private static final File RULED1007_1MICRO = new File(RULED_DIR, "/10.1007.s13142-010-0006-y/tables/table1/tablemicro.svg");
	
	
	File[] RULED_FILES = new File[] {
		RULED2001_1,
		RULED2001_2,
		RULED2001_3,
		RULED2001_4,
		RULED1007_1,
		RULED1007_2,
	};
	
	private static final double EPS_ANGLE = 0.001;


	/** test conversion of thin rects to lines
	 * 
	 * @throws FileNotFoundException
	 */
	@Test
	public void testThinRectToLineAndAttributes() throws FileNotFoundException {
		File svgFile = RULED1007_1MICRO;
		SVGStore svgStore = new SVGStore();
		svgStore.readGraphicsComponents(svgFile);
		SVGElement svgElement = (SVGElement) svgStore.getExtractedSVGElement();
		LOG.debug(svgElement.toXML());
		List<SVGLine> lineList = SVGLine.extractSelfAndDescendantLines(svgElement);
		Assert.assertEquals("lines", 4, lineList.size());
		SVGLine svgLine0 = lineList.get(0);
		Assert.assertEquals("line0", "#131313", svgLine0.getFill());
		Assert.assertEquals("line0", "line.0", svgLine0.getId());
		Assert.assertTrue("line0", svgLine0.getStyle().startsWith("clip-path:url(#clipPath1);fill:#131313;stroke:black;stroke-width:0.2"));
		Assert.assertEquals("line0", 0.283, svgLine0.getStrokeWidth(), 0.001);
//		Real2Test.assertEquals("line0 XY0", new Real2(353.537, 106.369), svgLine0.getXY(0), 0.001);
		Assert.assertEquals("line0", 353.537, svgLine0.getXY(0).getX(), 0.001);
		Assert.assertEquals("line0", 106.369, svgLine0.getXY(0).getY(), 0.001);
		double[] widths = {0.283, 0.283, 0.340, 0.397};
		double[] lengths = {213.392, 421.795, 422.362, 422.362};
		for (int i = 0; i < lineList.size(); i++) {
			SVGLine line = lineList.get(i);
			Assert.assertEquals("width "+i, widths[i], line.getStrokeWidth(), 0.001);
			Assert.assertEquals("length "+i, lengths[i], line.getLength(), 0.001);
		}
		File svgOutFile = NormaFixtures.getCompactSVGFile(new File("target/"+RULED), new File("target/"+RULED+"/"+svgFile.getPath()+"micro"));
		LOG.debug(">>"+svgOutFile);
		SVGSVG.wrapAndWriteAsSVG(svgElement, svgOutFile, 1000., 1000.);
	}
	
	/** test joining lines
	 * draws SVG showing lines to merge
	 * @throws FileNotFoundException
	 */
	@Test
	public void testMergeLines() throws FileNotFoundException {
		File svgFile = RULED1007_1;
		SVGStore svgStore = new SVGStore();
		svgStore.readGraphicsComponents(svgFile);
		SVGElement svgElement = (SVGElement) svgStore.getExtractedSVGElement();
		List<SVGLine> lineList = SVGLine.extractSelfAndDescendantLines(svgElement);
		Assert.assertEquals("lines", 59, lineList.size());
		debugLines(lineList, new File("target/ruled/lineSet.svg"));
		List<SVGLine> mergedLines = LineMerger.mergeLines(lineList, 1.0, MergeMethod.OVERLAP);
		Assert.assertEquals("merged lines", 43, mergedLines.size());
		debugLines(mergedLines, new File("target/ruled/mergedSet.svg"));
	}

	private void debugLines(List<SVGLine> lineList, File svgOutfile) {
		SVGG g = new SVGG();
		for (SVGLine line : lineList) {
			g.appendChild(line.copy());
			g.appendChild(new SVGCircle(line.getXY(0), 2.0));
			g.appendChild(new SVGCircle(line.getXY(1), 2.0));
		}
		SVGSVG.wrapAndWriteAsSVG(g, svgOutfile);
	}
	
	
	/** rotate element positions position
	 * @throws FileNotFoundException 
	 * 
	 */
	@Test
	public void testReadGraphicsComponents() throws FileNotFoundException {
		File cProjectDir = new File(NormaFixtures.TEST_TABLE_DIR, RULED);
		Assert.assertTrue(cProjectDir.exists());
		for (File svgFile : RULED_FILES) {
			File svgOutFile = new File("target/"+RULED+"/"+svgFile.getPath());
			SVGStore svgStore = new SVGStore();
			svgStore.readGraphicsComponents(svgFile);
			SVGElement svgElement = (SVGElement) svgStore.getExtractedSVGElement();
			List<SVGLine> horizontalLines = svgStore.getHorizontalLines();
			LOG.debug("====================HLINES====================="+horizontalLines.size());
			for (SVGLine hline : horizontalLines) {
				LOG.debug(hline.getStyle()+": "+hline);
			}
//			SVGSVG.wrapAndWriteAsSVG(svgElement, svgOutFile, 1200., 1000.);
			SVGSVG.wrapAndWriteAsSVG(svgElement, NormaFixtures.getCompactSVGFile(new File("target/" + RULED), svgOutFile), 1000., 1000.);
		}
	}

	private Real2Range makeResizedBbox(SVGRect rect, int delta) {
		Real2Range rectBox = rect.getBoundingBox();
		rectBox.extendBothEndsBy(Direction.HORIZONTAL, delta, delta);
		rectBox.extendBothEndsBy(Direction.VERTICAL, delta, delta);
		return rectBox;
	}
}
