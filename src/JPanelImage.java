import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class JPanelImage extends JPanel {
    private BufferedImage background;

    public JPanelImage(String imageFilename) {
        try {
            background = ImageIO.read(new File(imageFilename));
            setPreferredSize(new Dimension(background.getWidth(), background.getHeight()));
        } catch (IOException e) {
            System.out.println("JPanelImage: Error on reading "+imageFilename);
            e.printStackTrace();
        }
        this.background = background;
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        if (background!=null) {
            graphics.drawImage(background, 0, 0, background.getWidth(), background.getHeight(), null);
        } else {
            graphics.fillRect(0,0,20,20);
        }
    }
}
