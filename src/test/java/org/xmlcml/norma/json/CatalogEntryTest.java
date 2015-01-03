package org.xmlcml.norma.json;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import junit.framework.Assert;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class CatalogEntryTest {

	
	private static final Logger LOG = Logger.getLogger(CatalogEntryTest.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}
	
	private static String BIB_SOURCE_JSON = "{\"doi\":\"10.1371/journal.pone.0001764\",\"last_updated\":\"2014-12-02 0449\",\"description\":\"PLOS ONE: an inclusive, peer-reviewed, open-access resource from the PUBLIC LIBRARY OF SCIENCE. Reports of well-performed scientific studies from all disciplines freely available to the whole world.\",\"author\":[\"Alessandro Achilli\",\"Ugo A. Perego\",\"Claudio M. Bravi\",\"Michael D. Coble\",\"Qing-Peng Kong\",\"Scott R. Woodward\",\"Antonio Salas\",\"Antonio Torroni\",\"Hans-Jürgen Bandelt\"],\"firstpage\":\"The Phylogeny of the Four Pan-American MtDNA Haplogroups: Implications for Evolutionary and Disease Studies\",\"id\":\"e1764\",\"volume\":\"b11a6a6453b24ecb9313f9eec27501c4\",\"fulltext_xml\":\"3\",\"created_date\":\"2014-12-02 0449\",\"date\":\"2008/3/12\",\"issue\":\"3\",\"fulltext_pdf\":\"http://dx.plos.org/10.1371/journal.pone.0001764.pdf\"}";
	private static String HIT_JSON = "{\"_score\":0.5521466,\"_type\":\"catalogue\",\"_id\":\"b11a6a6453b24ecb9313f9eec27501c4\",\"_source\":{\"doi\":\"10.1371/journal.pone.0001764\",\"last_updated\":\"2014-12-02 0449\",\"description\":\"PLOS ONE: an inclusive, peer-reviewed, open-access resource from the PUBLIC LIBRARY OF SCIENCE. Reports of well-performed scientific studies from all disciplines freely available to the whole world.\",\"author\":[\"Alessandro Achilli\",\"Ugo A. Perego\",\"Claudio M. Bravi\",\"Michael D. Coble\",\"Qing-Peng Kong\",\"Scott R. Woodward\",\"Antonio Salas\",\"Antonio Torroni\",\"Hans-Jürgen Bandelt\"],\"firstpage\":\"The Phylogeny of the Four Pan-American MtDNA Haplogroups: Implications for Evolutionary and Disease Studies\",\"id\":\"e1764\",\"volume\":\"b11a6a6453b24ecb9313f9eec27501c4\",\"fulltext_xml\":\"3\",\"created_date\":\"2014-12-02 0449\",\"date\":\"2008/3/12\",\"issue\":\"3\",\"fulltext_pdf\":\"http://dx.plos.org/10.1371/journal.pone.0001764.pdf\"},\"_index\":\"contentmine\"}";
	private static String CATALOG_ENTRY_JSON = "{\"hits\":{\"hits\":[{\"_score\":0.5521466,\"_type\":\"catalogue\",\"_id\":\"b11a6a6453b24ecb9313f9eec27501c4\",\"_source\":{\"doi\":\"10.1371/journal.pone.0001764\",\"last_updated\":\"2014-12-02 0449\",\"description\":\"PLOS ONE: an inclusive, peer-reviewed, open-access resource from the PUBLIC LIBRARY OF SCIENCE. Reports of well-performed scientific studies from all disciplines freely available to the whole world.\",\"author\":[\"Alessandro Achilli\",\"Ugo A. Perego\",\"Claudio M. Bravi\",\"Michael D. Coble\",\"Qing-Peng Kong\",\"Scott R. Woodward\",\"Antonio Salas\",\"Antonio Torroni\",\"Hans-Jürgen Bandelt\"],\"firstpage\":\"The Phylogeny of the Four Pan-American MtDNA Haplogroups: Implications for Evolutionary and Disease Studies\",\"id\":\"e1764\",\"volume\":\"b11a6a6453b24ecb9313f9eec27501c4\",\"fulltext_xml\":\"3\",\"created_date\":\"2014-12-02 0449\",\"date\":\"2008/3/12\",\"issue\":\"3\",\"fulltext_pdf\":\"http://dx.plos.org/10.1371/journal.pone.0001764.pdf\"},\"_index\":\"contentmine\"},{\"_score\":0.5521422,\"_type\":\"catalogue\",\"_id\":\"b11a6a6453b24ecb9313f9eec27501c5\",\"_source\":{\"doi\":\"10.1371/journal.pone.0001765\",\"last_updated\":\"2014-12-02 0448\",\"description\":\"PLOS ONE: an inclusive, peer-reviewed, open-access resource from the PUBLIC LIBRARY OF SCIENCE. Reports of well-performed scientific studies from all disciplines freely available to the whole world.\",\"author\":[\"Foo Bar\",\"Plugh Xyzzy\"],\"firstpage\":\"Junk title\",\"id\":\"e1765\",\"volume\":\"b11a6a6453b24ecb9313f9eec27501c5\",\"fulltext_xml\":\"8\",\"created_date\":\"2014-12-02 0448\",\"date\":\"2008/3/19\",\"issue\":\"4\",\"fulltext_pdf\":\"http://dx.plos.org/10.1371/journal.pone.0001765.pdf\"},\"_index\":\"contentmine\"}],\"total\":54,\"max_score\":0.75475913},\"_shards\":[{\"name\":\"successful\",\"value\":5},{\"name\":\"failed\",\"value\":0},{\"name\":\"total\",\"value\":5}],\"took\":2,\"timed_out\":false}";

	private static String[] AUTHORS0 =
			new String[] {
	        "Alessandro Achilli",
		    "Ugo A. Perego",
		    "Claudio M. Bravi",
		    "Michael D. Coble",
		    "Qing-Peng Kong",
		    "Scott R. Woodward",
		    "Antonio Salas",
		    "Antonio Torroni",
		    "Hans-J\u00fcrgen Bandelt"
		};
	
	private static String[] AUTHORS1 =
			new String[] {
	        "Foo Bar",
		    "Plugh Xyzzy",
		};
	
	private static BibSource BIB_SOURCE0 =
		new BibSource(
	         "10.1371/journal.pone.0001764",
	         "2014-12-02 0449",
	         "PLOS ONE: an inclusive, peer-reviewed, open-access resource from the PUBLIC LIBRARY OF SCIENCE. Reports of well-performed scientific studies from all disciplines freely available to the whole world.",
	 		 Arrays.asList(AUTHORS0),
	         "The Phylogeny of the Four Pan-American MtDNA Haplogroups: Implications for Evolutionary and Disease Studies",
	         "e1764",
	         "b11a6a6453b24ecb9313f9eec27501c4",
	         "3",
	         "2014-12-02 0449",
	         "2008/3/12",
	         "3",
	          "http://dx.plos.org/10.1371/journal.pone.0001764.pdf"
	         );
	
	private BibSource BIB_SOURCE1 =
		new BibSource(
	         "10.1371/journal.pone.0001765",
	         "2014-12-02 0448",
	         "PLOS ONE: an inclusive, peer-reviewed, open-access resource from the PUBLIC LIBRARY OF SCIENCE. Reports of well-performed scientific studies from all disciplines freely available to the whole world.",
	 		 Arrays.asList(AUTHORS1),
	         "Junk title",
	         "e1765",
	         "b11a6a6453b24ecb9313f9eec27501c5",
	         "8",
	         "2014-12-02 0448",
	         "2008/3/19",
	         "4",
	          "http://dx.plos.org/10.1371/journal.pone.0001765.pdf"
	         );
	
	private Hit HIT0 = 
		new Hit(
			0.5521466,
		    "catalogue",
		    "b11a6a6453b24ecb9313f9eec27501c4",
			BIB_SOURCE0,
			"contentmine"
			);
	
	private Hit HIT1 = 
		new Hit(
			0.5521422,
		    "catalogue",
		    "b11a6a6453b24ecb9313f9eec27501c5",
			BIB_SOURCE1,
			"contentmine"
			);
	
	private Collection<Hit> HIT_ARRAY = Arrays.asList(new Hit[]{HIT0, HIT1});

	private Hits HITS = new Hits(
		HIT_ARRAY,
		54,
		0.75475913
		);

	private Collection<Shard> SHARDS = Arrays.asList(
		new Shard("successful", 5),
		new Shard("failed", 0),
		new Shard("total", 5)
		);
	
	private CatalogEntry CATALOG_ENTRY = 
		new CatalogEntry (
			HITS,
			SHARDS,
			2,
			false
		);

	File RSU_JSON_FILE = new File("src/test/resources/org/xmlcml/norma/document/plosone/rsu.json");

	/** part of entry.
	 * 
	  {"doi": "10.1371/journal.pone.0001764",
	    "last_updated": "2014-12-02 0449",
	    "description": "PLOS ONE: an inclusive, peer-reviewed, open-access resource from the PUBLIC LIBRARY OF SCIENCE. Reports of well-performed scientific studies from all disciplines freely available to the whole world.",
	    "author": ["Alessandro Achilli",
	    "Ugo A. Perego",
	    "Claudio M. Bravi",
	    "Michael D. Coble",
	    "Qing-Peng Kong",
	    "Scott R. Woodward",
	    "Antonio Salas",
	    "Antonio Torroni",
	    "Hans-J\u00fcrgen Bandelt"],
	    "title": "The Phylogeny of the Four Pan-American MtDNA Haplogroups: Implications for Evolutionary and Disease Studies",
	    "firstpage": "e1764",
	    "id": "b11a6a6453b24ecb9313f9eec27501c4",
	    "volume": "3",
	    "created_date": "2014-12-02 0449",
	    "date": "2008/3/12",
	    "issue": "3",
	    "fulltext_pdf": "http://dx.plos.org/10.1371/journal.pone.0001764.pdf"
      },
	 */
	@Test
	public void testSerializeBibSource() {
		Gson gson = new Gson();
		String json = gson.toJson(BIB_SOURCE0);
		Assert.assertEquals("bib",  "{\"doi\":\"10.1371/journal.pone.0001764\",\"last_updated\":\"2014-12-02 0449\",\"description\":\"PLOS ONE: an inclusive, peer-reviewed, open-access resource from the PUBLIC LIBRARY OF SCIENCE. Reports of well-performed scientific studies from all disciplines freely available to the whole world.\",\"author\":[\"Alessandro Achilli\",\"Ugo A. Perego\",\"Claudio M. Bravi\",\"Michael D. Coble\",\"Qing-Peng Kong\",\"Scott R. Woodward\",\"Antonio Salas\",\"Antonio Torroni\",\"Hans-Jürgen Bandelt\"],\"firstpage\":\"The Phylogeny of the Four Pan-American MtDNA Haplogroups: Implications for Evolutionary and Disease Studies\",\"id\":\"e1764\",\"volume\":\"b11a6a6453b24ecb9313f9eec27501c4\",\"fulltext_xml\":\"3\",\"created_date\":\"2014-12-02 0449\",\"date\":\"2008/3/12\",\"issue\":\"3\",\"fulltext_pdf\":\"http://dx.plos.org/10.1371/journal.pone.0001764.pdf\"}", json);
	}
	
	@Test 
	public void testSerializeHit() {
		Gson gson = new Gson();
		String json = gson.toJson(HIT0);
		Assert.assertEquals("hit",  "{\"_score\":0.5521466,\"_type\":\"catalogue\",\"_id\":\"b11a6a6453b24ecb9313f9eec27501c4\",\"_source\":{\"doi\":\"10.1371/journal.pone.0001764\",\"last_updated\":\"2014-12-02 0449\",\"description\":\"PLOS ONE: an inclusive, peer-reviewed, open-access resource from the PUBLIC LIBRARY OF SCIENCE. Reports of well-performed scientific studies from all disciplines freely available to the whole world.\",\"author\":[\"Alessandro Achilli\",\"Ugo A. Perego\",\"Claudio M. Bravi\",\"Michael D. Coble\",\"Qing-Peng Kong\",\"Scott R. Woodward\",\"Antonio Salas\",\"Antonio Torroni\",\"Hans-Jürgen Bandelt\"],\"firstpage\":\"The Phylogeny of the Four Pan-American MtDNA Haplogroups: Implications for Evolutionary and Disease Studies\",\"id\":\"e1764\",\"volume\":\"b11a6a6453b24ecb9313f9eec27501c4\",\"fulltext_xml\":\"3\",\"created_date\":\"2014-12-02 0449\",\"date\":\"2008/3/12\",\"issue\":\"3\",\"fulltext_pdf\":\"http://dx.plos.org/10.1371/journal.pone.0001764.pdf\"},\"_index\":\"contentmine\"}", json);
	}
	
	@Test 
	public void testSerializeCatalogEntry() {
		Gson gson = new Gson();
		String json = gson.toJson(CATALOG_ENTRY);
		Assert.assertEquals("hit",  "{\"hits\":{\"hits\":[{\"_score\":0.5521466,\"_type\":\"catalogue\",\"_id\":\"b11a6a6453b24ecb9313f9eec27501c4\",\"_source\":{\"doi\":\"10.1371/journal.pone.0001764\",\"last_updated\":\"2014-12-02 0449\",\"description\":\"PLOS ONE: an inclusive, peer-reviewed, open-access resource from the PUBLIC LIBRARY OF SCIENCE. Reports of well-performed scientific studies from all disciplines freely available to the whole world.\",\"author\":[\"Alessandro Achilli\",\"Ugo A. Perego\",\"Claudio M. Bravi\",\"Michael D. Coble\",\"Qing-Peng Kong\",\"Scott R. Woodward\",\"Antonio Salas\",\"Antonio Torroni\",\"Hans-Jürgen Bandelt\"],\"firstpage\":\"The Phylogeny of the Four Pan-American MtDNA Haplogroups: Implications for Evolutionary and Disease Studies\",\"id\":\"e1764\",\"volume\":\"b11a6a6453b24ecb9313f9eec27501c4\",\"fulltext_xml\":\"3\",\"created_date\":\"2014-12-02 0449\",\"date\":\"2008/3/12\",\"issue\":\"3\",\"fulltext_pdf\":\"http://dx.plos.org/10.1371/journal.pone.0001764.pdf\"},\"_index\":\"contentmine\"},{\"_score\":0.5521422,\"_type\":\"catalogue\",\"_id\":\"b11a6a6453b24ecb9313f9eec27501c5\",\"_source\":{\"doi\":\"10.1371/journal.pone.0001765\",\"last_updated\":\"2014-12-02 0448\",\"description\":\"PLOS ONE: an inclusive, peer-reviewed, open-access resource from the PUBLIC LIBRARY OF SCIENCE. Reports of well-performed scientific studies from all disciplines freely available to the whole world.\",\"author\":[\"Foo Bar\",\"Plugh Xyzzy\"],\"firstpage\":\"Junk title\",\"id\":\"e1765\",\"volume\":\"b11a6a6453b24ecb9313f9eec27501c5\",\"fulltext_xml\":\"8\",\"created_date\":\"2014-12-02 0448\",\"date\":\"2008/3/19\",\"issue\":\"4\",\"fulltext_pdf\":\"http://dx.plos.org/10.1371/journal.pone.0001765.pdf\"},\"_index\":\"contentmine\"}],\"total\":54,\"max_score\":0.75475913},\"_shards\":[{\"name\":\"successful\",\"value\":5},{\"name\":\"failed\",\"value\":0},{\"name\":\"total\",\"value\":5}],\"took\":2,\"timed_out\":false}", json);
	}
	
	@Test
	public void testDeserializeBibSource() {
	    JsonParser parser = new JsonParser();
	    JsonElement jsonElement = parser.parse(BIB_SOURCE_JSON);
	    BibSource bibSource = BibSource.createBibSource(jsonElement.getAsJsonObject());
	    Assert.assertEquals("date", "2008/3/12", bibSource.getDate().toString());
	    Assert.assertEquals("bib", " / 10.1371/journal.pone.0001764 / 2014-12-02 0449 / PLOS ONE: an inclusive, peer-reviewed, open-access resource from the PUBLIC LIBRARY OF SCIENCE. Reports of well-performed scientific studies from all disciplines freely available to the whole world. / [Alessandro Achilli, Ugo A. Perego, Claudio M. Bravi, Michael D. Coble, Qing-Peng Kong, Scott R. Woodward, Antonio Salas, Antonio Torroni, Hans-Jürgen Bandelt] / The Phylogeny of the Four Pan-American MtDNA Haplogroups: Implications for Evolutionary and Disease Studies / e1764 / b11a6a6453b24ecb9313f9eec27501c4 / 3 / 2014-12-02 0449 / 2008/3/12 / 3 / http://dx.plos.org/10.1371/journal.pone.0001764.pdf", 
	    		bibSource.toString());
	}
	
	@Test
	public void testDeserializeHit() {
	    JsonParser parser = new JsonParser();
	    JsonElement jsonElement = parser.parse(HIT_JSON);
	    Hit hit = Hit.createHit(jsonElement.getAsJsonObject());
	    Assert.assertEquals("score", 0.5521466, hit.get_score(), 0.000001);
	    Assert.assertEquals("hit", "0.5521466 / b11a6a6453b24ecb9313f9eec27501c4 /  / 10.1371/journal.pone.0001764 / 2014-12-02 0449 / PLOS ONE: an inclusive, peer-reviewed, open-access resource from the PUBLIC LIBRARY OF SCIENCE. Reports of well-performed scientific studies from all disciplines freely available to the whole world. / [Alessandro Achilli, Ugo A. Perego, Claudio M. Bravi, Michael D. Coble, Qing-Peng Kong, Scott R. Woodward, Antonio Salas, Antonio Torroni, Hans-Jürgen Bandelt] / The Phylogeny of the Four Pan-American MtDNA Haplogroups: Implications for Evolutionary and Disease Studies / e1764 / b11a6a6453b24ecb9313f9eec27501c4 / 3 / 2014-12-02 0449 / 2008/3/12 / 3 / http://dx.plos.org/10.1371/journal.pone.0001764.pdf / contentmine / ",
	    		hit.toString());
	}
	
	@Test
	public void testDeserializeCatalogEntry() {
	    JsonParser parser = new JsonParser();
		LOG.debug("\n"+CATALOG_ENTRY_JSON.substring(0,200));
	    JsonElement jsonElement = parser.parse(CATALOG_ENTRY_JSON);
	    CatalogEntry catalogEntry = CatalogEntry.createCatalogEntry(jsonElement.getAsJsonObject());
	    Assert.assertEquals("took", 2, catalogEntry.getTook().intValue());
	    Assert.assertFalse("timed_out", catalogEntry.getTimed_out());
	    Assert.assertEquals("hit", "[0.5521466 / b11a6a6453b24ecb9313f9eec27501c4 /  / 10.1371/journal.pone.0001764 / 2014-12-02 0449 / PLOS ONE: an inclusive, peer-reviewed, open-access resource from the PUBLIC LIBRARY OF SCIENCE. Reports of well-performed scientific studies from all disciplines freely available to the whole world. / [Alessandro Achilli, Ugo A. Perego, Claudio M. Bravi, Michael D. Coble, Qing-Peng Kong, Scott R. Woodward, Antonio Salas, Antonio Torroni, Hans-Jürgen Bandelt] / The Phylogeny of the Four Pan-American MtDNA Haplogroups: Implications for Evolutionary and Disease Studies / e1764 / b11a6a6453b24ecb9313f9eec27501c4 / 3 / 2014-12-02 0449 / 2008/3/12 / 3 / http://dx.plos.org/10.1371/journal.pone.0001764.pdf / contentmine / , 0.5521422 / b11a6a6453b24ecb9313f9eec27501c5 /  / 10.1371/journal.pone.0001765 / 2014-12-02 0448 / PLOS ONE: an inclusive, peer-reviewed, open-access resource from the PUBLIC LIBRARY OF SCIENCE. Reports of well-performed scientific studies from all disciplines freely available to the whole world. / [Foo Bar, Plugh Xyzzy] / Junk title / e1765 / b11a6a6453b24ecb9313f9eec27501c5 / 8 / 2014-12-02 0448 / 2008/3/19 / 4 / http://dx.plos.org/10.1371/journal.pone.0001765.pdf / contentmine / ] / 54 / 0.75475913 /  / successful / 5 / failed / 0 / total / 5 /  / 2 / false / ",
	    		catalogEntry.toString());
	}
	
	@Test
	@Ignore // not yet working
	public void testCompleteCatalog() throws IOException {
		
	    JsonParser parser = new JsonParser();
		String RSU_JSON = FileUtils.readFileToString(RSU_JSON_FILE);
//		LOG.debug("\n"+RSU_JSON.substring(0,200));
	    JsonElement jsonElement = parser.parse(RSU_JSON);
	    JsonObject jsonObject = jsonElement.getAsJsonObject();
	    jsonObject.getAsJsonArray();
	    CatalogEntry catalogEntry = CatalogEntry.createCatalogEntry(jsonObject);
	    Assert.assertEquals("took", 2, catalogEntry.getTook().intValue());
	    Assert.assertFalse("timed_out", catalogEntry.getTimed_out());
	    Assert.assertEquals("hit", "[0.5521466 / b11a6a6453b24ecb9313f9eec27501c4 /  / 10.1371/journal.pone.0001764 / 2014-12-02 0449 / PLOS ONE: an inclusive, peer-reviewed, open-access resource from the PUBLIC LIBRARY OF SCIENCE. Reports of well-performed scientific studies from all disciplines freely available to the whole world. / [Alessandro Achilli, Ugo A. Perego, Claudio M. Bravi, Michael D. Coble, Qing-Peng Kong, Scott R. Woodward, Antonio Salas, Antonio Torroni, Hans-Jürgen Bandelt] / The Phylogeny of the Four Pan-American MtDNA Haplogroups: Implications for Evolutionary and Disease Studies / e1764 / b11a6a6453b24ecb9313f9eec27501c4 / 3 / 2014-12-02 0449 / 2008/3/12 / 3 / http://dx.plos.org/10.1371/journal.pone.0001764.pdf / contentmine / , 0.5521422 / b11a6a6453b24ecb9313f9eec27501c5 /  / 10.1371/journal.pone.0001765 / 2014-12-02 0448 / PLOS ONE: an inclusive, peer-reviewed, open-access resource from the PUBLIC LIBRARY OF SCIENCE. Reports of well-performed scientific studies from all disciplines freely available to the whole world. / [Foo Bar, Plugh Xyzzy] / Junk title / e1765 / b11a6a6453b24ecb9313f9eec27501c5 / 8 / 2014-12-02 0448 / 2008/3/19 / 4 / http://dx.plos.org/10.1371/journal.pone.0001765.pdf / contentmine / ] / 54 / 0.75475913 /  / successful / 5 / failed / 0 / total / 5 /  / 2 / false / ",
	    		catalogEntry.toString());
	}
}
