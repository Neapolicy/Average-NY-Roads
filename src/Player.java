import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Player extends JLabel implements KeyListener, MouseListener {
    private String direction = "up"; // might scrap if it gets too complicated, but you basically have to look at the pothole in order to fix it
    public Player(ImageIcon icon) {
        super(icon);

        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        this.setBounds((int) size.getWidth()/2, (int) size.getHeight()/2, 50, 50);
        this.setBackground(Color.ORANGE);
        this.setOpaque(true);
    }

    @Override
    public void keyTyped(KeyEvent e) {} // Handle keyTyped event if needed

    @Override
    public void keyPressed(KeyEvent e) {
        var keyCode = e.getKeyChar();
        if (keyCode == 'w') {
            direction = "up";
            this.setLocation(this.getX(), this.getY() - 30);
        }
        if (keyCode == 'a') {
            direction = "left";
            this.setLocation(this.getX() - 30, this.getY());
        }
        if (keyCode == 's') {
            direction = "down";
            this.setLocation(this.getX(), this.getY() + 30);
        }
        if (keyCode == 'd') {
            direction = "right";
            this.setLocation(this.getX() + 30, this.getY());
        }
    }

    public void keyReleased(KeyEvent e) {}// Handle keyReleased event if needed

    public void mouseClicked(MouseEvent arg0) {
        if (arg0.getButton() == MouseEvent.BUTTON1)
        {
            ImageIcon icon = new ImageIcon("your_image_file_path_here.jpg");
            TrafficCone cone = new TrafficCone(icon, this.getX(), this.getY(), direction);
        }
        if (arg0.getButton() == MouseEvent.BUTTON3)
        {
            TrafficCone.conePlaced = false;
        }
    }
    public void mouseExited(MouseEvent arg0) {}

    public void mouseEntered(MouseEvent arg0) {}

    public void mousePressed(MouseEvent arg0) {}

    public void mouseReleased(MouseEvent arg0) {}

}
