package server.session;

import java.awt.event.ActionListener;

/**
 * Provides an interface for the window component of the Session. Classes that conform to
 * this interface can act as the GUI/View for the session tab.
 *
 * @author Robert Wells
 * @version 1.0
 *
 */
public interface SessionView {

    public void addClearButtonActionListener(ActionListener actionListener);

    public void clearLog();

    public void setNumOfPlayersLabel(String text);

    public void appendSessionLog(String text);
}
