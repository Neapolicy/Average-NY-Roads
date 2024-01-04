import javax.swing.*;
import java.awt.*;

public class TrafficCone extends JLabel{
    public static boolean conePlaced = false;

    public TrafficCone(ImageIcon icon, int x, int y, String direction)
    {
        Image image = icon.getImage(); // transform it
        Image newimg = image.getScaledInstance(50, 50,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        icon = new ImageIcon(newimg);  // transform it back

        this.setIcon(icon);
        if (!conePlaced)
        {
            switch (direction)
            {
                case "up" -> this.setBounds(x, y - 50, 50, 50);
                case "down" -> this.setBounds(x, y + 50, 50, 50);
                case "left" -> this.setBounds(x - 50, y, 50, 50);
                case "right" -> this.setBounds(x + 50, y, 50, 50);
            }
            this.setBackground(Color.ORANGE);
            this.setOpaque(true);
            conePlaced = true;
        }
    }
}
