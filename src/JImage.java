import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class JImage extends JPanel {
    private BufferedImage background;

    public JImage(BufferedImage image) {
        this.background = image;
        Dimension size = new Dimension(background.getWidth(), background.getHeight());
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);

        setOpaque(false);
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

    public void replaceImage(BufferedImage image) {
        this.background = image;
        Dimension size = new Dimension(background.getWidth(), background.getHeight());
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);

        setOpaque(false);
        validate();
    }
}
