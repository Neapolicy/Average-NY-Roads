import javax.swing.*;
public class MyFrame extends JFrame {
    private Player player;

    public MyFrame() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(null);

        ImageIcon icon = new ImageIcon("your_image_file_path_here.jpg");

        player = new Player(icon);

        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setUndecorated(false);
        this.addKeyListener(player);

        this.setFocusable(true);

        this.add(player);
        this.setVisible(true);
    }
}
