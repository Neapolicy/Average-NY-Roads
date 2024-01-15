import java.awt.*;
import java.awt.event.KeyEvent;

public class Box extends Rectangle {
    private Color color;

    public Box(int x, int y, int width, int height)
    {
        super(x, y, width, height);
        this.color = new Color(0, 0, 0, 0);
    }

}
