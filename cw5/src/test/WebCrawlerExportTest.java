package test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.PriorityQueue;

import iinterface.WebCrawler;
import iinterface.WebNode;
import implementation.WebCrawlerImpl;

import org.junit.Before;
import org.junit.Test;
import org.apache.commons.io.FileUtils;;

/**
 * A New Class of tests specifically to deal with correctly returning the database tables 
 * from the WebCrawler class.
 * 
 * N.b. This class uses "Apache commons API" to compare files in JUnit tests.
 * @author snewnham
 *
 */

public class WebCrawlerExportTest {

	String webPage1;
	String webPage2;
	String webPage3;
	WebCrawler wc;
	File containerFile;
	
	
	@Before
	public void setUp(){
		webPage1 = "file:href3.html";
		webPage1 = "file:href4.html";
		webPage1 = "file:href5.html";
		containerFile = new File("Container.txt");
		wc = new WebCrawlerImpl();
	}

	/**
	 * Test to check that getDatabase() correctly returns a text file that contains the right 
	 * data links in its temp database table file.
	 */
	@Test
	public void compareTempDatabaseTest1() {
		
		
		System.out.println("Start Test");
		PriorityQueue<WebNode> q = wc.crawl(webPage1);
		File database = wc.getDatabase();
		File ans1 = new File("myDatabaseAnswers1.txt");
	
		try {
			assertEquals("The files differ 1", 
				    FileUtils.readFileToString(ans1, "utf-8"), 
				    FileUtils.readFileToString(database, "utf-8"));
			
			assertTrue("The files differ 2", FileUtils.contentEquals(ans1, database));
		} 
		
		catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	/**
	 * Test to check that getDatabase() correctly returns a text file that contains the right 
	 * data links after multiple crawls.
	 */
	@Test
	public void compareTempDatabaseTest2() {
		
		
		System.out.println("Start Test");
		wc.crawl(webPage1);  //1st crawl()
		wc.crawl(webPage2);  // 2nd crawl()
		PriorityQueue<WebNode> q = wc.crawl(webPage3);  //3rd crawl()
		
		File database = wc.getDatabase();
		File ans1 = new File("myDatabaseAnswers2.txt");
	
		try {
			assertEquals("The files differ 1", 
				    FileUtils.readFileToString(ans1, "utf-8"), 
				    FileUtils.readFileToString(database, "utf-8"));
			
			assertTrue("The files differ 2", FileUtils.contentEquals(ans1, database));
		} 
		
		catch (IOException e) {
			e.printStackTrace();
		}	
	}

}
