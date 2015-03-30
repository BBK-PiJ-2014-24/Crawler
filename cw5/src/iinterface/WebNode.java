package iinterface;
/**
 * A Node Containing two fields, a http web link and it's priority Number in a Queue.
 * @author snewnham
 *
 */
public interface WebNode {

	/**
	 * Setter for weblink's priority number in a queue.
	 */
	void setPriorityNum(int priortyNum);
	
	/**
	 * Getter for a weblink's priority Number.
	 * @return return s the priority Number
	 */
	int getPriorityNum();
	
	/**
	 * Stores a webLink in the Node.
	 */
	void setWebLink(String webLink);
	
	/**
	 * Getter for obtaining the weblink stored in the node.
	 * @return a webLink stored in the node.
	 */
	String getWebLink();
	
	/**
	 * Arranges the Node Output in a row separated by 3 Tabs
	 * @return
	 */
	String toString();
	
	
}
