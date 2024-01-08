import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

public class Player extends JLabel implements KeyListener, MouseListener {
    private String direction = "up";
    private Box playerHitbox;

    public Player() throws IOException {
        ImageIcon icon = new ImageIcon(ImageIO.read(new File("ImageFiles/Images/player.png")));
        Image image = icon.getImage();
        Image newimg = image.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        icon = new ImageIcon(newimg);
        setIcon(icon);

        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        this.setBounds((int) size.getWidth() / 2, (int) size.getHeight() / 2, icon.getIconWidth(), icon.getIconHeight());
        this.setOpaque(true);
        playerHitbox = new Box(this.getX(), this.getY(), 50, 50, Color.ORANGE);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        char keyCode = e.getKeyChar();
        movePlayer(keyCode);
    }

    private void movePlayer(char keyCode) {
        int step = 30;

        switch (keyCode) {
            case 'w' -> {
                direction = "up";
                this.setLocation(this.getX(), this.getY() - step);
                playerHitbox.setLocation(this.getX(), this.getY() - step);
            }
            case 'a' -> {
                direction = "left";
                this.setLocation(this.getX() - step, this.getY());
                playerHitbox.setLocation(this.getX() - step, this.getY());
                System.out.println(playerHitbox.getX());
                System.out.println(this.getX());
            }
            case 's' -> {
                direction = "down";
                this.setLocation(this.getX(), this.getY() + step);
                playerHitbox.setLocation(this.getX(), this.getY() + step);
            }
            case 'd' -> {
                direction = "right";
                this.setLocation(this.getX() + step, this.getY());
                playerHitbox.setLocation(this.getX() + step, this.getY());
            }
        }
        /*System.out.println(this.getX());*/
    }

    public String getDirection() {
        return direction;
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            TrafficCone.conePlaced = true;
        } else if (e.getButton() == MouseEvent.BUTTON3) {
            TrafficCone.conePlaced = false;
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    public Box getPlayerHitbox()
    {
        return playerHitbox;
    }

}
