package server.exceptions;

/**
 * Thrown when the server reaches maximum number of concurrent players and
 * sessions.
 *
 * @author Robert Wells
 * @version 1.0
 *
 */
public class ServerFullException extends Exception {
    public ServerFullException(){
        this("server Full");
    }

    public ServerFullException(String text){
        super(text);
    }
}
