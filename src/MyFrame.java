import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class MyFrame extends JFrame implements KeyListener{
    JLabel label;
    ImageIcon icon;
    MyFrame()
    {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(500, 500);
        this.setLayout(null);
        this.addKeyListener(this);

        Dimension size = Toolkit.getDefaultToolkit().getScreenSize(); //gets the dimensions of your screen

        icon = new ImageIcon(); //put name of image file here, but you also need to import it

        label = new JLabel(); //does your character
        label.setBounds((int)size.getWidth()/2, (int)size.getHeight()/2 - 50, 50, 50);
        label.setBackground(Color.ORANGE);
        label.setOpaque(true);

        this.setExtendedState(JFrame.MAXIMIZED_BOTH); //makes it fullscreen
        this.setUndecorated(false); //gives you exit button while fullscreen

        this.add(label);
        this.setVisible(true);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        switch(e.getKeyChar())
        {
            case 'w' -> label.setLocation(label.getX(), label.getY() - 30);
            case 'a' -> label.setLocation(label.getX() - 30, label.getY());
            case 's' -> label.setLocation(label.getX(), label.getY() + 30);
            case 'd' -> label.setLocation(label.getX() + 30, label.getY());
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("Pressed " + e.getKeyChar());
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
