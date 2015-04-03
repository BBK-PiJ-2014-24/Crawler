package test;

import static org.junit.Assert.*;

import java.io.File;

import iinterface.DatabaseManager;
import iinterface.WebNode;
import implementation.WebNodeImpl;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class DatabaseManagerTest {

	// Fields
	// ------
	DatabaseManager dm;
	File file;
	
	@Before
	public void setUp() throws Exception {
		
		file = "dmDatabase.txt";
		dm = new DatabaseManagerImpl(file);
	}
	
	@Rule
	public ExpectedException ex = ExpectedException.none();

	/**
	 * Tests for stringToWebNode(). Testing for good strings, which are separated by white spaces
	 * or tabs, and bad strings with too many or too few arguments hidden string.
	 */
	@Test
	public void testStringToWebNode() {
		
		WebNode nodeAnswer = new WebNodeImpl("http://google.com", 3);
		String str1 = "\t3\t\t\thttp://google.com";  // good 
		String str2 = "3 http://google.com";		// good
		String str3 = "3http://google.com";			// bad
		String str4 = "3 http://google.com 3";		// bad
		WebNode strNode1 = dm.stringToWebNode(str1);
		assertEquals("test stringToWebNode(): ", nodeAnswer, str1);
		WebNode strNode2 = dm.stringToWebNode(str2);
		assertEquals("test stringToWebNode(): ", nodeAnswer, str2);
		ex.expect(IllegalArgumentException.class);
		dm.stringToWebNode(str3);	
	}

}
