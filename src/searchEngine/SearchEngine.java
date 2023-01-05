package searchEngine;

import java.util.HashMap;
import java.util.ArrayList;

public class SearchEngine {
	public HashMap<String, ArrayList<String> > wordIndex; 
	public MyWebGraph internet;
	public XmlParser parser;

	public SearchEngine(String filename) throws Exception{
		this.wordIndex = new HashMap<String, ArrayList<String>>();
		this.internet = new MyWebGraph();
		this.parser = new XmlParser(filename);
	}
	
	/* 
	 * This does a graph traversal of the web, starting at the given url.
	 * For each url, it will add all the words on that page to the hashmap
	 * wordIndex and will then proceed to the neighbours of that url
	 * 
	 * 	This method will fit in about 30-50 lines (or less)
	 */
	
	public void crawlAndIndex(String url) throws Exception {
		
		this.internet.addVertex(url);		
		this.internet.setVisited(url, true);
		
		// returns all the words at current url page
		for(String word : this.parser.getContent(url)) { 
			
			// each word is a key. check if hashmap contains the word
			if(wordIndex.containsKey(word)) { 
				// check if they key contains the url in its list of url
				if(!(wordIndex.get(word).contains(url))) { 
					// add url if not in list
					wordIndex.get(word).add(url);	
					
				}
			}
			else { // if the word is not a key in the list
				ArrayList<String> newList = new ArrayList<String>();
				newList.add(url); // we are creating a new arrayList with a list of url for this key
				word = word.toLowerCase();
				wordIndex.put(word, newList);			
			}
		}
		
		// For each url in the file given, we have a list of its neighbours.
		// This will add the urls as neighbours to our current url.
		// We use depth-first approach to add the urls
		for(String neighbour : this.parser.getLinks(url)) {
			this.internet.addVertex(neighbour);
			this.internet.addEdge(url, neighbour);			
			if(this.internet.getVisited(neighbour) == false) {
				crawlAndIndex(neighbour);
			}
		}
	}
	
	
	
	
	/* 
	 * This computes the pageRanks for every vertex in the web graph.
	 * It will only be called after the graph has been constructed using
	 * crawlAndIndex(). 
	 * To implement this method, refer to the algorithm described in the 
	 * assignment pdf. 
	 * 
	 * This method will probably fit in about 30 lines.
	 */
	public void assignPageRanks(double epsilon) {
		ArrayList<Double> previous;
		ArrayList<Double> current;
		int countVertex=0;	
		ArrayList<String> vertice = this.internet.getVertices();
		for(String j: vertice) {
			this.internet.setPageRank(j, 1.0);
			countVertex++;
		}
		while(true) {
			int k =0;
			int uu=0;
			int NoOfTimeTrue=0;
			previous = computeRanks(vertice);
			for(String please : vertice) {
				this.internet.setPageRank(please, previous.get(k));					
				k++;
			}
			current = computeRanks(vertice);
			for(String one: vertice) {				
				if((Math.abs(this.internet.getPageRank(one)- current.get(uu)))< epsilon) {
					NoOfTimeTrue++;
					uu++;			
				}
			}
			if(NoOfTimeTrue == countVertex) {
				break;		
			}
		}

	}

	/*
	 * The method takes as input an ArrayList<String> representing the urls in the web graph 
	 * and returns an ArrayList<double> representing the newly computed ranks for those urls. 
	 * Note that the double in the output list is matched to the url in the input list using 
	 * their position in the list.
	 */
	public ArrayList<Double> computeRanks(ArrayList<String> vertices) {
		double d = 0.5;	
		ArrayList<Double> rankList = new ArrayList<Double>();
		for(String loop : vertices) {
			double pp=0.0;	
			ArrayList<String> into = internet.getEdgesInto(loop);
			for(String lol : into) {
				pp += d*( (this.internet.getPageRank(lol))/ (this.internet.getOutDegree(lol)));				
			}
			System.out.println();
			pp += (1-d);
			rankList.add(pp); // might be problem				
		}	
		return rankList;
	}

	
	/* Returns a list of urls containing the query, ordered by rank
	 * Returns an empty list if no web site contains the query.
	 * 
	 * This method should take about 25 lines of code.
	 */
	public ArrayList<String> getResults(String query) {
		HashMap<String ,Double> tempHash = new HashMap<String ,Double>();
		ArrayList<String> result;
		query = query.toLowerCase();
		
		//get the list of url related to that word from the hashmap
		if(wordIndex.containsKey(query)) {
			ArrayList<String> tempList = wordIndex.get(query);
			for(String s : tempList) {
				tempHash.put(s, internet.getPageRank(s));
			}
			result = Sorting.fastSort(tempHash);			
		}
		else {		
			return null;
		}	
		return result;
	}
}
