package com.game;

import java.io.IOException;
import java.util.Random;

public class Spawner implements Runnable{
    private int[] car_locations = {360, 440, 520, 600};
    private int[] countDowns = {7, 10};
    private final Random rand = new Random();
    public Spawner(){
        Thread thread = new Thread(this);
        thread.start();
    }
    public void addCars() throws IOException, InterruptedException //creates new cars
    {
        if (MyFrame.s.getTimePassed() % (countDowns[1] - (MyFrame.s.getTimePassed() / 15)) == 0 && (MyFrame.s.getTimePassed() != 0 && MyFrame.s.getTimePassed() != Car.timeLastSpawned)) //5 seconds to add a car is purely for testing purposes, also rewrite this without using sleep
        {
            int timesGenerated;
            Car.timeLastSpawned = MyFrame.s.getTimePassed();
            int lastSpawnedYCoord = 0;
            if (countDowns[1] < 6) countDowns[1] = 6; //spawn timer min
            timesGenerated = rand.nextInt(1, 5);
            for (int i = 0; i < timesGenerated; i++) {
                int y_axis = rand.nextInt(car_locations.length);
                while (y_axis == lastSpawnedYCoord) y_axis = rand.nextInt(car_locations.length);
                MyFrame.cars.add(new Car((int) MyFrame.size.getWidth() + 500, car_locations[y_axis]));
                lastSpawnedYCoord = car_locations[y_axis];
                Thread.sleep(300);
            }
            Car.speed += 2.5;
        }
    }
    public void addPotholes() throws IOException {
        if (MyFrame.s.getTimePassed() % (countDowns[0] - MyFrame.s.getTimePassed() / 10) == 0 && MyFrame.s.getTimePassed() != 0 && Pothole.timeLastSpawned != MyFrame.s.getTimePassed()) //5 seconds to add a car is purely for testing purposes
        {
            if (countDowns[0] < 3) countDowns[0] = 3;
            int timesGenerated = rand.nextInt(1, 4);
            for (int i = 0; i < timesGenerated; i++) {
                int y_axis = rand.nextInt(360, 600);
                int x_axis = rand.nextInt(100, 600);
                MyFrame.potholes.add(new Pothole(x_axis, y_axis, false));
            }
            Pothole.timeLastSpawned = MyFrame.s.getTimePassed();
        }
    }
    public void trainSummon() throws IOException {
        if (MyFrame.s.getTimePassed() % 8 == 0 && MyFrame.s.getTimePassed() != 0 && MyFrame.s.getTimePassed() != Train.timeLastSpawned) {
            new Train((int) MyFrame.size.getWidth(), 100);
            Train.timeLastSpawned = MyFrame.s.getTimePassed();
        }
    }

    @Override
    public void run() {
        while (Gamestate.state != Gamestate.gameEnd) {
            long startTime = System.nanoTime();
            //do stuff per frame below
            try {
                trainSummon();
                addCars();
                addPotholes();
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }

            long totalTime = System.nanoTime() - startTime;

            if (totalTime < 1000000000) {
                try {
                    Thread.sleep((1000000000 - totalTime) / 1000000); //theoretically should only be running at 1 fps
                } catch (InterruptedException e) {
                    System.out.close();
                }
            }
        }
    }
}
