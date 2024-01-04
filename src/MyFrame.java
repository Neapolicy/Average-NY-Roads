import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class MyFrame extends JFrame { //make this in charge on handling of placing images
    private Player player;
    private JFrame frame = new JFrame();

    public MyFrame() throws IOException {
        while
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setLayout(null);

        ImageIcon icon = new ImageIcon(ImageIO.read(new File("Images/Player/player.png")));
        player = new Player(icon);

        frame.setUndecorated(false);
        frame.addKeyListener(player);
        frame.addMouseListener(player);

        frame.setFocusable(true);
        createCone();

        /*frame.setContentPane(new JLabel(icon));*/
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        frame.add(player); //find a way to somehow add the traffic cone AAAAAA

        frame.setVisible(true);
    }

    public void createCone() {
        if (TrafficCone.conePlaced) {
            ImageIcon coneIcon = new ImageIcon("Images/Player/player.png");
            TrafficCone cone = new TrafficCone(coneIcon, player.getX(), player.getY(), player.getDirection());
            frame.add(cone);
        }
        TrafficCone.conePlaced = false;
    }

}