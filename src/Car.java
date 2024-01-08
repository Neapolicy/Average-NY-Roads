import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Car extends JLabel implements Runnable{
    private Box carHitbox;
    public Car(int x, int y) throws IOException {
        ImageIcon icon = new ImageIcon(ImageIO.read(new File("ImageFiles/Images/car1.png")));
        Image image = icon.getImage();
        Image newimg = image.getScaledInstance(80, 125, Image.SCALE_SMOOTH);
        icon = new ImageIcon(newimg);
        setIcon(icon);

        this.setBounds(x, y, icon.getIconWidth(), icon.getIconHeight());
        this.setOpaque(true);
        carHitbox = (new Box(this.getX(), this.getY(), this.getWidth(), this.getHeight(), Color.RED));
    }

    public Box getCarHitbox()
    {
        return carHitbox;
    }

    @Override
    public void run() {

    }
}
