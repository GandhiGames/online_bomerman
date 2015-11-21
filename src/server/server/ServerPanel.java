package server.server;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Creates the first server tab in the GUI. Controlled by {@link ServerController}.
 *
 * @author Robert Wells
 * @version 1.0
 *
 */
public class ServerPanel extends JPanel implements ServerView {

    private JTextArea serverLog;
    private JButton start, stop, clear;

    /**
     * Invokes {@link #createGUI()}.
     *
     */
    public ServerPanel() {
        createGUI();
    }

    /**
     * Creates the GUI for the server overview tab.
     * Initialises {@link #serverLog} and adds it to a panel.
     * Initialises {@link #start}, {@link #stop}, and {@link #clear} and adds them to a panel.
     * The panels are then added to the frame.
     *
     */
    private void createGUI(){
        serverLog = new JTextArea(8,22);
        serverLog.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(serverLog);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        start = new JButton("Start");
        stop = new JButton("Stop");
        clear = new JButton("Clear");

        JPanel textPanel = new JPanel();
        TitledBorder textPanelTitle
                = BorderFactory.createTitledBorder("Status");
        textPanel.setBorder(textPanelTitle);
        textPanel.add(scrollPane);
        textPanel.setLayout(new GridLayout(1,1,0,0));

        JPanel controlPanel = new JPanel();
        TitledBorder controlPanelTitle
                = BorderFactory.createTitledBorder("Control Panel");
        controlPanel.setBorder(controlPanelTitle);
        controlPanel.add(start);
        controlPanel.add(stop);
        controlPanel.add(clear);
        controlPanel.setLayout(new GridLayout(1,3,2,2));

        setLayout(new GridLayout(2,1,1,1));
        add(textPanel);
        add(controlPanel);

    }

    public void addStartButtonActionListener(ActionListener actionListener){
        start.addActionListener(actionListener);
    }

    public void addStopButtonActionListener(ActionListener actionListener){
        stop.addActionListener(actionListener);
    }

    public void addClearButtonActionListener(ActionListener actionListener){
        clear.addActionListener(actionListener);
    }

    public void appendServerLog(String text){
       serverLog.append(text + '\n');
    }

    public void setStartEnabled(boolean enabled){
        start.setEnabled(enabled);
    }

    public void setStopEnabled(boolean enabled){
        stop.setEnabled(enabled);
    }

    public void clearLog(){
        serverLog.setText("");
    }
}
