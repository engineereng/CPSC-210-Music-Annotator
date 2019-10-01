package model;

import java.text.DecimalFormat;
import java.util.Objects;

// a timestamp. Must be within 0, max
public class TimeStamp {
    private double seconds;

    // EFFECTS: constructs a new timestamp
    //          throws IllegalArgumentException if startInMilliSeconds < 0
    public TimeStamp(double startInSeconds) {
        if (startInSeconds < 0) {
            throw new IllegalArgumentException("A timestamp's start in seconds cannot be < 0");
        }
        this.seconds = startInSeconds;
    }


    public double getTimeStampInSeconds() {
        return seconds;
    }

    // EFFECTS: returns a string representing the timestamp (format MM:SS or HH:MM:SS)
    public String getFormat() {
        if (seconds < 60) {
            return "00:" + renderDigit((int) this.seconds);
        }
        int hours = (int) seconds / 3600;
        int minutes = (int) seconds / 60 - hours * 60;
        int seconds = (int) this.seconds - (minutes * 60 + hours * 3600);
        String timestamp = "";
        if (hours > 0) {
            timestamp += renderDigit(hours) + ":";
        }
        timestamp += renderDigit(minutes) + ":";
        timestamp += renderDigit(seconds);
        return timestamp;
    }

    // EFFECTS: returns a string representing the timestamp (format MM:SS.SS or HH:MM:SS.SS)
    public String getFormatWithMilliseconds() {
        return getFormat() + new DecimalFormat(".00").format(seconds % 1);
    }

    // EFFECTS: if number >= 0, returns a string representing a number of hours, minutes, or seconds.
    private String renderDigit(int number) {
        if (number < 10) {
            return "0" + ((Integer) number).toString();
        }
        return ((Integer) number).toString();
    }

    // EFFECTS: if offset >= 0, returns a TimeStamp that is offset seconds after this
    //          otherwise throw IllegalArgumentException
    public TimeStamp getTimeStampAfterOffset(double offset) {
        if (offset >= 0) {
            return new TimeStamp(seconds + offset);
        }
        throw new IllegalArgumentException("Timestamp offsets cannot be negative.");
    }

    // EFFECTS: returns true iff this represents a timestamp after ts
    public boolean isAfter(TimeStamp ts) {
        return seconds > ts.getTimeStampInSeconds();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TimeStamp timeStamp = (TimeStamp) o;
        return seconds == timeStamp.seconds;
    }

    @Override
    public int hashCode() {
        return Objects.hash(seconds);
    }
}
