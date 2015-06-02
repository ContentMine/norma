package org.xmlcml.norma.json;

import java.io.File;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.xmlcml.norma.Fixtures;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/** manages manifest.json in CMDir
 * 
 * @author pm286
 *
 */
public class ManifestJson {

	
	private static final Logger LOG = Logger.getLogger(ManifestJson.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}

	private JsonElement rootElement;
	private JsonObject rootObject;
	private Set<Map.Entry<String,JsonElement>> objectSet;
	private Map<String, ManifestElement> elementByKeyMap;
	private Set<String> keySet;

	/** create from root element.
	 * 
	 * @param jsonElement
	 */
	public ManifestJson(JsonElement jsonElement) {
		this();
		if (jsonElement == null) {
			throw new RuntimeException("Null element for manifest");
		}
		this.rootElement = jsonElement;
	    rootObject = rootElement.getAsJsonObject();
		if (rootObject == null) {
			throw new RuntimeException("Null element for manifest");
		}

	    objectSet = rootObject.entrySet();
	}

	public ManifestJson() {
	}

	public static ManifestJson readManifest(File file) {
		ManifestJson manifestJson = null;
		if (file != null && file.exists()) {
			try {
				String resultsJsonString = FileUtils.readFileToString(new File(Fixtures.TEST_JSON_DIR, "results0.json"));
			    JsonParser parser = new JsonParser();
			    JsonElement jsonElement = parser.parse(resultsJsonString);
			    manifestJson = new ManifestJson(jsonElement);
			} catch (IOException ioe) {
				throw new RuntimeException("Cannot read manifest file: "+file, ioe);
			}
		}
		return manifestJson;
	}

	public int size() {
		return (objectSet == null ? 0 : objectSet.size());
	}
	
	public Set<String> getKeys() {
		if (keySet == null) {
			keySet = new HashSet<String>();
			elementByKeyMap = new HashMap<String, ManifestElement>();
			for (Map.Entry<String,JsonElement> jsonElement : objectSet) {
				ManifestElement manifestElement = new ManifestElement(jsonElement);
				String key = manifestElement.getKey();
				keySet.add(key);
				elementByKeyMap.put(key, manifestElement);
			}
		}
		return keySet;
	}

	public ManifestElement getManifestElement(String key) {
		return elementByKeyMap.get(key);
	}

	public ManifestElement getSingleElement() {
		ManifestElement singleElement = objectSet.size() == 1 ? new ManifestElement(objectSet.iterator().next()) : null;
		return singleElement;
	}

}
