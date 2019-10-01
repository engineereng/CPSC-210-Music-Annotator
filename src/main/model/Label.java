package model;

public class Label {

    private Source source;
    private String description;

    public Label(String description) {
        this.description = description;
    }

    // EFFECTS: sets the source of this label
    public void setSource(Source source) {
        if (this.source != source) {
            if (this.source != null) {
                this.source.removeLabel(this);
            }
            this.source = source;
            source.addLabel(this);
        }
    }

    // EFFECTS: returns true iff Label has a source
    public boolean hasSource() {
        return source != null;
    }

    // MODIFIES: this
    // EFFECTS: if there is a source, removes the source from this label and this from the source
    public void removeSource() {
        if (source != null) {
            Source oldSource = source;
            source = null;
            oldSource.removeLabel(this);
        }
    }

    // getters and setters
    public Source getSource() {
        return source;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
