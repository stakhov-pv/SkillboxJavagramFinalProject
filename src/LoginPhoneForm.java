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
    private JPanel bigLogoPanel;
    private JButton bTest;
    private JPanel phoneIcon;
    private JTextField phoneValue;
    private JPanel headerPanel;
    private JPanel phoneState;
    private JButton continueButton;
    private JLabel label1;
    private JPanel phonePanel;
    private JButton closeButton;
    private JButton minimiseButton;
    private JPanel backgroundPanel;
    private JPanel codeState;
    private JPanel nameState;
    private JPasswordField codeValue;
    private JPanel lockIcon;
    private JPanel codePanel;
    private JButton codeButton;
    private JPanel codeLogo;
    private JLabel codePhoneLabel;
    private JLabel codeInfoLabel;
    private JButton nameButton;
    private JPanel nameLogo;
    private JLabel nameLabel;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JPanel nameIcon;
    private ImageIcon imageButton;


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

        closeButton = new JButton(new ImageIcon("res/icon-close.png"));
        closeButton.setBorderPainted(false);
        closeButton.setContentAreaFilled(false);
        closeButton.setFocusable(false);

        minimiseButton = new JButton(new ImageIcon("res/icon-hide.png"));
        minimiseButton.setBorderPainted(false);
        minimiseButton.setContentAreaFilled(false);
        minimiseButton.setFocusable(false);

        backgroundPanel = new JPanelImage(Res.getImage("background.png"));
        imageButton = new ImageIcon("res/button-background.png");

        phoneState = new JPanel();

        bigLogoPanel = new JPanelImage(Res.getImage("logo.png"));
        phoneIcon = new JPanelImage(Res.getImage("icon-phone.png"));

        phonePanel = new JPanel();
        Border phoneBorder = BorderFactory.createMatteBorder(0,0,2,0,Color.WHITE);
        phonePanel.setBorder(phoneBorder);

        label1 = new JLabel();
        label1.setFont(lightFont.deriveFont(17f));

        phoneValue = new JTextField();
        phoneValue.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        phoneValue.setFont(lightFont.deriveFont(42f));
        //TODO: disable default text selection
        //TODO: only phone (plus and digits) allowed
        phoneValue.setCaretPosition(phoneValue.getText().length());

        continueButton = new JButton(imageButton);
        continueButton.setBorderPainted(false);
        continueButton.setContentAreaFilled(false);
        continueButton.setFocusable(false);
        continueButton.setFont(lightFont.deriveFont(25f));




        codePanel = new JPanel();
        Border codeBorder = BorderFactory.createMatteBorder(0,0,2,0,Color.WHITE);
        codePanel.setBorder(codeBorder);

        codeLogo = new JPanelImage(Res.getImage("logo-mini.png"));
        codePhoneLabel = new JLabel();
        codePhoneLabel.setFont(lightFont.deriveFont(41f));
        codePhoneLabel.setForeground(new Color(195,191,190));

        codeInfoLabel = new JLabel();
        codeInfoLabel.setFont(lightFont.deriveFont(16f));

        lockIcon = new JPanelImage(Res.getImage("icon-lock.png"));

        codeValue = new JPasswordField();
        codeValue.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        //codeValue.setFont(lightFont.deriveFont(42f));
        //Font codeFont = codeValue.getFont().deriveFont(22f);
        //codeValue.setFont(codeFont);
        codeValue.setCaretPosition(phoneValue.getText().length());

        codeButton = new JButton(imageButton);
        codeButton.setBorderPainted(false);
        codeButton.setContentAreaFilled(false);
        codeButton.setFocusable(false);
        codeButton.setFont(lightFont.deriveFont(25f));

        nameLogo = codeLogo; //new JPanelImage("res/logo-mini.png");

        nameLabel = new JLabel();
        nameLabel.setFont(lightFont.deriveFont(16f));

        nameIcon = new JPanelImage(Res.getImage("your-face.png"));

        nameButton = new JButton(imageButton);
        nameButton.setBorderPainted(false);
        nameButton.setContentAreaFilled(false);
        nameButton.setFocusable(false);
        nameButton.setFont(lightFont.deriveFont(25f));



    }



    interface LoginFormListener {
        void onTestButtonPressed();
    }

}
