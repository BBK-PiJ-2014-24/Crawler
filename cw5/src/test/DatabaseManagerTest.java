package test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.Queue;

import iinterface.DatabaseManager;
import iinterface.WebCrawler;
import iinterface.WebNode;
import implementation.DatabaseManagerImpl;
import implementation.WebCrawlerImpl;
import implementation.WebNodeImpl;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class DatabaseManagerTest {

	// Fields
	// ------
	DatabaseManager dm;
	File file;
	File fileA;
	File fileB;
	File fileC;
	String webPage1 = "file:href6.html";
	
	
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
		
		file = new File("myDatabaseAnswers1.txt");  // set up databaseManager with NON-empty database.
		DatabaseManager dmNonEmpty = new DatabaseManagerImpl(file);
		
		x = dmNonEmpty.sizeOfTempTable();   // size of non-empty database
		assertEquals("test 2 for sizeOfTempTable(): ", sizeAns2, x);	
	}
	
	/**
	 * test to see if printTempTable() prints the table of Temporary URL links correctly.
	 */
	@Test
	public void testPrintTempTable(){
		file = new File("myDatabaseAnswers1.txt");  // set up databaseManager with NON-empty database.
		DatabaseManager dm = new DatabaseManagerImpl(file);
		
		dm.printTempTable();
	}
	
	
	/**
	 * a) test to see if retrieveNextWebNode() retrieves the correct link for processing from the 
	 * Temporary URL table; b) Test Also that the database has been correctly amended so that the
	 * retrieved WebNode in the database has a priority setting at 0; c)  Also Return Null on Empty
	 * database
	 * 
	 */
	@Test
	public void testRetrieveNextWebNode(){
		file = new File("myDatabaseAnswers1a.txt");  // set up databaseManager with NON-empty database.
		fileB = new File("myDatabaseAnswers1b.txt"); // solution file containing correct retrieval.
		DatabaseManager dm = new DatabaseManagerImpl(file);
		WebNode wn = dm.retrieveNextWebNode();  
		WebNode ansNode = new WebNodeImpl("\"http://bbc.co.uk\"",1); // solution for retrieved WebNode

		assertEquals("Test retrieveNextWebNode() for correct PriorityNum", 
					 ansNode.getPriorityNum(),wn.getPriorityNum());
		assertEquals("Test retrieveNextWebNode() for correct Web Link", 
				 ansNode.getWebLink(),wn.getWebLink());
		try {
			fileC = dm.getDatabaseFile(); 
			assertEquals("The database has been correctly amended post retrieval", 
				    FileUtils.readFileToString(fileB, "utf-8"), 
				    FileUtils.readFileToString(fileC, "utf-8"));
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
		fileC = new File("emptyDatabase.txt");  // set up databaseManager with empty database.
		DatabaseManager dmEmpty = new DatabaseManagerImpl(fileC);
		wn = dmEmpty.retrieveNextWebNode();
		
		assertEquals("Test retrieveNextWebNode() returns Null for emptyDatabase", 
				 null,wn);
	}
	
	
	
	/**
	 * Test writeToTempTable() check that it correctly updates the table of Temporary URL links
	 * and also returns false if the table is NOT full after the update.
	 */
	@Test
	public void testWriteToTempTable1(){
		
		int depth = 6;
		int breath = 10;
		WebCrawler wc1 = new WebCrawlerImpl();
		wc1.setDepth(depth);
		wc1.setBreath(breath);
		Queue q = wc1.crawl(webPage1);
		boolean isFull = dm.writeToTempTable(q);
		
		assertFalse("Test to verify that Temp URL is not full: ", isFull);
		try {
			fileC = dm.getDatabaseFile(); 
			assertEquals("The Temp Table has been correctly updated & Not Full", 
				    FileUtils.readFileToString(fileB, "utf-8"), 
				    FileUtils.readFileToString(fileC, "utf-8"));
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 * Test writeToTempTable() check that it correctly updates the table of Temporary URL links
	 * and also returns false if the table is now full after the update.
	 */
	@Test
	public void testWriteToTempTable2(){
		
		int depth = 6;
		int breath = 10;
		WebCrawler wc1 = new WebCrawlerImpl();
		wc1.setDepth(depth);
		wc1.setBreath(breath);
		Queue q = wc1.crawl(webPage1);
		boolean isFull = dm.writeToTempTable(q);
		
		assertTrue("Test to verify that Temp URL is NOW full: ", isFull);
		try {
			fileC = dm.getDatabaseFile(); 
			assertEquals("The Temp Table has been correctly updated & NOW Full", 
				    FileUtils.readFileToString(fileB, "utf-8"), 
				    FileUtils.readFileToString(fileC, "utf-8"));
		} 
		catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	
	/**
	 * Test writeToTempTable() check that it DOES NOT updates the table of Temporary URL links
	 * because the Table is already full.
	 */
	@Test
	public void testWriteToTempTable3(){
		
		int depth = 6;
		int breath = 10;
		WebCrawler wc1 = new WebCrawlerImpl();
		wc1.setDepth(depth);
		wc1.setBreath(breath);
		Queue q = wc1.crawl(webPage1);
		boolean isFull = dm.writeToTempTable(q);
		
		assertTrue("Test to verify that Temp URL is NOW full: ", isFull);
		try {
			fileC = dm.getDatabaseFile(); 
			assertEquals("The Temp Table has NOT been updated as it is already full", 
				    FileUtils.readFileToString(fileB, "utf-8"), 
				    FileUtils.readFileToString(fileC, "utf-8"));
		} 
		catch (IOException e) {
			e.printStackTrace();
		}	
	}

}
