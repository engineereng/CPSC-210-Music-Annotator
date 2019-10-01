package ui;

import model.Waveform;

import javax.swing.*;
import java.awt.*;

// a helper class that draws rectangles onto a Drawing to represent a waveform
public class WaveformRenderer extends JPanel {
    private int playLineCoord;

    public static final int MIN_BLOCK_WIDTH = 20; // block width in pixels
    public static final int DEFAULT_MAX_BLOCK_HEIGHT = 20; // maximum height of a block
    public static final double HEIGHT_MULTIPLIER = 200; // pixels / amplitude
    public static final int MIN_BLOCK_HEIGHT = 10;

    private Waveform waveform;
    private Drawing drawing;
    private int rectangleWidth = MIN_BLOCK_WIDTH;
    private int pixelsPerSecond;
    //class invariants: bars do not intersect
    //                  no bars go outside the length
    //                  all bars are the same pixel length

    // MODIFIES: waveform, drawing, this
    // EFFECTS: constructs a WaveformRenderer with an underlying waveform and a drawing to edit
    public WaveformRenderer(Waveform waveform, Drawing drawing) {
        this.waveform = waveform;
        this.drawing = drawing;
        this.rectangleWidth = drawing.getWidth() / waveform.getNumberOfBars();
        pixelsPerSecond = (int) (drawing.getWidth() / waveform.getLength());
        if (rectangleWidth < MIN_BLOCK_WIDTH) {
            rectangleWidth = MIN_BLOCK_WIDTH;
            int wantedNumBars = drawing.getWidth() / rectangleWidth;
            double secondsPerBar = waveform.getLength() / wantedNumBars;
            waveform.setSecondsPerBar(secondsPerBar);
        }
        initialize();
    }

    public int getPixelsPerSecond() {
        return pixelsPerSecond;
    }

    private void initialize() {
        drawing.removeAllShapes();
        for (int i = 0; i < waveform.getNumberOfBars(); i++) {
            // "normalized" height.
            int height = (int) (waveform.getBar(i).getAmplitude() / waveform.averageAmplitude() * HEIGHT_MULTIPLIER);
            int drawingHeight = drawing.getHeight();
            if (height > drawingHeight) {
                height = drawingHeight;
            }
            if (height < MIN_BLOCK_HEIGHT) {
                height = MIN_BLOCK_HEIGHT;
            }
            Shape shape = new Shape(rectangleWidth * i, drawingHeight - height, rectangleWidth, height);
            drawing.addShape(shape);
        }
    }


    // EFFECTS: paints WaveformRenderer
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(500,500);
    }
}
