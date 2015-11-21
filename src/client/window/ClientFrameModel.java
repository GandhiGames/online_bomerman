package client.window;

import client.game.GamePanel;
import client.status.StatusBarView;

/**
 * Provides an interface for the model component of the ClientFrame. Classes that conform to
 * this interface can act as the model to hold and process data related to the menuBar-side
 * Jframe.
 *
 * @author Robert Wells
 * @version 1.0
 *
 */
public interface ClientFrameModel {

    public void cleanUp();

    public GamePanel getGameView();

    public StatusBarView getStatusBarView();
}
