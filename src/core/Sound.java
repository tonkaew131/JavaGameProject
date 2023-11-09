package core;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound {
    private Clip letterPickupSound = loadSound("/resources/sound/letter_pickup.wav");
    private Clip breadthSound = loadSound("/resources/sound/breath.wav");

    public Sound() {
        Clip backgroundMusic = loadSound("/resources/sound/background_music.wav");
        backgroundMusic.start();
        backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void playLetterPickupSound() {
        new Thread(null, () -> {
            letterPickupSound.setFramePosition(0);
            letterPickupSound.start();
        }).start();
    }

    public void playBreadthSound() {
        new Thread(null, () -> {
            breadthSound.setFramePosition(0);
            breadthSound.start();
        }).start();
    }

    public Clip loadSound(String path) {
        AudioInputStream audioInputStream;

        Clip clip = null;
        try {
            audioInputStream = AudioSystem
                    .getAudioInputStream(
                            getClass().getResourceAsStream(path));
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
        } catch (UnsupportedAudioFileException e) {
            System.out.println("[Sound]: Unsupported audio file. " + path);
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            System.out.println("[Sound]: Line unavailable." + path);
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("[Sound]: IO exception." + path);
            e.printStackTrace();
        }

        return clip;
    }
}
