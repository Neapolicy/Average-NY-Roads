import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;

public class MyFrame extends JFrame implements Runnable, MouseListener { //make this in charge on handling of placing images
    private Player player;
    private TrafficCone cone; //might make this an arraylist??
    private ArrayList<Car> cars = new ArrayList<>();
    private int timesGenerated;
    private JFrame frame = new JFrame();
    private Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
    public static boolean gameOver;
    private Thread thread;
    private Stopwatch s = new Stopwatch();

    public MyFrame() throws IOException { //https://stackoverflow.com/questions/2141019/how-can-i-check-if-something-leaves-the-screen-jframe car leaves screen idfk
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setTitle("I don't even know if JFrame works anymore..");

        cars.add(new Car(1000, 300));
        player = new Player();
        cone = new TrafficCone();

        frame.setUndecorated(false);
        frame.addKeyListener(player);
        frame.addMouseListener(this);

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
        frame.add(cone);
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

    public void checkCollision() { //refer to the hitbox instead
        for (Car car : cars)
            if (player.getPlayerHitbox().intersects(car.getCarHitbox())) {
                car.killSound();
                loseScreen();
            }
    }

    public void roadBlock() { //refer to the hitbox instead
        for (Car car : cars)
            if (cone.getConeHitbox().intersects(car.getCarHitbox())) {
                car.setSpeed(0);
            } else {
                car.setSpeed(car.getStep());
            }
    }

    public void conePlacement() {
        if (TrafficCone.conePlaced) {
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
    }

    public void loseScreen() {
        gameOver = true;
        frame.removeKeyListener(player);
        frame.removeMouseListener(this);
        frame.removeAll();
        thread.interrupt();
        s.killThread();
    }

    @Override
    public void run() {
        do {
            checkCollision();
            conePlacement();
            checkCarPositions();
            checkPlayerPosition();
            roadBlock();
            try {
                addCars();
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        } while (!Thread.currentThread().isInterrupted());
    }

    public void checkCarPositions() {
        for (int i = 0; i < cars.size(); i++) {
            if (cars.get(i).getX() <= Main.offScreen) {
                frame.remove(cars.get(i));
                cars.remove(cars.get(i));
            }
        }
    }

    public void checkPlayerPosition() {
        if (player.getX() < 0) player.setLocation(0, player.getY());
        if (player.getX() > size.getWidth() - 100) player.setLocation((int) size.getWidth() - 100, player.getY());
        if (player.getY() < 0) player.setLocation(player.getX(), 0);
        if (player.getY() > size.getHeight() - 150) player.setLocation(player.getX(), (int) size.getHeight() - 150);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            cone.setLocation(player.getX(), player.getY(), player.getDirection());
            TrafficCone.conePlaced = true;
        }
        if (e.getButton() == MouseEvent.BUTTON3) {
            TrafficCone.conePlaced = false;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
// i am questioning on whether i should rewrite this entire program altogether