package org.jochenhiller.json.simple;

import org.json.junit.JunitTestSuite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ JsonParserTest.class, JsonWriterTest.class, JunitTestSuite.class, })
public class AllTestSuite {
}
