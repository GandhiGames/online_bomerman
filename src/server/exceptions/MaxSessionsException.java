package server.exceptions;

/**
 * Thrown when the maximum number of concurrent session has been reached on the server.
 *
 * @author Robert Wells
 * @version 1.0
 *
 */
public class MaxSessionsException extends Exception {

    public MaxSessionsException(){
        this("Max number of sessions reached");
    }

    public MaxSessionsException(String text){
        super(text);
    }
}
