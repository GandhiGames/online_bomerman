package server.window;

import server.server.Server;
import server.server.ServerController;
import server.server.ServerPanel;
import server.session.Session;
import server.session.SessionController;

import java.util.ArrayList;

/**
 * Holds the data used by {@link ServerFrameView}. Controlled by {@link ServerViewController}.
 *
 * @author Robert Wells
 * @version 1.0
 *
 */
public class ServerFrame implements ServerViewModel {
    private ArrayList<Session> gameSessions;
    private ServerController serverController;
    private ServerPanel serverPanel;

    /**
     * Initialises {@link server.session.Session} ArrayList and
     * {@link server.server.ServerPanel}
     *
     */
    public ServerFrame(){
        gameSessions = new ArrayList<Session>();
        serverPanel = new ServerPanel();
    }

    /**
     * Retrieves game session from session controller using
     * {@link server.session.SessionController#getGameSession()}
     * Add to gameSessions ArrayList
     *
     * @param sessionController {@link SessionController}
     *
     */
    public void addGameSession(SessionController sessionController){
        gameSessions.add(sessionController.getGameSession());

    }

    /**
     * Creates a new {@link server.server.Server} and
     * creates a new {@link ServerController} passing the Server
     * and ServerPanel.
     *
     * @param serverViewController {@link ServerViewController}
     *
     */
    public void createServerController(ServerViewController serverViewController){
        serverController
                = new ServerController(new Server(serverViewController),
                serverPanel);
    }

    /**
     * Invokes {@link server.server.ServerController#cleanUp()}
     *
     */
    public void cleanUpServer(){
        serverController.cleanUp();
    }


    public void clearGameSessions(){
        gameSessions.clear();
    }

    public int getGameSessionSize(){
        return gameSessions.size();
    }

    public ServerPanel getServerPanel(){
        return serverPanel;
    }
}
