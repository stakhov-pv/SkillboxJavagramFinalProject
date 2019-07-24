package view;

import provider.Res;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;

public class EditContactView {
    public interface Listener {
        void onBackPressed();
        void onEditContactPressed(String name);
        void onDeleteContactPressed();
    }

    private String name;
    private String phone;
    private Listener listener;
    private BufferedImage image;

    private JPanel editContactPanel;
    private JLabel editContactLabel;
    private JTextField nameField;
    private JPanel saveButtonBackground;
    private JButton saveButton;
    private JLabel saveContactProcessLabel;
    private JPanel backPanel;
    private JButton backButton;
    private JPanel namePanel;
    private JPanel profileImagePanel;
    private JLabel phoneLabel;
    private JLabel deleteUserLabel;
    private JPanel deleteUserPanel;

    public EditContactView(String name, String phone, BufferedImage image) {
        this.name = name;
        this.phone = phone;
        this.image = image;
    }

    public void attachListener(Listener listener) {
        this.listener = listener;
    }

    public void detachListener() {
        listener = null;
    }

    public JPanel getEditContactPanel() {
        return editContactPanel;
    }

    private void createUIComponents() {
        editContactPanel = new JImage(Res.getImage("transparent.png"),new Dimension(900,630));
        editContactPanel.addMouseListener(new PanelClickListener( ()->{} ));

        editContactLabel = new JLabel();
        editContactLabel.setFont(Res.getFont(Res.FONT_TYPE.LIGHT_FONT,43f));

        Border textFieldBorder = BorderFactory.createMatteBorder(0,0,2,0,Color.WHITE);

        nameField = new JTextField(name);
        nameField.setBorder(textFieldBorder);
        nameField.setFont(Res.getFont(Res.FONT_TYPE.LIGHT_FONT,32f));
        nameField.requestFocusInWindow();

        TextPrompt nameTextPrompt = new TextPrompt("Имя Фамилия", nameField);
        nameTextPrompt.setForeground( Color.WHITE );
        nameTextPrompt.changeAlpha(0.5f);

        profileImagePanel = new JImage(image, new Dimension(72,72));

        phoneLabel = new JLabel(phone);
        phoneLabel.setFont(Res.getFont(Res.FONT_TYPE.LIGHT_FONT,18f));

        deleteUserLabel = new JLabel();
        deleteUserLabel.setFont(Res.getFont(Res.FONT_TYPE.LIGHT_FONT,18f));
        deleteUserLabel.addMouseListener(new PanelClickListener( ()-> {
            if (listener!=null) {
                showProcessingState();
                listener.onDeleteContactPressed();
            }
        } ));
        Border deleteUserBorder = BorderFactory.createMatteBorder(2,2,2,2,Color.RED);
        deleteUserLabel.setIcon(new ImageIcon("res/icon-trash.png"));
        deleteUserPanel = new JPanel();
        deleteUserPanel.setBorder(deleteUserBorder);

        saveButtonBackground = new JImage(Res.getImage("button-background.png"));
        saveButton = new JButton();
        saveButton.setBorderPainted(false);
        saveButton.setContentAreaFilled(false);
        saveButton.setFont(Res.getFont(Res.FONT_TYPE.LIGHT_FONT,25f));
        saveButton.setAction(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (nameField==null) return;
                String name = nameField.getText().trim();
                if (name.length()==0) return;
                if (listener!=null) {
                    showProcessingState();
                    listener.onEditContactPressed(name);
                }
            }
        });

        saveContactProcessLabel = new JLabel();
        saveContactProcessLabel.setIcon(new ImageIcon("res/processing.gif"));

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

        showWaitForActionsState();
    }

    private void showProcessingState() {
        nameField.setEnabled(false);
        saveButton.setVisible(false);
        saveButtonBackground.setVisible(false);
        saveContactProcessLabel.setVisible(true);
        backButton.setEnabled(false);
        deleteUserLabel.setEnabled(false);
    }

    private void showWaitForActionsState() {
        nameField.setEnabled(true);
        saveButton.setVisible(true);
        saveButtonBackground.setVisible(true);
        saveContactProcessLabel.setVisible(false);
        backButton.setEnabled(true);
        deleteUserLabel.setEnabled(true);
    }
}
