public class Startscreen extends Mainframe {
    public Startscreen(){
        Scoreboard startText = new Scoreboard("Press E to Deploy a Bomb", (int) (MyFrame.size.getWidth() / 2) - 150, (int) (MyFrame.size.getHeight() / 2), 1000, 100);
        Scoreboard midText = new Scoreboard("Press Space to Fill In Potholes", (int) (MyFrame.size.getWidth() / 2) - 170, (int) (MyFrame.size.getHeight() / 2) - 100, 1000, 100);
        Scoreboard endText = new Scoreboard("Most Importantly, Don't Let The Cars Run Over You Or The Potholes", (int) (MyFrame.size.getWidth() / 2) - 400, (int) (MyFrame.size.getHeight() / 2) + 100, 1000, 100);
        Scoreboard clickInfo = new Scoreboard("Click Anywhere to Begin", (int) (MyFrame.size.getWidth() / 2) - 400, (int) (MyFrame.size.getHeight() / 2) + 200, 1000, 100);

        frame.add(startText);
        frame.add(midText);
        frame.add(endText);
        frame.add(clickInfo);

        refresh(); //guarantees that the text for appear, because the start text aint guaranteed for reasons god knows why
    }
}
