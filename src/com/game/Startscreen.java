package com.game;
public class Startscreen extends Mainframe {
    public Startscreen(){
        int centerX = (int) (MyFrame.size.getWidth() / 2);
        int centerY = (int) (MyFrame.size.getHeight() / 2);

        Scoreboard titleCard = new Scoreboard("NY Simulator", centerX - 150, centerY - 300, 300, 100);
        Scoreboard startText = new Scoreboard("Press E to Deploy a com.game.Bomb", centerX - 500, centerY, 1000, 100);
        Scoreboard midText = new Scoreboard("Press Space to Fill In The Potholes", centerX - 500, centerY - 100, 1000, 100);
        Scoreboard endText = new Scoreboard("Most Importantly, Don't Let The Cars Run Over You Or The Potholes", centerX - 500, centerY + 100, 1000, 100);
        Scoreboard clickInfo = new Scoreboard("Click Anywhere to Begin", centerX - 500, centerY + 200, 1000, 100);

        frame.add(titleCard);
        frame.add(startText);
        frame.add(midText);
        frame.add(endText);
        frame.add(clickInfo);

        refresh(); //guarentees text appears
    }
}
