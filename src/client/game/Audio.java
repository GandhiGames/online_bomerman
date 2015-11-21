package client.game;

import common.GameConstants;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import javax.sound.sampled.*;
import java.io.*;


/**
 * Class that holds the audio used throughout the game. Audio files contained within
 * ./Audio folder. The methods are synchronised because they are accessed through a number of
 * threads.
 *
 * @author Robert Wells
 * @version 1.0
 *
 */
public class Audio {

    private AudioStream backgroundMusic;
    private AudioStream bombSound;

    private AudioStream hitByBombSound;
    private AudioStream gameStartSound;
    private AudioStream gameOverSound;
    private AudioStream winnerSound;
    private InputStream in;

    private boolean useMoveClip;

    public Audio(){
        useMoveClip = true;
    }

    public synchronized void playBackgroundMusic(){
        if (GameConstants.PLAYAUDIO){
            try {
                in = new FileInputStream("Audio//background.wav");
                backgroundMusic = new AudioStream(in);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            AudioPlayer.player.start(backgroundMusic);
        }
    }

    public synchronized void stopBackgroundMusic(){
        if (GameConstants.PLAYAUDIO){
            AudioPlayer.player.stop(backgroundMusic);
        }
    }

    public synchronized void playGameStartSound(){
        if (GameConstants.PLAYAUDIO){
            try {
                in =  new FileInputStream("Audio//start.wav");
                gameStartSound = new AudioStream(in);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            AudioPlayer.player.start(gameStartSound);
        }
    }

    public synchronized void playGameOverSound(){
        if (GameConstants.PLAYAUDIO){
            try {
                in =  new FileInputStream("Audio//gameOver.wav");
                gameOverSound = new AudioStream(in);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            AudioPlayer.player.start(gameOverSound);
        }
    }

    public synchronized void playWinnerSound(){
        if (GameConstants.PLAYAUDIO){
            try {
                in =  new FileInputStream("Audio//winner.wav");
                winnerSound = new AudioStream(in);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            AudioPlayer.player.start(winnerSound);
        }
    }

    public synchronized void playBombSound(){
        if (GameConstants.PLAYAUDIO){
            try {
                in =  new FileInputStream("Audio//bomb.wav");
                bombSound = new AudioStream(in);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            AudioPlayer.player.start(bombSound);
        }
    }

    public void playMoveSound(){

        if (useMoveClip && GameConstants.PLAYAUDIO){
            try {
                File file = new File("Audio//walk.wav");

                AudioInputStream temp = AudioSystem.getAudioInputStream(file);

                Clip clip = AudioSystem.getClip();

                clip.open(temp);
                clip.start();

            } catch (FileNotFoundException ffe) {
                useMoveClip = false;
            } catch (IOException ioe) {
                useMoveClip = false;
            } catch (LineUnavailableException lue) {
                useMoveClip = false;
            } catch (UnsupportedAudioFileException uafe) {
                useMoveClip = false;
            }
        }



    }

    public synchronized void playHitByBombSound(){
        if (GameConstants.PLAYAUDIO){
            try {
                in =  new FileInputStream("Audio//hitByBomb.wav");
                hitByBombSound = new AudioStream(in);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            AudioPlayer.player.start(hitByBombSound);
        }
    }


}
