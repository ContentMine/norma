package org.xmlcml.norma.json;

import junit.framework.Assert;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.xmlcml.norma.util.JsonUtil;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class JsonUtilTest {
	
	private static final Logger LOG = Logger.getLogger(JsonUtilTest.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}
	
	@Test
	public void testCreateInteger() {
		JsonObject  jsonObject = JsonUtil.createInteger("testInteger", 3);
		JsonElement testInteger = jsonObject.get("testInteger");
		Integer testInt = (Integer) testInteger.getAsInt();
		Assert.assertEquals(3,  (int) testInt); 
	}
}
