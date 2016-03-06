/*
    Names: Andrei Bonteanu, Katelyn Kelsey
    EIDs: fab542, kmk2669
    Solution for EE422C assignment4
    Lab Section: thursday @ 2:00 pm
*/

package assignment4;

import java.util.ArrayList;

public class Dictionary {

	//instance variable containing valid words processed from dictionary
	private ArrayList<String> words; 
	
	/**
	 * Dictionary constructor initializing the array that words will be stored in
	 */
	public Dictionary() { 
		words = new ArrayList<String>();
	}
	
	/**
	 * This function takes in a raw input line from the provided dictionary
	 * and parses it to obtain an array list stored in "words" of valid 
	 * english words to check against for the word ladder. If the line starts
	 * with a non-alphabetic character, it is ignored. Otherwise, the word is 
	 * contained in the first five characters of the line.
	 * 
	 * @param s: Each line read in from the unprocessed dictionary
	 */
	public void processNewWord(String s) {
		String alpha = "[a-z]";
		if (!s.isEmpty() && String.valueOf(s.charAt(0)).matches(alpha)) {
			String word = s.substring(0, 5);
			words.add(word);
		}
	}
	
	/**
	 * This function checks to see if an input test word is contained
	 * in the provided dictionary.
	 * 
	 * @param test: Word to check
	 * @return Boolean signifying whether word is in dictionary or not
	 */
	public boolean isWord(String test) {
		for (String word : words) {
			if (word.equals(test)) return true;
		}
		return false;
	}
	
}
