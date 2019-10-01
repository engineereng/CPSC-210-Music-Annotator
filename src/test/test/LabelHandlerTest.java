package test;

import model.Label;
import model.LabelHandler;
import model.Source;
import model.TimeStamp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class LabelHandlerTest {
    private LabelHandler labelHandler;
    @BeforeEach
    public void setup() {
        labelHandler = new LabelHandler();
    }

    @Test
    public void addLabelTest() {
        Label label = new Label("foo");
        TimeStamp timeStamp = new TimeStamp(13);
        labelHandler.addLabel(timeStamp, label);
        assertTrue(labelHandler.contains(label));
        assertEquals(label, labelHandler.getLabel(timeStamp));
    }

    @Test
    public void addLabelInSameSpotTest() {
        Label label1 = new Label("d");
        Label label2 = new Label("da");
        TimeStamp timeStamp = new TimeStamp(3);
        labelHandler.addLabel(timeStamp,label1);
        labelHandler.addLabel(timeStamp,label2);
        assertFalse(labelHandler.contains(label1));
        assertTrue(labelHandler.contains(label2));
    }

    @Test
    public void getLabelTest() {
        assertEquals(null, labelHandler.getLabel(new TimeStamp(0)));
    }

    @Test
    public void getLabelsWithSourceNoLabelsTest() {
        assertTrue(labelHandler.getLabelsWithSource(new Source("foo", "bar", Color.yellow)).isEmpty());
    }

    @Test
    public void getLabelsWithSourceTest() {
        Label label1 = new Label("d");
        Label label2 = new Label("doo");
        Label label3 = new Label("gi3u");
        Source source = new Source("foo","d",Color.black);
        Source source2 = new Source("bar", "e", Color.white);
        label1.setSource(source);
        label3.setSource(source);
        labelHandler.addLabel(new TimeStamp(3), label1);
        labelHandler.addLabel(new TimeStamp(0), label2);
        labelHandler.addLabel(new TimeStamp(1), label3);
        List<Label> labelsWithSource = new ArrayList<>();
        labelsWithSource.add(label1);
        labelsWithSource.add(label3);
        assertEquals(labelsWithSource,labelHandler.getLabelsWithSource(source));
        assertTrue(labelHandler.getLabelsWithSource(source2).isEmpty());
    }

    @Test
    public void removeSourceFromLabels() {
        Label label1 = new Label("fdsfasd");
        Label label2 = new Label("foo bar sada");
        Label label3 = new Label("14");
        Source source = new Source("u5", "f", Color.gray);
        Source source2 = new Source("31","gj", Color.orange);
        source.addLabel(label1);
        source.addLabel(label2);
        source2.addLabel(label3);
        labelHandler.addLabel(new TimeStamp(31),label1);
        labelHandler.addLabel(new TimeStamp(511), label2);
        labelHandler.removeSourceFromLabels(source);
        assertTrue(labelHandler.getLabelsWithSource(source).isEmpty());
        assertFalse(label1.hasSource());
        assertFalse(label2.hasSource());
    }

    @Test
    public void removeSourceFromLabelsNoLabelsTest() {
        labelHandler.getLabelsWithSource(new Source("foo", "bar", Color.yellow));
    }

    @Test
    public void removeSourceFromLabelsNoLabelsWithSourceTest() {
        Label label1 = new Label("fad");
        Label label2 = new Label("d8");
        Source source1 = new Source("d","d",Color.black);
        source1.addLabel(label1);
        source1.addLabel(label2);
        labelHandler.addLabel(new TimeStamp(10), label1);
        labelHandler.addLabel(new TimeStamp(2), label2);
        labelHandler.removeSourceFromLabels(new Source("foo", "bar", Color.black));
        assertTrue(labelHandler.contains(label1));
        assertTrue(labelHandler.contains(label2));
    }

}
