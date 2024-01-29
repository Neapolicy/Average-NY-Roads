import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Train extends JLabel implements Runnable{
    protected int offScreen;
    private Sound sound = new Sound();
    public static double step = 40;
    private Thread thread;
    public Train(int x, int y) throws IOException {
        ImageIcon icon = new ImageIcon(ImageIO.read(new File("ImageFiles/Images/train.png")));
        Image image = icon.getImage();
        Image newimg = image.getScaledInstance(925, 80, Image.SCALE_SMOOTH);
        icon = new ImageIcon(newimg);
        setIcon(icon);
        offScreen = -1000;

        this.setBounds(x, y, icon.getIconWidth(), icon.getIconHeight());
        this.setOpaque(true);
        playSound();
        thread = new Thread(this);
        thread.start();
    }

    public void moveTrain()
    {
        this.setLocation((int) (this.getX() - step), this.getY());
        if (step == 0) killSound(false);
    }

    public void playSound() {
        sound.play("game_over", true);
    }

    @Override
    public void run() { //controls train movement
        while (this.getX() > offScreen)
        {
            long startTime = System.nanoTime();

            moveTrain();

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            long totalTime = System.nanoTime() - startTime;

            if (totalTime < MyFrame.targetTime) {
                try {
                    Thread.sleep((MyFrame.targetTime - totalTime) / 1000000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        sound.setLoopable(false);
        killThreads();
    }

    public void killSound(boolean b){sound.setLoopable(b);}

    public void killThreads() {
        sound.killThread();
        killThread();
        thread.interrupt();
    }

    public void killThread(){thread.interrupt();}
}
