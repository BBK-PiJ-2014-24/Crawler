package test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.PriorityQueue;

import iinterface.WebCrawler;
import iinterface.WebNode;

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
	WebCrawler wc;
	File containerFile;
	
	
	@Before
	public void setUp() throws Exception {
		webPage1 = "file:href3.html";
		containerFile = new File("Container.txt");
	}

	/**
	 * Test to check that getDatabase correctly returns the right datalinks in its temp database table.
	 */
	@Test
	public void compareTempDataBasetest() {
		
		PriorityQueue<WebNode> q = wc.crawl(webPage1);
		File database = wc.getDatabase();
	
		try {
			assertEquals("The files differ!", 
				    FileUtils.readFileToString(database, "utf-8"), 
				    FileUtils.readFileToString(containerFile, "utf-8"));
			
			assertTrue("The files differ!", FileUtils.contentEquals(database, containerFile));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
	}

}
