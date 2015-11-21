package server.window;

import server.server.ServerPanel;
import server.session.SessionController;

import java.awt.event.WindowAdapter;

/**
 * Provides an interface for the window component of the Server. Classes that conform to
 * this interface can act as the GUI/View for the JFrame server-side.
 *
 * @author Robert Wells
 * @version 1.0
 *
 */
public interface ServerView {

    public void addTab(ServerPanel serverPanel);

    public void addGameSession(SessionController sessionController,
                               int gameSessionSize);

    public void removeGameSessions();

    public void addWindowCloser(WindowAdapter windowAdapter);


}
