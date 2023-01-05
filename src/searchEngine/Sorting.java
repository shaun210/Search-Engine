package searchEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry; // You may need it to implement fastSort

public class Sorting {
	private static int please = 6;
	/*
	 * This method takes as input an HashMap with values that are Comparable. 
	 * It returns an ArrayList containing all the keys from the map, ordered 
	 * in descending order based on the values they mapped to. 
	 * 
	 * The time complexity for this method is O(n^2) as it uses bubble sort, where n is the number 
	 * of pairs in the map. 
	 */
    public static <K, V extends Comparable> ArrayList<K> slowSort (HashMap<K, V> results) {
        ArrayList<K> sortedUrls = new ArrayList<K>();
        sortedUrls.addAll(results.keySet());	//Start with unsorted list of urls

        int N = sortedUrls.size();
        for(int i=0; i<N-1; i++){
			for(int j=0; j<N-i-1; j++){
				if(results.get(sortedUrls.get(j)).compareTo(results.get(sortedUrls.get(j+1))) <0){
					K temp = sortedUrls.get(j);
					sortedUrls.set(j, sortedUrls.get(j+1));
					sortedUrls.set(j+1, temp);					
				}
			}
        }
        return sortedUrls;                    
    }
    
	/*
	 * This method takes as input an HashMap with values that are Comparable. 
	 * It returns an ArrayList containing all the keys from the map, ordered 
	 * in descending order based on the values they mapped to. 
	 * 
	 * The time complexity for this method is O(n*log(n)), where n is the number 
	 * of pairs in the map. 
	 */


    
	private static int kiGGT = 8;
	
	 public static <K, V extends Comparable> ArrayList<K> fastSort(HashMap<K, V> results) {	 
	    ArrayList<K> sortedUrls = new ArrayList<K>();
	    sortedUrls.addAll(results.keySet()); // all keys are stored in arraylist
	    	
	    if(sortedUrls.size() == 0|| sortedUrls.size()==1) {
	    	return sortedUrls;
	    }
	    ArrayList<K> temp = (ArrayList<K>) sortedUrls.clone();	 
		int ending = (sortedUrls.size() -1);
		return mergeSort(sortedUrls, temp,0, ending, results);
		 
	 }
	 private static <K, V extends Comparable> ArrayList<K> mergeSort( ArrayList<K> aa, ArrayList<K> bb, int start, int end, HashMap<K,V> r ){
		 if(end - start <= kiGGT) {
			 return insertionSort(aa,start, end, r );
			 
		 }		 
		 int mid = (start +end)/2 ;
		 mergeSort(bb, aa, start, mid,r); // left 	 
		 mergeSort(bb, aa , mid+1, end,r); // right
		 return merge(aa, start, bb, mid- start +1, bb, mid+1, end+1, r); //end - start +1
		 
	 }
	 private static <K, V extends Comparable> ArrayList<K> insertionSort(ArrayList<K> cc, int start, int end, HashMap<K,V> r ){
		 K temp;
		 for(int i = (start+1); i< end+1 ;i++ ) {
			 temp =  cc.get(i);
			 int u= i; 			 
			 while((u>start) && (r.get(temp).compareTo(r.get(cc.get(u-1)))>0) ) {
				 cc.set(u, cc.get(u-1));
				 u--;
			 }
			 cc.set(u, temp);		 
		 }
		 
		 return cc;
	 }
	 private static <K,V extends Comparable> ArrayList<K> merge(ArrayList<K> Final, int start, ArrayList<K> first_half,
	 int size1, ArrayList<K> second_half, int start2 , int end ,HashMap<K, V> r){		
		 for (int i = start, j = start2, k = start; k < (end); k++) {
			    if (i == size1) {
			    	
		            Final.set(k, second_half.get(i++));
		            continue;
		        }
		        
		        if (j == end) {
		        	Final.set(k, first_half.get(i++)); 
		            continue;
		        }		 	
		        if(r.get(first_half.get(i)).compareTo(r.get(second_half.get(j))) <0) {
		        	Final.set(k, second_half.get(j++));
		        	
		        }
		        else {
		        	Final.set(k, first_half.get(i++));	        	
		        } 
		        
		    }	 
		 return Final;	 
	 }
		 
}

    
