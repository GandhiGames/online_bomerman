package client.menuBar;

/**
 * Provides an interface for the model component of the ClientMenuBar. Classes that conform to
 * this interface can act as the model.
 *
 * @author Robert Wells
 * @version 1.0
 *
 */
public interface ClientMenuBarModel {

    public void createClientController(ClientController clientController);

    public void disconnectClientController();

    public boolean isConnected();

    public void setConnected(boolean connected);
}
