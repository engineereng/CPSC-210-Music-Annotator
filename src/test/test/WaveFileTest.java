package test;

import model.WaveFile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;


public class WaveFileTest extends FileIOTest {
    private WaveFile waveFile;

    @BeforeEach
    public void setup() {
        try {
            waveFile = new WaveFile(PIANO2_FILE);
        } catch (Exception e) {
            fail("Test file cannot be found!");
        }
    }

    public void setupYogurtYardFile() {
        try {
            waveFile = new WaveFile(YOGURT_YARD_FILE);
        } catch (Exception e) {
            fail("Test file cannot be found!");
        }
    }

    @Test
    public void constructorThrowsUnsupportedAudioFileExceptionTest() {
        try {
            waveFile = new WaveFile(INVALID_AUDIO_FILE);
            fail("Constructor is supposed to throw UnsupportedAudioFileException.");
        } catch (UnsupportedAudioFileException e) {

        } catch (Exception e) {
            fail("Constructor is supposed to throw UnsupportedAudioFileException");
        }
    }

    @Test
    public void constructorThrowsIOExceptionTest() {
        try {
            waveFile = new WaveFile(new File("dskjaflkdsa jff"));
            fail("Constructor is supposed to throw IOException with a nonexistent filename.");
        }
        catch (IOException e){}
        catch (Exception e) {
            fail("Constructor is supposed to throw IOException with a nonexistent filename.");
        }
    }

    @Test
    public void getDurationTimeTest() {
        assertEquals(6.3,round(waveFile.getDurationTime(),1));
    }

    @Test
    public void getDurationTimeYogurtYardTest() {
        setupYogurtYardFile();
        assertEquals(187.4,round(waveFile.getDurationTime(),1));
    }

    @Test
    public void getFramesCountTest() {
        assertEquals(302712,waveFile.getFramesCount());
    }

    @Test
    public void getFramesCountYogurtYardTest() {
        setupYogurtYardFile();
        assertEquals(8266482,waveFile.getFramesCount());

    }

    @Test
    public void getSampleIntThrowsIllegalArgumentExceptionNegativeSampleNumberTest() {
        try {
            waveFile.getSampleInt(-1);
            fail("getSampleInt should throw an IllegalArgumentException with a negative index.");
        } catch (IllegalArgumentException e) {}
    }

    @Test
    public void getSampleIntThrowsIllegalArgumentExceptionInvalidSampleNumberTest() {
        try {
            waveFile.getSampleInt((int) waveFile.getFramesCount() + 1);
            fail("getSampleInt should throw an IllegalArgumentException with an invalid index.");
        } catch (IllegalArgumentException e) {}
    }

    @Test
    public void getSampleIntThrowsIllegalArgumentExceptionInvalidSampleNumberLargeTest() {
        try {
            waveFile.getSampleInt(1210848);
            fail("getSampleInt should throw an IllegalArgumentException with an invalid index.");
        } catch (IllegalArgumentException e) {}
    }

    @Test
    public void getSampleIntPiano2Test() {
        try {
            int sample = waveFile.getSampleInt(0);
            assertEquals(65525,sample);

        } catch (IllegalArgumentException e) {
            fail("getSampleInt shouldn't throw an exception for a valid integer");
        }
    }

    @Test
    public void getSampleIntYogurtYardTest() {
        try {
            int sample = waveFile.getSampleInt(0);
            assertEquals(65525,sample);

        } catch (IllegalArgumentException e) {
            fail("getSampleInt shouldn't throw an exception for a valid integer");
        }
    }

    @Test
    public void getSampleRatePiano2Test() {
        assertEquals(48000,waveFile.getSampleRate());
    }

    @Test
    public void getSampleRateYogurtYardTest() {
        setupYogurtYardFile();
        assertEquals(44100,waveFile.getSampleRate());

    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
