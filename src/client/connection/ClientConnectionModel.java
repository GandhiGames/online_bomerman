package client.connection;

import common.ServerShutDownException;

import java.io.IOException;

/**
 * Provides an interface for the model component of the ClientConnection. Classes that conform to
 * this interface can act as the model i.e. hold the data.
 *
 * @author Robert Wells
 * @version 1.0
 *
 */
public interface ClientConnectionModel {

    public void connectToServer(String address, int portNo);

    public void disconnectFromServer() throws IOException;

    public int[] getInputfromServer() throws IOException, ServerShutDownException;


}
