package client.game;

/**
 * Enumeration storing image names. These names are used to update
 * {@link client.game.Cell}. {@link client.game.Cell#paintComponent(java.awt.Graphics)} uses a
 * switch statement based on this enum to paint the correct images into each cell.
 *
 *
 * @author Robert Wells
 * @version 1.0
 *
 */
public enum ImageName {

    //Terrain can be exploded by bomb, whilst Permterrain cannot
    PLAYERONEFORWARD, PLAYERONELEFT, PLAYERONERIGHT, PLAYERONEBACK, PLAYERTWOFORWARD,
    PLAYERTWOLEFT, PLAYERTWORIGHT, PLAYERTWOBACK, BOMB, TERRAIN, PERMTERRAIN, EMPTY,
    EXPLOSIONCENTRE, EXPLOSIONLEFTONE, EXPLOSIONLEFTTWO, EXPLOSIONRIGHTONE,
    EXPLOSIONRIGHTTWO, EXPLOSIONTOPONE, EXPLOSIONTOPTWO,
    EXPLOSIONBOTTOMONE, EXPLOSIONBOTTOMTWO, ANIMATIONBLOCK, TITLE
}
