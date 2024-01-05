import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class Box extends Rectangle {
    private Color color;

    public Box(int x, int y, int width, int height, Color color)
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
    }

    public void keyPressed(KeyEvent e) {
        char keyCode = e.getKeyChar();
        movePlayer(keyCode);
    }

    public void movePlayer(char keyCode) {
        int step = 30;

        switch (keyCode) {
            case 'w':
                this.setLocation((int) this.getX(), (int) (this.getY() - step));
                break;
            case 'a':
                this.setLocation((int) (this.getX() - step), (int) this.getY());
                break;
            case 's':
                this.setLocation((int) this.getX(), (int) (this.getY() + step));
                break;
            case 'd':
                this.setLocation((int) (this.getX() + step), (int) this.getY());
                break;
        }
    }
    public void draw(Graphics g)
    {
        g.setColor(this.color);
        g.fillRect(this.x, this.y, this.width, this.height);
    }
}
