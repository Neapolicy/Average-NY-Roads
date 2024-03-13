package com.game.Screens;

public enum Gamestate{
    gameStart, gameMiddle, gameEnd;

    public static Gamestate state = gameStart;
    public String toString(){
        return "gamestate";
    } //cant overide equals method
}