import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

public class Mainframe extends JFrame implements MouseListener {
    public static JFrame frame = new JFrame();
    private Sound sound = new Sound();
    private int score;
    private int combo;
    private int time;
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
        if (Gamestate.state == Gamestate.gameStart) {
            sound.play("car_screech", true); //replace with soundtrack
            new Startscreen();
        }
        if (Gamestate.state == Gamestate.gameMiddle) {
            sound.play("car_screech", true);
            new MyFrame();
        }
        if (Gamestate.state == Gamestate.gameEnd) {
            frame.getContentPane().removeAll();
            new Endscreen(score, combo, time);
        }
    }

    public static void refresh() {
        frame.revalidate();
        frame.repaint();
    }
    public void setValues(int score, int combo, int time){
        this.score = score;
        this.combo = combo;
        this.time = time;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (Gamestate.state == Gamestate.gameStart){
            Gamestate.state = Gamestate.gameMiddle;
            frame.getContentPane().removeAll();
            frame.removeMouseListener(this);
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
