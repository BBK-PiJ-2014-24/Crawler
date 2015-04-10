Welcome to the WebCrawler.
-------------------------

Step 1: Run "WebCrawlLauncher". It will launch an interactive user interface.

Step 2: On the Command Prompt, you will be asked to supply:
	a) The Starting WebPage from which to begin the Crawl.
	b) The Choice of Whether to Select a Crawl Constrained by Breath or Depth.

Step 3: The crawl() will then begin. It will display the results of the crawl on the Command Console as well as export 	them to the Table of Temporary URL links found in the database.
	
Step 4: The user will then be asked as to whether he/she will like to execute the search() method on the current web 	page. If yes is selected, then a message will immediately follow indicating whether the search was
	successful or not. If the search was successful, then the link is added to the table of Permanent URL links.

Step 5: The Table of Permanent URL Links is then printed to the Command Console, reagrdless of whether the search() was 
	requested or not.

Step 6: The program will return to Step 3, unless:
	i) The crawl has run out of links to search.
	ii) The Maximum Breath of the Crawl has been reached.
	iii) The Maximum Depth of the Crawl has been reached.
	
	
ASSUMPTIONS:
------------

	i) All user inputs will be valid.