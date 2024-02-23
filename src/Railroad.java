import javax.swing.*;
import java.awt.*;

public class Railroad extends JPanel {

    private int width;

    /** Creates a new instance of Road */
    public Railroad(int width) {
        this.setLayout(null);
        this.width = width;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Paint your images
        ImageIcon icon1 = new ImageIcon("ImageFiles/Images/railroad.png");
        Image image1 = icon1.getImage();
        Image newimg = image1.getScaledInstance(width, 80, Image.SCALE_SMOOTH);
        icon1 = new ImageIcon(newimg);
        g.drawImage(icon1.getImage(), 0, 0, this);
    }
}
