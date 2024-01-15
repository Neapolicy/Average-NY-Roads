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
            frame.add(potholes.get(potholes.size() - 1));
        }
        bombs.remove(0);
        frame.revalidate();
        frame.repaint();
    }
    public void roadBlock() throws InterruptedException { //refer to the hitbox instead
        for (Car car : cars)
            if (!cone.isEmpty())
                if (cone.get(0).getConeHitbox().intersects(car.getCarHitbox())) {
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
        frame.removeKeyListener(this);
        frame.removeMouseListener(this);
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

    public void stunPlayer() throws InterruptedException { //make a second thread exclusively to handle this

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
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            try {
                cone.add(new TrafficCone());
                stunPlayer();
                cone.get(0).setLocation(player.getX(), player.getY(), player.getDirection());
                frame.add(cone.get(0));
            } catch (IOException | InterruptedException ex) {
                throw new RuntimeException(ex);
            }
            TrafficCone.conesPlaced++;
        }
        if (e.getButton() == MouseEvent.BUTTON3) {
            if (cone != null)
            {
                frame.remove(cone.get(0));
                cone.remove(0);
                frame.revalidate();
                frame.repaint();
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
                frame.revalidate();
                frame.repaint();
            } catch (IOException | InterruptedException ignored) {

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