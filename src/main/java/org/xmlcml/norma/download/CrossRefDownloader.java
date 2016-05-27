package org.xmlcml.norma.download;

public class CrossRefDownloader {

	private DownloadFilter downloadFilter;
	private String baseUrl = "http://api.crossref.org/works";
	
	public CrossRefDownloader() {
		
	}

	public DownloadFilter getOrCreateFilter() {
		if (downloadFilter == null) {
			downloadFilter = new DownloadFilter();
		}
		return downloadFilter;
	}

	protected String getBaseUrl() {
		return baseUrl;
	}
}
