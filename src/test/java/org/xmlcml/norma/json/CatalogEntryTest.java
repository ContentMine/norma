package org.xmlcml.norma.json;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class CatalogEntryTest {

	
	/** NOTE: this contains &uuml; (\u00fc) . If this file is kept as UTF-8 there should
	 * be no problem. But if it is copied to some other encoding it will fail.
	 */
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

	private File RSU_JSON_FILE = new File("src/test/resources/org/xmlcml/norma/pubstyle/plosone/rsu.json");
	private File SNIPPET_JSON_FILE = new File("src/test/resources/org/xmlcml/norma/json/snippet.json");

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
	    "Hans-Jürgen Bandelt"], // ISOLATIN character
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
		LOG.trace("\n"+CATALOG_ENTRY_JSON.substring(0,200));
	    JsonElement jsonElement = parser.parse(CATALOG_ENTRY_JSON);
	    CatalogEntry catalogEntry = CatalogEntry.createCatalogEntry(jsonElement.getAsJsonObject());
	    Assert.assertEquals("took", 2, catalogEntry.getTook().intValue());
	    Assert.assertFalse("timed_out", catalogEntry.getTimed_out());
	    Assert.assertEquals("hit", "[0.5521466 / b11a6a6453b24ecb9313f9eec27501c4 /  / 10.1371/journal.pone.0001764 / 2014-12-02 0449 / PLOS ONE: an inclusive, peer-reviewed, open-access resource from the PUBLIC LIBRARY OF SCIENCE. Reports of well-performed scientific studies from all disciplines freely available to the whole world. / [Alessandro Achilli, Ugo A. Perego, Claudio M. Bravi, Michael D. Coble, Qing-Peng Kong, Scott R. Woodward, Antonio Salas, Antonio Torroni, Hans-Jürgen Bandelt] / The Phylogeny of the Four Pan-American MtDNA Haplogroups: Implications for Evolutionary and Disease Studies / e1764 / b11a6a6453b24ecb9313f9eec27501c4 / 3 / 2014-12-02 0449 / 2008/3/12 / 3 / http://dx.plos.org/10.1371/journal.pone.0001764.pdf / contentmine / , 0.5521422 / b11a6a6453b24ecb9313f9eec27501c5 /  / 10.1371/journal.pone.0001765 / 2014-12-02 0448 / PLOS ONE: an inclusive, peer-reviewed, open-access resource from the PUBLIC LIBRARY OF SCIENCE. Reports of well-performed scientific studies from all disciplines freely available to the whole world. / [Foo Bar, Plugh Xyzzy] / Junk title / e1765 / b11a6a6453b24ecb9313f9eec27501c5 / 8 / 2014-12-02 0448 / 2008/3/19 / 4 / http://dx.plos.org/10.1371/journal.pone.0001765.pdf / contentmine / ] / 54 / 0.75475913 /  / successful / 5 / failed / 0 / total / 5 /  / 2 / false / ",
	    		catalogEntry.toString());
	}
	
	@Test
	@Ignore // problems with character encodings not yet sorted
	public void testCompleteCatalog() throws IOException {
		
	    JsonParser parser = new JsonParser();
		String RSU_JSON = FileUtils.readFileToString(RSU_JSON_FILE);
	    JsonElement jsonElement = parser.parse(RSU_JSON);
	    JsonObject jsonObject = jsonElement.getAsJsonObject();
	    CatalogEntry catalogEntry = CatalogEntry.createCatalogEntry(jsonObject);
	    Assert.assertEquals("took", 2, catalogEntry.getTook().intValue());
	    Assert.assertFalse("timed_out", catalogEntry.getTimed_out());
	    // has ISO-Latin ignore for the present 
	    Assert.assertEquals("hit", "[0.75475913 / 54d58c9da5c44dfd9c5313107ade2152 /  / 10.1371/journal.pone.0102272 / 2014-12-01 0620 / PLOS ONE: an inclusive, peer-reviewed, open-access resource from the PUBLIC LIBRARY OF SCIENCE. Reports of well-performed scientific studies from all disciplines freely available to the whole world. / [The PLOS ONE Staff] / e102272 / 54d58c9da5c44dfd9c5313107ade2152 / 9 / /article/fetchObjectAttachment.action?uri=info%3Adoi%2F10.1371%2Fjournal.pone.0102272&representation=XML / 2014-12-01 0616 / 2014/7/2 / 7 / http://dx.plos.org/10.1371/journal.pone.0102272.pdf / contentmine / , 0.7116731 / 0d84b65dd0f044ab9f6a5e430104315d /  / 10.1371/journal.pone.0070048 / 2014-12-29 1902 / PLOS ONE: an inclusive, peer-reviewed, open-access resource from the PUBLIC LIBRARY OF SCIENCE. Reports of well-performed scientific studies from all disciplines freely available to the whole world. / [Angelina R. Sutin, Antonio Terracciano] / e70048 / 0d84b65dd0f044ab9f6a5e430104315d / 8 / null / 2014-12-29 1902 / 2013/7/24 / 7 / http://dx.plos.org/10.1371/journal.pone.0070048.pdf / contentmine / , 0.6614297 / f53f4ee866964ab08417720ed500e72d /  / 10.1371/journal.pbio.1001934 / 2014-12-01 1929 / PLOS Biology is an open-access, peer-reviewed journal that features works of exceptional significance in all areas of biological science, from molecules to ecosystems, including works at the interface with other disciplines. / [Johan J. Bolhuis, Ian Tattersall, Noam Chomsky, Robert C. Berwick] / e1001934 / f53f4ee866964ab08417720ed500e72d / 12 / null / 2014-12-01 1929 / 2014/8/26 / 8 / http://dx.plos.org/10.1371/journal.pbio.1001934.pdf / contentmine / , 0.6614297 / d66cb9ce42ab40c68fbd6d8d548e267c /  / 10.1371/journal.pone.0053095 / 2014-12-07 1523 / PLOS ONE: an inclusive, peer-reviewed, open-access resource from the PUBLIC LIBRARY OF SCIENCE. Reports of well-performed scientific studies from all disciplines freely available to the whole world. / [Mahendra Piraveenan, Mikhail Prokopenko, Liaquat Hossain] / e53095 / d66cb9ce42ab40c68fbd6d8d548e267c / 8 / null / 2014-12-07 1523 / 2013/1/22 / 1 / http://dx.plos.org/10.1371/journal.pone.0053095.pdf / contentmine / , 0.6227139 / 2cd1ac54443e463bb1f385febae2ea94 /  / 10.1371/journal.pone.0105948 / 2014-12-01 1926 / PLOS ONE: an inclusive, peer-reviewed, open-access resource from the PUBLIC LIBRARY OF SCIENCE. Reports of well-performed scientific studies from all disciplines freely available to the whole world. / [Richard D. Norris, James M. Norris, Ralph D. Lorenz, Jib Ray, Brian Jackson] / e105948 / 2cd1ac54443e463bb1f385febae2ea94 / 9 / null / 2014-12-01 1926 / 2014/8/27 / 8 / http://dx.plos.org/10.1371/journal.pone.0105948.pdf / contentmine / , 0.6227139 / e2a99c800e4f4e7caad2cff11c4b5880 /  / 10.1371/journal.pntd.0003131 / 2014-12-04 1819 / PLOS Neglected Tropical Diseases is an open-access journal publishing peer-reviewed research on the world\\'s most neglected tropical diseases, such as elephantiasis, river blindness, leprosy, hookworm, schistosomiasis, and African sleeping sickness / [Samson Leta, Thi Ha Thanh Dao, Frehiwot Mesele, Gezahegn Alemayehu] / e3131 / e2a99c800e4f4e7caad2cff11c4b5880 / 8 / null / 2014-12-01 1925 / 2014/9/4 / 9 / http://dx.plos.org/10.1371/journal.pntd.0003131.pdf / contentmine / , 0.6099375 / 2e865e2652ad4adb95303092d6c8b7a2 /  / 10.1371/journal.pcbi.1003892 / 2014-12-31 0120 / PLOS Computational Biology is an open-access / [Nicholas Generous, Geoffrey Fairchild, Alina Deshpande, Sara Y. Del Valle, Reid Priedhorsky] / e1003892 / 2e865e2652ad4adb95303092d6c8b7a2 / 10 / null / 2014-12-30 2017 / 2014/11/13 / 11 / http://dx.plos.org/10.1371/journal.pcbi.1003892.pdf / contentmine / , 0.5906909 / 9bbeac16ebe040f4bf4ee49b7b763053 /  / 10.1371/journal.pone.0094346 / 2014-12-04 1819 / PLOS ONE: an inclusive, peer-reviewed, open-access resource from the PUBLIC LIBRARY OF SCIENCE. Reports of well-performed scientific studies from all disciplines freely available to the whole world. / [Chuong V. Nguyen, David R. Lovell, Matt Adcock, John La Salle] / e94346 / 9bbeac16ebe040f4bf4ee49b7b763053 / 9 / null / 2014-12-01 1926 / 2014/4/23 / 4 / http://dx.plos.org/10.1371/journal.pone.0094346.pdf / contentmine / , 0.5521466 / b11a6a6453b24ecb9313f9eec27501c4 /  / 10.1371/journal.pone.0001764 / 2014-12-02 0449 / PLOS ONE: an inclusive, peer-reviewed, open-access resource from the PUBLIC LIBRARY OF SCIENCE. Reports of well-performed scientific studies from all disciplines freely available to the whole world. / [Alessandro Achilli, Ugo A. Perego, Claudio M. Bravi, Michael D. Coble, Qing-Peng Kong, Scott R. Woodward, Antonio Salas, Antonio Torroni, Hans-Jürgen Bandelt] / e1764 / b11a6a6453b24ecb9313f9eec27501c4 / 3 / null / 2014-12-02 0449 / 2008/3/12 / 3 / http://dx.plos.org/10.1371/journal.pone.0001764.pdf / contentmine / , 0.45745313 / bb282dc238684b51bc1ee60ed1a4bc6b /  / 10.1371/journal.pone.0000829 / 2014-12-02 0449 / PLOS ONE: an inclusive, peer-reviewed, open-access resource from the PUBLIC LIBRARY OF SCIENCE. Reports of well-performed scientific studies from all disciplines freely available to the whole world. / [Erika Tamm, Toomas Kivisild, Maere Reidla, Mait Metspalu, David Glenn Smith, Connie J. Mulligan, Claudio M. Bravi, Olga Rickards, Cristina Martinez-Labarga, Elsa K. Khusnutdinova, Sardana A. Fedorova, Maria V. Golubenko, Vadim A. Stepanov, Marina A. Gubina, Sergey I. Zhadanov, Ludmila P. Ossipova, Larisa Damba, Mikhail I. Voevoda, Jose E. Dipierri, Richard Villems, Ripan S. Malhi] / e829 / bb282dc238684b51bc1ee60ed1a4bc6b / 2 / null / 2014-12-02 0449 / 2007/9/5 / 9 / http://dx.plos.org/10.1371/journal.pone.0000829.pdf / contentmine / ] / 54 / 0.75475913 /  / null / null /  / 2 / false / ",
	    		catalogEntry.toString());
	}
	
	
}
