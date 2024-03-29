package com.game.Entities;

import com.game.Misc.Box;
import com.game.Screens.Mainframe;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Pothole extends JLabel { //shouldn't be that hard hopefully??
    private Box potholeHitbox;
    private ImageIcon icon;
    public static int timeLastSpawned;
    private boolean bombMade;

    public Pothole(int x, int y, boolean bombMade) throws IOException {
        icon = new ImageIcon(ImageIO.read(new File("ImageFiles/Images/pothole.png")));
        Image image = icon.getImage();
        Image newimg = image.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        icon = new ImageIcon(newimg);
        this.bombMade = bombMade;
        setIcon(icon);
        setLocation(x, y);
        Mainframe.frame.add(this);
        Mainframe.refresh();
    }

    public void setLocation(int x, int y) {
        this.setOpaque(false);
        this.setBounds(x, y, icon.getIconWidth(), icon.getIconHeight());
        potholeHitbox = new Box(this.getX(), this.getY(), 50, 50);
    }

    public Box getPotholeHitbox() {
        return potholeHitbox;
    }
    public boolean getBombMade(){
        return bombMade;
    }
    public String toString(){
        return "pothole";
    }
    public boolean equals(Object other){
        return super.equals(other);
    }
}
