package server.window;

import server.server.ServerPanel;
import server.session.SessionController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;

/**
 * Class that acts as the window for the man GUI. Data contained {@link ServerFrame}.
 * The Server View provides the main GUI server-side. Controlled by {@link ServerViewController}.
 *
 * @author Robert Wells
 * @version 1.0
 *
 */
public class ServerFrameView extends JFrame implements ServerView {

    private JTabbedPane pane;

    /**
     * Sets title and size of window.
     * Sets default close operation to DO_NOTHING_ON_CLOSE
     * The close behaviour is handled in {@link server.window.ServerViewController.WindowCloser}
     *
     */
    public ServerFrameView(){
        setTitle("Bomberman Server");
        setSize(400, 450);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

    }

    /**
     * Adds Server Overview to {@link #pane} (the first tab of the GUI)
     * and adds pane to frame.
     *
     * @param serverPanel {@link server.server.ServerPanel}
     *
     */
    public void addTab(ServerPanel serverPanel){
        pane.addTab("Overview", serverPanel);
        pane.setMnemonicAt(0, KeyEvent.VK_1);
        add(pane);

    }

    /**
     * Initialises JtabbedPane {@link #pane}
     * Sets layout to GridLayout and position to middle of screen
     * Sets frames visibility to true
     *
     */
    public void createGUI(){
        pane = new JTabbedPane();
        setLayout(new GridLayout(1, 1));
        setLocationRelativeTo(null);
        setVisible(true);

    }

    /**
     * Adds Session tab to {@link #pane}
     * Invokes {@link server.session.SessionController#getSessionOverview()}
     * which provides the GUI for the session tab
     *
     * @param sessionController {@link SessionController}
     * @param gameSessionSize used to output the game session number to the GUI
     * @see server.session.SessionPanel
     *
     */
    public void addGameSession(SessionController sessionController,
                               int gameSessionSize) {
        pane.addTab("Session "
                + gameSessionSize, sessionController.getSessionOverview());
    }

    /**
     * Removes game tabs from {@link #pane} (and consequently the GUI)
     *
     */
    public void removeGameSessions(){
        int numOfPanes = pane.getTabCount();

        for (int i = 1; i < numOfPanes; i++){
            pane.remove(1);
        }
    }

    public void addWindowCloser(WindowAdapter windowAdapter){
        addWindowListener(windowAdapter);
    }







}