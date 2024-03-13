package com.game.Screens;

import com.game.Background.Scoreboard;
import com.game.Misc.Sound;

import java.awt.*;

public class Endscreen extends Mainframe {
    private Sound sound = new Sound();
    public Endscreen(int score, int combo, int time) {
        MyFrame.cars.clear();
        MyFrame.potholes.clear();
        int centerX = (int) (MyFrame.size.getWidth() / 2);
        int centerY = (int) (MyFrame.size.getHeight() / 2);

        sound.play("explosion", false);
        frame.getContentPane().setBackground(new Color(29, 201, 183));

        Scoreboard endText = new Scoreboard("Game Over!", centerX - 500, centerY - 175, 1000, 100);
        Scoreboard scoreText = new Scoreboard("Total Score: " + score, centerX - 500, centerY - 125, 1000, 100);
        Scoreboard comboText = new Scoreboard("Highest Combo: " + combo, centerX - 500, centerY - 75, 1000, 100);
        Scoreboard timeText = new Scoreboard("Time Elapsed: " + time, centerX - 500, centerY - 25, 1000, 100);
        Scoreboard restart = new Scoreboard("Click Anywhere to Restart", centerX - 500, centerY + 25, 1000, 100);

        endText.changeFont("Calibri", 30, new Color(255, 255, 255));
        scoreText.changeFont("Calibri", 30, new Color(255, 255, 255));
        comboText.changeFont("Calibri", 30, new Color(255, 255, 255));
        timeText.changeFont("Calibri", 30, new Color(255, 255, 255));
        restart.changeFont("Calibri", 30, new Color(255, 255, 255));

        frame.add(endText);
        frame.add(scoreText);
        frame.add(comboText);
        frame.add(timeText);
        frame.add(restart);

        refresh();
    }
    public String toString(){
        return "endscreen";
    }
    public boolean equals(Object other){
        return super.equals(other);
    }
}
