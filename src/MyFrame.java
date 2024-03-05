import java.awt.*;
import java.util.Random;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;

public class MyFrame extends Mainframe implements Runnable { //make this in charge on handling of placing images
    public static int targetFPS = 60;
    public static int targetTime = 1000000000 / targetFPS;
    public static int targetTimeThread = 1000000000;
    private final Random rand = new Random();
    private Bomb bomb;
    private Player player;
    private ArrayList<Car> cars = new ArrayList<>();
    private ArrayList<Pothole> potholes = new ArrayList<>();
    private int timesGenerated;
    public static Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
    private Railroad railroad; // Create an instance of Railroad
    private Scoreboard scoreInfo;
    private Scoreboard timeInfo;
    private Scoreboard comboInfo;
    private Road road; // Create an instance of road
    private Stopwatch s;
    private Sound sound = new Sound();
    private int[] car_locations = {300, 400, 500, 600};
    private int[] countDowns = {7, 10};
    private int timeLastFilled;
    private int lastBomb;
    private boolean collision = false; //make the frame public lol idk

    public MyFrame() throws IOException { //render the railroad and road first
        gameStart();
    }

    public void gameStart() throws IOException {
        player = new Player();
        road = new Road();
        s = new Stopwatch();
        railroad = new Railroad();
        scoreInfo = new Scoreboard("Current Score: 0", 100, (int) (size.getHeight() / 2) + 150, 500, 200);
        comboInfo = new Scoreboard("Current Combo: 0", 100, (int) (size.getHeight() / 2) + 200, 500, 200);
        timeInfo = new Scoreboard("Current Time: 0", 100, (int) (size.getHeight() / 2) + 250, 500, 200);

        frame.addKeyListener(player);

        frame.add(scoreInfo); //text
        frame.add(comboInfo);
        frame.add(timeInfo);

        frame.getContentPane().setBackground(new Color(0, 102, 0));

        Thread thread = new Thread(this);
        thread.start();

        gameLoop();
    }

    public void gameLoop() throws IOException {
        if (Gamestate.state != Gamestate.gameEnd) {
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
                    for (Car car : cars)
                        car.killSound(false);
                    System.out.close();
                }
            }
        }
    }

    public void addCars() throws IOException, InterruptedException //creates new cars
    {
        if (s.getTimePassed() % (countDowns[1] - (s.getTimePassed() / 15)) == 0 && s.getTimePassed() != 0) //5 seconds to add a car is purely for testing purposes, also rewrite this without using sleep
        {
            int lastSpawnedYCoord = 0;
            if (countDowns[1] < 6) countDowns[1] = 6; //spawn timer min
            timesGenerated = rand.nextInt(1, 5);
            for (int i = 0; i < timesGenerated; i++) {
                int y_axis = rand.nextInt(car_locations.length);
                while (y_axis == lastSpawnedYCoord) y_axis = rand.nextInt(car_locations.length);
                cars.add(new Car((int) size.getWidth() + 500, car_locations[y_axis]));
                lastSpawnedYCoord = car_locations[y_axis];
                Thread.sleep(300);
            }
            Car.speed += 2.5;
        }
    }

    public void trainSummon() throws IOException, InterruptedException {
        if (s.getTimePassed() % 8 == 0 && s.getTimePassed() != 0) {
            new Train((int) size.getWidth(), 100);
        }
    }

    public void addPotholes() throws InterruptedException, IOException {
        if (s.getTimePassed() % (countDowns[0] - s.getTimePassed() / 10) == 0 && s.getTimePassed() != 0) //5 seconds to add a car is purely for testing purposes
        {
            if (countDowns[0] < 3) countDowns[0] = 3;
            timesGenerated = rand.nextInt(1, 4);
            int y_axis = rand.nextInt(300, 600);
            int x_axis = rand.nextInt(100, 600);
            for (int i = 0; i < timesGenerated; i++) {
                potholes.add(new Pothole(x_axis, y_axis));
            }
            refresh();
        }
    }

    public void checkCollision() { //refer to the hitbox instead
        for (Car car : cars) {
            if (player.getPlayerHitbox().intersects(car.getCarHitbox())) {
                car.killSound(false);
                Gamestate.state = Gamestate.gameEnd;
                Mainframe.frame.getContentPane().removeAll();
            }
            for (Pothole pothole : potholes) {
                if (car.getCarHitbox().intersects(pothole.getPotholeHitbox())){
                    Gamestate.state = Gamestate.gameEnd;
                    Mainframe.frame.getContentPane().removeAll();
                }
            }
        }
    }

    public void bombCollision() throws IOException {
        for (int i = 0; i < cars.size(); i++) { //first check if bomb hit car, if true, then remove car
            if (bomb.getBombHitbox().intersects(cars.get(i).getCarHitbox())) {
                frame.remove(cars.get(i));
                cars.remove(cars.get(i));
                sound.play("man_V_machine", false);
                collision = true; //bombed a car, so a pothole won't spawn
            }
        }
        if (!collision) { //checks if you blew up a car, if you didn't, it will create a pothole
            potholes.add(new Pothole(bomb.getX(), bomb.getY())); //fix the thing where blowing up a car creates a pothole
        }
        collision = false;
        frame.remove(bomb);
    }

    public void comboManager()  //always increases combo
    {
        player.increaseCombo();
        comboInfo.displayCombo(player.getCombo());
        player.increaseScore(s.getTimePassed());
    }

    public void resetCombo() {
        if (s.getTimePassed() - timeLastFilled > 4) {
            player.resetCombo();
            comboInfo.displayCombo(0);
        }
    }

    public void userKeyInput() throws IOException { //current bug: bombs don't display after cone placement
        switch (player.getKeyCode()) //will be necessary once i do the pothole fixing
        {
            case 'e' -> {
                if ((s.getTimePassed() - lastBomb) >= .1) { //.1 sec cd, prevents spam
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
                        frame.remove(potholes.get(i));
                        potholes.remove(potholes.get(i));
                        timeLastFilled = s.getTimePassed();
                        comboManager();
                        scoreInfo.updateScore(player.getScore());
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
                timeInfo.displayTime(s.getTimePassed()); //updates time counter
                trainSummon();
                addCars(); //create cars, train ,and potholes
                // addPotholes();

                frame.add(road);
                frame.add(railroad);
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }

            long totalTime = System.nanoTime() - startTime;

            if (totalTime < targetTimeThread) {
                try {
                    Thread.sleep((targetTimeThread - totalTime) / 1000000); //theoretically should only be running at 1 fps
                } catch (InterruptedException e) {
                    for (Car car : cars)
                        car.killSound(false);
                    System.out.close();
                }
            }
        }
    }
}