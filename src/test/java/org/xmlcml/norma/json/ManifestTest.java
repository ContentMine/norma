package org.xmlcml.norma.json;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.xmlcml.norma.Fixtures;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;

/** read/write manifest.json file.
 * 
 * @author pm286
 *
 */
public class ManifestTest {

	
	private static final Logger LOG = Logger.getLogger(ManifestTest.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}
	
	@Test
	public void testReadManifestArray() throws IOException {
		String resultsJsonString = FileUtils.readFileToString(new File(Fixtures.TEST_JSON_DIR, "all_results.json"));
	    JsonParser parser = new JsonParser();
	    JsonElement jsonElement = parser.parse(resultsJsonString);
	    JsonArray jsonArray = jsonElement.getAsJsonArray();
		LOG.debug(jsonArray);
		Assert.assertEquals("results",  100, jsonArray.size());
	}
	
	@Test
	public void testReadFirstManifestFromArray() throws IOException {
		String resultsJsonString = FileUtils.readFileToString(new File(Fixtures.TEST_JSON_DIR, "all_results.json"));
	    JsonParser parser = new JsonParser();
	    JsonElement jsonElement = parser.parse(resultsJsonString);
	    JsonArray jsonArray = jsonElement.getAsJsonArray();
	    JsonElement jsonElement0 = jsonArray.get(0);
	    LOG.trace(jsonElement0.toString());
	    int l = jsonElement.toString().length();
	    Assert.assertTrue("manifest0",  524966 < l && l < 526000);
	    Assert.assertEquals("manifest0 start",  "{\"id\":[\"26000862\"]", jsonElement0.toString().substring(0,18));
	}
	
	@Test
	public void testReadFirstManifestFromFile() throws IOException {
		String resultsJsonString = FileUtils.readFileToString(new File(Fixtures.TEST_JSON_DIR, "results0.json"));
	    JsonParser parser = new JsonParser();
	    JsonElement jsonElement = parser.parse(resultsJsonString);
	    JsonObject jsonObject = jsonElement.getAsJsonObject();
	    Assert.assertEquals("manifest0",  4087, jsonObject.toString().length());
	    Assert.assertEquals("manifest0 start",  "{\"id\":[\"26000862\"]", jsonObject.toString().substring(0,18));
	    Set<Map.Entry<String,JsonElement>> objectSet = jsonObject.entrySet();
	    Assert.assertEquals("set", 30, objectSet.size());
	}
	
	/**
0    [main] DEBUG org.xmlcml.norma.json.ManifestTest  - id = "26000862"
1    [main] DEBUG org.xmlcml.norma.json.ManifestTest  - source = "MED"
1    [main] DEBUG org.xmlcml.norma.json.ManifestTest  - pmid = "26000862"
1    [main] DEBUG org.xmlcml.norma.json.ManifestTest  - pmcid = "PMC4441471"
1    [main] DEBUG org.xmlcml.norma.json.ManifestTest  - DOI = "10.1371/journal.pone.0127339"
1    [main] DEBUG org.xmlcml.norma.json.ManifestTest  - title = "Control of relative timing and stoichiometry by a master regulator."
1    [main] DEBUG org.xmlcml.norma.json.ManifestTest  - authorString = "Goldschmidt Y, Yurkovsky E, Reif A, Rosner R, Akiva A, Nachman I."
1    [main] DEBUG org.xmlcml.norma.json.ManifestTest  - authorList = {"author":[{"fullName":["Goldschmidt Y"],"firstName":["Yifat"],"lastName":["Goldschmidt"],"initials":["Y"],"affiliation":["Department of Biochemistry and Molecular Biology, Tel Aviv University, Tel Aviv, Israel."]},{"fullName":["Yurkovsky E"],"firstName":["Evgeny"],"lastName":["Yurkovsky"],"initials":["E"],"affiliation":["Department of Biochemistry and Molecular Biology, Tel Aviv University, Tel Aviv, Israel."]},{"fullName":["Reif A"],"firstName":["Amit"],"lastName":["Reif"],"initials":["A"],"affiliation":["Department of Biochemistry and Molecular Biology, Tel Aviv University, Tel Aviv, Israel."]},{"fullName":["Rosner R"],"firstName":["Roni"],"lastName":["Rosner"],"initials":["R"],"affiliation":["Department of Biochemistry and Molecular Biology, Tel Aviv University, Tel Aviv, Israel."]},{"fullName":["Akiva A"],"firstName":["Amit"],"lastName":["Akiva"],"initials":["A"],"affiliation":["Department of Biochemistry and Molecular Biology, Tel Aviv University, Tel Aviv, Israel."]},{"fullName":["Nachman I"],"firstName":["Iftach"],"lastName":["Nachman"],"initials":["I"],"affiliation":["Department of Biochemistry and Molecular Biology, Tel Aviv University, Tel Aviv, Israel."]}]}
1    [main] DEBUG org.xmlcml.norma.json.ManifestTest  - journalInfo = {"issue":["5"],"volume":["10"],"journalIssueId":["2278532"],"dateOfPublication":["2015"],"monthOfPublication":["0"],"yearOfPublication":["2015"],"printPublicationDate":["2015-01-01"],"journal":[{"title":["PloS one"],"ISOAbbreviation":["PLoS ONE"],"medlineAbbreviation":["PLoS One"],"NLMid":["101285081"],"ESSN":["1932-6203"]}]}
1    [main] DEBUG org.xmlcml.norma.json.ManifestTest  - pageInfo = "e0127339"
1    [main] DEBUG org.xmlcml.norma.json.ManifestTest  - abstractText = "Developmental processes in cells require a series of complex steps. Often only a single master regulator activates genes in these different steps. This poses several challenges: some targets need to be ordered temporally, while co-functional targets may need to be synchronized in both time and expression level. Here we study in single cells the dynamic activation patterns of early meiosis genes in budding yeast, targets of the meiosis master regulator Ime1. We quantify the individual roles of the promoter and protein levels in expression pattern control, as well as the roles of individual promoter elements. We find a consistent expression pattern difference between a non-cofunctional pair of genes, and a highly synchronized activation of a co-functional pair. We show that dynamic control leading to these patterns is distributed between promoter, gene and external regions. Through specific reciprocal changes to the promoters of pairs of genes, we show that different genes can use different promoter elements to reach near identical activation patterns."
2    [main] DEBUG org.xmlcml.norma.json.ManifestTest  - affiliation = "Department of Biochemistry and Molecular Biology, Tel Aviv University, Tel Aviv, Israel."
2    [main] DEBUG org.xmlcml.norma.json.ManifestTest  - language = "eng"
2    [main] DEBUG org.xmlcml.norma.json.ManifestTest  - pubModel = "Electronic-eCollection"
2    [main] DEBUG org.xmlcml.norma.json.ManifestTest  - pubTypeList = {"pubType":["Journal Article"]}
2    [main] DEBUG org.xmlcml.norma.json.ManifestTest  - subsetList = {"subset":[{"code":["IM"],"name":["Index Medicus"]}]}
2    [main] DEBUG org.xmlcml.norma.json.ManifestTest  - fullTextUrlList = {"fullTextUrl":[{"availability":["Open access"],"availabilityCode":["OA"],"documentStyle":["pdf"],"site":["Europe_PMC"],"url":["http://europepmc.org/articles/PMC4441471?pdf=render"]},{"availability":["Open access"],"availabilityCode":["OA"],"documentStyle":["html"],"site":["Europe_PMC"],"url":["http://europepmc.org/articles/PMC4441471"]},{"availability":["Subscription required"],"availabilityCode":["S"],"documentStyle":["doi"],"site":["DOI"],"url":["http://dx.doi.org/10.1371/journal.pone.0127339"]}]}
2    [main] DEBUG org.xmlcml.norma.json.ManifestTest  - isOpenAccess = "Y"
2    [main] DEBUG org.xmlcml.norma.json.ManifestTest  - inEPMC = "Y"
2    [main] DEBUG org.xmlcml.norma.json.ManifestTest  - inPMC = "N"
2    [main] DEBUG org.xmlcml.norma.json.ManifestTest  - citedByCount = "0"
2    [main] DEBUG org.xmlcml.norma.json.ManifestTest  - hasReferences = "N"
2    [main] DEBUG org.xmlcml.norma.json.ManifestTest  - hasTextMinedTerms = "Y"
2    [main] DEBUG org.xmlcml.norma.json.ManifestTest  - hasDbCrossReferences = "N"
2    [main] DEBUG org.xmlcml.norma.json.ManifestTest  - hasLabsLinks = "N"
2    [main] DEBUG org.xmlcml.norma.json.ManifestTest  - hasTMAccessionNumbers = "N"
2    [main] DEBUG org.xmlcml.norma.json.ManifestTest  - dateOfCreation = "2015-05-24"
2    [main] DEBUG org.xmlcml.norma.json.ManifestTest  - electronicPublicationDate = "2015-05-22"
2    [main] DEBUG org.xmlcml.norma.json.ManifestTest  - firstPublicationDate = "2015-05-22"
2    [main] DEBUG org.xmlcml.norma.json.ManifestTest  - luceneScore = "1176.4783"
	 */

	@Test
	public void testReadObjectFromFirstManifest() throws IOException {
		ManifestJson manifestJson = ManifestJson.readManifest(new File(Fixtures.TEST_JSON_DIR, "results0.json"));
		Assert.assertEquals("manifest", 30, manifestJson.size());
		Set<String> keySet = manifestJson.getKeys();
		Assert.assertTrue("inPMC", keySet.contains("inPMC"));
		Assert.assertTrue("subsetList", keySet.contains("subsetList"));
		for (String key : keySet) {
			ManifestElement manifestElement = manifestJson.getManifestElement(key);
			JsonObject jsonObject = manifestElement.getJsonObject();
			if (jsonObject != null) {
//				LOG.debug(jsonObject);
				if (jsonObject.isJsonArray()) {
					LOG.debug(key+" A "+jsonObject+" / "+jsonObject.getAsJsonArray());
				} else if (jsonObject.entrySet().size() == 1) {
					LOG.debug("jo> "+jsonObject);
					if (jsonObject.isJsonArray()) {
						JsonArray jsonArray = jsonObject.getAsJsonArray();
						LOG.debug("array >"+jsonArray);
					} else {
						ManifestJson manifestJson0 = new ManifestJson(jsonObject);
						ManifestElement manifestElement1 = manifestJson0.getSingleElement();
						LOG.debug(" "+key+":"+manifestElement1);
					}
				} else if (jsonObject.entrySet().size() > 1) {
					ManifestJson manifestJson0 = new ManifestJson(jsonObject);
					for (String key0 : manifestJson0.getKeys()) {
						ManifestElement manifestElement0 = manifestJson0.getManifestElement(key0);
						LOG.debug(">> "+manifestElement0);
					}
//					ManifestMap manifestMap = new ManifestMap(jsonObject);
//					if (manifestMap.getSingleElement() != null) {
//						LOG.debug("single: "+manifestMap.getSingleElement());
//					} else {
//						Map<String, JsonElement> elementByKey = manifestMap.getElementByKeyMap();
//						LOG.debug("m: "+elementByKey);
//						for (String key0 : elementByKey.keySet()) {
//							JsonElement jsonElement = elementByKey.get(key0);
//							if (jsonElement.isJsonPrimitive()) {
////								ManifestPrimitive = new ManifestPrimitive(jsonElement);
//							}
////							ManifestElement manifestElement0 = new ManifestElement(elementByKey);
//							LOG.debug(key0+"="+jsonElement);
//						}
//						LOG.debug("----");
//					}
				} else {
					LOG.debug(key+" ?? "+jsonObject);
				}
			} else {
				if (manifestElement.getBool() != null) {
					LOG.trace(key+"="+manifestElement.getBool());
				} else if (manifestElement.getDateTime() != null) {
					LOG.trace(key+"="+manifestElement.getDateTime());
				} else if (manifestElement.getDouble() != null) {
					LOG.trace(key+"="+manifestElement.getDouble());
				} else if (manifestElement.getInteger() != null) {
					LOG.trace(key+"="+manifestElement.getInteger());
				} else if (manifestElement.getString() != null) {
					LOG.trace(key+"="+manifestElement.getString());
				} else {
					LOG.error("unknown key: "+key);
				}
			}
		}
	}
	
	@Test
	/** extract data from all_results.json - first entry
	 * 
	 * @throws IOException
	 */
	public void testJSONPath() throws IOException {
		String json = FileUtils.readFileToString(new File(Fixtures.TEST_JSON_DIR, "results0.json"));
		ReadContext ctx = JsonPath.parse(json);
		net.minidev.json.JSONArray authorList = ctx.read("$.authorList[0].author");
		for (int i = 0; i < authorList.size(); i++) { 
			String[] attributeNames = {"fullName", "firstName", "lastName", "initials", "affiliation"};
			for (String attributeName : attributeNames) {
				String authname = ctx.read("$.authorList[0].author["+i+"]."+attributeName+"[0]");
				LOG.trace("auth["+i+"]."+attributeName+"=\""+authname+"\"");
			}
		}
		String[] categoryNames = {
			"id",
			"source",
            "pmid",
            "pmcid",
            "DOI",
            "title",
            "authorString",
            "authorList",
            "journalInfo",
            "pageInfo",
            "abstractText",
            "affiliation",
            "language",
            "pubModel",
            "pubTypeList",
            "subsetList",
           "fullTextUrlList",
           "isOpenAccess",
           "inEPMC",
           "inPMC",
           "citedByCount",
           "hasReferences",
           "hasTextMinedTerms",
           "hasDbCrossReferences",
           "hasLabsLinks",
           "hasTMAccessionNumbers",
           "dateOfCreation",
           "electronicPublicationDate",
           "firstPublicationDate",
           "luceneScore",
		};
		for (String categoryName : categoryNames) {
			net.minidev.json.JSONArray categoryList = ctx.read("$."+categoryName);
			LOG.trace(categoryName+"="+categoryList.get(0));
		}

	}

/** how to find attributes NAMES recursively
var input = {
    "store": {
        "book": [{
            "category": "reference",
                "author": "Nigel Rees",
                "title": "Sayings of the Century",
                "price": 8.95
        }, {
            "category": "fiction",
                "author": "J. R. R. Tolkien",
                "title": "The Lord of the Rings",
                "isbn": "0-395-19395-8",
                "price": 22.99
        }],
            "bicycle": {
            "color": "red",
                "price": 19.95
        }
    },
        "expensive": 10
}

var keys = [];

function recursiveParser(obj) {
    if (!obj) {
        return;
    }
  
    if (obj.constructor == Array) { //if it's an array than parse every element of it
        for (var i = 0; i < obj.length; i++) {
            console.log(obj[i]);
            recursiveParser(obj[i]);
        }
    } else if (obj.constructor == Object) { //if it's json
        for (var key in obj) { //for each key
            if (keys.indexOf(key) === -1) { // if you don't have it
                keys.push(key); //store it
                recursiveParser(obj[key]); //give the value of the key to the parser
            } else {
                recursiveParser(obj[key]); //if you do have it pass the value of the key anyway to the parser
            }
        }
    }
}

recursiveParser(input);
$('#result').text(keys); */
	
}
