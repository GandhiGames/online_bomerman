package client.window;

import client.menuBar.ClientMenuBar;
import client.menuBar.ClientController;
import client.menuBar.ClientControllerModel;
import client.game.Audio;
import client.game.Game;
import client.game.GameController;
import client.game.GamePanel;
import client.status.StatusBarView;

/**
 * Class that acts as the model for {@link ClientFrameController}.
 * This class holds the data to be operated on. Its main responsibility is creating
 * the initial controllers.
 *
 * @author Robert Wells
 * @version 1.0
 *
 */
public class ClientFrame implements ClientFrameModel {
    private ClientController clientController;
    private StatusBarView statusBarView;
    private GamePanel gameView;

    /**
     * Creates and initialises initial objects including {@link StatusBarView},
     * {@link GameController}, and {@link ClientController}.
     *
     */
    public ClientFrame(ClientMenuBar clientControlView){
        statusBarView = new StatusBarView();

        gameView = new GamePanel();
        Audio audio = new Audio();
        GameController gameController = new GameController(gameView, new Game(audio), statusBarView, audio);


        ClientControllerModel clientControllerModel = new ClientControllerModel(gameController);
        clientController = new ClientController(clientControlView, clientControllerModel);


        gameController.setClientController(clientController);
    }

    /**
     * Calls {@link client.menuBar.ClientController#cleanUp()}
     *
     */
    public void cleanUp(){
        clientController.cleanUp();
    }

    public GamePanel getGameView(){
        return gameView;
    }

    public StatusBarView getStatusBarView(){
        return statusBarView;
    }
}
