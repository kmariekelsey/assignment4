package assignment4;

import java.util.List;

public class Assign4Driver
{
public static Dictionary dictionary = new Dictionary();
	
	/**
	 * This function runs the main driver for word ladders
	 * 
	 * @param args: First is dictionary, second is word pairs
	 */
    public static void main(String[] args) {
    	
    	//check to see if there are two inputs to command line
    	if (args.length != 2) {
			System.err.println ("Error: Incorrect number of command line arguments");
			System.exit(-1);
		}
		
    	String A4Words = args[0]; //first argument is dictionary
    	
    	//reading file one line at a time with buffered file reader
		try {
			FileReader freader = new FileReader(A4Words);
			BufferedReader reader = new BufferedReader(freader);

			for (String s = reader.readLine(); s != null; s = reader.readLine()) {
				dictionary.processNewWord(s); //read each line in and add to dictionary
			}
			reader.close();
			//catching exceptions that could be thrown by buffered reader
		} catch (FileNotFoundException e) {
			System.err.println ("Error: File not found. Exiting...");
			e.printStackTrace();
			System.exit(-1);
		} catch (IOException e) {
			System.err.println ("Error: IO exception. Exiting...");
			e.printStackTrace();
			System.exit(-1);
		}
		
String wordPairs = args[1]; //actual words to generate ladders for
		
		try {
			FileReader freader = new FileReader(wordPairs);
			BufferedReader reader = new BufferedReader(freader);
			System.out.println("\n**********\n");
    		System.out.flush();

			for (String s = reader.readLine(); s != null; s = reader.readLine()) {
				processWordPair(s); //process each line and generate/output word latter
			}
			reader.close();
			//catch exceptions for buffered reader
		} catch (FileNotFoundException e) {
			System.err.println ("Error: File not found. Exiting...");
			e.printStackTrace();
			System.exit(-1);
		} catch (IOException e) {
			System.err.println ("Error: IO exception. Exiting...");
			e.printStackTrace();
			System.exit(-1);
		}
  
    }
    
    /**
     * This function parses an input string into two words and
     * calls the function to compute the word ladder using the
     * provided interface. There must be two words for the word
     * ladder method to be called. 
     * 
     * @param s: Pair of words from test file
     */
	private static void processWordPair(String s) {
		// Create a word ladder solver object
        Assignment4Interface wordLadderSolver = new WordLadderSolver();
        if (s.length() < 1) { //if the input string detected is empty, word ladder DNE
        	System.err.println("No valid word input detected: \"" + s + " \"");
    		System.err.flush(); //flushing to make sure console output in order
        	System.out.println("\n**********\n");
    		System.out.flush();
        	return;
        }
        String[] parsedInput = s.split("\\s+"); //any amount of white space can separate words
        if (parsedInput.length != 2) { //can only generate ladder btwn two words
        	System.err.println("Exactly two words required for word ladder: \"" + s + " \"");
        	System.err.flush(); //flushing to make sure console output in order
        	System.out.println("\n**********\n");
        	System.out.flush();
        	return;
        }
        
        //surrounding safe code in try catch block
        try {
            List<String> result = wordLadderSolver.computeLadder(parsedInput[0], parsedInput[1]); //interface method
            boolean correct = wordLadderSolver.validateResult(parsedInput[0], parsedInput[1], result); //interface method
            if (correct) printWordLadder(result); //print word ladder if it exists
            //method above to compute ladder can throw no such ladder exception
        } catch (NoSuchLadderException e) {
        	e.printStackTrace(); //identify where exception was called in code and println
        	System.err.flush();
        	System.out.println("\n**********\n");
    		System.out.flush(); //flushing system to ensure order of console output

        }
		
	}
    
}
