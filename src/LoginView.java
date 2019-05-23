import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginView {

    public enum LoginState {
        Init,
        AskPhone,
        ProcessingPhone,
        AskCode,
        ProcessingCode,
        AskNewProfile,
        ProcessingNewProfile,
    }

    private Listener listener;

    public interface Listener {
        void onPhoneButtonPressed();
        void onCodeButtonPressed();
        void onNameButtonPressed();
    }

    private JPanel rootPanel;
    private JPanel bigLogoPanel;
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
    private JPanel continuePanel;
    private ImageIcon imageButton;

    //private LoginFormListener listener;


    public LoginView(/*LoginFormListener listener*/) {
    //    this.listener = listener;
    //    bTest.addActionListener(new ActionListener() {
    //        @Override
    //        public void actionPerformed(ActionEvent e) {
    //            if (listener!=null) listener.onTestButtonPressed();
    //        }
    //    });
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }

    private void createUIComponents() {

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
        label1.setFont(Res.getFont(Res.FONT_TYPE.LIGHT_FONT,17f));

        phoneValue = new JTextField();
        phoneValue.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        phoneValue.setFont(Res.getFont(Res.FONT_TYPE.LIGHT_FONT,42f));
        //TODO: disable default text selection
        //TODO: only phone (plus and digits) allowed
        phoneValue.setCaretPosition(phoneValue.getText().length());

        continueButton = new JButton(new ImageIcon("res/button-background.png"));
        //continueButton.setBorderPainted(false);
        //continueButton.setContentAreaFilled(false);
        //continueButton.setFocusable(false);
        //continueButton.setFont(Res.getFont(Res.FONT_TYPE.LIGHT_FONT,25f));
        continuePanel = new JPanelImage(Res.getImage("button-background.png"));
        JLabel continuePanelLabel = new JLabel("ПРОДОЛЖИТЬ");
        continuePanelLabel.setForeground(Color.WHITE);
        continuePanel.add(continuePanelLabel);



        codePanel = new JPanel();
        Border codeBorder = BorderFactory.createMatteBorder(0,0,2,0,Color.WHITE);
        codePanel.setBorder(codeBorder);

        codeLogo = new JPanelImage(Res.getImage("logo-mini.png"));
        codePhoneLabel = new JLabel();
        codePhoneLabel.setFont(Res.getFont(Res.FONT_TYPE.LIGHT_FONT,41f));
        codePhoneLabel.setForeground(new Color(195,191,190));

        codeInfoLabel = new JLabel();
        codeInfoLabel.setFont(Res.getFont(Res.FONT_TYPE.LIGHT_FONT,16f));

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
        codeButton.setFont(Res.getFont(Res.FONT_TYPE.LIGHT_FONT,25f));

        nameLogo = new JPanelImage(Res.getImage("logo-mini.png"));

        nameLabel = new JLabel();
        nameLabel.setFont(Res.getFont(Res.FONT_TYPE.LIGHT_FONT,16f));

        nameIcon = new JPanelImage(Res.getImage("your-face.png"));

        nameButton = new JButton(imageButton);
        nameButton.setBorderPainted(false);
        nameButton.setContentAreaFilled(false);
        nameButton.setFocusable(false);
        nameButton.setFont(Res.getFont(Res.FONT_TYPE.LIGHT_FONT,25f));

        continueButton.setAction(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (listener!=null) listener.onPhoneButtonPressed();
            }
        });

        codeButton.setAction(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (listener!=null) listener.onCodeButtonPressed();
            }
        });

        nameButton.setAction(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (listener!=null) listener.onNameButtonPressed();
            }
        });

    }

    //interface LoginFormListener {
    //    void onTestButtonPressed();
    //}

    void attachListener(Listener listener) {
        this.listener = listener;
    }

    void detachListener() {
        this.listener=null;
    }

    void show() {
        Gui.getInstance().changePane(rootPanel);
        showState(LoginState.AskPhone);
    }

    void showState(LoginState newState) {

        phoneState.setVisible(false);
        codeState.setVisible(false);
        nameState.setVisible(false);
        switch (newState) {
            case Init:
                break;
            case AskPhone:
                phoneState.setVisible(true);
                break;
            case ProcessingPhone:
                phoneState.setVisible(true);
                break;
            case AskCode:
                codeState.setVisible(true);
                break;
            case ProcessingCode:
                codeState.setVisible(true);
                break;
            case AskNewProfile:
                nameState.setVisible(true);
                break;
            case ProcessingNewProfile:
                nameState.setVisible(true);
                break;
        }
        Gui.getInstance().validate();

    }
}
