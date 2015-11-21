package client.connection;

import client.MessageBox;
import common.GameConstants;
import common.ServerShutDownException;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Class that acts as the model for {@link ClientConnectionController}.
 * This class holds the data to be operated on. Its main responsibility is performing the
 * initial connection to the server and sending disconnect messages to the server.
 *
 * @author Robert Wells
 * @version 1.0
 *
 */
public class ClientConnection implements ClientConnectionModel, GameConstants {
    private Socket socket;
    private DataInputStream fromServer;
    private DataOutputStream toServer;
    private boolean connected;

    /**
     * Set {@link #connected} to false as not connected when menuBar runs.
     */
    public ClientConnection(){
        socket = null;
        connected = false;
    }

    /**
     * Creates new sockets based on method parameters and sets
     * {@link #connected} to true.
     *
     * @param address the IP address of the server.
     * @param portNo the Port number of the server.
     *
     */
    public void connectToServer(String address, int portNo){

        try{
            socket = new Socket(address, portNo);
        } catch (ConnectException ce) {
            new MessageBox("Server Not Found");

        } catch (UnknownHostException uhe) {
            new MessageBox("Unknown Host");

        } catch (IOException ignored){
            //Program logic deals with this.
        }

        connected = true;
    }


    /**
     * Informs server that player has left by writing {@link GameConstants#PLAYERLEFTSESSION}
     * to {@link #toServer}.
     *
     */
    public void disconnectFromServer() throws IOException {

         if (toServer == null)
            toServer = new DataOutputStream(socket.getOutputStream());

        toServer.writeInt(PLAYERLEFTSESSION);
        toServer.writeInt(PLAYERLEFTSESSION);
        toServer.writeInt(PLAYERLEFTSESSION);
        toServer.writeInt(PLAYERLEFTSESSION);
        toServer.flush();

    }

    /**
     * Used to get first input from server and initialises {@link #fromServer} if it equals null.
     *
     * @throws ServerShutDownException if data from server reads {@link GameConstants#KICKED}.
     *
     */
    public int[] getInputfromServer() throws IOException, ServerShutDownException {

        if (fromServer == null)
            fromServer = new DataInputStream(socket.getInputStream());

        int read[] = new int[4];

        read[0] = fromServer.readInt();
        read[1] = fromServer.readInt();
        read[2] = fromServer.readInt();
        read[3] = fromServer.readInt();

        if(read[0] == KICKED) {
            throw new ServerShutDownException();
        }

        return read;
    }



    public void setSocketToNull() throws IOException{
        socket = null;
    }

    public boolean isSocketNull(){
        return socket == null;
    }

    public Socket getSocket(){
        return socket;
    }

    public boolean isConnected(){
        return connected;
    }


}

