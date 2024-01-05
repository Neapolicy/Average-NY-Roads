import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class MyFrame extends JFrame { //make this in charge on handling of placing images
    private Player player;
    private Image image;
    private Graphics graphics;
    private Box playerHitbox;
    private Box carHitbox;
    private boolean collision;
    private ImageIcon icon;;
    private TrafficCone cone;

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

        try {
            icon = new ImageIcon(ImageIO.read(new File("Images/Player/player.png")));
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading player.png");
        }


        player = new Player(icon);
        cone = new TrafficCone(icon, player.getX(), player.getY(), "down");
        playerHitbox = new Box(player.getX(), player.getY(), 50, 50, Color.ORANGE);
        carHitbox = new Box(player.getX(), player.getY() - 100, 50, 50, Color.RED);

        this.setUndecorated(false);
        this.addKeyListener(player);
        this.addKeyListener(new AL());
        this.addMouseListener(player);

        this.setFocusable(true);

        this.setContentPane(new JLabel(icon));
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);

        this.add(player); //find a way to somehow add the traffic cone AAAAAA
        this.add(cone);

        this.setVisible(true);
    }


    public void paint(Graphics g) {
        image = createImage(this.getWidth(), this.getHeight());
        graphics = image.getGraphics();
        g.drawImage(image, 0, 0, this);

        playerHitbox.draw(g);
        carHitbox.draw(g);
    }
    public void checkCollision() { //refer to the hitbox instead
        if (playerHitbox.intersects(carHitbox))
        {
            /*remove(cone);*/
            System.out.println("HEY");
        }
    }

    public class AL extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            playerHitbox.keyPressed(e);
            checkCollision();
            repaint();
        }
    }
}