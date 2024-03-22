package com.game.Screens;

import com.game.Background.Road;
import com.game.Background.Scoreboard;
import com.game.Entities.Bomb;
import com.game.Entities.Car;
import com.game.Entities.Player;
import com.game.Entities.Pothole;
import com.game.Background.Railroad;
import com.game.Misc.Sound;
import com.game.Misc.Spawner;
import com.game.Misc.Stopwatch;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class MyFrame extends Mainframe implements Runnable { //make this in charge on handling of placing images
    public static int targetFPS = 60;
    public static int targetTime = 1000000000 / targetFPS;
    private Bomb bomb;
    private Player player;
    public static ArrayList<Car> cars = new ArrayList<>();
    public static ArrayList<Pothole> potholes = new ArrayList<>();
    private ArrayList<Scoreboard> trackers = new ArrayList<>();
    public static Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
    private Railroad railroad; // Create an instance of com.game.Background.Railroad
    private Road road; // Create an instance of road
    public static Stopwatch s;
    private Sound sound = new Sound();
    private Sound bgMusic = new Sound();
    private int timeLastFilled;
    private int lastBomb;
    private boolean collision = false; //make the frame public lol idk

    public MyFrame() throws IOException { //render the railroad and road first
        bgMusic.setVolume(0.1);
        bgMusic.play("main_game", true);
        gameStart();
    }

    public void gameStart() throws IOException {
        player = new Player();
        road = new Road();
        s = new Stopwatch();
        railroad = new Railroad();
        trackers.add(new Scoreboard("Current Score: 0", (int) (size.getWidth() - 2010), (int) (size.getHeight() / 2) + 150, 500, 200));
        trackers.add(new Scoreboard("Current Combo: 0", (int) (size.getWidth() - 2000), (int) (size.getHeight() / 2) + 200, 500, 200));
        trackers.add(new Scoreboard("Current Time: 0", (int) (size.getWidth() - 2015), (int) (size.getHeight() / 2) + 250, 500, 200));

        frame.addKeyListener(player);

        for (Scoreboard scores : trackers) {
            scores.changeFont("Calibri", 30, new Color(255, 255, 255));
            frame.add(scores);
        }

        frame.getContentPane().setBackground(new Color(0, 102, 0));
        new Spawner();

        Thread thread = new Thread(this);
        thread.start();
    }

    public void gameLoop() throws IOException {
        if (Gamestate.state != Gamestate.gameEnd) { //loop causes program to freeze
            long startTime = System.nanoTime();
            userKeyInput();
            player.run();
            checkCollision();

            resetCombo();
            long totalTime = System.nanoTime() - startTime;

            if (totalTime < targetTime) {
                try {
                    Thread.sleep((targetTime - totalTime) / 1000000);
                } catch (InterruptedException e) {
                    System.out.close();
                }
            }
        }
    }

    public void checkCollision() throws IOException { //refer to the hitbox instead
        for (Car car : cars) {
            if (player.getPlayerHitbox().intersects(car.getCarHitbox())) {
                frame.remove(railroad);
                frame.remove(road);
                Gamestate.state = Gamestate.gameEnd;
                setValues(player.getScore(), player.getHighestCombo(), s.getTimePassed());
                sound.play("car_screech", false);
                checkGameState();
                bgMusic.stopSound();
                break;
            }
            for (Pothole pothole : potholes) {
                if (car.getCarHitbox().intersects(pothole.getPotholeHitbox())) {
                    frame.remove(railroad);
                    frame.remove(road);
                    Gamestate.state = Gamestate.gameEnd;
                    setValues(player.getScore(), player.getCombo(), s.getTimePassed());
                    sound.play("car_screech", false);
                    checkGameState();
                    bgMusic.stopSound();
                    break;
                }
            }
        }
    }

    public void bombCollision() throws IOException {
        Sound bombs = new Sound();
        for (int i = 0; i < cars.size(); i++) {
            if (bomb.getBombHitbox().intersects(cars.get(i).getCarHitbox())) {
                frame.remove(cars.get(i));
                cars.remove(cars.get(i));
                collision = true;
                break;
            }
        }
        if (!collision) {
            potholes.add(new Pothole(bomb.getX(), bomb.getY(), true));
        }
        bombs.play("man_v_machine", false);
        collision = false;
        frame.remove(bomb);
    }

    public void comboManager(boolean bombMade)  //always increases combo
    {
        if (!bombMade) {
            player.increaseCombo();
            trackers.get(1).displayCombo(player.getCombo());
            player.increaseScore(s.getTimePassed());
        }
    }

    public void resetCombo() {
        if (s.getTimePassed() - timeLastFilled > 4) {
            player.resetCombo();
            trackers.get(1).displayCombo(0);
        }
    }

    public void userKeyInput() throws IOException { //current bug: bombs don't display after cone placement
        switch (player.getKeyCode()) //will be necessary once i do the pothole fixing
        {
            case 'e' -> {
                if ((s.getTimePassed() - lastBomb) >= .1) { //.1 sec cd, prevents spam
                    sound.play("bomb_place", false);
                    lastBomb = s.getTimePassed();
                    bomb = new Bomb();
                    bomb.setLocation(player.getX(), player.getY(), player.getDirection());
                    bombCollision();
                    bomb = null;
                }
            }
            case ' ' -> {
                for (int i = 0; i < potholes.size(); i++)
                    if (player.getPlayerHitbox().intersects(potholes.get(i).getPotholeHitbox())) {
                        sound.play("fill", false);
                        comboManager(potholes.get(i).getBombMade());
                        frame.remove(potholes.get(i));
                        potholes.remove(potholes.get(i));
                        timeLastFilled = s.getTimePassed();
                        trackers.get(0).updateScore(player.getScore());
                        break;
                    }
            }
        }
        refresh();
        player.resetKeyCode();
    }

    @Override
    public void run() {
        while (Gamestate.state != Gamestate.gameEnd) {
            long startTime = System.nanoTime();
            //do stuff per frame below
            try {
                trackers.get(2).displayTime(s.getTimePassed()); //updates time counter
                gameLoop();

                frame.add(road);
                frame.add(railroad);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            long totalTime = System.nanoTime() - startTime;

            if (totalTime < targetTime) {
                try {
                    Thread.sleep((targetTime - totalTime) / 1000000); //theoretically should only be running at 1 fps
                } catch (InterruptedException e) {
                    System.out.close();
                }
            }
        }
    }

    public String toString() {
        return "myframe";
    }

    public boolean equals(Object other) {
        return super.equals(other);
    }
}