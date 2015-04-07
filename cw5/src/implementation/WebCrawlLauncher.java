package implementation;

import iinterface.WebCrawler;

public class WebCrawlLauncher {

	public static void main(String[] args) {
		
		WebCrawler wc = new WebCrawlerImpl();
		wc.launch();
	
	}

}
