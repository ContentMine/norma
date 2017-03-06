package org.xmlcml.norma.output;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.xmlcml.cproject.files.CTree;
import org.xmlcml.cproject.files.RegexPathFilter;
import org.xmlcml.cproject.util.Utils;
import org.xmlcml.euclid.Util;
import org.xmlcml.graphics.svg.SVGElement;
import org.xmlcml.html.HtmlElement;
import org.xmlcml.html.HtmlImg;
import org.xmlcml.html.HtmlP;
import org.xmlcml.html.HtmlTable;
import org.xmlcml.html.HtmlTd;
import org.xmlcml.html.HtmlTr;
import org.xmlcml.xml.XMLUtil;

/** creates HTML output for display
 * 
 * @author pm286
 *
 */
public class HtmlDisplay {
	private static final Logger LOG = Logger.getLogger(HtmlDisplay.class);

	static {
		LOG.setLevel(Level.DEBUG);
	}

	private String output;
	private List<RegexPathFilter> displayFilters;
	private CTree cTree;

	private HtmlDisplay() {
	}

	public HtmlDisplay(List<String> displayFilterStrings) {
		this.createDisplayFilters(displayFilterStrings);
	}

	public HtmlTr createHtmlTr() {
		if (cTree == null) {
			LOG.error("no CTree");
			return null;
		}
		if (output == null) {
			LOG.error("no output");
			return null;
		}
		HtmlTr tr = new HtmlTr();
		for (RegexPathFilter displayFilter : displayFilters) {
//			LOG.debug("dir "+cTree.getDirectory());
//			LOG.debug("filter "+displayFilter);
			File[] files = Utils.getFilesWithFilter(cTree.getDirectory(), displayFilter);
			File file = null;
			if (files.length > 0) {
				file = files[0];
			}
			HtmlTd td = createTd(file);
			tr.appendChild(td);
		}
		return tr;
	}

	private HtmlTd createTd(File file) {
		HtmlTd td = new HtmlTd();
		String filename = file == null ? null : file.getName();
		if (file == null) {
			td.appendChild(new HtmlP("null file"));
		} else if (filename.endsWith(".svg")) {
			SVGElement svgElement = SVGElement.readAndCreateSVG(file);
			td.appendChild(svgElement);
		} else if (filename.endsWith(".png")) {
			HtmlImg img = createHtmlImg(file);
			td.appendChild(img);
		} else if (filename.endsWith(".html")) {
			HtmlElement htmlElement = HtmlElement.create(XMLUtil.parseQuietlyToDocument(file).getRootElement());
//			LOG.debug("HTML "+htmlElement.toXML());
			td.appendChild(htmlElement);
		} else {
			td.appendChild(new HtmlP("unknown"));
		}
//		String name = filename == null ? "null" : filename;
		String relativeToHome = file == null ? " null" : Util.getRelativeFilename(new File("."), file, File.separator);
		td.appendChild(new HtmlP(relativeToHome));
		return td;
	}

	private HtmlImg createHtmlImg(File file) {
		HtmlImg img = new HtmlImg();
		String relativeFilename = Util.getRelativeFilename(cTree.getDirectory(), file, File.separator);
		img.setSrc(relativeFilename);
		return img;
	}

	private void createDisplayFilters(List<String> displayFilterStrings) {
		displayFilters = new ArrayList<RegexPathFilter>();
		for (String displayFilterString : displayFilterStrings) {
			displayFilters.add(new RegexPathFilter(displayFilterString));
		}
	}

	public void setOutput(String output) {
		this.output = output;
	}

	public void setCTree(CTree currentCTree) {
		this.cTree = currentCTree;
	}

	public void display() {
		if (output != null) {
			HtmlTable table = new HtmlTable();
			HtmlTr tr = createHtmlTr();
			table.appendChild(tr);
			try {
				File outputFile = new File(cTree.getDirectory(), this.output);
				LOG.debug("output to "+outputFile.getAbsolutePath());
				XMLUtil.debug(table, outputFile, 1);
			} catch (IOException e) {
				throw new RuntimeException("Cannot write "+output, e);
			}
		}
	}
	
}
