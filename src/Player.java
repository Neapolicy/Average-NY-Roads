import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

public class Player extends JLabel {
    public static String direction = "up";
    private Box playerHitbox; //https://stackoverflow.com/questions/40252221/java-how-to-use-an-object-from-one-mouselistener-to-another-class cheque it out idk
    public Player() throws IOException {
        ImageIcon icon = new ImageIcon(ImageIO.read(new File("ImageFiles/Images/player.png")));
        Image image = icon.getImage();
        Image newimg = image.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        icon = new ImageIcon(newimg);
        setIcon(icon);

        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        this.setBounds((int) size.getWidth() / 2, (int) size.getHeight() / 2, icon.getIconWidth(), icon.getIconHeight());
        this.setOpaque(true);
        playerHitbox = new Box(this.getX(), this.getY(), 50, 50);
    }

    public String getDirection() {
        return direction;
    }


    public Box getPlayerHitbox()
    {
        return playerHitbox;
    }
}
