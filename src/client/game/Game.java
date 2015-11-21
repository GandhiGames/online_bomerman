package client.game;

import client.MessageBox;
import common.GameConstants;
import common.ServerShutDownException;
import common.PlayerQuitException;

import java.io.*;
import java.net.Socket;


/**
 * Class that acts as the model for {@link Game}.
 * This class holds the data to be operated on. Its main responsibility is receiving
 * and sending data to the server, and updating player data in response
 * to data received from the server.
 *
 * @author Robert Wells
 * @version 1.0
 *
 */
public class Game implements GameModel {
    private Socket socket;
    private Player player, otherPlayer;
    private Bomb bomb, otherPlayerBomb;
    private DataInputStream fromServer;
    private DataOutputStream toServer;
    //private int otherPlayerLives;
    private  Audio audio;

    public Game(Audio audio){
        this.audio = audio;
       // otherPlayerLives =  GameConstants.PLAYERLIVES;
    }

    /**
     * Adds socket to this object and initialises new Datainputstreams {@link #fromServer}
     * and Dataoutputsteams {@link #toServer} based on this socket. These are used to
     * communicate the game state with the server.
     *
     * @param socket the socket to be stored in this object.
     */
    public void addSocket(Socket socket) {
        this.socket = socket;

        try {
            fromServer = new DataInputStream(socket.getInputStream());
            toServer = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            new MessageBox("Problem with game, please restart menuBar");
        }

    }


    /**
     * Initialises local player objects {@link #player} and {@link #bomb}.
     *
     */
    public void createPlayer(int x, int y, int playerNum, Cell[][] cells){
        player = new Player(cells, x, y, playerNum, audio);
        bomb = new Bomb(cells, player, otherPlayer, audio);


    }

    /**
     * Initialises network player objects {@link #otherPlayer} and {@link #otherPlayerBomb}.
     *
     */
    public void createOtherPlayer(int x, int y, int playerNum, Cell[][] cells){
        otherPlayer = new Player(cells, x, y, playerNum, audio);
        otherPlayerBomb = new Bomb(cells, otherPlayer, player, audio);
    }


    /**
     * Sets {@link #player}, {@link #bomb}, {@link #otherPlayer}, and {@link #otherPlayerBomb}
     * to null.
     *
     */
    public void setPlayerToNull(){
        player = null;
        bomb = null;
        otherPlayer = null;
        otherPlayerBomb = null;
    }

    /**
     * Returns true if {@link #player} not equal to null.
     */
    public boolean isPlayerAlive(){
        return player != null;
    }



    /**
     * Sends {@link GameConstants#PLAYERLEFTSESSION} informing server that player
     * has disconnected.
     */
    public void sendDisconnect(){
        try{
            toServer.writeInt(GameConstants.PLAYERLEFTSESSION);
            toServer.writeInt(GameConstants.PLAYERLEFTSESSION);
            toServer.writeInt(GameConstants.PLAYERLEFTSESSION);
            toServer.writeInt(GameConstants.PLAYERLEFTSESSION);
            toServer.flush();
        } catch (IOException ioe){
            //new MessageBox("Problem disconnecting from server, server may need restarting");
        }


    }

    /**
     * Receives and returns an update from server. Four ints are
     * read and stored in an array. Checks if first int equals
     * {@link GameConstants#PLAYERLEFTSESSION} (other player left game) or
     * {@link GameConstants#KICKED} (server shutdown) and throws the exception.
     *
     *
     * @return array of ints containing the server update.
     *
     * @throws PlayerQuitException when other player has left the game.
     * @throws ServerShutDownException when server is shutdown.
     */
    public int[] getUpdateFromServer() throws PlayerQuitException,
            IOException, ServerShutDownException, EOFException {
        int playerData[] = new int[4];

        playerData[0] = fromServer.readInt();
        playerData[1] = fromServer.readInt();
        playerData[2] = fromServer.readInt();
        playerData[3] = fromServer.readInt();


        if(playerData[0] == GameConstants.PLAYERLEFTSESSION){
            throw new PlayerQuitException();
        } else if (playerData[0] == GameConstants.KICKED) {
            throw new ServerShutDownException();
        }

        return playerData;

    }


    /**
     * Sends player data to server. The data consists of:
     * 1) local players X position
     * 2) local players Y position
     * 3) local bombs X position
     * 4) local bombs Y position
     *
     * If the local bomb is already active and placed {@link GameConstants#NOBOMB}
     * is sent. This stops the player from sending a large number of bomb positions
     * over the network.
     *
     */
    public void sendPlayerData() throws IOException {

        int[] playerPos = getPlayerPosition();

        toServer.writeInt(playerPos[0]);
        toServer.writeInt(playerPos[1]);

        if(!bomb.isActive() || !bomb.isPlaced()){
            toServer.writeInt(GameConstants.NOBOMB);
            toServer.writeInt(GameConstants.NOBOMB);

        } else {
            int[] bombPosition = bomb.getPosition();
            toServer.writeInt(bombPosition[0]);
            toServer.writeInt(bombPosition[1]);
        }

    }

    /**
     * Sends player data to server.
     *
     * @param playerData the data to be sent.
     *
     */
    public void sendPlayerData(int[] playerData) throws IOException, NullPointerException {
        toServer.writeInt(playerData[0]);
        toServer.writeInt(playerData[1]);
        toServer.writeInt(playerData[2]);
        toServer.writeInt(playerData[3]);

    }

    /**
     * Sends {@link GameConstants#PLAYERWON} to server. This informs the other player
     * that this player has won.
     *
     */
    public void sendPlayerWon() throws IOException{
        toServer.writeInt(GameConstants.PLAYERWON);
        toServer.writeInt(GameConstants.PLAYERWON);
        toServer.writeInt(GameConstants.PLAYERWON);
        toServer.writeInt(GameConstants.PLAYERWON);

    }

    /**
     * Sends {@link GameConstants#PLAYERHIT} to server. This informs the other player
     * that this player has been hit by a bomb..
     *
     */
    public void sendPlayerHit() throws IOException{
        toServer.writeInt(GameConstants.PLAYERHIT);
        toServer.writeInt(GameConstants.PLAYERHIT);
        toServer.writeInt(GameConstants.PLAYERHIT);
        toServer.writeInt(GameConstants.PLAYERHIT);


    }

    /**
     * Places {@link #otherPlayerBomb} is start position.
     *
     */
    public void setOtherPlayerBomb(int positionX, int positionY){
      otherPlayerBomb.placeInStart(new int[]{positionX, positionY});

    }

    /**
     * Checks if local player has been hit by own bomb or other players bomb.
     *
     * Checks if both bombs are active and calls {@link Bomb#isPlayerHit(int[])}.
     *
     * If the player has been hit, there life is removed, and {@link client.game.Audio#playHitByBombSound()}
     * is played.
     *
     * @return returns true if player hit by bomb.
     */
    public boolean checkIfPlayerHit(){
        if (bomb.isActive() && bomb.isPlayerHit(player.getPosition()) &&
                !bomb.isHitPlayer()){
            bomb.setHitPlayer(true);
            player.removeLife();
            audio.playHitByBombSound();
            return true;
        }

        if (otherPlayerBomb.isActive()
                && otherPlayerBomb.isPlayerHit(player.getPosition()) &&
                !otherPlayerBomb.isHitPlayer()) {
            otherPlayerBomb.setHitPlayer(true);
            player.removeLife();
            audio.playHitByBombSound();
            return true;
        }

        return false;
    }

    /**
     * Clears the other players image when local player is not connected with another player.
     *
     */
    public void clearOtherPlayerImage(){
        if (otherPlayer != null)
            otherPlayer.clear();
    }

    /**
     * Deals with updating the network players position based on information received by the server.
     * Invoked by {@link GameController}.
     *
     * Calls {@link Player#setPosition(int, int)} for network players and
     * checks if bomb has been set. If so, returns true.
     *
     * @param update the server update.
     * @param bomb the other players bomb.
     * @param player the other player.
     * @return Whether the other player (over the network) has placed a bomb.
     *
     */
    public boolean updateCells(int[] update, Bomb bomb, Player player) throws ArrayIndexOutOfBoundsException{

        boolean bombSet = false;

        player.setPosition(update[0], update[1]);


        if(update[2] != GameConstants.NOBOMB){
            // cells[update[2]][update[3]].setImageName(ImageName.BOMB);

            if (!bomb.isActive() && !bomb.isPlaced())
                bombSet = true;
        }


        return bombSet;
    }

    /**
     * Moves player based on move received from {@link client.game.GameController.InputListener}.
     *
     * @param move {@link Moves} holds the possible moves.
     *
     */
    public void movePlayer(Moves move){

        switch (move){
            case RIGHT:
                player.movePlayerRight();
                break;
            case LEFT:
                player.movePlayerLeft();
                break;
            case UP:
                player.movePlayerUP();
                break;
            case DOWN:
                player.movePlayerDown();
                break;
            case SPACE:
                if (!bomb.isActive())
                    bomb.placeInStart(player, otherPlayer);
                break;

        }
    }

    public void closeSocket(){
        try {
            socket.close();
            fromServer.close();
            toServer.close();
        } catch (IOException e) {
            new MessageBox("Problem disconnecting, please restart menuBar");
        }

    }

    public void flushStreams(){
        try {
            toServer.flush();
        } catch (IOException ignored) {
        }
    }

    public void resetPlayerLives(){
        otherPlayer.setLives(GameConstants.PLAYERLIVES);
    }

    public boolean isOtherPlayersBombActive(){
        return  otherPlayerBomb.isActive();
    }

    public Player getOtherPlayer(){
        return otherPlayer;
    }

    public Socket getSocket(){
        return socket;
    }

    public Bomb getOtherPlayerBomb(){
        return otherPlayerBomb;
    }

    public int[] getPlayerPosition(){
        return player.getPosition();
    }

    public int getPlayerLives(){
        return player.getLives();
    }

    public int getOtherPlayerLives(){
        return otherPlayer.getLives();
    }

    public void setGameOverInBomb(boolean gameOver){
        bomb.setGameOver(gameOver);
        otherPlayerBomb.setGameOver(gameOver);
    }

    public void otherPlayerHit(){
        otherPlayer.removeLife();
    }


}
