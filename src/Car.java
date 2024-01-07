import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Car extends JLabel{
    public Car(int x, int y) throws IOException {
        ImageIcon icon = new ImageIcon(ImageIO.read(new File("ImageFiles/Images/road_block.png")));
        Image image = icon.getImage();
        Image newimg = image.getScaledInstance(80, 125, Image.SCALE_SMOOTH);
        icon = new ImageIcon(newimg);
        setIcon(icon);

        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        this.setBounds(x, y, icon.getIconWidth(), icon.getIconHeight());
        this.setOpaque(true);
    }
}
