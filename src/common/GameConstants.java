package common;

/**
 * Holds a number of constants used for both the menuBar and the server.
 * These constants are used as a protocol during message passing so both menuBar
 * and server can process them correctly.
 *
 * @author Robert Wells
 * @version 1.0
 *
 */
public interface GameConstants {

    public static int PLAYER1 = -1;      // Indicate player 1
    public static int PLAYER2 = -2;      // Indicate player 2
    public static int DRAW = -3;         // Indicate a draw
    public static int CONTINUE = -4;     // Indicate to continue
    public static int KICKED = -5;
    public static int SERVERFULL = -6;
    public static int PLAYERLEFTSESSION = -7;
    public static int MAPSIZE = 11;
    public static int OTHERPLAYERREADY = -8;
    public static int NOBOMB = -9;
    public static int OTHERPLAYERNOTREADY = -10;
    public static int PLAYERWON = -11;
    public static int PLAYERHIT = -12;
    public static int PLAYERLIVES = 2;
    public static boolean PLAYAUDIO = false;
}