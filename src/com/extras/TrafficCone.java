package com.extras;// com.extras.TrafficCone.java
import com.game.Box;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class TrafficCone extends JLabel {
    public static int conesPlaced;
    private com.game.Box coneHitbox;

    public TrafficCone() throws IOException {
        ImageIcon icon = new ImageIcon(ImageIO.read(new File("ImageFiles/Images/cone.png")));
        Image image = icon.getImage();
        Image newimg = image.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        icon = new ImageIcon(newimg);
        this.setOpaque(true);
        setIcon(icon);
        }

    public void setLocation(int x, int y, String direction) {
            int coneSize = 50;
            switch (direction) {
                case "up" -> this.setBounds(x, y - coneSize, coneSize, coneSize);
                case "down" -> this.setBounds(x, y + coneSize, coneSize, coneSize);
                case "left" -> this.setBounds(x - coneSize, y, coneSize, coneSize);
                case "right" -> this.setBounds(x + coneSize, y, coneSize, coneSize);
            }
            this.setOpaque(true);
            coneHitbox = new com.game.Box(this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }

    public Box getConeHitbox() {return coneHitbox;}
    public boolean equals(Object o){
        return false;
    }
    public String toString(){
        return "";
    }
}
