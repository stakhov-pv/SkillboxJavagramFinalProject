import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class Res {
    private static final String RESOURCES_PATH = "res/";

    private static HashMap<String, BufferedImage> images = new HashMap<>();
    private static BufferedImage noImage = null;

    public static synchronized BufferedImage getImage(String name) {
        if (!images.containsKey(name)) {
            BufferedImage resourceImage = null;
            try {
                String filename = RESOURCES_PATH + name;
                resourceImage = ImageIO.read(new File(filename));
            } catch (IOException e) {
                e.printStackTrace();
                //TODO: return abstract pic if no resource found
                resourceImage = getNoImage();
            }
            images.put(name,resourceImage);
        }
        return images.get(name);
    }

    private static synchronized BufferedImage getNoImage() {
        if (noImage==null) {
            noImage = new BufferedImage(10,10,BufferedImage.TYPE_BYTE_BINARY);
        }
        return noImage;
    }
}
