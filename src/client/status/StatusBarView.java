package client.status;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;

/**
 * Creates and updates the status bar at the bottom of a players screen. This can contain information
 * on players lives or a "waiting status" that informs the player that they are waiting for
 * another player to connect.
 *
 * @author Robert Wells
 * @version 1.0
 *
 */
public class StatusBarView extends JPanel {
    private JLabel firstPlayerStatusText;
    private JLabel secondPlayerStatusText;

    /**
     * Calls {@link #createGUI()}.
     *
     */
    public StatusBarView(){
        createGUI();
    }

    /**
     * Initialises {@link #firstPlayerStatusText} and {@link #secondPlayerStatusText}
     * and adds them to the panel.
     *
     */
    private void createGUI(){
        firstPlayerStatusText = new JLabel();
        secondPlayerStatusText = new JLabel();

        Font myFont = firstPlayerStatusText.getFont();
        Font boldFont = new Font(myFont.getName(), Font.BOLD, 14);

        firstPlayerStatusText.setFont(boldFont);
        secondPlayerStatusText.setFont(boldFont);

        setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));

        firstPlayerStatusText.setForeground(Color.BLACK);
        secondPlayerStatusText.setForeground(Color.BLACK);

        setLayout(new GridLayout(1,1,0,0));
        add(firstPlayerStatusText);
        add(secondPlayerStatusText);

    }

    public void setFirstPlayerStatusText(String newStatus){
        firstPlayerStatusText.setText(newStatus);

    }
    public void setSecondPlayerStatusText(String newStatus){
        secondPlayerStatusText.setText(newStatus);
    }

    public void clearText(){
        firstPlayerStatusText.setText("");
        secondPlayerStatusText.setText("");
    }

}
