import javax.swing.*;

public class Startscreen extends Mainframe{
    private JFrame frame;
    public Startscreen(JFrame frame){
        this.frame = frame;
        startScreen();
    }
    public void startScreen() {
        JButton button = new JButton("Start Game");
        Scoreboard startText = new Scoreboard("Press E to deploy a bomb", (int) (MyFrame.size.getWidth() / 2), (int) (MyFrame.size.getHeight() / 2) - 100, 1000, 100);
        Railroad railroad = new Railroad();
        frame.add(railroad);

        frame.add(startText);
        frame.add(button);

        button.setBounds((int) (MyFrame.size.getWidth() / 2) , (int) (MyFrame.size.getHeight() / 2), 100, 50);
        startText.setBounds((int) (MyFrame.size.getWidth() / 2), (int) (MyFrame.size.getHeight() / 2) - 100, 1000, 100);
        button.addActionListener(e -> Gamestate.state = Gamestate.gameMiddle);
    }
}
