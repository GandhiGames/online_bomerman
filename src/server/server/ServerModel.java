package server.server;

import common.ServerShutDownException;

import java.io.IOException;

/**
 * Provides an interface for the model component of the Server. Classes that conform to
 * this interface can act as the model i.e. hold and process data related to connecting players
 * and creating sessions.
 *
 * @author Robert Wells
 * @version 1.0
 *
 */
public interface ServerModel {

    public boolean createSession() throws ServerShutDownException;

    public boolean stopConnectingPlayers();

    public void connectPlayer() throws IOException;

    public void start() throws IOException;

    public void stopServer();

    public void closeSocket();

    public boolean isSessionsFull();

    public boolean isMaxSessions();


}
