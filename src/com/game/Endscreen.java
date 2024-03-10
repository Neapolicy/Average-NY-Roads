package com.game;

public class Endscreen extends Mainframe {
    private Sound sound = new Sound();
    public Endscreen(int score, int combo, int time) {
        int centerX = (int) (MyFrame.size.getWidth() / 2);
        int centerY = (int) (MyFrame.size.getHeight() / 2);

        sound.play("explosion", false);

        Scoreboard endText = new Scoreboard("Game Over!", centerX - 500, centerY - 200, 1000, 100);
        Scoreboard scoreText = new Scoreboard("Total Score: " + score, centerX - 500, centerY - 100, 1000, 100);
        Scoreboard comboText = new Scoreboard("Highest Combo: " + combo, centerX - 500, centerY, 1000, 100);
        Scoreboard timeText = new Scoreboard("Time Elapsed: " + time, centerX - 500, centerY + 100, 1000, 100);
        Scoreboard restart = new Scoreboard("Click Anywhere to Restart", centerX - 500, centerY + 200, 1000, 100);

        frame.add(endText);
        frame.add(scoreText);
        frame.add(comboText);
        frame.add(timeText);
        frame.add(restart);

        refresh();
    }

}
