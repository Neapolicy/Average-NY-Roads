public class Startscreen extends Mainframe {
    public Startscreen(){
        Scoreboard startText = new Scoreboard("Press E to deploy a bomb", (int) (MyFrame.size.getWidth() / 2), (int) (MyFrame.size.getHeight() / 2) - 100, 1000, 100);

        startText.setBounds((int) (MyFrame.size.getWidth() / 2) - 100, (int) (MyFrame.size.getHeight() / 2) - 100, startText.getWidth(), 100);

        frame.add(startText);
        refresh(); //guarantees that the text for appear, because the start text aint guaranteed for reasons god knows why
    }
}
