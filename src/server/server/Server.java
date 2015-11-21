package server.server;

import server.session.Session;
import server.session.SessionController;
import server.session.SessionPanel;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import common.*;
import server.window.ServerViewController;


/**
 * Holds the data used by the Server. Controlled by {@link ServerController}.
 *
 * @author Robert Wells
 * @version 1.0
 *
 */
public class Server implements GameConstants {

    private ServerViewController serverViewController;
    private ServerSocket serverSocket;
    private ArrayList<SessionController> sessionControllers;
    //private SessionController sessionController;
    private final int maxNumOfSessions = 2;

    private boolean isRunning;
    //private int playersPerSession = 2;

    /**
     * Initialises ArrayList {@link SessionController}.
     * Sets {@link #isRunning} to false, as server is not running when initialised.
     */
    public Server(ServerViewController serverViewController){
        sessionControllers = new ArrayList<SessionController>(maxNumOfSessions);

        this.serverViewController = serverViewController;
        isRunning = false;
    }

    /**
     * Creates the logic for a new game session.
     * Checks whether {@link #maxNumOfSessions} has been reached. If not,
     * a new sessionController is created and added to {@link #sessionControllers} and
     * also passed to {@link ServerViewController#addGameSession(server.session.SessionController)}.
     *
     * @return true if session is created otherwise false.
     * @throws ServerShutDownException If server isn't running.
     */
    public boolean createSession() throws ServerShutDownException {
        if(!isRunning) throw new ServerShutDownException();

        boolean sessionCreated = false;

        if(sessionControllers.size() < maxNumOfSessions) {
            SessionController session = new SessionController(new SessionPanel(),
                    new Session(this));
            sessionControllers.add(session);
            serverViewController.addGameSession(session);
            sessionCreated = true;
        }

        return sessionCreated;
    }

    /**
     * Stops the server from accepting new players. A new new socket is created and
     * {@link GameConstants#SERVERFULL} is written to the sockets outputstream.
     *
     * @return returns true if player stopped from connecting or false if IOException occurs.
     */
    public boolean stopConnectingPlayers() {

        Socket player;
        try {
            player = serverSocket.accept();

            new DataOutputStream(player.getOutputStream()).writeInt(SERVERFULL);

        } catch (IOException e) {
            return false;
        }

        return true;

    }

    /**
     * Connects player to a session and informs menuBar of player number.
     * Creates and accepts new socket, iterates through {@link #sessionControllers}, and if
     * a sessions is not full: the socket is added to that session, a new outputstream is created,
     * and the players number is written to the menuBar.
     *
     * @throws IOException sets {@link #isRunning} to false and throws IOException
     */
    public void connectPlayer() throws IOException {

        Socket player;
        try {
            player = serverSocket.accept();

        } catch (IOException e) {
            isRunning = false;
            throw new IOException();
        }

        for (SessionController sessions : sessionControllers) {
            if (!sessions.isGameSessionFull()){
                int playerNum = sessions.getPlayerNum();


                DataOutputStream outputStream = new DataOutputStream(player.getOutputStream());

                if(playerNum == 0){

                    for (int i = 0; i < 4; i++){
                        outputStream.writeInt(PLAYER1);
                    }
                } else {
                    for (int i = 0; i < 4; i++){
                        outputStream.writeInt(PLAYER2);
                    }
                }

                sessions.addPlayer(player, playerNum);

                break;
            }
        }

    }

    /**
     * Initialises {@link #serverSocket}. Sets {@link #isRunning} to true.
     *
     * @throws IOException if socket not created successfully.
     */
    public void start() throws IOException {
        serverSocket = new ServerSocket(3012);
        isRunning = true;
    }

    /**
     * Closes {@link #serverSocket} and sets {@link #isRunning} to false. Invokes
     * {@link server.window.ServerViewController#removeGameSessions()} to remove
     * the game session tabs from GUI and iterates through {@link #sessionControllers} and invokes
     * {@link server.session.SessionController#disconnectPlayers()} to send disconnect
     * messages to clients. Lastly, {@link #sessionControllers} ArrrayList is cleared.
     */
    public void stopServer() {
        isRunning = false;


        serverViewController.removeGameSessions();

        for (SessionController sessions : sessionControllers) {
            sessions.disconnectPlayers();
        }

        sessionControllers.clear();

        closeSocket();



    }

    public void closeSocket(){
        try {
            serverSocket.close();
        } catch (IOException ignored) {
            //Program logic deals with this.
        }
    }

    /**
     * Checks if all sessions are full by iterating through {@link #sessionControllers}
     * and invoking {@link server.session.SessionController#isGameSessionFull()}.
     *
     * @return Returns true if all sessions are full else false.
     */
    public boolean isSessionsFull(){
        int i = 0;
        for (SessionController sessions : sessionControllers) {
            if(sessions.isGameSessionFull()){
                i++;
            }
        }

        return i == maxNumOfSessions;

    }

    /**
     * Checks if maximum number of sessions has been reached by iterating through
     * {@link #sessionControllers} and checking whether they equal null.
     *
     * @return Returns true if maximum number of sessions has been reached else false.
     */
    public boolean isMaxSessions(){
        int i = 0;
        for (SessionController sessions : sessionControllers) {
            if(sessions != null){
                i++;
            }
        }

        return i == maxNumOfSessions;
    }

    /**
     * Checks whether the specified session is full by returning
     * {@link server.session.SessionController#isGameSessionFull()}.
     *
     * @param i the session number whose capacity is being checked.
     * @return Returns true if the specified session is full.
     */
    public boolean sessionFull(int i){
        return sessionControllers.size() == 0 || sessionControllers.get(i).isGameSessionFull();
    }

    public void setRunning(boolean running){
        isRunning = running;
    }

    public boolean isRunning(){
        return isRunning;
    }

}
