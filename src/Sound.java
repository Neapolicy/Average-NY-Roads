import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;


public class Sound implements Runnable
{
    private String fileLocation;
    private boolean loopable;
    private SourceDataLine line;
    // https://stackoverflow.com/questions/23255162/looping-audio-on-separate-thread-in-java <- ripped from there, and modified a bit
    public void play(String fileName, boolean loopable) //make sure to use the full file name maybe?
    {
        this.loopable = loopable;
        fileLocation = "SFX/Sounds/" + fileName + ".wav";
        Thread t1 = new Thread(this);
        t1.start();
    }
    @Override
    public void run() //plays once
    {
        if (!loopable) playSound(fileLocation);
        else {
            while (loopable) {
                playSound(fileLocation);
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
        int nBytesRead = 0;
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

    public void setLoopable(boolean b) {loopable = b;}
}