import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;

public class MyFrame extends JFrame implements Runnable, MouseListener, KeyListener { //make this in charge on handling of placing images
    public static int targetFPS = 30;
    public static int targetTime = 1000000000 / targetFPS;
    public static boolean gameOver;
    private Player player;
    private TrafficCone cone; //might make this an arraylist??
    private ArrayList<Car> cars = new ArrayList<>();
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
        frame.addKeyListener(this);
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
            if (player.getPlayerHitbox().intersects(car.getCarHitbox())) {
                car.killSound(false);
                loseScreen();
            }
    }

    public void roadBlock() throws InterruptedException { //refer to the hitbox instead
        for (Car car : cars)
            if (cone != null)
                if (cone.getConeHitbox().intersects(car.getCarHitbox())) {
                    car.killSound(false);
                    car.setSpeed(0);
            } else {
                car.setSpeed(10);// allows car to play audio again once no longer blocked by cone
            }
    }

    public void conePlacement() {
        if (TrafficCone.conesPlaced == 1) {
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

    public void loseScreen() throws IOException {
        gameOver = true;
        frame.removeKeyListener(this);
        frame.removeMouseListener(this);
        frame.removeAll();
        refresh();
        thread.interrupt();
        s.killThread();
    }

    private void refresh() throws IOException {
        frame.validate();
        frame.repaint();
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            long startTime = System.nanoTime();

            //do stuff per frame below
            try {
                checkCollision();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            conePlacement();
            checkCarPositions();
            checkPlayerPosition();

            try {
                addCars();
                roadBlock();
                refresh();
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
                    throw new RuntimeException(e);
                }
            }
        }
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

    public void stunPlayer() throws InterruptedException { //make a second thread exclusively to handle this

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            try {
                cone = new TrafficCone();
                stunPlayer();
                cone.setLocation(player.getX(), player.getY(), player.getDirection());
                frame.add(cone);
            } catch (IOException | InterruptedException ex) {
                throw new RuntimeException(ex);
            }
            TrafficCone.conesPlaced++;
        }
        if (e.getButton() == MouseEvent.BUTTON3) {
            if (cone != null)
            {
                frame.remove(cone);
                TrafficCone.conesPlaced--;
            }
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

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        char keyCode = e.getKeyChar();
        try {
            movePlayer(keyCode);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    private void movePlayer(char keyCode) throws IOException {
        int step = 30;

        switch (keyCode) {
            case 'w' -> {
                Player.direction = "up";
                player.setLocation(player.getX(), player.getY() - step);
                player.getPlayerHitbox().setLocation(player.getX(), player.getY());
            }
            case 'a' -> {
                Player.direction = "left";
                player.setLocation(player.getX() - step, player.getY());
                player.getPlayerHitbox().setLocation(player.getX(), player.getY());
            }
            case 's' -> {
                Player.direction = "down";
                player.setLocation(player.getX(), player.getY() + step);
                player.getPlayerHitbox().setLocation(player.getX(), player.getY());
            }
            case 'd' -> {
                Player.direction = "right";
                player.setLocation(player.getX() + step, player.getY());
                player.getPlayerHitbox().setLocation(player.getX(), player.getY());
            }
            case 'e' ->
            {
                Bomb bomb = new Bomb();
                bomb.setLocation(player.getX(), player.getY(), player.getDirection());
                frame.add(bomb);
                refresh();
            }
        }
        /*System.out.println(this.getX());*/
    }
}