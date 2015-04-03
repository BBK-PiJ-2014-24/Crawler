package implementation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.StringTokenizer;

import iinterface.DatabaseManager;
import iinterface.WebNode;

public class DatabaseManagerImpl implements DatabaseManager {

	
	// Fields
	// ------
	
	private File databaseFile;
	
	
	// Constructor
	// ----------
	
	public DatabaseManagerImpl(File file){
		this.databaseFile = file;
	}
	
	// getter
	public File getDatabaseFile(){
		return this.databaseFile;
	}
	
	// stringToWebNode(String s)
	// --------------------------
	
	@Override
	public WebNode stringToWebNode(String s) throws IllegalArgumentException {
		
		StringTokenizer st1 = new StringTokenizer(s,"\t");
		String tok1;
		String tok2;
		WebNode wn = null;
		
		if(st1.countTokens() == 2){
			tok1 = st1.nextToken();
			tok2 = st1.nextToken();
		}
		else{
			StringTokenizer st2 = new StringTokenizer(s," ");
			if(st2.countTokens() == 2){
				tok1 = st2.nextToken();
				tok2 = st2.nextToken();
			}
			else{
				throw new IllegalArgumentException();
			}		
		}
		
		try{
			int priorityNum = Integer.parseInt(tok1);
			String webPage = tok2;
			wn = new WebNodeImpl(webPage, priorityNum);
		}
		catch(IllegalArgumentException ex){
			ex.printStackTrace();
		}
	
		return wn;
	}


	
	// sizeOfTempTable()
	// -----------------
	@Override
	public int sizeOfTempTable() {
			
		BufferedReader br = null;
		int numWebNodes = -2;  // include two headers
		
		try{
			FileReader fr= new FileReader(databaseFile);
			br = new BufferedReader(fr);
			String line;
			
			while((line=br.readLine()) != null){
				if(line.equals("END")){
					//System.out.println("Find END");
					break;
				}
				else{
					//System.out.println(line);
					numWebNodes++;
					//System.out.println("numWebNodes " + numWebNodes);
				}
				
			}	
		}
		catch(FileNotFoundException ex1){
			ex1.printStackTrace();
		}
		catch(IOException ex2){
			ex2.printStackTrace();
		}
		finally{
			try{
				br.close();
				if(numWebNodes < 0)  // i.e just headers in the file and no webNodes.
					numWebNodes = 0; 
				return numWebNodes;
			}
			catch(IOException ex3){
				ex3.printStackTrace();
			}
		}
		
		return 0;
	}

	// printTempTable()
	// ----------------
	@Override
	public void printTempTable() {
		BufferedReader br = null;
	
		try{
			FileReader fr= new FileReader(databaseFile);
			br = new BufferedReader(fr);
			String line;
			
			while((line=br.readLine()) != null){
				if(line.equals("END")){
					//System.out.println("Find END");
					break;
				}
				else{
					System.out.println(line);
				}	
			}	
		}
		catch(FileNotFoundException ex1){
			ex1.printStackTrace();
		}
		catch(IOException ex2){
			ex2.printStackTrace();
		}
		finally{
			try{
				br.close();
			}
			catch(IOException ex3){
				ex3.printStackTrace();
			}
		}
	}
	
	
	// retrieveNextWebNode()
	// ---------------------
	@Override
	public WebNode retrieveNextWebNode() {
		boolean found = false;  //flag for finding first WebNode in temp Table of database
		File tempFile = new File("tempDatabase.txt");  // temp Database to write to with amendments
		FileWriter fw = null; 
		BufferedReader br = null;
		BufferedWriter bw = null;
		WebNode retrievedNode = null;  // retrieved WebNode
		
		
		try{
			FileReader fr= new FileReader(databaseFile);
			br = new BufferedReader(fr);
			fw = new FileWriter(tempFile); 
			bw = new BufferedWriter(fw);
			
			String line;
			
			line = br.readLine();  // copy and paste headers
			bw.write(line + "\n");
			line = br.readLine();
			bw.write(line + "\n");
			
			while((line=br.readLine()) != null){
				StringTokenizer st = new StringTokenizer(line);
				String tok1 = st.nextToken();
				if(!tok1.equals("0") && found == false){     // if found FIRST non-zero priority
					String tok2 = st.nextToken();
					String replaceLine = "\t0\t\t\t" + tok2;   // amend line
					bw.write(replaceLine + "\n");				    // write to file
					found = true;							// set flag
					retrievedNode = new WebNodeImpl(tok2,Integer.parseInt(tok1));  // create WebNode
				}
				else{
					bw.write(line + "\n");   // copy and paste line
				}						
			}		
		}
		catch(FileNotFoundException ex1){
			ex1.printStackTrace();
		}
		catch(IOException ex2){
			ex2.printStackTrace();
		}
		finally{
			try{
				br.close();
				bw.close();
				
				this.databaseFile = new File("tempDatabase.txt");
				return retrievedNode;
			}
			catch(IOException ex3){
				ex3.printStackTrace();
			}
		}
		return null;
	}

}
