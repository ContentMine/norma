package org.xmlcml.norma.output;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
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
	private Pattern fileFilterPattern;
	private File currentDirectory;

	private HtmlDisplay() {
	}

	public HtmlDisplay(List<String> displayFilterStrings) {
		this.createDisplayFilters(displayFilterStrings);
	}

	/** create row, and write to HTML .
	 * 
	 */
	public void display() {
		if (output != null) {
			List<File> files = new RegexPathFilter(fileFilterPattern).listDirectoriesRecursively(cTree.getDirectory());
			LOG.debug("files: "+files.size() + " "+cTree.getDirectory()+"; "+fileFilterPattern);
			for (File file : files) {
				currentDirectory = file;
				HtmlTable table = new HtmlTable();
				HtmlTr tr = createHtmlTr();
				table.appendChild(tr);
				try {
					File outputFile = new File(file, this.output);
					LOG.debug("output to "+outputFile.getAbsolutePath());
					XMLUtil.debug(table, outputFile, 1);
				} catch (IOException e) {
					throw new RuntimeException("Cannot write "+output, e);
				}
			}
		}
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
		HtmlTr tr = createTrWithTdCellsForRegexFilters();
		return tr;
	}

	private HtmlTr createTrWithTdCellsForRegexFilters() {
		HtmlTr tr = new HtmlTr();
		for (RegexPathFilter displayFilter : displayFilters) {
			File file = getSingleFile(displayFilter);
			if (file != null) {
				HtmlTd td = createTd(file);
				tr.appendChild(td);
				File directory = file.getParentFile();
				currentDirectory = directory;
			}
		}
		return tr;
	}

	private File getSingleFile(RegexPathFilter displayFilter) {
		File[] files = Utils.getFilesWithFilter(currentDirectory, displayFilter);
		File file = null;
		if (files.length > 0) {
			file = files[0];
		}
		return file;
	}

	private HtmlTd createTd(File file) {
		LOG.debug("processing TD "+file);
		HtmlTd td = new HtmlTd();
		String filename = file == null ? null : file.getName();
		if (file == null) {
			td.appendChild(new HtmlP("null file"));
		} else if (filename.endsWith(".svg")) {
			SVGElement svgElement = createSVG(file);
			td.appendChild(svgElement);
		} else if (filename.endsWith(".png")) {
			HtmlImg img = createHtmlImg(file.getName());
			td.appendChild(img);
		} else if (filename.endsWith(".html")) {
			HtmlElement htmlElement = createHtml(file);
			if (htmlElement != null) td.appendChild(htmlElement);
		} else {
			td.appendChild(new HtmlP("unknown"));
		}
//		String name = filename == null ? "null" : filename;
		String relativeToHome = file == null ? " null" : Util.getRelativeFilename(new File("."), file, File.separator);
		td.appendChild(new HtmlP(relativeToHome));
		return td;
	}

	private HtmlElement createHtml(File file) {
		HtmlElement htmlElement = HtmlElement.create(XMLUtil.parseQuietlyToDocument(file).getRootElement());
		List<HtmlTable> tables = HtmlTable.extractSelfAndDescendantTables(htmlElement);
		if (tables.size() == 1) {
			htmlElement = tables.get(0);
			htmlElement.detach();
			return htmlElement;
		} else {
			return new HtmlP("no single table");
		}
	}

	private SVGElement createSVG(File file) {
		SVGElement svgElement = SVGElement.readAndCreateSVG(file);
		// remove sodipodi inkscape //		<sodipodi:namedview
		XMLUtil.removeElementsByXPath(svgElement, "//*[local-name()='g' and @class='namedview']");
		XMLUtil.removeElementsByXPath(svgElement, "//*[local-name()='g' and @class='metadata']");
		// remove defs/clipPath
		XMLUtil.removeElementsByXPath(svgElement, "//*[local-name()='defs']/*[local-name()='clipPath']");
		
		return svgElement;
	}

	private HtmlImg createHtmlImg(File file) {
		String relativeFilename = Util.getRelativeFilename(cTree.getDirectory(), file, File.separator);
		return createHtmlImg(relativeFilename);
	}

	/** create img with relative filename.
	 * 
	 * @param filename (caller is responsible for anchoring this)
	 * @return
	 */
	private HtmlImg createHtmlImg(String filename) {
		HtmlImg img = new HtmlImg();
		img.setSrc(filename);
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

	public void setFileFilterPattern(Pattern fileFilterPattern) {
		this.fileFilterPattern = fileFilterPattern;
	}
	

}
