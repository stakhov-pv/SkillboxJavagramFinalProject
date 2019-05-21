import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class Res {
    private static final String RESOURCES_PATH = "res/";

    enum FONT_TYPE {
        REGULAR_FONT,
        LIGHT_FONT,
        SEMIBOLD_FONT
    }

    private static HashMap<String, BufferedImage> images = new HashMap<>();
    private static BufferedImage noImage = null;

    private static HashMap<String,Font> fonts = new HashMap<>();
    private static Font lightFont = null;
    private static Font regularFont = null;
    private static Font boldFont = null;
    private static Font noFont = null;

    public static synchronized BufferedImage getImage(String name) {
        if (!images.containsKey(name)) {
            BufferedImage resourceImage = null;
            try {
                String filename = RESOURCES_PATH + name;
                resourceImage = ImageIO.read(new File(filename));
            } catch (IOException e) {
                e.printStackTrace();
                resourceImage = getNoImage();
            }
            images.put(name,resourceImage);
        }
        return images.get(name);
    }

    public static synchronized Font getFont(FONT_TYPE type, float size) {
        if (type==null) throw new IllegalArgumentException("Font type must not be null");
        if (size<=0) throw new IllegalArgumentException("Font size must be greater than 0");
        String generatedFontName = type.toString()+":"+size;
        if (!fonts.containsKey(generatedFontName)) {
            Font derivedFont;

            if (regularFont==null) {
                try {
                    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

                    lightFont = Font.createFont(Font.TRUETYPE_FONT, new File("res/OpenSansLight.ttf"));
                    regularFont = Font.createFont(Font.TRUETYPE_FONT, new File("res/OpenSansRegular.ttf"));
                    boldFont = Font.createFont(Font.TRUETYPE_FONT, new File("res/OpenSansSemiBold.ttf"));

                    ge.registerFont(lightFont);
                    ge.registerFont(regularFont);
                    ge.registerFont(boldFont);

                } catch (IOException e) {
                    e.printStackTrace();
                    lightFont = getNoFont();
                    regularFont = getNoFont();
                    boldFont = getNoFont();
                } catch (FontFormatException e) {
                    e.printStackTrace();
                    lightFont = getNoFont();
                    regularFont = getNoFont();
                    boldFont = getNoFont();
                }
            }

            switch (type) {
                case LIGHT_FONT:
                    derivedFont = lightFont.deriveFont(size);
                    break;
                case SEMIBOLD_FONT:
                    derivedFont = boldFont.deriveFont(size);
                    break;
                case REGULAR_FONT:
                default:
                    derivedFont = regularFont.deriveFont(size);
                    break;
            }

            fonts.put(generatedFontName,derivedFont);
        }
        return fonts.get(generatedFontName);
    }

    private static synchronized Font getNoFont() {
        if (noFont==null) {
            noFont = new Font("Serif", Font.PLAIN, 10);
        }
        return noFont;
    }

    private static synchronized BufferedImage getNoImage() {
        if (noImage==null) {
            noImage = new BufferedImage(10,10,BufferedImage.TYPE_BYTE_BINARY);
        }
        return noImage;
    }
}
