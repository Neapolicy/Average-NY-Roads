import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Car extends JLabel implements Runnable{
    private Box carHitbox;
    private String direction;
    public Car(int x, int y) throws IOException {
        ImageIcon icon = new ImageIcon(ImageIO.read(new File("ImageFiles/Images/car1.png")));
        Image image = icon.getImage();
        Image newimg = image.getScaledInstance(80, 125, Image.SCALE_SMOOTH);
        icon = new ImageIcon(newimg);
        setIcon(icon);

        this.setBounds(x, y, icon.getIconWidth(), icon.getIconHeight());
        this.setOpaque(true);
        carHitbox = (new Box(this.getX(), this.getY(), this.getWidth(), this.getHeight(), Color.RED));
        pickDirection();
        Thread thread = new Thread(this);
        thread.start();
    }

    public void pickDirection()
    {
        Random rand = new Random();
        int i = rand.nextInt(1, 5);
        switch (i) {
            case 1 -> direction = "up";
            case 2 -> direction = "down";
            case 3 -> direction = "left";
            case 4 -> direction = "right";
        }
        System.out.println(direction);
    }

    private void moveCar()
    {
        int step = 30;

        switch (direction) {
            case "up" -> {
                this.setLocation(this.getX(), this.getY() - step);
                carHitbox.setLocation(this.getX(), this.getY());
            }
            case "left" -> {
                this.setLocation(this.getX() - step, this.getY());
                carHitbox.setLocation(this.getX(), this.getY());
            }
            case "down" -> {
                this.setLocation(this.getX(), this.getY() + step);
                carHitbox.setLocation(this.getX(), this.getY());
            }
            case "right" -> {
                this.setLocation(this.getX() + step, this.getY());
                carHitbox.setLocation(this.getX(), this.getY());
            }
        }
    }

    public Box getCarHitbox()
    {
        return carHitbox;
    }

    @Override
    public void run() {
        //noinspection InfiniteLoopStatement
        while (true)
        {
            moveCar();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
