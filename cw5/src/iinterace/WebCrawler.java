package iinterace;

public interface WebCrawler {

	/**
	 * crawl() method will open an HTTP connection to the starting URL and review the entire web page 
	 * found there, saving all URL links found within the page to a temporary database table.
	 */
	 void crawl();
}
