package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ testReadUntil.class, TestSkipSpace.class, TestReadString.class })
public class AllTests {

}