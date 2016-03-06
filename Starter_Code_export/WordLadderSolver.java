   Names: Andrei Bonteanu, Katelyn Kelsey
    EIDs: fab542, kmk2669
    Solution for EE422C assignment4
    Lab Section: thursday @ 2:00 pm
*/

package assignment4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

// do not change class name or interface it implements
public class WordLadderSolver implements Assignment4Interface {
    // declare class members here.
	ArrayList<String> solutionList;
	ArrayList<String> deadList;
	
	/**
	 * Constructor for word solver initializing necessary arrays.
	 */
	public WordLadderSolver(){
		solutionList = new ArrayList<String>(); //initialize solutions
		deadList = new ArrayList<String>(); //initialize dead end words
	}

	/**
	 * This function is declared in the interface. It generates a word ladder
	 * from the fromWord to the endWord. The returned list is an ArrayList
	 * containing the words in order if the ladder exists. If the words are
	 * invalid or there is no possible ladder between the two, and exception
	 * is thrown and the program continues without crashing.
	 */
    public List<String> computeLadder(String startWord, String endWord) throws NoSuchLadderException {
        //in the case that the words don't exist in the dictionary or they aren't of proper length
    	if (startWord.length() != 5 || endWord.length() !=5 
        		|| !Assign4Driver.dictionary.isWord(startWord) || !Assign4Driver.dictionary.isWord(endWord)) {
        	throw new NoSuchLadderException("Invalid Words: Ladder cannot be generated between \"" 
        		+ startWord + "\" and \"" + endWord +"\"!");
        }
    	boolean exists = makeLadder(startWord, endWord, 0); //generate the word ladder
    	if (!exists) { //if recursion returns false, throw exception and exit
    		throw new NoSuchLadderException("Discontinuity: No word ladder exists between " 
    				+ startWord + " and " + endWord + "!");
    	}					
        return solutionList;
    }

    /**
     * This function checks the word ladder against the first and last words.
     * Given that the word wouldn't be here if it didn't exist in the dictionary,
     * we don't have to make sure each word is valid. All we have to do is see
     * if there is only one change between each pair of words.
     */
    public boolean validateResult(String startWord, String endWord, List<String> wordLadder) {
    	if (!wordLadder.get(0).equals(startWord) || !wordLadder.get(wordLadder.size()-1).equals(endWord)) return false;
        for (int i=0; i<wordLadder.size()-2; i++) { //go for n-2 because of 0 indexing and n-1 connections between n items
        	String first = wordLadder.get(i);
        	String second = wordLadder.get(i+1);
        	int misMatch = 0;
        	for (int j=0; j<first.length(); j++) { //calculate how many different letters between consecutive words
        		if (first.charAt(j) != second.charAt(j)) misMatch++;
        	}
        	if (misMatch != 1) return false; //if more than one mismatch in letters, not valid word ladder
        }
    	return true;
    }

    // add additional methods here
}
