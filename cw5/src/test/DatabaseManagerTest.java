package test;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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
	File fTemplate;
	File fCopy;
	File file;
	File fileA;
	File fileB;
	File fileC;
	FileReader fr;
	FileWriter fw;
	BufferedReader br = null;
	BufferedWriter bw = null;
	String webPage1 = "file:href10.html";
	String webPage2 = "file:href11.html";
	String webPage3 = "file:href12.html";
	int breath;
	
	
	@Before
	public void setUp() throws Exception {
		breath = 100;
		file = new File("empty.txt");
		dm = new DatabaseManagerImpl(file, breath);
		
		// Reseting the DatabaseFiles
		File fileTemplate = new File("myDatabaseAnswers1Template.txt"); // Template File.
		fCopy = new File("myDatabaseAnswers1a.txt");
		try{
			final Path src = Paths.get(fileTemplate.getCanonicalPath());
			final Path dst = Paths.get(fCopy.getCanonicalPath());
			Files.copy(src, dst,StandardCopyOption.REPLACE_EXISTING);  // copy tempFile into databaseFile
		} catch(FileAlreadyExistsException e) {
		    //destination file already exists
		} catch (IOException e) {
		    //something else went wrong
		    e.printStackTrace();
		}
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
		DatabaseManager dmEmpty = new DatabaseManagerImpl(file, breath);
		
		int x = dmEmpty.sizeOfTempTable();  // size of empty database
		assertEquals("test 1 for sizeOfTempTable(): ", sizeAns1, x);
		
		DatabaseManager dmNonEmpty = new DatabaseManagerImpl(file, breath);
		file = new File("myDatabaseAnswers1.txt");  // set up databaseManager with pre-made NON-empty database table.
		dmNonEmpty.setDatabaseFile(file);
		
		x = dmNonEmpty.sizeOfTempTable();   // size of non-empty database
		assertEquals("test 2 for sizeOfTempTable(): ", sizeAns2, x);	
	}
	
	/**
	 * test to see if printTempTable() prints the table of Temporary URL links correctly.
	 */
	@Test
	public void testPrintTempTable(){
		
		DatabaseManager dm = new DatabaseManagerImpl(file, breath);
		file = new File("myDatabaseAnswers1.txt");  // set up databaseManager with pre-made NON-empty database table.
		dm.setDatabaseFile(file);
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
		
		DatabaseManager dm = new DatabaseManagerImpl(file, breath);
		file = new File("myDatabaseAnswers1a.txt");  // set up databaseManager with pre-made NON-empty database table.
		dm.setDatabaseFile(file);
		WebNode wn = dm.retrieveNextWebNode();  
		WebNode ansNode = new WebNodeImpl("\"http://bbc.co.uk\"",1); // solution for retrieved WebNode
		
		fileB = new File("myDatabaseAnswers1b.txt"); // solution file containing correct retrieval.
		
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
		DatabaseManager dmEmpty = new DatabaseManagerImpl(fileC, breath);
		wn = dmEmpty.retrieveNextWebNode();
		
		assertEquals("Test retrieveNextWebNode() returns Null for emptyDatabase", 
				 null,wn);
				
	}
	
	
	
	/**
	 * Test writeToTempTable() when breath = 100, so that it checks that the updates to the table 
	 * of Temporary URL links are correct and also returns false because the table is NOT full after the updates.
	 */
	@Test
	public void testWriteToTempTable1(){
	
		int breath = 100;  
		File newFile = new File("clean.txt");  // new textfile
		DatabaseManager dm1 = new DatabaseManagerImpl(newFile, breath);
		fileB = new File("MyDatabaseAnswers3.txt");   // solutionFile - The model answer
		WebCrawler wc1 = new WebCrawlerImpl();  // Separate crawlers, each crawling a diff webpage
		WebCrawler wc2 = new WebCrawlerImpl();
		WebCrawler wc3 = new WebCrawlerImpl();
		
		wc1.setBreath(breath); wc2.setDepth(breath); wc3.setDepth(breath);
		Queue<WebNode> q1 = wc1.crawl(webPage1);  // href10.html
		Queue<WebNode> q2 = wc2.crawl(webPage2);  // href11.html
		Queue<WebNode> q3 = wc3.crawl(webPage3);  // href12.html
		boolean isFull1 = dm1.writeToTempTable(q1);  //load Queues into the SAME database
		boolean isFull2 = dm1.writeToTempTable(q2);
		boolean isFull3 = dm1.writeToTempTable(q3);
		
		assertFalse("Test to verify that Temp URL is not full1: ", isFull1);
		assertFalse("Test to verify that Temp URL is not full2: ", isFull2);
		assertFalse("Test to verify that Temp URL is not full3: ", isFull3);
		
		fileC = dm1.getDatabaseFile();
		
		try { 
			assertEquals("The Temp Table has been correctly updated & Not Full", 
				    FileUtils.readFileToString(fileB, "utf-8"), 
				    FileUtils.readFileToString(fileC, "utf-8"));
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 * Test writeToTempTable() when breath = 14, so that it checks that the updates to the table 
	 * of Temporary URL links are correct and also returns True on the last call because the 
	 * table is Exactly full after the last update.
	 */
	@Test
	public void testWriteToTempTable2(){
		
		int breath = 14;  
		File newFile = new File("clean.txt");  // new textfile
		DatabaseManager dm1 = new DatabaseManagerImpl(newFile, breath);
		fileB = new File("MyDatabaseAnswers3.txt");   // solutionFile - The model answer
		WebCrawler wc1 = new WebCrawlerImpl();  // Separate crawlers, each crawling a diff webpage
		WebCrawler wc2 = new WebCrawlerImpl();
		WebCrawler wc3 = new WebCrawlerImpl();
		
		wc1.setBreath(breath); wc2.setDepth(breath); wc3.setDepth(breath);
		Queue<WebNode> q1 = wc1.crawl(webPage1);  // href10.html
		Queue<WebNode> q2 = wc2.crawl(webPage2);  // href11.html
		Queue<WebNode> q3 = wc3.crawl(webPage3);  // href12.html
		boolean isFull1 = dm1.writeToTempTable(q1);  //load Queues into the SAME database
		boolean isFull2 = dm1.writeToTempTable(q2);
		boolean isFull3 = dm1.writeToTempTable(q3);
		
		assertFalse("Test to verify that Temp URL is not full1: ", isFull1);
		assertFalse("Test to verify that Temp URL is not full2: ", isFull2);
		assertTrue("Test to verify that Temp URL is NOW full3: ", isFull3);  // Full Table!
		
		fileC = dm1.getDatabaseFile();
		
		try { 
			assertEquals("The Temp Table has been correctly updated & Not Full", 
				    FileUtils.readFileToString(fileB, "utf-8"), 
				    FileUtils.readFileToString(fileC, "utf-8"));
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}
		
	
	/**
	 * Test writeToTempTable() when breath = 7, so that the updates to the Temporary URL table
	 *  stop after second update.  Also returns True on the 2nd and 3rd call because the 
	 * table is Exactly full after the first update. Plus check that the depth of the table after the
	 * three updates is maxed out at 7. 
	 */
	@Test
	public void testWriteToTempTable3(){
		
		int breath = 7;  
		File newFile = new File("clean.txt");  // new textfile
		DatabaseManager dm1 = new DatabaseManagerImpl(newFile, breath);
		fileB = new File("MyDatabaseAnswers3a.txt");   // solutionFile - The model answer
		WebCrawler wc1 = new WebCrawlerImpl();  // Separate crawlers, each crawling a diff webpage
		WebCrawler wc2 = new WebCrawlerImpl();
		WebCrawler wc3 = new WebCrawlerImpl();
		
		wc1.setBreath(breath); wc2.setDepth(breath); wc3.setDepth(breath);
		Queue<WebNode> q1 = wc1.crawl(webPage1);  // href10.html
		Queue<WebNode> q2 = wc2.crawl(webPage2);  // href11.html
		Queue<WebNode> q3 = wc3.crawl(webPage3);  // href12.html
		boolean isFull1 = dm1.writeToTempTable(q1);  //load Queues into the SAME database
		boolean isFull2 = dm1.writeToTempTable(q2);
		boolean isFull3 = dm1.writeToTempTable(q3);
		
		assertFalse("Test to verify that Temp URL is not full1: ", isFull1);
		assertTrue("Test to verify that Temp URL is NOW full2: ", isFull2);   // Full Table!
		assertTrue("Test to verify that Temp URL is NOW full3: ", isFull3);  // Full Table!
		
		assertEquals("Test Depth of Table is Maxed Out at 7: ", 7, dm1.sizeOfTempTable());
		
	}
	
	
	@Test
	/**
	 * Tests that the a WebNode correctly updates at the bottom of a table for permanent URL links. 
	 * And also checks that method returns false if a duplicate link is being added.
	 */
	public void testWriteToPermanentTable1(){
		
		int breath = 100;  
		File newFile = new File("clean.txt");  // new textfile
		DatabaseManager dm1 = new DatabaseManagerImpl(newFile, breath);
		fileB = new File("MyDatabaseAnswers4.txt");   // solutionFile - The model answer
		WebCrawler wc1 = new WebCrawlerImpl();  // Separate crawlers, each crawling a diff webpage
		WebCrawler wc2 = new WebCrawlerImpl();
		WebCrawler wc3 = new WebCrawlerImpl();
		
		// Load Up some Dummy Links in the Temp Data Table
		wc1.setBreath(breath); wc2.setDepth(breath); wc3.setDepth(breath);
		Queue<WebNode> q1 = wc1.crawl(webPage1);  // href10.html
		Queue<WebNode> q2 = wc2.crawl(webPage2);  // href11.html
		Queue<WebNode> q3 = wc3.crawl(webPage3);  // href12.html
		boolean isFull1 = dm1.writeToTempTable(q1);  //load Queues into the SAME database
		boolean isFull2 = dm1.writeToTempTable(q2);
		boolean isFull3 = dm1.writeToTempTable(q3);
		
		// Some WebNodes to add to the Table of Permanent Links
		WebNode wn1 = new WebNodeImpl("http://google.com", 1);
		WebNode wn2 = new WebNodeImpl("http://mises.org", 2);
		WebNode wn3 = new WebNodeImpl("http://google.com", 2);  // duplicate!!
		
		// Start Adding Perm Links
		boolean isPermLoad1 = dm1.writeToPemanentTable(wn1);
		boolean isPermLoad2 = dm1.writeToPemanentTable(wn2);
		boolean isPermLoad3 = dm1.writeToPemanentTable(wn3);
		
		assertTrue("Test to verify that wn1 is in Table of Perm Links: ", isPermLoad1);
		assertTrue("Test to verify that wn2 is in Table of Perm Links: ", isPermLoad2);
		assertFalse("Test to verify that wn3 is NOT in Table of Perm Links: ", isPermLoad3);
		
		fileC = dm1.getDatabaseFile();
		
		try { 
			assertEquals("The Perm Table has been correctly updated", 
				    FileUtils.readFileToString(fileB, "utf-8"), 
				    FileUtils.readFileToString(fileC, "utf-8"));
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}

}
