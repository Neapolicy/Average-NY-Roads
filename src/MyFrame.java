import java.awt.*;
import java.util.Random;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;

public class MyFrame extends JFrame implements Runnable { //make this in charge on handling of placing images
    public static int targetFPS = 60;
    public static int targetTime = 1000000000 / targetFPS;
    public static int targetTimeThread = 1000000000;
    public static boolean gameOver;
    private final Random rand = new Random();
    private Bomb bomb;
    private Player player;
    private ArrayList<Car> cars = new ArrayList<>();
    private ArrayList<Pothole> potholes = new ArrayList<>();
    private int timesGenerated;
    public static Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
    private Railroad railroad = new Railroad((int) size.getWidth()); // Create an instance of Railroad
    private Road road; // Create an instance of road
    private Thread thread;
    private Stopwatch s = new Stopwatch();
    private Sound sound = new Sound();
    private int[] car_locations = {300, 400, 500, 600};
    private int[] countDowns = {7, 10};
    private int potholesFilled;
    private int timeLastFilled;
    private int lastBomb;
    private Train train;
    private boolean collision = false;


    public MyFrame() throws IOException { //render the railroad and road first
        setTitle("FWMC RADIO BAU BAU");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 500);
        setLayout(null);
        setLocationRelativeTo(null);

        player = new Player();
        road = new Road((int) size.getWidth());

        addKeyListener(player);

        setExtendedState(JFrame.MAXIMIZED_BOTH);

        add(player);
        add(road);
        add(railroad);

        getContentPane().setBackground(new Color(0, 102, 0));

        railroad.setBounds(0, 100, (int) size.getWidth(), 90); //controls how much railroad it reveals
        road.setBounds(0, (int) (size.getHeight() / 2) - 200, (int) size.getWidth(), (int) (size.getHeight() - 700));

        thread = new Thread(this);
        thread.start();

        setVisible(true);
        gameLoop();
    }


    public void gameLoop() throws IOException {
        while (!gameOver) {
            long startTime = System.nanoTime();
            userKeyInput();
            player.run();
            checkCollision();
            checkPlayerPosition();
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

    public void refresh() {
        revalidate();
        repaint();
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
                add(cars.get(cars.size() - 1));
                Thread.sleep(300);
            }
            Car.speed += 2.5;
        }
    }

    public void trainSummon() throws IOException, InterruptedException {
        if (s.getTimePassed() % 8 == 0 && s.getTimePassed() != 0) {
            train = new Train((int) size.getWidth(), 100);
            add(train);
            repaint();
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
                add(potholes.get(potholes.size() - 1));
            }
            refresh();
        }
    }

    public void checkCollision() { //refer to the hitbox instead
        for (Car car : cars) {
            if (player.getPlayerHitbox().intersects(car.getCarHitbox())) {
                car.killSound(false);
                loseScreen();
            }
            for (Pothole pothole : potholes) {
                if (car.getCarHitbox().intersects(pothole.getPotholeHitbox())) loseScreen();
            }
        }
    }

    public void bombCollision() throws IOException {
        for (int i = 0; i < cars.size(); i++) { //first check if bomb hit car, if true, then remove car
            if (bomb.getBombHitbox().intersects(cars.get(i).getCarHitbox())) {
                remove(cars.get(i));
                cars.remove(cars.get(i));
                sound.play("man_V_machine", false);
                collision = true; //bombed a car, so a pothole won't spawn
            }
        }
        if (!collision) { //checks if you blew up a car, if you didn't, it will create a pothole
            potholes.add(new Pothole(bomb.getX(), bomb.getY())); //fix the thing where blowing up a car creates a pothole
            add(potholes.get(potholes.size() - 1));
        }
        collision = false;
        remove(bomb);
    }

    public void comboManager()  //always increases combo
    {
        player.increaseCombo();
        player.increaseScore((int) s.getTimePassed());
    }

    public void resetCombo() {
        if (s.getTimePassed() - timeLastFilled > 4) {
            player.resetCombo();
        }
    }

    public void checkPlayerPosition() {
        if (player.getX() < 0) player.setLocation(0, player.getY()); //left
        if (player.getX() > size.getWidth() - 500)
            player.setLocation((int) size.getWidth() - 500, player.getY()); //right
        if (player.getY() < 300) player.setLocation(player.getX(), 300); //top
        if (player.getY() > size.getHeight() - 400)
            player.setLocation(player.getX(), (int) size.getHeight() - 400); //bottom
    }

    public void loseScreen() {
        System.out.println("Game Over!");
        System.out.println("You survived for: " + s.getTimePassed() + " seconds");
        System.out.println("Your highest combo was: " + player.getHighestCombo());
        System.out.println("Your score was: " + player.getScore());
        System.out.println("You filled a total of " + potholesFilled + " potholes");
        System.exit(0);
    }

    public void checkCarPositions() {
        for (int i = 0; i < cars.size(); i++) {
            if (cars.get(i).getX() <= -200) {
                remove(cars.get(i));
                cars.remove(cars.get(i));
            }
        }
    }

    public void checkTrainPosition() {
        if (train != null && train.getX() <= -1000) {
            remove(train);
            train = null;
            repaint();
        }
    }

    public void userKeyInput() throws IOException { //current bug: bombs don't display after cone placement
        switch (player.getKeyCode()) //will be necessary once i do the pothole fixing
        {
            case 'e' -> {
                    if ((s.getTimePassed() - lastBomb) >= .1) { //.1 sec cd, prevents double bombing by accident
                        lastBomb = (int) s.getTimePassed();
                        bomb = new Bomb();
                        bomb.setLocation(player.getX(), player.getY(), player.getDirection());
                        add(bomb);
                        bombCollision();
                        bomb = null;
                    }
            }
            case ' ' -> {
                for (int i = 0; i < potholes.size(); i++)
                    if (player.getPlayerHitbox().intersects(potholes.get(i).getPotholeHitbox())) {
                        remove(potholes.get(i));
                        potholes.remove(potholes.get(i));
                        potholesFilled++;
                        timeLastFilled = (int) s.getTimePassed();
                        comboManager();
                    }
            }
        }
        refresh();
        player.resetKeyCode();
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            long startTime = System.nanoTime();
            //do stuff per frame below
            try {
                trainSummon();
                addCars(); //create cars, train ,and potholes
                addPotholes();
                checkCarPositions();
                checkTrainPosition();
                add(road);
                add(railroad);
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