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
    private int combo;
    private int highestCombo;
    private char keyCode;
    private ImageIcon icon;
    private boolean up;
    private boolean down;
    private boolean left;
    private boolean right;
    private int step = 15;
    private Box playerHitbox; //https://stackoverflow.com/questions/40252221/java-how-to-use-an-object-from-one-mouselistener-to-another-class cheque it out idk

    public Player() throws IOException {
        setIcon("player_right");

        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        this.setBounds((int) size.getWidth() / 2, (int) size.getHeight() / 2, icon.getIconWidth(), icon.getIconHeight());
        this.setOpaque(false);
        playerHitbox = new Box(this.getX(), this.getY(), 50, 50);
        Mainframe.frame.add(this);
    }

    public void setIcon(String imageName) throws IOException {
        icon = new ImageIcon(ImageIO.read(new File("ImageFiles/Images/" + imageName + ".png")));
        Image image = icon.getImage();
        Image newimg = image.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        icon = new ImageIcon(newimg);
        setIcon(icon);
    }

    public void increaseScore(int timeElapsed) //time between filling potholes
    {
        int moneyMade = 10 + (timeElapsed / 4);
        if (combo != 1) {
            moneyMade *= 1 + (((double) combo) / 10); //multiplies by combo/10
        }
        score += moneyMade;
    }

    public void resetCombo() {
        combo = 0;
    }

    public void increaseCombo() {
        combo++;
        if (combo > highestCombo) highestCombo = combo;
    }

    public String getDirection() {
        return direction;
    }

    public Box getPlayerHitbox() {
        return playerHitbox;
    }

    public int getScore() {
        return score;
    }

    public int getHighestCombo() {
        return highestCombo;
    }
    public int getCombo(){return combo;}
    private void movePlayer(char keyCode) throws IOException {
        switch (keyCode) {
            case 'w' -> {
                Player.direction = "up";
                up = true;
                setIcon("player_up");
            }
            case 'a' -> {
                left = true;
                Player.direction = "left";
                setIcon("player_left");
            }
            case 's' -> {
                down = true;
                Player.direction = "down";
                setIcon("player_down");
            }
            case 'd' -> {
                right = true;
                Player.direction = "right";
                setIcon("player_right");
            }
        }
    }

    public char getKeyCode() {
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
        keyCode = e.getKeyChar();
        switch (keyCode) {
            case 'w' -> up = false;
            case 'a' -> left = false;
            case 's' -> down = false;
            case 'd' -> right = false;
        }
    }

    public void run() {
        if (up) {
            this.setLocation(this.getX(), this.getY() - step);
            this.getPlayerHitbox().setLocation(this.getX(), this.getY());
        }
        if (down) {
            this.setLocation(this.getX(), this.getY() + step);
            this.getPlayerHitbox().setLocation(this.getX(), this.getY());
        }
        if (left) {
            this.setLocation(this.getX() - step, this.getY());
            this.getPlayerHitbox().setLocation(this.getX(), this.getY());
        }
        if (right) {
            this.setLocation(this.getX() + step, this.getY());
            this.getPlayerHitbox().setLocation(this.getX(), this.getY());
        }
                checkPlayerPosition();
    }
    public void checkPlayerPosition() {
        if (this.getX() < 0) this.setLocation(0, this.getY()); //left
        if (this.getX() > MyFrame.size.getWidth() - 500)
        this.setLocation((int) MyFrame.size.getWidth() - 500, this.getY()); //right
        if (this.getY() < 300) this.setLocation(this.getX(), 300); //top
        if (this.getY() > MyFrame.size.getHeight() - 400)
        this.setLocation(this.getX(), (int) MyFrame.size.getHeight() - 400); //bottom
    }
}
