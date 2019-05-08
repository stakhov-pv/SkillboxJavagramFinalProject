import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class LoginPhoneForm {
    private JPanel rootPanel;
    private JPanel logoPanel;
    private JButton bTest;
    private JPanel phoneIcon;
    private JTextField phoneValue;
    private BufferedImage background;
    private BufferedImage logo;
    private LoginFormListener listener;

    public LoginPhoneForm(LoginFormListener listener) {
        this.listener = listener;
        try {
            background = ImageIO.read(new File("res/background.png"));
            //logo = ImageIO.read(new File("res/logo.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        bTest.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (listener!=null) listener.onTestButtonPressed();
            }
        });
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }

    private void createUIComponents() {
        rootPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics graphics) {
                super.paintComponent(graphics);
                graphics.drawImage(background,0,0, rootPanel.getWidth(), rootPanel.getHeight(),null);
            }
        };

        //ImageIcon ic = new ImageIcon(logo);
        //logoLabel = new JLabel(ic);
        //logoPanel = new JPanel() {
        //    @Override
        //    protected void paintComponent(Graphics graphics) {
        //        super.paintComponent(graphics);
        //        graphics.drawImage(logo,0,0, logo.getWidth(), logo.getHeight(),null);
        //    }
        //};

        logoPanel = new JPanelImage("res/logo.png");
        //phoneIcon = new JPanelImage("res/icon-phone.png");
        phoneIcon = new JPanelImage("res/your-face.png");

    }

    interface LoginFormListener {
        void onTestButtonPressed();
    }

}
