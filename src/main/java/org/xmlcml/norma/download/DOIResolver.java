package org.xmlcml.norma.download;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.xmlcml.norma.pubstyle.rs.RSDownloadTest;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class DOIResolver {

	private static final String URL = "URL";
	private static final String TYPE = "type";
	private static final Logger LOG = Logger.getLogger(DOIResolver.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}

	private static final String HTTP_DOI_ORG_API_HANDLES = "http://doi.org/api/handles/";
	private static final String HTTP_DX_DOI_ORG = "http://dx.doi.org/";
	private static final String VALUE = "value";
	private static final String DATA = "data";
	private static final String VALUES = "values";
	
	private List<String> urlStrings;
	private List<String> doiStrings;
//	private boolean skipHyphenDois;
	

	public DOIResolver() {
		
	}

	/** reads list of DOIs from crossRefDOIsFile file and resolves them through dx.doi.org to resolvedDoisFile
	 * 
	 * @throws IOException
	 * @throws MalformedURLException
	 * @throws FileNotFoundException
	 */
	public List<String> resolveDOIs(List<String> dois) throws IOException, MalformedURLException, FileNotFoundException {
		urlStrings = new ArrayList<String>();
		int i = 0;
		for (String doi : dois) {
			String urlString = resolveDOI(doi);
			if (urlString != null) {
				urlStrings.add(urlString);
			}
			if (i++ % 10 == 0) System.out.print(".");
		}
		return urlStrings;
	}

	/** resolves a single CrossrefDOI to URL.
	 * 
	 * Typical response:
	 * 	
	 * 
{

    "responseCode": 1,
    "handle": "10.3389/fpsyg.2016.00565",
    "values": [
        {
            "index": 1,
            "type": "URL",
            "data": {
                "format": "string",
                "value": "http://journal.frontiersin.org/article/10.3389/fpsyg.2016.00565"
            },
            "ttl": 86400,
            "timestamp": "2016-05-01T03:01:22Z"
        },
        {
            "index": 700050,
            "type": "700050",
            "data": {
                "format": "string",
                "value": "201605010301"
            },
            "ttl": 86400,
            "timestamp": "2016-05-01T03:01:22Z"
        },
        {
            "index": 100,
            "type": "HS_ADMIN",
            "data": {
                "format": "admin",
                "value": {
                    "handle": "0.na/10.3389",
                    "index": 200,
                    "permissions": "111111110010"
                }
            },
            "ttl": 86400,
            "timestamp": "2016-05-01T03:01:22Z"
        }
    ]
	* we take the first values[] and extract the data.value

	 * @param crossRefDOI
	 * @return null for malformed URL, or if skipHyphens and domain has hyphen
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public String resolveDOI(String doiString) throws MalformedURLException, IOException {
		String handleURL = doiString.replace(HTTP_DX_DOI_ORG, HTTP_DOI_ORG_API_HANDLES);
		URL url = new URL(handleURL);
		InputStream is = (InputStream) url.getContent();
		// returns a list of lines but think there is only ever one line
		List<String> ss = IOUtils.readLines(is);
		is.close();
		String s = ss.get(0);
		// transform to JSON
		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(s);
		JsonObject object = element.getAsJsonObject();
		JsonArray array = object.getAsJsonArray(VALUES);
		JsonObject object1 = array.get(0).getAsJsonObject();
		String type = object1.get(TYPE).getAsString();
		String resolvedURL = null;
		if (!URL.equals(type)) {
			LOG.warn("missing URL type; found "+type+"; array size: "+array.size());
		} else {
			JsonObject object2 = object1.get(DATA).getAsJsonObject();
			JsonElement element1 = object2.get(VALUE);
			try {
				resolvedURL = element1.getAsString();
			} catch (Exception e) {
				LOG.debug(">>> "+element1+"; "+e);
			}
		}
		return resolvedURL;
	}

	public void writeResolvedURLsFile(File urlFile) throws IOException {
		if (urlStrings != null) {
			FileUtils.writeLines(urlFile, urlStrings, "\n");
		} else {
			LOG.error("No resolved URLs");
		}
	}

	public void resolveDOIs(File doiFile) throws IOException {
		if (doiFile != null) {
			doiStrings = FileUtils.readLines(doiFile, Charset.forName("UTF-8"));
			urlStrings = resolveDOIs(doiStrings);
		}
	}

}
