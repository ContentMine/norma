package org.xmlcml.norma.util;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class JsonUtil {

	/** extract an array of strings from a Json object.
	 * 
	 * @param jsonObject
	 * @param name
	 * @return
	 */
	public static List<String> getAsStringArray(JsonObject jsonObject, String name ) {
		JsonArray jsonArray = jsonObject.get(name).getAsJsonArray();
		List<String> stringList = new ArrayList<String>();
		for (int i = 0; i < jsonArray.size(); i++) {
			stringList.add(jsonArray.get(i).getAsString());
		}
		return stringList;
	}

}
