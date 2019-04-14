# json-java-simple

[![Build Status](https://travis-ci.org/JochenHiller/json-java-simple.svg?branch=master)](https://travis-ci.org/JochenHiller/json-java-simple)

Simple JSON parser and writer implementation in just one class.

## Overview

This project aims to provide a most simple Json parser and writer in one single class. It is intended to be used by copying it into your target Java package and NOT to be used as a library. This helps to avoid dependencies on some JSON implementation sharing over your Java installation. If you don't care, please use the `org.json` package from https://github.com/stleary/JSON-java instead.

This implementation has been design to have as lowest footprint as possible providing a stable and comfortable parser. The current size of JAR file containing byte code of this one single class is about 32 kB.

The implementation is based on `org.json` JSON parser. This provides well designed, good APIs and essentially consists of 4 public classes only:
* `JSONObject`
* `JSONArray`
* `JSONString`
* `JSONException`

To make it easy to copy this parser, a tiny wrapper class will be provided, which encapsulates all needed classes as inner classes. Internal classes (`JSONTokenizer`) is hidden behind an internal inner class.

The `org.json` test project (see https://github.com/stleary/JSON-Java-unit-test) has also been adapted to run the original tests against this tiny parser as well. So the test coverage is the same like the original implementation.

You can *NOT* get this package via Maven Central, as it is intended to be used by copying it as source code into your application. Simple copy Json.java in your projecz and adapt the package name, thats all. If you want to get a library for JSON, consider to use `org.json` original implementation instead.

## Limitations

These parts of `org.json` implementation have been removed due to smaller footprint:
* `CDL.java`
* `Cookie.java`
* `CookieList.java`
* `HTTP.java`
* `HTTPTokener.java`
* `JSONML.java`
* `JSONPointer.java`
* `JSONPointerException.java`
* `JSONPropertyIgnore.java`
* `JSONPropertyName.java`
* `JSONStringer.java`
* `Property.java`
* `XML.java`
* `XMLParserConfiguration.java`
* `XMLTokener.java`

So this simple implementation does *NOT* contain Cookie, HTTP, Property, XML support.

The use of JSONPointer to make query into a JSON document has been replaced by a vwery very simple `query` implementation. It only contains a subset of the IETF Spec 6901 (https://tools.ietf.org/html/rfc6901). See code below how to use for JSON object and arrays.

The tests using and testing these classes have been removed as well or by commenting out a test case or parts with

```
// REMOVED FROM TEST CASE
```
.

## How to use

You can parse a JSON structure by `Json.Parser.parse(jsonString)`. Assume following JSON structure:

```json
{
  "a" : "A",
  "b" : "B",
  "array01" : [
    0, 1
  ],
  "array02" : [
    { "d1" : 1.0 },
    { "d2" : 2.0 }
  ]
}
```

You can access the document via `JSONObject` and `JSONArray` that way:

```java
String jsonString = ...; // read somehow json
JSONObject jsonObject = Json.Parser.parse(jsonString);
String a = jsonObject.getString("a");
JSONArray array01 = jsonObject.getJSONArray("array01");
int val1 = array01.getInt(0);
int val2 = array01.getInt(1);
JSONArray array02 = jsonObject.getJSONArray("array02");
Double d1 = array02.getJSONObject(0).getDouble("d1");
Double d2 = array02.getJSONObject(1).getDouble("d2");
```

You can access via `query()` methods as well:

```java
String jsonString = ...; // read somehow json
JSONObject jsonObject = Json.Parser.parse(jsonString);
String a = (String) jsonObject.query("/a");
JSONArray array01 = jsonObject.getJSONArray("array01");
int val1 = (Integer) jsonObject.query("/array01/0");
int val2 = (Integer) jsonObject.query("/array01/1");
Double d1 = (Double) jsonObject.query("/array02/0/d1");
Double d2 = (Double) jsonObject.query("/array02/1/d2");
```

Note: `query()` will throw `JSONException`s in case there are missing elements. You can also use `optQuery()` instead which will return `null` if an element is missing. This simplifies error handling.


You can write a JSON structure to a String by `Json.Writer.write(jsonObject)`:

```java
JSONObject jsonObject = ...; // create JSON structure
String jsonString = Json.Writer.write(jsonObject );
```



For more documentation read also https://github.com/stleary/JSON-java, or https://www.baeldung.com/java-org-json.

## Compatibility to org.json

If you are already using `org.json` implementation, it is enough to change your imports from `org.json` to your package where the class `Json` will be copied to.
E.g. the whole test suite has been adapted that way by re-organizing imports from `org.json.*` to `org.jochenhiller.json.simple.Json.*`.

If you are using more advanced feature which are not supported here, you have to remove these parts, or add it from original implementation to your copy.

## References

* org.json implementation: https://github.com/stleary/JSON-java
* org.json tests: https://github.com/stleary/JSON-Java-unit-test
