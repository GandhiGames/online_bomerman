package client.game;

import java.awt.event.KeyListener;

/**
 * Provides an interface for the window component of the Game. Classes that conform to
 * this interface can act as the GUI/View for the game i.e. provide and update the game
 * environment.
 *
 * @author Robert Wells
 * @version 1.0
 *
 */
public interface GameView {

    public void createEnvironment();

    public void runEndingAnimation();

    public void showStartScreen();

    public void resetEnvironment();

    public void addInputListener(KeyListener keyListener);

    public void removeInputListener(KeyListener keyListener);


}
