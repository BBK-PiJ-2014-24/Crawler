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
	String webPage6;
	String webPage7;
	String webPage8;
	String webPageX;
	
	File containerFile;
	
	
	@Before
	public void setUp(){
		wc = new WebCrawlerImpl();
		webPage1 = "file:href.html";    
		webPage2 = "file:href2.html";
		webPage3 = "file:firstSearch.html";
		webPage4 = "http://www.tobycarvery.co.uk";
		webPage5 = "file:href2a.html";
		webPage6 = "file:href3.html";
		webPage7 = "file:hrefBase.html";
		webPage8 = "file:hrefRoot.html";
		webPageX = "http://www.dcs.bbk.ac.uk/%7Emartin/sewn/ls3/testpage.html";
		
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
    	String ans = "\"/nationalsearch/\"";
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
		System.out.println("\n" + q.toString());;
		assertEquals("crawl correctly counts number of links: ", numLinks, q.size());
		for(WebNode w : q){
			assertTrue("crawl identifies multiplies Links", strArr.contains(w.getWebLink()));
		} 
	}
		
	    /**
	     * A Test to see if crawl() can detect multiple links in a local HTML file filled with 
	     * normal text.
	     */
		@Test
		public void multipleLinkTest2(){
			
			ArrayList<String> strArr = new ArrayList<String>();
			strArr.add("\"http://google.com\"");
			strArr.add("\"http://bbc.co.uk\"");
			strArr.add("\"http://mises.org\"");
			int numLinks = strArr.size();
			
			PriorityQueue<WebNode> q = wc.crawl(webPage6);
			System.out.println("\n" + q.toString());;
			assertEquals("crawl correctly counts number of links: ", numLinks, q.size());
			for(WebNode w : q){
				assertTrue("crawl() identifies multiplies Links", strArr.contains(w.getWebLink()));
			} 
		}
		
		/**
		 * Test to see if can extract the root from a base reference
		 */
		@Test
		public void findBaseRefRoot(){
			
		}
		
		/**
		 * Test to cut back a base ref to its root
		 */
		@Test
		public void testExtractRoot(){
			
			String baseRef1 = "http://www.tobycarvery.co.uk/index.html";
			String baseRef2 = "http://www.tobycarvery.co.uk/";
			String baseRef3 = "http://www.tobycarvery.co.uk";
			String baseRef4 = "http://www.tobycarvery.co.uk/directory/index.html";
			String rootA = "http://www.tobycarvery.co.uk/";
			String rootB = "http://www.tobycarvery.co.uk/directory/";
			
			
			String ans1 = wc.extractRoot(baseRef1);
			String ans2 = wc.extractRoot(baseRef2);
			String ans3 = wc.extractRoot(baseRef3);
			String ans4 = wc.extractRoot(baseRef4);
			assertEquals("Test extractRoot() on a standard base reference: ", rootA, ans1);
			assertEquals("Test extractRoot() on a Root Refence: ", rootA, ans2);
			assertEquals("Test extractRoot() on a Root Reference with no / on end : ", rootA, ans3);
			assertEquals("Test extractRoot() on a Root Reference with extra Directory : ", rootB, ans4);	
			
		}
		
		
		
		/**
		 * Test to detect a Base Link in a Local HTML page and combine it with its base element.
		 */
		@Test
		public void pickUpBaseLink(){
			String ans = "\"http://www.tobycarvery.co.uk/nationalsearch/\"";
			PriorityQueue<WebNode> q = wc.crawl(webPage7);
			String s = q.poll().getWebLink();
			assertEquals("crawl() identifies base links",ans, s);	
		}
	

}
