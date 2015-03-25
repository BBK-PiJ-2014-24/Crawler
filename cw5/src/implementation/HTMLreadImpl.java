package implementation;

import java.io.IOException;
import java.io.InputStream;

import iinterace.HTMLread;

public class HTMLreadImpl implements HTMLread{

	@Override
	public boolean readUntil(InputStream inpStream, char c1, char c2) {
		
		if(Character.isAlphabetic(c1))
			 c1 = Character.toLowerCase(c1);
		if(Character.isAlphabetic(c2))
			c2 = Character.toLowerCase(c2);
		
		int i = 0;
		try {
			while((i = inpStream.read()) != -1){
				if((char) i == c1)
					return true;
				else if((char) i == c2)
					return false;
			}
		} catch (IOException e) {
			System.out.println("ERROR WITH INPUTSTREAM.READ()");
			e.printStackTrace();
		}
		
		return false;
	}
	

	
}
