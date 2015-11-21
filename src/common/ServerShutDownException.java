package common;

/**
 * Thrown when the server is shut down. This is a common class
 * so both the menuBar and server can perform the correct action.
 *
 * @author Robert Wells
 * @version 1.0
 *
 */
public class ServerShutDownException extends Exception {

    public ServerShutDownException(){
        this("Server Shut Down");
    }

    public ServerShutDownException(String text){
        super(text);
    }
}
