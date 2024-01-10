import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;

public class MyFrame extends JFrame implements Runnable, MouseListener { //make this in charge on handling of placing images
    private Player player;
    private TrafficCone cone; //might make this an arraylist??
    private ArrayList<Car> cars = new ArrayList<>();
    private JFrame frame = new JFrame();

/*    public MyFrame() {
        SwingUtilities.invokeLater(() ->{
            this.setDefaultCloseOperation(EXIT_ON_CLOSE);
            this.setExtendedState(JFrame.MAXIMIZED_BOTH);

            ImageIcon icon;
            try {
                icon = new ImageIcon(ImageIO.read(new File("Images/Player/player.png")));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            player = new Player(icon);
            this.addKeyListener(player);
            this.addMouseListener(player);
            this.setFocusable(true);

            *//*playerHitbox = new Box(300, 300, 50, 50, Color.BLACK);*//*
            this.add(player);
            revalidate();
            repaint();

            *//*this.addKeyListener(new AL());*//*

            this.setContentPane(new JLabel(icon));
            this.setVisible(true);
        });
    }*/

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
        Thread thread = new Thread(this);
        thread.start();
    }

    public void add()
    {
        frame.add(player); //find a way to somehow add the traffic cone AAAAAA
        frame.add(cars.get(0));
        frame.add(cone);
    }

    public void checkCollision() { //refer to the hitbox instead
        for (Car car : cars)
            if (player.getPlayerHitbox().intersects(car.getCarHitbox())) {
                switch (player.getDirection()) {
                    case "up":
                        player.setLocation(player.getX(), player.getY() + 35);
                        player.getPlayerHitbox().setLocation(player.getX(), player.getY());
                    case "down":
                        player.setLocation(player.getX(), player.getY() - 35);
                        player.getPlayerHitbox().setLocation(player.getX(), player.getY());
                    case "left":
                        player.setLocation(player.getX() + 35, player.getY());
                        player.getPlayerHitbox().setLocation(player.getX(), player.getY());
                    case "right":
                        player.setLocation(player.getX() - 30, player.getY());
                        player.getPlayerHitbox().setLocation(player.getX(), player.getY());
                }
                loseScreen();
                /*System.out.println(player.getDirection());*/
                /*System.out.println("HEY");*/
            }
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
        //idk make a game over screen or smtg
    }

    @Override
    public void run() {
        //noinspection InfiniteLoopStatement
        while (true) {
            checkCollision();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            cone.setLocation(player.getX(), player.getY(), player.getDirection());
            TrafficCone.conePlaced = true;
        } else if (e.getButton() == MouseEvent.BUTTON3) {
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