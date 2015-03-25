package test;

import static org.junit.Assert.*;
import iinterace.HTMLread;
import implementation.HTMLreadImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Junit Tests for the skipSpace() Method in HTML Read
 * @author snewnham
 *
 */
public class TestSkipSpace {

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
	 * SkipSpace Test with lower-case Alphabetical char as a parameter
	 */
	@Test
	public void testSkipSpace1(){
		char ch1 = 'c';  // lower-case parameter char BEFORE space
		char ch2 = 'z';  // parameter char AFTER space
		char lowChar = '0';
		char charAfterSpace = 'e'; // actual char AFTER space
		char ans = hReader.skipSpace(inpStream, ch1);
		assertEquals("Test skipSpace for a LowCase Alphabetical char before white space: ", lowChar, ans);
		ans = hReader.skipSpace(inpStream, ch2);
		assertEquals("Test skipSpace for a LowCase Alphabetical char after white space: ", charAfterSpace, ans);
	}
	
	/**
	 * SkipSpace Test with upper-case Alphabetical char as a parameter
	 */
	@Test
	public void testSkipSpace2(){
		char ch1 = 'C';  // Upper-Case parameter char BEFORE space
		char ch2 = 'Z';  // parameter char AFTER space
		char lowChar = '0';
		char charAfterSpace = 'e'; // actual char AFTER space
		char ans = hReader.skipSpace(inpStream, ch1);
		assertEquals("Test skipSpace for a LowCase Alphabetical char before white space: ", lowChar, ans);
		ans = hReader.skipSpace(inpStream, ch2);
		assertEquals("Test skipSpace for a LowCase Alphabetical char after white space: ", charAfterSpace, ans);
	}
	
	
	/**
	 * SkipSpace Test with a Symbolic char as a parameter
	 */
	@Test
	public void testSkipSpace3(){
		char ch1 = '?';   // Symbol parameter char BEFORE space
		char ch2 = 'Z';
		char lowChar = '0';
		char charAfterSpace = 'e';
		char ans = hReader.skipSpace(inpStream, ch1);
		assertEquals("Test skipSpace for a Symbolic char before white space: ", lowChar, ans);
		ans = hReader.skipSpace(inpStream, ch2);
		assertEquals("Test skipSpace for a Symbolic after white space: ", charAfterSpace, ans);
	}
	
	/**
	 * SkipSpace Test with a Numeric char as a parameter
	 */
	@Test
	public void testSkipSpace4(){
		char ch1 = '6';   // numeric parameter char BEFORE space
		char ch2 = 'Z';
		char lowChar = '0';
		char charAfterSpace = 'e';
		char ans = hReader.skipSpace(inpStream, ch1);
		assertEquals("Test skipSpace for a Symbolic char before white space: ", lowChar, ans);
		ans = hReader.skipSpace(inpStream, ch2);
		assertEquals("Test skipSpace for a Symbolic after white space: ", charAfterSpace, ans);
	}
	

	/**
	 * Close Down InputStream After Tests
	 */
	@After
	public void closeDown(){
		try {
			inpStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
