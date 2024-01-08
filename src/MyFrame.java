import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;

public class MyFrame extends JFrame { //make this in charge on handling of placing images
    private Player player;
    private Image image;
    private Graphics graphics;
    private Box playerHitbox;
    private Box carHitbox;
    private Box coneHitbox;
    private boolean collision; //if collide with car game over is true
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
        /*cone = new TrafficCone(player.getX(), player.getY(), player.getDirection());*/
        cars.add(new Car(1000, 300));
        playerHitbox = new Box(player.getX(), player.getY(), 50, 50, Color.ORANGE);
        carHitbox = new Box(cars.get(0).getX(), cars.get(0).getY(), cars.get(0).getWidth(), cars.get(0).getHeight(), Color.RED);
        /*coneHitbox = new Box(cone.getX(), cone.getY(), cone.getWidth(), cone.getHeight(), Color.RED);*/

        this.setUndecorated(false);
        this.addKeyListener(player);
        this.addKeyListener(new AL());
        this.addMouseListener(player);

        this.setFocusable(true);

        this.setExtendedState(JFrame.MAXIMIZED_BOTH);

        this.add(player); //find a way to somehow add the traffic cone AAAAAA
        /*this.add(cone);*/
        this.add(cars.get(0));

        this.setVisible(true);
        image = createImage(this.getWidth(), this.getHeight());
        graphics = image.getGraphics();
    }


/*    public void paint(Graphics g) {
     *//*   image = createImage(this.getWidth(), this.getHeight());
        graphics = image.getGraphics();
        g.drawImage(image, 0, 0, this);*//*

*//*
        carHitbox.draw(g); //draws the car box
*//*
    }*/
    public void checkCollision() { //refer to the hitbox instead
        if (playerHitbox.intersects(carHitbox))
        {
            collision = true;
            System.out.println("HEY");
        }
/*
        if (playerHitbox.intersects(coneHitbox)) System.out.println("HEYA");
*/
    }

    public class AL extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            playerHitbox.keyPressed(e);
            checkCollision();
            /*repaint();*/
        }
    }
}
// i am questioning on whether i should rewrite this entire program altogether