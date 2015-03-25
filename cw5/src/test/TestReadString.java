package test;

import static org.junit.Assert.*;
import iinterace.HTMLread;
import implementation.HTMLreadImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the ReadString() method in the HTMLread class
 * @author snewnham
 *
 */

public class TestReadString {

	// Fields
	// ------
	File file;
	InputStream inpStream;
	HTMLread hReader;
	
	
	/**
	 * Set Up Alphabet.txt
	 * Instantiate an InputStream
	 * @throws FileNotFoundException
	 */
	@Before
	public void setUp() throws FileNotFoundException{
		file = new File("Alphabet.txt");
		inpStream = null;
		hReader = new HTMLreadImpl();
        inpStream = new FileInputStream(file);
	}
	
	/**
	 * Test When ch1 and ch2 are Alphabetical
	 */
	@Test
	public void testAlphabet() {
		char ch1 = 'm';
		char ch2 = 'q';
		String correctLowerString = "<abc?26d efgh ijkl m";
		char ch3 = 'M';
		char ch4 = 'Q';
		String correctUpperString = "rst uvw/x yz\nABCD EFGH IJKL M";
				
		
		String ans = hReader.readString(inpStream, ch1, ch2);
		assertEquals("Test that Returns Alphabet String for Lower Case ch1: ", correctLowerString, ans);
		ans = hReader.readString(inpStream, ch1, ch2);
		assertEquals("Test that Returns Null String for  Lower Case ch2: ", null, ans);
		
		ans = hReader.readString(inpStream, ch3, ch4);
		assertEquals("Test that Returns Alphabet String for Upper Case ch3: ", correctUpperString, ans);
		ans = hReader.readString(inpStream, ch3, ch4);
		assertEquals("Test that Returns Null String for Upper Case ch3: ", null, ans);	
	}
	
	/**
	 * Test When ch1 and ch2 are symbols
	 */
	@Test
	public void testSymbol(){
		char ch1 = '<';
		String correctString1 = "<";
		char ch2 = '?';
		char ch3 = '/';
		String correctString2 = "abc?";
		
		String ans = hReader.readString(inpStream, ch1, ch2);
		assertEquals("Test that Returns Symbol String of First Char of Stream: ", correctString1, ans);
		ans = hReader.readString(inpStream, ch2, ch3);
		assertEquals("Test that Returns Symbol String for ch2: ", correctString2, ans);
		ans = hReader.readString(inpStream, ch2, ch3);
		assertEquals("Test that Returns Null String for ch3: ", null, ans);
	}
	
	/**
	 * Test When ch1 and ch2 are Numerical
	 */
	@Test
	public void testNumerical(){
		char ch1 = '2';
		String correctString1 = "<abc?2";
		char ch2 = '6';
	
		
		String ans = hReader.readString(inpStream, ch1, ch2);
		assertEquals("Test that Returns Numerical String of First Char of Stream: ", correctString1, ans);
		ans = hReader.readString(inpStream, ch1, ch2);
		assertEquals("Test that Returns Null String for ch2: ", null, ans);
		
	}

}
