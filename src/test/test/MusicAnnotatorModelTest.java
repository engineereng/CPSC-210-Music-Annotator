package test;

import model.*;
import model.Label;
import model.exceptions.SourceAlreadyInModelException;
import model.exceptions.TimeStampOutsideWaveformRange;
import model.observers.MusicAnnotatorObserver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;


public class MusicAnnotatorModelTest {

    MusicAnnotatorModel musicAnnotator;

    @BeforeEach
    public void setup(){
        musicAnnotator = new MusicAnnotatorModel();
    }

//    @Test
//    public void saveLoadTest(){
//        musicAnnotator = new MusicAnnotatorModel();
//        musicAnnotator.addSource(new Source("782","6us",Color.green));
//        musicAnnotator.addLabel(new TimeStamp(0), new Label("foo"));
//        musicAnnotator.save("foo.txt");
//        MusicAnnotatorModel otherModel = new MusicAnnotatorModel();
//        try {
//            otherModel.load("foo.txt");
//        }
//        catch (Exception e){
//            fail("foo.txt does not exist.");
//        }
//        assertTrue(musicAnnotator.equals(otherModel));
//    }

    @Test
    public void addLabelExceptionTest() {
        try {
            musicAnnotator.loadWaveformFile(FileIOTest.PIANO2_FILE);
        } catch (Exception e) {
            fail("MusicAnnotator failed to load the waveform file!");
        }
        try {
            musicAnnotator.addLabel(musicAnnotator.getWaveform().getEnd().getTimeStampAfterOffset(1),new Label("foo"));
            fail("MusicAnnotator failed to throw TimeStampOutOfWaveformRangeException");
        } catch (TimeStampOutsideWaveformRange e) {}
    }

    @Test
    public void addLabelNoExceptionTest() {
        try {
            musicAnnotator.loadWaveformFile(FileIOTest.PIANO2_FILE);
        } catch (Exception e) {
            fail("MusicAnnotator failed to load the waveform file!");
        }
        musicAnnotator.addLabel(Waveform.START,new Label("foo"));
    }

    @Test
    public void addOneLabelTest() {
        Label label1 = new Label("foo bar");
        TimeStamp timeStamp1 = new TimeStamp(100);
        musicAnnotator.addLabel(timeStamp1,label1);
        assertTrue(musicAnnotator.hasLabel(label1));
        assertEquals(label1,musicAnnotator.getLabel(timeStamp1));
    }

    @Test
    public void addTwoLabelsTest(){
        Label label1 = new Label("fdsfasd");
        Label label2 = new Label("foo bar sada");
        TimeStamp timeStamp1 = new TimeStamp(3211);
        TimeStamp timeStamp2 = new TimeStamp(2131);
        musicAnnotator.addLabel(timeStamp1,label1);
        musicAnnotator.addLabel(timeStamp2,label2);
        assertTrue(musicAnnotator.hasLabel(label2));
        assertEquals(label2, musicAnnotator.getLabel(timeStamp2));
    }

    @Test
    public void addSourceThrowsSourceAlreadyInModelException() {
        Source source = new Source("foo","ba", Color.BLUE);
        try {
            musicAnnotator.addSource(source);
        } catch (SourceAlreadyInModelException e) {
            fail("musicAnnotator shouldn't throw an exception when it has no sources.");
        }
        try {
            musicAnnotator.addSource(source);
            fail("musicAnnotator should have thrown an exception when trying to add a duplicate source");
        } catch (SourceAlreadyInModelException e) {}
    }

    @Test
    public void addSourceTest() {
        Source source = new Source("foo","bar", Color.DARK_GRAY);
        musicAnnotator.addSource(source);
        assertTrue(musicAnnotator.containsSource(source));
    }

    @Test
    public void removeSourceNullTest() {
        Label label1 = new Label("fdsfasd");
        Label label2 = new Label("foo bar sada");
        Source source = new Source("u5", "f", Color.gray);
        source.addLabel(label1);
        source.addLabel(label2);
        musicAnnotator.addLabel(new TimeStamp(31), label1);
        musicAnnotator.addLabel(new TimeStamp(315), label2);
        musicAnnotator.addSource(source);
        musicAnnotator.removeSource(null);
        assertTrue(musicAnnotator.hasLabel(label1));
        assertTrue(musicAnnotator.hasLabel(label2));
        assertTrue(musicAnnotator.containsSource(source));
    }

    @Test
    public void removeSourceNotInSourcesTest() {
        Label label1 = new Label("fdsfasd");
        Label label2 = new Label("foo bar sada");
        Source source = new Source("u5", "f", Color.gray);
        source.addLabel(label1);
        source.addLabel(label2);
        musicAnnotator.addLabel(new TimeStamp(31), label1);
        musicAnnotator.addLabel(new TimeStamp(315), label2);
        musicAnnotator.addSource(source);
        Source otherSource = new Source("gg", "fsa", Color.black);
        musicAnnotator.removeSource(otherSource);
        assertTrue(musicAnnotator.hasLabel(label1));
        assertTrue(musicAnnotator.hasLabel(label2));
        assertTrue(musicAnnotator.containsSource(source));
        assertFalse(musicAnnotator.containsSource(otherSource));
    }

    @Test
    public void removeSourceNoLabelsTest() {
        Source source = new Source("sfa", "fdsaf", Color.red);
        Source otherSource = new Source("gg", "fsa", Color.black);
        musicAnnotator.addLabel(new TimeStamp(0), new Label("foo"));
        musicAnnotator.addLabel(new TimeStamp(13), new Label("bar"));
        musicAnnotator.addSource(source);
        musicAnnotator.addSource(otherSource);
        musicAnnotator.removeSource(source);
        assertFalse(musicAnnotator.containsSource(source));
        assertTrue(musicAnnotator.containsSource(otherSource));
        assertTrue(musicAnnotator.getLabelsWithSource(source).isEmpty());
    }

    @Test
    public void removeSourceHasLabelsTest() {
        Label label1 = new Label("fdsfasd");
        Label label2 = new Label("foo bar sada");
        Source source = new Source("u5", "f", Color.gray);
        source.addLabel(label1);
        source.addLabel(label2);
        musicAnnotator.addLabel(new TimeStamp(31), label1);
        musicAnnotator.addLabel(new TimeStamp(315), label2);
        Source otherSource = new Source("gg", "fsa", Color.black);
        musicAnnotator.addSource(source);
        musicAnnotator.addSource(otherSource);
        musicAnnotator.removeSource(source);
        assertFalse(musicAnnotator.containsSource(source));
        assertTrue(musicAnnotator.containsSource(otherSource));
        assertTrue(musicAnnotator.getLabelsWithSource(source).isEmpty());
        assertFalse(label1.hasSource());
        assertFalse(label2.hasSource());
    }

    @Test
    public void loadWaveformFromFileTest() {
        try {
            musicAnnotator.loadWaveformFile(FileIOTest.PIANO2_FILE);
        } catch (Exception e) {
            fail("MusicAnnotator failed to load the waveform file!");
        }
        assertTrue(musicAnnotator.getWaveform() != null);
    }

    @Test
    public void addModelObserverTest() {
        musicAnnotator.addObserver(new Foo());
        try {
            musicAnnotator.loadWaveformFile(FileIOTest.PIANO2_FILE);
        } catch (Exception e) {
            fail("MusicAnnotator failed to load the waveform file!");
        }
    }

    class Foo implements MusicAnnotatorObserver {
        @Override
        public void update(MusicAnnotatorModel model, String message) {
            //stub
        }
    }
}
