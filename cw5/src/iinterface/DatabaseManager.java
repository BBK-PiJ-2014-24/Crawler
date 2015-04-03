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
	
	
	/**
	 * Prints the Links in the Temporary URL Table along with their Priority Number.
	 */
	void printTempTable();
	
	/**
	 * Interrogates the Temporary URL Table of the database to retrieve the next Web Link to be 
	 * processed by the WebCrawler. The WebNode is copied from the database, but its priority number 
	 * is remarked 0 
	 * @return the next WebNode to be processed by WebCrawler 
	 */
	WebNode retrieveNextWebNode();
	
}
