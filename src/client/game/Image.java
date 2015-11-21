package client.game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Class that holds the images used throughout the game. Images files contained within
 * ./Sprites folder.
 *
 * @author Robert Wells
 * @version 1.0
 *
 */
public class Image {

    private BufferedImage explosionCentre,explosionBottomOne, explosionBottomTwo,
        explosionRightOne, explosionRightTwo, explosionTopOne, explosionTopTwo,
        explosionLeftOne, explosionLeftTwo, playerOneForward, playerOneLeft,
        playerOneRight, playerOneBack, playerTwoForward, playerTwoLeft,
        playerTwoRight, playerTwoBack, bomb, terrain, permTerrain, animationBlock,
        title, arrow;




    public Image(){
        try {
            explosionCentre = ImageIO.read(new File("Sprites//explosionCentre.png"));

            explosionBottomOne
                    = ImageIO.read(new File("Sprites//explosionBottomOne.png"));

            explosionBottomTwo
                    = ImageIO.read(new File("Sprites//explosionBottomTwo.png"));

            explosionRightOne
                    = ImageIO.read(new File("Sprites//explosionRightOne.png"));

            explosionRightTwo
                    = ImageIO.read(new File("Sprites//explosionRightTwo.png"));

            explosionTopOne
                    = ImageIO.read(new File("Sprites//explosionTopOne.png"));

            explosionTopTwo
                    = ImageIO.read(new File("Sprites//explosionTopTwo.png"));

            explosionLeftOne
                    = ImageIO.read(new File("Sprites//explosionLeftOne.png"));

            explosionLeftTwo
                    = ImageIO.read(new File("Sprites//explosionLeftTwo.png"));

            playerOneForward = ImageIO.read(new File("Sprites//playerOneForward.png"));

            playerOneLeft = ImageIO.read(new File("Sprites//playerOneLeft.png"));

            playerOneRight = ImageIO.read(new File("Sprites//playerOneRight.png"));

            playerOneBack = ImageIO.read(new File("Sprites//playerOneBack.png"));

            playerTwoForward = ImageIO.read(new File("Sprites//playerTwoForward.png"));

            playerTwoLeft = ImageIO.read(new File("Sprites//playerTwoLeft.png"));

            playerTwoRight = ImageIO.read(new File("Sprites//playerTwoRight.png"));

            playerTwoBack = ImageIO.read(new File("Sprites//playerTwoBack.png"));

            bomb = ImageIO.read(new File("Sprites//bomb.png"));

            terrain = ImageIO.read(new File("Sprites//terrain.png"));

            permTerrain = ImageIO.read(new File("Sprites//permTerrain.png"));

            animationBlock
                    = ImageIO.read(new File("Sprites//terrain.png"));

            title
                    = ImageIO.read(new File("Sprites//title.png"));

            arrow
                    = ImageIO.read(new File("Sprites//arrow.png"));


        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Problem loading file");
        }
    }

    public BufferedImage getExplosionCentre() {
        return explosionCentre;
    }

    public BufferedImage getExplosionBottomOne() {
        return explosionBottomOne;
    }

    public BufferedImage getExplosionBottomTwo() {
        return explosionBottomTwo;
    }

    public BufferedImage getExplosionRightOne() {
        return explosionRightOne;
    }

    public BufferedImage getExplosionRightTwo() {
        return explosionRightTwo;
    }

    public BufferedImage getExplosionTopOne() {
        return explosionTopOne;
    }

    public BufferedImage getExplosionTopTwo() {
        return explosionTopTwo;
    }

    public BufferedImage getExplosionLeftOne() {
        return explosionLeftOne;
    }

    public BufferedImage getExplosionLeftTwo() {
        return explosionLeftTwo;
    }

    public BufferedImage getPlayerOneForward() {
        return playerOneForward;
    }

    public BufferedImage getPlayerOneLeft() {
        return playerOneLeft;
    }

    public BufferedImage getPlayerOneRight() {
        return playerOneRight;
    }

    public BufferedImage getPlayerOneBack() {
        return playerOneBack;
    }

    public BufferedImage getPlayerTwoForward() {
        return playerTwoForward;
    }

    public BufferedImage getPlayerTwoLeft() {
        return playerTwoLeft;
    }

    public BufferedImage getPlayerTwoRight() {
        return playerTwoRight;
    }

    public BufferedImage getPlayerTwoBack() {
        return playerTwoBack;
    }

    public BufferedImage getBomb() {
        return bomb;
    }

    public BufferedImage getTerrain() {
        return terrain;
    }

    public BufferedImage getPermTerrain() {
        return permTerrain;
    }

    public BufferedImage getAnimationBlock() {
        return animationBlock;
    }


    public BufferedImage getTitle() {
        return title;
    }

    public BufferedImage getArrow() {
        return arrow;
    }
}
