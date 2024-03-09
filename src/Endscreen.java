public class Endscreen extends Mainframe{
    private Sound sound = new Sound();
    public Endscreen(int score, int combo, int time) {
        sound.play("explosion", false);
        Scoreboard endText = new Scoreboard("Game Over!", (int) (MyFrame.size.getWidth() / 2) - 50, (int) (MyFrame.size.getHeight() / 2) - 200, 1000, 100);
        Scoreboard scoreText = new Scoreboard("Total Score: " + score, (int) (MyFrame.size.getWidth() / 2) - 100, (int) (MyFrame.size.getHeight() / 2) - 100, 1000, 100);
        Scoreboard comboText = new Scoreboard("Highest Combo: " + combo, (int) (MyFrame.size.getWidth() / 2) - 100, (int) (MyFrame.size.getHeight() / 2), 1000, 100);
        Scoreboard timeText = new Scoreboard("Time Elapsed: " + time, (int) (MyFrame.size.getWidth() / 2) - 100, (int) (MyFrame.size.getHeight() / 2) + 100, 1000, 100);

        frame.add(endText);
        frame.add(scoreText);
        frame.add(comboText);
        frame.add(timeText);

        refresh();
    }

}
