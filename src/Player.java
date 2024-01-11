import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

public class Player extends JLabel implements KeyListener {
    private String direction = "up";
    private Box playerHitbox; //https://stackoverflow.com/questions/40252221/java-how-to-use-an-object-from-one-mouselistener-to-another-class cheque it out idk
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

    @Override
    public void keyReleased(KeyEvent e) {

    }

    private void movePlayer(char keyCode) {
        int step = 30;

        switch (keyCode) {
            case 'w' -> {
                direction = "up";
                this.setLocation(this.getX(), this.getY() - step);
                playerHitbox.setLocation(this.getX(), this.getY());
            }
            case 'a' -> {
                direction = "left";
                this.setLocation(this.getX() - step, this.getY());
                playerHitbox.setLocation(this.getX(), this.getY());
            }
            case 's' -> {
                direction = "down";
                this.setLocation(this.getX(), this.getY() + step);
                playerHitbox.setLocation(this.getX(), this.getY());
            }
            case 'd' -> {
                direction = "right";
                this.setLocation(this.getX() + step, this.getY());
                playerHitbox.setLocation(this.getX(), this.getY());
            }
        }
        /*System.out.println(this.getX());*/
    }

    public String getDirection() {
        return direction;
    }


    public Box getPlayerHitbox()
    {
        return playerHitbox;
    }
}
