public class Stopwatch implements Runnable { //just tracks the time ig
    private long startTime = System.currentTimeMillis();
    private long elapsedTime;
    private long elapsedSeconds;
    private long secondsDisplay;
    private Thread t;
    public Stopwatch()
    {
        t = new Thread(this);
        t.start();
    }

    public void startTimer()
    {
        while (!Thread.currentThread().isInterrupted())
        {
            elapsedTime = System.currentTimeMillis() - startTime; // this needs to do one per second, bc right now this does it like 10000 times per second, so it lags
            elapsedSeconds = elapsedTime / 1000;
            secondsDisplay = elapsedSeconds % 60;
        }
    }

    public long getElapsedSeconds() {return elapsedSeconds;}

    @Override
    public void run() {
        startTimer();
    }

    public void killThread(){t.interrupt();}
}
