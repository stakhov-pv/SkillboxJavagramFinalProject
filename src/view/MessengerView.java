package view;

import model.ConversationTopic;
import org.javagram.response.object.Message;
import org.javagram.response.object.User;
import provider.Gui;
import provider.Res;
import provider.TelegramProvider;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class MessengerView {

    public enum MessengerState {
        Init,
        Messenger,
    }

    private Listener listener;

    public interface Listener {
        void onSendButtonPressed(String message);
        void onProfileButtonPressed();
        void onEditUserButtonPressed();
        void onAddUserButtonPressed();
        void onMinimiseButtonPressed();
        void onCloseButtonPressed();
        void onSelectConversation(int index);
        void onCloseProfileEditor();
        void onSaveProfileEditor(String firstName, String lastName);
        void onCloseUserEditor();
        void onSaveUserEditor(int userId, String phone, String firstName, String lastName);
        void onDeleteUserPressed(int userId);
        void onImportContactPressed();
        void onImportContactFilled(String firstName, String lastName, String phone);
        void onLogoff();
    }

    private JPanel rootPanel;
    private JPanel headerPanel;
    private JPanel logoAccountPanel;
    private JPanel mainPanel;
    private JPanel conversationsPanel;
    private JPanel chatPanel;
    private JPanel searchPanel;
    private JPanel conversationsListPanel;
    private JPanel chatPartnerPanel;
    private JPanel chatMessagesPanel;
    private JPanel messageEditorPanel;
    private JPanel logoPanel;
    private JPanel accountPanel;
    private JPanel searchIconPanel;
    private JPanel accountSettingsIconPanel;
    private JLabel accountNameLabel;
    private JPanel accountIconPanel;
    private JPanel partnerIconPanel;
    private JLabel partnerLabel;
    private JPanel editPartnerPanel;
    private JPanel user1;
    private JPanel user1AvatarPanel;
    private JPanel user2;
    private JLabel user1when;
    private JLabel user1Name;
    private JLabel user1LastMessage;
    private JLabel searchLabel;
    private JTextArea messageTextField;
    private JPanel messageButtonPanel;
    private JList conversationsList;
    private JButton closeButton;
    private JButton minimiseButton;
    private JList messagesList;
    private JPanel importContactPanel;
    private JPanel layeredPanel;


    public JPanel getRootPanel() {
        return rootPanel;
    }

    private void createUIComponents() {

        closeButton = new JButton();
        closeButton.setBorderPainted(false);
        closeButton.setContentAreaFilled(false);
        closeButton.setFocusable(false);
        closeButton.setAction(new AbstractAction(null, new ImageIcon("res/icon-close.png")) {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (listener!=null) listener.onCloseButtonPressed();
            }
        });

        minimiseButton = new JButton();
        minimiseButton.setBorderPainted(false);
        minimiseButton.setContentAreaFilled(false);
        minimiseButton.setFocusable(false);
        minimiseButton.setAction(new AbstractAction(null,new ImageIcon("res/icon-hide.png")) {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (listener!=null) listener.onMinimiseButtonPressed();
            }
        });

        layeredPanel = new JPanel();
        //JLayeredPane jLayeredPane = rootPanel.getRootPane().getLayeredPane();
        //EditProfileView editProfileView = new EditProfileView();
        //jLayeredPane.add(editProfileView.getEditProfilePanel(), JLayeredPane.POPUP_LAYER);
        //layeredPanel.add(editProfileView.getEditProfilePanel(), JLayeredPane.POPUP_LAYER);

        logoPanel = new JImage(Res.getImage("logo-micro.png"));
        searchIconPanel = new JImage(Res.getImage("icon-search.png"));

        accountPanel = new JPanel();
        accountPanel.addMouseListener(new PanelClickListener( ()->listener.onProfileButtonPressed() ));

        accountIconPanel = new JImage(Res.getImage("your-face.png"), new Dimension(29,29));
        accountSettingsIconPanel = new JImage(Res.getImage("icon-settings.png"));
        accountNameLabel = new JLabel();
        accountNameLabel.setFont(Res.getFont(Res.FONT_TYPE.REGULAR_FONT,14f));
        partnerIconPanel = new JImage(Res.getImage("current-user.png"), new Dimension(29,29));
        editPartnerPanel = new JImage(Res.getImage("icon-edit.png"));

        user1AvatarPanel = new JImage(Res.getImage("user-avatar.png"));

        Border conversationsRightBorder = BorderFactory.createMatteBorder(0,0,0,1,new Color(219,219,219));
        conversationsListPanel = new JPanel();
        conversationsListPanel.setBorder(conversationsRightBorder);

        Border searchPanelRightBottomBorder = BorderFactory.createMatteBorder(0,0,1,1,new Color(219,219,219));
        searchPanel = new JPanel();
        searchPanel.setBorder(searchPanelRightBottomBorder);

        searchLabel = new JLabel();
        searchLabel.setFont(Res.getFont(Res.FONT_TYPE.REGULAR_FONT,16f));

        importContactPanel = new JImage(Res.getImage("icon-plus.png"));
        importContactPanel.addMouseListener(new PanelClickListener( ()->listener.onImportContactPressed() ));

        Border chatPartnerBottomBorder = BorderFactory.createMatteBorder(0,0,1,0,new Color(237,237,227));
        chatPartnerPanel = new JPanel();
        chatPartnerPanel.setBorder(chatPartnerBottomBorder);
        chatPartnerPanel.addMouseListener(new PanelClickListener( () -> listener.onEditUserButtonPressed() ));

        Border conversationsBottomBorder = BorderFactory.createMatteBorder(0,0,1,0,new Color(219,219,219));
        user1 = new JPanel();
        user1.setBorder(conversationsBottomBorder);
        user2 = new JPanel();
        user2.setBorder(conversationsBottomBorder);

        user1Name = new JLabel();
        user1Name.setFont(Res.getFont(Res.FONT_TYPE.SEMIBOLD_FONT,16f));

        user1LastMessage = new JLabel();
        user1LastMessage.setFont(Res.getFont(Res.FONT_TYPE.REGULAR_FONT,12f));
        user1when = new JLabel();
        user1when.setFont(Res.getFont(Res.FONT_TYPE.REGULAR_FONT,10f));

        partnerLabel = new JLabel();
        partnerLabel.setFont(Res.getFont(Res.FONT_TYPE.REGULAR_FONT,14f));

        messageButtonPanel = new JImage(Res.getImage("button-send.png"));
        messageButtonPanel.addMouseListener(new PanelClickListener( () -> listener.onSendButtonPressed(messageTextField.getText()) ));

        conversationsList = new JList<ConversationTopic>();
        conversationsList.setCellRenderer(new ConversationTopicRenderer());
        conversationsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        conversationsList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                int index = conversationsList.getSelectedIndex();
                if (index>=0) listener.onSelectConversation(index);
            }
        });

        //TODO: show small list from bottom
        messagesList = new JList<Message>();
        messagesList.setCellRenderer(new MessageRenderer());
        messagesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    }

    public void attachListener(Listener listener) {
        this.listener = listener;
    }

    public void detachListener() {
        this.listener=null;
    }

    public void show() {
        Gui.getInstance().changePane(rootPanel);
        showState(MessengerState.Messenger);
    }

    public void showState(MessengerState newState) {
        switch (newState) {
            case Init:
                break;
            case Messenger:
                break;
        }
        Gui.getInstance().validate();
    }

    public void setProfile(String name, BufferedImage smallPic) {
        accountNameLabel.setText(name);
        ((view.JImage)accountIconPanel).replaceImage(smallPic,new Dimension(29,29));
    }

    public void showConversationTopics(ArrayList<ConversationTopic> conversations) {
        DefaultListModel<ConversationTopic> listModel = new DefaultListModel<>();
        listModel.addAll(conversations);
        conversationsList.setModel(listModel);
    }

    public void repaintConversationTopics() {
        conversationsList.repaint();
    }

    public void showChatPartner(User user) {
        if (user!=null) {
            partnerLabel.setText(user.getFirstName() + " " + user.getLastName());
        } else {
            partnerLabel.setText("User deleted");
        }

        BufferedImage image = null;
        if (user!=null) {
            image = TelegramProvider.getInstance().getUserPic(user,true);
        }
        if (image==null) {
            image = Res.getImage("your-face.png");
        }

        JImage avatar = (JImage)partnerIconPanel;
        avatar.replaceImage(image, new Dimension(29,29));

    }

    public void showConversationMessages(List<Message> messages) {
        DefaultListModel<Message> messageModel = new DefaultListModel<>();
        messageModel.addAll(messages);
        messagesList.setModel(messageModel);
        if (messages.size()>0) {
            messagesList.ensureIndexIsVisible(messages.size() - 1);
        }
    }

    public void emptyMessageTextField() {
        messageTextField.setText("");
    }

    public void minimiseApp() {
        Gui.getInstance().doMinimize();
    }

    public void closeApp() {
        Gui.getInstance().doClose();
    }

    public void showProfileEdit(String firstName, String lastName, String phone) {
        EditProfileView editProfileView = new EditProfileView(firstName, lastName, phone);
        editProfileView.attachListener(new EditProfileView.Listener() {
            @Override
            public void onBackPressed() {
                Gui.getInstance().hidePopup();
                listener.onCloseProfileEditor();
            }

            @Override
            public void onExitPressed() {
                Gui.getInstance().hidePopup();
                listener.onLogoff();
            }

            @Override
            public void onSaveChangesPressed(String firstName, String lastName) {
                listener.onSaveProfileEditor(firstName, lastName);
                Gui.getInstance().hidePopup();
                listener.onCloseProfileEditor();
            }
        });
        Gui.getInstance().showPopup(editProfileView.getEditProfilePanel());
    }

    public void showEditUser(int userId, String firstName, String lastName, String phone) {
        JPanel inputPanel = new JPanel();
        JTextField nameTextField = new JTextField(firstName+" "+lastName);
        JButton deleteUserButton = new JButton();
        deleteUserButton.setAction(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //TODO: how to close dialog?
                if (listener!=null) listener.onDeleteUserPressed(userId);
            }
        });
        deleteUserButton.setText("Delete user: "+phone);
        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(nameTextField);
        inputPanel.add(deleteUserButton);

        if (JOptionPane.OK_OPTION == JOptionPane.showConfirmDialog(
                rootPanel, inputPanel, "Change user", JOptionPane.OK_CANCEL_OPTION)) {

            String updatedName = nameTextField.getText();
            String[] splittedName = updatedName.trim().split("[ ]+");
            String updatedFirstName="";
            String updatedLastName="";
            if (splittedName.length>0) updatedFirstName=splittedName[0];
            if (splittedName.length>1) updatedLastName=splittedName[splittedName.length-1];
            if (listener!=null) listener.onSaveUserEditor(userId, phone, updatedFirstName, updatedLastName);

        } else {

            //TODO:

        }
        listener.onCloseUserEditor();
    }

    public void showImportUser() {
        JPanel inputPanel = new JPanel();
        JTextField firstNameTextField = new JTextField();
        JTextField lastNameTextField = new JTextField();
        JTextField phoneTextField = new JTextField();

        inputPanel.add(new JLabel("First name:"));
        inputPanel.add(firstNameTextField);
        inputPanel.add(new JLabel("Last name:"));
        inputPanel.add(lastNameTextField);
        inputPanel.add(new JLabel("Phone:"));
        inputPanel.add(phoneTextField);

        if (JOptionPane.OK_OPTION == JOptionPane.showConfirmDialog(
                rootPanel, inputPanel, "Import contact", JOptionPane.OK_CANCEL_OPTION)) {

            String firstName = firstNameTextField.getText();
            String lastName = lastNameTextField.getText();
            String phone = phoneTextField.getText();
            if (listener!=null) listener.onImportContactFilled(firstName, lastName, phone);

        } else {

            //TODO:

        }
        //listener.onCloseProfileEditor();
    }

    public void showNoRealisation() {
        JOptionPane.showMessageDialog(
                rootPanel,
                "Еще не реализовано!",
                "Ошибка",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}
