package org.xmlcml.norma.json;

import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.xmlcml.euclid.JodaDate;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class ManifestElement {

	
	private static final Logger LOG = Logger.getLogger(ManifestElement.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}
	private static final String ABSTRACT = "abstractText";
	
	
	private ManifestString abstractText = new ManifestString(ABSTRACT); 
	/**
	affiliation, 
	authorList, 
	authorString, 
	citedByCount, 
	dateOfCreation, 
	DOI, 
	electronicPublicationDate, 
	fullTextUrlList, 
	firstPublicationDate, 
	hasDbCrossReferences, 
	hasLabsLinks, 
	hasReferences, 
	hasTextMinedTerms, 
	hasTMAccessionNumbers
	id, 
	inEPMC, 
	inPMC, 
	isOpenAccess, 
	journalInfo, 
	language, 
	luceneScore, 
	pageInfo, 
	pmcid, 
	pmid, 
	pubModel, 
	pubTypeList, 
	source, 
	subsetList, 
	title, 
	*/

			
	
	private Map.Entry<String, JsonElement> rootElement;
	private String key;
	private JsonElement value;
	private JsonPrimitive jsonPrimitive;
	private JsonObject jsonObject;
	private Boolean bool;
	private JsonArray array;
	private String string;
	private DateTime dateTime;
	private Double dubble ;
	private Integer integer;

	public ManifestElement(Entry<String, JsonElement> jsonElement) {
		this.rootElement = (Map.Entry<String, JsonElement>) jsonElement;
		// there is always exactly one array element;
		this.value = rootElement.getValue().getAsJsonArray().get(0);
		if (this.value instanceof JsonPrimitive) {
			jsonPrimitive = (JsonPrimitive) value;
			string = jsonPrimitive.getAsString();
			String unquoted = string.replaceAll("\"", "");
			try {
				dateTime = JodaDate.parseDate(unquoted, "yyyy-mm-dd");
			} catch (Exception e) {
				//
			}
			if (dateTime == null) {
				try {
					integer = Integer.parseInt(unquoted);
				} catch (Exception e) {
					//
				}
			}
			if (dateTime == null && integer == null) {
				try {
					dubble = Double.parseDouble(unquoted);
				} catch (Exception e) {
					//
				}
			}
			bool = ("Y".equalsIgnoreCase(unquoted) ? new Boolean(true) : ("N".equalsIgnoreCase(unquoted) ? new Boolean(false) : null));
		} else if (this.value instanceof JsonObject) {
			jsonObject = (JsonObject) value;
			if (jsonObject.isJsonArray()) {
				array = jsonObject.getAsJsonArray();
			}
		} else {
			throw new RuntimeException("unsupported type: "+value.getClass());
		}

		getKey();
	}

	public JsonObject getJsonObject() {
		return jsonObject;
	}

	public void setJsonObject(JsonObject jsonObject) {
		this.jsonObject = jsonObject;
	}

	public Boolean getBool() {
		return bool;
	}

	public void setBool(Boolean bool) {
		this.bool = bool;
	}

	public String getString() {
		return string;
	}

	public void setString(String string) {
		this.string = string;
	}

	public DateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(DateTime dateTime) {
		this.dateTime = dateTime;
	}

	public Double getDouble() {
		return dubble;
	}

	public void setDubble(Double dubble) {
		this.dubble = dubble;
	}

	public Integer getInteger() {
		return integer;
	}

	public void setInteger(Integer integer) {
		this.integer = integer;
	}

	public String getKey() {
		if (key == null) {
			key = rootElement.getKey();
		}
		return key;
	}
	
	@Override
	public String toString() {
		return key+"="+(jsonPrimitive != null ? (jsonPrimitive + "/" + dateTime + "/" + dubble + "/" + integer + "/" + bool) : jsonObject);
	}

}
