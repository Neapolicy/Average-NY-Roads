package com.game.Screens;

import com.game.Background.Scoreboard;
import com.game.Screens.Mainframe;
import com.game.Screens.MyFrame;

import java.awt.*;

public class Startscreen extends Mainframe {
    public Startscreen(){
        int centerX = (int) (MyFrame.size.getWidth() / 2);
        int centerY = (int) (MyFrame.size.getHeight() / 2);
        frame.getContentPane().setBackground(new Color(107, 25, 179));

        Scoreboard titleCard = new Scoreboard("NY Simulator", centerX - 150, centerY - 225, 300, 100);
        Scoreboard startText = new Scoreboard("Press E to Deploy a Bomb", centerX - 500, centerY - 150, 1000, 100);
        Scoreboard midText = new Scoreboard("Press Space to Fill In The Potholes", centerX - 500, centerY - 75, 1000, 100);
        Scoreboard endText = new Scoreboard("Most Importantly, Don't Let The Cars Run Over You Or The Potholes", centerX - 500, centerY, 1000, 100);
        Scoreboard clickInfo = new Scoreboard("Click Anywhere to Begin", centerX - 500, centerY + 75, 1000, 100);

        titleCard.boldFont("Calibri", 50, new Color(255, 255, 255));
        startText.changeFont("Calibri", 30, new Color(255, 255, 255));
        midText.changeFont("Calibri", 30, new Color(255, 255, 255));
        endText.changeFont("Calibri", 30, new Color(255, 255, 255));
        clickInfo.changeFont("Calibri", 30, new Color(255, 255, 255));

        frame.add(titleCard);
        frame.add(startText);
        frame.add(midText);
        frame.add(endText);
        frame.add(clickInfo);

        refresh(); //guarentees text appears
    }
}
