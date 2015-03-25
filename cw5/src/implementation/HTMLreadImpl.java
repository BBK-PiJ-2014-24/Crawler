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
				char c = (char) i;
				if(Character.isAlphabetic(c))    // Convert char in stream to lowerCase
					 c = Character.toLowerCase(c);
				
				if(c == ch1)
					return true;
				else if(c == ch2)
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
		char charBefore = 'b';   // Memory Variable to store the char before the being consumed. 
		
		if(Character.isAlphabetic(ch))
			 ch = Character.toLowerCase(ch);
		
		int i = 0;
		try {
			while((i = inpStream.read()) != -1){
				char c = (char) i;
				if (Character.isAlphabetic(c))
					c = Character.toLowerCase(c);
				
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

	@Override
	public String readString(InputStream inpStream, char ch1, char ch2) {
		
		int i = 0;
		String s = "";
		try {
			while((i = inpStream.read()) != -1){
				char c = (char) i;
				
				if(c == ch1){
					s += c;
					return s;
				}
				else if(c == ch2)
					return null;
				else
					s+=c;
			}
		} catch (IOException e) {
			System.out.println("ERROR WITH INPUTSTREAM.READ()");
			e.printStackTrace();
		}
		
		return "readString ERROR";
	}
	

	
}
