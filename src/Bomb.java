import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Bomb extends TrafficCone implements Runnable {
    private Box bombHitbox;
    private Sound sound = new Sound();

    public Bomb() throws IOException {
        ImageIcon icon = new ImageIcon(ImageIO.read(new File("ImageFiles/Images/road_block.png")));
        Image image = icon.getImage();
        Image newimg = image.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        icon = new ImageIcon(newimg);
        setIcon(icon);
        bombHitbox = new Box(this.getX(), this.getY(), 50, 50, Color.CYAN);
        Thread t = new Thread(this);
        t.start();
    } //has access to set location
    @Override
    public void run() {
        sound.play("bomb_place", false);
        try {
            Thread.sleep(400);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        sound.play("man_V_machine", false);
    } //this thing might result in me having to move key listener to myframe but idk lol
    public Box getBombHitbox() {return bombHitbox;}
}
