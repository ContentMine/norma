package org.xmlcml.norma.download;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.vafer.jdeb.shaded.compress.io.FilenameUtils;
import org.xmlcml.cmine.util.CMineGlobber;
import org.xmlcml.norma.NormaFixtures;
import org.xmlcml.norma.download.CrossRefDownloader.Type;
import org.xmlcml.norma.pubstyle.aip.AIPTest;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class CrossRefDownloader {

	private static final int MAX_DAILY_LIMIT = 99999;
	private static final String ITEMS = "items";
	private static final String MESSAGE = "message";
	private static final String AMP = "&";
	private static final String UTF_8 = "UTF-8";
	private static final String OFFSET = "&offset=";
	private static final String ROWS = "&rows=";
	
	private static final Logger LOG = Logger.getLogger(CrossRefDownloader.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}

	public enum Type {
		JOURNAL_ARTICLE("journal-article"),
		;
		private String name;

		private Type(String name) {
			this.name = name;
		}
		public String getName() {
			return name;
		}

	}
	
	static final String URLS_FILTER_CSV = "urls.filter.csv"; // suffix after conversion by doi.org
	static final String FILTER_CSV = "filter.csv"; // suffix for file with filtered URLs
	static final String URLS_CSV = "urls.csv";
	private static String CROSSREF_URL = "http://api.crossref.org";
	private static String WORKS_URL = CROSSREF_URL+ "/works";


	private DownloadFilter downloadFilter;
	protected JsonArray items;
	private int rowsInChunk = 250;
	private int offset = 0;
	private JsonParser parser;
	private JsonElement rootElement;
	private JsonElement message;
	private String query;
	
	public CrossRefDownloader() {
		
	}

	public DownloadFilter getOrCreateFilter() {
		if (downloadFilter == null) {
			downloadFilter = new DownloadFilter();
		}
		return downloadFilter;
	}

	protected String getBaseUrl() {
		return WORKS_URL;
	}

	public void setRows(int rows) {
		this.rowsInChunk = rows;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public URL getURL() throws IOException {
		String urlString = "";
		DownloadFilter downloadFilter = this.getOrCreateFilter();
		String filterString = downloadFilter.getFilterString();
		if (filterString != null) {
			urlString = getBaseUrl() + "?" + filterString ;
		}
		if (query != null) {
			urlString += (urlString.equals("") ? "" : AMP);
			urlString += "query="+query;
		}
		urlString += ROWS+rowsInChunk;
		urlString += OFFSET+offset;
		return urlString == "" ? null : new URL(urlString);
	}

	public JsonArray getItems() throws IOException {
		URL url = getURL();
		InputStream stream = url.openStream();
		String content = IOUtils.readLines(stream, UTF_8).get(0);
		parser = new JsonParser();
		rootElement = parser.parse(content);
		message = rootElement.getAsJsonObject().get(MESSAGE);
		items = message.getAsJsonObject().get(ITEMS).getAsJsonArray();
		return items;
	}

	public List<String> getUrlList() throws IOException {
		getItems();
		List<String> urlList = new ArrayList<String>();
		for (JsonElement item : items) {
			JsonElement url = item.getAsJsonObject().get("URL");
			urlList.add(url.getAsString());
		}
		return urlList;
	}

	public void setQuery(String string) {
		this.query = string;
	}
	
	/** utility method for downloading journal articles
	 * 
	 * @param fromDate yyyy-mm-dd
	 * @param untilDate yyyy-mm-dd
	 * @param query see CrossRef (can be as simple as "psychology") queries the title
	 * @param resolveDois if true use dx.doi.org to resove URLs
	 * @param rows max urls to download
	 * @param outputDir
	 * @return
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public File simpleJournalDownload(String fromDate, String untilDate, String query, boolean resolveDois, 
			int rows, int offset, File outputDir) throws IOException, FileNotFoundException {
		this.getOrCreateFilter().setFromPubDate(fromDate);
		this.getOrCreateFilter().setUntilPubDate(untilDate);
		this.getOrCreateFilter().setType(Type.JOURNAL_ARTICLE);
		if (query != null) {
			this.setQuery(query);
		}
		this.setRows(rows);
		this.setOffset(offset);
		List<String> urlList = this.getUrlList();
		LOG.debug("urls: "+urlList.size());		
		outputDir.mkdirs();
		// file of form: query+date+rows+offset+type
		String root = (query == null) ? "" : query;
		root += fromDate.replaceAll("\\-",  "");
		root += "_"+offset+"_"+rows;
		File doiFile = new File(outputDir, root+".dois.txt");
		IOUtils.writeLines(urlList, "\n", new FileOutputStream(doiFile));
		File outfile = doiFile;
		if (resolveDois) {
			File urlFile = new File(outputDir, root+".urls.txt");
			DOIResolver doiResolver = new DOIResolver();
			doiResolver.resolveDOIs(doiFile);
			doiResolver.writeResolvedURLsFile(urlFile);
			outfile = urlFile;
		}
		return outfile;
	}

	/**
	 * utility method to create and run downloader.
	 * 
	 * 
	 * @param dailyDir top directory
	 * @param fromDate start date as yyyy-mm-dd
	 * @param untilDate until date exclusive
	 * @param rowsInChunk 
	 * @param deltaoffset 
	 * @param currentPos startin position 
	 * @param resolveDois use dx.doi.org to resolve DOIs to URLs
	 * @param blackList regexes of files to be skipped
	 * @return next starting position
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public static int runDailyDownload(File dailyDir, String fromDate, String untilDate, int rowsInChunk, int currentPos,
			boolean resolveDois, BlackList blackList) throws IOException, FileNotFoundException {
		for (int j = 0; j < MAX_DAILY_LIMIT; j++) {
			File urlFile = CrossRefDownloader.runCrossRefDate(fromDate, untilDate, resolveDois, rowsInChunk, currentPos, 
					new File(dailyDir, fromDate+"/"));
			List<String> lines = FileUtils.readLines(urlFile);
			if (lines.size() == 0) {
				LOG.debug("no URLS above: "+currentPos+"); terminating");
				break;
			}
			List<String> filteredLines = blackList.omitLines(lines);
			File filterFile = new File(FilenameUtils.removeExtension(urlFile.getAbsolutePath())+"."+FILTER_CSV);
			FileUtils.writeLines(filterFile, filteredLines);
			currentPos += rowsInChunk;
		}
		return currentPos;
	}
	
	/**
	 * takes list of URLs, filters with blacklist, and writes to files of filtered URLs
	 * 
	 * @param urlList list of URL
	 * @param blackList to reject files
	 * @return list of files of filtered URLs
	 * @throws IOException
	 */
	public static List<File> writeFilteredURLs(List<File> urlList, BlackList blackList) throws IOException {
		List<File> filteredFileList = new ArrayList<File>();
		for (File urlFile : urlList) {
			String urlFileName = urlFile.toString();
			List<String> filterLines = blackList.omitLines(FileUtils.readLines(urlFile));
			File filtered = new File(FilenameUtils.removeExtension(FilenameUtils.removeExtension(urlFileName))+"."+URLS_FILTER_CSV);
			FileUtils.writeLines(filtered, filterLines);
			filteredFileList.add(filtered);
		}
		return filteredFileList;
	}

	/**
	 * analyses results of download. Maybe should be separate class
	 * 
	 * @param ctreeGlob glob for files inside a CTree (e.g. "glob:[star][star]/results.json"
	 * @param urlsGlobber
	 * @throws IOException
	 */
	public static void analyzeFilesAndCProjects(String ctreeGlob, CMineGlobber urlsGlobber) throws IOException {
		List<File> urlFileList = urlsGlobber.listFiles();
		
		for (File urlFile : urlFileList) {
			String urlFileName = urlFile.toString();
			File cProjectDir = new File(FilenameUtils.removeExtension(FilenameUtils.removeExtension(urlFileName)).toString());
			File filtered = new File(FilenameUtils.removeExtension(FilenameUtils.removeExtension(urlFileName))+"."+CrossRefDownloader.URLS_FILTER_CSV);
			if (filtered.exists()) {
				LOG.trace(">> "+FileUtils.readLines(filtered).size()+": "+filtered);
			}
			
			CMineGlobber resultsGlobber = new CMineGlobber(ctreeGlob, cProjectDir);
			List<File> resultsList = resultsGlobber.listFiles();
			LOG.debug("results "+resultsList.size()+" in: "+cProjectDir);
		}
	}

	/** utility method to create and run downloader
	 * 
	 * @param fromDate
	 * @param untilDate
	 * @param query
	 * @param resolveDois
	 * @param rows
	 * @param outputDir
	 * @return
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public static File runCrossRefQuery(String fromDate, String untilDate, String query, boolean resolveDois, int rows, File outputDir) throws IOException, FileNotFoundException {
		CrossRefDownloader downLoader = new CrossRefDownloader();
		return downLoader.simpleJournalDownload(fromDate, untilDate, query, resolveDois, rows, 0, outputDir);

	}

	/** utitlity method to create and run downloader.
	 * 
	 * @param fromDate
	 * @param untilDate
	 * @param resolveDois
	 * @param rows
	 * @param offset
	 * @param outputDir
	 * @return
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public static File runCrossRefDate(String fromDate, String untilDate, boolean resolveDois, int rows, int offset, File outputDir) throws IOException, FileNotFoundException {
		CrossRefDownloader downLoader = new CrossRefDownloader();
		return downLoader.simpleJournalDownload(fromDate, untilDate, null, resolveDois, rows, offset, outputDir);

	}




}
