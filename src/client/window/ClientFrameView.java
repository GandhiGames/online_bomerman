package client.window;

import client.menuBar.ClientMenuBar;
import client.game.GamePanel;
import client.status.StatusBarView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;

/**
 * Class that acts as the window for {@link ClientFrameController}.
 * This class creates the main window for the player that contains
 * {@link client.menuBar.ClientMenuBar}, {@link client.game.GamePanel}, and {@link StatusBarView}.
 *
 * @author Robert Wells
 * @version 1.0
 *
 */
public class ClientFrameView extends JFrame implements ClientView {

    /**
     * Sets title and size of main window.
     *
     */
    public ClientFrameView() {
        setTitle("Bomberman");
        setSize(750, 550);
    }

    /**
     * Adds {@link client.menuBar.ClientMenuBar} as a menubar, and {@link client.game.GamePanel} and {@link StatusBarView}
     * as panels to the frame.
     *
     */
    public void createGUI(ClientMenuBar clientControlView, GamePanel gameView,
                          StatusBarView statusBarView){

        setJMenuBar(clientControlView);
        add(gameView, BorderLayout.CENTER);
        add(statusBarView, BorderLayout.PAGE_END);

        //setLayout(new BorderLayout());

        setLocationRelativeTo(null);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setVisible(true);

    }

    /**
     * Invoked by the {@link ClientFrameController} to add a window adapter to
     * deal with closing the window gracefully.
     *
     */
    public void addWindowAdapter(WindowAdapter windowAdapter){
        addWindowListener(windowAdapter);
    }




}
