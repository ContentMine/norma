package org.xmlcml.norma.pubstyle.util;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.xmlcml.graphics.svg.SVGElement;

/** a region of a page.
 * remembers the SVGElement, the page and the sectionNumber
 * 
 * @author pm286
 *
 */
public class PageRegion {
	private static final Logger LOG = Logger.getLogger(PageRegion.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}

	private int pageNumber;
	private int section;
	private SVGElement svgElement;

	public PageRegion() {
		
	}

	public PageRegion(int pageNumber, int section, SVGElement svgElement0) {
		this.setPageNumber(pageNumber);
		this.setSection(section);
		this.setSvgElement(svgElement0);
	}
	
	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getSection() {
		return section;
	}

	public void setSection(int section) {
		this.section = section;
	}

	public SVGElement getSvgElement() {
		return svgElement;
	}

	public void setSvgElement(SVGElement svgElement) {
		this.svgElement = svgElement;
	}


}
