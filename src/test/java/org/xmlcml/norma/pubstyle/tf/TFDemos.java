package org.xmlcml.norma.pubstyle.tf;

import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.xmlcml.cproject.files.CProject;
import org.xmlcml.cproject.files.CTree;
import org.xmlcml.cproject.files.CTreeList;
import org.xmlcml.cproject.util.CMineTestFixtures;
import org.xmlcml.cproject.util.CMineUtil;
import org.xmlcml.graphics.html.HtmlElement;
import org.xmlcml.graphics.html.HtmlFactory;
import org.xmlcml.graphics.html.HtmlTable;
import org.xmlcml.graphics.html.HtmlTd;
import org.xmlcml.graphics.html.HtmlTh;
import org.xmlcml.graphics.html.HtmlTr;
import org.xmlcml.norma.Norma;
import org.xmlcml.norma.cproject.HtmlTidier;
import org.xmlcml.xml.XMLUtil;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multiset.Entry;

import nu.xom.Element;
import nu.xom.Node;

public class TFDemos {
	
	private static final Logger LOG = Logger.getLogger(TFDemos.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}
	
	public static void main(String[] args) {
		testConvertJOER();
	}
	
	public static void testConvertJOER() {
		File targetDir = new File("target/tutorial/joer");
		CMineTestFixtures.cleanAndCopyDir(new File("../../../phyto/joer"), targetDir);
		HtmlTidier htmlTidier = new HtmlTidier(targetDir);
		htmlTidier.htmlTidy("jsoup"); 
		htmlTidier.xslTidy("tf2html"); 
		moveCSVToTableDir(targetDir);
		transformTables2Html(targetDir);
		
		CProject project = new CProject(targetDir);
		CTreeList ctrees = project.getResetCTreeList();
		List<HtmlTable> tables = new ArrayList<HtmlTable>();
		List<HtmlElement> rows = new ArrayList<HtmlElement>();
		StringBuilder captionBuilder = new StringBuilder();
		for (CTree ctree : ctrees) {
			File directory = ctree.getDirectory();
			File tableDir = new File(directory, CProject.TABLE);
			String[] files = extractTableStarHtml(tableDir);
			HtmlTr tr = new HtmlTr();
			HtmlTd td = new HtmlTd();
			tr.appendChild(td);
			td.appendChild(directory.getName());
			rows.add(tr);
			List<String> filenames = files == null ? new ArrayList<String>() : Arrays.asList(files);
			createHtmlTables(tables, rows, tableDir, filenames);
			File scholarlyHtmlFile = ctree.getExistingScholarlyHTML();
			if (scholarlyHtmlFile != null) {
				String captions = getTableCaptions(scholarlyHtmlFile, directory);
				captionBuilder.append(captions);
			}
		}
		debugTermsInTh(rows, "target/tutorial/joer/terms.txt");
		debugTh(tables, "target/tutorial/joer/rows.txt");
		List<List<String>> values = extractColumns(tables, "compound", "component");
		Multiset<String> stringSet = removeNumbersAndNulls(values);
		writeSortedEntries("target/tutorial/joer/chemicals.txt", stringSet);
		writeCaptions(captionBuilder, "target/tutorial/joer/tables.txt");

	}

	private static void writeCaptions(StringBuilder sb, String outfileName) {
		try {
			FileWriter fw = new FileWriter(outfileName);
			fw.write(sb.toString());
			fw.close();
		} catch (IOException e) {
			throw new RuntimeException("cannot write captions ", e);
		}
	}

	private static String getTableCaptions(File scholarlyHTMLFile, File directory) {
		//<p><span class="captionLabel">Table 1. </span> Density (mg/mL) and essential oils chemical compounds (%) obtained by chromatography–mass spectrometry (GC–MS) from the supplier of the essential oils (By Samia Aromatherapy/São Paulo/Brazil).
		HtmlElement htmlElement = null;
		try {
			htmlElement = new HtmlFactory().parse(scholarlyHTMLFile);
		} catch (Exception e) {
			throw new RuntimeException("Cannot create HTML: ", e);
		}
		StringBuilder sb = new StringBuilder();
		List<Element> pList = XMLUtil.getQueryElements(htmlElement, 
				".//*[local-name()='p' and *[local-name()='span' and @class='captionLabel']]");
		for (Element p : pList) {
			List<Element> spanList = XMLUtil.getQueryElements(p, 
					".//*[local-name()='span' and @class='captionLabel']");
			for (Element span : spanList) {
				span.detach();
			}
			String s = directory.getName()+": "+spanList.get(0).getValue()+": "+p.getValue();
			sb.append(s+"\n");
		}
		return sb.toString();
	}

	private static Multiset<String> removeNumbersAndNulls(List<List<String>> stringListList) {
		Multiset<String> multiset = HashMultiset.create();
		for (List<String> stringList : stringListList) {
			for (String string : stringList) {
				if (string != null && string.trim().length() > 0) {
					try {
						new Double(string);
					} catch (NumberFormatException e) {
						multiset.add(string); 
					}
				}
			}
		}
		return multiset;
	}

	/** finds columns with Th headers containingIgnoreCase any word from a list.
	 * may contain null values
	 * 
	 * @param tables
	 * @param queryList
	 * @return
	 */
	private static List<List<String>> extractColumns(List<HtmlTable> tables, String ... queryList) {
		List<List<String>> valuesList = new ArrayList<List<String>>();
		for (HtmlTable table :tables) {
			HtmlTr trth = table.getSingleLeadingTrThChild();
			int jCol = getColumn(trth, queryList);
			if (jCol != -1) {
				List<String> values = getColumnTdValues(table, jCol);
				valuesList.add(values);
			}
		}
		return valuesList;
	}

	private static List<String> getColumnTdValues(HtmlTable table, int jCol) {
		List<HtmlTr> trtdList = table.getTrTdRows();
		List<String> colValues = new ArrayList<String>();
		for (HtmlTr tr : trtdList) {
			HtmlTd td = (HtmlTd) tr.getTd(jCol);
			String tdValue = td == null ? null : td.getValue();
			colValues.add(tdValue);
		}
		return colValues;
	}

	private static int getColumn(HtmlTr trth, String ... searchHeadings) {
		LOG.trace(trth.toXML());
		List<HtmlTh> thChildren = trth.getThChildren();
		for (int jCol = 0; jCol < thChildren.size(); jCol++) {
			String headingValue = thChildren.get(jCol).getValue().toLowerCase();
			LOG.trace(headingValue);
			for (int i = 0; i < searchHeadings.length; i++) {
				String searchHeading = searchHeadings[i].toLowerCase();
				if (headingValue.startsWith(searchHeading)) {
					return jCol;
				}
			}
		}
		return -1;
	}

	private static void debugTermsInTh(List<HtmlElement> rows, String filename) {
		Multiset<String> set = HashMultiset.create();
		for (HtmlElement row : rows) {
			List<Node> nodes = XMLUtil.getQueryNodes(row, ".//text()");
			for (Node node : nodes) {
				String s = node.getValue().trim();
				if (!s.startsWith("http")) {
					set.add(s);
				}
			}
		}
		writeSortedEntries(filename, set);
	}

	private static void writeSortedEntries(String filename, Multiset<String> set) {
		Iterable<Multiset.Entry<String>> entrys = CMineUtil.getEntriesSortedByCount(set);
		Iterator<Entry<String>> iterator = entrys.iterator();
		try {
			FileWriter fw = new FileWriter(filename);
			while (iterator.hasNext()) {
				String ss = iterator.next().toString();
				fw.write(ss+"\n");
			}
			fw.close();
		} catch (IOException e) {
			LOG.warn("cannot write file "+e);
		}
	}

	private static void debugTh(List<HtmlTable> tables, String filename) {
		try {
			FileWriter fw = new FileWriter(filename);
			for (HtmlTable table : tables) {
				HtmlTr th = table.getSingleLeadingTrThChild();
				if (th != null){
					String ss = th.toXML();
					ss = ss.replaceAll("</?tr>", "");
					ss = ss.replaceAll("</th><th>", " | ");
					ss = ss.replaceAll("</?th>", "");
					fw.write(ss+"\n");
				}
			}
			fw.close();
		} catch (IOException e) {
			LOG.warn("cannot write file "+e);
		}
	}

	private static void transformTables2Html(File targetDir) {
		String args = "--project "+targetDir.toString()+" --transform csv2html";
		Norma norma = new Norma();
		norma.run(args);
	}

	private static void moveCSVToTableDir(File targetDir) {
		String args = "--project "+targetDir.toString()+" --move csv,table/";
		Norma norma = new Norma();
		norma.run(args);
	}

	private static void createHtmlTables(List<HtmlTable> tables, List<HtmlElement> rows, File tableDir,
			List<String> filenames) {
		for (String filename : filenames) {
			File file = new File(tableDir, filename);
			try {
				HtmlElement htmlElement = new HtmlFactory().parse(file);
				List<HtmlTable> htmlTables = HtmlTable.extractTables(htmlElement, "//*[local-name()='table']");
				if (htmlTables.size() == 1) {
					HtmlTable table = htmlTables.get(0);
					tables.add(table);
					List<Element> elements = XMLUtil.getQueryElements(table, "//*[local-name()='tr' and *[local-name()='th']]");
					if (elements.size() > 0) {
						rows.add((HtmlElement)elements.get(0));
					}
				}
			} catch (Exception e) {
				LOG.warn("skipped file: "+file+"; "+e);
			}
		}
	}

	private static String[] extractTableStarHtml(File tableDir) {
		;			String[] files = tableDir.list(new FilenameFilter() {
						public boolean accept(File dir, String name) {
							return name.startsWith("table") || name.endsWith(".html");
						}
					});
		return files;
	}


}
