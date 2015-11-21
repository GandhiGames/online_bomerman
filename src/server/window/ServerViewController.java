package server.window;

import server.session.SessionController;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
  * Class that acts as the the controller for {@link ServerFrame} and {@link ServerFrameView}.
  * The Server View provides the main GUI server-side
  *
  * @author Robert Wells
  * @version 1.0
  *
  */
public class ServerViewController {

    private ServerFrameView serverView;
    private ServerFrame serverFrame;


    /**
     * Invokes {@link #addListeners()} and {@link #createGUI()}
     *
     * @param serverView {@link ServerFrameView}
     * @param serverFrame {@link ServerFrame}
     *
     */
    public ServerViewController(ServerFrameView serverView,
                                ServerFrame serverFrame){
        this.serverView = serverView;
        this.serverFrame = serverFrame;

        addListeners();
        createGUI();
    }

    /**
     * Called from the constructor.
     * Adds window closer listener to {@link ServerFrameView}.
     *
     * @see ServerViewController#ServerViewController(ServerFrameView, ServerFrame)
     *
     */
    private void addListeners(){
        serverView.addWindowCloser(new WindowCloser());
    }

    /**
     * Adds Game session using {@link ServerFrame#addGameSession(server.session.SessionController)}
     * and {@link ServerFrameView#addGameSession(server.session.SessionController, int)}
     *
     * Adds a tab to main server GUI and accepts incoming connections for that session.
     *
     * @param sessionController {@link SessionController}
     *
     */
    public void addGameSession(SessionController sessionController){
        serverFrame.addGameSession(sessionController);
        serverView.addGameSession(sessionController,
                serverFrame.getGameSessionSize());
    }

    /**
     * Creates the main server GUI. Adds Server overview tab.
     * Creates the Server controller.
     *
     */
    public void createGUI(){
        serverFrame.createServerController(this);
        serverView.createGUI();
        serverView.addTab(serverFrame.getServerPanel());
    }

    /**
     * Removes Game session tab from main GUI
     *
     */
    public void removeGameSessions(){
        serverView.removeGameSessions();
        serverFrame.clearGameSessions();
    }

    /**
     * Inner class extends WindowAdapter
     * Invokes {@link ServerFrame#cleanUpServer()}
     * and exits system.
     *
     */
     class WindowCloser extends WindowAdapter {

        public void windowClosing(WindowEvent e) {
            serverFrame.cleanUpServer();
            System.exit(0);
        }
    }
}
