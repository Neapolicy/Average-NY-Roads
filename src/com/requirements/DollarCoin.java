package com.requirements;

public class DollarCoin extends Coin {
    private String material;
    private boolean valuable;
    public DollarCoin(){
        super();
    }
    public DollarCoin(int i, String s){
        super(i, s);
    }
    public void flip(){
        System.out.println("tails");
    }
    public void examine(int i){
        super.examine(i);
    }
}
