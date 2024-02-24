import javax.swing.*;
import java.awt.*;

public class Road extends JPanel{
    private int width;
    public Road(int width) {
        this.setLayout(null);
        this.width = width;
    }
    @Override
    public void paintComponent(Graphics g) { //rahh
        super.paintComponent(g);

        // Paint your images
        ImageIcon icon2 = new ImageIcon("ImageFiles/Images/background.jpg");
        Image image2 = icon2.getImage();
        Image newimg = image2.getScaledInstance(width, 300, Image.SCALE_SMOOTH);
        icon2 = new ImageIcon(newimg);
        g.drawImage(icon2.getImage(), 0, 0, this);
    }

    public void setSize(int width, int height) {
        super.setSize(width, height);
        repaint();  // Trigger a repaint to update the displayed image
    }
}
