package test;

import static org.junit.Assert.*;
import iinterace.HTMLread;
import implementation.HTMLreadImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Before;
import org.junit.Test;

/**
 * JUnit Test Class for readUntil()
 * @author snewnham
 *
 */
public class testReadUntil {
	
	// Fields
	// ------
	File file;
	InputStream inpStream;
	HTMLread hReader;
	
	

	@Before
	public void setUp() throws FileNotFoundException{
		file = new File("Alphabet.txt");
		inpStream = null;
		hReader = new HTMLreadImpl();
	
        inpStream = new FileInputStream(file);
	
	
	}
	
	/**
	 * Tests for Correctly picking up alphabetical char in lower and upper case
	 */
	@Test
	public void testAlphabet(){
		char c1 = 'a';
		char c2 = 'z';
		boolean ans = hReader.readUntil(inpStream, c1, c2);
		assertTrue("Test That Picks Up char 1 in Lower Case: ", ans);
		ans = hReader.readUntil(inpStream, c1, c2);
		assertFalse("Test That Picks Up char 2 in LowerCase: ", ans);
		ans = hReader.readUntil(inpStream, c1, c2);
		assertTrue("Test That Picks Up char 1 in Upper Case: ", ans);
		ans = hReader.readUntil(inpStream, c1, c2);
		assertFalse("Test That Picks Up char 2 in Upper Case: ", ans);
	}
	
	/**
	 * Tests for correctly picking up chars that are non-alphanumeric symbols
	 */
	@Test
	public void testSymbol(){
		char c1 = '<';
		char c2 = '>';
		boolean ans = hReader.readUntil(inpStream, c1, c2);
		assertTrue("Test That Picks Up char Symobol 1: ", ans);
		ans = hReader.readUntil(inpStream, c1, c2);
		assertFalse("Test That Picks Up char Symobol 2: ", ans);
		ans = hReader.readUntil(inpStream, c1, c2);
	}
	
	/**
	 * Tests for correctly picking up chars that are numeric
	 */
	@Test
	public void testNumeric(){
		char c1 = '1';
		char c2 = '9';
		boolean ans = hReader.readUntil(inpStream, c1, c2);
		assertTrue("Test That Picks Up Numeric char  1: ", ans);
		ans = hReader.readUntil(inpStream, c1, c2);
		assertFalse("Test That Picks Up Numeric char 2: ", ans);
		ans = hReader.readUntil(inpStream, c1, c2);
	}
	
}
