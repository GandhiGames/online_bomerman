package client.game;

import client.MessageBox;
import client.menuBar.ClientController;
import client.status.StatusBarView;
import common.*;
import common.PlayerQuitException;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;

/**
 * Class that acts as the controller for {@link Game}
 * and {@link GamePanel}. This set of classes handles game playing i.e. communicating with
 * the server, and updating the GUI in response to the server updates
 * (player moves, bombs, and changes in environment).
 *
 * @author Robert Wells
 * @version 1.0
 *
 */
public class GameController implements Runnable {
    private GamePanel gameView;
    private Game game;
    private boolean isGameStarted;
    private ClientController clientController;
    private int playerNum;
    private InputListener inputListener;
    private StatusBarView statusBarView;
    private boolean isThreadRunning;
    private boolean clientDisconnected;
    private Audio audio;

    /**
     * Initialises variables. Sets {@link #isGameStarted} to false, {@link #isThreadRunning}
     * to true, and creates a new {@link #inputListener}.
     *
     * @param gameView the window of the MVC.
     * @param game the model of the MVC.
     * @param statusBarView updated with information for players i.e. number of lives.
     *
     */
    public GameController(GamePanel gameView, Game game,
                          StatusBarView statusBarView, Audio audio){
        this.audio = audio;
        this.gameView = gameView;
        this.game = game;
        this.statusBarView = statusBarView;
        isGameStarted = false;
        isThreadRunning = true;
        inputListener = new InputListener();
        clientDisconnected = false;

    }

    /**
     * Adds input listener {@link InputListener}  inner class to {@link GamePanel}.
     *
     */
    private void addListeners(){
        gameView.addInputListener(inputListener);
    }

    /**
     * Removes listener if {@link #inputListener} not equal to null.
     *
     */
    private void removeListeners(){
        if (inputListener != null)
            gameView.removeInputListener(inputListener);
    }

    /**
     * Updates first players (local) lives using {@link StatusBarView#setFirstPlayerStatusText(String)}.
     * The number of lives are retrieved by invoking {@link Game#getPlayerLives()}.
     *
     */
    private void updatePlayerLives(){
        statusBarView.setFirstPlayerStatusText("Your Lives: " + game.getPlayerLives());
    }

    /**
     * Updates other players lives (networked) using {@link StatusBarView#setSecondPlayerStatusText(String)}.
     * The number of lives are retrieved by invoking {@link Game#getOtherPlayerLives()}.
     *
     */
    private void updateOtherPlayerLives(){
        statusBarView.setSecondPlayerStatusText("Other Player Lives: " + game.getOtherPlayerLives());
    }

    /**
     * Updates {@link StatusBarView} when a menuBar is playing one player. This is no longer
     * called once a successful connection has been made.
     *
     */
    private void updateWaitingStatus(){
        statusBarView.setFirstPlayerStatusText("Waiting for other player...");
        statusBarView.setSecondPlayerStatusText("Why not practice blowing up some blocks!");
    }

    /**
     * Invoked when the game needs to be reset i.e. win conditions have been reached. This allows
     * players to play another game quickly.
     *
     * Background music is stopped, {@link GamePanel#createEnvironment()} is invoked,
     * players created and placed in there starting position, there lives reset, and the background
     * music restarted.
     *
     */
    private void resetGame(){
        audio.stopBackgroundMusic();

        gameView.createEnvironment();

        int xy = gameView.getCells().length - 1;

        if(playerNum == GameConstants.PLAYER1){
            game.createPlayer(0, 0, 1, gameView.getCells());
            game.createOtherPlayer(xy,xy, 2, gameView.getCells());
        } else if (playerNum == GameConstants.PLAYER2) {

            game.createPlayer(xy, xy, 2, gameView.getCells());
            game.createOtherPlayer(0, 0, 1, gameView.getCells());
        }


        game.resetPlayerLives();

        audio.playBackgroundMusic();
    }


    /**
     * Invoked when connected to server, performs necessary steps to setup environment.
     * {@link #isThreadRunning} set to true, {@link #isGameStarted} set to false
     * (as not connected with another player yet), {@link GamePanel#createEnvironment()}
     * is invoked, the player put in starting position based on playerNum, the socket is passed to
     * {@link Game#addSocket(java.net.Socket)}, input listener is created and added,
     * player waiting status is created, background music is started, and lastly a thread is
     * created using this object.
     *
     * @param socket the players socket, used to communicate with server.
     * @param playerNum the player number, used to place in correct position and paint correct
     *                  sprite colour.
     *
     */
    public void connectedToServer(Socket socket, int playerNum){
        clientDisconnected = false;
        isThreadRunning = true;
        isGameStarted = false;

        gameView.createEnvironment();

        if(playerNum == GameConstants.PLAYER1){
            game.createPlayer(0, 0, 1, gameView.getCells());
        } else  {
            int xy = gameView.getCells().length - 1;
            game.createPlayer(xy, xy, 2, gameView.getCells());
        }

        this.playerNum = playerNum;

        game.addSocket(socket);

        addListeners();

        updateWaitingStatus();

        audio.playBackgroundMusic();


        new Thread(this).start();


    }

    /**
     * Invoked when the server is stopped, performs necessary maintenance to
     * cleanly disconnect and update the GUI appropriately.
     * {@link #isGameStarted} set to false as the game is no longer being played.
     * {@link #isThreadRunning} set to false so that while loop in the thread is exited.
     * The {@link StatusBarView} text is cleared.
     * {@link GamePanel#resetEnvironment()} is invoked to clear the GUI.
     * {@link client.game.Game#setPlayerToNull()} is invoked to clear the players. Lastly,
     * the input listeners are removed.
     *
     */
    public void serverStopped(){

        clientController.setConnectMenuName();

        clientController.setConnected(false);

        audio.stopBackgroundMusic();
        isGameStarted = false;
        isThreadRunning = false;
        statusBarView.clearText();
        gameView.resetEnvironment();
        game.setPlayerToNull();
        removeListeners();
        JOptionPane.showMessageDialog(null, "Disconnected: server shut down");
    }

    /**
     * Invoked when the player disconnects from server, performs necessary maintenance to
     * cleanly disconnect and update the GUI appropriately.
     *
     * Background audio is stopped.
     * {@link #isGameStarted} set to false as the game is no longer being played.
     * {@link #isThreadRunning} set to false so that while loop in the thread is exited.
     * The {@link StatusBarView} text is cleared.
     * A disconnect message is sent to the server by invoking {@link Game#sendDisconnect()}.
     * {@link GamePanel#resetEnvironment()} is invoked to clear the GUI.
     * {@link client.game.Game#setPlayerToNull()} is invoked to clear the players. Lastly,
     * the input listeners are removed.
     *
     */
    public void disconnectedFromServer(){
        clientDisconnected = true;

        audio.stopBackgroundMusic();
        statusBarView.clearText();
       // game.sendDisconnect();
        gameView.resetEnvironment();
        game.setPlayerToNull();
        //game.flushStreams();
        //game.closeSocket();
        removeListeners();


    }

    /**
     * Invoked by {@link #run()} when the thread is running and {@link #isGameStarted} is set to false.
     *
     * The message {@link GameConstants#OTHERPLAYERREADY} is sent to the server to signify
     * that this is menuBar is ready to play. An update is received from the server and it is checked
     * if the update equals {@link GameConstants#OTHERPLAYERREADY} to signify another player has connected.
     * If true, then {@link #isGameStarted} is set to true and another update is sent and received. This
     * is done to ensure that message passing is done in the correct order. Lastly, {@link #resetGame()}
     * is invoked.
     *
     */
    private void waitForOtherPlayer() throws PlayerQuitException,
            ServerShutDownException, IOException{

        int[] serverUpdate;

        game.clearOtherPlayerImage();

        game.sendPlayerData(new int[]{GameConstants.OTHERPLAYERREADY,
                GameConstants.OTHERPLAYERREADY, GameConstants.OTHERPLAYERREADY,
                GameConstants.OTHERPLAYERREADY});

        serverUpdate = game.getUpdateFromServer();

        if (clientDisconnected) {
            game.sendDisconnect();
            isGameStarted = false;
            isThreadRunning = false;
        } else if (serverUpdate[0] == GameConstants.OTHERPLAYERREADY){
            isGameStarted = true;


            game.sendPlayerData(new int[]{GameConstants.OTHERPLAYERREADY,
                    GameConstants.OTHERPLAYERREADY, GameConstants.OTHERPLAYERREADY,
                    GameConstants.OTHERPLAYERREADY});

            game.getUpdateFromServer();


            resetGame();

        }

    }

    /**
     * Invoked when win conditions have been met, performs necessary steps to
     * inform players, play ending animation and reset the game.
     *
     */
    private void gameEnded(String message) throws IOException{
        game.setGameOverInBomb(true);
        isGameStarted = false;
        new MessageBox(message);
        game.sendPlayerWon();
        gameView.runEndingAnimation();
        updateWaitingStatus();
        resetGame();
    }

    /**
     * Thread started when a menuBar connects to the server, performs the necessary steps
     * to send and receive updates, update the GUI, and perform win checking.
     *
     * <pre>
     * Pseudo code:
     *
     * While (thread is running) {
     *     while (other player hasn't connected) {
     *         {@link #waitForOtherPlayer()}
     *     }
     *
     *
     *     play game start sound ({@link client.game.Audio#playGameStartSound()}.
     *
     *     while (other player is connected) {
     *         update players lives.
     *
     *         if (local player hit) {
     *
     *             if (local players lives = 0) {
     *                  local player lost.
     *                 play game over sound.
     *                 call {@link #gameEnded(String)}
     *                 break from loop.
     *             } else {
     *                 send player hit data to server.
     *             }
     *         }
     *
     *         get update from server.
     *
     *         update other players lives.
     *
     *         if (first int from server update = {@link GameConstants#PLAYERWON}){
     *               if (other plays lives = 0) {
     *                  Draw.
     *                  call {@link #gameEnded(String)}
     *               } else {
     *                   local player won.
     *                   play game won sound.
     *
     *               }
     *
     *               break from loop.
     *         } else if (first int from server update = {@link GameConstants#PLAYERHIT}) {
     *             call {@link Game#otherPlayerHit()}.
     *             update other players lives.
     *         } else if (first int from server greater than 0) {
     *              update gameview with server update ({@link Game#updateCells(int[], Bomb, Player)}
     *
     *              if (bomb has been placed by other player) {
     *                  place other plays bomb in local environment {@link Game#setOtherPlayerBomb(int, int)}
     *              }
     *
     *         }
     *     }
     *
     * }
     * </pre>
     *
     * {@link PlayerQuitException} is caught and the game reset to signle player mode.
     * {@link ServerShutDownException} is caught and the menuBar sent back to the start screen.
     *
     */
    public void run(){

        int[] serverUpdate;// = new int[4];

        while (isThreadRunning){
            try {

                while (!isGameStarted && !clientDisconnected){
                    waitForOtherPlayer();
                }

                if (!isThreadRunning){
                    break;
                }
                audio.playGameStartSound();


                while (isGameStarted){

                    if (clientDisconnected) {
                        game.sendDisconnect();
                        isGameStarted = false;
                        isThreadRunning = false;
                        break;
                    } else {
                        updatePlayerLives();

                        if (game.checkIfPlayerHit()){
                            updatePlayerLives();
                            if (game.getPlayerLives() == 0) {
                                audio.playGameOverSound();
                                gameEnded("You Lost");
                                break;
                            } else
                                game.sendPlayerHit();
                        } else {
                            game.sendPlayerData();
                        }
                    }


                    serverUpdate = game.getUpdateFromServer();

                    updateOtherPlayerLives();


                        if (serverUpdate[0] == GameConstants.PLAYERWON ){
                            if (game.getOtherPlayerLives() == 0){
                                gameEnded("Draw!");
                            } else {
                                audio.playWinnerSound();
                                gameEnded("You Won!");
                            }
                            break;

                        } else if(serverUpdate[0] == GameConstants.PLAYERHIT){
                            game.otherPlayerHit();
                            updateOtherPlayerLives();
                        } else {
                            if (serverUpdate[0] >= 0) {
                                try{
                                    if (game.updateCells(serverUpdate,
                                            game.getOtherPlayerBomb(), game.getOtherPlayer())){
                                        game.setOtherPlayerBomb(serverUpdate[2], serverUpdate[3]);
                                    }
                                } catch (ArrayIndexOutOfBoundsException ai){
                                    break;

                                }
                            }
                        }






                }
            } catch (PlayerQuitException e) {
                new MessageBox("Other player quit!");
                isGameStarted = false;
                resetGame();
                updateWaitingStatus();
            } catch (ServerShutDownException e) {
                serverStopped();
                break;
            } catch (NullPointerException ignored){
                //Program logic deals with this.
            }catch (EOFException ignored){
                //Program logic deals with this
            }catch (IOException ignored) {
                //Program logic deals with this
            }

        }

       // game.sendDisconnect();


    }


    public void setClientController(ClientController clientController){
        this.clientController = clientController;
    }


    /**
     * Class that acts as an input listener for keyboard commands. The local character
     * is controlled using the arrow keys and bombs are placed using the space bar. When
     * a key is registered, {@link Game#movePlayer(Moves)} is called and the move passed.
     *
     */
    private class InputListener implements KeyListener {
        public void keyTyped(KeyEvent e) {
            //System.out.println("keyPressed="+KeyEvent.getKeyText(e.getKeyCode()));
        }

        public void keyPressed(KeyEvent e) {
            if(e.getKeyCode() == KeyEvent.VK_RIGHT){
                game.movePlayer(Moves.RIGHT);
                // System.out.println("keyPressed="+KeyEvent.getKeyText(e.getKeyCode()));
            }

            if(e.getKeyCode() == KeyEvent.VK_LEFT){
                game.movePlayer(Moves.LEFT);
            }

            if(e.getKeyCode() == KeyEvent.VK_UP){
                game.movePlayer(Moves.UP);
            }

            if(e.getKeyCode() == KeyEvent.VK_DOWN){
                game.movePlayer(Moves.DOWN);
            }

            if(e.getKeyCode() == KeyEvent.VK_SPACE){
                game.movePlayer(Moves.SPACE);
            }



        }

        public void keyReleased(KeyEvent e) {
            // System.out.println("keyPressed="+KeyEvent.getKeyText(e.getKeyCode()));
        }
    }


}
