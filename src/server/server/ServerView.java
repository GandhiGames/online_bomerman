package server.server;

import java.awt.event.ActionListener;

/**
 * Provides an interface for the window component of the Server. Classes that conform to
 * this interface can act as the GUI/View for the session tabs.
 *
 * @author Robert Wells
 * @version 1.0
 *
 */
public interface ServerView {

    public void addStartButtonActionListener(ActionListener actionListener);

    public void addStopButtonActionListener(ActionListener actionListener);

    public void addClearButtonActionListener(ActionListener actionListener);

    public void appendServerLog(String text);

    public void clearLog();
}
