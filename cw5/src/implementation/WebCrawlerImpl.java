package implementation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

import iinterface.HTMLread;
import iinterface.WebCrawler;
import iinterface.WebNode;


public class WebCrawlerImpl implements WebCrawler{

	// Fields
	// ------
	private PriorityQueue<WebNode> tempQueue;
	private PriorityQueue<WebNode> permQueue;
	private int priorityNum;
	private File database;
	
	// Constructor
	// -----------
	public WebCrawlerImpl(){
		tempQueue = new PriorityQueue<WebNode>(new priorityComparator());  // See below for priorityComparator
		priorityNum = 0;
		//database = new File("database.txt");
	}
	
	
	// crawl()
	// -------
	@Override
	public PriorityQueue<WebNode> crawl(String webPage) {
		
		//File file = new File(url);  // Pick Up the HTML page.
		InputStream inpStream = null;
		HTMLread hReader;
		char LOW = (char) Integer.MIN_VALUE;
		String root = "";// The root of the link (subject to change below) 
		boolean tabTestA = true;  // href link is with <a> anchor tab rather than a <base> tab (aTest = false) 
		
		
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
							}	// end <base> search													// base reference
					
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
													WebNode w = new WebNodeImpl(link, priorityNum);
													tempQueue.add(w);
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
				priorityNum++;
				inpStream.close();
				return tempQueue;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}  // end Finally
		return tempQueue;
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
		
		if(c == 'h'){  //check for absolute link
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


	@Override
	public File getDatabase() {
		return database;
	}
	
	
	/**
	 * A private method that writes the PriorityQueue, tempQueue to a text file.
	 */
	private void writeToDatabase(){
		this.database = new File("database.txt");
		BufferedWriter bw = null;
		
		try{
			FileWriter fw = new FileWriter(this.database);
			bw = new BufferedWriter(fw);
			
			for(WebNode wn : tempQueue){
				bw.write(wn.toString() + "\n");
			} // end loop

		}// end try
		catch (IOException ex1){
				System.out.println("File Not Writable: " + this.database.toString());
				ex1.printStackTrace();
		}
		finally{
			try{
				bw.close();
			}
			catch(IOException ex2){
				System.out.println("File Can't Be Closed");
			}
		}
		
	}
	
	

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
