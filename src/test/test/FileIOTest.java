package test;

import org.junit.jupiter.api.Test;

import java.io.File;

public abstract class FileIOTest {

    private static final String PATH_PREFIX = "."+ File.separator + "src" + File.separator + "data" + File.separator;
    public static final File PIANO2_FILE = new File(PATH_PREFIX + "piano2.wav");
    public static final File YOGURT_YARD_FILE = new File(PATH_PREFIX + "Nape Mango - Kirby Rip Attack - 93 Frozen Yogurt Yard.wav");
    public static final File INVALID_AUDIO_FILE = new File( PATH_PREFIX + "foo.txt");

    @Test
    public abstract void constructorThrowsUnsupportedAudioFileExceptionTest();

    @Test
    public abstract void constructorThrowsIOExceptionTest();
}
