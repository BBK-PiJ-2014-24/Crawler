package test;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
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
		int ansCount = 3;
		String ans1 = "\"http://bbc.co.uk\"";
		String ans2 = "\"https://www.thenewboston.com\"";
		String ans3 = "\"http://en.wikipedia.org/wiki/Main_Page\"";
		PriorityQueue<WebNode> q = wc.crawl(webPage2);
		int numLegal = q.size();
		assertEquals("Test crawl() can Count all Legal forms <a href = xxxx. com>: ", ansCount, numLegal);
		
		String s = q.poll().getWebLink();
		assertEquals("Test crawl() can detect First Legal form <a href = xxxx. com>: ", ans1,s);
		
		s = q.poll().getWebLink();
		assertEquals("Test crawl() can detect Second Legal form <a href = xxxx. com>: ", ans3,s);
		
		s = q.poll().getWebLink();
		assertEquals("Test crawl() can detect Third Legal form <a href = xxxx. com>: ", ans2,s);
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
     * Test that crawl() can connect to an URL(http://tobycarvery.co.uk) on the WEB and grab the first 
     * link().
     */
    @Test
    public void pickUpLinkTest4(){
    	String ans = "\"http://www.tobycarvery.co.uk/nationalsearch/\"";
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
		//System.out.println("\n" + q.toString());;
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
			//System.out.println("\n" + q.toString());;
			assertEquals("crawl correctly counts number of links: ", numLinks, q.size());
			for(WebNode w : q){
				assertTrue("crawl() identifies multiplies Links", strArr.contains(w.getWebLink()));
			} 
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
		 * Test whether linkAnalyzer() can identify whether a link is absolute, root-relative or relative 
		 * and returns the correctly formed absolute link
		 */
		@Test
		public void testLinkAnalyzer(){
			
			String absol = "http://www.tobycarvery.co.uk/index.html";  // absolute link
			String absolReturned = "\"http://www.tobycarvery.co.uk/index.html\"";
			String root = "http://www.tobycarvery.co.uk/";   // root link
			String currentLink = "http://www.tobycarvery.co.uk/directory/index.html";
			String rootRelatLink = "/nationalsearch/";
			String relatLink = "../nationalsearch/";
			String concatLink =   "\"http://www.tobycarvery.co.uk/nationalsearch/\"";
			
			String ans1 = wc.linkAnalyzer(absol, root, currentLink);
			assertEquals("linkAnalyzer indentifies absolute link", absolReturned, ans1);
			String ans2 = wc.linkAnalyzer(rootRelatLink, root, currentLink);
			assertEquals("linkAnalyzer indentifies root-relative link", concatLink, ans2);
			String ans3 = wc.linkAnalyzer(relatLink, root, currentLink);
			assertEquals("linkAnalyzer indentifies relative link", concatLink, ans2);	
		}
		
		
		/**
		 * Test to detect a Root-Relative Link in a Local HTML page and combine it with its base element.
		 */
		@Test
		public void pickUpBaseLink(){
			String ans1 = "\"http://www.tobycarvery.co.uk/index.html\"";
			String ans2 = "\"http://www.tobycarvery.co.uk/nationalsearch/\"";
			PriorityQueue<WebNode> q = wc.crawl(webPage7);
			String s = q.poll().getWebLink();  // 
			assertEquals("crawl() identifies that the First in the Queue is the Base Link", ans1, s);
			s = q.poll().getWebLink(); 
			assertEquals("crawl() identifies that the Second in the Queue is the root and the root-refernence link",ans2, s);	
		}
		
		
		/**
		 * Test whether crawl() can detect any legal html references in Mark Levene's Test Page
		 * http://www.dcs.bbk.ac.uk/%7Emartin/sewn/ls3/testpage.html
		 */
		@Test
		public void testBirkbeck(){
			System.out.println("\nBIRKBECK");
			List<String> ansArr = new ArrayList<String>();
			ansArr.add("\"http://www.dcs.bbk.ac.uk/~mark/\"");
			ansArr.add("\"http://www.searchengineworld.com/robots/robots_tutorial.htm\"");
			ansArr.add("\"http://www.cse.ucsc.edu/~ejw/tatum/images/tatum-crawling.jpg\"");
			ansArr.add("\"http://www.dcs.bbk.ac.uk/~martin/sewn/ls3/images/GoodGoing-YouGotTheLink.jpg\"");
			ansArr.add("Incorrect Link Concaternation");  // REJECT ftp page link
			PriorityQueue<WebNode> q = wc.crawl(webPageX);
			for(WebNode node : q){
				String s = node.getWebLink();
				assertTrue("Test crawl() on Mark Levine's Test Page: ", ansArr.contains(s)); 
			}
			String s = q.poll().getWebLink(); 	
		}
		
		/**
		 * Test to check search method works correctly.
		 */
		@Test
		public void testSearch(){
			String even = "even";
			String odd = "odd";
			
			assertTrue("Test search() can detect even length strings", wc.search(even));
			assertFalse("Test search() can detect odd length strings", wc.search(odd));
		}
		
	
}
