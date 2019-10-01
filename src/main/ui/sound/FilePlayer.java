package ui.sound;


import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

// plays a song
public class FilePlayer extends Observable {
    private Clip clip;
    private boolean canPlay;

    public FilePlayer(File file) throws UnsupportedAudioFileException, IOException, CantPlayFileException {
        AudioInputStream aisForPlay = AudioSystem.getAudioInputStream(file);
        try {
            clip = AudioSystem.getClip();
            clip.open(aisForPlay);
            clip.setFramePosition(0);
            canPlay = true;
        } catch (LineUnavailableException e) {
            canPlay = false;
            throw new CantPlayFileException("FilePlayer can only play 8 bit and 16 bit music.");
        }
    }

    public void setPosition(double seconds) {
        clip.setMicrosecondPosition((long) seconds / (long) 1000000.0);
    }

    public boolean isPlaying() {
        return clip.isActive();
    }

    public boolean isCanPlay() {
        return canPlay;
    }

    // REQUIRES: clip is stopped
    public void reset() {
        clip.setFramePosition(0);
    }

    public void play() {
        clip.start();
        LineListener listener = new LineListener() {
            public void update(LineEvent event) {
                if (event.getType() == LineEvent.Type.STOP) {
                    if (clip.getMicrosecondLength() == clip.getMicrosecondPosition()) {
                        notifyObservers(FilePlayer.this);
                    }
                    stop();
                }
            }
        };
        clip.addLineListener(listener);
    }

    public void stop() {
        clip.stop();
    }

    public Clip getClip() {
        return clip;
    }
}


