package implementation;

import iinterface.WebNode;

public class WebNodeImpl implements WebNode {

	// Fields
	// ------
	
	String webLink;
	int priorityNum;
	
	
	// Constructor
	// -----------
	public WebNodeImpl(String webLink, int priorityNum){
		this.webLink = webLink;
		this.priorityNum = priorityNum;
	}
	
	// Getter/Setter
	// -------------
	@Override
	public void setPriorityNum(int priorityNum) {
		this.priorityNum = priorityNum;
	}

	@Override
	public int getPriorityNum() {
		return priorityNum;
	}

	@Override
	public void setWebLink(String webLink) {
		this.webLink = webLink;
	}

	@Override
	public String getWebLink() {
		return webLink;
	}
	
	@Override
	public String toString(){
		return "\t" + priorityNum + "\t\t\t" + webLink;
	}

}
