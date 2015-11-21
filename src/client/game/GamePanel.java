package client.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;

import common.GameConstants;

/**
 * Class that acts as the window for {@link GameController}.
 * This class creates the main window for the player. This includes the initial screen
 * with the bomberman logo and the game screen. It also runs the game over animation.
 *
 * @author Robert Wells
 * @version 1.0
 *
 */
public class GamePanel extends JPanel implements GameView, GameConstants {

    private Cell[][] cells = new Cell[MAPSIZE][MAPSIZE];
    private Cell startingCell;
    private Image image;

    /**
     * Calls {@link #createGUI()},
     *
     */
    public GamePanel(){
        image = new Image();
        createGUI();

    }

    /**
     * Creates the initial game screen seen by clients and {@link #setFocusable(boolean)}
     * to true so that keyboard input is registered. Initialises {@link #startingCell}
     * and adds them to the screen and calls {@link #showStartScreen()}.
     *
     */
    private void createGUI(){
        setFocusable(true);

        setLayout(new GridLayout(1, 1, 0, 0));


        add(startingCell = new Cell(0, 0, image));


        showStartScreen();

    }

    /**
     * Creates the game environment. Removes {@link #startingCell} and initialises
     * {@link #cells} and adds to screen. Calls {@link #setPermanentTerrain()} and
     * {@link #setTerrain()}.
     *
     */
    public void createEnvironment(){

        remove(startingCell);


        for (int i = 0; i < cells.length; i++)
            for (int j = 0; j < cells.length; j++) {
                //   cells[i][j].clearCell();

                if (cells [i][j] != null)
                    remove(cells[i][j]);


            }


        for (int i = 0; i < cells.length; i++)
            for (int j = 0; j < cells.length; j++) {
                add(cells[i][j] = new Cell(i, j, image));
            }

        setPermanentTerrain();
        setTerrain();

        setLayout(new GridLayout(cells.length, cells.length, 0, 0));

        repaint();
    }

    /**
     * Adds permanent terrain to screen.
     *
     */
    private void setPermanentTerrain(){
        cells[1][1].setImageName(ImageName.PERMTERRAIN);
        cells[3][1].setImageName(ImageName.PERMTERRAIN);
        cells[5][1].setImageName(ImageName.PERMTERRAIN);
        cells[7][1].setImageName(ImageName.PERMTERRAIN);
        cells[9][1].setImageName(ImageName.PERMTERRAIN);

        cells[1][3].setImageName(ImageName.PERMTERRAIN);
        cells[3][3].setImageName(ImageName.PERMTERRAIN);
        cells[5][3].setImageName(ImageName.PERMTERRAIN);
        cells[7][3].setImageName(ImageName.PERMTERRAIN);
        cells[9][3].setImageName(ImageName.PERMTERRAIN);

        cells[1][5].setImageName(ImageName.PERMTERRAIN);
        cells[3][5].setImageName(ImageName.PERMTERRAIN);
        cells[5][5].setImageName(ImageName.PERMTERRAIN);
        cells[7][5].setImageName(ImageName.PERMTERRAIN);
        cells[9][5].setImageName(ImageName.PERMTERRAIN);

        cells[1][7].setImageName(ImageName.PERMTERRAIN);
        cells[3][7].setImageName(ImageName.PERMTERRAIN);
        cells[5][7].setImageName(ImageName.PERMTERRAIN);
        cells[7][7].setImageName(ImageName.PERMTERRAIN);
        cells[9][7].setImageName(ImageName.PERMTERRAIN);

        cells[1][9].setImageName(ImageName.PERMTERRAIN);
        cells[3][9].setImageName(ImageName.PERMTERRAIN);
        cells[5][9].setImageName(ImageName.PERMTERRAIN);
        cells[7][9].setImageName(ImageName.PERMTERRAIN);
        cells[9][9].setImageName(ImageName.PERMTERRAIN);


    }

    /**
     * Adds destroyable terrain to screen.
     *
     */
    private void setTerrain(){
        cells[0][2].setImageName(ImageName.TERRAIN);
        cells[0][3].setImageName(ImageName.TERRAIN);
        cells[0][4].setImageName(ImageName.TERRAIN);
        cells[0][5].setImageName(ImageName.TERRAIN);
        cells[0][7].setImageName(ImageName.TERRAIN);

        cells[1][2].setImageName(ImageName.TERRAIN);
        cells[1][4].setImageName(ImageName.TERRAIN);

        cells[2][3].setImageName(ImageName.TERRAIN);
        cells[2][4].setImageName(ImageName.TERRAIN);
        cells[2][5].setImageName(ImageName.TERRAIN);
        cells[2][6].setImageName(ImageName.TERRAIN);
        cells[2][7].setImageName(ImageName.TERRAIN);
        cells[2][8].setImageName(ImageName.TERRAIN);

        cells[3][0].setImageName(ImageName.TERRAIN);
        cells[3][2].setImageName(ImageName.TERRAIN);
        cells[3][4].setImageName(ImageName.TERRAIN);
        cells[3][8].setImageName(ImageName.TERRAIN);
        cells[3][10].setImageName(ImageName.TERRAIN);

        cells[4][2].setImageName(ImageName.TERRAIN);
        cells[4][4].setImageName(ImageName.TERRAIN);
        cells[4][5].setImageName(ImageName.TERRAIN);
        cells[4][6].setImageName(ImageName.TERRAIN);
        cells[4][7].setImageName(ImageName.TERRAIN);
        cells[4][8].setImageName(ImageName.TERRAIN);

        cells[5][0].setImageName(ImageName.TERRAIN);
        cells[5][2].setImageName(ImageName.TERRAIN);
        cells[5][8].setImageName(ImageName.TERRAIN);
        cells[5][10].setImageName(ImageName.TERRAIN);

        cells[6][2].setImageName(ImageName.TERRAIN);
        cells[6][3].setImageName(ImageName.TERRAIN);
        cells[6][5].setImageName(ImageName.TERRAIN);
        cells[6][8].setImageName(ImageName.TERRAIN);
        cells[6][9].setImageName(ImageName.TERRAIN);
        cells[6][10].setImageName(ImageName.TERRAIN);

        cells[7][0].setImageName(ImageName.TERRAIN);
        cells[7][2].setImageName(ImageName.TERRAIN);
        cells[7][4].setImageName(ImageName.TERRAIN);
        cells[7][6].setImageName(ImageName.TERRAIN);
        cells[7][8].setImageName(ImageName.TERRAIN);
        cells[7][10].setImageName(ImageName.TERRAIN);

        cells[8][2].setImageName(ImageName.TERRAIN);
        cells[8][3].setImageName(ImageName.TERRAIN);
        cells[8][4].setImageName(ImageName.TERRAIN);
        cells[8][5].setImageName(ImageName.TERRAIN);
        cells[8][6].setImageName(ImageName.TERRAIN);
        cells[8][7].setImageName(ImageName.TERRAIN);

        cells[9][6].setImageName(ImageName.TERRAIN);
        cells[9][10].setImageName(ImageName.TERRAIN);

        cells[10][3].setImageName(ImageName.TERRAIN);
        cells[10][5].setImageName(ImageName.TERRAIN);
        cells[10][6].setImageName(ImageName.TERRAIN);
        cells[10][7].setImageName(ImageName.TERRAIN);


    }


    /**
     * Runs the animation when the win condition has been met.
     * {@link #cells} is updated with {@link ImageName#ANIMATIONBLOCK} in a spiral pattern.
     *
     */
    public void runEndingAnimation(){

        //top right to bottom right
        int ANIMATIONSLEEP = 15;

        for (int i = 0; i < GameConstants.MAPSIZE; i++) {
            cells[i][GameConstants.MAPSIZE-1].setImageName(ImageName.ANIMATIONBLOCK);

            try{
                Thread.sleep(ANIMATIONSLEEP);
            } catch (InterruptedException ie){
                ie.printStackTrace();
            }
        }

        //bottom right to bottom left
        for (int i = GameConstants.MAPSIZE-2; i > -1; i--) {
            cells[GameConstants.MAPSIZE-1][i].setImageName(ImageName.ANIMATIONBLOCK);

            try{
                Thread.sleep(ANIMATIONSLEEP);
            } catch (InterruptedException ie){
                ie.printStackTrace();
            }
        }

        //bottom left to top left
        for (int i = GameConstants.MAPSIZE-1; i > -1; i--) {
            cells[i][0].setImageName(ImageName.ANIMATIONBLOCK);

            try{
                Thread.sleep(ANIMATIONSLEEP);
            } catch (InterruptedException ie){
                ie.printStackTrace();
            }
        }

        //top left to top right
        for (int i = 1; i < GameConstants.MAPSIZE; i++) {
            cells[0][i].setImageName(ImageName.ANIMATIONBLOCK);

            try{
                Thread.sleep(ANIMATIONSLEEP);
            } catch (InterruptedException ie){
                ie.printStackTrace();
            }
        }

        for (int i = 0; i < GameConstants.MAPSIZE-1; i++) {
            cells[i][GameConstants.MAPSIZE-2].setImageName(ImageName.ANIMATIONBLOCK);

            try{
                Thread.sleep(ANIMATIONSLEEP);
            } catch (InterruptedException ie){
                ie.printStackTrace();
            }
        }

        for (int i = GameConstants.MAPSIZE-3; i > -1; i--) {
            cells[GameConstants.MAPSIZE-2][i].setImageName(ImageName.ANIMATIONBLOCK);

            try{
                Thread.sleep(ANIMATIONSLEEP);
            } catch (InterruptedException ie){
                ie.printStackTrace();
            }
        }

        for (int i = GameConstants.MAPSIZE-2; i > -1; i--) {
            cells[i][1].setImageName(ImageName.ANIMATIONBLOCK);

            try{
                Thread.sleep(ANIMATIONSLEEP);
            } catch (InterruptedException ie){
                ie.printStackTrace();
            }
        }

        for (int i = 1; i < GameConstants.MAPSIZE-1; i++) {
            cells[1][i].setImageName(ImageName.ANIMATIONBLOCK);

            try{
                Thread.sleep(ANIMATIONSLEEP);
            } catch (InterruptedException ie){
                ie.printStackTrace();
            }
        }

        for (int i = 1; i < GameConstants.MAPSIZE-1; i++) {
            cells[i][GameConstants.MAPSIZE-3].setImageName(ImageName.ANIMATIONBLOCK);

            try{
                Thread.sleep(ANIMATIONSLEEP);
            } catch (InterruptedException ie){
                ie.printStackTrace();
            }
        }

        for (int i = GameConstants.MAPSIZE-4; i > 1; i--) {
            cells[GameConstants.MAPSIZE-3][i].setImageName(ImageName.ANIMATIONBLOCK);

            try{
                Thread.sleep(ANIMATIONSLEEP);
            } catch (InterruptedException ie){
                ie.printStackTrace();
            }
        }

        for (int i = GameConstants.MAPSIZE-3; i > 1; i--) {
            cells[i][2].setImageName(ImageName.ANIMATIONBLOCK);

            try{
                Thread.sleep(ANIMATIONSLEEP);
            } catch (InterruptedException ie){
                ie.printStackTrace();
            }
        }

        for (int i = 3; i < GameConstants.MAPSIZE-2; i++) {
            cells[2][i].setImageName(ImageName.ANIMATIONBLOCK);

            try{
                Thread.sleep(ANIMATIONSLEEP);
            } catch (InterruptedException ie){
                ie.printStackTrace();
            }
        }

        for (int i = 2; i < GameConstants.MAPSIZE-3; i++) {
            cells[i][GameConstants.MAPSIZE-4].setImageName(ImageName.ANIMATIONBLOCK);

            try{
                Thread.sleep(ANIMATIONSLEEP);
            } catch (InterruptedException ie){
                ie.printStackTrace();
            }
        }

        for (int i = GameConstants.MAPSIZE-3; i > 2; i--) {
            cells[GameConstants.MAPSIZE-4][i].setImageName(ImageName.ANIMATIONBLOCK);

            try{
                Thread.sleep(ANIMATIONSLEEP);
            } catch (InterruptedException ie){
                ie.printStackTrace();
            }
        }

        for (int i = GameConstants.MAPSIZE-3; i > 2; i--) {
            cells[i][3].setImageName(ImageName.ANIMATIONBLOCK);

            try{
                Thread.sleep(ANIMATIONSLEEP);
            } catch (InterruptedException ie){
                ie.printStackTrace();
            }
        }

        for (int i = 3; i < GameConstants.MAPSIZE-4; i++) {
            cells[3][i].setImageName(ImageName.ANIMATIONBLOCK);

            try{
                Thread.sleep(ANIMATIONSLEEP);
            } catch (InterruptedException ie){
                ie.printStackTrace();
            }
        }

        for (int i = 4; i < GameConstants.MAPSIZE-4; i++) {
            cells[i][GameConstants.MAPSIZE-5].setImageName(ImageName.ANIMATIONBLOCK);

            try{
                Thread.sleep(ANIMATIONSLEEP);
            } catch (InterruptedException ie){
                ie.printStackTrace();
            }
        }

        for (int i = GameConstants.MAPSIZE-5; i > 3; i--) {
            cells[GameConstants.MAPSIZE-5][i].setImageName(ImageName.ANIMATIONBLOCK);

            try{
                Thread.sleep(ANIMATIONSLEEP);
            } catch (InterruptedException ie){
                ie.printStackTrace();
            }
        }

        //bottom left to top left
        for (int i = GameConstants.MAPSIZE-6; i > 3; i--) {
            cells[i][4].setImageName(ImageName.ANIMATIONBLOCK);

            try{
                Thread.sleep(ANIMATIONSLEEP);
            } catch (InterruptedException ie){
                ie.printStackTrace();
            }
        }

        cells[4][5].setImageName(ImageName.ANIMATIONBLOCK);

        try{
            Thread.sleep(ANIMATIONSLEEP);
        } catch (InterruptedException ie){
            ie.printStackTrace();
        }

        cells[5][5].setImageName(ImageName.ANIMATIONBLOCK);
    }

    /**
     * Updates {@link #startingCell} with the images for the logo.
     *
     */
    public void showStartScreen(){
        startingCell.setImageName(ImageName.TITLE);

    }


    /**
     * Removes {@link #cells} from the screen and calls
     * {@link #createGUI()}. This resets the environment to the
     * starting screen.
     *
     */
    public void resetEnvironment(){
        if(cells != null){


            for (int i = 0; i < cells.length; i++)
                for (int j = 0; j < cells.length; j++) {
                    remove(cells[i][j]);
                }

        }

        createGUI();

        //repaint();
    }


    /**
     * Invoked by the {@link GameController} to add a key listener to
     * control the players movement.
     *
     */
    public void addInputListener(KeyListener keyListener){
        addKeyListener(keyListener);
    }

    /**
     * Invoked by the {@link GameController} to remove a key listener. This
     * prevents the player from being moved when it is not appropriate i.e.
     * the game has not been started.
     *
     */
    public void removeInputListener(KeyListener keyListener){
        removeKeyListener(keyListener);
    }

    public Cell[][] getCells(){
        return cells;
    }

    public void setCells(Cell[][] cells){
        this.cells = cells;
        repaint();
    }


}

