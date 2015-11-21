package client.connection;

import java.awt.event.ActionListener;

/**
 * Provides an interface for the window component of the ClientConnection. Classes that conform to
 * this interface can act as the GUI/View.
 *
 * @author Robert Wells
 * @version 1.0
 *
 */
public interface ClientConnectionView {

    public void addConnectButtonListener(ActionListener actionListener);

    public void addCancelButtonListener(ActionListener actionListener);


}
