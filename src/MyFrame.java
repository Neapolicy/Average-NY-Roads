import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;

public class MyFrame extends JFrame implements Runnable{ //make this in charge on handling of placing images
    private Player player;
    private TrafficCone cone; //might make this an arraylist??
    private ArrayList<Car> cars = new ArrayList<>();

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

    public MyFrame() throws IOException {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setTitle("I don't even know if JFrame works anymore..");

        player = new Player();
        cone = new TrafficCone(player.getX(), player.getY(), player.getDirection());
        cars.add(new Car(1000, 300));

        this.setUndecorated(false);
        this.addKeyListener(player);
        this.addMouseListener(player);

        this.setFocusable(true);

        this.setExtendedState(JFrame.MAXIMIZED_BOTH);

        this.add(player); //find a way to somehow add the traffic cone AAAAAA
        this.add(cars.get(0));

        this.setVisible(true);
        this.add(cone);
        /*cone.setVisible(false);*/
        Thread thread = new Thread(this);
        thread.start();
    }


/*    public void paint(Graphics g) {
        image = createImage(this.getWidth(), this.getHeight());
        graphics = image.getGraphics();
        g.drawImage(image, 0, 0, this);


        player.getPlayerHitbox().draw(g); //draws the car box
        repaint();
    }*/
    public void checkCollision() { //refer to the hitbox instead
        for (Car car : cars)
            if (player.getPlayerHitbox().intersects(car.getCarHitbox())) {
                switch(player.getDirection())
                {
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
        if (player.getPlayerHitbox().intersects(cone.getConeHitbox()))
        {
            /*switch(player.getDirection())
            {
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
            }*/
            /*System.out.println(player.getDirection());*/
        }
    }

    public void loseScreen()
    {
        //idk make a game over screen or smtg
    }

    @Override
    public void run() {
        //noinspection InfiniteLoopStatement
        while (true)
        {
            checkCollision();
        }
    }
}
// i am questioning on whether i should rewrite this entire program altogether