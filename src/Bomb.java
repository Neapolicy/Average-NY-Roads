import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Bomb extends JLabel implements Runnable {
    private Box bombHitbox;
    private Sound sound = new Sound();

    public Bomb() throws IOException {
        ImageIcon icon = new ImageIcon(ImageIO.read(new File("ImageFiles/Images/bomb.png")));
        Image image = icon.getImage();
        Image newimg = image.getScaledInstance(80, 80, Image.SCALE_SMOOTH);
        icon = new ImageIcon(newimg);
        setIcon(icon);
        Thread t = new Thread(this);
        t.start();
        Mainframe.frame.add(this);
    }

    public void setLocation(int x, int y, String direction) {
        int distanceFromPlayer = 50;
        switch (direction) {
            case "up" -> this.setBounds(x, y - distanceFromPlayer, 80, 80);
            case "down" -> this.setBounds(x, y + distanceFromPlayer, 80, 80);
            case "left" -> this.setBounds(x - distanceFromPlayer, y, 80, 80);
            case "right" -> this.setBounds(x + distanceFromPlayer, y, 80, 80);
        }
        this.setBackground(Color.BLACK);
        this.setOpaque(false);
        bombHitbox = (new Box(this.getX(), this.getY(), this.getWidth(), this.getHeight()));
    }

    @Override
    public void run() {
        sound.play("explosion", false);
    } //this thing might result in me having to move key listener to myframe but idk lol

    public Box getBombHitbox() {
        return bombHitbox;
    }
}
