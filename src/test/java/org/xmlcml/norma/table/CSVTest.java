package org.xmlcml.norma.table;

import java.io.File;
import java.nio.charset.Charset;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.xmlcml.cproject.util.CMineTestFixtures;
import org.xmlcml.graphics.html.HtmlHtml;
import org.xmlcml.graphics.html.HtmlTable;
import org.xmlcml.norma.Norma;
import org.xmlcml.norma.NormaFixtures;
import org.xmlcml.xml.XMLUtil;

import net.minidev.json.JSONObject;

public class CSVTest {
	
	public static final Logger LOG = Logger.getLogger(CSVTest.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}

	@Test
	public void testReadCSV() throws Exception {
		CSVTransformer csvTransformer = new CSVTransformer();
		csvTransformer.readFile(new File(NormaFixtures.TEST_TABLE_DIR, "table.csv"));
		HtmlTable table = csvTransformer.createTable();
		HtmlHtml html = HtmlHtml.createUTF8Html();
		html.getOrCreateBody().appendChild(table);
		XMLUtil.debug(html, new File("target/table/table.html"), 1);
	}

	@Test
	public void testCSV2TSV() throws Exception {
		CSVTransformer csvTransformer = new CSVTransformer();
		csvTransformer.readFile(new File(NormaFixtures.TEST_TABLE_DIR, "table.csv"));
		String tsvString = csvTransformer.createTSV();
		FileUtils.write(new File("target/table/table.tsv"), tsvString, Charset.forName("UTF-8"));
	}

	@Test
	public void testCSV2JSON() throws Exception {
		CSVTransformer csvTransformer = new CSVTransformer();
		csvTransformer.readFile(new File(NormaFixtures.TEST_TABLE_DIR, "table.csv"));
		JSONObject object= csvTransformer.createJSON();
		FileUtils.write(new File("target/table/table.json"), object.toJSONString(), Charset.forName("UTF-8"));
	}

	@Test
	public void testTransformCSV() throws Exception {
		File targetDir= new File("target/table/project1/");
		File projectDir = new File(NormaFixtures.TEST_TABLE_DIR, "project1");
		CMineTestFixtures.cleanAndCopyDir(projectDir, targetDir);
		String args = "--project "+targetDir.toString()+" --transform csv2html";
		Norma norma = new Norma();
		norma.run(args);
	}

	@Test
	public void testMoveCSV() throws Exception {
		File targetDir= new File("target/table/project2/");
		File projectDir = new File(NormaFixtures.TEST_TABLE_DIR, "project2");
		CMineTestFixtures.cleanAndCopyDir(projectDir, targetDir);
		String args = "--project "+targetDir.toString()+" --move csv,table/";
		Norma norma = new Norma();
		norma.run(args);
	}

	@Test
	public void testMoveAndProcessTFCSV() throws Exception {
		String args;
		Norma norma;
		File targetDir= new File("target/table/joer/");
		File projectDir = new File(NormaFixtures.TEST_TABLE_DIR, "joer");
		CMineTestFixtures.cleanAndCopyDir(projectDir, targetDir);
		args = "--project "+targetDir.toString()+" --move csv,table/";
		norma = new Norma();
		norma.run(args);
		args = "--project "+targetDir.toString()+" --transform csv2html";
		norma = new Norma();
		norma.run(args);
	}


}
