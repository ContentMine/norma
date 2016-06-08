package org.xmlcml.norma.download;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.xmlcml.cmine.util.CMineGlobber;
import org.xmlcml.cmine.util.CMineUtil;
import org.xmlcml.norma.Norma;
import org.xmlcml.norma.download.CrossRefDownloader.Type;

public class CrossRefTest {

	private static final String XREF_DIR = "/Users/pm286/workspace/cmdev/norma-dev/xref";
	private static final Logger LOG = Logger.getLogger(CrossRefTest.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}



	@Test
	public void testCreateDownloadUrl() throws IOException {
		CrossRefDownloader downLoader = new CrossRefDownloader();
		downLoader.getOrCreateFilter().setFromPubDate("2016-05-02");
		downLoader.getOrCreateFilter().setUntilPubDate("2016-05-03");
		downLoader.getOrCreateFilter().setType(Type.JOURNAL_ARTICLE);
		downLoader.setQuery("psychology");
		downLoader.setRows(1000);
		URL url = downLoader.getURL();
		LOG.debug(url);
		List<String> urlList = downLoader.getUrlList();
		LOG.debug(urlList);
		File targetDir = new File("target/pubstyle/xref");
		targetDir.mkdirs();
		IOUtils.writeLines(urlList, "\n", new FileOutputStream(new File(targetDir, "psych20160502.txt")));

	}

	@Test
	public void testCreateDownloadAgro() throws IOException {
		String fromDate = "2010-05-02";
		String untilDate = "2016-05-03";
		String query = "Puccinia";
		CrossRefDownloader.runCrossRefQuery(fromDate, untilDate, query, true, 500, new File("target/xref/puccinia"));
	}
	

	@Test
	public void testCreateDownloadTheoChem() throws IOException {
		String fromDate = "2016-04-03";
		String untilDate = "2016-05-03";
		String query = "theoretical+chemistry";
		CrossRefDownloader.runCrossRefQuery(fromDate, untilDate, query, true, 50, new File("target/xref/theochem"));
	}

	@Test
	public void testCreateDownloadGlutamate() throws IOException {
		String fromDate = "2016-04-03";
		String untilDate = "2016-05-03";
		String query = "glutamate";
		CrossRefDownloader.runCrossRefQuery(fromDate, untilDate, query, true, 50, new File("target/xref/glutamate"));
	}

	@Test
	public void testQuickscrape() throws IOException {
		File scrapers = new File("workspace/journalScrapers/scrapers");
		File outdir = new File("target/crossref");
		String CROSSREF = "/Users/pm286/.nvm/v0.10.38/bin/quickscrape";
	    Process process = CMineUtil.runProcess(
	    		new String[]{CROSSREF, "-q", "http://dx.plos.org/10.1371/journal.pone.0075293", "-d", scrapers.toString(), "-o", outdir.toString()}, null);
	}

	@Test
	// @Ignore non-public content
	public void testTransformDownloads() {
		File targetDir = new File("xref/sage");
		File sageXsl = new File("src/main/resources/org/xmlcml/norma/pubstyle/sage/toHtml.xsl");
		LOG.debug(sageXsl.getAbsolutePath()+"; "+sageXsl.exists());
		String args = "--project "+targetDir.toString()+" -i fulltext.html --html jsoup -o fulltext.xhtml";
		Norma norma = new Norma();
		norma.run(args);
		args = "--project "+targetDir.toString()+" -i fulltext.xhtml --transform "+sageXsl+" -o scholarly.html";
		norma = new Norma();
		norma.run(args);
		
	}
	
	@Test
	public void testResolveDOI() throws Exception {
		String crossRefDOI = "http://dx.doi.org/10.3389/fpsyg.2016.00565";
		String s = new DOIResolver().resolveDOI(crossRefDOI);
		LOG.debug(s);
	}

	@Test
	public void testResolveDOIs() throws Exception {
		File doiFile = new File("/Users/pm286/workspace/cmdev/norma-dev/epmc/puccinia/Puccinia2010-05-02.txt");
		File resolvedDois = new File("/Users/pm286/workspace/cmdev/norma-dev/epmc/puccinia/Puccinia2010-05-02.resolved.txt");
		List<String> dois = FileUtils.readLines(doiFile);
		List<String> urls = new DOIResolver().resolveDOIs(dois);
	}

	@Test
	public void testCreateDaily() throws IOException {
		String fromDate = "2016-05-02";
		String untilDate = "2016-05-03";
		String query = null;
		boolean resolveDois = true;
		boolean skipHyphenDois = true;
		int rows = 10;
		int offset = 0;
		for (int j = 0; j < 5; j++) {
			CrossRefDownloader.runCrossRefDate(fromDate, untilDate, resolveDois, rows, offset, new File("xref/daily/20100601/"));
			offset += rows;
		}
	}

	@Test
	public void testCreateDaily100Chunks() throws IOException {
		String fromDate = "2016-05-01";
		String untilDate = "2016-05-02";
		int rows = 100;
		int currentPos = 0;
		boolean resolveDois = true;
		BlackList blackList = new BlackList(new File(XREF_DIR, "blacklist.txt"));
		File dailyDir = new File(XREF_DIR, "daily/");
		currentPos = CrossRefDownloader.createChunksAndWrite(dailyDir, fromDate, untilDate, rows, currentPos, resolveDois, blackList);
	}


	@Test
	public void testAnalyzeFilesAndCProjects() throws IOException {
		String fromDate = "2016-05-02";
		int delta = 100;
		File projectTop = new File(XREF_DIR, "daily/"+fromDate+"/");

		CMineGlobber urlsGlobber = new CMineGlobber("glob:**/"+fromDate+"/"+fromDate.replaceAll("\\-", "")+"_*"+"."+CrossRefDownloader.URLS_TXT, projectTop);
		CrossRefDownloader.analyzeFilesAndCProjects("glob:**/results.json", urlsGlobber);
	}

	@Test
	public void testCreateFilter() throws IOException {
		String fromDate = "2016-05-02";
		int delta = 100;
		File projectTop = new File(XREF_DIR, "daily/"+fromDate+"/");

		CMineGlobber globber = new CMineGlobber("glob:**/"+fromDate+"/"+fromDate.replaceAll("\\-", "")+"*"+"."+CrossRefDownloader.URLS_TXT, projectTop);
		List<File> urlList = globber.listFiles();
		
		BlackList blackList = new BlackList(new File(XREF_DIR, "blacklist.txt"));
		
		CrossRefDownloader.writeFilteredURLs(urlList, blackList);
	}
	
	// ==========================
	

	private String createSubPath(String fromDate, int count, int delta) {
		String fromDateMin = fromDate.replaceAll("\\-", "");
		return fromDate+"/"+fromDateMin+"_"+count+"_"+delta;
	}



}
