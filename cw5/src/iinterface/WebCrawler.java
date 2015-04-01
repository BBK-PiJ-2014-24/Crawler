package iinterface;

import java.util.PriorityQueue;

public interface WebCrawler {

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
	
}
