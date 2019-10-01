package test;

import model.TimeStamp;
import model.WaveformBar;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class WaveformBarTest {
    private WaveformBar waveformBar;

    @BeforeEach
    public void setUp() {
        waveformBar = new WaveformBar(31, new TimeStamp(0.30),31);
    }

    @Test
    public void constructorThrowsIllegalArgumentExceptionTest() {
        try {
            waveformBar = new WaveformBar(-1, new TimeStamp(3), 31.31);
            fail("WaveformBar is supposed to throw an exception if the amplitude is negative!");
        } catch (IllegalArgumentException e) {}
    }

    @Test
    public void getAmplitudeZeroTest() {
        waveformBar = new WaveformBar(0, new TimeStamp(0.0),31);
        assertEquals(0, waveformBar.getAmplitude());
    }

    @Test
    public void getAmplitudeTest() {
        assertEquals(31, waveformBar.getAmplitude());
    }

    @Test
    public void getStartZeroTest() {
        waveformBar = new WaveformBar(601, new TimeStamp(0),8.4);
        assertEquals(new TimeStamp(0), waveformBar.getStart());
    }

    @Test
    public void getStartTest() {
        waveformBar = new WaveformBar(601, new TimeStamp(41.9),8.4);
        assertEquals(new TimeStamp(41.9), waveformBar.getStart());
    }

    @Test
    public void getEndZeroTest() {
        waveformBar = new WaveformBar(10, new TimeStamp(0), 0.0);
        assertEquals(new TimeStamp(0), waveformBar.getEnd());
    }

    @Test
    public void getEndTest() {
        waveformBar = new WaveformBar(72, new TimeStamp(51), 391.4);
        assertEquals(new TimeStamp(51 + 391.4), waveformBar.getEnd());
    }

    @Test
    public void equalsSameReferenceTest() {
        assertTrue(waveformBar.equals(waveformBar));
        assertEquals(waveformBar.hashCode(),waveformBar.hashCode());
    }

    @Test
    public void equalsNullTest() {
        assertFalse(waveformBar.equals(null));
    }

    @Test
    public void equalsDifferentTypeTest() {
        assertFalse(waveformBar.equals("foo"));
        assertFalse(waveformBar.hashCode() == "foo".hashCode());
    }

    @Test
    public void equalsDifferentAmplitudeTest() {
        WaveformBar bar = new WaveformBar(waveformBar.getAmplitude() + 1, waveformBar.getStart(),31);
        assertFalse(waveformBar.equals(bar));
        assertFalse(waveformBar.hashCode() == bar.hashCode());
    }

    @Test
    public void equalsDifferentStartTest() {
        WaveformBar bar = new WaveformBar(waveformBar.getAmplitude(), waveformBar.getStart().getTimeStampAfterOffset(1),31);
        assertFalse(waveformBar.equals(bar));
        assertFalse(waveformBar.hashCode() == bar.hashCode());

    }

    @Test
    public void equalsDifferentDurationAfterTest() {
        WaveformBar bar = new WaveformBar(waveformBar.getAmplitude(), waveformBar.getStart(),31 + 3);
        assertFalse(waveformBar.equals(bar));
        assertFalse(waveformBar.hashCode() == bar.hashCode());
    }

    @Test
    public void equalsDifferentDurationBeforeTest() {
        WaveformBar bar = new WaveformBar(waveformBar.getAmplitude(), waveformBar.getStart(),31 - 3);
        assertFalse(waveformBar.equals(bar));
        assertFalse(waveformBar.hashCode() == bar.hashCode());
    }

    @Test
    public void equalsDifferentValuesTest() {
        WaveformBar bar = new WaveformBar(waveformBar.getAmplitude() + 1, waveformBar.getStart().getTimeStampAfterOffset(1.5),31 - 0.1);
        assertFalse(waveformBar.equals(bar));
        assertFalse(waveformBar.hashCode() == bar.hashCode());
    }

    @Test
    public void equalsDifferentStartDurationTest() {
        WaveformBar bar = new WaveformBar(waveformBar.getAmplitude(), waveformBar.getStart().getTimeStampAfterOffset(0.3),31 + 2.5);
        assertFalse(waveformBar.equals(bar));
        assertFalse(waveformBar.hashCode() == bar.hashCode());
    }

    @Test
    public void equalsDifferentAmplitudeDurationTest() {
        WaveformBar bar = new WaveformBar(waveformBar.getAmplitude(), waveformBar.getStart().getTimeStampAfterOffset(0.3),31 + 2.5);
        assertFalse(waveformBar.equals(bar));
        assertFalse(waveformBar.hashCode() == bar.hashCode());
    }

    @Test
    public void equalsDifferentAmplitudeStartTest() {
        WaveformBar bar = new WaveformBar(waveformBar.getAmplitude() + 3, waveformBar.getStart().getTimeStampAfterOffset(1),31);
        assertFalse(waveformBar.equals(bar));
        assertFalse(waveformBar.hashCode() == bar.hashCode());
    }

    @Test
    public void equalsSameValuesTest() {
        WaveformBar bar = new WaveformBar(waveformBar.getAmplitude(), waveformBar.getStart(),31);
        assertTrue(waveformBar.equals(bar));
        assertTrue(waveformBar.hashCode() == bar.hashCode());
    }


}
