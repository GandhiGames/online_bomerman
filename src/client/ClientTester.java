package client;

import client.menuBar.ClientMenuBar;
import client.window.ClientFrame;
import client.window.ClientFrameController;
import client.window.ClientFrameView;

/**
 * Run to test client.
 */
public class ClientTester {

    public static void main(String[] args) {
        ClientMenuBar clientControlView = new ClientMenuBar();
        new ClientFrameController(new ClientFrameView(),
                new ClientFrame(clientControlView), clientControlView);
    }
}
