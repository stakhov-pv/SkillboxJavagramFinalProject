import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class JPanelImage extends JPanel {
    private BufferedImage background;

    public JPanelImage(BufferedImage image) {
        this.background = image;
        setPreferredSize(new Dimension(background.getWidth(), background.getHeight()));
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
