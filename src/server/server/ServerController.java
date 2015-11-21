package server.server;

import common.ServerShutDownException;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Date;

/**
 * Class that acts as the the controller for {@link Server} and {@link ServerPanel}.
 * Deals with creating sessions, and starting and stopping the server.
 *
 * @author Robert Wells
 * @version 1.0
 *
 */
public class ServerController {
    private ServerPanel serverPanel;
    private Server server;
    private int numOfSessions;

    /**
     * Invokes {@link #addListeners()} and {@link #initialSetup()}.
     *
     * @param server {@link Server} The model.
     * @param serverPanel {@link ServerPanel} The window.
     */
    public ServerController(Server server, ServerPanel serverPanel){
        this.server = server;
        this.serverPanel = serverPanel;

        addListeners();
        initialSetup();
    }

    /**
     * Sets {@link #numOfSessions} to 0 and and disables the stop button
     * on the server gui (as it isn't running at first launch).
     *
     */
    private void initialSetup(){
        numOfSessions = 0;
        serverPanel.setStopEnabled(false);
    }

    /**
     * Checks if server is running, if yes then stops server by invoking
     * {@link Server#stopServer()}.
     */
    public void cleanUp(){
        if(server.isRunning())
            server.stopServer();
    }

    /**
     * Adds {@link StartServerListener} to {@link ServerPanel#start},
     * {@link StopServerListener} to {@link ServerPanel#stop}, and
     * {@link ClearLogListener} to {@link ServerPanel#clear}. This Connects
     * the controler with the window.
     */
    private void addListeners(){
        serverPanel.addStartButtonActionListener(new StartServerListener());
        serverPanel.addStopButtonActionListener(new StopServerListener());
        serverPanel.addClearButtonActionListener(new ClearLogListener());
    }

    /**
     * Inner class that is that actionlistener for {@link ServerPanel#start}.
     *
     * @author Robert Wells
     * @version 1.0
     *
     */
    private class StartServerListener implements ActionListener, Runnable {

        /**
         * Starts the server.
         * Updates GUI Server Log with: "Server Started",
         * disables {@link ServerPanel#start} button as the server has started,
         * enables {@link ServerPanel#stop} button, and sets the boolean value
         * {@link Server#isRunning} to true. Lastly, starts a new thread, passing this object.
         *
         */
        public void actionPerformed(ActionEvent actionEvent) {

            serverPanel.appendServerLog(new Date() + ": Server Started");
            serverPanel.setStartEnabled(false);
            serverPanel.setStopEnabled(true);
            //server.setRunning(true);


            new Thread(this).start();

        }

        /**
         * Controls the creation of sessions and accepting and rejecting new players.
         * This thread runs while the server is running.
         *
         * First it starts the server by invoking {@link Server#start()},
         * the rest of the logic is shown in pseudocode below.
         *
         * <pre>
         * While(Server Running)
         * {
         *     if ((Server is not max sessions) and (Session 1 is full))
         *     {
         *        Create new Session ({@link Server#createSession()})
         *        and update GUI.
         *
         *        if(Server is max sessions)
         *        {
         *            Update GUI to inform administrator.
         *        }
         *     }
         *
         *     if(not all sessions full)
         *     {
         *         Connect player. {@link Server#connectPlayer()}
         *
         *         if(All sessions full)
         *         {
         *             Stop connecting players. {@link Server#stopConnectingPlayers()}
         *             Update GUI to inform administrator that server is full.
         *         }
         *     }
         * }
         *
         * </pre>
         *
         * Catches ServerShutDownException and sets {@link Server#isRunning} to false
         * and breaks from loop.
         * Catches IOException sets {@link Server#isRunning} to false
         * and breaks from loop.
         */
        public void run() {

            try {
                server.start();
            } catch (IOException e) {
                server.setRunning(false);
            }

            while (server.isRunning()) {

                try {
                    if(!server.isMaxSessions() && server.sessionFull(0)){
                        if (server.createSession()) {
                            serverPanel.appendServerLog(new Date() + ": Session "
                                    + (numOfSessions+1) + " Created");
                            numOfSessions++;

                            if(server.isMaxSessions()){
                                serverPanel.appendServerLog(new Date() +
                                        ": Max Number of Sessions Reached");
                            }
                        }
                    }

                    if(!server.isSessionsFull()) {

                        server.connectPlayer();

                        if(server.isSessionsFull()){
                            serverPanel.appendServerLog(new Date() +
                                    ": Max Number of Players Reached");
                        }
                    }

                    if(server.isSessionsFull()){
                        if(server.stopConnectingPlayers()) {
                            serverPanel.appendServerLog(new Date() + ": User Rejected");
                        }
                    }

                } catch (ServerShutDownException she){
                   // she.printStackTrace();
                    server.setRunning(false);
                    break;
                }catch (IOException e) {
                   // e.printStackTrace();
                    server.setRunning(false);
                    break;
                }
            }
        }
    }

    /**
     * Inner class that is the actionlistener for {@link ServerPanel#stop}.
     *
     * @author Robert Wells
     * @version 1.0
     *
     */
    private class StopServerListener implements ActionListener {

        /**
         * Stops the server. GUI updated with "Server Stopped",
         * {@link #numOfSessions} set to zero, {@link Server#stopServer()}
         * invoked, {@link ServerPanel#start} button enabled, and
         * {@link ServerPanel#stop} button disabled.
         *
         */
        public void actionPerformed(ActionEvent actionEvent) {
            serverPanel.appendServerLog(new Date() + ": Server Stopped");
            numOfSessions = 0;
            server.stopServer();
            serverPanel.setStartEnabled(true);
            serverPanel.setStopEnabled(false);
        }
    }

    /**
     * Inner class that is the actionlistener for {@link ServerPanel#clear}.
     *
     * @author Robert Wells
     * @version 1.0
     *
     */
    private class ClearLogListener implements ActionListener {

        /**
         * Clears session log by invoking {@link ServerPanel#clearLog()}.
         *
         */
        public void actionPerformed(ActionEvent actionEvent) {
            serverPanel.clearLog();
        }
    }

}
