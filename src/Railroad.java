import javax.swing.*;
import java.awt.*;

public class Railroad extends JPanel {

    private Color grey = new Color(204, 204, 204);

    /** Creates a new instance of railroad */
    public Railroad(int width) {
        this.setLayout(null);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Paint your images
        g.setColor(new Color(51, 0, 0)); // dark brown, will change later
        g.fillRect(0, 15, (int) MyFrame.size.getWidth(), 60);

        g.setColor(new Color(102, 51, 0)); //brown
        for (int x = 10; x < MyFrame.size.getWidth(); x += 80){
            g.fillRect(x, 0, 20, 100);
        }

        g.setColor(grey); //sets color

        g.fillRect(0, 0, (int) MyFrame.size.getWidth(), 15);
        g.fillRect(0, 75, (int) MyFrame.size.getWidth(), 75);
        g.dispose();
    }

    public void setSize(int width, int height) {
        super.setSize(width, height);
        repaint();  // Trigger a repaint to update the displayed image
    }
}
