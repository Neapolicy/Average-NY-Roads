package com.game;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

public class Mainframe extends JFrame implements MouseListener {
    public static JFrame frame = new JFrame();
    private Sound sound = new Sound();
    private int score;
    private int combo;
    private int time;
    public void start() throws IOException {
        frame.setTitle("FWMC RADIO BAU BAU");
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setSize(900, 700);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
        frame.addMouseListener(this);
        sound.setVolume(.3);

        checkGameState();
    }
    public void checkGameState() throws IOException { //depression nap, divinity of the office, and chunkopops from cruelty squad is where the sound comes from
        if (Gamestate.state == Gamestate.gameStart) {
            sound.play("start_music", true); //transition the music
            new Startscreen();
        }
        if (Gamestate.state == Gamestate.gameMiddle) {
            sound.stopSound();
            new MyFrame();
        }
        if (Gamestate.state == Gamestate.gameEnd) {
            frame.addMouseListener(this);
            sound.stopSound();
            frame.getContentPane().removeAll();
            sound.play("end_music", true);
            SwingUtilities.invokeLater(() -> new Endscreen(score, combo, time));
        }
    }

    public static void refresh() {
        frame.revalidate();
        frame.repaint();
    }
    public void setValues(int score, int combo, int time){
        this.score = score;
        this.combo = combo;
        this.time = time;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (Gamestate.state == Gamestate.gameStart){
            Gamestate.state = Gamestate.gameMiddle;
            frame.getContentPane().removeAll();
            frame.removeMouseListener(this);
        }
        else if (Gamestate.state == Gamestate.gameEnd){
            Gamestate.state = Gamestate.gameMiddle;
            frame.getContentPane().removeAll();
            frame.removeMouseListener(this);
        }
        try {
            checkGameState();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
    public boolean equals(Object o){
        return false;
    }
    public String toString(){
        return "";
    }
}
