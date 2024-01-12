// TrafficCone.java
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class TrafficCone extends JLabel {
    public static int conesPlaced;
    private Box coneHitbox;

    public TrafficCone() throws IOException {
        ImageIcon icon = new ImageIcon(ImageIO.read(new File("ImageFiles/Images/road_block.png")));
        Image image = icon.getImage();
        Image newimg = image.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        icon = new ImageIcon(newimg);
        setIcon(icon);
        coneHitbox = new Box(this.getX(), this.getY(), 50, 50, Color.CYAN);
        }

    public void setLocation(int x, int y, String direction) {
        if (conesPlaced < 1) {
            int coneSize = 50;
            switch (direction) {
                case "up" -> this.setBounds(x, y - coneSize, coneSize, coneSize);
                case "down" -> this.setBounds(x, y + coneSize, coneSize, coneSize);
                case "left" -> this.setBounds(x - coneSize, y, coneSize, coneSize);
                case "right" -> this.setBounds(x + coneSize, y, coneSize, coneSize);
            }
            this.setBackground(Color.BLACK);
            this.setOpaque(true);
            coneHitbox = (new Box(this.getX(), this.getY(), this.getWidth(), this.getHeight(), Color.RED));
        }
        capConesPlaced();
    }

    public void capConesPlaced(){if (conesPlaced > 1) conesPlaced = 1;}

    public Box getConeHitbox() {return coneHitbox;}

}
