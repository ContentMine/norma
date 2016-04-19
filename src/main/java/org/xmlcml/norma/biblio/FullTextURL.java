package org.xmlcml.norma.biblio;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class FullTextURL {
	
	private static final String URL2 = "url";
	private static final String SITE = "site";
	private static final String DOCUMENT_STYLE = "documentStyle";
	private static final String AVAILABILITY_CODE = "availabilityCode";
	private static final String AVAILABILITY = "availability";
	
	private String availability;
	private String availabilityCode;
	private String documentStyle;
	private String site;
	private String url;
	
	private JsonElement fullTextURL;

	/**
    "fullTextUrlList": [
      {
        "fullTextUrl": [
	      {
	        "availability": [
	          "Open access"
	        ],
	        "availabilityCode": [
	          "OA"
	        ],
	        "documentStyle": [
	          "pdf"
	        ],
	        "site": [
	          "Europe_PMC"
	        ],
	        "url": [
	          "http://europepmc.org/articles/PMC4472175?pdf=render"
	        ]
	 * @param fullTextURL
	 */

	
	public FullTextURL(JsonElement fullTextURL) {
		this.fullTextURL = fullTextURL;
		getFields();
	}
	
	public String getAvailability() {
		return availability;
	}

	public String getAvailabilityCode() {
		return availabilityCode;
	}

	public String getDocumentStyle() {
		return documentStyle;
	}

	public String getSite() {
		return site;
	}

	public String getUrl() {
		return url;
	}


	private void getFields() {
		JsonArray jsonArray0 = ((JsonObject)fullTextURL).get("fullTextUrl").getAsJsonArray();
		if (jsonArray0 != null) {
			availability = EPMCResultsJsonEntry.getField(AVAILABILITY, jsonArray0);
			availabilityCode = EPMCResultsJsonEntry.getField(AVAILABILITY_CODE, jsonArray0);
			documentStyle = EPMCResultsJsonEntry.getField(DOCUMENT_STYLE, jsonArray0);
			site = EPMCResultsJsonEntry.getField(SITE, jsonArray0);
			url = EPMCResultsJsonEntry.getField(URL2, jsonArray0);
		}
	}

	public String toString() {
		String s = ""
				+ availability + " | "
				+ availabilityCode + " | "
				+ documentStyle + " | "
				+ site + " | "
				+ url;
		return s;
	}

}
