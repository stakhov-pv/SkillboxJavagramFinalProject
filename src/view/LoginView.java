package view;

import provider.Gui;
import provider.Res;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;

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
    private JPanel nameButtonBackground;
    private JPanel codeButtonBackground;

    public LoginView() {
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }

    public String getPhoneValue() {
        if (phoneValue==null) return "";
        return phoneValue.getText();
    }

    public String getCodeValue() {
        if (codeValue==null) return "";
        return codeValue.getText();
    }

    public void setCodePhoneLabel(String phone) {
        codePhoneLabel.setText(phone);
    }

    private void createUIComponents() {

        closeButton = new JButton();
        closeButton.setBorderPainted(false);
        closeButton.setContentAreaFilled(false);
        closeButton.setFocusable(false);
        closeButton.setAction(new AbstractAction(null, new ImageIcon("res/icon-close.png")) {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Gui.getInstance().doClose();
            }
        });

        minimiseButton = new JButton();
        minimiseButton.setBorderPainted(false);
        minimiseButton.setContentAreaFilled(false);
        minimiseButton.setFocusable(false);
        minimiseButton.setAction(new AbstractAction(null,new ImageIcon("res/icon-hide.png")) {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Gui.getInstance().doMinimize();
            }
        });

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
        phoneButton.setAction(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (listener!=null) listener.onPhoneButtonPressed();
            }
        });

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
        codeValue.setCaretPosition(phoneValue.getText().length());

        codeButtonBackground = new JImage(Res.getImage("button-background.png"));
        codeButton = new JButton();
        codeButton.setBorderPainted(false);
        codeButton.setContentAreaFilled(false);
        codeButton.setFont(Res.getFont(Res.FONT_TYPE.LIGHT_FONT,25f));
        codeButton.setAction(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (listener!=null) listener.onCodeButtonPressed();
            }
        });

        nameLogo = new JImage(Res.getImage("logo-mini.png"));

        nameLabel = new JLabel();
        nameLabel.setFont(Res.getFont(Res.FONT_TYPE.LIGHT_FONT,16f));

        nameIcon = new JImage(Res.getImage("your-face.png"));

        nameButtonBackground = new JImage(Res.getImage("button-background.png"));
        nameButton = new JButton();
        nameButton.setBorderPainted(false);
        nameButton.setContentAreaFilled(false);
        //nameButton.setFocusable(false);
        nameButton.setFont(Res.getFont(Res.FONT_TYPE.LIGHT_FONT,25f));
        nameButton.setAction(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (listener!=null) listener.onNameButtonPressed();
            }
        });

    }

    public void attachListener(Listener listener) {
        this.listener = listener;
    }

    public void detachListener() {
        this.listener=null;
    }

    public void show() {
        Gui.getInstance().changePane(rootPanel);
        showState(LoginState.AskPhone);
    }

    public void showState(LoginState newState) {

        phoneState.setVisible(false);
        codeState.setVisible(false);
        nameState.setVisible(false);
        switch (newState) {
            case Init:
                break;
            case AskPhone:
                phoneState.setVisible(true);
                phoneButton.setEnabled(true);
                break;
            case ProcessingPhone:
                phoneState.setVisible(true);
                phoneButton.setEnabled(false);
                break;
            case AskCode:
                codeValue.setText("");
                codeState.setVisible(true);
                codeButton.setEnabled(true);
                break;
            case ProcessingCode:
                codeState.setVisible(true);
                codeButton.setEnabled(false);
                break;
            case AskNewProfile:
                nameState.setVisible(true);
                nameButton.setEnabled(true);
                break;
            case ProcessingNewProfile:
                nameState.setVisible(true);
                nameButton.setEnabled(false);
                break;
        }
        Gui.getInstance().validate();

    }
}
