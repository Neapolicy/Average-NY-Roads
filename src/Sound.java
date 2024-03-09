import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;

public class Sound implements Runnable {
    private String fileLocation;
    private volatile boolean loopable;
    private volatile SourceDataLine line;
    private Thread t1;
    private volatile boolean stopRequested; // Flag to signal stop

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
            do {
                playSound(fileLocation);
            } while (loopable && !stopRequested);
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
                line.write(buffer, 0, bytesRead);
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
}
