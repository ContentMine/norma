package org.xmlcml.norma.table;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.xmlcml.euclid.Real2Range;
import org.xmlcml.euclid.RealArray;
import org.xmlcml.euclid.RealRange.Direction;
import org.xmlcml.euclid.Util;
import org.xmlcml.euclid.util.MultisetUtil;
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

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

import junit.framework.Assert;

public class RuledTest {
	private static final Logger LOG = Logger.getLogger(RuledTest.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}
	private static final String RULED = "ruled";
	private static final File RULED_DIR = new File(NormaFixtures.TEST_TABLE_DIR, RULED);
	private static final File RULED2001_1 = new File(RULED_DIR, "/_bmj.2001.323.1–5/tables/table1/table.svg");
	private static final File RULED2001_1MICRO = new File(RULED_DIR, "/_bmj.2001.323.1–5/tables/table1/tablemicro.svg");
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
	public void testThinRectToLineAndAttributes0() throws FileNotFoundException {
		File svgFile = RULED2001_1MICRO;
		SVGStore svgStore = new SVGStore();
		svgStore.setSplitAtMove(true);
		svgStore.readGraphicsComponents(svgFile);
		SVGElement svgElement = (SVGElement) svgStore.getExtractedSVGElement();
		LOG.debug("container "+svgElement.toXML());
		List<SVGLine> lineList = SVGLine.extractSelfAndDescendantLines(svgElement);
		Assert.assertEquals("lines", 19, lineList.size());
		SVGLine svgLine0 = lineList.get(0);
		LOG.debug("new line "+svgLine0.toXML());
		Assert.assertEquals("line0", "none", svgLine0.getFill());
		Assert.assertEquals("line0", "line.0", svgLine0.getId());
//		Assert.assertTrue("line0 style", svgLine0.getStyle().startsWith("clip-path:url(#clipPath1);fill:#131313;stroke:black;stroke-width:0.2"));
		Assert.assertEquals("line0", 0.469, svgLine0.getStrokeWidth(), 0.001);
		Assert.assertEquals("line0", 66.332, svgLine0.getXY(0).getX(), 0.001);
		Assert.assertEquals("line0", 59.319, svgLine0.getXY(0).getY(), 0.001);
		RealArray widthArray = new RealArray();
		RealArray lengthArray = new RealArray();
		for (int i = 0; i < lineList.size(); i++) {
			SVGLine line = lineList.get(i);
			widthArray.addElement(line.getStrokeWidth());
			lengthArray.addElement(Util.format(line.getLength(), 3));
		}
		Assert.assertEquals("width", 
				"(0.469,0.234,0.234,0.234,0.234,0.093,0.093,0.093,0.093,0.093,0.093,0.093,0.093,0.093,0.093,0.093,0.093,0.093,0.093)",
				widthArray.toString());
		Assert.assertEquals("length", 
				"(409.205,408.941,146.852,146.853,408.941,408.941,408.941,408.941,408.941,408.941,408.941,408.941,408.941,408.941,408.941,408.941,408.941,408.941,408.941)",
				lengthArray.toString());
		List<Multiset.Entry<Double>> lengthSet = MultisetUtil.createDoubleListSortedByValue(lengthArray.createDoubleMultiset(3));
		Assert.assertEquals("lengths", "[146.852, 146.853, 408.941 x 16, 409.205]",  lengthSet.toString());
		List<Multiset.Entry<Double>> widthSet = MultisetUtil.createDoubleListSortedByValue(widthArray.createDoubleMultiset(3));
		Assert.assertEquals("widths", "[0.093 x 14, 0.234 x 4, 0.469]",  widthSet.toString());
		File svgOutFile = NormaFixtures.getCompactSVGFile(new File("target/"+RULED), new File("target/"+RULED+"/"+svgFile.getPath()+"micro"));
		SVGSVG.wrapAndWriteAsSVG(svgElement, svgOutFile, 1000., 1000.);
	}
	
//	/** test conversion of thin rects to lines
//	 * 
//	 * @throws FileNotFoundException
//	 */
//	@Test
//	public void testThinRectToLineAndAttributes() throws FileNotFoundException {
//		File svgFile = RULED1007_1MICRO;
//		SVGStore svgStore = new SVGStore();
//		svgStore.readGraphicsComponents(svgFile);
//		SVGElement svgElement = (SVGElement) svgStore.getExtractedSVGElement();
//		LOG.debug(svgElement.toXML());
//		List<SVGLine> lineList = SVGLine.extractSelfAndDescendantLines(svgElement);
//		Assert.assertEquals("lines", 4, lineList.size());
//		SVGLine svgLine0 = lineList.get(0);
//		Assert.assertEquals("line0", "#131313", svgLine0.getFill());
//		Assert.assertEquals("line0", "line.0", svgLine0.getId());
//		Assert.assertTrue("line0", svgLine0.getStyle().startsWith("clip-path:url(#clipPath1);fill:#131313;stroke:black;stroke-width:0.2"));
//		Assert.assertEquals("line0", 0.283, svgLine0.getStrokeWidth(), 0.001);
//		Assert.assertEquals("line0", 353.537, svgLine0.getXY(0).getX(), 0.001);
//		Assert.assertEquals("line0", 106.369, svgLine0.getXY(0).getY(), 0.001);
//		double[] widths = {0.283, 0.283, 0.340, 0.397};
//		double[] lengths = {213.392, 421.795, 422.362, 422.362};
//		for (int i = 0; i < lineList.size(); i++) {
//			SVGLine line = lineList.get(i);
//			Assert.assertEquals("width "+i, widths[i], line.getStrokeWidth(), 0.001);
//			Assert.assertEquals("length "+i, lengths[i], line.getLength(), 0.001);
//		}
//		File svgOutFile = NormaFixtures.getCompactSVGFile(new File("target/"+RULED), new File("target/"+RULED+"/"+svgFile.getPath()+"micro"));
//		LOG.debug(">>"+svgOutFile);
//		SVGSVG.wrapAndWriteAsSVG(svgElement, svgOutFile, 1000., 1000.);
//	}
	
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

	/** test joining lines
	 * draws SVG showing lines to merge
	 * @throws FileNotFoundException
	 */
	@Test
	public void testLinesByWidthAndLength() throws FileNotFoundException {
		File svgFile = RULED1007_1;
		SVGStore svgStore = new SVGStore();
		svgStore.readGraphicsComponents(svgFile);
		SVGElement svgElement = (SVGElement) svgStore.getExtractedSVGElement();
		List<SVGLine> lineList = SVGLine.extractSelfAndDescendantLines(svgElement);
		List<SVGLine> mergedLines = LineMerger.mergeLines(lineList, 1.0, MergeMethod.OVERLAP);
		Multiset<Double> widthSet = HashMultiset.create();
		Multiset<Double> lengthSet = HashMultiset.create();
		for (SVGLine mergedLine : mergedLines) {
			widthSet.add(Util.format(mergedLine.getStrokeWidth(), 2));
			lengthSet.add(Util.format(mergedLine.getLength(), 0));
		}
		List<Multiset.Entry<Double>> lengthEntryList = MultisetUtil.createDoubleListSortedByValue(lengthSet);
		LOG.debug(lengthEntryList);
		List<Multiset.Entry<Double>> widthEntryList = MultisetUtil.createDoubleListSortedByValue(widthSet);
		LOG.debug(widthSet);
	}

	/** rotate element positions position
	 * @throws FileNotFoundException 
	 * 
	 */
	@Test
	public void testReadGraphicsComponents() throws FileNotFoundException {
		String[] lengths = {
			"[147.0 x 2, 409.0 x 17]",
			"[198.0 x 15]",
			"[108.0, 118.0, 268.0 x 8]",
			"[268.0 x 15]",
			"[213.0, 293.0 x 22, 422.0 x 20]",
			"[213.0, 293.0 x 22, 422.0 x 20]",
		};
		String[] widths = {
			"[0.09 x 14, 0.23 x 4, 0.47]",
			"[0.09 x 12, 0.23 x 2, 0.47]",
			"[0.09 x 5, 0.23 x 4, 0.47]",
			"[0.09 x 12, 0.23 x 2, 0.47]",
			"[0.28 x 41, 0.34, 0.4]",
			"[0.28 x 41, 0.34, 0.4]",
		};
		int i = 0;
		for (File svgFile : RULED_FILES) {
			SVGStore svgStore = new SVGStore();
			svgStore.readGraphicsComponents(svgFile);
			SVGElement svgElement = (SVGElement) svgStore.getExtractedSVGElement();
			List<SVGLine> lineList = SVGLine.extractSelfAndDescendantLines(svgElement);
			List<SVGLine> mergedLines = LineMerger.mergeLines(lineList, 1.0, MergeMethod.OVERLAP);
			Multiset<Double> widthSet = HashMultiset.create();
			Multiset<Double> lengthSet = HashMultiset.create();
			for (SVGLine mergedLine : mergedLines) {
				widthSet.add(Util.format(mergedLine.getStrokeWidth(), 2));
				lengthSet.add(Util.format(mergedLine.getLength(), 0));
			}
			List<Multiset.Entry<Double>> lengthEntryList = MultisetUtil.createDoubleListSortedByValue(lengthSet);
			Assert.assertEquals("length "+i, lengths[i], lengthEntryList.toString());
			List<Multiset.Entry<Double>> widthEntryList = MultisetUtil.createDoubleListSortedByValue(widthSet);
			Assert.assertEquals("length "+i, widths[i], widthEntryList.toString());
			i++;
			
		}
	}

	// ==============================================
	
	private void debugLines(List<SVGLine> lineList, File svgOutfile) {
		SVGG g = new SVGG();
		for (SVGLine line : lineList) {
			g.appendChild(line.copy());
			g.appendChild(new SVGCircle(line.getXY(0), 2.0));
			g.appendChild(new SVGCircle(line.getXY(1), 2.0));
		}
		SVGSVG.wrapAndWriteAsSVG(g, svgOutfile);
	}
	
	private Real2Range makeResizedBbox(SVGRect rect, int delta) {
		Real2Range rectBox = rect.getBoundingBox();
		rectBox.extendBothEndsBy(Direction.HORIZONTAL, delta, delta);
		rectBox.extendBothEndsBy(Direction.VERTICAL, delta, delta);
		return rectBox;
	}
}
