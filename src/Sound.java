import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;


public class Sound implements Runnable
{
    private String fileLocation;
    private volatile boolean loopable;
    private volatile boolean safeGuard = true;
    private volatile SourceDataLine line;
    private Thread t1;
    private int nBytesRead;
    // https://stackoverflow.com/questions/23255162/looping-audio-on-separate-thread-in-java <- ripped from there, and modified a bit
    public void play(String fileName, boolean loopable) //make sure to use the full file name maybe?
    {
        this.loopable = loopable;
        fileLocation = "SFX/Sounds/" + fileName + ".wav";
        t1 = new Thread(this);
        t1.start();
    }
    @Override
    public void run() //plays once
    {
        if (!loopable && safeGuard) { //when loopable is false, loopable here ironically is true
            System.out.println("lol");
            playSound(fileLocation);
        }
        else if (loopable){
            while (loopable) {
                System.out.println("lolz");
                long startTime = System.nanoTime();

                playSound(fileLocation);
                long totalTime = System.nanoTime() - startTime;

                if (totalTime < MyFrame.targetTime) {
                    try {
                        Thread.sleep((MyFrame.targetTime - totalTime) / 1000000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    private void playSound(String fileName)
    {
        File soundFile = new File(fileName);
        AudioInputStream audioInputStream = null;
        try
        {
            audioInputStream = AudioSystem.getAudioInputStream(soundFile);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        assert audioInputStream != null;
        AudioFormat audioFormat = audioInputStream.getFormat();
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
        try
        {
            line = (SourceDataLine) AudioSystem.getLine(info);
            line.open(audioFormat);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        assert line != null;
        line.start();
        nBytesRead = 0;
        byte[] abData = new byte[128000];
        while (nBytesRead != -1)
        {
            try
            {
                nBytesRead = audioInputStream.read(abData, 0, abData.length);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            if (nBytesRead >= 0)
            {
                line.write(abData, 0, nBytesRead);
            }
        }
        line.drain();
        line.close();
    }

    public void setLoopable(boolean b) {
        loopable = b;
        safeGuard = false;
    }

    public void killThread() {t1.interrupt();}
}