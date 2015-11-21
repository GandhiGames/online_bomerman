package client.game;

/**
 * Abstract base class for a number of objects i.e. {@link Player} and {@link Bomb}.
 * Provides positional and audio data used by these objects.
 *
 * @author Robert Wells
 * @version 1.0
 *
 */
public abstract class GameObject {
    protected int positionX, positionY;
    private Audio audio;

    public GameObject(Audio audio) {
        this.audio = audio;

    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public void setPosition(int[] position) {
        positionX = position[0];
        positionY = position[1];

    }

    public Audio getAudio() {
        return audio;
    }
}
