package org.xmlcml.norma.util;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.xmlcml.cmine.files.CProject;
import org.xmlcml.cmine.files.CTree;
import org.xmlcml.norma.NormaArgProcessor;
import org.xmlcml.xml.XMLUtil;

import nu.xom.Document;

public class NormaTestFixtures {
	private static final Logger LOG = Logger.getLogger(NormaTestFixtures.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}
	


	public static void checkScholarlyHtml(File target, String start) {
		CTree cTree = new CTree(target);
		File shtml = cTree.getExistingScholarlyHTML();
		if (shtml == null || !shtml.exists()) {
			throw new RuntimeException("No SHTML found "+target);
		}
		Document doc = XMLUtil.parseQuietlyToDocument(shtml);
		if (doc == null) {
			throw new RuntimeException("Cannot parse as ScholarlyHtml "+shtml);
		}
		String text = doc.toXML();
		text = text.replaceAll("\\n\\s*", "");
		String tesc = text.replaceAll("\"", "\\\\\\\"");
		if (!text.startsWith(start)) {
			String t300 = tesc.substring(0,  Math.min(300, text.length()));
			LOG.debug("\n"+t300);
			throw new RuntimeException("ScholarlyHtml does not start correctly: "+shtml+":: "+ text);
		}
	}

	public static void cleanAndCopyDir(File sourceDir, File targetDir) throws IOException {
		if (targetDir.exists()) FileUtils.forceDelete(targetDir);
		FileUtils.copyDirectory(sourceDir, targetDir);
	}

	/**
	 * 
	 * @param dir
	 * @param type "project" or "ctree"
	 * @param transform transform (nlm2html, etc.)
	 */
	public static void runNorma(File dir, String type, String transform) {
		// norma-lise
		// check if already normalized
		String args = null;
		if (type.equals("project")) {
			CProject project = new CProject(dir);
			List<CTree> cTreeList = project.getCTreeList();
			for (CTree cTree : cTreeList) {
				if (!cTree.hasScholarlyHTML()) {
					// mising SHTML, normalize all
					args = "-i fulltext.xml  --transform "+transform+" -o scholarly.html --"+type+" "+dir;
					break;
				}
			}
		} else if (type.equals("ctree")) {
			CTree cTree = new CTree(dir);
			if (!cTree.hasScholarlyHTML()) {
				// mising SHTML, normalize all
				args = "-i fulltext.xml  --transform "+transform+" -o scholarly.html --"+type+" "+dir;
			}
		}
		if (args != null) {
			NormaArgProcessor argProcessor = new NormaArgProcessor(args);
			argProcessor.runAndOutput();
		}
	}

}
