package iinterface;

import java.io.InputStream;

public interface HTMLread {

	/**
	 * Write a method named readUntil() that accepts an instance of an InputStream and two char’s as 
	 * parameters. We will refer to the two chars as ch1 and ch2. The method consumes characters from 
	 * the InputStream and stops when either a character that is the same as ch1 or ch2 is encountered,
	 * ignoring case. If the character is the same as ch1 the value true is returned; 
	 * otherwise, false is returned.
	 * @param inpStream
	 * @param c1
	 * @param c2
	 * @return character is the same as ch1 the value true is returned; otherwise, false is returned.
	 */
	boolean readUntil(InputStream inpStream, char ch1, char ch2);
	
	/**
	 * Write a method named skipSpace() that accepts an instance of an InputStream and a char as 
	 * parameters. We will refer to the char as ch. This method consumes up to and including the 
	 * first non-whitespace character from the InputStream or up to and including ch. 
	 * If the method stopped reading characters from the InputStream because ch was encountered, 
	 * it returns the smallest possible value of a char [hint: Use the constant Java provides]. 
	 * Otherwise, the method returns the non-whitespace character that was read.
	 * @param inpStream
	 * @param c1
	 * @return it returns the smallest possible value of a char. Otherwise, the method returns 
	 * the non-whitespace character that was read
	 */
	char skipSpace(InputStream inpStream, char ch);
	
	
	
	/**
	 * Write a method named readString() that accepts an instance of an InputStream, and two char’s 
	 * as parameters. We will refer to the two chars as ch1 and ch2. The method consumes characters 
	 * from the InputStream and stops when either a character that is the same as ch1 or ch2 is 
	 * encountered, not ignoring case. This is very similar to readUntil() described above. 
	 * However, in this case, if the character is the same as ch1 a String is returned that contains, 
	 * in-order, the characters read. If the terminating character was the same as ch2, then the 
	 * special String value null is returned.
	 * @param inpStream
	 * @param c1
	 * @param c2
	 * @return If the terminating character was the same as ch2, then the 
	 * special String value null is returned.
	 */
	String readString(InputStream inpStream, char ch1, char ch2);
	
	
	
}
