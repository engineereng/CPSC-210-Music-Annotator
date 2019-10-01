package test;

import model.TimeStamp;
import model.Waveform;
import model.WaveformBar;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.*;

public class WaveformTest extends FileIOTest {
    Waveform wave;
    @BeforeEach
    public void setup(){
        try {
            wave = new Waveform(PIANO2_FILE,1);
        }
        catch (Exception e) {
            fail("Can't use a non-existent test file!");
        }
    }

    @Test
    public void constructorThrowsIllegalArgumentExceptionZeroTest() {
        try {
            wave = new Waveform(PIANO2_FILE, 0);
        } catch (IllegalArgumentException e) {

        } catch (Exception e) {
            fail("Constructor should throw IllegalArgumentException!");
        }
    }

    @Test
    public void constructorThrowsIllegalArgumentExceptionNegTest() {
        try {
            wave = new Waveform(PIANO2_FILE, -1);
        } catch (IllegalArgumentException e) {

        } catch (Exception e) {
            fail("Constructor should throw IllegalArgumentException!");
        }
    }

    @Test
    public void constructorThrowsUnsupportedAudioFileExceptionTest() {
        try {
            wave = new Waveform(INVALID_AUDIO_FILE, 2);
            fail("Constructor is supposed to throw UnsupportedAudioFileException.");
        } catch (UnsupportedAudioFileException e) {

        } catch (Exception e) {
            fail("Constructor is supposed to throw UnsupportedAudioFileException");
        }
    }

    @Test
    public void constructorThrowsIOExceptionTest() {
        try {
            wave = new Waveform(new File("dskjaflkdsa jff"),2);
            fail("Constructor is supposed to throw IOException with a nonexistent filename.");
        }
        catch (IOException e){}
        catch (Exception e) {
            fail("Constructor is supposed to throw IOException with a nonexistent filename.");
        }
    }

    @Test
    public void getAverageAmplitudeTest() {
        assertEquals(37744.6,round(wave.averageAmplitude(),1));
    }

    @Test
    public void getNumberOfBarsHalfSecondBarsTest() {
        try {
            wave = new Waveform(PIANO2_FILE, 0.5);
        } catch (Exception e){
            fail("Constructor shouldn't have thrown an error for half-second bars test!");
        }
        assertEquals(13, wave.getNumberOfBars());
    }

    @Test
    public void getNumberOfBarsHalfSecondBarsYogurtYardTest() {
        try {
            wave = new Waveform(YOGURT_YARD_FILE, 0.5);
        } catch (Exception e){
            fail("Constructor shouldn't have thrown an error for half-second bars test!");
        }
        assertEquals(375, wave.getNumberOfBars());
    }

    @Test
    public void getNumberOfBarsOneSecondBarsTest() {
        assertEquals(7,wave.getNumberOfBars());
    }

    @Test
    public void getNumberOfBarsOneSecondBarsYogurtYardTest() {
        try {
            wave = new Waveform(YOGURT_YARD_FILE, 1.0);
        } catch (Exception e){
            fail("Constructor shouldn't have thrown an error for half-second bars test!");
        }
        assertEquals(188, wave.getNumberOfBars());
    }

    @Test
    public void setSecondsPerBarGetNumberOfBarsTest() {
        try {
            wave.setSecondsPerBar(2);
        } catch (Exception e) {
            fail("Constructor shouldn't have thrown an error for two seconds bars test!");
        }
        assertEquals(2, wave.getSecondsPerBar());
        assertEquals(4, wave.getNumberOfBars());
    }

    @Test
    public void getLengthTest() {
        try {
            wave = new Waveform(PIANO2_FILE, 0.4);
        } catch (Exception e) {
            fail("Constructor shouldn't have thrown an error for getLength()");
        }
        assertEquals(6.3, round(wave.getLength(),1));
    }

    @Test
    public void getLengthYogurtYardTest() {
        try {
            wave = new Waveform(YOGURT_YARD_FILE,0.31);
        } catch (Exception e) {
            fail("Constructor shouldn't have thrown an error for getLength()");
        }
        assertEquals(187.4,round(wave.getLength(),1));
    }

    @Test
    public void getBarThrowsIndexOutOfBoundsExceptionNegativeIndexTest() {
        try {
            wave.getBar(-1);
            fail("getBar is supposed to throw an index out of bounds exception!");
        } catch (IndexOutOfBoundsException e) {}
    }

    @Test
    public void getBarThrowsIndexOutOfBoundsExceptionOutOfRangeTest() {
        try {
            wave.getBar(wave.getNumberOfBars());
            fail("getBar is supposed to thrown an index out of bounds exception!");
        } catch (IndexOutOfBoundsException e) {}
    }

    @Test
    public void getBarPiano2FirstTest() {
        assertEquals(new WaveformBar(65525,Waveform.START,1),wave.getBar(0));
    }

    @Test
    public void getBarYogurtYardSecondTest() {
        try {
            wave = new Waveform(YOGURT_YARD_FILE,1);
        } catch (Exception e) {
            fail("getBar shouldn't have thrown an exception when trying to initialize YogurtYard!");
        }
        WaveformBar bar = wave.getBar(1);
        assertEquals(new TimeStamp(1), bar.getStart());
        assertEquals(new TimeStamp(1+1), bar.getEnd());
        assertEquals(29199.2, round(bar.getAmplitude(), 1));
    }

    @Test
    public void getEnd() {
        assertEquals(wave.getEnd().getTimeStampInSeconds(), wave.getLength());
    }

//    @Test
//    public void equalsNullTest() {
//        assertFalse(wave.equals(null));
//    }
//
//    @Test
//    public void equalsSameReferenceTest() {
//        assertTrue(wave.equals(wave));
//        assertEquals(wave.hashCode(), wave.hashCode());
//    }
//
//    @Test
//    public void equalsDifferentTypeTest() {
//        assertFalse(wave.equals("foo"));
//        assertFalse(wave.hashCode() == "foo".hashCode());
//    }
//
//    @Test
//    public void equalsDifferentFileTest() {
//        try {
//            Waveform otherWave;
//            otherWave = new Waveform(YOGURT_YARD_FILE, 1);
//            assertFalse(wave.equals(otherWave));
//            assertFalse(wave.hashCode() == otherWave.hashCode());
//        } catch (Exception e) {
//            fail("");
//        }
//    }
//
//    @Test
//    public void equalsDifferentSecondsPerBarTest() {
//        try {
//            Waveform otherWave;
//            otherWave = new Waveform(PIANO2_FILE, 0.5);
//            assertFalse(wave.equals(otherWave));
//            assertFalse(wave.hashCode() == otherWave.hashCode());
//        } catch (Exception e) {
//            fail("");
//        }
//    }
//
//    @Test
//    public void equalsSameValuesTest() {
//        try {
//            Waveform otherWave;
//            otherWave = new Waveform(PIANO2_FILE, 1);
//            assertEquals(wave,otherWave);
//            assertTrue(wave.hashCode() == otherWave.hashCode());
//        } catch (Exception e) {
//            fail("");
//        }
//    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}