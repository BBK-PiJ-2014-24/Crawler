package iinterface;

import java.io.File;
import java.util.PriorityQueue;

public interface WebCrawler {

	/**
	 * setter for the maximum priority Number of URL link
	 * @param d the maximum priority Number of URL link
	 */
	public void setDepth(int d);
	
	/**
	 * getter for the maximum priority Number of URL link
	 * @param d the maximum priority Number of URL link
	 */
	public int getDepth();
	
	/**
	 * setter for the maximum Number of URL links stored in a temporary table of a database
	 * @param b the maximum Number of URL links stored in a temporary table of a database
	 */
	public void setBreath(int b);
	
	
	/**
	 * getter for the maximum Number of URL links stored in a temporary table of a database
	 * @param b the maximum Number of URL links stored in a temporary table of a database
	 */
	public int getBreath();
	
	
	
	/**
	 * crawl() method will open an HTTP connection to the starting URL and review the entire web page 
	 * found there, saving all URL links found within the page to a temporary database table.
	 * @parameter url - The URL to be Searched for Links
	 * @return Returns a URL link found on the page
	 */
	PriorityQueue<WebNode> crawl(String url);
	
	
	/**
	 * Extracts the root of a link from a declared base element.
	 * @param link - The base element link
	 * @return the root of the base element
	 */
	String extractRoot(String link);
	
	
	/**
	 * Identifies whether a link is absolute, root-relative or relative and returns the 
	 * correctly formed absolute link
	 * @param link -  The link to be analyzed
	 * @param root - The root of the base reference
	 * @param currentWebPage - The current link the WebCrawler is sitting on. 
	 * @return if link is absolute, return absolute
	 * 		   if link is root-relative, return root+root-relative
	 * 		   if link is relative, return currentWebPage(minus 1 directory) + relative 	
	 */
	String linkAnalyzer(String link, String root, String currentWebPage);
	
	
	/**
	 * Returns a database file containing two tables. One table containing a file of temporary weblinks
	 * and the other file containing the final selected weblinks.
	 * @return a file containing the two tables of the database.
	 */
	File getDatabase();
	
	
	/**
	 * Method that launches the interface, allowing the programmer to interact with the crawler 
	 */
	void launch();
	
	/**
	 * A default method for this interface. Includes a simple method for testing the method, but should be 
	 * overwritten when a search algorhythm is required. 
	 * @param webPage - Name of the web page to be searched
	 * @return returns true if the length of the link is even, false if of odd length
	 */
	default boolean search(String webPage){
		
		int mod = (webPage.length()) % 2;
		if(mod == 0)
			return true;
		else
			return false;
	}
	
}
