package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ TestReadUntil.class, TestSkipSpace.class, TestReadString.class,
				WebCrawlerTest.class, DatabaseManagerTest.class})
public class AllTests {

}
