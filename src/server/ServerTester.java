package server;

/**
 * Run to test server.
 */

import server.window.ServerFrameView;
import server.window.ServerViewController;
import server.window.ServerFrame;

public class ServerTester {
    public static void main(String[] args) {
        new ServerViewController(new ServerFrameView(), new ServerFrame());

    }
}
