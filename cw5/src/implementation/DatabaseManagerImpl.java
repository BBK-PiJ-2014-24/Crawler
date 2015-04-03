package implementation;

import java.io.File;
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
				tok1 = st1.nextToken();
				tok2 = st1.nextToken();
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

}
