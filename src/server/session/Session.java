package server.session;

import java.io.*;
import java.net.Socket;
import java.util.TreeMap;

import common.PlayerQuitException;
import common.GameConstants;
import server.server.Server;

/**
 * Holds and performs logic on the data used during game sessions.
 * Controlled by {@link SessionController}.
 *
 * @author Robert Wells
 * @version 1.0
 *
 */
public class Session implements GameConstants, SessionModel {

    private boolean isRunning;
    private TreeMap<Integer, Socket> players;
    private TreeMap<Integer, DataInputStream> fromPlayers;
    private TreeMap<Integer, DataOutputStream> toPlayers;
    private Server server;
    //private Cell[][] cells = new Cell[MAPSIZE][MAPSIZE];

    /**
     *  Initialises {@link #players}, {@link #fromPlayers}, {@link #toPlayers}
     *  and sets {@link #isRunning} to true
     */
    public Session(Server server){
        this.server = server;
        players = new TreeMap<Integer, Socket>();

        fromPlayers = new TreeMap<Integer, DataInputStream>();
        toPlayers = new TreeMap<Integer, DataOutputStream>();

        isRunning = true;
    }

    /**
     * Sets {@link #isRunning} to false, sends {@link GameConstants#KICKED} message to players,
     * and invokes {@link #removePlayer(int)} for each player.
     *
     */
    public synchronized void stopSession(int playerNum) {


        removePlayer(playerNum);


        if (getNumOfPlayers() == 0){
            closeServerSocket();
        }
    }



    public void closeServerSocket(){

        isRunning = false;
        server.closeSocket();
    }

    /**
     * Creates Input and Output stream based on socket,
     * adds socket to {@link #players}, and adds streams to
     * {@link #fromPlayers} and {@link #toPlayers}.
     *
     * if(players contains a socket in index 0)
     *      add player socket to index 1 (i.e. it becomes player two)
     * else
     *      add player socket to index 0 (i.e. it becomes player one)
     *
     * This allows for a player to join a game if player one has previously left.
     *
     * @param player Socket used to send and recieve data from player.
     * @throws IOException if message sending to players was unsuccessful.
     *
     */
    public void addPlayer(Socket player) throws IOException{


        InputStream playerInputStream = player.getInputStream();
        OutputStream playerOutputStream = player.getOutputStream();

        if(players.containsKey(new Integer(0))){
            players.put(1, player);
            toPlayers.put(1, new DataOutputStream(playerOutputStream));

            fromPlayers.put(1, new DataInputStream(playerInputStream));


        } else  {

            players.put(0, player);

            toPlayers.put(0, new DataOutputStream(playerOutputStream));

            fromPlayers.put(0, new DataInputStream(playerInputStream));


        }

        //System.out.println("Added player: " + (playerNum + 1));


    }

    public int getPlayerNum(){
        int playerNum;


        if(players.containsKey(new Integer(0))){

            playerNum = 1;
        } else  {


            playerNum = 0;
        }

        //System.out.println("Added player: " + (playerNum + 1));

        return playerNum;

    }

    /**
     *  Returns true if {@link #players} size is greater than or equal to two
     *  i.e. only two players allowed per session.
     *
     *  @return true if maximum players for a session
     */
    public boolean isMaxPlayers(){
        return players.size() >= 2;
    }

    /**
     * Creates new int array and reads from {@link #fromPlayers} based on playerNum.
     *
     * @param playerNum the player whose input is to be received.
     * @return returns player input as an array of ints
     * @throws PlayerQuitException to signify a player has quit
     */
    public int[] getPlayerInput(int playerNum) throws PlayerQuitException,
        IndexOutOfBoundsException, IOException, NullPointerException, EOFException {

        int playerData[] = new int[4];

        playerData[0] = fromPlayers.get(playerNum).readInt();
        playerData[1] = fromPlayers.get(playerNum).readInt();
        playerData[2] = fromPlayers.get(playerNum).readInt();
        playerData[3] = fromPlayers.get(playerNum).readInt();

        for (int aPlayerData : playerData) {
            if (aPlayerData == PLAYERLEFTSESSION) {

                throw new PlayerQuitException();
            }
        }

            return playerData;

    }

    /**
     * Sends data to player using {@link #toPlayers} based on playerNum.
     *
     * @param data the data to be sent to the player
     * @param playerNum the player whose is to receive the output.
     *
     */
    public void sendPlayerData(int[] data, int playerNum){

        try {
            toPlayers.get(playerNum).writeInt(data[0]);
            toPlayers.get(playerNum).writeInt(data[1]);
            toPlayers.get(playerNum).writeInt(data[2]);
            toPlayers.get(playerNum).writeInt(data[3]);
        } catch (IOException e) {
            //Program logic deals with this.
        }
    }

    /**
     * Removes player from session by removing item from {@link #players},
     * {@link #fromPlayers}, and {@link #toPlayers} based on playerNum.
     *
     * @param playerNum the player whose is to be removed from the session.
     */
    public void removePlayer(int playerNum) {
        players.remove(playerNum);
        fromPlayers.remove(playerNum);
        toPlayers.remove(playerNum);
        //System.out.println("Removed player: " + players.size());
    }

    /**
     * Checks if the session contains a player.
     * This is used when adding a player to a session.
     *
     * @param player the number of the player whose status ie being checked
     * @return true if {@link #players} size is greater than or equal to player param
     */
    public boolean containsPlayer(int player){
        return players.size() >= player;

    }

    public int getNumOfPlayers(){
        return players.size();
    }

    public boolean isRunning(){
        return isRunning;
    }

}
