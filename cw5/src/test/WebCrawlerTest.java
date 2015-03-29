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
	String webPage3;
	
	File containerFile;
	
	
	@Before
	public void setUp(){
		wc = new WebCrawlerImpl();
		webPage1 = "file:href.html";    //"firstSearch2.html";
		webPage2 = "file:href2.html";
		webPage3 = "file:firstSearch.html";
		containerFile = new File("Container.txt");
	}
	
	/**
	 * Test whether crawl() can detect a legal <a href = "xxxx.xxx">
	 */
	@Test
	public void pickUpLinkTest1() {
		String ans = "\"http://google.com\"";
		String s = wc.crawl(webPage1);
		assertEquals("Test crawl() can detect a link (filled with white-space gaps) in HTML page: ", ans,s);
	}
	
	/**
	 * Test whether crawl() can differentiate between Legal and Illegal Link formats of the form <a href = "xxxx.xxx">
	 */
	@Test
	public void pickUpLinkTest2() {
		String ans = "\"http://bbc.co.uk\"";
		String s = wc.crawl(webPage2);
		assertEquals("Test crawl() can detect detect Legal and Illegal Link formats of the form <a href = xxxx. com>: "
				, ans,s);		
	}
	
	/**
	 * Test for first easy search of a web link in a simple HTML mock up page (firstSearch.html)
	 */
    @Test
	public void pickUpLinkTest3() {
		String ans = "\"http://google.com\"";
		String s = wc.crawl(webPage3);
		assertEquals("Test crawl() can detect a normal looking link in HTML page: ", ans,s);
	} 
	

	

	
	

}
