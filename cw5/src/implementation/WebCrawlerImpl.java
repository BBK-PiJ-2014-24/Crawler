package implementation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import iinterace.HTMLread;
import iinterace.WebCrawler;

public class WebCrawlerImpl implements WebCrawler{

	@Override
	public String crawl(String webPage) {
		
		//File file = new File(url);  // Pick Up the HTML page.
		InputStream inpStream = null;
		HTMLread hReader;
		char LOW = (char) Integer.MIN_VALUE;
		String ans ="\"";
		
		
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
				if(hReader.readUntil(inpStream, '<', '>') == true);
					if(hReader.skipSpace(inpStream,' ')=='a')
						if(hReader.readUntil(inpStream, ' ', 'h') == true);   // ++++
							if(hReader.skipSpace(inpStream,'h')==LOW)
								if(hReader.skipSpace(inpStream,'r')==LOW)
									if(hReader.skipSpace(inpStream,'e')==LOW)
										if(hReader.skipSpace(inpStream,'f')==LOW)
											if(hReader.skipSpace(inpStream,'=')==LOW)
												if(hReader.skipSpace(inpStream,'"')==LOW){
													ans += hReader.readString(inpStream, '"', '<');
													System.out.println("Answer = " + ans);
													return ans;
												} // end if
			}	// end while
			
		}
		catch(IOException ex){
			ex.printStackTrace();
		}
		finally{
			try {
				inpStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return "Not Found";
	}

}
