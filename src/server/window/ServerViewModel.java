package server.window;

import server.session.SessionController;

/**
 * Provides an interface for the model component of the Server. Classes that conform to
 * this interface can act as the model for the server frame.
 *
 * @author Robert Wells
 * @version 1.0
 *
 */
public interface ServerViewModel {

    public void addGameSession(SessionController sessionController);

    public void createServerController(ServerViewController serverViewController);

    public void cleanUpServer();
}
