package implementation;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.StringTokenizer;

import iinterface.DatabaseManager;
import iinterface.HTMLread;
import iinterface.WebCrawler;
import iinterface.WebNode;


public class WebCrawlerImpl implements WebCrawler{

	// Fields
	// ------
	private PriorityQueue<WebNode> tempQueue; // Queue of Links from last crawl
	private int priorityNum; // Link's priority Number
	private File database;   // database file
	private DatabaseManager databaseManager; // database Manager
	private int breath;  	// Max Number of Links to be stored in Table of Temp URL links 
	private int depth;		// Max Priority Number a Link Can hold  
	private static final int BREATH_DEFAULT = 100;  // Default Max Breath
	private static final int DEPTH_DEFAULT = 6;		// Default Max Depth
	private static final char LOW = (char) Integer.MIN_VALUE; // constant used in crawl()
	
	
	// Constructor
	// -----------
	public WebCrawlerImpl(){
		tempQueue = new PriorityQueue<WebNode>(new priorityComparator());  
		priorityNum = 1;
		breath = BREATH_DEFAULT; // defaults
		depth = DEPTH_DEFAULT;  // defaults
		database = new File("database.txt"); // defaults
		databaseManager = new DatabaseManagerImpl(database, breath);
	}
	

	// getter/setters
	// --------------
	
	public void setDepth(int d){
		this.depth = d;
	}
	
	public int getDepth(){
		return depth;
	}
	
	public void setBreath(int b){
		this.breath = b;
		databaseManager.setBreath(b);
	}
	
	public int getBreath(){
		return breath;
	}
	
	@Override
	public File getDatabase() {
		return database;
	}
	
	// crawl()
	// -------
	@Override
	public PriorityQueue<WebNode> crawl(String webPage) {
		
		StringTokenizer st = new StringTokenizer(webPage,"\""); // Ensure that the String webPage 
		String tok1 = st.nextToken();							// is Stripped of any ""
		webPage = tok1;
		PriorityQueue<WebNode> crawlQueue= new PriorityQueue<WebNode>(new priorityComparator());
		//WebNode startNode = new WebNodeImpl("\"" + tok1 + "\"", priorityNum); // Re-apply "" in WebNode
		//tempQueue.add(startNode);  // add the starting URL to queue
		//priorityNum++;
		InputStream inpStream = null;
		HTMLread hReader;
		//char LOW = (char) Integer.MIN_VALUE;
		String root = "";// The root of the link (subject to change below) 
		boolean tabTestA = true;  // flag for href link. True if link with <a> anchor tab, 
								  // false if link with <base> tab 
		
		
		// Set Up URL Connection and InputSteam
		// ------------------------------------
		try {
			URL url2 = new URL(webPage);
			URLConnection conn = url2.openConnection();
			inpStream = conn.getInputStream();
		} catch (IOException ex1) {
			System.out.println("URL CONNECTION FAILURE");
			ex1.printStackTrace();
		}
		
		
		// Search for Links
		// ----------------
		try{
			hReader = new HTMLreadImpl();
			while(inpStream.available()>0){ 										// Start search for links 
				if(hReader.readUntil(inpStream, '<', LOW) == true){ 				//<
					char secondDigit = hReader.skipSpace(inpStream,' ');			// ensure next bit is non white space 
						if(secondDigit == 'a' || secondDigit == 'b'){				// <a or <b
							if(secondDigit == 'b'){								    //<b
								if(hReader.skipSpace(inpStream,'a')==LOW)		    //<ba
									if(hReader.skipSpace(inpStream,'s')==LOW)	    //<bas
										if(hReader.skipSpace(inpStream,'e')==LOW)	//<base 
											tabTestA = false; 							// conditions met for finding a 
							}	// end <base> search								    // base reference
					
						if(hReader.readUntil(inpStream, ' ', 'h') == true);     // ensure space <a_
							if(hReader.skipSpace(inpStream,'h')==LOW)           // <a h
								if(hReader.skipSpace(inpStream,'r')==LOW)       // <a hr
									if(hReader.skipSpace(inpStream,'e')==LOW)   // <a hre 
										if(hReader.skipSpace(inpStream,'f')==LOW) // <a href
											if(hReader.skipSpace(inpStream,'=')==LOW) // <a href =  
												if(hReader.skipSpace(inpStream,'"')==LOW){ // a href = "
													String link = "";
													link += hReader.readString(inpStream, '"', LOW);
													link = link.substring(0,link.length()-1);
													if(tabTestA == false){          // if <base href = 
														root = extractRoot(link);	// set root of the link
														link = "\"" + link + "\"";
														tabTestA = true;      			
													}
													else{								// if <a = href =
														link = linkAnalyzer(link, root, webPage);  // convert to absolute link
													}									// from any relative or root-relative
													
													System.out.println("Crawl Answer = " + link);
													WebNode w = new WebNodeImpl(link, this.priorityNum);  // create WebNode out of Link
													crawlQueue.add(w);   // Add WebNode to Queue of Links
												} // end if
						}
					}
			}	// end while
		}
		catch(IOException ex){
			ex.printStackTrace();
		}
		finally{
			try {
				//priorityNum++;
				inpStream.close();
				//databaseManager.writeToTempTable(tempQueue);
				return crawlQueue;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}  // end Finally
		return crawlQueue;
	} // end crawl()
	
	
	/**
	 * Extracts the root of a link from a declared base element.
	 * @param link - The base element link
	 * @return the root of the base element
	 */
	public String extractRoot(String link){
		
		String root = "";
		StringTokenizer st = new StringTokenizer(link, "/");
		int count = st.countTokens();
		
		root = st.nextToken() + "//" + st.nextToken(); // add back "//" in "http://"
		
		if(count > 3){  // 3 As 2 Tokens are Used and Don't Want to Concaternate Last Token
			for(int i=0; i<count-3; i++){
				root += "/" + st.nextToken();  // add / to facilitate future concaternation with root references.
			}
		}
		root+="/"; // add / to facilitate future concaternation with root references.
		return root;
	}


	@Override
	public String linkAnalyzer(String link, String root, String currentWebPage) {

		char c = link.charAt(0);
		if(c == '"')											// ignore quote mark
			c = link.charAt(1);
		
		if(c == 'h' || c == 'f'){  //check for absolute link (h for http: or f for file:)
			return "\"" + link + "\"";
		}
		else if(c == '/'){ // check for root+relative link
			if(root ==""){  // if a root has not yet been detected...
				root = extractRoot(currentWebPage);  //extract one from currentWebPage
			}
			link = link.substring(1);  	// get rid of starting quote mark " and /
			return "\"" + root + link + "\"";
		}
		else if(c == '.'){ // check for relative link
			link = link.substring(3);  						// get rid of start " and ../
			currentWebPage = extractRoot(currentWebPage);   // Go Back UP a Directory in the HTML path
			return "\"" + currentWebPage + link + "\"";
		}
		
		return "Incorrect Link Concaternation";
	}

	// launchInterface()
	// -----------------
	@Override
	public void launchInterface() {
		
		Scanner input = new Scanner(System.in);
		boolean breathFlag = false; // Flag to indicate that the max breath has been reached.
									// i.e. the table of temp URL links in the database is FULL
		
		// Input
		// -----
		System.out.println("Welcome to the Web Crawler\n");
		
		System.out.print("What Web Page Do You Want to Start the Web Crawler With?");
		String webPage = input.nextLine();
		WebNode wn = new WebNodeImpl(webPage, this.priorityNum); //PriorityNumber will be set to zero on retrieve from database
		this.tempQueue.add(wn);
		databaseManager.writeToTempTable(tempQueue);  //add starting webNode immediately to database table
		
		System.out.print("Do You Wish to Determine the Breath or the Depth of the Search? (Press B for Breath,  D for Depth)");
		String BorD = input.nextLine();
		
		if(BorD.equals("B") || BorD.equals("b")){
			System.out.print("What is the Maximum Breath of your Search? ");
			setBreath(input.nextInt()); // set breath in database as well.
		}
		else if(BorD.equals("D") || BorD.equals("d")){
			System.out.print("What is the Maximum Depth of your search? ");
			this.depth = input.nextInt();
		}
		else{
			System.out.print("invalid input");
		}
		
		// Start Loop
		// ----------
		while(true){
			if(this.depth <= this.priorityNum){   // depth check
				System.out.println("Maximum Crawl Depth Has Been Reached");
				break;
			}
			if(breathFlag == true){				// breath check
				System.out.println("Maximum Crawl Breath Has Been Reached");
				break;
			}
			
			System.out.print("Do You Wish to Search " + wn.getWebLink() + "? (Y/N)"); // Search Options
			String wantSearch = input.nextLine();
				if(wantSearch.equals("Y")){
					// search()
					databaseManager.writeToPemanentTable(wn);  // write WebNode to Permanent Table
				}
			
			this.tempQueue.clear(); // clear the old queue of WebNodes in preparation for new crawl
			this.tempQueue = crawl(wn.getWebLink());						   // CRAWL()!
			breathFlag = databaseManager.writeToTempTable(tempQueue);  // write queue of links to database
																	   // return true if Table is Full
			System.out.println("TABLE OF TEMPORARY URL LINKS");
			databaseManager.printTempTable();
			System.out.println("\nTABLE OF PERMANENT URL LINKS");
			databaseManager.printPermanentTable(); 
			
			wn = databaseManager.retrieveNextWebNode(); // retrieve the next link from the database.
			if(wn == null){  							// if No WebNodes Returned
				System.out.println("No More Links Left to Crawl");
				break;
			}
			this.priorityNum++; 
		} // end while
		input.close();
	} // end launchInterface()

} // end class

// ----------------------------------------------------------------------------------------

/**
 * Comparator class to order the priority queue of WebNodes by their priority Number.
 * @author snewnham
 *
 */
class priorityComparator implements Comparator<WebNode>{

	/**
	 * Ranks WebNodes by their priorityNumber
	 */
	@Override
	public int compare(WebNode w1, WebNode w2) {
		
		if(w1.getPriorityNum() > w2.getPriorityNum()) return 1;
		if(w1.getPriorityNum() < w2.getPriorityNum()) return -1;
		return 0;
	}
	
}
