package org.xmlcml.norma.biblio.json;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.xmlcml.cmine.files.CMDir;
import org.xmlcml.norma.biblio.EPMCResultsJsonEntry;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class EPMCConverter {

	private InputStream jsonInputStream;
	private File cProjectDir;
	
	public EPMCConverter() {
		
	}
	public void convertEPMCJsonFileToCProject() throws IOException {
		if (jsonInputStream == null) {
			throw new RuntimeException("No EMPCJson file to convert");
		}
		
		String resultsJsonString = IOUtils.toString(jsonInputStream, "UTF-8");
	    JsonParser parser = new JsonParser();
	    JsonElement jsonElement = parser.parse(resultsJsonString);
		if (cProjectDir == null) {
			throw new RuntimeException("No cProjectDir given");
		}
		cProjectDir.mkdirs();
	    JsonArray entryArray = jsonElement.getAsJsonArray();
		for (int i = 0; i < entryArray.size(); i++) {
			JsonElement entry = entryArray.get(i);
			CMDir cTree = convertEPMCToCTree(entry);
		}
	}

	public CMDir convertEPMCToCTree(JsonElement entry) {
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
		CMDir cTree = new CMDir(cTreeDir);
		
		return cTree;
		
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
