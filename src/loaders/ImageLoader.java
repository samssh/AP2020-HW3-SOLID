package loaders;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ImageLoader {
    private final Map<String, BufferedImage> bufferedImages;
    private static final ImageLoader instance = new ImageLoader();

    public static ImageLoader getInstance() {
        return instance;
    }

    private ImageLoader() {
        bufferedImages = new HashMap<>();
    }

    public BufferedImage getImage(String s) {
        if (!bufferedImages.containsKey(s)) {
            String address = MyConfigLoader.getInstance().getProperty(s);
            File file = new File(address);
            try {
                bufferedImages.put(s, ImageIO.read(file));
            } catch (IOException e) {
                return null;
            }
        }
        return bufferedImages.get(s);
    }
}
