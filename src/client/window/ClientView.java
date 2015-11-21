package client.window;

import client.menuBar.ClientMenuBar;
import client.game.GamePanel;
import client.status.StatusBarView;

import java.awt.event.WindowAdapter;

/**
 * Provides an interface for the window component of the ClientFrame. Classes that conform to
 * this interface can act as the GUI/View i.e. provide a container to hold the other
 * menuBar-side components.
 *
 * @author Robert Wells
 * @version 1.0
 *
 */
public interface ClientView {

    public void createGUI(ClientMenuBar clientControlView, GamePanel gameView,
                          StatusBarView statusBarView);

    public void addWindowAdapter(WindowAdapter windowAdapter);
}
