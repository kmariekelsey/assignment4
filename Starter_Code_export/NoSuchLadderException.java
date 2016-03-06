/*
    Names: Andrei Bonteanu, Katelyn Kelsey
    EIDs: fab542, kmk2669
    Solution for EE422C assignment4
    Lab Section: thursday @ 2:00 pm
*/

package assignment4;

public class NoSuchLadderException extends Exception {
    private static final long serialVersionUID = 1L; //identifier

    /**
     * Constructor with only error message
     * 
     * @param message: Error message to display
     */
    public NoSuchLadderException(String message) {
        super(message);
    }

    /**
     * Constructor with error message and throwable object
     * 
     * @param message: Error to display
     * @param throwable
     */
    public NoSuchLadderException(String message, Throwable throwable) {
        super(message, throwable);
    }
}