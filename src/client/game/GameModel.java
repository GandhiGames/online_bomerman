package client.game;

import common.ServerShutDownException;
import common.PlayerQuitException;

import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;

/**
 * Provides an interface for the model component of the Game. Classes that conform to
 * this interface can act as the model i.e hold and process the data required for
 * a game session.
 *
 * @author Robert Wells
 * @version 1.0
 *
 */
public interface GameModel {

    public void addSocket(Socket socket);

    public void createPlayer(int x, int y, int playerNum, Cell[][] cells);

    public void createOtherPlayer(int x, int y, int playerNum, Cell[][] cells);

    public void setPlayerToNull();

    public boolean isPlayerAlive();

    public void sendDisconnect();

    public int[] getUpdateFromServer() throws PlayerQuitException,
            IOException, ServerShutDownException, EOFException;

    public void sendPlayerData() throws IOException;

    public void sendPlayerData(int[] playerData) throws IOException, NullPointerException;

    public void setOtherPlayerBomb(int positionX, int positionY);

    public boolean checkIfPlayerHit();

    public boolean updateCells(int[] update, Bomb bomb, Player player) throws ArrayIndexOutOfBoundsException;

    public void movePlayer(Moves move);

    public void closeSocket();


}
