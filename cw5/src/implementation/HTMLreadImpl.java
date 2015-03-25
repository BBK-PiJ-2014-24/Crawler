package implementation;

import java.io.IOException;
import java.io.InputStream;

import iinterace.HTMLread;

public class HTMLreadImpl implements HTMLread{

	@Override
	public boolean readUntil(InputStream inpStream, char ch1, char ch2) {
		
		if(Character.isAlphabetic(ch1))
			 ch1 = Character.toLowerCase(ch1);
		if(Character.isAlphabetic(ch2))
			ch2 = Character.toLowerCase(ch2);
		
		int i = 0;
		try {
			while((i = inpStream.read()) != -1){
				if((char) i == ch1)
					return true;
				else if((char) i == ch2)
					return false;
			}
		} catch (IOException e) {
			System.out.println("ERROR WITH INPUTSTREAM.READ()");
			e.printStackTrace();
		}
		
		return false;
	}
	

	
}
