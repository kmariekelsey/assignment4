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
    
    /**
     * Make a ladder recursively checking every possible path from each node. This is
     * an exhaustive search where if we reach a dead end, the word is remembered such
     * that no future node will pass through it again. For each next word in the 
     * recursion, a list of candidates is generated of which each word is only one
     * letter away from the current word without changing the letter that was just
     * previously altered (to progress). Then, each candidate word is checked to
     * see how far away it is from the destination word. The word closest is picked
     * first, then the others follow. Recursion ends when we have looked through all
     * possible paths or found a possible path.
     * 
     * @param fromWord: First word of ladder
     * @param toWord: Last word of ladder
     * @param posChanged: Last character position changed
     * @return true/false: If ladder was successfully generated or not
     */
    private boolean makeLadder(String fromWord, String toWord, int posChanged) {
    	solutionList.add(fromWord); //automatically add word if we pass through it
    	if (fromWord.equals(toWord)) return true; //done if going from word to same word
    	String endLadder = checkIfOneAway(fromWord, toWord); //done if word is one away from endWord
    	ArrayList<String> candidates = Assign4Driver.dictionary.matchAllButOne(fromWord, posChanged);
    	candidates = removeDuplicates(candidates); //remove candidate words already in ladder
    	candidates = removeDeadWords(candidates); //remove dead end words
    	if (endLadder != null) { 
    		solutionList.add(endLadder); //add last word and terminate
    		return true;
    	}
    	prependMismatch(candidates, toWord); //add number of differences in letter from endWord
    	Collections.sort(candidates); //sort by alphabetical order (fewest differences first)
    	
    	//go through each possible candidate to search for possible ladder
    	for (String word : candidates) {
    		String nextWord = word.substring(1); //ignore the number (letter difference from endWord)
    		int diffLetter = findPositionChanged(nextWord, fromWord); //find where it differs from previous
    		boolean done = makeLadder(nextWord, toWord, diffLetter); //recursive call with new word
    		if (done) return true; //if end found, exit recursion
    	}
    	
    	//case if dead end reached from previous stack frame path
    	solutionList.remove(fromWord); //remove word from solution list
    	deadList.add(fromWord); //word is a "dead end"
		return false; //return false to pop up in recursion stack
    }
    
    /**
     * Iterate through all the candidate words and remove dead end words.
     * 
     * @param candidates: Current possible next words
     * @return Refined candidates
     */
	private ArrayList<String> removeDeadWords(ArrayList<String> candidates) {
		Iterator<String> i = candidates.iterator(); //use iterator to manipulate collection in process
		while (i.hasNext()) {
			String temp = i.next();
			for (String ded : deadList) {
				if (ded.equals(temp)) {
					i.remove();
					break;
				}
			}
		}
		return candidates;
	}

	/**
	 * Checks to see if the current word is only one letter away from the end word.
	 * If not, a null reference is returned.
	 * 
	 * @param fromWord: Current word 
	 * @param toWord: End word
	 * @return reference to last word if it would be next
	 */
	private String checkIfOneAway(String fromWord, String toWord) {
    	int nonMatch = 0;
    	for (int i=0; i<fromWord.length(); i++) {
			if (fromWord.charAt(i) != toWord.charAt(i)) nonMatch++; //check each character across both
		}
    	if (nonMatch == 1) return toWord;
		return null;
	}

	/**
     * Returns the position of the letter that was just changed comparing nextWord (just obtained) with
     * fromWord (original search). Shouldn't normally return -1 because something had to change.
     * 
     * @return position that was changed
     */
	private int findPositionChanged(String nextWord, String fromWord) {
		for (int i=0; i<fromWord.length(); i++) {
			if (fromWord.charAt(i) != nextWord.charAt(i)) return i+1;
		}
		return -1;
	}

	/**
	 * Remove occurrences of solution list from the current list
	 * of candidates. This is so we don't pass through the same
	 * node when looking for valid ladder.
	 * 
	 * @param candidates: Current list of possible words
	 * @return Refined candidates
	 */
	private ArrayList<String> removeDuplicates(ArrayList<String> candidates) {
		Iterator<String> i = candidates.iterator();
		while (i.hasNext()) {
			String temp = i.next();
			for (String sol : solutionList) {
				if (sol.equals(temp)) {
					i.remove();
					break;
				}
			}
		}
		return candidates;
	}
	
	/**
	 * Add the number of letters that are a mismatch between each candidate
	 * and the final word to the beginning of each word. This makes it easy
	 * to sort by closeness to final word once words are sorted.
	 * 
	 * @param candidates: Current possible next words
	 * @param toWord: Final word in ladder
	 */
	private void prependMismatch(ArrayList<String> candidates, String toWord) {
		for (int j=0; j<candidates.size(); j++) {
			String word = candidates.get(j);
			int noMatch = 0;
			for (int i=0; i<toWord.length(); i++) {
				if (word.charAt(i) != toWord.charAt(i)) noMatch++; //calculate mismatches candidate vs toWord
			}
			word = "" + noMatch + word; //prepending toString(int #mismatches) to candidate words
			candidates.set(j, word);
		}
	}
	
}
