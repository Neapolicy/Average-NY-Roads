package com.requirements;
import java.util.ArrayList;
public class FunFunTime { //this file only exists to meet the requirements of the project
    private ArrayList<Integer> array = new ArrayList<>();
    private ArrayList<Coin> coined = new ArrayList<>();
    private int[] arr = {9, 1, 3, 2, 6, 1};
    private int[] arrs = {9, 1, 3, 2, 6, 1};
    private Coin[][] coins = {{new Coin(), new Coin(), new DollarCoin()}, {new DollarCoin(), new Coin(), new Coin()}, {new Coin(), new Coin(), new Coin()}};
    public void print(){
        if (array.size() > 0) System.out.println("ok");
        System.out.println(array.add(12));
        array.add(0, 21);
        System.out.println(array.get(0));
        array.set(0, 33);
        array.remove(1);
        for (int i = 0; i < 5; i++) array.add(33);
        for (int i : array) System.out.println(i);
        for (int i = array.size() - 1; i >= 0; i--) if (array.get(i) == 33) array.remove(i);
        for (int i = 9; i >= 0; i--) array.add(i);
        selection();
        for (int i : arr) System.out.println(i);
        insertion();
        for (int i : arrs) System.out.println(i);
        twoDArrays();
    }

    public void twoDArrays(){
        for (int i = 0; i < coins.length; i++){
            for (int j = 0; j < coins[0].length; j++) System.out.println(coins[i][j]);
        }
        for (Coin[] coin : coins) {
            for (int j = 0; j < coins[0].length; j++) System.out.println(coin[j]);
        }
        for (int i = 0; i < coins[0].length; i++){
            for (int j = 0; j < coins.length; j++) System.out.println(coins[i][j]);
        }
    }

    public void inheritance(){
        Coin coin = new DollarCoin(1, "gold");
        DollarCoin DOLLARS = new DollarCoin();
        System.out.println(coin.getMaterial());
        System.out.println(DOLLARS.isCoin(DOLLARS));
        coined.add(new DollarCoin());
        coined.add(new DollarCoin());
    }
    public void selection(){
        int n = arr.length;

        for (int i = 0; i < n - 1; i++) {
            // Find the minimum element in unsorted array
            int min_idx = i;
            for (int j = i + 1; j < n; j++) {
                if (arr[j] < arr[min_idx])
                    min_idx = j;
            }

            // Swap the found minimum element with the first element
            int temp = arr[min_idx];
            arr[min_idx] = arr[i];
            arr[i] = temp;
        }
    }
    public void insertion()
    {
        int n = arrs.length;
        for (int i = 1; i < n; ++i) {
            int key = arrs[i];
            int j = i - 1;

            while (j >= 0 && arrs[j] > key) {
                arrs[j + 1] = arrs[j];
                j = j - 1;
            }
            arrs[j + 1] = key;
        }
    }
    public boolean equals(Object o){
        return false;
    }
    public String toString(){
        return "";
    }
}
