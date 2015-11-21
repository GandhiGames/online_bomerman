package client.game;

import common.GameConstants;

/**
 * Represents the player, deals with movement and the correct use of images e.g.
 * player one should only use the image set for player one.
 *
 * @author Robert Wells
 * @version 1.0
 *
 */
public class Player extends GameObject {

    private Cell[][] cells;
    private ImageName imageForward, imageLeft, imageRight, imageBack,
            currentImage;
    private int lives;

    /**
     * Initialises variables used for the class. {@link #lives} is initialised to
     * {@link GameConstants#PLAYERLIVES}. The images are set based on the player number; for example,
     * if playerNum = 1, {@link #imageForward} = {@link ImageName#PLAYERONEFORWARD}.
     * {@link #currentImage} is set to {@link #imageForward} and {@link #placePlayer(ImageName)}
     * is called.
     *
     * @param cells the environment in which the player exists.
     *              Used for movement and updating the players image position.
     * @param positionX X position of player within the cells.
     * @param positionY Y position of player within the cells.
     * @param playerNum the player number used to assign correct images.
     * @param audio used to play {@link client.game.Audio#playMoveSound()} when player moves.
     *
     */
    public Player(Cell[][] cells, int positionX, int positionY,
                  int playerNum, Audio audio){
        super(audio);
        this.cells = cells;
        this.positionX = positionX;
        this.positionY = positionY;
        lives = GameConstants.PLAYERLIVES;

        if (playerNum == 1){
            imageForward = ImageName.PLAYERONEFORWARD;
            imageBack = ImageName.PLAYERONEBACK;
            imageLeft = ImageName.PLAYERONELEFT;
            imageRight = ImageName.PLAYERONERIGHT;

        } else {
            imageForward = ImageName.PLAYERTWOFORWARD;
            imageBack = ImageName.PLAYERTWOBACK;
            imageLeft = ImageName.PLAYERTWOLEFT;
            imageRight = ImageName.PLAYERTWORIGHT;
        }

        currentImage = imageForward;

        placePlayer(imageForward);

    }

    /**
     * Sets the image of the cell of the players position equal to the players image.
     *
     */
    private void placePlayer(ImageName imageName){
        cells[positionX][positionY].setImageName(imageName);
        cells[positionX][positionY].repaint();
    }


    /**
     *
     * Sets the position of the networked player. This is called when updated player data is received.
     * Checks the difference between {@link #positionX} and {@link #positionY} and the parameters to
     * determine which way the networked player has moved and calls the relevant method, for example:
     *
     * If a characters new positionX is one more that it was last time step then the networked
     * character has moved up one cell and therefore {@link #movePlayerUP()} is called to update
     * the local image of the player accordingly.
     *
     */
    public void setPosition(int positionX, int positionY) {


        if (this.positionX-1 == positionX) {

            movePlayerUP();

        } else if (this.positionY+1 == positionY) {


            movePlayerRight();

        } else if (this.positionX+1 == positionX) {

            movePlayerDown();
        } else if (this.positionY-1 == positionY){

            movePlayerLeft();
        }


    }


    /**
     * Moves the player up one cell. First checks that the player can move
     * by calling {@link #canMove(int, int)}. If the player can move the players
     * previous cell is checked for a bomb, if there is no bomb the cells image is set to
     * empty. This stops placed bombs from being overwritten by an empty image as the player
     * moves away from it. Next the cells are updated with {@link #imageBack} and {@link #currentImage}
     * set accordingly.
     *
     */
    public void movePlayerUP(){
        int tempPositionX = positionX - 1;
        if(tempPositionX >= 0 && canMove(tempPositionX, positionY)){
            if(!checkForBomb(positionX, positionY)) {
                if (!checkForExplosion(positionX, positionY))
                    cells[positionX][positionY].setImageName(ImageName.EMPTY);
            }
            else
                cells[positionX][positionY].setImageName(ImageName.BOMB);

            positionX--;
            cells[positionX][positionY].setImageName(imageBack);
            currentImage = imageBack;

        }
    }

    /**
     * Moves the player left one cell. First checks that the player can move
     * by calling {@link #canMove(int, int)}. If the player can move the players
     * previous cell is checked for a bomb, if there is no bomb the cells image is set to
     * empty. This stops placed bombs from being overwritten by an empty image as the player
     * moves away from it. Next the cells are updated with {@link #imageLeft}
     * and {@link #currentImage} set accordingly.
     *
     */
    public void movePlayerLeft(){
        int tempPositionY = positionY - 1;

        if(tempPositionY >= 0 && canMove(positionX, tempPositionY)){
            if(!checkForBomb(positionX, positionY)){
                if (!checkForExplosion(positionX, positionY))
                    cells[positionX][positionY].setImageName(ImageName.EMPTY);
            } else
                cells[positionX][positionY].setImageName(ImageName.BOMB);

            positionY--;
            cells[positionX][positionY].setImageName(imageLeft);
            currentImage = imageLeft;
            //audio.playMoveSound();
        }
    }

    /**
     * Moves the player down one cell. First checks that the player can move
     * by calling {@link #canMove(int, int)}. If the player can move the players
     * previous cell is checked for a bomb, if there is no bomb the cells image is set to
     * empty. This stops placed bombs from being overwritten by an empty image as the player
     * moves away from it. Next the cells are updated with {@link #imageForward} and {@link #currentImage}
     * set accordingly.
     *
     */
    public void movePlayerDown(){
        int tempPositionX = positionX + 1;

        if(tempPositionX < cells.length && canMove(tempPositionX, positionY)){
            if(!checkForBomb(positionX, positionY)){
                 if (!checkForExplosion(positionX, positionY))
                    cells[positionX][positionY].setImageName(ImageName.EMPTY);
            }else
                cells[positionX][positionY].setImageName(ImageName.BOMB);

            positionX++;
            cells[positionX][positionY].setImageName(imageForward);
            currentImage = imageForward;
        }
    }

    /**
     * Moves the player right one cell. First checks that the player can move
     * by calling {@link #canMove(int, int)}. If the player can move the players
     * previous cell is checked for a bomb, if there is no bomb the cells image is set to
     * empty. This stops placed bombs from being overwritten by an empty image as the player
     * moves away from it. Next the cells are updated with {@link #imageRight} and {@link #currentImage}
     * set accordingly.
     *
     */
    public void movePlayerRight(){
        int tempPositionY = positionY + 1;

        if(tempPositionY < cells.length && canMove(positionX, tempPositionY)){
            if(!checkForBomb(positionX, positionY)) {
                if (!checkForExplosion(positionX, positionY))
                    cells[positionX][positionY].setImageName(ImageName.EMPTY);
            }
            else
                cells[positionX][positionY].setImageName(ImageName.BOMB);


            positionY++;
            cells[positionX][positionY].setImageName(imageRight);
            currentImage = imageRight;

        }
    }

    /**
     * Checks to see if a bomb image is in the selected cell.
     *
     * @return returns true if there is a bomb image.
     *
     */
    private boolean checkForBomb(int positionX, int positionY){
        return cells[positionX][positionY].getImageName().equals(ImageName.BOMB);

    }

    /**
     * Checks to see if there is an explosion image is in the selected cell.
     *
     * @return returns true if there is an explosion image.
     *
     */
    private boolean checkForExplosion(int positionX, int positionY){
        return cells[positionX][positionY].getImageName().equals(ImageName.EXPLOSIONLEFTTWO) ||
                cells[positionX][positionY].getImageName().equals(ImageName.EXPLOSIONLEFTONE) ||
                cells[positionX][positionY].getImageName().equals(ImageName.EXPLOSIONBOTTOMTWO) ||
                cells[positionX][positionY].getImageName().equals(ImageName.EXPLOSIONBOTTOMONE) ||
                cells[positionX][positionY].getImageName().equals(ImageName.EXPLOSIONRIGHTTWO) ||
                cells[positionX][positionY].getImageName().equals(ImageName.EXPLOSIONRIGHTONE) ||
                cells[positionX][positionY].getImageName().equals(ImageName.EXPLOSIONTOPONE) ||
                cells[positionX][positionY].getImageName().equals(ImageName.EXPLOSIONTOPTWO)||
                cells[positionX][positionY].getImageName().equals(ImageName.EXPLOSIONCENTRE);
    }

    /**
     * Checks to see if a player can move into the selected cell. If the cells
     * image equals {@link ImageName#EMPTY} or contains an explosion, the move is legal
     * and is processed accordingly. The player is allowed to walk into an explosion
     * (although they will lose a life). If the move is legal {@link client.game.Audio#playMoveSound()}
     * is called.
     *
     * @return returns true if it is a legal move.
     *
     */
    private boolean canMove(int positionX, int positionY){
        if(cells[positionX][positionY].getImageName().equals(ImageName.EMPTY) ||
                checkForExplosion(positionX, positionY)) {
            super.getAudio().playMoveSound();
            return true;
        }

        return false;

    }

    /**
     * Sets the cell at the players position to {@link ImageName#EMPTY}. Useful
     * for when only one player is connected.
     *
     */
    public void clear(){
        cells[positionX][positionY].setImageName(ImageName.EMPTY);
    }
    public ImageName getCurrentImage(){
        return currentImage;
    }

    public int[] getPosition(){
        return new int[] {positionX, positionY};
    }

    public void removeLife(){
        if (lives > 0)
            lives--;
    }
    public int getLives(){
        return lives;
    }

    public void setLives(int lives){
        this.lives = lives;
    }


}
