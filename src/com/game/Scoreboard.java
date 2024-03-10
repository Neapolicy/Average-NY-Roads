package com.game;

import javax.swing.*;
import java.awt.*;

public class Scoreboard extends JLabel {
    private Font font = new Font("Arial", Font.PLAIN, 30); // default
    public Scoreboard(String text, int x, int y, int width, int height){
        super(text);
        this.setLayout(null);
        this.setFont(font);
        this.setForeground(Color.BLUE); // font color
        this.setBounds(x, y, width, height);
        this.setHorizontalAlignment(JLabel.CENTER); // center text horizontally
        this.setVerticalAlignment(JLabel.CENTER);   // center text vertically
    }
    public void updateScore(int score){
        this.setText("Current Score: " + score);
    }

    public void displayCombo(int combo){
        this.setText("Current Combo: " + combo);
    }
    public void displayTime(int time){
        this.setText("Current Time: " + time);
    }
    public void changeFont(String fontType, int size, Color color){ //keeping this just in case
        font = new Font(fontType, Font.PLAIN, size); // default
        this.setFont(font);
        this.setForeground(color); //font color
    }
    public void boldFont(String fontType, int size, Color color){
        font = new Font(fontType, Font.BOLD, size);
        this.setFont(font);
        this.setForeground(color);
    }
    public String toString(){
        return "professional gambler";
    }
    public boolean equals(Object o){
        return false;
    }
}
