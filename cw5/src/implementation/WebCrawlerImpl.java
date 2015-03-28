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
	public String crawl(String url) {
		
		File file = new File(url);  // Pick Up the HTML page.
		InputStream inpStream = null;
		HTMLread hReader;
		char LOW = '0';
		String ans ="\"";
		
		try{
			inpStream = new FileInputStream(file);
			hReader = new HTMLreadImpl();
			hReader.readUntil(inpStream, '<', '>');
			if(hReader.skipSpace(inpStream,'a')==LOW)
				if(hReader.skipSpace(inpStream,'h')==LOW)
					if(hReader.skipSpace(inpStream,'r')==LOW)
						if(hReader.skipSpace(inpStream,'e')==LOW)
							if(hReader.skipSpace(inpStream,'f')==LOW)
								if(hReader.skipSpace(inpStream,'=')==LOW)
									if(hReader.skipSpace(inpStream,'"')==LOW){
										ans = hReader.readString(inpStream, '"', '<');
										System.out.println("Answer = " + ans);
									}
										
			
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
		
		return "";
	}

}
