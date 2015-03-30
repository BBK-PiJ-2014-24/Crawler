package test;

import static org.junit.Assert.*;
import iinterface.WebNode;

import org.junit.Before;
import org.junit.Test;

public class WebNodeTest {

	// Fields
	// ------
	
	WebNode w;
	int qNum;
	String webPage;
	
	@Before
	public void setUp(){
		w = WebNodeImpl();
		qNum = 3;
		webPage = "\"http://google.com\"";
		w.setPriorityNum(qNum); 
		w.setWebLink(webPage);
	}
	
	/**
	 * Testing setter/getter of node's priority number
	 */
	@Test
	public void testPriorityNumber() {
		assertEquals("Test getPriorityNum", qNum, w.getPriorityNum());
	}
	
	/**
	 * Testing setter/getter of node's webLink
	 */
	@Test
	public void testWebPageLink() {
		assertEquals("Test getWebPageLink", webPage, w.getWebLink());
	}
	
	
	/**
	 * Testing output of Node's toString().
	 */
	@Test
	public void testNodeOutput(){
		String output = "\t" + w.getPriorityNum() + "\t\t\t" + w.getWebLink();	
		assertEquals("Test toString(): ", output, w.toString());
	}
	
	

}
