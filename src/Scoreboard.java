import javax.swing.*;
import java.awt.*;

public class Scoreboard extends JLabel {
    public Scoreboard(String text, int x, int y, int width, int height){
        Font font = new Font("Arial", Font.PLAIN, 30); // default
        this.setLayout(null);
        this.setFont(font);
        this.setForeground(Color.BLUE); //font color
        this.setText(text);
        this.setBounds(x, y, width, height);
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
        Font font = new Font(fontType, Font.PLAIN, size); // default
        this.setFont(font);
        this.setForeground(color); //font color
    }
    public String toString(){
        return "professional gambler";
    }
}
