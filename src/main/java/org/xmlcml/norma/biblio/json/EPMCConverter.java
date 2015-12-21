package org.xmlcml.norma.biblio.json;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.xmlcml.cmine.files.CProject;
import org.xmlcml.cmine.files.CTree;
import org.xmlcml.norma.biblio.EPMCResultsJsonEntry;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;

public class EPMCConverter {

	private static final Logger LOG = Logger.getLogger(EPMCConverter.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}
	
	private InputStream jsonInputStream;
	private File cProjectDir;
	
	public EPMCConverter() {
		
	}
	public void convertEPMCJsonFileToCProject() throws IOException {
		JsonElement jsonElement = readJsonElementFromStream();
		if (cProjectDir == null) {
			throw new RuntimeException("No cProjectDir given");
		}
		cProjectDir.mkdirs();
	    JsonArray entryArray = jsonElement.getAsJsonArray();
		for (int i = 0; i < entryArray.size(); i++) {
			JsonElement entry = entryArray.get(i);
			CTree cTree = convertEPMCEntryToCTree(entry);
		}
	}
	
	private JsonElement readJsonElementFromStream() throws IOException {
		if (jsonInputStream == null) {
			throw new RuntimeException("No EMPCJson file to convert");
		}
		
		String resultsJsonString = IOUtils.toString(jsonInputStream, "UTF-8");
	    JsonParser parser = new JsonParser();
	    JsonElement jsonElement = parser.parse(resultsJsonString);
		return jsonElement;
	}

	public CTree convertEPMCEntryToCTree() throws IOException {
		JsonElement entry = readJsonElementFromStream();
		CTree cTree = convertEPMCEntryToCTree(entry);
		return cTree;
	}

	public CTree convertEPMCEntryToCTree(JsonElement entry) {
		EPMCResultsJsonEntry resultJson = new EPMCResultsJsonEntry(entry);
		String id = resultJson.getIdText();
		if (id == null) {
			id = resultJson.getPmidText();
		}
		if (id == null) {
			System.err.println("entry without ID: "+entry);
			return null;
		}
		File cTreeDir = new File(cProjectDir, id);
		cTreeDir.mkdirs();
		CTree cTree = new CTree(cTreeDir);
		File entryFile = new File(cTreeDir, CProject.EUPMC_RESULTS_JSON);
		entry = stripOneElementArrays(entry);
		try {
			FileUtils.writeStringToFile(entryFile, entry.toString(), Charset.forName("UTF-8"));
		} catch (IOException e) {
			throw new RuntimeException("Cannot write "+entryFile);
		}
		
		return cTree;
		
	}
	
	private JsonElement stripOneElementArrays(JsonElement entry) {
		LOG.debug("strip one element arrays does not yet work");
		String json = entry.toString();
		String jsonPath = "$..source";//,$..id]";
		ReadContext ctx = JsonPath.parse(json);
		Object result = ctx.read(jsonPath);
		
		JSONArray jsonArray = (JSONArray) result;
		LOG.debug(">>Array>>"+jsonArray);
		
		for (int i = 0; i < jsonArray.size(); i++) {
			Object object = jsonArray.get(i);
			if (object instanceof JSONArray) {
				JSONArray jsonArray1 = (JSONArray) object;
				if (jsonArray1.size() == 1) {
					Object object1 = jsonArray1.get(0);
					LOG.debug(">> "+object1.getClass());
					if (object1 instanceof String) {
						String s = object1.toString();
						LOG.debug("STR: "+s);
					} else {
						LOG.debug(">CL>"+object1.getClass());
					}
				}
			}
			LOG.debug(">>?"+object.getClass()+": "+object);
		}
		return entry;
//		return (JSONArray) result;
	}
	public void setCProjectDir(File cProjectDir) {
		this.cProjectDir = cProjectDir;
	}
	
	public void setJsonFile(File jsonFile) {
		try {
			this.jsonInputStream = new FileInputStream(jsonFile);
		} catch (FileNotFoundException e) {
			throw new RuntimeException("File input stream not found: "+jsonFile);
		}
	}
	
	public void setJsonInputStream(InputStream jsonFile) {
		this.jsonInputStream = jsonFile;
	}
	public void readInputStream(FileInputStream fileInputStream) {
		jsonInputStream = fileInputStream;
	}
	

}
