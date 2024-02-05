import java.awt.*;
import java.util.Random;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;

public class MyFrame extends JFrame implements Runnable { //make this in charge on handling of placing images
    public static int targetFPS = 40;
    public static int targetTime = 1000000000 / targetFPS;
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
    private int[] pothole_locations_Y = {300, 400};
    private int[] pothole_locations_X = {500, 600};
    private int potholesFilled;
    private int timeLastFilled;
    private Train train;

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
            roadBlock();
            checkCollision();
            conePlacement();
            checkPlayerPosition();
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
        if (s.getTimePassed() % 10 == 0) //5 seconds to add a car is purely for testing purposes
        {
            int y_axis = rand.nextInt(car_locations.length);
            for (int i = 0; i < timesGenerated; i++) {
                cars.add(new Car(frame.getWidth(), car_locations[y_axis]));
                frame.add(cars.get(cars.size() - 1));
                Thread.sleep(300);
                Car.step += 2.5;
                Car.speed = Car.step;
            }
            Thread.sleep(700);
        }
        timesGenerated = rand.nextInt(1, 2); //method works woohoo
    }

    public void trainSummon() throws IOException, InterruptedException {
        if (s.getTimePassed() % 8 == 0) //5 seconds to add a car is purely for testing purposes
        {
            train = new Train(frame.getWidth(), 100);
            frame.add(train);
            Thread.sleep(1000);
        }
    }

    public void addPotholes() throws InterruptedException, IOException {
        if (s.getTimePassed() % 5 == 0 && s.getTimePassed() != 0) //5 seconds to add a car is purely for testing purposes
        {
            int y_axis = rand.nextInt(pothole_locations_Y.length);
            int x_axis = rand.nextInt(pothole_locations_X.length);
            for (int i = 0; i < timesGenerated; i++)
            {
                potholes.add(new Pothole(pothole_locations_X[x_axis], pothole_locations_Y[y_axis]));
                frame.add(potholes.get(potholes.size() - 1));
                Thread.sleep(300);
            }
            timesGenerated = 0;
        }
        timesGenerated = rand.nextInt(1, 4); //method works woohoo
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
            }
        }
        if (cone != null){ //checks of there even is a cone
            if (bomb.getBombHitbox().intersects(cone.getConeHitbox())) {
                frame.remove(cone);
                TrafficCone.conesPlaced--;  //checks for cone, if there is, remove it
            }
        } else{ //checks if you blew up a car
            potholes.add(new Pothole(bomb.getX(), bomb.getY())); //fix the thing where blowing up a car creates a pothole
            frame.add(potholes.get(potholes.size() - 1));
                                                            //this is the cause for the orange square
        }                                                 //bc that was supposed to be the pothole being created
        frame.remove(bomb);
        frame.revalidate();
        frame.repaint();
    }

    public void comboManager()  //always increases combo
    {
        player.increaseCombo();
        if (s.getTimePassed() - timeLastFilled > 5)
        {
            player.resetCombo();
        }
    }

    public void roadBlock() throws InterruptedException { //refer to the hitbox instead
        for (Car car : cars) {
            if (cone != null && cone.getConeHitbox() != null) {
                if (cone.getConeHitbox().intersects(car.getCarHitbox())) { //have to fix the issue where cone hitbox is null after removing
                    car.killSound(false);
                    car.stopCar();
                } else {
                    car.setSpeed(Car.speed);// allows car to play audio again once no longer blocked by cone
                }
            }
        }
    }

    public void checkPlayerPosition() {
        if (player.getX() < 0) player.setLocation(0, player.getY());
        if (player.getX() > size.getWidth() - 100) player.setLocation((int) size.getWidth() - 100, player.getY());
        if (player.getY() < 0) player.setLocation(player.getX(), 0);
        if (player.getY() > size.getHeight() - 150) player.setLocation(player.getX(), (int) size.getHeight() - 150);
    }

    public void loseScreen() {
        gameOver = true;
        frame.removeKeyListener(player);
        frame.removeAll();
        thread.interrupt();
        s.killThread();
        frame.revalidate();
        frame.repaint();
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
                addCars(); //create cars, train ,and potholes
                trainSummon();
                addPotholes();
                checkCarPositions();
                frame.revalidate();
                frame.repaint();
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }

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
}