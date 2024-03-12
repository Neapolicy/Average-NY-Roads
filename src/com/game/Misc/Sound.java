package com.game.Misc;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;

public class Sound implements Runnable {
    private String fileLocation;
    private volatile boolean loopable;
    private volatile SourceDataLine line;
    private Thread t1;
    private volatile boolean stopRequested; // Flag to signal stop
    private double volume = 1.0f;

    public void play(String fileName, boolean loopable) {
        this.loopable = loopable;
        fileLocation = "SFX/Sounds/" + fileName + ".wav";
        t1 = new Thread(this);
        t1.start();
        stopRequested = false;
    }

    @Override
    public void run() {
        try {
            if (!loopable) playSound(fileLocation); //if not loopable, play it once
            else {
                while (!stopRequested){
                    playSound(fileLocation);
                }
            }
        } finally {
            if (line != null) {
                line.stop();
                line.close();
            }
        }
    }

    private void playSound(String fileName) {
        File soundFile = new File(fileName);

        try (AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile)) {
            AudioFormat audioFormat = audioInputStream.getFormat();
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);

            line = (SourceDataLine) AudioSystem.getLine(info);
            line.open(audioFormat);
            line.start();

            int bytesRead;
            byte[] buffer = new byte[128000];

            while ((bytesRead = audioInputStream.read(buffer, 0, buffer.length)) != -1 && !stopRequested) {
                // Adjust the volume of the audio samples
                for (int i = 0; i < bytesRead; i += 2) {
                    short sample = (short) ((buffer[i + 1] << 8) | (buffer[i] & 0xFF));
                    sample = (short) (sample * volume);
                    buffer[i] = (byte) sample;
                    buffer[i + 1] = (byte) (sample >> 8);
                }

                line.write(buffer, 0, bytesRead);
                if (stopRequested) break;
            }

            line.drain();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void stopSound() {
        stopRequested = true;
        if (line != null) {
            line.stop();
            line.flush();
            line.close();
        }
    }

    public void setLoopable(boolean b) {
        loopable = b;
    }

    public void killThread() {
        stopSound();
        t1.interrupt();
    }
    public void setVolume(double volume) {
        if (volume >= 0.0f && volume <= 1.0f) {
            this.volume = volume;
        } else {
            throw new IllegalArgumentException("Volume should be in the range [0.0, 1.0]");
        }
    }

}
