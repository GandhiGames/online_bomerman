package client.connection;

import client.MessageBox;
import client.menuBar.ClientController;
import client.game.GameController;
import common.GameConstants;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import common.*;

/**
 * Class that acts as the controller for {@link ClientConnection}
 * and {@link ClientConnectionFrame}. This set of classes deals with connecting to a server and
 * is created when a menuBar clicks on connect from the file menu.
 *
 * @author Robert Wells
 * @version 1.0
 *
 */
public class ClientConnectionController implements GameConstants {

    private ClientConnectionFrame clientConnectionView;
    private ClientController clientController;
    private ClientConnection clientConnection;
    private GameController gameController;

    /**
     * Invokes {@link #addListeners()}.
     *
     * @param clientConnectionView {@link ClientConnectionFrame}, the window for the MVC.
     * @param clientConnection {@link ClientConnection}, the model for the MVC.
     * @param clientController {@link ClientController}, used to update the GUI when menuBar connects/disconnects.
     * @param gameController {@link GameController},when menuBar connects this is updated with the socket.
     */
    public ClientConnectionController(ClientConnectionFrame clientConnectionView,
                                      ClientController clientController,
                                      ClientConnection clientConnection,
                                      GameController gameController){
        this.clientConnectionView = clientConnectionView;
        this.clientController = clientController;
        this.clientConnection = clientConnection;
        this.gameController = gameController;
        addListeners();
    }


    /**
     * Adds connect and cancel actionlisteners from this class to {@link ClientConnectionFrame}.
     *
     */
    private void addListeners(){
        clientConnectionView.addConnectButtonListener(new ConnectButtonListener());
        clientConnectionView.addCancelButtonListener(new CancelButtonListener());
    }


    /**
     * Performs a number of actions when the menuBar disconnects. The name of the
     * connect option in the file menu is changed to disconnect, a new {@link MessageBox}
     * is shown informing the user that the menuBar is disconnected,
     * sets {@link ClientController#setConnected(boolean)} to false, and invokes
     * {@link client.game.GameController#disconnectedFromServer()}
     *
     */
    public void disconnect(){
        clientController.setConnectMenuName();
        //new MessageBox("Disconnected from Server");
        clientController.setConnected(false);
        gameController.disconnectedFromServer();
        JOptionPane.showMessageDialog(null, "Disconnected from server");
    }


    /**
     * Class that acts as the actionlistener for {@link ClientConnectionFrame#connect}.
     * Orchestrates the connection to a server.
     *
     * @author Robert Wells
     * @version 1.0
     *
     */
    private class ConnectButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent actionEvent) {
            connectToServer();

        }

        /**
         * Called when {@link ClientConnectionFrame#connect} button is pressed. This method attempts
         * to connect to the server, if server full informs user, if successful informs user that
         * connection was successful and tells them the player number.
         *
         * <pre>
         * Pseudo code:
         *  if (menuBar not connected) {
         *      store ip address using {@link ClientConnectionFrame#getIPAddress()}
         *      store port number using {@link ClientConnectionFrame#getPort()}
         *
         *      connect to server and pass these variables: {@link ClientConnection#connectToServer(String, int)}
         *
         *      if (connection successful) {
         *          get first input from server
         *
         *          if (server input = {@link GameConstants#SERVERFULL}) {
         *               inform user
         *               Set {@link ClientConnection#socket} to null
         *          } else {
         *              change "connect" in file menu to "disconnect"
         *              set {@link client.menuBar.ClientController#isConnected()} to true
         *
         *              Close connection window
         *
         *              Pass socket and first input from server
         *              to {@link GameController#connectedToServer(java.net.Socket, int)}
         *
         *              if (input == {@link GameConstants#PLAYER1}) {
         *                  Inform user that connection is successful and they are player 1
         *              } else {
         *                  Inform user that connection is successful and they are player 2
         *              }
         *
         *          }
         *      }
         *  }
         *  </pre>
         *
         */
        private void connectToServer(){
            if(!clientController.isConnected()){
                try{
                    String address = clientConnectionView.getIPAddress();
                    int portNo = clientConnectionView.getPort();


                    clientConnection.connectToServer(address, portNo);


                    if(!clientConnection.isSocketNull()) {
                        int player[] = clientConnection.getInputfromServer();


                        if(player[0] == SERVERFULL){
                            new MessageBox("Server is currently full");
                            clientConnection.setSocketToNull();
                        } else {
                            clientController.setDisconnectMenuName();
                            clientController.setConnected(true);
                            clientConnectionView.dispose();
                            gameController.connectedToServer(clientConnection.getSocket(), player[0]);
                            //JOptionPane.showMessageDialog(null, "Connection Successful");

                            if(player[0] == PLAYER1) {
                                //JOptionPane.showMessageDialog(null, "Player One");
                                new MessageBox("Connection Successful: Player One");
                            } else if(player[0] == PLAYER2) {
                                //JOptionPane.showMessageDialog(null, "Player Two");
                                new MessageBox("Connection Successful: Player Two");
                            }

                        }

                    }
                } catch (ServerShutDownException she){
                    new MessageBox("Disconnected: Server Shut Down");
                    clientController.setConnectMenuName();
                    clientController.setConnected(false);
                    gameController.serverStopped();
                    clientController.setConnectMenuName();

                } catch (IOException ioe){
                    JOptionPane.showMessageDialog(null, "Cannot get data");
                }
            }
        }


    }

    /**
     * Class that acts as the actionlistener for {@link ClientConnectionFrame#cancel}.
     * Closes the window when the cancel button is pressed.
     *
     * @author Robert Wells
     * @version 1.0
     *
     */
    private class CancelButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent actionEvent) {
            clientConnectionView.closeWindow();
        }
    }
}
