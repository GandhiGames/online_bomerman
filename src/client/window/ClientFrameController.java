package client.window;

import client.menuBar.ClientMenuBar;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Class that acts as the controller for {@link ClientFrameView}
 * and {@link ClientFrame}. This set of classes handle the main clientFrame window window
 * and the closing of the main window as well as the creation of initial objects (including other controllers).
 * This is the entry point into the application.
 *
 * @author Robert Wells
 * @version 1.0
 *
 */
public class ClientFrameController {
    private ClientFrameView clientFrameView;
    private ClientFrame clientFrame;

    /**
     * Calls {@link ClientFrameView#createGUI(client.menuBar.ClientMenuBar,
     * client.game.GamePanel, client.status.StatusBarView)} and {@link #addListeners()}.
     *
     *
     */
    public ClientFrameController(ClientFrameView clientFrameView, ClientFrame clientFrame,
                                 ClientMenuBar clientControlView) {

        this.clientFrameView = clientFrameView;
        this.clientFrame = clientFrame;


        clientFrameView.createGUI(clientControlView,
                clientFrame.getGameView(), clientFrame.getStatusBarView());

        addListeners();
    }

    /**
     * Adds window adapter {@link WindowCloser} to {@link ClientFrameView}.
     *
     */
    private void addListeners(){
        clientFrameView.addWindowAdapter(new WindowCloser());
    }

    /**
     * Class that handles graceful exiting of game when window is closed.
     * Calls {@link ClientFrame#cleanUp()} and the exits the
     * application.
     *
     * @author Robert Wells
     * @version 1.0
     *
     */
    public class WindowCloser extends WindowAdapter {

        public void windowClosing(WindowEvent e) {
            clientFrame.cleanUp();
            System.exit(0);
        }
    }




}
