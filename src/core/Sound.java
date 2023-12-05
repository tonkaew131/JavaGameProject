package core;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound {
    private Clip letterPickupSound = loadSound("/resources/sound/letter_pickup.wav");
    private Clip breadthSound = loadSound("/resources/sound/breath.wav");
    private Clip jumpScareSound = loadSound("/resources/sound/jump_scare.wav");
    private Clip backgroundMusic = loadSound("/resources/sound/josh.wav");

    public Sound() {
        if (Setting.ENABLED_SOUND) {
            backgroundMusic.start();
            backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    public void playLetterPickupSound() {
        if (Setting.ENABLED_SOUND)
            new Thread(null, () -> {
                letterPickupSound.setFramePosition(0);
                letterPickupSound.start();
            }).start();
    }

    public void playBreadthSound() {
        if (Setting.ENABLED_SOUND)
            new Thread(null, () -> {
                breadthSound.setFramePosition(0);
                breadthSound.start();
            }).start();
    }

    public void playJumpScareSound() {
        if (Setting.ENABLED_SOUND)
            new Thread(null, () -> {
                jumpScareSound.setFramePosition(0);
                jumpScareSound.start();
            }).start();
    }

    public Clip loadSound(String path) {
        AudioInputStream audioInputStream;

        Clip clip = null;
        try {
            InputStream audioSrc = getClass().getResourceAsStream(path);
            InputStream bufferedIn = new BufferedInputStream(audioSrc);
            audioInputStream = AudioSystem.getAudioInputStream(bufferedIn);
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

    public Clip getBackgroundMusic() {
        return backgroundMusic;
    }

    public static void setVolume(Clip clip, int level) {
        if (level > 100)
            level = 100;
        if (level < 0)
            level = 0;

        Objects.requireNonNull(clip);

        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

        if (gainControl != null) {
            float db = (gainControl.getMaximum() - gainControl.getMinimum()) * level / 100.f;
            db += gainControl.getMinimum();
            gainControl.setValue(db);
        }
    }
}
