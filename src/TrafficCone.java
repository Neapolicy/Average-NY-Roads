// TrafficCone.java
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class TrafficCone extends JLabel {
    public static boolean conePlaced = false;
    private Box coneHitbox;

    public TrafficCone(int x, int y, String direction) throws IOException {
        ImageIcon icon = new ImageIcon(ImageIO.read(new File("ImageFiles/Images/road_block.png")));
        Image image = icon.getImage();
        Image newimg = image.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        icon = new ImageIcon(newimg);
        setIcon(icon);

        if (!conePlaced) {
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
            conePlaced = true;
        }
    }

    public Box getConeHitbox()
    {
        return coneHitbox;
    }

}
