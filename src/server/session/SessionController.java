package server.session;

import common.GameConstants;
import common.PlayerQuitException;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.util.Date;

/**
 * Class that acts as the the controller for {@link Session} and {@link SessionPanel}.
 * The Session Controller controls the logic and GUI for game sessions.
 *
 * @author Robert Wells
 * @version 1.0
 *
 */
public class SessionController implements GameConstants {

    private SessionPanel sessionOverview;
    private Session gameSession;
    private boolean sessionRunning;

    /**
     *  Appends "Sessions started" to session log
     *  by invoking {@link SessionPanel#appendSessionLog(String)}.
     *  Invokes {@link #addListeners()}.
     *
     *  @param sessionOverview {@link SessionPanel}
     *  @param gameSession {@link Session}
     */
    public SessionController(SessionPanel sessionOverview,
                             Session gameSession){

        this.sessionOverview = sessionOverview;
        this.gameSession = gameSession;

        sessionOverview.appendSessionLog(new Date() + ": Session Started");
        sessionRunning = true;

        addListeners();

        sessionRunning = true;

    }

    /**
     *  Adds {@link ClearSessionListener} to {@link SessionPanel}.
     */
    private void addListeners(){
        sessionOverview.addClearButtonActionListener(new ClearSessionListener());
    }

    /**
     *  Sets {@link #sessionRunning} to false.
     */
    public void disconnectPlayers(){
        sessionRunning = false;
        /*try {
            gameSession.stopSession();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Problem disconnecting players");
        } */
    }

    /**
     *  Adds socket to game session by invoking {@link Session#addPlayer(java.net.Socket)},
     *  updates GUI with player data including IP address and number of players in session.
     *
     *  Checks if gamesession contains maximum number of players, if yes the GUI is updated with
     *  "Maximum Players" and "Game started".
     *
     *  Creates and starts a new new thread passing {@link PlayerInput} and returns player number.
     *
     *  @param player the socket of the player being added to the session.
     */
    public void addPlayer(Socket player, int playerNum) throws IOException{
        gameSession.addPlayer(player);
        sessionOverview.appendSessionLog(new Date() + ": Player " + (playerNum+1) + " Joined");
        sessionOverview.appendSessionLog("Player IP address: "
                + player.getInetAddress().getHostAddress());
        sessionOverview.setNumOfPlayersLabel("" + gameSession.getNumOfPlayers());


        if(gameSession.isMaxPlayers()) {
            sessionOverview.appendSessionLog(new Date() + ": Maximum Players");
            sessionOverview.appendSessionLog(new Date() + ": Game Started");
        }

        new Thread(new PlayerInput(playerNum, this)).start();




    }

    public int getPlayerNum(){
        return gameSession.getPlayerNum();
    }

    /**
     *  The method is passed data received from the one player, which is then sent
     *  to the other player.
     *
     *  if (playerNum == 0)
     *      send data to player 2
     *  else
     *      send data to player 1
     *
     *  @param data player data to be sent.
     *  @param playerNum the number of the player whose data has been previously received.
     */
    public void handlePlayerData(int[] data, int playerNum){
        if(playerNum == 0){
            gameSession.sendPlayerData(data, 1);
        } else if (playerNum == 1) {
            gameSession.sendPlayerData(data, 0);
        }

    }

    public boolean isGameSessionFull(){
        return gameSession.isMaxPlayers();
    }

    public Session getGameSession(){
        return gameSession;
    }

    public SessionPanel getSessionOverview(){
        return sessionOverview;
    }

    /*private class StopSessionListener implements ActionListener {

        public void actionPerformed(ActionEvent actionEvent) {
            try {
                gameSession.stopSession();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Problem disconnecting players");
            }
            sessionOverview.appendSessionLog(new Date() + ": Session Stopped");
            sessionOverview.setStopButtonEnabled(false);

        }
    } */

    /**
     * Inner class that acts as an action listener for {@link SessionPanel#clear}.
     * Invokes {@link SessionPanel#clearLog()} when clear button pressed.
     *
     * @author Robert Wells
     * @version 1.0
     *
     */
    private class ClearSessionListener implements ActionListener {

        public void actionPerformed(ActionEvent actionEvent) {
            sessionOverview.clearLog();
        }
    }

    /**
     * Inner class that handles game session logic by receiving
     * and sending data to and from players. Implements Runnable.
     * A new thread is started for each player.
     *
     * @author Robert Wells
     * @version 1.0
     *
     */
    private class PlayerInput implements Runnable
    {
        private int playerNum;
        private SessionController sessionController;

        /**
         * Sets player number and session controller.
         *
         * @param playerNum the number of the player in the session whose data will be handled by this thread.
         * @param sessionController {@link SessionController}
         */
        public PlayerInput(int playerNum, SessionController sessionController){
            this.playerNum = playerNum;
            this.sessionController = sessionController;
        }


        public void run() {

            int[] playerUpdate;// = new int[4];

            while (gameSession.containsPlayer(playerNum)){
                try{


                    playerUpdate = gameSession.getPlayerInput(playerNum);





                    if (gameSession.isMaxPlayers())
                            sessionController.handlePlayerData(playerUpdate, playerNum);
                    else{
                        int[] temp = new int[]{OTHERPLAYERNOTREADY, OTHERPLAYERNOTREADY,
                                 OTHERPLAYERNOTREADY, OTHERPLAYERNOTREADY};
                                 gameSession.sendPlayerData(temp, playerNum);
                    }

                    if (!sessionRunning){

                        break;
                    }



                } catch (PlayerQuitException pqe){
                    disconnectPlayer();
                    break;
                } catch (IndexOutOfBoundsException ignored){
                    //program logic deals with this
                } catch (EOFException eof){
                    break;
                }catch (IOException ioe) {
                    //program logic deals with this
                }





            }

            if (!sessionRunning){
                        int[] temp = new int[]{KICKED, KICKED, KICKED, KICKED};
                        gameSession.sendPlayerData(temp, playerNum);
                        gameSession.stopSession(playerNum);


            } else if (gameSession.containsPlayer(playerNum)) {
                disconnectPlayer();
            }


        }

        private void disconnectPlayer(){
            boolean maxPlayers = false;
            if(gameSession.isMaxPlayers()){
                handlePlayerData(new int[]{GameConstants.PLAYERLEFTSESSION,
                        GameConstants.PLAYERLEFTSESSION, GameConstants.PLAYERLEFTSESSION,
                        GameConstants.PLAYERLEFTSESSION}, playerNum);
                maxPlayers = true;
            }

            sessionOverview.appendSessionLog(new Date() +
                    ": Player " + (playerNum + 1) + " quit");

            if (maxPlayers){
                sessionOverview.appendSessionLog(new Date() + ": Game Stopped");
            }

            gameSession.removePlayer(playerNum);
            sessionOverview.setNumOfPlayersLabel("" + gameSession.getNumOfPlayers());


        }

        private void disconnectPlayerNoOutput(){
            if(gameSession.isMaxPlayers()){
                handlePlayerData(new int[]{GameConstants.PLAYERLEFTSESSION,
                        GameConstants.PLAYERLEFTSESSION, GameConstants.PLAYERLEFTSESSION,
                        GameConstants.PLAYERLEFTSESSION}, playerNum);
            }


            gameSession.removePlayer(playerNum);
            sessionOverview.setNumOfPlayersLabel("" + gameSession.getNumOfPlayers());


        }
    }








}
