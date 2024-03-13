package com.game.Screens;

import java.io.IOException;

public class Main
{
    public static void main(String[] args) throws IOException {
        Mainframe main = new Mainframe();
        main.start();
    }
    public String toString(){
        return "main";
    }
    public boolean equals(Object other){
        return super.equals(other);
    }
}