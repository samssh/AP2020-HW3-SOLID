package view;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MyPanel extends JPanel {
    private List<Drawable> drawables = new ArrayList<>();

    public MyPanel() {
        int screenWidth, screenHeight;
        screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        int maxSize = Math.max(screenWidth, screenHeight) / 3;
        Dimension dimension = new Dimension(maxSize, maxSize);
        this.setPreferredSize(dimension);
        this.setSize(dimension);
        this.setLocation(screenWidth / 2 - maxSize / 2, screenHeight / 2 - maxSize / 2);
    }

    public void setDrawables(List<Drawable> drawables) {
        this.drawables = drawables;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawables.forEach(drawable -> drawable.draw(g, getWidth(), getHeight()));
    }
}
