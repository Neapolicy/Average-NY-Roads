import javax.swing.*;

public class MyFrame extends JFrame {
    private Player player;
    private JFrame frame = new JFrame();
    private ImageIcon icon = new ImageIcon("player.jpg");

    public MyFrame() {
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setLayout(null);

        player = new Player(icon);

        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(false);
        frame.addKeyListener(player);
        frame.addMouseListener(player);

        frame.setFocusable(true);

        frame.add(player); //find a way to somehow add the traffic cone AAAAAA
        frame.setVisible(true);
    }

}
