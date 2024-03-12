package com.game.Background;

import com.game.Screens.Mainframe;
import com.game.Screens.MyFrame;

import javax.swing.*;
import java.awt.*;

public class Road extends JPanel{
    public Road() {
        this.setLayout(null);
        this.setBounds(0, (int) (MyFrame.size.getHeight() / 2) - 200, (int) MyFrame.size.getWidth(), (int) (MyFrame.size.getHeight() - 700));
        Mainframe.frame.add(this);
    }
    @Override
    public void paintComponent(Graphics g) { //rahh
        super.paintComponent(g);

        // Paint your images
        g.setColor(new Color(102, 102, 102)); //dark grey
        g.fillRect(0, 0, (int) MyFrame.size.getWidth(), (int) MyFrame.size.getHeight() - 700);

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, (int) MyFrame.size.getWidth(), 30);
        g.fillRect(0, this.getHeight() - 30, (int) MyFrame.size.getWidth(), 30);

        g.setColor(new Color(255, 204, 51)); //yellow
        for (int x = 10; x < this.getWidth(); x += 100){
            g.fillRect(x, this.getHeight() / 2 - 10, 50, 20);
        }
    }

    public void setSize(int width, int height) {
        super.setSize(width, height);
        repaint();  // Trigger a repaint to update the displayed image
    }

}
