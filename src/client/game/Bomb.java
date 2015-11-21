package client.game;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the bomb object used in the game. Handles initial image placement for both players.
 * Runs a thread to animate the explosion and clearing of explosion and tracks if a player is hit.
 *
 * @author Robert Wells
 * @version 1.0
 *
 */
public class Bomb extends GameObject implements Runnable  {

    private ImageName image;
    private Cell[][] cells;

    private boolean isActive;
    private List<Integer[]> explodedCells;
    private List<Integer[]> explodedCellsHitPlayerCheck;
    private boolean isPlaced;
    private boolean hitPlayer;
    private Player player, otherPlayer;
    private boolean isGameOver;

    /**
     * Initialises variables.
     * Sets {@link #isGameOver} to false, {@link #isPlaced} to false,
     * {@link #isActive} to false, and {@link #hitPlayer} to false.
     *
     * @param cells the environment of Bomberman, updated with bomb position and explosion.
     * @param player the local players position is used for
     *                initial placement and deciding whether a player has been hit.
     * @param otherPlayer the network players position is used for
     *                initial placement and deciding whether a player has been hit.
     * @param audio used for explosion sound.
     */
    public Bomb(Cell[][] cells, Player player, Player otherPlayer, Audio audio){
        super(audio);
        isGameOver = false;
        isPlaced = false;
        isActive = false;
        hitPlayer = false;
        this.cells = cells;
        this.image = ImageName.BOMB;

        this.player = player;
        this.otherPlayer = otherPlayer;

    }

    /**
     * If {@link #isActive} and {@link #isPlaced} is false the
     * places bomb in initial position based on {@link client.game.Player#getPosition()}.
     * This ensures that the player can place one bomb at any onetime.
     * This method is invoked when setting a bomb placed by the local player.
     * isPlaced and isActive is set to true and a new thread is created using
     * this object.
     *
     * @param player the local player.
     * @param otherPlayer the network player.
     *
     */
    public void placeInStart(Player player, Player otherPlayer){
        if (!isPlaced() && !isActive()){
            isPlaced = true;
            isActive = true;
            int[] tempPosition = player.getPosition();
            positionX = tempPosition[0];
            positionY = tempPosition[1];

            cells[positionX][positionY].setImageName(image);

            this.otherPlayer = otherPlayer;

            new Thread(this).start();
        }

    }

    /**
     * If {@link #isActive} and {@link #isPlaced} is false the
     * places bomb in initial position based on position.
     * This ensures that the player can place one bomb at any onetime.
     * This method is invoked when setting a bomb placed by the networked player.
     * isPlaced and isActive is set to true and a new thread is created using
     * this object.
     *
     * @param position the position of the other player i.e. where the bomb should be placed.
     */
    public void placeInStart(int[] position){
        if (!isPlaced() && !isActive()){
            isPlaced = true;
            isActive = true;
            positionX = position[0];
            positionY = position[1];
            cells[position[0]][position[1]].setImageName(image);

            new Thread(this).start();
        }

    }


    /**
     * This method handles the bombs explosion. Before this method is invoked
     *  the bomb has already been placed therefore the three sleeps. This simulates
     *  a bomb countdown i.e. the comb does not explode straight away, then
     *  {@link #isPlaced} is set to false, {@link client.game.Audio#playBombSound()} is
     *  invoked, and {@link #explode()} is invoked. The thread then sleeps again before
     *  clearing the explosion ({@link #clearExplosion()}), setting {@link #isActive} to false
     *  and {@link #hitPlayer} to false.
     *
     */
    public void run(){

       try {
           Thread.sleep(1000);
       } catch (InterruptedException ie){
           ie.printStackTrace();
       }

        isPlaced = false;

        super.getAudio().playBombSound();
        explode();



        try {
            Thread.sleep(500);
        } catch (InterruptedException ie){
            ie.printStackTrace();
        }



        clearExplosion();

        isActive = false;
        hitPlayer = false;



    }

    /**
     * Updates {@link #cells} with explosion images and saves location of
     * cells changed in {@link #explodedCells} so they can be set to empty
     * when explosion is cleared using {@link #clearExplosion()}.
     *
     */
    private void explode(){
        explodedCells = new ArrayList<Integer[]>();
        explodedCellsHitPlayerCheck = new ArrayList<Integer[]>();

        cells[positionX][positionY].setImageName(ImageName.EXPLOSIONCENTRE);
        explodedCells.add(new Integer[]{positionX, positionY});

        for (int i = 1; i < 3; i++){
            int tempPositionX = positionX-i;

            if(tempPositionX >= 0 && !containsPermTerrain(tempPositionX, positionY)){

                if (i > 1)
                    cells[tempPositionX][positionY].setImageName(ImageName.EXPLOSIONTOPTWO);
                else
                    cells[tempPositionX][positionY].setImageName(ImageName.EXPLOSIONTOPONE);

                explodedCells.add(new Integer[]{tempPositionX, positionY});


            } else {
                break;
            }

        }

        for (int i = 1; i < 3; i++){
            int tempPositionX = positionX+i;


            if(tempPositionX < cells.length && !containsPermTerrain(tempPositionX, positionY)){


                if (i > 1)
                    cells[tempPositionX][positionY].setImageName(ImageName.EXPLOSIONBOTTOMTWO);
                else
                    cells[tempPositionX][positionY].setImageName(ImageName.EXPLOSIONBOTTOMONE);

                explodedCells.add(new Integer[]{tempPositionX, positionY});


            } else {
                break;
            }

        }

        for (int i = 1; i < 3; i++){
            int tempPositionY = positionY-i;



            if(tempPositionY >= 0 && !containsPermTerrain(positionX, tempPositionY)){

                if (i > 1)
                    cells[positionX][tempPositionY].setImageName(ImageName.EXPLOSIONLEFTTWO);
                else
                    cells[positionX][tempPositionY].setImageName(ImageName.EXPLOSIONLEFTONE);

                explodedCells.add(new Integer[]{positionX, tempPositionY});




            }  else {
                break;
            }

        }

        for (int i = 1; i < 3; i++){
            int tempPositionY = positionY+i;



            if(tempPositionY < cells.length && !containsPermTerrain(positionX, tempPositionY)){


                if (i > 1)
                    cells[positionX][tempPositionY].setImageName(ImageName.EXPLOSIONRIGHTTWO);
                else
                    cells[positionX][tempPositionY].setImageName(ImageName.EXPLOSIONRIGHTONE);

                explodedCells.add(new Integer[]{positionX, tempPositionY});



            } else {
                break;
            }

        }

    }

    /**
     * Checks if players position is contained within the {@link #explodedCells}
     * list and therefore has been hit by the bomb.
     *
     * @return true if player hit or else false if {@link #explodedCells} is null or size == 0
     *  or players position not contained within the list.
     *
     */
    public boolean isPlayerHit(int[] playerLocation){


        if (explodedCells == null || explodedCells.size() == 0) {
            return false;
        }

        explodedCellsHitPlayerCheck.clear();
        explodedCellsHitPlayerCheck.addAll(explodedCells);

        for (Integer[] tempPosition : explodedCellsHitPlayerCheck) {
            if (tempPosition[0] == playerLocation[0] &&
                    tempPosition[1] == playerLocation[1]) {
                explodedCellsHitPlayerCheck.clear();
                return true;
            }
        }

        explodedCellsHitPlayerCheck.clear();
        return false;
    }


    /**
     * Clears the cells set to the explosion image. Iterates through
     * {@link #explodedCells} and if {@link #isGameOver} is false, sets them to empty,
     * and then restores the players images in case they were overwritten by the explosion.
     *
     */
    private void clearExplosion(){
        for (Integer[] tempPosition : explodedCells) {
            if (!isGameOver)
                cells[tempPosition[0]][tempPosition[1]].setImageName(ImageName.EMPTY);
        }

        if (!isGameOver){
            int[] playerPosition = player.getPosition();
            cells[playerPosition[0]][playerPosition[1]].setImageName(player.getCurrentImage());

            if (otherPlayer != null){
                playerPosition = otherPlayer.getPosition();
                cells[playerPosition[0]][playerPosition[1]].setImageName(otherPlayer.getCurrentImage());
            }
        }

        explodedCells.clear();
    }

    /**
     * Checks whether a specific cell contains the {@link ImageName#PERMTERRAIN} image.
     *
     * @param positionX X position of cell being tested.
     * @param positionY Y position of cell being tested.
     * @return returns true if cell contains {@link ImageName#PERMTERRAIN}.
     */
    private boolean containsPermTerrain(int positionX, int positionY){
        return cells[positionX][positionY].getImageName().equals(ImageName.PERMTERRAIN);
    }

    public List<Integer[]> getExplodedCells(){
        return explodedCells;
    }

    public int[] getPosition(){
        return new int[]{positionX, positionY};
    }

    public boolean isActive(){
        return isActive;
    }

    public boolean isPlaced(){
        return isPlaced;
    }

    public boolean isHitPlayer(){
        return hitPlayer;
    }

    public void setHitPlayer(boolean hitPlayer){
        this.hitPlayer = hitPlayer;
    }

    public void setGameOver(boolean gameOver){
        isGameOver = gameOver;
    }
}
