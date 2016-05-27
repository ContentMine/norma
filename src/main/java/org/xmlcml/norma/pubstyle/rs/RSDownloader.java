package org.xmlcml.norma.pubstyle.rs;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.xmlcml.norma.download.CrossRefDownloader;
import org.xmlcml.norma.download.DownloadFilter;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class RSDownloader extends CrossRefDownloader {

	private int rows = 250;
	private JsonParser parser;
	private JsonElement rootElement;
	private JsonElement message;
	private JsonArray items;

	public RSDownloader() {
		super();
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public URL getURL() throws IOException {
		String urlString = null;
		DownloadFilter downloadFilter = this.getOrCreateFilter();
		String filterString = downloadFilter.getFilterString();
		if (filterString != null) {
			urlString = getBaseUrl() + "?" + filterString + "" + "&rows="+rows;
		}
		return urlString == null ? null : new URL(urlString);
	}

	public JsonArray getItems() throws IOException {
		URL url = getURL();
		InputStream stream = url.openStream();
		String content = IOUtils.readLines(stream, "UTF-8").get(0);
		parser = new JsonParser();
		rootElement = parser.parse(content);
		message = rootElement.getAsJsonObject().get("message");
		items = message.getAsJsonObject().get("items").getAsJsonArray();
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

}
