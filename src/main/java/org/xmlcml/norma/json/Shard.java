package org.xmlcml.norma.json;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.xmlcml.norma.util.JsonUtil;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Shard {
	
	private static final Logger LOG = Logger.getLogger(Shard.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}
	
	private String name;
	private Integer value;
	
	public Shard(
		String name, 
		Integer value
		) {
		setName(name);
		setValue(value);
	}

	public static Shard createShard(JsonObject jsonObject) {
		String name =   JsonUtil.getString(jsonObject, "name");
		Integer value = JsonUtil.getInteger(jsonObject, "value");
		Shard shard = new Shard(
			name,
			value
			);
		return shard;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	/** extract an array of Hit from a Json object.
	 * 
	 * @param jsonObject
	 * @param name
	 * @return
	 */
	public static List<Shard> getAsShardArray(JsonObject jsonObject, String name ) {
		JsonElement element = jsonObject.get(name);
		List<Shard> shardList = new ArrayList<Shard>();
		if (element.isJsonArray()) {
			JsonArray jsonArray = jsonObject.get(name).getAsJsonArray();
			for (int i = 0; i < jsonArray.size(); i++) {
				shardList.add(Shard.createShard(jsonArray.get(i).getAsJsonObject()));
			}
		} else {
			shardList.add(Shard.createShard(element.getAsJsonObject()));
		}
		return shardList;
	}
	
	public static String toString(Collection<Shard> shardList) {
		StringBuilder sb = new StringBuilder();
		for (Shard shard : shardList) {
			sb.append(shard+" / ");
		}
		return sb.toString();
	}
	
	public String toString() {
		return name+" / "+value;
	}

}
