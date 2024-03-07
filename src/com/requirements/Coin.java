package com.requirements;

public class Coin {
    public static int valu;
    private int value;
    private String material;
    public Coin() {
        valu++;
    }
    public Coin(int i, String s){
        value = i;
        material = s;
    }
    public void flip(){
        System.out.println("heads");
    }
    public void examine(int i){
        System.out.println("examined " + i + " times");
    }
    public String getMaterial(){
        return material;
    }
    public boolean isCoin(Coin coin){
        if (coin instanceof Coin) return true;
        return false;
    }
}
