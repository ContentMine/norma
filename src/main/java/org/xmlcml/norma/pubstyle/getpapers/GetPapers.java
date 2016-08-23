package org.xmlcml.norma.pubstyle.getpapers;

import java.io.File;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import net.minidev.json.JSONArray;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.xmlcml.cmine.files.CTree;
import org.xmlcml.cmine.metadata.AbstractMetadata.Type;
import org.xmlcml.cmine.metadata.quickscrape.QuickscrapeMD;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;

public class GetPapers {

	private static final Logger LOG = Logger.getLogger(GetPapers.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}

	public static final String PMCID = ".pmcid";
	
	private String jsonPath;

	public GetPapers() {
	}

	public void mapJsonArrayToFiles(File getPapersDir, File resultsJsonFile) throws IOException {
		JsonParser parser = new JsonParser();
	    String jsonString = FileUtils.readFileToString(resultsJsonFile);
	    JsonElement jsonElement = parser.parse(jsonString);
	    JsonArray resultsArray = jsonElement.getAsJsonArray();
	    
	    Map<String, JsonElement> resultsByPmcid = this.createResultsByPMCID(resultsArray);
	    File[] files = getPapersDir.listFiles();
	    for (File file : files) {
	    	if (file.isDirectory()) {
	    		String name = file.getName();
	    		JsonElement element = resultsByPmcid.get(name);
	    		if (element != null) {
		    		CTree cTree = new CTree(file);
		    		File resultsJson = cTree.getReservedFile( Type.QUICKSCRAPE.getCTreeMDFilename());
		    		FileUtils.write(resultsJson, element.toString(), Charset.forName("UTF-8"));
	    		}
	    	}
	    }
	}
	
	public void setJsonPath(String jsonPath) {
		this.jsonPath = jsonPath;
	}
	
	private Map<String, JsonElement> createResultsByPMCID(JsonArray resultsArray) {
		if (jsonPath == null) {
			throw new RuntimeException("Must set JsonPath");
		}
		Map<String, JsonElement> resultsByPmcid = new HashMap<String, JsonElement>();
	    for (int i = 0; i < resultsArray.size(); i++) {
	    	JsonElement resultElement = resultsArray.get(i);
    		ReadContext ctx = JsonPath.parse(resultElement.toString());
    		JSONArray result = ctx.read(jsonPath);
    		String pmcid = (String) ((JSONArray)result.get(0)).get(0);
    		resultsByPmcid.put(pmcid, resultElement);
	    }
	    return resultsByPmcid;
	}


}
