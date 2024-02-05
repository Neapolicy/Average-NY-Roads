import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

public class Player extends JLabel implements KeyListener {
    public static String direction = "up";
    private int score;
    private char keyCode;
    private ImageIcon icon;
    private Box playerHitbox; //https://stackoverflow.com/questions/40252221/java-how-to-use-an-object-from-one-mouselistener-to-another-class cheque it out idk
    public Player() throws IOException {
        setIcon("player");

        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        this.setBounds((int) size.getWidth() / 2, (int) size.getHeight() / 2, icon.getIconWidth(), icon.getIconHeight());
        this.setOpaque(true);
        playerHitbox = new Box(this.getX(), this.getY(), 50, 50);
    }

    public void setIcon(String imageName) throws IOException {
        icon = new ImageIcon(ImageIO.read(new File("ImageFiles/Images/" + imageName + ".png")));
        Image image = icon.getImage();
        Image newimg = image.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        icon = new ImageIcon(newimg);
        setIcon(icon);
    }

    public String getDirection() {
        return direction;
    }

    public Box getPlayerHitbox()
    {
        return playerHitbox;
    }
    public int getScore()
    {
        return score;
    }

    public void addScore(int timeBetween, int timeElapsed)
    {
        score += 10;
        if (timeBetween < 3)
        {
            score += 
        }
    }

    private void movePlayer(char keyCode) throws IOException {
        int step = 40;

        switch (keyCode) {
            case 'w' -> {
                Player.direction = "up";
                this.setLocation(this.getX(), this.getY() - step);
                this.getPlayerHitbox().setLocation(this.getX(), this.getY());
            }
            case 'a' -> {
                Player.direction = "left";
                this.setLocation(this.getX() - step, this.getY());
                this.getPlayerHitbox().setLocation(this.getX(), this.getY());
            }
            case 's' -> {
                Player.direction = "down";
                setIcon("player_down");
                this.setLocation(this.getX(), this.getY() + step);
                this.getPlayerHitbox().setLocation(this.getX(), this.getY());
            }
            case 'd' -> {
                Player.direction = "right";
                this.setLocation(this.getX() + step, this.getY());
                this.getPlayerHitbox().setLocation(this.getX(), this.getY());
            }
        }
    }
    public char getKeyCode(){
        return keyCode;
    }

    public void resetKeyCode() {
        keyCode = Character.MIN_VALUE;
    }
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keyCode = e.getKeyChar();
        try {
            movePlayer(keyCode);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
