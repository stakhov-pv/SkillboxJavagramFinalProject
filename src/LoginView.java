import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

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

    private JPanel headerPanel;
    private JButton closeButton;
    private JButton minimiseButton;

    private JPanel bigLogoPanel;
    private JPanel phoneState;
    private JLabel phoneMessageLabel;
    private JPanel phonePanel;
    private JPanel phoneIcon;
    private JTextField phoneValue;

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
    private JPanel phoneButtonBackground;
    private JButton phoneButton;

    public LoginView() {
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

        backgroundPanel = new JImage(Res.getImage("background.png"));

        phoneState = new JPanel();

        bigLogoPanel = new JImage(Res.getImage("logo.png"));
        phoneIcon = new JImage(Res.getImage("icon-phone.png"));

        phonePanel = new JPanel();
        Border phoneBorder = BorderFactory.createMatteBorder(0,0,2,0,Color.WHITE);
        phonePanel.setBorder(phoneBorder);

        phoneMessageLabel = new JLabel();
        phoneMessageLabel.setFont(Res.getFont(Res.FONT_TYPE.LIGHT_FONT,17f));

        phoneValue = new JTextField();
        phoneValue.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        phoneValue.setFont(Res.getFont(Res.FONT_TYPE.LIGHT_FONT,42f));
        //TODO: disable default text selection
        //TODO: only phone (plus and digits) allowed
        phoneValue.setCaretPosition(phoneValue.getText().length());

        phoneButtonBackground = new JImage(Res.getImage("button-background.png"));
        phoneButton = new JButton();
        phoneButton.setFont(Res.getFont(Res.FONT_TYPE.LIGHT_FONT,25f));
        phoneButton.setForeground(Color.WHITE);


        codePanel = new JPanel();
        Border codeBorder = BorderFactory.createMatteBorder(0,0,2,0,Color.WHITE);
        codePanel.setBorder(codeBorder);

        codeLogo = new JImage(Res.getImage("logo-mini.png"));
        codePhoneLabel = new JLabel();
        codePhoneLabel.setFont(Res.getFont(Res.FONT_TYPE.LIGHT_FONT,41f));
        codePhoneLabel.setForeground(new Color(195,191,190));

        codeInfoLabel = new JLabel();
        codeInfoLabel.setFont(Res.getFont(Res.FONT_TYPE.LIGHT_FONT,16f));

        lockIcon = new JImage(Res.getImage("icon-lock.png"));

        codeValue = new JPasswordField();
        codeValue.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        //codeValue.setFont(lightFont.deriveFont(42f));
        //Font codeFont = codeValue.getFont().deriveFont(22f);
        //codeValue.setFont(codeFont);
        codeValue.setCaretPosition(phoneValue.getText().length());

        codeButton = new JButton(new ImageIcon("res/button-background.png"));
        codeButton.setBorderPainted(false);
        codeButton.setContentAreaFilled(false);
        codeButton.setFocusable(false);
        codeButton.setFont(Res.getFont(Res.FONT_TYPE.LIGHT_FONT,25f));

        nameLogo = new JImage(Res.getImage("logo-mini.png"));

        nameLabel = new JLabel();
        nameLabel.setFont(Res.getFont(Res.FONT_TYPE.LIGHT_FONT,16f));

        nameIcon = new JImage(Res.getImage("your-face.png"));

        nameButton = new JButton(new ImageIcon("res/button-background.png"));
        nameButton.setBorderPainted(false);
        nameButton.setContentAreaFilled(false);
        nameButton.setFocusable(false);
        nameButton.setFont(Res.getFont(Res.FONT_TYPE.LIGHT_FONT,25f));

        phoneButton.setAction(new AbstractAction() {
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

        minimiseButton.setAction(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

            }
        });

        closeButton.setAction(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

            }
        });

    }

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
