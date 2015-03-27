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
	File webPage;
	File containerFile;
	
	
	@Before
	public void setUp(){
		wc = new WebCrawlerImpl();
		webPage = new File("firstSearch.html");
		containerFile = new File("Container.txt");
	}
	
	/**
	 * Test for first search of a web link on a HTML
	 */
	@Test
	public void pickUpLinktest() {
		String ans = "http//:google.com";
		String s = wc.crawl();
		assertEquals("Test crawl() can detect a link in HTML page: ", ans,s);
	}

}
