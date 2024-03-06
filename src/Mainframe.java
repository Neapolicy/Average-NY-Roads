import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

public class Mainframe extends JFrame implements MouseListener {
    public static JFrame frame = new JFrame();
    public void start() throws IOException {
        frame.setTitle("FWMC RADIO BAU BAU");
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setSize(900, 700);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
        frame.addMouseListener(this);

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

    @Override
    public void mouseClicked(MouseEvent e) {
        if (Gamestate.state == Gamestate.gameStart){
            Gamestate.state = Gamestate.gameMiddle;
            frame.getContentPane().removeAll();
        }
        try {
            checkGameState();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
