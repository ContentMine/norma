package org.xmlcml.norma.json;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.xmlcml.norma.Fixtures;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;

public class JsonPathTest {

	
	private static final Logger LOG = Logger.getLogger(JsonPathTest.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}
	@Test
	public void testExample0() throws IOException {
		String json = FileUtils.readFileToString(new File(Fixtures.TEST_JSON_DIR, "jsonpathExample.json"));
		ReadContext ctx = JsonPath.parse(json);
		List<String> authorsOfBooksWithISBN = ctx.read("$.store.book[?(@.isbn)].author");
		for (String author : authorsOfBooksWithISBN) {
			LOG.trace("auth >"+author);
		}
		List<Map<String, Object>> expensiveBooks = (List<Map<String, Object>>) JsonPath
//		                            .using(configuration)
		                            .parse(json)
		                            .read("$.store.book[?(@.price > 10)]", List.class);
		for (Map<String, Object> book : expensiveBooks) {
			LOG.trace("book "+book);
		}
	}
}
