package client.game;


import javax.swing.*;
import java.awt.*;


/**
 * Class that creates part of the clients game environment. The game environment
 * consists of a number of cells.
 * When there is a change in the contents of the cell {@link #setImageName(ImageName)} is called
 * and the cells image updated.
 *
 * @author Robert Wells
 * @version 1.0
 *
 */
public class Cell extends JPanel {

    private int row, column;
    private ImageName imageName, previousImageName;
    private Image image;

    public Cell(int row, int column, Image image){
        this.row = row;
        this.column = column;
        imageName = ImageName.EMPTY;
        previousImageName = imageName;
        setBackground(Color.white);

        this.image = image;
    }


    /**
     * Updates the cells image based on {@link #imageName}.
     *
     */
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);


        switch (imageName){
            case EMPTY:

                break;
            case PLAYERONEFORWARD:
                checkPreviousExplosion(g);
                g.drawImage(image.getPlayerOneForward(), 15, 0,45,45, null);


                break;

            case PLAYERONELEFT:
                checkPreviousExplosion(g);

                g.drawImage(image.getPlayerOneLeft(), 15, 0,45,45, null);


                break;

            case PLAYERONERIGHT:
                checkPreviousExplosion(g);


                g.drawImage(image.getPlayerOneRight(), 15, 0,45,45, null);


                break;


            case PLAYERONEBACK:
                checkPreviousExplosion(g);


                g.drawImage(image.getPlayerOneBack(), 15, 0,45,45, null);


                break;



            case PLAYERTWOFORWARD:
                checkPreviousExplosion(g);


                g.drawImage(image.getPlayerTwoForward(), 15, 0,45,45, null);


                break;

            case PLAYERTWOLEFT:
                checkPreviousExplosion(g);

                g.drawImage(image.getPlayerTwoLeft(), 15, 0,45,45, null);


                break;

            case PLAYERTWORIGHT:
                checkPreviousExplosion(g);



                g.drawImage(image.getPlayerTwoRight(), 15, 0,45,45, null);


                break;

            case PLAYERTWOBACK:
                checkPreviousExplosion(g);


                g.drawImage(image.getPlayerTwoBack(), 15, 0,45,45, null);


                break;

            case BOMB:

                checkPreviousPlayer(g);


                g.drawImage(image.getBomb(), 15, 0,45,45, null);


                break;
            case TERRAIN:

                g.drawImage(image.getTerrain(), 0, 0,70,50, null);
                break;


            case PERMTERRAIN:

                g.drawImage(image.getPermTerrain(), 0, 0,70,50, null);
                break;
            case EXPLOSIONCENTRE:

                g.drawImage(image.getExplosionCentre(), 0, 0,80,45, null);

                //checkPrevious(g);
                break;
            case EXPLOSIONBOTTOMONE:

                g.drawImage(image.getExplosionBottomOne(), 0, 0,70,45, null);

                //checkPrevious(g);
                break;
            case EXPLOSIONBOTTOMTWO:

                g.drawImage(image.getExplosionBottomTwo(), 0, 0,70,45, null);

                //checkPrevious(g);
                break;

            case EXPLOSIONRIGHTONE:

                g.drawImage(image.getExplosionRightOne(), 0, 0,80,45, null);

                //checkPrevious(g);
                break;

            case EXPLOSIONRIGHTTWO:

                g.drawImage(image.getExplosionRightTwo(), 0, 0, 80,45, null);

                //checkPrevious(g);
                break;

            case EXPLOSIONTOPONE:

                g.drawImage(image.getExplosionTopOne(), 0, 0,70,45, null);

                //checkPrevious(g);
                break;

            case EXPLOSIONTOPTWO:

                g.drawImage(image.getExplosionTopTwo(), 0, 0,70,45, null);

                //checkPrevious(g);
                break;

            case EXPLOSIONLEFTONE:

                g.drawImage(image.getExplosionLeftOne(), 0, 0,80,45, null);

                //checkPrevious(g);
                break;

            case EXPLOSIONLEFTTWO:

                g.drawImage(image.getExplosionLeftTwo(), 0, 0,80,45, null);

                //checkPrevious(g);
                break;

            case ANIMATIONBLOCK:

                g.drawImage(image.getAnimationBlock(), 0, 0,80,45, null);

                //checkPrevious(g);
                break;

            case TITLE:

               // g.drawImage(image.getArrow(), 2,10, 50, 70, null);
                g.drawImage(image.getTitle(), 100, 120,560,240, null);

                //checkPrevious(g);
                break;




        }

    }

    /**
     * Used to check if a players image was in the cell before it was overwritten
     * by a bomb. This allows for a player to be visible as well as the bomb in the same cell.
     */
    private void checkPreviousPlayer(Graphics g){

        if (previousImageName == ImageName.PLAYERONEFORWARD) {

            g.drawImage(image.getPlayerOneForward(), 15, 0,45,45, null);
        } else if (previousImageName == ImageName.PLAYERONELEFT ) {


            g.drawImage(image.getPlayerOneLeft(), 15, 0,45,45, null);
        } else if (previousImageName == ImageName.PLAYERONERIGHT) {

            g.drawImage(image.getPlayerOneRight(), 15, 0,45,45, null);
        } else if (previousImageName == ImageName.PLAYERONEBACK) {


            g.drawImage(image.getPlayerOneBack(), 15, 0,45,45, null);
        }



        if (previousImageName == ImageName.PLAYERTWOFORWARD) {


            g.drawImage(image.getPlayerTwoForward(), 15, 0,45,45, null);
        } else if (previousImageName == ImageName.PLAYERTWOLEFT ) {


            g.drawImage(image.getPlayerTwoLeft(), 15, 0,45,45, null);
        } else if (previousImageName == ImageName.PLAYERTWORIGHT) {


            g.drawImage(image.getPlayerTwoRight(), 15, 0,45,45, null);
        } else if (previousImageName == ImageName.PLAYERTWOBACK) {

            g.drawImage(image.getPlayerTwoBack(), 15, 0,45,45, null);
        }
    }

    /**
     * Used to check if an explosion image was in the cell before it was overwritten
     * by a player. This allows for a player to be visible as well as an
     * explosion in the same cell.
     */
    private void checkPreviousExplosion(Graphics g){

        switch (previousImageName){
            case EXPLOSIONCENTRE:

                g.drawImage(image.getExplosionCentre(), 0, 0,80,45, null);

            break;

            case EXPLOSIONBOTTOMONE:

                g.drawImage(image.getExplosionBottomOne(), 0, 0,70,45, null);

            //checkPrevious(g);
            break;

            case EXPLOSIONBOTTOMTWO:

                g.drawImage(image.getExplosionBottomTwo(), 0, 0,70,45, null);

            //checkPrevious(g);
            break;

            case EXPLOSIONRIGHTONE:

                g.drawImage(image.getExplosionRightOne(), 0, 0,80,45, null);

            //checkPrevious(g);
            break;

            case EXPLOSIONRIGHTTWO:

            g.drawImage(image.getExplosionRightTwo(), 0, 0, 80,45, null);

            //checkPrevious(g);
            break;

            case EXPLOSIONTOPONE:

            g.drawImage(image.getExplosionTopOne(), 0, 0,70,45, null);

            //checkPrevious(g);
            break;

            case EXPLOSIONTOPTWO:

            g.drawImage(image.getExplosionTopTwo(), 0, 0,70,45, null);

            //checkPrevious(g);
            break;

            case EXPLOSIONLEFTONE:

            g.drawImage(image.getExplosionLeftOne(), 0, 0,80,45, null);

            //checkPrevious(g);
            break;

            case EXPLOSIONLEFTTWO:

            g.drawImage(image.getExplosionLeftTwo(), 0, 0,80,45, null);

            //checkPrevious(g);
            break;

        }
    }

    /**
     * Sets {@link #imageName} and invokes {@link #repaint()}
     * to update the environment.
     *
     * @param imageName the name of the image to be painted.
     */
    public void setImageName(ImageName imageName){
        previousImageName = this.imageName;
        this.imageName = imageName;

        if (imageName != previousImageName || imageName == ImageName.BOMB)
            repaint();
    }

    /**
     * Two cells are considered equal if their row and column numbers match.
     *
     */
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cell cell = (Cell) o;

        return column == cell.column && row == cell.row;

    }

    public void clearCell(){
        this.imageName = ImageName.EMPTY;
    }

    public int getRow(){
        return row;
    }

    public int getColumn(){
        return column;
    }

    public ImageName getImageName(){
        return imageName;
    }

    public ImageName getPreviousImageName(){
        return previousImageName;
    }

}
