package view;

import provider.Res;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;

public class EditProfileView {

    public interface Listener {
        void onBackPressed();
        void onExitPressed();
        void onSaveChangesPressed(String firstName, String lastName);
    }

    private Listener listener;

    private String firstName;
    private String lastName;
    private String phone;

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
    private JPanel backExitPhonePanel;

    public EditProfileView(String firstName, String lastName, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
    }

    public JPanel getEditProfilePanel() {
        return editProfilePanel;
    }

    public void attachListener(Listener listener) {
        this.listener = listener;
    }

    public void detachListener() {
        this.listener = null;
    }

    private void createUIComponents() {
        editProfilePanel = new JImage(Res.getImage("transparent.png"),new Dimension(900,630));

        profileLabel = new JLabel();
        profileLabel.setFont(Res.getFont(Res.FONT_TYPE.LIGHT_FONT,43f));

        Border textFieldBorder = BorderFactory.createMatteBorder(0,0,2,0,Color.WHITE);


        firstNameField = new JTextField(firstName);
        firstNameField.setBorder(textFieldBorder);
        firstNameField.setFont(Res.getFont(Res.FONT_TYPE.LIGHT_FONT,42f));

        TextPrompt firstNameTextPrompt = new TextPrompt("Имя", firstNameField);
        firstNameTextPrompt.setForeground( Color.WHITE );
        firstNameTextPrompt.changeAlpha(0.5f);

        lastNameField = new JTextField(lastName);
        lastNameField.setBorder(textFieldBorder);
        lastNameField.setFont(Res.getFont(Res.FONT_TYPE.LIGHT_FONT,42f));

        TextPrompt lastNameTextPrompt = new TextPrompt("Фамилия", lastNameField);
        lastNameTextPrompt.setForeground( Color.WHITE );
        lastNameTextPrompt.changeAlpha(0.5f);

        saveButtonBackground = new JImage(Res.getImage("button-background.png"));
        saveButton = new JButton();
        saveButton.setBorderPainted(false);
        saveButton.setContentAreaFilled(false);
        //nameButton.setFocusable(false);
        saveButton.setFont(Res.getFont(Res.FONT_TYPE.LIGHT_FONT,25f));
        saveButton.setAction(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (firstNameField==null || lastNameField==null) return;
                String firstName = firstNameField.getText().trim();
                String lastName = lastNameField.getText().trim();
                if (firstName.length()==0 || lastName.length()==0) return;
                if (listener!=null) {
                    showProcessingState();
                    listener.onSaveChangesPressed(firstName, lastName);
                }
            }
        });

        profileProcessLabel = new JLabel();
        profileProcessLabel.setIcon(new ImageIcon("res/processing.gif"));

        phoneLabel = new JLabel(phone);
        phoneLabel.setFont(Res.getFont(Res.FONT_TYPE.LIGHT_FONT,18f));

        exitButtonLabel = new JLabel();
        exitButtonLabel.setFont(Res.getFont(Res.FONT_TYPE.LIGHT_FONT,18f));
        exitButtonLabel.addMouseListener(new PanelClickListener( ()->listener.onExitPressed() ));

        backButton = new JButton();
        backButton.setBorderPainted(false);
        backButton.setContentAreaFilled(false);
        backButton.setFocusable(false);
        backButton.setAction(new AbstractAction(null,new ImageIcon("res/icon-back.png")) {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (listener!=null) listener.onBackPressed();
            }
        });

        backExitPhonePanel = new JPanel();

        showWaitForActionsState();
    }

    private void showProcessingState() {
        firstNameField.setEnabled(false);
        lastNameField.setEnabled(false);
        saveButton.setVisible(false);
        saveButtonBackground.setVisible(false);
        profileProcessLabel.setVisible(true);
        backExitPhonePanel.setVisible(false);
    }

    private void showWaitForActionsState() {
        firstNameField.setEnabled(true);
        lastNameField.setEnabled(true);
        saveButton.setVisible(true);
        saveButtonBackground.setVisible(true);
        profileProcessLabel.setVisible(false);
        backExitPhonePanel.setVisible(true);
    }
}
