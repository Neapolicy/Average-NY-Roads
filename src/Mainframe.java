import javax.swing.*;
import java.io.IOException;

public class Mainframe extends JFrame {
    public JFrame frame = new JFrame();
    public void start() throws IOException {
        frame.setTitle("FWMC RADIO BAU BAU");
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setSize(900, 700);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
        new MyFrame(frame);
    }
}
