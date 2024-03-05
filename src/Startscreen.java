import javax.swing.*;
import java.io.IOException;

public class Startscreen extends Mainframe {
    public Startscreen(){
        startScreen();
    }
    public void startScreen() {
        JButton button = new JButton("Start Game");
        Scoreboard startText = new Scoreboard("Press E to deploy a bomb", (int) (MyFrame.size.getWidth() / 2), (int) (MyFrame.size.getHeight() / 2) - 100, 1000, 100);

        frame.add(startText);
        frame.add(button);

        button.setBounds((int) (MyFrame.size.getWidth() / 2) , (int) (MyFrame.size.getHeight() / 2), 100, 50);
        startText.setBounds((int) (MyFrame.size.getWidth() / 2), (int) (MyFrame.size.getHeight() / 2) - 100, 1000, 100);
        button.addActionListener(e -> {
            Gamestate.state = Gamestate.gameMiddle;
            frame.getContentPane().removeAll();
            switchStates();
        });
    }

    public void switchStates(){
        try {
            checkGameState();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
