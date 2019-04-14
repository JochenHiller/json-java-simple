package org.json.junit;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
@RunWith(Suite.class)
@Suite.SuiteClasses({
   JSONObjectTest.class,
   JSONObjectLocaleTest.class,
   JSONArrayTest.class,
   EnumTest.class,
   JSONStringTest.class,
   JSONTokenerTest.class,
})
public class JunitTestSuite {
}
