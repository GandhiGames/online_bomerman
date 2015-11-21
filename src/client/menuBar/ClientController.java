package client.menuBar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Class that acts as the controller for {@link ClientControllerModel} and {@link ClientMenuBar}.
 * This set of classes deals with creating objects to connect to a server
 * and disconnecting from the server from an option within
 * the file menu.
 *
 * @author Robert Wells
 * @version 1.0
 *
 */
public class ClientController {
    private ClientMenuBar clientControlView;
    private ClientControllerModel clientControllerModel;

    /**
     * Invokes {@link #addListeners()}.
     *
     * @param clientControllerModel {@link ClientControllerModel}, the model for the MVC.
     * @param clientControlView {@link ClientMenuBar}, the window for the MVC.
     */
    public ClientController(ClientMenuBar clientControlView,
                            ClientControllerModel clientControllerModel ){
        this.clientControlView = clientControlView;
        this.clientControllerModel = clientControllerModel;
        addListeners();
    }

    /**
     * Adds connect and exit actionlisteners from this class to {@link ClientMenuBar}.
     *
     */
    private void addListeners(){
        clientControlView.addConnectActionListener(new ConnectMenuListener());
        clientControlView.addExitActionListener(new ExitMenuListener());

    }

    /**
     * Changes connect menu text to "Connect To Server". This is performed when the menuBar
     * is not connected to the server.
     *
     */
    public void setConnectMenuName(){
        clientControlView.setConnectMenuName("Connect To server");
    }

    /**
     * Changes connect menu text to "Disconnect From Server". This is performed when the menuBar
     * is connected to the server and therefore wans to disconnect.
     *
     */
    public void setDisconnectMenuName(){
        clientControlView.setConnectMenuName("Disconnect From server");
    }

    /**
     * Invokes {@link ClientControllerModel#setConnected(boolean)}.
     *
     */
    public void setConnected(boolean connected){
        clientControllerModel.setConnected(connected);
    }


    public boolean isConnected(){
        return clientControllerModel.isConnected();
    }

    /**
     * If {@link ClientControllerModel#isConnected} is true then
     * {@link client.menuBar.ClientControllerModel#disconnectClientController()} is invoked.
     *
     */
    public void cleanUp(){
        if(isConnected()){
            clientControllerModel.disconnectClientController();
        }
    }

    /**
     * Class that acts as the actionlistener for the connect button in the clients file menu.
     * If {@link ClientControllerModel#isConnected} is true when the button is pressed then the
     * menuBar disconnects, if it is false then {@link ClientControllerModel#createClientController(ClientController)}
     * is invoked and a new {@link client.connection.ClientConnectionController} is created.
     *
     * @author Robert Wells
     * @version 1.0
     *
     */
    private class ConnectMenuListener implements ActionListener {

        public void actionPerformed(ActionEvent actionEvent) {
            if(!isConnected()) {
                clientControllerModel.createClientController(ClientController.this);

            } else {
                if(!clientControllerModel.isClientControllerNull()){
                    clientControllerModel.disconnectClientController();
                }
            }
        }
    }

    /**
     * Class that acts as the actionlistener for the exit button in the clients file menu. If
     * {@link #isConnected()} is true {@link client.menuBar.ClientControllerModel#disconnectClientController()}
     * is invoked and the system exits.
     *
     * @author Robert Wells
     * @version 1.0
     *
     */
    private class ExitMenuListener implements ActionListener {

        public void actionPerformed(ActionEvent actionEvent) {
            if (isConnected())
                clientControllerModel.disconnectClientController();

            System.exit(0);
        }
    }

}
