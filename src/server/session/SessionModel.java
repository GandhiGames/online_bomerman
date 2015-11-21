package server.session;

import common.PlayerQuitException;

import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;

/**
 * Provides an interface for the model component of the Session. Classes that conform to
 * this interface can act as the model i.e. hold and process data relating to updating clients
 * during a game session.
 *
 * @author Robert Wells
 * @version 1.0
 *
 */
public interface SessionModel {

    public void stopSession(int playerNum);

    public void closeServerSocket();

    public void addPlayer(Socket player) throws IOException;

    public int[] getPlayerInput(int playerNum) throws PlayerQuitException,
            IndexOutOfBoundsException, IOException, NullPointerException, EOFException;

    public void sendPlayerData(int[] data, int playerNum);

    public void removePlayer(int playerNum);


}
