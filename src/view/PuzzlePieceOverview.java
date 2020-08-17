package view;

import loaders.ImageLoader;

import java.awt.*;

public class PuzzlePieceOverview implements Drawable {
    private final String imageName;
    private final int x, y;

    public PuzzlePieceOverview(String imageName, int x, int y) {
        this.imageName = imageName;
        this.x = x;
        this.y = y;
    }

    @Override
    public void draw(Graphics g, int width, int height) {
        Image image = ImageLoader.getInstance().getImage(imageName);
        g.drawImage(image, x * width / 3, y * height / 3, width / 3, height / 3, null);
    }
}
