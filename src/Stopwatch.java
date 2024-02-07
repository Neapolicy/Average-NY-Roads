public class Stopwatch implements Runnable { //just tracks the time ig
    private final long startTime = System.currentTimeMillis();
    private int timePassed = 1;
    private int gameTime;
    private Thread t;
    public Stopwatch()
    {
        t = new Thread(this);
        t.start();
    }

    public long getTimePassed() {return gameTime;}

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted())
        {
            long startTimer = System.nanoTime();

            int elapsedTime = (int) (System.currentTimeMillis() - startTime); // this needs to do one per second, bc right now this does it like 10000 times per second, so it lags
            int elapsedSeconds = elapsedTime / 1000;
            if (elapsedSeconds == timePassed)
            {
                timePassed++;
                gameTime = elapsedSeconds;
            }

            long totalTime = System.nanoTime() - startTimer;

            if (totalTime < MyFrame.targetTime) {
                try {
                    Thread.sleep((MyFrame.targetTime - totalTime) / 1000000);
                } catch (InterruptedException e) {
                    System.out.println("Elapsed Time: " + gameTime);
                }
            }
        }
    }
}
