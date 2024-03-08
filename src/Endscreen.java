public class Endscreen extends Mainframe{
    public Endscreen() {
        Scoreboard startText = new Scoreboard("Game Over!", (int) (MyFrame.size.getWidth() / 2), (int) (MyFrame.size.getHeight() / 2) - 100, 1000, 100);

        frame.add(startText);

        startText.setBounds((int) (MyFrame.size.getWidth() / 2), (int) (MyFrame.size.getHeight() / 2) - 100, 1000, 100);
    }
}
