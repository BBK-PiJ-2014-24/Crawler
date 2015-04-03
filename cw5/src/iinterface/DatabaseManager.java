package iinterface;
/**
 * An Interface that connects the WebCrawler to a Database File
 * @author snewnham
 *
 */
public interface DatabaseManager {

	
	/**
	 * Converts a string to a webNode. String must include a priority number and webPage and must be
	 * separated by \t.
	 */
	WebNode stringToWebNode(String s);
}
