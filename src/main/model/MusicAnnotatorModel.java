package model;

import model.exceptions.SourceAlreadyInModelException;
import model.exceptions.TimeStampOutsideWaveformRange;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MusicAnnotatorModel extends Observable {
    private LabelHandler labelHandler = new LabelHandler();
    private Waveform waveform = null;
    private Set<Source> sources = new HashSet<>();

    // TODO implement save

    // EFFECTS: saves current state of this to a .txt file
//    public void save(String fileName) {
//        //https://stackoverflow.com/questions/1053467/how-do-i-save-a-string-to-a-text-file-using-java
//        try {
//            PrintWriter out = new PrintWriter(fileName);
//            // TODO save state of MusicAnnotator to file using save()
//            out.println("foo bar.");
//            out.close();
//        } catch (FileNotFoundException e) {
//            System.out.println("File not found!");
//        }
//    }
//
//    // TODO implement load
//    // MODIFIES: this
//    // EFFECTS: replaces current state of this with a MusicAnnotator from .txt file
//    public void load(String fileName) {
//        // https://javarevisited.blogspot.com/2015/09/how-to-read-file-into-string-in-java-7.html
//        try {
//            Files.lines(Paths.get("foo.txt"), StandardCharsets.UTF_8).forEach(System.out::println);
//        } catch (Exception e) {
//            System.out.println("An error occurred while loading the MusicAnnotator file!");
//        }
//    }

    // MODIFIES: this
    // EFFECTS: loads a waveform file from the
    public void loadWaveformFile(File file) throws UnsupportedAudioFileException, IOException {
        waveform = new Waveform(file,0.5);
        notifyObservers(this,"Waveform file loaded");
    }

    // EFFECTS: returns the current waveform or null if uninitialized
    public Waveform getWaveform() {
        return waveform;
    }

    // MODIFIES: this
    // EFFECTS: adds the label to the model at the timeStamp;
    //          throws TimeStampOutsideWaveformRange if timeStamp > waveform range
    public void addLabel(TimeStamp timeStamp, Label label) throws TimeStampOutsideWaveformRange {
        if (waveform != null && timeStamp.isAfter(waveform.getEnd())) {
            throw new TimeStampOutsideWaveformRange();
        }
        labelHandler.addLabel(timeStamp,label);
    }

    public Label getLabel(TimeStamp timeStamp) {
        return labelHandler.getLabel(timeStamp);
    }

    public List<Label> getLabelsWithSource(Source source) {
        return labelHandler.getLabelsWithSource(source);
    }

    public boolean hasLabel(Label label) {
        return labelHandler.contains(label);
    }


    // MODIFIES: this
    // EFFECTS: if the source is not already in the model, adds the source to the model
    //          otherwise, throws SourceAlreadyInModelException
    public void addSource(Source s) throws SourceAlreadyInModelException {
        if (!sources.add(s)) {
            throw new SourceAlreadyInModelException();
        }
    }

    // MODIFIES: this
    // EFFECTS: if the source is in the model, removes it from the model and all labels associated with it
    public void removeSource(Source source) {
        if (sources.contains(source)) {
            source.removeFromAllLabels();
            labelHandler.removeSourceFromLabels(source);
            sources.remove(source);
        }
    }

    // EFFECTS: returns true iff source is in the model
    public boolean containsSource(Source source) {
        return sources.contains(source);
    }
}