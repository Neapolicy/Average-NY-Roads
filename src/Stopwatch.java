public class Stopwatch implements Runnable { //just tracks the time ig
    private int gameTime;
    private Thread t;
    public Stopwatch()
    {
        t = new Thread(this);
        t.start();
    }

    public int getTimePassed() {return gameTime;}

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted())
        {
            try {
                Thread.sleep(1000);
                gameTime ++;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public boolean equals(Object o){
        return false;
    }
    public String toString(){
        return "";
    }
}
