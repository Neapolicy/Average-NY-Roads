import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Car extends JLabel implements Runnable{
    private Box carHitbox;
    private Sound sound = new Sound();
    private int step = 1;
    private Thread thread;
    public Car(int x, int y) throws IOException {
        ImageIcon icon = new ImageIcon(ImageIO.read(new File("ImageFiles/Images/car1.png")));
        Image image = icon.getImage();
        Image newimg = image.getScaledInstance(125, 80, Image.SCALE_SMOOTH);
        icon = new ImageIcon(newimg);
        setIcon(icon);

        this.setBounds(x, y, icon.getIconWidth(), icon.getIconHeight());
        this.setOpaque(true);
        carHitbox = (new Box(this.getX(), this.getY(), this.getWidth(), this.getHeight(), Color.RED));
        sound.play("Gun_Fire", true);
        thread = new Thread(this);
        thread.start();
    }


    private void moveCar()
    {
        this.setLocation(this.getX() - step, this.getY());
        carHitbox.setLocation(this.getX(), this.getY());
    }

    public Box getCarHitbox()
    {
        return carHitbox;
    }

    @Override
    public void run() { //controls car movement
        while (this.getX() > Main.offScreen)
        {
            moveCar();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        sound.setLoopable(false);
        killThreads();
    }

    public void killSound(){sound.setLoopable(false);}

    public void killThreads() {
        sound.killThread();
        killThread();
        thread.interrupt();
    }

    public void killThread(){thread.interrupt();}

    public int getStep() {return step;}

    public void setSpeed(int speed)
    {
        step = speed;
    }
}
