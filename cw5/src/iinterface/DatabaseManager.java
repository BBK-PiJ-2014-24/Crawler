package iinterface;
/**
 * An Interface that connects the WebCrawler to a Database File
 * @author snewnham
 *
 */
public interface DatabaseManager {

	
	/**
	 * Converts a string to a webNode. String must include a priority number and webPage (in that order)
	 * and must be separated by \t or white spaces. 
	 * @param A String containing a priority number and webPage (in that order) and must be separated
	 *  by \t or white spaces.
	 * @return returns a correctly formed webNode with a Priority Number and WebPage
	 * @exception An Illegal Argument Exception is thrown if too many or too few arguments are found 
	 * in the String. 
	 */
	WebNode stringToWebNode(String s) throws IllegalArgumentException;
	
	
	/**
	 * Determines the number of links in the "Temporary URL Table" found in the Database. 
	 * @return
	 */
	int sizeOfTempTable();
}
