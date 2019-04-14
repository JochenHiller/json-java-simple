package org.jochenhiller.json.simple;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.jochenhiller.json.simple.Json.JSONArray;
import org.jochenhiller.json.simple.Json.JSONException;
import org.jochenhiller.json.simple.Json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

public class JsonParserTest {

	@Test
	public void testParseEmpty() {
		JSONObject o = Json.Parser.parse("{}");
		Assert.assertEquals(0, o.length());
	}

	@Test
	public void testParseMap() {
		JSONObject o = Json.Parser.parse("{\"a\":\"A\", \"b\": \"B\"}");
		Assert.assertEquals(2, o.length());
		Assert.assertEquals("A", o.get("a"));
		Assert.assertEquals("B", o.get("b"));
		Assert.assertTrue(o.has("a"));
		try {
			Assert.assertFalse(o.has("c"));
			Assert.assertNull(o.get("c"));
			Assert.fail("Oops, JSONException expected");
		} catch (Json.JSONException e) {
			// expected
		}
	}

	@Test
	public void testParseArray() {
		JSONObject o = Json.Parser.parse("{\"array\":[\"a\", \"b\"]}");
		Assert.assertEquals(1, o.length());
		JSONArray a = o.getJSONArray("array");
		Assert.assertEquals(2, a.length());
		Assert.assertEquals("a", a.get(0));
		Assert.assertEquals("b", a.get(1));
		try {
			Assert.assertNull(a.get(2));
			Assert.fail("Oops, JSONException expected");
		} catch (Json.JSONException e) {
			// expected
		}
	}

	/**
	 * This test parses a "real world" sample JSON file.
	 */
	@Test
	public void testParseSample01() throws IOException {
		String s = Utils.readFile("./src/test/resources/sample01.json", StandardCharsets.UTF_8);
		JSONObject o = Json.Parser.parse(s);
		Assert.assertEquals(6, o.length());
		Assert.assertEquals("PROP1", o.query("/prop1"));
		Assert.assertEquals("PROP2", o.query("/prop2"));
		Assert.assertEquals("PROP3", o.query("/prop3"));
		// 4 does not exist
		try {
			o.query("/prop4");
		} catch (JSONException ex) {
			// ignore
		}

		Assert.assertEquals("PROP11", o.query("/prop10/prop11"));
		Assert.assertEquals("PROP12", o.query("/prop10/prop12"));
		Assert.assertEquals("PROP21", o.query("/prop20/prop21"));
		Assert.assertEquals("PROP31", o.query("/prop30/0/prop31"));
		Assert.assertEquals("PROP32", o.query("/prop30/1/prop32"));
		// 2 does not exist
		try {
			o.query("/prop30/2/prop33");
		} catch (JSONException ex) {
			// ignore
		}
		// optQuery does not fail
		Assert.assertNull(o.optQuery("/prop30/2/prop33"));

		// iterate of prop30's
		JSONArray a = (JSONArray) o.query("/prop30");
		Assert.assertEquals(2, a.length());
		Assert.assertEquals("PROP31", a.getJSONObject(0).get("prop31"));
		Assert.assertEquals("PROP32", a.getJSONObject(1).get("prop32"));
	}

	/**
	 * This test parses a "real world" sample JSON file.
	 */
	@Test
	public void testParseQuery() throws IOException {
		String s = Utils.readFile("./src/test/resources/sample01.json", StandardCharsets.UTF_8);
		JSONObject o = Json.Parser.parse(s);
		Assert.assertEquals(6, o.length());
		Assert.assertEquals("PROP1", o.query("/prop1"));
		Assert.assertEquals("PROP1", o.optQuery("/prop1"));
		// 4 does not exist
		try {
			o.query("/prop4");
		} catch (JSONException ex) {
			// ignore
		}
		// optQuery does not fail
		Assert.assertNull(o.optQuery("/prop4"));

		Assert.assertEquals("PROP11", o.query("/prop10/prop11"));
		Assert.assertEquals("PROP11", o.optQuery("/prop10/prop11"));
		Assert.assertEquals("PROP31", o.query("/prop30/0/prop31"));
		Assert.assertEquals("PROP31", o.optQuery("/prop30/0/prop31"));
		// 2 does not exist
		try {
			o.query("/prop30/2/prop33");
		} catch (JSONException ex) {
			// ignore
		}
		// optQuery does not fail
		Assert.assertNull(o.optQuery("/prop30/2/prop33"));

		// iterate of prop30's
		JSONArray a = (JSONArray) o.query("/prop30");
		Assert.assertEquals(2, a.length());
		Assert.assertEquals("PROP31", a.getJSONObject(0).get("prop31"));
		Assert.assertEquals("PROP32", a.getJSONObject(1).get("prop32"));
	}

	@Test
	public void testParseJSONObjectQueryInvalid() throws IOException {
		JSONObject o = Json.Parser.parse("{\"a\":\"A\", \"b\": \"B\"}");

		Assert.assertEquals(2, o.length());
		Assert.assertEquals("A", o.query("/a"));
		Assert.assertEquals("B", o.query("/b"));
		try {
			o.query("a");
			Assert.fail("Uups, IllegalArgumentException  should happen");
		} catch (IllegalArgumentException ex) {
			// ignore
		}
		try {
			o.optQuery("a");
			Assert.fail("Uups, IllegalArgumentException  should happen");
		} catch (IllegalArgumentException ex) {
			// ignore
		}
	}

	@Test
	public void testParseJSONArrayQueryInvalid() throws IOException {
		JSONObject o = Json.Parser.parse("{\"array\":[\"a\", \"b\"]}");
		JSONArray a = o.getJSONArray("array");

		Assert.assertEquals(2, a.length());
		Assert.assertEquals("a", a.query("/0"));
		Assert.assertEquals("b", a.query("/1"));
		try {
			a.query("0");
			Assert.fail("Uups, IllegalArgumentException  should happen");
		} catch (IllegalArgumentException ex) {
			// ignore
		}
		try {
			a.optQuery("0");
			Assert.fail("Uups, IllegalArgumentException  should happen");
		} catch (IllegalArgumentException ex) {
			// ignore
		}
	}

	@SuppressWarnings("unused")
	@Test
	public void testParseSample02Plain() throws IOException {
		String jsonString = Utils.readFile("./src/test/resources/sample02.json", StandardCharsets.UTF_8);
		JSONObject jsonObject = Json.Parser.parse(jsonString);
		String a = jsonObject.getString("a");
		JSONArray array01 = jsonObject.getJSONArray("array01");
		int val1 = array01.getInt(0);
		int val2 = array01.getInt(1);
		JSONArray array02 = jsonObject.getJSONArray("array02");
		Double d1 = array02.getJSONObject(0).getDouble("d1");
		Double d2 = array02.getJSONObject(1).getDouble("d2");
	}

	@SuppressWarnings("unused")
	@Test
	public void testParseSample02Query() throws IOException {
		String jsonString = Utils.readFile("./src/test/resources/sample02.json", StandardCharsets.UTF_8);
		JSONObject jsonObject = Json.Parser.parse(jsonString);
		String a = (String) jsonObject.query("/a");
		JSONArray array01 = jsonObject.getJSONArray("array01");
		int val1 = (Integer) jsonObject.query("/array01/0");
		int val2 = (Integer) jsonObject.query("/array01/1");
		Double d1 = (Double) jsonObject.query("/array02/0/d1");
		Double d2 = (Double) jsonObject.query("/array02/1/d2");
	}
}
