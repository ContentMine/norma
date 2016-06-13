package org.xmlcml.norma.download;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;
import org.xmlcml.cmine.files.CProject;
import org.xmlcml.cmine.files.CTreeList;
import org.xmlcml.cmine.util.CMineGlobber;
import org.xmlcml.cmine.util.CMineUtil;
import org.xmlcml.cmine.util.CSVWriter;
import org.xmlcml.norma.Norma;
import org.xmlcml.norma.download.CrossRefDownloader.Type;

import junit.framework.Assert;

public class CrossRefTest {

	private static final String XREF_DIR = "/Users/pm286/workspace/cmdev/norma-dev/xref";
	private static final Logger LOG = Logger.getLogger(CrossRefTest.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}



	@Test
	// uses net to call CrossRef
	/**
	 * searches with query "psychology" and Type.JOURNAL_ARTICLE between dates 2016-05-02 and 2016-05-03
	 * downloads as CSV file
	 * 
	 */
	public void testQueryCrossRefAndDownloadResults() throws IOException {
		CrossRefDownloader downLoader = new CrossRefDownloader();
		downLoader.getOrCreateFilter().setFromPubDate("2016-05-02");
		downLoader.getOrCreateFilter().setUntilPubDate("2016-05-03");
		downLoader.getOrCreateFilter().setType(Type.JOURNAL_ARTICLE);
		downLoader.setQuery("psychology");
		downLoader.setRows(1000);
		URL url = downLoader.getURL();
		LOG.debug("URL: "+url);
		List<String> urlList = downLoader.getUrlList();
		LOG.debug("downloaded: "+urlList.size());
		File targetDir = new File("target/pubstyle/xref");
		targetDir.mkdirs();
		IOUtils.writeLines(urlList, "\n", new FileOutputStream(new File(targetDir, "psych20160502.csv")));

	}

	@Test
	@Ignore // downloads many
	public void testCreateDownloadAgro() throws IOException {
		String fromDate = "2010-05-02";
		String untilDate = "2016-05-03";
		String query = "Puccinia";
		CrossRefDownloader.runCrossRefQuery(fromDate, untilDate, query, true, 500, new File("target/xref/puccinia"));
	}
	

	@Test
	@Ignore // downloads many
	public void testCreateDownloadTheoChem() throws IOException {
		String fromDate = "2016-04-03";
		String untilDate = "2016-05-03";
		String query = "theoretical+chemistry";
		CrossRefDownloader.runCrossRefQuery(fromDate, untilDate, query, true, 50, new File("target/xref/theochem"));
	}

	@Test
	@Ignore // downloads many
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
	 @Ignore //non-public content
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
	@Ignore //missing file
	public void testResolveDOIs() throws Exception {
		File doiFile = new File("/Users/pm286/workspace/cmdev/norma-dev/epmc/puccinia/Puccinia2010-05-02.txt");
		File resolvedDois = new File("/Users/pm286/workspace/cmdev/norma-dev/epmc/puccinia/Puccinia2010-05-02.resolved.txt");
		List<String> dois = FileUtils.readLines(doiFile);
		List<String> urls = new DOIResolver().resolveDOIs(dois);
	}

	@Test
	@Ignore // downloads
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
	@Ignore // downloads
	public void testCreateDaily100Chunks() throws IOException {
		String fromDate = "2016-05-01";
		String untilDate = "2016-05-02";
		int rows = 100;
		int currentPos = 0;
		boolean resolveDois = true;
		BlackList blackList = new BlackList(new File(XREF_DIR, "blacklist.txt"));
		File dailyDir = new File(XREF_DIR, "daily/");
		currentPos = CrossRefDownloader.runDailyDownload(dailyDir, fromDate, untilDate, rows, currentPos, resolveDois, blackList);
	}


	@Test
	public void testAnalyzeFilesAndCProjects() throws IOException {
		String fromDate = "2016-05-02";
		int delta = 100;
		File projectTop = new File(XREF_DIR, "daily/"+fromDate+"/");

		CMineGlobber urlsGlobber = new CMineGlobber("glob:**/"+fromDate+"/"+fromDate.replaceAll("\\-", "")+"_*"+"."+CrossRefDownloader.URLS_CSV, projectTop);
		CrossRefDownloader.analyzeFilesAndCProjects("glob:**/results.json", urlsGlobber);
	}

	@Test
	/** this creates a filter to remove blacklisted URLs.
	 * 
	 */
	public void testCreateAndApplyFilter() throws IOException {
		String fromDate = "2016-05-02";
		int delta = 100;
		File projectTop = new File(XREF_DIR, "daily/"+fromDate+"/");

		CMineGlobber globber = new CMineGlobber("glob:**/"+fromDate+"/"+fromDate.replaceAll("\\-", "")+"*"+"."+CrossRefDownloader.URLS_CSV, projectTop);
		List<File> urlList = globber.listFiles();
		LOG.debug("before filter: "+urlList.size());
		
		BlackList blackList = new BlackList(new File(XREF_DIR, "blacklist.txt"));
		
		List<File> filteredURLList = CrossRefDownloader.writeFilteredURLs(urlList, blackList);
		LOG.debug("after filter: "+filteredURLList.size());
	}
	
	
	@Test 
	public void testProjectSize() {
		String fromDate = "2016-05-01";
		String fromDate0 = fromDate.replaceAll("\\-", "");
		int start = 0;
		int delta = 100;
		File projectTop = new File(XREF_DIR, "daily/"+fromDate+"/");
		File cProjectDir = new File(projectTop, createSubPath0(fromDate, start, delta));
		CProject cProject = new CProject(cProjectDir);
		CTreeList cTreeList = cProject.getCTreeList();
		Assert.assertEquals("ctrees",  95, cTreeList.size());
		List<File> childDirs= cProject.getAllChildDirectoryList();
		Assert.assertEquals("ctrees",  96, childDirs.size());
	}
	
	@Test 
	public void testProjectSizeAll() throws IOException {
		int MAX = 99999;
		String fromDate = "2016-05-01";
		int start = 0;
		int delta = 100;
		File pubFilterFile = new File(XREF_DIR, "pubFilter.txt");
		File projectTop = new File(XREF_DIR, "daily/"+fromDate+"/");
		List<List<String>> rows = new ArrayList<List<String>>();
		rows.add(Arrays.asList(new String[] {"trees", "child", "dois", "urls", "filter", "first"}));

		List<String> doiPrefixList = new ArrayList<String>();
		List<String> urlPrefixList = new ArrayList<String>();
		List<String> pubPrefixList = new ArrayList<String>();
		List<Pair<Pattern, String>> filterList = readFilter(pubFilterFile);
		
		for (; start < MAX; start+=delta) {
			String subPath = createSubPath0(fromDate, start, delta);
			File cProjectDir = new File(projectTop, subPath);
			if (!cProjectDir.exists()) {
				LOG.debug("break");
				break;
			}
			CProject cProject = new CProject(cProjectDir);
			List<String> row = new ArrayList<String>();
			row.add(String.valueOf(cProject.getCTreeList().size()));
			row.add(String.valueOf(cProject.getAllChildDirectoryList().size()));
			
			List<String> doiNames = FileUtils.readLines(new File(projectTop, subPath+".dois.txt"));
			List<CMDOI> doiList = CMDOI.readDois(doiNames);
			doiPrefixList.addAll(AbstractCM.getPrefixList(doiList));
			row.add(String.valueOf(doiNames.size()));
			
			List<String> urlNames = FileUtils.readLines(new File(projectTop, subPath+".urls.txt"));
			List<CMURL> urlList = CMURL.readUrls(urlNames);
			for (String urlPrefix : AbstractCM.getPrefixList(urlList)) {
				if (urlPrefix != null) {
					urlPrefixList.add(urlPrefix);
				}
			}
			row.add(String.valueOf(urlNames.size()));

			for (String urlPrefix : AbstractCM.getPrefixList(urlList)) {
				if (urlPrefix != null) {
					pubPrefixList.add(normalizePub(urlPrefix, filterList));
				}
			}
			row.add(String.valueOf(urlNames.size()));

			try {
				row.add(String.valueOf(FileUtils.readLines(new File(projectTop, subPath+".urls.filter.txt")).size()));
			} catch (Exception e) {
				LOG.warn(e);
			}
			row.add(doiNames.size() > 0 ? doiNames.get(0) : "-");
			row.add(urlNames.size() > 0 ? urlNames.get(0) : "-");
			rows.add(row);
		}
		File csvDir = new File("target/csvtest/");
		csvDir.mkdirs();
		CSVWriter csvWriter = new CSVWriter();
		csvWriter.setFileName(new File(csvDir, "projects.csv").toString());
		csvWriter.setRows(rows);
		csvWriter.writeCsvFile();
		
		CSVWriter doiCounter = new CSVWriter();
		doiCounter.createMultisetAndOutputRowsWithCounts(doiPrefixList, new File(csvDir, "doiCount.csv").toString());
		CSVWriter urlCounter = new CSVWriter();
		urlCounter.createMultisetAndOutputRowsWithCounts(urlPrefixList, new File(csvDir, "urlCount.csv").toString());
		CSVWriter pubCounter = new CSVWriter();
		pubCounter.createMultisetAndOutputRowsWithCounts(pubPrefixList, new File(csvDir, "pubCount.csv").toString());
		
	}

	
	private String normalizePub(String urlPrefix, List<Pair<Pattern, String>> filterList) {
		for (Pair<Pattern, String> filter : filterList) {
			Pattern pattern = filter.getLeft();
			Matcher matcher = pattern.matcher(urlPrefix);
			if (matcher.find()) {
				urlPrefix = urlPrefix.replaceAll(pattern.toString(), filter.getRight());
			}
		}
		return urlPrefix;
	}

	private List<Pair<Pattern, String>> readFilter(File pubFilterFile) throws IOException {
		List<String> lines = FileUtils.readLines(pubFilterFile);
		List<Pair<Pattern, String>> filterList = new ArrayList<Pair<Pattern, String>>();
		for (String line : lines) {
			String[] parts = line.split("\\s+");
			if (parts.length == 0 || parts.length > 2) {
				LOG.warn("filter requires 1/2 parts");
				continue;
			}
			String replace = parts.length == 1 ? "" : parts[1];
//			LOG.debug(">>"+replace);
			Pattern pattern = Pattern.compile(parts[0]);
			Pair<Pattern, String> filter = new MutablePair<Pattern, String>(pattern, replace);
			filterList.add(filter);
		}
		return filterList;
	}

	// ==========================
	

	private String createSubPath(String fromDate, int count, int delta) {
		String s1 = createSubPath0(fromDate, count, delta);
		String s2 = fromDate+"/"+s1;
		LOG.debug(s1);
		return s2;
	}

	private String createSubPath0(String fromDate, int count, int delta) {
		String fromDateMin = fromDate.replaceAll("\\-", "");
		String s1 = fromDateMin+"_"+count+"_"+delta;
		return s1;
	}
}
