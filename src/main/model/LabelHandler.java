package model;

import java.util.*;

// handles adding labels
public class LabelHandler {
    private HashMap<TimeStamp, Label> labels = new HashMap<>();

    public boolean contains(Label label) {
        return labels.containsValue(label);
    }

    // MODIFIES: this
    // EFFECTS: adds a new label beginning at the given timestamp;
    //          if a Label already exists at that timeStamp, replace it with label.
    public void addLabel(TimeStamp timeStamp, Label label) {
        labels.put(timeStamp, label);
    }

    // EFFECTS: gets the label at the designated timestamp,
    //          returns null if there is no such label
    public Label getLabel(TimeStamp timestamp) {
        return labels.get(timestamp);
    }

    // EFFECTS: returns a list of all labels that have the source;
    //          returns null if no labels with the source
    public List<Label> getLabelsWithSource(Source source) {
        List<Label> labelsWithSource = new ArrayList<>();
        //https://stackoverflow.com/questions/1066589/iterate-through-a-hashmap
        for (Label label : labels.values()) {
            if (source.equals(label.getSource())) {
                labelsWithSource.add(label);
            }
        }
        return labelsWithSource;
    }

    // REQUIRES: source != null
    // MODIFIES: this
    // EFFECTS: removes the source from all labels in the handler
    public void removeSourceFromLabels(Source source) {
        for (Label label : labels.values()) {
            if (source.equals(label.getSource())) {
                label.removeSource();
            }
        }
    }
}
