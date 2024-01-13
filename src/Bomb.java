import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Bomb extends JLabel implements Runnable {
    private Box bombHitbox;
    private Sound sound = new Sound();

    public Bomb() throws IOException {
        ImageIcon icon = new ImageIcon(ImageIO.read(new File("ImageFiles/Images/player.png")));
        Image image = icon.getImage();
        Image newimg = image.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        icon = new ImageIcon(newimg);
        setIcon(icon);
        Thread t = new Thread(this);
        t.start();
    } //has access to set location

    public void setLocation(int x, int y, String direction) {
            int coneSize = 50;
            switch (direction) {
                case "up" -> this.setBounds(x, y - coneSize, coneSize, coneSize);
                case "down" -> this.setBounds(x, y + coneSize, coneSize, coneSize);
                case "left" -> this.setBounds(x - coneSize, y, coneSize, coneSize);
                case "right" -> this.setBounds(x + coneSize, y, coneSize, coneSize);
            }
            this.setBackground(Color.BLACK);
            this.setOpaque(true);
            bombHitbox = (new Box(this.getX(), this.getY(), this.getWidth(), this.getHeight(), Color.RED));
    }
    @Override
    public void run() {
        sound.play("bomb_place", false);
    } //this thing might result in me having to move key listener to myframe but idk lol
    public Box getBombHitbox() {return bombHitbox;}
}
