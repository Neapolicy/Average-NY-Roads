import javax.swing.*;
import java.io.IOException;

public class Startscreen extends Mainframe {
    public Startscreen(){
        startScreen();
    }
    public void startScreen() {
        Scoreboard startText = new Scoreboard("Press E to deploy a bomb", (int) (MyFrame.size.getWidth() / 2), (int) (MyFrame.size.getHeight() / 2) - 100, 1000, 100);

        frame.add(startText);

        startText.setBounds((int) (MyFrame.size.getWidth() / 2), (int) (MyFrame.size.getHeight() / 2) - 100, 1000, 100);
    }

}
