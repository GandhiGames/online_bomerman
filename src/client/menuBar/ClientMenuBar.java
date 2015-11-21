package client.menuBar;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

/**
 * Class that acts as the window for {@link ClientController}.
 * This class  creates the file menu window for the menuBar
 *
 * @author Robert Wells
 * @version 1.0
 *
 */
public class ClientMenuBar extends JMenuBar implements ClientMenuBarView {

    private JMenuItem connect, exit;

    /**
     * Invokes {@link #createGUI()}.
     *
     */
    public ClientMenuBar(){
         createGUI();
    }

    /**
     * Invoked when object is created; creates the file menu for the menuBar and adds them
     * to itself.
     *
     */
    private void createGUI(){

        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        fileMenu.getAccessibleContext().setAccessibleDescription(
                "File Menu");


        connect = new JMenuItem("Connect To server");
        connect.setMnemonic(KeyEvent.VK_C);
        exit = new JMenuItem("Exit");
        exit.setMnemonic(KeyEvent.VK_A);


        fileMenu.add(connect);
        fileMenu.add(exit);

        add(fileMenu);

    }

    /**
     * Invoked by the {@link ClientController} to add an action listener to
     * {@link #connect}.
     *
     */
    public void addConnectActionListener(ActionListener actionListener){
        connect.addActionListener(actionListener);
    }

    /**
     * Invoked by the {@link ClientController} to add an action listener to
     * {@link #exit}.
     *
     */
    public void addExitActionListener(ActionListener actionListener){
        exit.addActionListener(actionListener);
    }


    /**
     * When a menuBar connects to a server the name of the menu item is changed to
     * disconnect.
     */
    public void setConnectMenuName(String text){
        connect.setText(text);
    }


}
