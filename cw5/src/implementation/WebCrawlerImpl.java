package implementation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
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
	private PriorityQueue<WebNode> queue;
	private int priorityNum;
	
	// Constructor
	// -----------
	public WebCrawlerImpl(){
		queue = new PriorityQueue<WebNode>(new priorityComparator());  // See below for priorityComparator
		priorityNum = 0;
	}
	
	
	// crawl()
	// -------
	@Override
	public PriorityQueue<WebNode> crawl(String webPage) {
		
		//File file = new File(url);  // Pick Up the HTML page.
		InputStream inpStream = null;
		HTMLread hReader;
		char LOW = (char) Integer.MIN_VALUE;
		
		
		
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
			//inpStream = new FileInputStream(file);
			hReader = new HTMLreadImpl();
			while(inpStream.available()>0){ 		// Search for <a href = " 
				if( hReader.readUntil(inpStream, '<', LOW) == true);
					if(hReader.skipSpace(inpStream,' ')=='a')
						if(hReader.readUntil(inpStream, ' ', 'h') == true);   // ++++
							if(hReader.skipSpace(inpStream,'h')==LOW)
								if(hReader.skipSpace(inpStream,'r')==LOW)
									if(hReader.skipSpace(inpStream,'e')==LOW)
										if(hReader.skipSpace(inpStream,'f')==LOW)
											if(hReader.skipSpace(inpStream,'=')==LOW)
												if(hReader.skipSpace(inpStream,'"')==LOW){
													String link = "\"";
													link += hReader.readString(inpStream, '"', LOW);
													System.out.println("Answer = " + link);
													WebNode w = new WebNodeImpl(link, priorityNum);
													queue.add(w);
												} // end if
			}	// end while
		}
		catch(IOException ex){
			ex.printStackTrace();
		}
		finally{
			try {
				priorityNum++;
				inpStream.close();
				return queue;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}  // end Finally
		return queue;
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
		
		for(int i=0; i<count-1; i++){
			root += st.nextToken();
		}
		
		root+="/"; // add / to facilitate future concaternation with root references.
		return root;
	}
	
	

} // end class

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
