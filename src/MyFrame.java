import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;

public class MyFrame extends JFrame implements Runnable { //make this in charge on handling of placing images
    public static int targetFPS = 30;
    public static int targetTime = 1000000000 / targetFPS;
    public static boolean gameOver;
    private ArrayList<Bomb> bombs = new ArrayList<>();
    private Player player;
    private ArrayList<TrafficCone> cone = new ArrayList<>(); //might make this an arraylist??
    private ArrayList<Car> cars = new ArrayList<>();
    private ArrayList<Pothole> potholes = new ArrayList<>();
    private int timesGenerated;
    private JFrame frame = new JFrame();
    private Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
    private Thread thread;
    private Stopwatch s = new Stopwatch();
    private Sound sound = new Sound();

    public MyFrame() throws IOException { //https://stackoverflow.com/questions/2141019/how-can-i-check-if-something-leaves-the-screen-jframe car leaves screen idfk
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setTitle("I don't even know if JFrame works anymore..");

        cars.add(new Car((int) size.getWidth(), 300));
        player = new Player();

        frame.setUndecorated(false);
        frame.addKeyListener(player);
        frame.addMouseListener(player);

        frame.setFocusable(true);

        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        frame.setVisible(true);
        add();
        thread = new Thread(this);
        thread.start();
    }

    public void add() {
        frame.add(player); //find a way to somehow add the traffic cone AAAAAA
        for (Car car : cars) frame.add(car);
    }

    public void addCars() throws IOException, InterruptedException //creates new cars
    {
        if (s.getTimePassed() % 5 == 0) //5 seconds to add a car is purely for testing purposes
        {
            for (int i = 0; i < timesGenerated; i++) cars.add(new Car(1000, 300));
            timesGenerated = 0;
            for (Car car : cars) frame.add(car);
            Thread.sleep(1000);
        }
    }

    public void checkCollision() throws IOException { //refer to the hitbox instead
        for (Car car : cars)
        {
            if (player.getPlayerHitbox().intersects(car.getCarHitbox())) {
                car.killSound(false);
                loseScreen();
            }
            for (Pothole pothole : potholes)
            {
                if (car.getCarHitbox().intersects(pothole.getPotholeHitbox())) loseScreen();
            }
        }
    }

    public void conePlacement() {
        if (!cone.isEmpty()) {
            if (player.getPlayerHitbox().intersects(cone.get(0).getConeHitbox())) {
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
        for (int i = 0; i < cars.size(); i++)
        {
            if (bombs.get(0).getBombHitbox().intersects(cars.get(i).getCarHitbox()))
            {
                frame.remove(cars.get(i));
                cars.remove(cars.get(i));
                Bomb.bombCount ++;
                sound.play("man_V_machine", false);
            }
        }
        if (TrafficCone.conesPlaced >= 1)
        {
            if(bombs.get(0).getBombHitbox().intersects(cone.get(0).getConeHitbox()))
            {
                frame.remove(cone.get(0));
            }
        }
        else
        {
            potholes.add(new Pothole(bombs.get(0).getX(), bombs.get(0).getY()));
            frame.add(potholes.get(potholes.size() - 1)); //this is the cause for the orange square
        }                                                 //bc that was supposed to be the pothole being created
        frame.remove(bombs.get(0));
        bombs.remove(0);
        frame.revalidate();
        frame.repaint();
    }
    public void roadBlock() throws InterruptedException { //refer to the hitbox instead
        for (Car car : cars)
            if (!cone.isEmpty())
                if (cone.get(0).getConeHitbox().intersects(car.getCarHitbox())) { //have to fix the issue where cone hitbox is null after removing
                    car.killSound(false);
                    car.setSpeed(0);
                } else {
                    car.setSpeed(20);// allows car to play audio again once no longer blocked by cone
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
        frame.removeMouseListener(player);
        frame.removeAll();
        thread.interrupt();
        s.killThread();
        frame.revalidate();
        frame.repaint();
    }

    public void checkCarPositions() {
        for (int i = 0; i < cars.size(); i++) {
            if (cars.get(i).getX() <= Main.offScreen) {
                frame.remove(cars.get(i));
                cars.remove(cars.get(i));
                frame.revalidate();
                frame.repaint();
            }
        }
    }
    public void userKeyInput() throws IOException {
        switch(player.getKeyCode()) //will be necessary once i do the pothole fixing
        {
            case 'e' ->
            {
                if (Bomb.bombCount > 0)
                {
                    bombs.add(new Bomb());
                    bombs.get(0).setLocation(player.getX(), player.getY(), player.getDirection());
                    frame.add(bombs.get(0));
                    bombCollision();
                    Bomb.bombCount --;
                }
            }
        }
        player.resetKeyCode();
    }
    public void userMouseInput() { //make a second thread exclusively to handle this
        if (Player.clickCount % 2 == 0) {
            try {
                cone.add(new TrafficCone());
                cone.get(0).setLocation(player.getX(), player.getY(), player.getDirection());
                frame.add(cone.get(0));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            TrafficCone.conesPlaced++;
        }
        if (Player.clickCount % 4 == 0) {
            if (cone != null)
            {
                frame.remove(cone.get(0));
                cone.remove(0);
                frame.revalidate();
                frame.repaint();
                TrafficCone.conesPlaced--;
            }
            Player.clickCount = 0;
        }
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            long startTime = System.nanoTime();
            //do stuff per frame below
            try {
                addCars();
                roadBlock();
                userKeyInput();
                userMouseInput();
                checkCollision();
                conePlacement();
                checkCarPositions();
                checkPlayerPosition();
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