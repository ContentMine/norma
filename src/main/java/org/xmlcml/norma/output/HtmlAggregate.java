package org.xmlcml.norma.output;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.xmlcml.cproject.files.CTree;
import org.xmlcml.cproject.files.RegexPathFilter;
import org.xmlcml.html.HtmlTable;
import org.xmlcml.html.HtmlTr;
import org.xmlcml.xml.XMLUtil;

/** aggregates a number of files into a single (display) table.
 * Still under development. May interact with HtmlDisplay
 * 
 * @author pm286
 *
 */
public class HtmlAggregate extends HtmlDisplay {
	private static final Logger LOG = Logger.getLogger(HtmlAggregate.class);
	
	static {
		LOG.setLevel(Level.DEBUG);
	}
	
	public HtmlAggregate() {
		super();
	}

	public HtmlAggregate(List<String> displayFilterStrings) {
		this.createDisplayFilters(displayFilterStrings);
		if (displayFilterStrings.size() == 1) {
			fileFilterPattern = Pattern.compile(displayFilterStrings.get(0));
		}
	}

	/** create row, and write to HTML .
	 * 
	 */
	@Override
	public void display() {
		if (output != null) {
			List<File> files = new RegexPathFilter(fileFilterPattern).listNonDirectoriesRecursively(cTree.getDirectory());
			LOG.debug("files: "+files.size() + " "+cTree.getDirectory()+"; "+fileFilterPattern);
			HtmlTabbedButtonDisplay htmlButtonDisplay = new HtmlTabbedButtonDisplay(files);
			try {
				File outputFile = new File(cTree.getDirectory(), this.output);
				LOG.debug("output to "+outputFile.getAbsolutePath());
//				XMLUtil.debug(htmlButtonDisplay, outputFile, 1);
				XMLUtil.debug(htmlButtonDisplay, outputFile, 0);
			} catch (IOException e) {
				throw new RuntimeException("Cannot write "+output, e);
			}
		}
	}

}
