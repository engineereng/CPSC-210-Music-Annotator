package test;

import model.Label;
import model.Source;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import static org.junit.jupiter.api.Assertions.*;


public class SourceTest {
    private Source source;

    @BeforeEach
    public void setup() {
        source = new Source("e","grand dad", Color.black);
    }

    @Test
    public void addLabelTest() {
        Label label = new Label("dead");
        source.addLabel(label);
        assertTrue(label.hasSource());
        assertEquals(source, label.getSource());
        assertTrue(source.containsLabel(label));
    }

    @Test
    public void addLabelToDifferentSource() {
        Label label = new Label("mashup");
        source.addLabel(label);
        Source otherSource = new Source("f","f",Color.green);
        otherSource.addLabel(label);
        assertFalse(source.containsLabel(label));
        assertTrue(otherSource.containsLabel(label));
    }

    @Test
    public void addLabelIdenticalLabelToDifferentSource() {
        Label label = new Label("pootis");
        Label otherLabel = new Label("pootis");
        source.addLabel(label);
        Source otherSource = new Source("fff", "ff",Color.yellow);
        otherSource.addLabel(otherLabel);
        assertTrue(source.containsLabel(label));
        assertTrue(otherSource.containsLabel(otherLabel));
    }

    @Test
    public void containsLabelEmptyNullTest() {
        assertFalse(source.containsLabel(null));
    }

    @Test
    public void containsLabelEmptyTest() {
        Label label = new Label("2313sd");
        assertFalse(source.containsLabel(label));
    }

    @Test
    public void containsLabelNotEmptyNullTest() {
        source.addLabel(new Label("djgnfadiu"));
        assertFalse(source.containsLabel(null));
    }

    @Test
    public void containsLabelNotEmptyFirstTest() {
        Label label = new Label("djgnfadiu");
        source.addLabel(label);
        source.addLabel(new Label("fsaliu2n"));
        assertTrue(source.containsLabel(label));
    }

    @Test
    public void containsLabelNotEmptyLastTest() {
        Label label = new Label("fsrl23lj 2lkdsl");
        source.addLabel(new Label("fdaslf l3j0 "));
        source.addLabel(label);
        assertTrue(source.containsLabel(label));
    }

    @Test
    public void containsLabelNotEmptyFalseTest() {
        source.addLabel(new Label("ou,mwsdh2"));
        source.addLabel(new Label(" oiulkjf8uslji"));
        assertFalse(source.containsLabel(new Label("dsoui2mcp")));
    }

    @Test
    public void removeLabelRemoveFirstTwiceTest() {
        Label label1 = new Label("u91jklanvnz");
        Label label2 = new Label("fdaslf l3j0 ");
        source.addLabel(label1);
        source.addLabel(label2);
        source.removeLabel(label1);
        assertFalse(source.containsLabel(label1));
        assertTrue(source.containsLabel(label2));
        assertFalse(label1.hasSource());
        source.removeLabel(label2);
        assertFalse(source.containsLabel(label2));
        assertFalse(label2.hasSource());
        assertEquals(0,source.getLabelCount());
    }

    @Test
    public void removeLabelRemoveLastTwiceTest() {
        Label label1 = new Label("u91jklanvnz");
        Label label2 = new Label("fdaslf l3j0 ");
        Label label3 = new Label("u2ms ");
        source.addLabel(label1);
        source.addLabel(label2);
        source.addLabel(label3);
        source.removeLabel(label2);
        assertFalse(source.containsLabel(label2));
        assertTrue(source.containsLabel(label1));
        assertTrue(source.containsLabel(label3));
        assertFalse(label2.hasSource());
        source.removeLabel(label3);
        assertFalse(source.containsLabel(label3));
        assertFalse(label3.hasSource());
        assertEquals(1,source.getLabelCount());
    }

    @Test
    public void setGetNameTest() {
        assertEquals("e", source.getName());
        source.setName("foo");
        assertEquals("foo",source.getName());
    }

    @Test
    public void setGetArtistTest() {
        assertEquals("grand dad",source.getArtist());
        source.setArtist("PSY");
        assertEquals("PSY", source.getArtist());
    }

    @Test
    public void setGetColorTest() {
        assertEquals(new Color(0,0,0), source.getColor());
        source.setColor(Color.black);
        assertEquals(Color.black, source.getColor());
    }

    @Test
    public void equalsNullTest() {
        assertFalse(source.equals(null));
    }

    @Test
    public void equalsSameReferenceTest() {
        assertTrue(source.equals(source));
        assertEquals(source.hashCode(), source.hashCode());
    }

    @Test
    public void equalsDifferentTypeTest() {
        assertFalse(source.equals("foo"));
        assertFalse(source.hashCode() == "foo".hashCode());
    }

    @Test
    public void equalsDifferentNameTest() {
        Source otherSource = new Source("name","grand dad", Color.black);
        assertFalse(source.equals(otherSource));
        assertFalse(source.hashCode() == otherSource.hashCode());
    }

    @Test
    public void equalsDifferentArtistTest() {
        Source otherSource = new Source("e","grandest dad", Color.black);
        assertFalse(source.equals(otherSource));
        assertFalse(source.hashCode() == otherSource.hashCode());
    }

    @Test
    public void equalsDifferentColorTest() {
        Source otherSource = new Source("e","grand dad", Color.orange);
        assertFalse(source.equals(otherSource));
        assertFalse(source.hashCode() == otherSource.hashCode());
    }

    @Test
    public void equalsDoesntCareAboutLabelsTest() {
        Label label1 = new Label("foo");
        Label label2 = new Label("bar");
        source.addLabel(label1);
        source.addLabel(label2);
        Source otherSource = new Source("e","grand dad", Color.black);
        assertTrue(source.equals(otherSource));
        assertTrue(source.hashCode() == otherSource.hashCode());
    }

    @Test
    public void equalsSameValuesTest() {
        Source otherSource = new Source("e","grand dad", Color.black);
        assertTrue(source.equals(otherSource));
        assertTrue(source.hashCode() == otherSource.hashCode());
    }

    @Test
    public void equalsSameValuesDifferentLabelsTest() {
        Label label1 = new Label("foo");
        Label label2 = new Label("bar");
        source.addLabel(label1);
        source.addLabel(label2);
        Source otherSource = new Source("e","grand dad", Color.black);
        Label label3 = new Label("foo");
        Label label4 = new Label("bar");
        otherSource.addLabel(label3);
        otherSource.addLabel(label4);
        assertEquals(source,otherSource);
        assertTrue(source.hashCode() == otherSource.hashCode());
    }
}
