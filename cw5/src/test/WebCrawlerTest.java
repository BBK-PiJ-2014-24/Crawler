package test;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.PriorityQueue;

import iinterface.WebCrawler;
import iinterface.WebNode;
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
	String webPage4;
	String webPage5;
	
	File containerFile;
	
	
	@Before
	public void setUp(){
		wc = new WebCrawlerImpl();
		webPage1 = "file:href.html";    //"firstSearch2.html";
		webPage2 = "file:href2.html";
		webPage3 = "file:firstSearch.html";
		webPage4 = "http://bbc.co.uk";
		containerFile = new File("Container.txt");
	}
	
	/**
	 * Test whether crawl() can detect a legal <a href = "xxxx.xxx"> in local file: href.html
	 */
	@Test
	public void pickUpLinkTest1() {
		String ans = "\"http://google.com\"";
		PriorityQueue<WebNode> q = wc.crawl(webPage1);
		WebNode node = q.poll();
		String s = node.getWebLink();
		assertEquals("Test crawl() can detect a link (filled with white-space gaps) in HTML page: ", ans,s);
	}
	
	/**
	 * Test whether crawl() can differentiate between Legal and Illegal Link formats of the form 
	 * <a href = "xxxx.xxx"> in local file href2.html
	 */
	@Test
	public void pickUpLinkTest2() {
		String ans = "\"http://bbc.co.uk\"";
		PriorityQueue<WebNode> q = wc.crawl(webPage2);
		WebNode node = q.poll();
		String s = node.getWebLink();
		assertEquals("Test crawl() can detect detect Legal and Illegal Link formats of the form <a href = xxxx. com>: "
				, ans,s);		
	}
	
	/**
	 * Test for first easy search of a web link in a more realistic HTML mock up page (firstSearch.html)
	 * 
	 */
    @Test
	public void pickUpLinkTest3() {
		String ans = "\"http://google.com\"";
		PriorityQueue<WebNode> q = wc.crawl(webPage3);
		WebNode node = q.poll();
		String s = node.getWebLink();
		assertEquals("Test crawl() can detect a normal looking link in HTML page: ", ans, s);
	} 
	
    /**
     * Test that crawl() can connect to an URL(http://bbc.co.uk) on the WEB and grab the first 
     * link(m.bbc.co.uk).
     */
    
    @Test
    public void pickUpLinkTest4(){
    	String ans = "\"http://m.bbc.co.uk\"";
    	PriorityQueue<WebNode> q = wc.crawl(webPage4);
		WebNode node = q.poll();
		String s = node.getWebLink();
    	System.out.println("I have found: " + s);
    	assertEquals("Test crawl() can detect a link on the Web: ", ans,s);
    }
	

    /**
     * A Test to see if crawl() can detect multiple links in a local HTML file.
     */
	@Test
	public void multipleLinkTest1(){
		
		ArrayList<String> strArr = new ArrayList<String>();
		strArr.add("\"http://google.com\"");
		strArr.add("\"http://bbc.co.uk\"");
		strArr.add("\"http://mises.org\"");
		int numLinks = strArr.size();
		
		PriorityQueue<WebNode> q = wc.crawl(webPage5);
		assertEquals("crawl correctly counts number of links: ", numLinks, q.size());
		for(WebNode w : q){
			assertEquals("crawl identifies multiplies Links", strArr.contains(w));
		}
		
	}
	

}
