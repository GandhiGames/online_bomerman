package client;

import javax.swing.*;

/**
 * Invokes {@link JOptionPane#showMessageDialog(java.awt.Component, Object)}
 * using a thread. Useful for when information needs to be displayed that should
 * not interrupt the flow of logic.
 *
 * @author Robert Wells
 * @version 1.0
 *
 */
public class MessageBox implements Runnable {

    private String message;

    public MessageBox(String message){
        this.message = message;
        new Thread(this).start();
    }

    public void run(){
        JOptionPane.showMessageDialog(null, message);
    }
}
