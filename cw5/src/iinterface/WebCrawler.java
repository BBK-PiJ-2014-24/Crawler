package iinterface;

import java.util.PriorityQueue;

import test.WebNode;

public interface WebCrawler {

	/**
	 * crawl() method will open an HTTP connection to the starting URL and review the entire web page 
	 * found there, saving all URL links found within the page to a temporary database table.
	 * @parameter url - The URL to be Searched for Links
	 * @return Returns a URL link found on the page
	 */
	PriorityQueue<WebNode> q = crawl(String url);
}
