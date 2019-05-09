import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
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
    private JPanel headerPanel;
    private JPanel backgroundPanel;
    private JButton continueButton;
    private JLabel label1;
    private JPanel phonePanel;
    private BufferedImage logo;
    private LoginFormListener listener;

    public LoginPhoneForm(LoginFormListener listener) {
        this.listener = listener;
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
        //ImageIcon ic = new ImageIcon(logo);
        //logoLabel = new JLabel(ic);
        //logoPanel = new JPanel() {
        //    @Override
        //    protected void paintComponent(Graphics graphics) {
        //        super.paintComponent(graphics);
        //        graphics.drawImage(logo,0,0, logo.getWidth(), logo.getHeight(),null);
        //    }
        //};

        Font lightFont = null;
        try {
            //create the font to use. Specify the size!
            lightFont = Font.createFont(Font.TRUETYPE_FONT, new File("res/OpenSansLight.ttf"));
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            //register the font
            ge.registerFont(lightFont);
        } catch (IOException e) {
            e.printStackTrace();
        } catch(FontFormatException e) {
            e.printStackTrace();
        }

        logoPanel = new JPanelImage("res/logo.png");
        phoneIcon = new JPanelImage("res/icon-phone.png");
        //phoneIcon = new JPanelImage("res/your-face.png");
        backgroundPanel = new JPanelImage("res/background.png");
        //continueButton = new JButtonImage("res/button-background.png");

        phonePanel = new JPanel();
        Border phoneBorder = BorderFactory.createMatteBorder(0,0,2,0,Color.WHITE);
        phonePanel.setBorder(phoneBorder);

        ImageIcon imageForOne = new ImageIcon("res/button-background.png");
        continueButton = new JButton(imageForOne);
        continueButton.setBorderPainted(false);
        continueButton.setContentAreaFilled(false);
        continueButton.setFocusable(false);
        continueButton.setFont(lightFont.deriveFont(28f));

        phoneValue = new JTextField();
        phoneValue.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        //phoneValue.setText("+7");
        phoneValue.setFont(lightFont.deriveFont(44f));

        label1 = new JLabel();
        label1.setFont(lightFont.deriveFont(18f));

    }

    interface LoginFormListener {
        void onTestButtonPressed();
    }

}
