import javax.swing.*;
import java.awt.*;

public class Railroad extends JPanel {

    private int width;

    /** Creates a new instance of railroad */
    public Railroad(int width) {
        this.setLayout(null);
        this.width = width;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Paint your images
        g.drawRect(0, 100, (int) MyFrame.size.getWidth(), 80);
        g.fillRect(0, 100, (int) MyFrame.size.getWidth(), 80);
    }

    public void setSize(int width, int height) {
        super.setSize(width, height);
        repaint();  // Trigger a repaint to update the displayed image
    }
}
