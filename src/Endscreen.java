public class Endscreen extends Mainframe{
    private Sound sound = new Sound();
    public Endscreen() {
        sound.play("explosion", false);
        Scoreboard endText = new Scoreboard("Game Over!", (int) (MyFrame.size.getWidth() / 2), (int) (MyFrame.size.getHeight() / 2) - 100, 1000, 100);

        endText.setBounds((int) (MyFrame.size.getWidth() / 2) - 100, (int) (MyFrame.size.getHeight() / 2) - 100, endText.getWidth(), 100);

        frame.add(endText);
        refresh();
    }
}
