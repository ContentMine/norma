package org.xmlcml.norma.biblio;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.xmlcml.cproject.args.DefaultArgProcessor;
import org.xmlcml.graphics.html.HtmlHtml;
import org.xmlcml.norma.NormaFixtures;
import org.xmlcml.norma.biblio.json.EPMCConverter;
import org.xmlcml.xml.XMLUtil;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class EPMCResultsJsonTest {
	
	private static final Logger LOG = Logger.getLogger(EPMCResultsJsonTest.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}
	
	@Test
	public void testReadEpmcMD() throws IOException {
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
	public void testReadEpmcMDFields() throws IOException {
		String resultsJsonString = FileUtils.readFileToString(new File(NormaFixtures.TEST_BIBLIO_DIR, "json/eupmc_results.json"));
	    JsonParser parser = new JsonParser();
	    JsonElement jsonElement = parser.parse(resultsJsonString);
	    JsonArray entryArray = jsonElement.getAsJsonArray();
		Assert.assertEquals("results",  25, entryArray.size());
		JsonElement jsonElement1 = entryArray.get(0);
		EPMCResultsJsonEntry epmcJsonEntry = new EPMCResultsJsonEntry(jsonElement1);
		
		
		String abstractString = epmcJsonEntry.getAbstractText();
        Assert.assertEquals("abs", 
        		"BACKGROUND: The genus Flaveria has been extensively used as a model to study the evolution "
        		+ "of C4 photosynthesis as it contains C3 and C4 species as well as a number of species that "
        		+ "exhibit intermediate types of photosynthesis. The current phylogenetic tree of the genus "
        		+ "Flaveria contains 21 of the 23 known Flaveria species and has been previously constructed "
        		+ "using a combination of morphological data and three non-coding DNA sequences (nuclear "
        		+ "encoded ETS, ITS and chloroplast encoded trnL-F). RESULTS: Here we developed a new strategy "
        		+ "to update the phylogenetic tree of 16 Flaveria species based on RNA-Seq data. The updated "
        		+ "phylogeny is largely congruent with the previously published tree but with some modifications. "
        		+ "We propose that the data collection method provided in this study can be used as a generic "
        		+ "method for phylogenetic tree reconstruction if the target species has no genomic information. "
        		+ "We also showed that a \"F. pringlei\" genotype recently used in a number of labs may be a "
        		+ "hybrid between F. pringlei (C3) and F. angustifolia (C3-C4). CONCLUSIONS: We propose that "
        		+ "the new strategy of obtaining phylogenetic sequences outlined in this study can be used to "
        		+ "construct robust trees in a larger number of taxa. The updated Flaveria phylogenetic tree "
        		+ "also supports a hypothesis of stepwise and parallel evolution of C4 photosynthesis in the "
        		+ "Flavaria clade.", abstractString);
		String author = epmcJsonEntry.getAuthorStringText();
        Assert.assertEquals("auth", "Lyu MJ, Gowik U, Kelly S, Covshoff S, Mallmann J, Westhoff P, Hibberd JM, Stata M, Sage RF, Lu H, Wei X, Wong GK, Zhu XG.", author);
		List<EPMCAuthor> authorList = epmcJsonEntry.getAuthorList();
        Assert.assertEquals("authList", "[\"Lyu MJ\" | \"Ming-Ju Amy\" | \"MJ\" | \"Lyu\" | "
        		+ "\"CAS-MPG Partner Institute and Key Laboratory for Computational Biology, Shanghai Institutes for Biological Sciences, Shanghai, China. mingju.lv@gmail.com.\","
        		+ " \"Gowik U\" | \"Udo\" | \"U\" | \"Gowik\" | \"Institute of Plant Molecular and Developmental Biology, Heinrich-Heine-University, Dusseldorf, Germany. gowik@uni-duesseldorf.de.\","
        		+ " \"Kelly S\" | \"Steve\" | \"S\" | \"Kelly\" | \"Department of Plant Sciences, University of Oxford, Oxford, UK. steven.kelly@plants.ox.ac.uk.\","
        				+ " \"Covshoff S\" | \"Sarah\" | \"S\" | \"Covshoff\" | \"Department of Plant Sciences, University of Cambridge, Cambridge, UK. sarahcovshoff@gmail.com.\","
        				+ " \"Mallmann J\" | \"Julia\" | \"J\" | \"Mallmann\" | \"Institute of Plant Molecular and Developmental Biology, Heinrich-Heine-University, Dusseldorf, Germany. julia.mallmann@uni-duesseldorf.de.\","
        				+ " \"Westhoff P\" | \"Peter\" | \"P\" | \"Westhoff\" | \"Institute of Plant Molecular and Developmental Biology, Heinrich-Heine-University, Dusseldorf, Germany. west@uni-duesseldorf.de.\","
        				+ " \"Hibberd JM\" | \"Julian M\" | \"JM\" | \"Hibberd\" | \"Department of Plant Sciences, University of Cambridge, Cambridge, UK. jmh65@cam.ac.uk.\","
        				+ " \"Stata M\" | \"Matt\" | \"M\" | \"Stata\" | \"Department of Ecology and Evolutionary Biology, University of Toronto, Toronto, Canada. mattstata@gmail.com.\","
        				+ " \"Sage RF\" | \"Rowan F\" | \"RF\" | \"Sage\" | \"Department of Ecology and Evolutionary Biology, University of Toronto, Toronto, Canada. r.sage@utoronto.ca.\","
        				+ " \"Lu H\" | \"Haorong\" | \"H\" | \"Lu\" | \"BGI-Shenzhen, Beishan Industrial Zone, Yantian District, Shenzhen, 518083, China. luhaorong@genomics.cn.\","
        				+ " \"Wei X\" | \"Xiaofeng\" | \"X\" | \"Wei\" | \"BGI-Shenzhen, Beishan Industrial Zone, Yantian District, Shenzhen, 518083, China. weixiaofeng@genomics.cn.\","
        				+ " \"Wong GK\" | \"Gane Ka-Shu\" | \"GK\" | \"Wong\" | \"Department of Medicine, University of Alberta, Edmonton, AB, T6G 2E1, Canada. gane@ualberta.ca.\","
        				+ " \"Zhu XG\" | \"Xin-Guang\" | \"XG\" | \"Zhu\" | \"CAS-MPG Partner Institute and Key Laboratory for Computational Biology, Shanghai Institutes for Biological Sciences, Shanghai, China. xinguang.zhu@gmail.com.\"]",
        				authorList.toString());
		String doi = epmcJsonEntry.getDoiText();
        Assert.assertEquals("doi", "10.1186/s12862-015-0399-9", doi);
		String id = epmcJsonEntry.getIdText();
        Assert.assertEquals("id", "26084484", id);
		String pmid = epmcJsonEntry.getPmidText();
        Assert.assertEquals("pmid", "26084484", pmid);
		String pmcid = epmcJsonEntry.getPmcidText();
        Assert.assertEquals("pmcid", "PMC4472175", pmcid);
	 	String source = epmcJsonEntry.getSource();
        Assert.assertEquals("source", "MED", source);
		String title = epmcJsonEntry.getTitleText();
        Assert.assertEquals("title", "RNA-Seq based phylogeny recapitulates previous phylogeny of the genus Flaveria (Asteraceae) with some modifications.", title);
        // some of the boolean values look wrong
	    Boolean oa = epmcJsonEntry.isOpenAccess();
        Assert.assertTrue("oa: "+oa, oa);
	    Boolean inepmc = epmcJsonEntry.inEPMC();
       Assert.assertTrue("inEpmc: "+inepmc, inepmc);
	    Boolean inpmc = epmcJsonEntry.inPMC();
//        Assert.assertTrue("inPmc: "+inpmc, inpmc);
	    Integer cited = epmcJsonEntry.citedByCount();
        Assert.assertEquals("cited", new Integer(0), cited);
	    Boolean hasRef = epmcJsonEntry.hasReferences();
        Assert.assertTrue("hasref: "+hasRef, hasRef);
	    Boolean hasMined= epmcJsonEntry.hasTextMinedTerms();
        Assert.assertTrue("hasTextMine", hasMined);
	    Boolean hasDb= epmcJsonEntry.hasDbCrossReferences();
        Assert.assertFalse("hasdbcross", hasDb);
	    Boolean hasLabs = epmcJsonEntry.hasLabsLinks();
        Assert.assertFalse("hasLabs", hasLabs);
	    Boolean hasTM= epmcJsonEntry.hasTMAccessionNumbers();
        Assert.assertFalse("hasAccession", hasTM);
	    DateTime creationDate = epmcJsonEntry.getDateOfCreation();
        Assert.assertNull("creationdate", creationDate);
        DateTime revDate = epmcJsonEntry.getDateOfRevision();
        Assert.assertEquals("revDate", "2015-01-20T00:06:00.000Z", revDate.toString());
        DateTime epubDate = epmcJsonEntry.getElectronicPublicationDate();
        Assert.assertEquals("epubDate", "2015-01-18T00:06:00.000Z", epubDate.toString());
        DateTime firstPDateTime = epmcJsonEntry.getFirstPublicationDate();
        Assert.assertEquals("firstDate", "2015-01-18T00:06:00.000Z", firstPDateTime.toString());
	    Double lucene = epmcJsonEntry.getLuceneScore();
        Assert.assertEquals("lucene", new Double(767.6693), lucene, 0.01);
	    Boolean book= epmcJsonEntry.hasBook();
        Assert.assertFalse("book", book);
	 	Integer page = epmcJsonEntry.getPageInfo();
        Assert.assertEquals("page", new Integer(116), page);
	  	String affiliation = epmcJsonEntry.getAffiliation();
        Assert.assertEquals("affil", "CAS-MPG Partner Institute and Key Laboratory for Computational Biology, Shanghai Institutes for Biological Sciences, Shanghai, China. mingju.lv@gmail.com.", affiliation);
	    String model = epmcJsonEntry.getPubModel();
        Assert.assertEquals("pubModel", "Electronic", model);
	   	String lang = epmcJsonEntry.getLanguage();
        Assert.assertEquals("lang", "eng", lang);
	 	List<FullTextURL> valueFullTextList = epmcJsonEntry.getFullTextURLArray();
        Assert.assertEquals("fullUrl", "[\"Open access\" | \"OA\" | \"pdf\" | \"Europe_PMC\" | "
        		+ "\"http://europepmc.org/articles/PMC4472175?pdf=render\"]",
        		valueFullTextList.toString());
	 	JournalInfo journal = epmcJsonEntry.getJournalInfo();
        Assert.assertEquals("journal", "null | \"15\" | \"2259071\" | \"2015 \" | \"0\" | \"2015\" | \"2015-01-01\""
        		+ "\n\"BMC evolutionary biology\" | \"BMC Evol. Biol.\" | \"BMC Evol Biol\" | \"100966975\" | \"1471-2148\"", 
        		journal.toString());
	}
	/**
    (abstractText == null) ? "" : abstractText.substring(0,  Math.min(abstractText.length(), 200))+"... \n"
	+hasBook + " |  "
    +isOpenAccess + " |  "
    +inEPMC + " |  "
    +inPMC + " |  "
    +hasReferences + " |  "
    +hasTextMinedTerms + " |  "
    +hasDbCrossReferences + " |  "
    +hasLabsLinks + " |  "
    +hasTMAccessionNumbers + " |  "
    +citedByCount + " |  "
    +dateOfCreation + " |  "
    +dateOfRevision + " |  "
    +electronicPublicationDate + " |  "
    +firstPublicationDate + " |  "
    +luceneScore+"\n"
    +fullTextList+"\n"
    +journalInfo)
*/
	
	@Test
	public void testReadResultsDataTable() throws IOException {
		
		File resultsJson = new File(NormaFixtures.TEST_BIBLIO_DIR, "json/eupmc_results.json");
		
		EPMCConverter epmcConverter = new EPMCConverter();
		epmcConverter.readInputStream(new FileInputStream(resultsJson));
		epmcConverter.createJsonEntryListAndPossiblyCProject();
		epmcConverter.setColumnHeadingList(Arrays.asList(EPMCResultsJsonEntry.FLAGS));
		HtmlHtml html = epmcConverter.createHtml();
		XMLUtil.debug(html, new File("target/empc/metadata.html"), 1);
		
	}
		
	@Test
	public void testHindawi() throws IOException {
		
		File resultsJson = new File("../../hindawi/epmc/eupmc_results.json");
		if (resultsJson.exists()) {
			EPMCConverter epmcConverter = new EPMCConverter();
			epmcConverter.readInputStream(new FileInputStream(resultsJson));
			epmcConverter.createJsonEntryListAndPossiblyCProject();
			epmcConverter.setColumnHeadingList(Arrays.asList(EPMCResultsJsonEntry.FLAGS));
			HtmlHtml html = epmcConverter.createHtml();
			XMLUtil.debug(html, new File("../../hindawi/epmc/metadataTable.html"), 1);
			
		} else {
			LOG.debug(resultsJson.getCanonicalPath()+" does not exist");
		}
		
	}
		


	@Test
	public void testReadEpmcMD1() throws IOException {
		// a 5 Mbyte file
		String resultsJsonString = FileUtils.readFileToString(new File(NormaFixtures.TEST_BIBLIO_DIR, "json/ursusmaritimus.json"));
	    JsonParser parser = new JsonParser();
	    JsonElement jsonElement = parser.parse(resultsJsonString);
	    JsonArray entryArray = jsonElement.getAsJsonArray();
		Assert.assertEquals("results",  500, entryArray.size());
		for (int i = 0; i < entryArray.size(); i++) {
			JsonElement entry = entryArray.get(i);
			EPMCResultsJsonEntry resultJson = new EPMCResultsJsonEntry(entry);
			DefaultArgProcessor.CM_LOG.debug(">>"+resultJson.getDoiText()+": "+resultJson.getFullTextURLArray());
		}
	}
	
	@Test
	/** 6 entries.
	 * 
	 * @throws IOException
	 */
	public void testCreateCTreesFromEPMCMD() throws IOException {
		File jsonFile = new File(NormaFixtures.TEST_BIBLIO_DIR, "json/ursus1.json");
		File cProjectDir = new File("target/json/cproject/ursus1/");
		EPMCConverter epmcConverter = new EPMCConverter();
		epmcConverter.setCProjectDir(cProjectDir);
		epmcConverter.readInputStream(new FileInputStream(jsonFile));
		epmcConverter.createJsonEntryListAndPossiblyCProject();
		Assert.assertEquals("entries: ", 6, epmcConverter.getOrCreateEntryArray().size());
	}

	@Test
	/** single entry.
	 * 
	 * @throws IOException
	 */
	public void testCreateSingleEntryFromEPMCResultsJSON() throws IOException {
		File jsonFile = new File(NormaFixtures.TEST_BIBLIO_DIR, "json/6780_eupmc_results.json");
		EPMCConverter epmcConverter = new EPMCConverter();
		epmcConverter.setCProjectDir(new File("target/json/cproject/ursus1/temp/"));
		epmcConverter.readInputStream(new FileInputStream(jsonFile));
		epmcConverter.createJsonEntryListAndPossiblyCProject();
		// not an array
		Assert.assertEquals("entries: ", 0, epmcConverter.getOrCreateEntryArray().size());
		JsonElement jsonElement = epmcConverter.getJsonElement();
		Assert.assertTrue("jsonObject ", jsonElement instanceof JsonObject);
		JsonObject jsonObject = (JsonObject) jsonElement;
		Assert.assertEquals("set: ", 24, jsonObject.entrySet().size());
	}

	
	@Test
	/** single entry.
	 * 
	 * @throws IOException
	 */
	public void testCreateCTreeFromEPMCResults6780JSON() throws IOException {
		File jsonFile = new File(NormaFixtures.TEST_BIBLIO_DIR, "json/6780_eupmc_results.json");
		File cProjectDir = new File("target/json/cproject/ursus1/");
		EPMCConverter epmcConverter = new EPMCConverter(cProjectDir);
		epmcConverter.readInputStream(new FileInputStream(jsonFile));
		epmcConverter.readAndProcessEntry();
	}
	
	/** not required since latest getpapers splits files
	 * 
	 */
//	@Test
//	/** complete project
//	 * 
//	 * starts with a CProject and aggregated results.json which it splits
//	 * @throws IOException
//	 */
//	public void testCreateCompleteProject() throws IOException {
//		
//		File cProjectDir = new File("../../hindawi/epmc/hindawi");
//		if (!cProjectDir.exists()) {
//			LOG.debug("PMR only test");
//			return;
//		}
//		File jsonFile = new File(cProjectDir, "eupmc_results.json");
//		if (!jsonFile.exists()) {
//			LOG.debug("No json file");
//			return;
//		}
//		EPMCConverter epmcConverter = new EPMCConverter();
//		epmcConverter.setCProjectDir(cProjectDir);
//		epmcConverter.readInputStream(new FileInputStream(jsonFile));
//		epmcConverter.createJsonEntryListAndPossiblyCProject();
//		Assert.assertEquals("entries: ", 1000, epmcConverter.getOrCreateEntryArray().size());
//	}


}
