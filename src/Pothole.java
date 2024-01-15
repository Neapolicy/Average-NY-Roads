import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Pothole extends JLabel { //shouldnt be that hard hopefully??
    private Box potholeHitbox;
    private ImageIcon icon;
    public Pothole(int x, int y) throws IOException {
        icon = new ImageIcon(ImageIO.read(new File("ImageFiles/Images/road_block.png")));
        Image image = icon.getImage();
        Image newimg = image.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        icon = new ImageIcon(newimg);
        setIcon(icon);
        setLocation(x, y);
    }

    public void setLocation(int x, int y) {
            this.setBackground(Color.BLACK);
            this.setOpaque(true);
            this.setBounds(x, y , icon.getIconWidth(), icon.getIconHeight());
            potholeHitbox = new Box(this.getX(), this.getY(), 50, 50);
    }

    public Box getPotholeHitbox()
    {
        return potholeHitbox;
    }
}
