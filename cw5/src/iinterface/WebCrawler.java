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
}
