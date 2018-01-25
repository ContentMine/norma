package org.xmlcml.norma.pubstyle.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.plaf.synth.Region;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.xmlcml.cproject.util.CMineGlobber;
import org.xmlcml.euclid.Real2Range;
import org.xmlcml.graphics.svg.SVGElement;
import org.xmlcml.graphics.svg.SVGSVG;
import org.xmlcml.graphics.svg.SVGUtil;
import org.xmlcml.svg2xml.page.PageCropper;

/** finds boxes by style
 * 
 * @author pm286
 *
 */
public class RegionFinder {
	private static final Logger LOG = Logger.getLogger(RegionFinder.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}

	private static final String BLOCK_ROOT = "block";
	private static final String BLOCKS_BLOCK = "blocks/block";
	private static final String FULLTEXT_SVG_REGEX = ".*/svg/fulltext\\-page\\d+\\.svg";
	private static final String FULLTEXT_PAGE = "fulltext-page";

//	private List<String> xPathList;
	private SVGElement svgElement;
	private String xPath;
	private String outdir;
	private PageCropper pageCropper;

	private List<PageRegion> pageRegionList;
	private File svgDir;
	private File ctreeDirectory;


	public RegionFinder() {
		getOrCreatePageCropper();
	}

	public void setXPath(String xPath) {
		this.xPath = xPath;
	}
	
	public PageCropper getOrCreatePageCropper() {
		if (pageCropper == null) {
			pageCropper = new PageCropper();
		}
		return pageCropper;
	}

	public void setSVGElement(SVGElement svgElement) {
		this.svgElement = svgElement;
	}

	public List<SVGElement> findXPathRegions(SVGElement svgElement) {
		this.setSVGElement(svgElement);
		List<SVGElement> subElementList = SVGUtil.getQuerySVGElements(svgElement, xPath);
		return subElementList;
	}

	public void setRegionPathFill(String regionColor) {
		String xpath = "*[local-name()='path' and contains(@style, 'fill:" + regionColor + ";')]";
		setXPath(xpath);
	}

	public void setOutputDir(String outdir) {
		this.outdir = outdir;
	}

	public List<PageRegion> findRegions(File ctreeDirectory) throws IOException {
		this.ctreeDirectory = ctreeDirectory;
		pageRegionList = new ArrayList<PageRegion>();
		pageCropper = getOrCreatePageCropper();
		CMineGlobber globber = new CMineGlobber();
		globber.setRegex(FULLTEXT_SVG_REGEX);
		globber.setLocation(ctreeDirectory.toString());
		List<File> svgFiles = globber.listFiles();
		svgDir = new File(ctreeDirectory, "svg");
		for (int pageNumber = 1; pageNumber < svgFiles.size(); pageNumber++) {
			findPageRegions(pageNumber);
		}
		return pageRegionList;
	}

	private void findPageRegions(int pageNumber) {
		File svgFile = getFulltextSVGFile(svgDir, pageNumber);
		SVGElement svgElement = SVGElement.readAndCreateSVG(svgFile);
		List<SVGElement> subElementList = findXPathRegions(svgElement);
		if (subElementList.size() > 0) {
			LOG.debug("page "+pageNumber+": "+subElementList.size());
			int section = 1;
			for (SVGElement subElement : subElementList) {
				Real2Range bbox = subElement.getBoundingBox().format(0);
				pageCropper.setSVGElementCopy(svgElement);
				pageCropper.setTLBRUserCropBox(bbox);
				pageCropper.detachElementsOutsideBox();
				SVGElement svgElement0 = pageCropper.getSVGElement();
				LOG.debug(bbox + "/" +svgElement0.getBoundingBox());
				if (svgElement0 != null) {
					PageRegion region = new PageRegion(pageNumber, section, svgElement0);
					pageRegionList.add(region);
				}
			}
		}
	}

	private File getFulltextSVGFile(File svgDir, int pageNumber) {
		return new File(svgDir, FULLTEXT_PAGE+ pageNumber + ".svg");
	}
	
	public void writeBlockList(File ctree) {
		for (PageRegion pageRegion : pageRegionList) {
			SVGSVG.wrapAndWriteAsSVG(pageRegion.getSvgElement(),
					new File(new File(outdir, ctreeDirectory.getName()),
							BLOCKS_BLOCK+"."+pageRegion.getPageNumber()+"/"+BLOCK_ROOT+(pageRegion.getSection())+".svg"));
		}
	}
}
