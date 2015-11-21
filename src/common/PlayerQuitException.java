package common;

/**
 * Thrown when a player quits. Used to inform server and in turn inform the other player.
 *
 * @author Robert Wells
 * @version 1.0
 *
 */
public class PlayerQuitException extends Exception {
    public PlayerQuitException(){
        this("Player Quit Session");
    }

    public PlayerQuitException(String text){
        super(text);
    }
}
