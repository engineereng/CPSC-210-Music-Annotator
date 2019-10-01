package model;

import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

// A musical source with a name, artist, color, and list of labels that use the source.
public class Source {
    private String name;
    private String artist;
    private Color color;
    private ArrayList<Label> labels = new ArrayList<>();

    public Source(String name, String artist, Color color) {
        this.name = name;
        this.artist = artist;
        this.color = color;
    }

    // EFFECTS: returns true iff the source has a label with that source
    public boolean containsLabel(Label label) {
        return labels.contains(label);
    }

    //getters and setters
    public void addLabel(Label label) {
        if (!labels.contains(label)) {
            labels.add(label);
            label.setSource(this);
        }
    }

    // EFFECTS: if label uses this source, remove it from the label and remove this from the label
    public void removeLabel(Label label) {
        if (labels.contains(label)) {
            labels.remove(label);
            label.removeSource();
        }
    }

    // EFFECTS: returns the number of labels in this
    public int getLabelCount() {
        return labels.size();
    }

    // EFFECTS: removes this from all labels
    void removeFromAllLabels() {
        for (Label label : labels) {
            label.removeSource();
        }
    }

    // getters and setters
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getArtist() {
        return artist;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Source source = (Source) o;
        return Objects.equals(name, source.name)
                && Objects.equals(artist, source.artist)
                && Objects.equals(color, source.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, artist, color);
    }
}
