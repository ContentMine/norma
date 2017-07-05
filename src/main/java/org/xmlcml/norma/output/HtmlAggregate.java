package org.xmlcml.norma.output;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.xmlcml.cproject.files.RegexPathFilter;
import org.xmlcml.euclid.Util;

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
			Util.sortByEmbeddedInteger(files);
			File outputFile = new File(cTree.getDirectory(), this.output);
			LOG.debug("files: "+files.size() + " "+cTree.getDirectory()+"; "+fileFilterPattern+"; "+outputFile);
			HtmlTabbedButtonDisplay htmlButtonDisplay = new HtmlTabbedButtonDisplay(cTree.getDirectory().getName(), files, outputFile.getParentFile());
			String htmlText = new HtmlTextifier().textify(htmlButtonDisplay);
			try {
				LOG.trace("output to "+outputFile.getAbsolutePath());
				FileUtils.writeStringToFile(outputFile, htmlText);
			} catch (IOException e) {
				throw new RuntimeException("Cannot write "+output, e);
			}
		}
	}

}
