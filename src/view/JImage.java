package view;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class JImage extends JPanel {
    private BufferedImage background;
    private Dimension size;

    public JImage(BufferedImage image) {
        init(image);
    }

    public JImage(BufferedImage image, Dimension size) {
        init(image,size);
    }

    private void init(BufferedImage image) {
        init(image, new Dimension(image.getWidth(), image.getHeight()));
    }

    private void init(BufferedImage image, Dimension size) {
        this.background = image;
        this.size = size;
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        if (background!=null) {
            graphics.drawImage(background, 0, 0, size.width, size.height, null);
        } else {
            graphics.fillRect(0,0,20,20);
        }
    }

    public void replaceImage(BufferedImage image) {
        init(image);
        validate();
    }

    public void replaceImage(BufferedImage image, Dimension size) {
        init(image,size);
        repaint();
    }
}
