import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI implements ActionListener
{
    int count = 0;
    private JLabel label;
    private JFrame frame;
    private JPanel panel;
    public GUI()
    {
        frame = new JFrame();

        JButton button = new JButton("Click Click!");
        button.addActionListener(this);
        label = new JLabel("N");

        panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(300, 300, 100, 300));
        panel.setLayout(new GridLayout(0, 1));
        panel.add(button);
        panel.add(label);

        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Suimachi :3");
        frame.pack();
        frame.setVisible(true);
    }
    public static void main (String[] arge)
    {
        new GUI();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        count ++;
        label.setText(String.valueOf(count));
    }
}