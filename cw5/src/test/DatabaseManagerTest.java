package test;

import static org.junit.Assert.*;

import java.io.File;

import iinterface.DatabaseManager;
import iinterface.WebNode;
import implementation.DatabaseManagerImpl;
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
		
		file = new File("dmDatabase.txt");
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
		assertEquals("test 1a stringToWebNode(): ", nodeAnswer.getPriorityNum(), strNode1.getPriorityNum());
		assertEquals("test 1b stringToWebNode(): ", nodeAnswer.getWebLink(), strNode1.getWebLink());
		
		WebNode strNode2 = dm.stringToWebNode(str2);
		assertEquals("test 2a stringToWebNode(): ", nodeAnswer.getPriorityNum(), strNode2.getPriorityNum());
		assertEquals("test 2b stringToWebNode(): ", nodeAnswer.getWebLink(), strNode2.getWebLink());
		
		ex.expect(IllegalArgumentException.class);
		dm.stringToWebNode(str3);	
		
		ex.expect(IllegalArgumentException.class);
		dm.stringToWebNode(str4);	
	}
	
	/**
	 * tests for sizeOfTempTable(), checking it can determine the correct size of the database, including
	 * recognizing an empty table (even when permanent Table is non-Empty). 
	 */
	@Test
	public void testSizeOfTempTable(){
		int sizeAns1 = 0;
		int sizeAns2 = 4;
		
		file = new File("emptyDatabase.txt");  // set up databaseManager with empty database.
		DatabaseManager dmEmpty = new DatabaseManagerImpl(file);
		
		int x = dmEmpty.sizeOfTempTable();  // size of empty database
		assertEquals("test 1 for sizeOfTempTable(): ", sizeAns1, x);
		
		x = dm.sizeOfTempTable();   // size of non-empty database
		assertEquals("test 2 for sizeOfTempTable(): ", sizeAns1, x);	
	}

}
