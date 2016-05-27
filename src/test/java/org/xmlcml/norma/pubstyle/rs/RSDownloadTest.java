package org.xmlcml.norma.pubstyle.rs;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.xmlcml.cmine.util.CMineTestFixtures;
import org.xmlcml.norma.cproject.HtmlTidier;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class RSDownloadTest {
	
	private static final Logger LOG = Logger.getLogger(RSDownloadTest.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}

	private static final String PRSB_1471_2954 = "1471-2954";
	public JsonParser parser;
	public JsonElement rootElement;
	public JsonElement message;
	public JsonArray items;

	@Test
	public void testCreateDownloadUrl() throws IOException {
		RSDownloader rsDownLoader = new RSDownloader();
		rsDownLoader.getOrCreateFilter().setISSN(PRSB_1471_2954);
		rsDownLoader.getOrCreateFilter().setFromPubDate("2014-01-01");
		rsDownLoader.setRows(250);
		URL url = rsDownLoader.getURL();
		Assert.assertEquals(url.toString(), "http://api.crossref.org/works?filter=issn:1471-2954,from-pub-date:2014-01-01&rows=250");
	}
	
	@Test 
	public void testDownloadJson() throws IOException {
		RSDownloader rsDownLoader = new RSDownloader();
		rsDownLoader.getOrCreateFilter().setISSN(PRSB_1471_2954);
		rsDownLoader.getOrCreateFilter().setFromPubDate("2014-01-01");
		rsDownLoader.setRows(250);
		URL url = rsDownLoader.getURL();
		InputStream stream = url.openStream();
		List<String> content = IOUtils.readLines(stream, "UTF-8");
		Assert.assertEquals(1, content.size());
		
	}
	
	@Test 
	public void testDownloadJson1() throws IOException {
		RSDownloader rsDownLoader = new RSDownloader();
		rsDownLoader.getOrCreateFilter().setISSN(PRSB_1471_2954);
		rsDownLoader.getOrCreateFilter().setFromPubDate("2014-01-01");
		rsDownLoader.setRows(250);
		URL url = rsDownLoader.getURL();
		InputStream stream = url.openStream();
		String content = IOUtils.readLines(stream, "UTF-8").get(0);
		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(content);
		JsonElement message = element.getAsJsonObject().get("message");
		JsonArray items = message.getAsJsonObject().get("items").getAsJsonArray();
		Assert.assertEquals(250, items.size());
		
	}
	@Test 
	public void testDownloadJson2() throws IOException {
		RSDownloader rsDownLoader = new RSDownloader();
		rsDownLoader.getOrCreateFilter().setISSN(PRSB_1471_2954);
		rsDownLoader.getOrCreateFilter().setFromPubDate("2014-01-01");
		rsDownLoader.setRows(250);
//		JsonArray items = rsDownLoader.getItems();
//		Assert.assertEquals(250, items.size());
		List<String> urlList = rsDownLoader.getUrlList();
		LOG.debug(urlList);
		File targetDir = new File("target/pubstyle/rs");
		targetDir.mkdirs();
		IOUtils.writeLines(urlList, "\n", new FileOutputStream(new File(targetDir, "urls.txt")));
		
	}
	
	@Test 
	public void testDownloadJsonTidy() throws IOException {
		File targetDir = new File("target/pubstyle/rs");
		CMineTestFixtures.cleanAndCopyDir(new File("../../../workspace/projects/rs/rs"), targetDir);
		HtmlTidier htmlTidier = new HtmlTidier(targetDir);
		htmlTidier.htmlTidy("jsoup"); 
		htmlTidier.xslTidy("rs2html"); 
		// then run cmine
	}
	
	/*
	 * {

    "status": "ok",
    "message-type": "work-list",
    "message-version": "1.0.0",
    "message": 

	{

    	"query": 

	{

    	"search-terms": null,
    	"start-index": ​0

	},
	"items-per-page": ​250,
	"items": 
	[

	{

    "indexed": 
		{

    	"date-parts": 

	[

        [
            ​2015,
            ​12,
            ​21
        ]
    ],
    "date-time": "2015-12-21T21:24:16Z",
    "timestamp": ​1450733056519

},
"reference-count": ​55,
"publisher": "The Royal Society",
"issue": "1813",
"published-print": 
{

    "date-parts": 

[

        [
            ​2015,
            ​8,
            ​22
        ]
    ]

},
"DOI": "10.1098/rspb.2015.1498",
"type": "journal-article",
"created": 
{

    "date-parts": 

[

        [
            ​2015,
            ​9,
            ​14
        ]
    ],
    "date-time": "2015-09-14T18:27:47Z",
    "timestamp": ​1442255267000

},
"page": "20151498",
"update-policy": "http://dx.doi.org/10.1098/crossmarkpolicy",
"source": "CrossRef",
"title": 
[

    "Making pore choices: repeated regime shifts in stomatal ratio"

],
"prefix": "http://id.crossref.org/prefix/10.1098",
"volume": "282",
"author": 
[

    {
        "affiliation": [ ],
        "family": "Muir",
        "given": "Christopher D.",
        "ORCID": "http://orcid.org/0000-0003-2555-3878"
    }

],
"member": "http://id.crossref.org/member/175",
"published-online": 
{

    "date-parts": 

[

        [
            ​2015,
            ​8,
            ​12
        ]
    ]

},
"container-title": 
[

    "Proceedings of the Royal Society B: Biological Sciences",
    "Proc. R. Soc. B"

],
"deposited": 
{

    "date-parts": 

[

        [
            ​2015,
            ​9,
            ​14
        ]
    ],
    "date-time": "2015-09-14T18:27:47Z",
    "timestamp": ​1442255267000

},
"score": ​1.0,
"subtitle": [ ],
"issued": 
{

    "date-parts": 

[

        [
            ​2015,
            ​8,
            ​12
        ]
    ]

},
"alternative-id": 
[

    "10.1098/rspb.2015.1498"

],
"URL": "http://dx.doi.org/10.1098/rspb.2015.1498",
"ISSN": 
[

    "0962-8452",
    "1471-2954"

],
"subject": 

    [
        "Biochemistry, Genetics and Molecular Biology(all)",
        "Immunology and Microbiology(all)",
        "Agricultural and Biological Sciences(all)",
        "Environmental Science(all)",
        "Medicine(all)"
    ]

},
{

    "indexed": 

{

    "date-parts": 

[

        [
            ​2015,
            ​12,
            ​21
        ]
    ],
    "date-time": "2015-12-21T21:24:21Z",
    "timestamp": ​1450733061484
    */
}

