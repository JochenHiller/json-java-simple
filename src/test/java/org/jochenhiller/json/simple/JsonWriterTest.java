package org.jochenhiller.json.simple;

import org.jochenhiller.json.simple.Json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

public class JsonWriterTest {

	@Test
	public void testWrite() {
		JSONObject jsonObject = Json.Parser.parse("{\"a\":\"A\", \"b\": \"B\"}");
		String jsonString = Json.Writer.write(jsonObject);
		// no guarantee about ordering, might fail
		Assert.assertEquals("{\"a\":\"A\",\"b\":\"B\"}", jsonString);
	}

}
