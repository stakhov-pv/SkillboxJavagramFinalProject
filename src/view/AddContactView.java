package view;

import provider.Gui;
import provider.Res;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;

public class AddContactView {

    public interface Listener {
        void onBackPressed();
        void onAddContactPressed(String firstName, String lastName, String phone);
    }

    private Listener listener;

    private JPanel addContactPanel;
    private JLabel addContactLabel;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JPanel addButtonBackground;
    private JButton addButton;
    private JLabel addContactProcessLabel;
    private JPanel backPanel;
    private JButton backButton;
    private JTextField phoneField;
    private JLabel messageLabel;

    public JPanel getAddContactPanel() {
        return addContactPanel;
    }

    public void attachListener(Listener listener) {
        this.listener = listener;
    }

    public void detachListener() {
        listener = null;
    }

    private void createUIComponents() {
        addContactPanel = new JImage(Res.getImage("transparent.png"),new Dimension(Gui.POPUP_WIDTH,Gui.POPUP_HEIGHT));
        addContactPanel.addMouseListener(new PanelClickListener( ()->{} ));

        addContactLabel = new JLabel();
        addContactLabel.setFont(Res.getFont(Res.FONT_TYPE.LIGHT_FONT,43f));

        messageLabel = new JLabel();
        messageLabel.setFont(Res.getFont(Res.FONT_TYPE.LIGHT_FONT,17f));


        Border textFieldBorder = BorderFactory.createMatteBorder(0,0,2,0,Color.WHITE);

        phoneField = new JTextField();
        phoneField.setBorder(textFieldBorder);
        phoneField.setFont(Res.getFont(Res.FONT_TYPE.LIGHT_FONT,42f));

        TextPrompt phoneTextPrompt = new TextPrompt("Телефон", phoneField);
        phoneTextPrompt.setForeground( Color.WHITE );
        phoneTextPrompt.changeAlpha(0.5f);

        firstNameField = new JTextField();
        firstNameField.setBorder(textFieldBorder);
        firstNameField.setFont(Res.getFont(Res.FONT_TYPE.LIGHT_FONT,32f));

        TextPrompt firstNameTextPrompt = new TextPrompt("Имя", firstNameField);
        firstNameTextPrompt.setForeground( Color.WHITE );
        firstNameTextPrompt.changeAlpha(0.5f);

        lastNameField = new JTextField();
        lastNameField.setBorder(textFieldBorder);
        lastNameField.setFont(Res.getFont(Res.FONT_TYPE.LIGHT_FONT,32f));

        TextPrompt lastNameTextPrompt = new TextPrompt("Фамилия", lastNameField);
        lastNameTextPrompt.setForeground( Color.WHITE );
        lastNameTextPrompt.changeAlpha(0.5f);

        addButtonBackground = new JImage(Res.getImage("button-background.png"));
        addButton = new JButton();
        addButton.setBorderPainted(false);
        addButton.setContentAreaFilled(false);
        //nameButton.setFocusable(false);
        addButton.setFont(Res.getFont(Res.FONT_TYPE.LIGHT_FONT,25f));
        addButton.setAction(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (firstNameField==null || lastNameField==null || phoneField==null) return;
                String firstName = firstNameField.getText().trim();
                String lastName = lastNameField.getText().trim();
                String phone = phoneField.getText().trim();
                if (firstName.length()==0 || lastName.length()==0 || phone.length()==0) return;
                if (listener!=null) {
                    showProcessingState();
                    listener.onAddContactPressed(firstName, lastName, phone);
                }
            }
        });

        addContactProcessLabel = new JLabel();
        addContactProcessLabel.setIcon(new ImageIcon("res/processing.gif"));

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

        backPanel = new JPanel();

        showWaitForActionsState();
    }

    private void showProcessingState() {
        firstNameField.setEnabled(false);
        lastNameField.setEnabled(false);
        phoneField.setEnabled(false);
        addButton.setVisible(false);
        addButtonBackground.setVisible(false);
        addContactProcessLabel.setVisible(true);
        backButton.setEnabled(false);
    }

    private void showWaitForActionsState() {
        firstNameField.setEnabled(true);
        lastNameField.setEnabled(true);
        phoneField.setEnabled(true);
        addButton.setVisible(true);
        addButtonBackground.setVisible(true);
        addContactProcessLabel.setVisible(false);
        backButton.setEnabled(true);
    }
}
