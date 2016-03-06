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
}
