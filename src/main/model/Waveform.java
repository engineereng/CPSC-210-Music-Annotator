package model;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//a waveform for a song
public class Waveform {
    public static final TimeStamp START = new TimeStamp(0);

    private double secondsPerBar;
    private double length;
    private WaveFile waveFile;
    private List<WaveformBar> bars = new ArrayList<>();

    // EFFECTS: constructs a Waveform with using a specified audio file and number of seconds per bar;
    //          throws UnsupportedAudioFileException if the audio file is not of a supported type
    //          throws IOException if there was an error opening the file
    //          throws IllegalArgumentException if secondsPerBar <= 0
    public Waveform(File file, double secondsPerBar) throws UnsupportedAudioFileException, IOException {
        waveFile = new WaveFile(file);
        length = waveFile.getDurationTime();
        setSecondsPerBar(secondsPerBar);
    }

    public double getSecondsPerBar() {
        return secondsPerBar;
    }

    // REQUIRES: waveFile is already initialized
    // EFFECTS: sets the seconds per bar and refreshes the waveform
    public void setSecondsPerBar(double secondsPerBar) throws IllegalArgumentException {
        if (secondsPerBar <= 0) {
            throw new IllegalArgumentException();
        }
        this.secondsPerBar = secondsPerBar;
        generate();
    }

    // EFFECTS: produces a waveform of a song.
    private void generate() {
        bars = new ArrayList<>();
        double sum = 0; //sum of amplitudes
        int count = 0;
        int indexIncrement = (int) (secondsPerBar * waveFile.getSampleRate());

        for (int i = 0; i < waveFile.getFramesCount(); i++) {
            sum += waveFile.getSampleInt(i);
            count++;

            double start = i / waveFile.getSampleRate();
            if (i % indexIncrement == 0) {
                double duration = secondsPerBar;
                if (new TimeStamp(start).getTimeStampAfterOffset(duration).isAfter(getEnd())) {
                    duration = waveFile.getDurationTime() - start;
                }
                bars.add(new WaveformBar(sum / count,new TimeStamp(start), duration));
                sum = 0;
                count = 0;
            }
        }
    }

    // EFFECTS: returns number of bars in the waveform
    public int getNumberOfBars() {
        return bars.size();
    }

    // EFFECTS: returns the average amplitude of the waveform
    public double averageAmplitude() {
        double sum = 0;
        for (WaveformBar bar : bars) {
            sum += bar.getAmplitude();
        }
        return sum / bars.size();
    }

    // EFFECTS: returns the bar at the specified index
    //          if index is out of the bounds, throw IndexOutOfBoundsException
    public WaveformBar getBar(int index) throws IndexOutOfBoundsException {
        return bars.get(index);
    }

    // EFFECTS: returns the length of the waveform in seconds
    public double getLength() {
        return length;
    }

    // EFFECTS: returns a timeStamp representing the end of the waveform
    public TimeStamp getEnd() {
        return new TimeStamp(waveFile.getDurationTime());
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) {
//            return true;
//        }
//        if (o == null || getClass() != o.getClass()) {
//            return false;
//        }
//        Waveform waveform = (Waveform) o;
//        return Double.compare(waveform.secondsPerBar, secondsPerBar) == 0
//                && Double.compare(waveform.length, length) == 0
//                && waveFile.equals(waveform.waveFile)
//                && bars.equals(waveform.bars);
//    }
//
//    @Override
//    public int hashCode() {
//
//        return Objects.hash(secondsPerBar, length, waveFile, bars);
//    }
}