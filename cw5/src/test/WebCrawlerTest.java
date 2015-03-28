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
	String webPage2;
	File containerFile;
	
	
	@Before
	public void setUp(){
		wc = new WebCrawlerImpl();
		webPage1 = "firstSearch.html";
		webPage2 = "firstSearch2.html";
		containerFile = new File("Container.txt");
	}
	
	/**
	 * Test for first easy search of a web link in firstSearch.html
	 */
	@Test
	public void pickUpLinkTest1() {
		String ans = "\"http://google.com\"";
		String s = wc.crawl(webPage1);
		assertEquals("Test crawl() can detect a normal looking link in HTML page: ", ans,s);
	}
	
	@Test
	public void pickUpLinkTest2() {
		String ans = "\"http://google.com\"";
		String s = wc.crawl(webPage2);
		System.out.println("test2 " + s);
		assertEquals("Test crawl() can detect a link (filled with white-space gaps) in HTML page: ", ans,s);
	}
	
	

}
