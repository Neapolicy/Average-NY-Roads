import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Player extends JLabel implements KeyListener{
    private String direction = "up";
    public Player(ImageIcon icon) {
        super(icon);

        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        this.setBounds((int) size.getWidth()/2, (int) size.getHeight()/2, 50, 50);
        this.setBackground(Color.ORANGE);
        this.setOpaque(true);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Handle keyTyped event if needed
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyChar();
        switch (keyCode) {
            case 'w':
                direction = "up";
                this.setLocation(this.getX(), this.getY() - 30);
                break;
            case 'a':
                direction = "left";
                this.setLocation(this.getX() - 30, this.getY());
                break;
            case 's':
                direction = "down";
                this.setLocation(this.getX(), this.getY() + 30);
                break;
            case 'd':
                direction = "right";
                this.setLocation(this.getX() + 30, this.getY());
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Handle keyReleased event if needed
    }
}
