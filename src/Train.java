import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Train extends Car{
    public Train(int x, int y) throws IOException { //idk, i just want this to have the same functionality as a car, except its technically offscreen?
        super(x, y);

        ImageIcon icon = new ImageIcon(ImageIO.read(new File("ImageFiles/Images/train.png")));
        Image image = icon.getImage();
        Image newimg = image.getScaledInstance(925, 80, Image.SCALE_SMOOTH);
        icon = new ImageIcon(newimg);
        setIcon(icon);

        this.setBounds(x, y, icon.getIconWidth(), icon.getIconHeight());
    }
}
