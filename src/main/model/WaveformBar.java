package model;

import java.util.Objects;

//A segment of the waveform, represented as a rectangle
public class WaveformBar {

    private double amplitude;
    private double duration;
    private TimeStamp start;

    // EFFECTS: constructs a new WaveformBar of given amplitude that starts at a given time and ends at a given time in
    //          seconds.
    //          throws IllegalArgumentException if amplitude < 0
    public WaveformBar(double amplitude, TimeStamp start, double duration) {
        if (amplitude < 0) {
            throw new IllegalArgumentException("WaveformBar's amplitude cannot be negative. Given " + amplitude);
        }
        this.amplitude = amplitude;
        this.start = start;
        this.duration = duration;
    }

    // EFFECTS: returns the amplitude of this
    public double getAmplitude() {
        return amplitude;
    }

    // EFFECTS: returns the timestamp when this starts
    public TimeStamp getStart() {
        return start;
    }

    // EFFECTS: returns the timestamp when this ends
    public TimeStamp getEnd() {
        return start.getTimeStampAfterOffset(duration);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WaveformBar that = (WaveformBar) o;
        return Double.compare(that.amplitude, amplitude) == 0
                && Double.compare(that.duration, duration) == 0
                && Objects.equals(start, that.start);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amplitude, duration, start);
    }
}
