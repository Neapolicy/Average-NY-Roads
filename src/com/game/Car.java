package com.game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Car extends JLabel implements Runnable{
    public static int timeLastSpawned;
    protected int offScreen;
    private com.game.Box carHitbox;
    private Sound sound = new Sound();
    private double step = 10;
    public static double speed = 10;
    private Thread thread;
    public Car(int x, int y) throws IOException {
        ImageIcon icon = new ImageIcon(ImageIO.read(new File("ImageFiles/Images/car1.png")));
        Image image = icon.getImage();
        Image newimg = image.getScaledInstance(125, 80, Image.SCALE_SMOOTH);
        icon = new ImageIcon(newimg);
        setIcon(icon);
        offScreen = -200;

        this.setBounds(x, y, icon.getIconWidth(), icon.getIconHeight()); //spawn offscreen
        this.setOpaque(false);
        carHitbox = (new com.game.Box(this.getX(), this.getY(), this.getWidth(), this.getHeight()));
        playSound();
        thread = new Thread(this);
        thread.start();
        Mainframe.frame.add(this);
    }

    public void moveCar()
    {
        this.setLocation((int) (this.getX() - step), this.getY());
        carHitbox.setLocation(this.getX(), this.getY());
        if (step == 0) killSound(false);
        if (this.getX() < 0){this.remove(this);} 
    }

    public void playSound() {
        sound.play("car_move", true);
    }

    public Box getCarHitbox()
    {
        return carHitbox;
    }

    @Override
    public void run() { //controls car movement
        while (this.getX() > offScreen)
        {
            long startTime = System.nanoTime();

            moveCar();

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            long totalTime = System.nanoTime() - startTime;

            if (totalTime < MyFrame.targetTime) {
                try {
                    Thread.sleep((MyFrame.targetTime - totalTime) / 1000000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        sound.setLoopable(false);
        killThreads();
    }

    public void killSound(boolean b){sound.setLoopable(b);}

    public void killThreads() {
        sound.killThread();
        killThread();
        thread.interrupt();
    }

    public void killThread(){thread.interrupt();}

}
