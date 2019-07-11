package view;

import provider.Res;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class EditProfileView {
    private JPanel editProfilePanel;
    private JLabel profileLabel;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JPanel saveButtonBackground;
    private JButton saveButton;
    private JLabel profileProcessLabel;
    private JButton backButton;
    private JLabel exitButtonLabel;
    private JLabel phoneLabel;

    public JPanel getEditProfilePanel() {
        return editProfilePanel;
    }

    private void createUIComponents() {
        editProfilePanel = new JImage(Res.getImage("transparent.png"),new Dimension(200,100));

        profileLabel = new JLabel();
        firstNameField = new JTextField();
        lastNameField = new JTextField();


        saveButtonBackground = new JImage(Res.getImage("button-background.png"));
        saveButton = new JButton();
        saveButton.setBorderPainted(false);
        saveButton.setContentAreaFilled(false);
        //nameButton.setFocusable(false);
        saveButton.setFont(Res.getFont(Res.FONT_TYPE.LIGHT_FONT,25f));
        saveButton.setAction(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //if (listener!=null) listener.onNameButtonPressed();
            }
        });

        profileProcessLabel = new JLabel();
        profileProcessLabel.setIcon(new ImageIcon("res/processing.gif"));
    }
}
