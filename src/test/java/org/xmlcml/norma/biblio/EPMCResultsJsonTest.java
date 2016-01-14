package org.xmlcml.norma.biblio;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.xmlcml.cmine.files.CTree;
import org.xmlcml.norma.NormaFixtures;
import org.xmlcml.norma.biblio.json.EPMCConverter;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class EPMCResultsJsonTest {
	
	private static final Logger LOG = Logger.getLogger(EPMCResultsJsonTest.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}
	


	@Test
	public void testReadResultsJSON() throws IOException {
		String resultsJsonString = FileUtils.readFileToString(new File(NormaFixtures.TEST_BIBLIO_DIR, "json/eupmc_results.json"));
	    JsonParser parser = new JsonParser();
	    JsonElement jsonElement = parser.parse(resultsJsonString);
	    JsonArray entryArray = jsonElement.getAsJsonArray();
		Assert.assertEquals("results",  25, entryArray.size());
		for (int i = 0; i < entryArray.size(); i++) {
			JsonElement entry = entryArray.get(i);
			EPMCResultsJsonEntry resultJson = new EPMCResultsJsonEntry(entry);
			LOG.trace(resultJson.toString()+"\n===========================================\n");
		}
	}
	
	@Test
	public void testReadResultsJSON1() throws IOException {
		String resultsJsonString = FileUtils.readFileToString(new File(NormaFixtures.TEST_BIBLIO_DIR, "json/ursusmaritimus.json"));
	    JsonParser parser = new JsonParser();
	    JsonElement jsonElement = parser.parse(resultsJsonString);
	    JsonArray entryArray = jsonElement.getAsJsonArray();
		Assert.assertEquals("results",  500, entryArray.size());
		for (int i = 0; i < entryArray.size(); i++) {
			JsonElement entry = entryArray.get(i);
			EPMCResultsJsonEntry resultJson = new EPMCResultsJsonEntry(entry);
//			System.out.println(resultJson.toString()+"\n===========================================\n");
			LOG.trace(">>"+resultJson.getDoiText()+": "+resultJson.getFullTextURLArray());
		}
	}
	
	@Test
	public void testCreateCTreesFromEPMCResultsJSON() throws IOException {
		File jsonFile = new File(NormaFixtures.TEST_BIBLIO_DIR, "json/ursus1.json");
		File cProjectDir = new File("target/json/cproject/ursus1/");
		EPMCConverter epmcConverter = new EPMCConverter();
		epmcConverter.setCProjectDir(cProjectDir);
		epmcConverter.readInputStream(new FileInputStream(jsonFile));
		epmcConverter.convertEPMCJsonFileToCProject();
	}

	
	@Test
	public void testCreateCTreeFromEPMCResults6780JSON() throws IOException {
		File jsonFile = new File(NormaFixtures.TEST_BIBLIO_DIR, "json/6780_eupmc_results.json");
		File cProjectDir = new File("target/json/cproject/ursus1/");
		EPMCConverter epmcConverter = new EPMCConverter();
		epmcConverter.setCProjectDir(cProjectDir);
		epmcConverter.readInputStream(new FileInputStream(jsonFile));
		CTree cTree = epmcConverter.convertEPMCEntryToCTree();
		LOG.debug("CT "+cTree);
	}
}
