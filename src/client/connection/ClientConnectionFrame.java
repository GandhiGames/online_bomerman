package client.connection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Class that acts as the window for {@link ClientConnectionController}.
 * This class creates the connection menu that is shown when a user clicks
 * "Connect to server" from the file menu.
 *
 * @author Robert Wells
 * @version 1.0
 *
 */
public class ClientConnectionFrame extends JFrame implements ClientConnectionView {
    private JTextField ipField, portField;
    private JButton connect, cancel;

    /**
     * Creates window. Sets title to "Connection Settings", sets size, centers to
     * middle of screen, invokes {@link #createGUI()}, and the sets visible to true.
     *
     */
    public ClientConnectionFrame() {
        setTitle("Connection Settings");
        setSize(300, 200);
        setLocationRelativeTo(null);
        createGUI();
        setVisible(true);

    }

    /**
     * Initialises Jtextfields used for ip address and port number,
     * {@link #connect} and {@link #cancel} buttons, and adds them
     * to itself.
     *
     */
    private void createGUI() {
        ipField = new JTextField();
        ipField.setText("127.0.0.1");

        portField = new JTextField();
        portField.setText("3012");

        connect = new JButton("Connect");
        cancel = new JButton("Cancel");

        JPanel playerDataPanel = new JPanel(new GridLayout(2,2,1,1));
        playerDataPanel.add(new JLabel("\tIP Address:"));
        playerDataPanel.add(new JLabel("\tPort Number:"));
        playerDataPanel.add(ipField);
        playerDataPanel.add(portField);

        JPanel buttonPanel = new JPanel(new GridLayout(1,2,1,1));
        buttonPanel.add(connect);
        buttonPanel.add(cancel);

        setLayout(new GridLayout(2,2,2,2));
        add(playerDataPanel);
        add(buttonPanel);


    }

    /**
     * Invoked by the {@link ClientConnectionController} to add an action listener to
     * {@link #connect}.
     *
     */
    public void addConnectButtonListener(ActionListener actionListener){
        connect.addActionListener(actionListener);
    }

    /**
     * Invoked by the {@link ClientConnectionController} to add an action listener to
     * {@link #cancel}.
     *
     */
    public void addCancelButtonListener(ActionListener actionListener){
        cancel.addActionListener(actionListener);
    }

    /**
     *
     * @return string contained within {@link #ipField}.
     *
     */
    public String getIPAddress(){
        return ipField.getText();
    }

    /**
     *
     * @return int contained within {@link #portField}.
     *
     */
    public int getPort(){
        return Integer.parseInt(portField.getText());
    }

    public void closeWindow(){
        this.dispose();
    }




}
