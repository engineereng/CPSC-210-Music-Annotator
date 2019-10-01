package ui;

import ui.sound.FilePlayer;
import ui.sound.FilePlayerObserver;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// taken from drawing player
public class DrawingPlayer implements ActionListener, FilePlayerObserver {

    private Drawing drawing;
    private Timer timer;
    private int playingColumn;

    // EFFECTS: constructs a DrawingPlayer
    public DrawingPlayer(Drawing drawing, Timer timer) {
        this.drawing = drawing;
        this.timer = timer;
        playingColumn = 0;
    }

    public void setPlayingColumn(int column) {
        playingColumn = column;
    }

    // MODIFIES: this
    // EFFECTS:  repaints, increments column,  stops if done
    //           this class is the listener for the timer object, and this method is what the timer calls
    //           each time through its loop.
    @Override
    public void actionPerformed(ActionEvent e) {
        drawRedLine();
        incrementColumn();
        stopAtEnd();
    }

    // MODIFIES: this
    // EFFECTS:  moves current x-column to next column; updates figures
    private void incrementColumn() {
        playingColumn += 1;
    }

    // MODIFIES: this
    // EFFECTS:  moves playback line to playingColumn to trigger sound and repaint
    private void drawRedLine() {
        drawing.setPlayLineColumn(playingColumn);
        drawing.repaint(); // the Java Graphics framework will call Drawing.paintComponent()
    }

    // MODIFIES: this
    // EFFECTS:  calls Timer.stop() when playingColumn is past the edge of the frame
    private void stopAtEnd() {
        if (playingColumn > drawing.getWidth()) {
            timer.stop();
        }
    }

    public void update(FilePlayer filePlayer) {
        if (filePlayer.isPlaying()) {
            timer.stop();
        }
    }

    public void stop() {
        timer.stop();
    }
}

