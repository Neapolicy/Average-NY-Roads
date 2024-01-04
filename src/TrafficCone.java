import javax.swing.*;
import java.awt.*;

public class TrafficCone extends JLabel{
    public static boolean conePlaced = false;

    public TrafficCone(ImageIcon icon, int x, int y, String direction)
    {
        super(icon);
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
