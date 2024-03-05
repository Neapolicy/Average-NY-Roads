import javax.swing.*;
import java.io.IOException;

public class Mainframe extends JFrame {
    public static JFrame frame = new JFrame();
    public void start() throws IOException {
        frame.setTitle("FWMC RADIO BAU BAU");
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setSize(900, 700);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);

        checkGameState();
    }
    public void checkGameState() throws IOException {
        if (Gamestate.state == Gamestate.gameStart) new Startscreen();
        if (Gamestate.state == Gamestate.gameMiddle) new MyFrame();
    }

    public static void refresh() {
        frame.revalidate();
        frame.repaint();
    }
}
