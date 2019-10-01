package model;

import javax.sound.sampled.*;
import java.io.*;
import java.nio.*;

// adapted from https://stackoverflow.com/questions/39313634/extract-amplitude-array-from-a-wav-file-using-java
public class WaveFile {

    private int sampleSize = AudioSystem.NOT_SPECIFIED;
    private long framesCount = AudioSystem.NOT_SPECIFIED;
    private int sampleRate = AudioSystem.NOT_SPECIFIED;
    private int channelsNum;
    private byte[] data;      // wav bytes
    private AudioInputStream ais;
    private AudioFormat af;

//    private Clip clip;
//    private boolean canPlay;

    public WaveFile(File file) throws UnsupportedAudioFileException, IOException {
        if (!file.exists()) {
            throw new FileNotFoundException(file.getAbsolutePath());
        }

        ais = AudioSystem.getAudioInputStream(file);

        af = ais.getFormat();

        framesCount = ais.getFrameLength();

        sampleRate = (int) af.getSampleRate();

        sampleSize = af.getSampleSizeInBits() / 8;

        channelsNum = af.getChannels();

        long dataLength = framesCount * af.getSampleSizeInBits() * af.getChannels() / 8;

        data = new byte[(int) dataLength];
        ais.read(data);
    }

    private AudioFormat getAudioFormat() {
        return af;
    }

    // EFFECTS: returns the total time in seconds of the file
    public double getDurationTime() {
        return getFramesCount() / getAudioFormat().getFrameRate();
    }

    // EFFECTS: returns the number of frames in the WaveFile.
    public long getFramesCount() {
        return framesCount;
    }


    /**
     * Returns sample (amplitude value). Note that in case of stereo samples
     * go one after another. I.e. 0 - first sample of left channel, 1 - first
     * sample of the right channel, 2 - second sample of the left channel, 3 -
     * second sample of the right channel, etc.
     */
    public int getSampleInt(int sampleNumber) {

        if (sampleNumber < 0 || sampleNumber >= data.length / sampleSize || sampleNumber >= framesCount) {
            throw new IllegalArgumentException(
                    "sample number can't be < 0 or >= main.model.data.length/"
                            + sampleSize);
        }

        byte[] sampleBytes = new byte[4]; //4byte = int

        for (int i = 0; i < sampleSize; i++) {
            sampleBytes[i] = data[sampleNumber * sampleSize * channelsNum + i];
        }

        int sample = ByteBuffer.wrap(sampleBytes)
                .order(ByteOrder.LITTLE_ENDIAN).getInt();
        return sample;
    }

    // EFFECTS: produces the sample rate in Hz.
    public int getSampleRate() {
        return sampleRate;
    }
}