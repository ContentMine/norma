package org.xmlcml.norma.json;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/** holds a Json map.
 * 
 * @author pm286
 *
 */
public class ManifestMap {

	
	private static final Logger LOG = Logger.getLogger(ManifestMap.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}
	
	private Set<Map.Entry<String, JsonElement>> keyValueMap;
	private Map.Entry<String, JsonElement> singleElement;
	private Map<String, JsonElement> elementByKeyMap;

	public ManifestMap(JsonObject jsonObject) {
		keyValueMap = jsonObject.entrySet();
		if (keyValueMap.size() == 1) {
			singleElement = keyValueMap.iterator().next();
		} else {
			elementByKeyMap = new HashMap<String, JsonElement>();
			Iterator<Map.Entry<String, JsonElement>> iterator = keyValueMap.iterator();
			while (iterator.hasNext()) {
				Map.Entry<String, JsonElement> keyValue = iterator.next();
				elementByKeyMap.put(keyValue.getKey(), keyValue.getValue());
			}
		}
	}

	public Map.Entry<String, JsonElement> getSingleElement() {
		return singleElement;
	}

//	public void setSingleElement(Map.Entry<String, JsonElement> singleElement) {
//		this.singleElement = singleElement;
//	}

	public Map<String, JsonElement> getElementByKeyMap() {
		return elementByKeyMap;
	}

//	public void setElementByKeyMap(Map<String, JsonElement> elementByKeyMap) {
//		this.elementByKeyMap = elementByKeyMap;
//	}

	
}
