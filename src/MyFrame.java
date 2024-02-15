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
    private TrafficCone cone; //might make this an arraylist??
    private ArrayList<Car> cars = new ArrayList<>();
    private ArrayList<Pothole> potholes = new ArrayList<>();
    private int timesGenerated;
    private JFrame frame = new JFrame();
    private Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
    private Thread thread;
    private Stopwatch s = new Stopwatch();
    private Sound sound = new Sound();
    private int[] car_locations = {300, 400, 500, 600};
    private int[] countDowns = {7, 10};
    private int potholesFilled;
    private int timeLastFilled;
    private Train train;
    private boolean collision = false;;

    public MyFrame() throws IOException, InterruptedException { //https://stackoverflow.com/questions/2141019/how-can-i-check-if-something-leaves-the-screen-jframe car leaves screen idfk
        JLabel background;
        frame.setSize((int) size.getWidth(), (int) size.getHeight());
        frame.setLayout(null);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setTitle("FWMC RADIO BAU BAU");

        player = new Player();
        ImageIcon image = new ImageIcon("ImageFiles/Images/player.png"); //has to be png idk??
        background = new JLabel("", image, JLabel.CENTER);
        background.setBounds(0, 0, frame.getWidth(), frame.getHeight());

        frame.setUndecorated(false);
        frame.addKeyListener(player);

        frame.setFocusable(true);

        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        frame.setVisible(true);
        frame.add(player);
        /*frame.add(background);*/

        thread = new Thread(this);
        thread.start();
        while (!gameOver)
        {
            long startTime = System.nanoTime();
            userKeyInput();
            checkCollision();
            conePlacement();
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


    public void addCars() throws IOException, InterruptedException //creates new cars
    {
        if (s.getTimePassed() % (countDowns[1] - (s.getTimePassed() / 15)) == 0) //5 seconds to add a car is purely for testing purposes, also rewrite this without using sleep
        {
            int lastSpawnedYCoord = 0;
            if (countDowns[1] < 6) countDowns[1] = 6; //spawn timer min
            timesGenerated = rand.nextInt(1, 5);
            for (int i = 0; i < timesGenerated; i++) {
                int y_axis = rand.nextInt(car_locations.length);
                while (y_axis == lastSpawnedYCoord) y_axis = rand.nextInt(car_locations.length);
                cars.add(new Car(frame.getWidth(), car_locations[y_axis]));
                lastSpawnedYCoord = car_locations[y_axis];
                frame.add(cars.get(cars.size() - 1));
            }
            Car.step += 2.5;
            Car.speed = Car.step;
        }
         //method works woohoo
    }

    public void trainSummon() throws IOException, InterruptedException {
        if (s.getTimePassed() % 8 == 0) //5 seconds to add a car is purely for testing purposes
        {
            train = new Train(frame.getWidth(), 100);
            frame.add(train);
        }
    }

    public void addPotholes() throws InterruptedException, IOException {
        if (s.getTimePassed() % (countDowns[0] - s.getTimePassed() / 10) == 0 && s.getTimePassed() != 0) //5 seconds to add a car is purely for testing purposes
        {
            if (countDowns[0] < 3) countDowns[0] = 3;
            timesGenerated = rand.nextInt(1, 4);
            int y_axis = rand.nextInt(300, 600);
            int x_axis = rand.nextInt(100, 600);
            for (int i = 0; i < timesGenerated; i++)
            {
                potholes.add(new Pothole(x_axis, y_axis));
                frame.add(potholes.get(potholes.size() - 1));
            }
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
            if (cone != null && cone.getConeHitbox() != null) {
                if (cone.getConeHitbox().intersects(car.getCarHitbox())) { 
                    car.killSound(false);
                    car.stopCar();
                } else {
                    car.setSpeed(Car.speed);// allows car to play audio again once no longer blocked by cone
                }
            }
        }
    }

    public void conePlacement() {
        if (cone != null && cone.getConeHitbox() != null) {
            if (player.getPlayerHitbox().intersects(cone.getConeHitbox())) {
                switch (player.getDirection()) {
                    case "up":
                        player.setLocation(player.getX(), player.getY() + 60);
                        player.getPlayerHitbox().setLocation(player.getX(), player.getY());
                    case "down":
                        player.setLocation(player.getX(), player.getY() - 30);
                        player.getPlayerHitbox().setLocation(player.getX(), player.getY());
                    case "left":
                        player.setLocation(player.getX() + 60, player.getY());
                        player.getPlayerHitbox().setLocation(player.getX(), player.getY());
                    case "right":
                        player.setLocation(player.getX() - 30, player.getY());
                        player.getPlayerHitbox().setLocation(player.getX(), player.getY());
                }
            }
        }
    } //genius idea for completely eliminating rectangles, make it a arraylist for each class/object, and remove it when you do frame.remove
    //have it call a method that will remove the rectangle wooo I AM A FXCKING GENIUS

    public void bombCollision() throws IOException {
        for (int i = 0; i < cars.size(); i++) { //first check if bomb hit car, if true, then remove car
            if (bomb.getBombHitbox().intersects(cars.get(i).getCarHitbox())) {
                frame.remove(cars.get(i));
                cars.remove(cars.get(i));
                Bomb.bombCount++;
                sound.play("man_V_machine", false);
                collision = true;
            }
        }
        if (cone != null){ //checks of there even is a cone
            if (bomb.getBombHitbox().intersects(cone.getConeHitbox())) {
                frame.remove(cone);
                TrafficCone.conesPlaced--;  //checks for cone, if there is, remove it
            }
        } else if (!collision){ //checks if you blew up a car
            potholes.add(new Pothole(bomb.getX(), bomb.getY())); //fix the thing where blowing up a car creates a pothole
            frame.add(potholes.get(potholes.size() - 1));                                                    //this is the cause for the orange square
        }                                                 //bc that was supposed to be the pothole being created
        frame.remove(bomb);
        frame.revalidate();
        frame.repaint();
    }

    public void comboManager()  //always increases combo
    {
        player.increaseCombo();
        player.increaseScore((int) s.getTimePassed());
    }

    public void resetCombo()
    {
        if (s.getTimePassed() - timeLastFilled > 4)
        {
            player.resetCombo();
        }
    }

    public void checkPlayerPosition() {
        if (player.getX() < 0) player.setLocation(0, player.getY());
        if (player.getX() > size.getWidth() - 500) player.setLocation((int) size.getWidth() - 500, player.getY());
        if (player.getY() < 300) player.setLocation(player.getX(), 300);
        if (player.getY() > size.getHeight() - 400) player.setLocation(player.getX(), (int) size.getHeight() - 400);
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
                frame.remove(cars.get(i));
                cars.remove(cars.get(i));
                frame.revalidate();
                frame.repaint();
            }
        }
    }

    public void checkTrainPosition()
    {
        if (train.getX() <= -1000)
        {
            frame.remove(train);
            frame.revalidate();
            frame.repaint();
        }
    }

    public void userKeyInput() throws IOException { //current bug: bombs don't display after cone placement
        switch (player.getKeyCode()) //will be necessary once i do the pothole fixing
        {
            case 'e' -> {
                if (Bomb.bombCount > 0) {
                    bomb = new Bomb();
                    bomb.setLocation(player.getX(), player.getY(), player.getDirection());
                    frame.add(bomb);
                    bombCollision();
                    Bomb.bombCount--;
                }
            }
            case ' ' -> {
                for (int i = 0; i < potholes.size(); i++)
                    if (player.getPlayerHitbox().intersects(potholes.get(i).getPotholeHitbox())) {
                        frame.remove(potholes.get(i));
                        potholes.remove(potholes.get(i));
                        potholesFilled++;
                        timeLastFilled = (int) s.getTimePassed();
                        comboManager();
                    }
            }
            case 'q' ->{
                if (TrafficCone.conesPlaced < 1) {
                    cone = new TrafficCone();
                    cone.setLocation(player.getX(), player.getY(), player.getDirection());
                    frame.add(cone);
                    TrafficCone.conesPlaced++;
                }
                else if (TrafficCone.conesPlaced == 1) {
                    frame.remove(cone);
                    cone = null;
                    TrafficCone.conesPlaced--;
                }
            }
        }
        frame.revalidate();
        frame.repaint();
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
                frame.revalidate();
                frame.repaint();
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }

            long totalTime = System.nanoTime() - startTime;

            if (totalTime < targetTimeThread) {
                try {
                    Thread.sleep((targetTimeThread - totalTime) / 1000000);
                } catch (InterruptedException e) {
                    for (Car car : cars)
                        car.killSound(false);
                    System.out.close();
                }
            }
        }
    }
}