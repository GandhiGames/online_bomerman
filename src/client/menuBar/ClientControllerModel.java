package client.menuBar;

import client.connection.ClientConnectionController;
import client.connection.ClientConnectionFrame;
import client.connection.ClientConnection;
import client.game.GameController;

/**
 * Class that acts as the model for {@link ClientController}.
 * This class holds the data to be operated on i.e. {@link ClientConnectionController}
 * and {@link GameController}. Its main responsibility is creating a new
 * {@link ClientConnectionController} to help with connecting to a server and
 * invoking {@link client.connection.ClientConnectionController#disconnect()} when the menuBar
 * disconnects.
 *
 * @author Robert Wells
 * @version 1.0
 *
 */
public class ClientControllerModel implements ClientMenuBarModel {

    private ClientConnectionController clientConnectionController;
    private GameController gameController;

    private boolean isConnected;

    /**
     * Sets {@link #isConnected} to false. THe menuBar is not connected on startup.
     *
     * @param gameController {@link GameController}, this is passed to the constructor of
     *                                             the {@link ClientConnectionController} in
     *                                             {@link #createClientController(ClientController)}
     */
    public ClientControllerModel(GameController gameController){
        this.gameController = gameController;
        isConnected = false;
    }

    /**
     * Creates a new {@link ClientConnectionController} that deals with connecting to a server.
     * This method is invoked when a menuBar clicks on the connect option in the file menu.
     *
     * @param clientController {@link ClientController}, this is passed to the constructor of
     *                                                 the ClientConnectionController.
     */
    public void createClientController(ClientController clientController){
        clientConnectionController =
                new ClientConnectionController(new ClientConnectionFrame(),
                        clientController, new ClientConnection(),
                        gameController);

    }


    /**
     * Invokes {@link client.connection.ClientConnectionController#disconnect()} when
     * the menuBar clicks on the disconnect button in the file menu.
     *
     */
    public void disconnectClientController(){
        clientConnectionController.disconnect();
    }



    /**
     * If true then menuBar is disconnected when program closed and is used
     * to determine whether to connect to a server or disconnect.
     *
     */
    public boolean isConnected(){
        return  isConnected;
    }

    public void setConnected(boolean connected){
        isConnected = connected;
    }

    public boolean isClientControllerNull(){
        return clientConnectionController == null;
    }

}
