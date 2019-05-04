import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class LoginPhoneForm {
    private JPanel rootPanel;
    private BufferedImage background;

    public LoginPhoneForm() {
        try {
            background = ImageIO.read(new File("res/background.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }

    private void createUIComponents() {
        rootPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics graphics) {
                super.paintComponent(graphics);
                graphics.drawImage(background,0,0, null);
            }
        };
    }

}
