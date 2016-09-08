package org.xmlcml.norma;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class NormaUtil {

	public static final Logger LOG = Logger.getLogger(NormaUtil.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}

	private static final String HTML_START = "<";
	private static final String PDF_MAGIC = "%PDF";

	public static String getStringFromInputFile(File file) {
		String s = null;
		try {
			s = FileUtils.readFileToString(file);
		} catch (Exception e) {
			// consume exception
		}
		return s;
	}

	public static boolean isHtmlContent(String s) {
		return s.startsWith(HTML_START);
	}

	public static boolean isPDFContent(String s) {
		return s.startsWith(PDF_MAGIC);
	}
	
	public static boolean isPDF(String name) {
		return name.toLowerCase().endsWith(InputFormat.PDF.toString().toLowerCase());
	}


}
