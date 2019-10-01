package test;

import model.Label;
import model.Source;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;


public class LabelTest {
    private Label label;
    @BeforeEach
    public void setup() {
        label = new Label("");
    }

    @Test
    public void setSourceTest() {
        assertFalse(label.hasSource());
        Source s = new Source("lo", "82", Color.BLACK);
        label.setSource(s);
        assertTrue(label.hasSource());
        assertEquals(s,label.getSource());
        assertTrue(s.containsLabel(label));
    }

    @Test
    public void setSourceTwiceTest() {
        Source s = new Source("1p9", "1", Color.white);
        Source s2 = new Source("3131","310", Color.black);
        label.setSource(s);
        label.setSource(s2);
        assertEquals(s2, label.getSource());
        assertFalse(s.containsLabel(label));
        assertTrue(s2.containsLabel(label));
    }

    @Test
    public void setDescriptionTest() {
        assertEquals("", label.getDescription());
        label.setDescription("label description");
        assertEquals("label description", label.getDescription());
    }

    @Test
    public void removeSourceTest() {
        Source s = new Source("fooo","bar",Color.black);
        label.setSource(s);
        label.removeSource();
        assertFalse(label.hasSource());
        assertFalse(s.containsLabel(label));
    }
}
