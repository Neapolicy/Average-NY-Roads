// TrafficCone.java
import javax.swing.*;
import java.awt.*;

public class TrafficCone extends JLabel {
    public static boolean conePlaced = false;
    private Box coneHitbox;

    public TrafficCone(ImageIcon icon, int x, int y, String direction) {
        Image image = icon.getImage();
        Image newimg = image.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        icon = new ImageIcon(newimg);

        this.setIcon(icon);
        if (!conePlaced) {
            int coneSize = 50;
            switch (direction) {
                case "up" -> this.setBounds(x, y - coneSize, coneSize, coneSize);
                case "down" -> this.setBounds(x, y + coneSize, coneSize, coneSize);
                case "left" -> this.setBounds(x - coneSize, y, coneSize, coneSize);
                case "right" -> this.setBounds(x + coneSize, y, coneSize, coneSize);
            }
            this.setBackground(Color.ORANGE);
            this.setOpaque(true);
            conePlaced = true;
        }
    }
}
