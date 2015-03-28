package test;

import static org.junit.Assert.*;

import java.io.File;

import iinterace.WebCrawler;
import implementation.WebCrawlerImpl;

import org.junit.Before;
import org.junit.Test;
/**
 * Tests for the WebCrawler Implementation
 * @author snewnham
 *
 */
public class WebCrawlerTest {

	// Fields
    // ------
	
	WebCrawler wc;
	String webPage1;
	File containerFile;
	
	
	@Before
	public void setUp(){
		wc = new WebCrawlerImpl();
		webPage1 = "firstSearch.html";
		containerFile = new File("Container.txt");
	}
	
	/**
	 * Test for first easy search of a web link in firstSearch.html
	 */
	@Test
	public void pickUpLinkTest1() {
		String ans = "\"http://google.com\"";
		String s = wc.crawl(webPage1);
		assertEquals("Test crawl() can detect a link in HTML page: ", ans,s);
	}
	
	

}
