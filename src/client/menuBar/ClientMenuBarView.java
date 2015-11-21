package client.menuBar;

import java.awt.event.ActionListener;


/**
 * Provides an interface for the window component of the ClientMenuBar. Classes that conform to
 * this interface can act as the GUI.
 *
 * @author Robert Wells
 * @version 1.0
 *
 */
public interface ClientMenuBarView {

    public void addConnectActionListener(ActionListener actionListener);

    public void addExitActionListener(ActionListener actionListener);

    public void setConnectMenuName(String text);
}
