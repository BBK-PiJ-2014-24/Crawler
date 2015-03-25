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

	@Override
	public char skipSpace(InputStream inpStream, char ch) {
		
		char lowChar = '0';
		if(Character.isAlphabetic(ch))
			 ch = Character.toLowerCase(ch);
		
		int i = 0;
		try {
			while((i = inpStream.read()) != -1){
				char c = (char) i;
				char charBefore = 'b'; 
				if (Character.isAlphabetic(c))
					c = Character.toLowerCase(lowChar);
				
				if( c == ch)
					return lowChar;   // found char returns lowChar 
				else if( charBefore == ' ' && c != ' ')
					return c;  // found char after white space
				else 
					charBefore = c;	
			}
		} 
		catch (IOException e) {
			System.out.println("ERROR WITH INPUTSTREAM.READ()");
			e.printStackTrace();
		}
		
		return ' '; 	// returns whiteSpace if not found char and not found char after whiteSpace
		
	}
	

	
}
