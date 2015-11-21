package server.session;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Creates and maintains the session GUI.Controlled by {@link SessionController}.
 *
 * @author Robert Wells
 * @version 1.0
 *
 */
public class SessionPanel extends JPanel implements SessionView {

    private JTextArea sessionLog;
    private JButton clear;
    private JLabel numOfPlayers;

    /**
     * Invokes {@link #createGUI()}
     *
     */
    public SessionPanel(){
        createGUI();
    }

    /**
     * Creates session GUI.
     * Initialises {@link #sessionLog},  {@link #clear},
     * {@link #numOfPlayers}. Creates two panels "Game Status" containing
     * the sessionLog and "Control Panel" containing the start and stop buttons,
     * and numOfPlayers label. There are added to the frame.
     *
     */
    private void createGUI(){
        sessionLog = new JTextArea(14,22);
        sessionLog.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(sessionLog);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        clear = new JButton("Clear");

        numOfPlayers = new JLabel();
        setNumOfPlayersLabel("" + 0);

        JPanel logPanel = new JPanel();
        TitledBorder textPanelTitle
                = BorderFactory.createTitledBorder("Game Status");
        logPanel.setBorder(textPanelTitle);
        logPanel.add(scrollPane);
        logPanel.setLayout(new GridLayout(1,1,0,0));

        JPanel numOfPlayersPanel = new JPanel();
        numOfPlayersPanel.add(numOfPlayers);
        numOfPlayersPanel.setLayout(new GridLayout(1,1,5,5));


        JPanel controlPanel = new JPanel();
        TitledBorder controlPanelTitle
                = BorderFactory.createTitledBorder("Control Panel");
        controlPanel.setBorder(controlPanelTitle);
        controlPanel.add(numOfPlayersPanel);
        controlPanel.add(clear);
        controlPanel.setLayout(new GridLayout(2,1,2,2));

        setLayout(new GridLayout(2,1,1,1));
        add(logPanel);
        add(controlPanel);
    }

    /**
     *  Clears {@link #sessionLog}
     */
    public void clearLog(){
        sessionLog.setText("");
    }


    public void addClearButtonActionListener(ActionListener actionListener){
        clear.addActionListener(actionListener);
    }

    public void setNumOfPlayersLabel(String text){
        numOfPlayers.setText("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t" +
                "Number of players: " + text);
    }

    public void appendSessionLog(String text){
        sessionLog.append(text + '\n');
    }






}
