import javax.swing.*;
import java.awt.*;

public class Scoreboard extends JLabel {
    public Scoreboard(String text){
        Font font = new Font("Arial", Font.PLAIN, 30);
        this.setFont(font);
        this.setForeground(Color.BLUE); //font color
        this.setText(text);
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
    public void potholesFilled(int filled){
        this.setText("Potholes Filled: " + filled);
    }
}
