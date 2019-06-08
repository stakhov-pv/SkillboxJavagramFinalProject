package view;

import model.ConversationTopic;
import org.javagram.response.object.Message;
import org.javagram.response.object.User;
import provider.Gui;
import provider.Res;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class MessengerView {

    public enum MessengerState {
        Init,
        Messenger,
    }

    private Listener listener;

    public interface Listener {
        void onSendButtonPressed();
        void onProfileButtonPressed();
        void onEditUserButtonPressed();
        void onAddUserButtonPressed();
        void onMinimiseButtonPressed();
        void onCloseButtonPressed();
        void onSelectConversation(int index);
        void onCloseProfileEditor();
        void onSaveProfileEditor(String firstName, String lastName);
        void onCloseUserEditor();
        void onSaveUserEditor(String firstName, String lastName);
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
    private JTextField messageTextField;
    private JPanel messageButtonPanel;
    private JPanel myMessagePanel;
    private JPanel theyMessagePanel;
    private JPanel myMessageArrowPanel;
    private JPanel myMessageTopPanel;
    private JPanel myMessageBottomPanel;
    private JPanel myMessageTextPanel;
    private JPanel inMessageTopPanel;
    private JPanel inMessageBottomPanel;
    private JPanel inMessageTextPanel;
    private JPanel inMessageLeftPanel;
    private JList conversationsList;
    private JButton closeButton;
    private JButton minimiseButton;
    private JList messagesList;


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

        logoPanel = new JImage(Res.getImage("logo-micro.png"));
        searchIconPanel = new JImage(Res.getImage("icon-search.png"));

        accountPanel = new JPanel();
        accountPanel.addMouseListener(new PanelClickListener( ()->listener.onProfileButtonPressed() ));

        accountIconPanel = new JImage(Res.getImage("your-face.png"));
        accountSettingsIconPanel = new JImage(Res.getImage("icon-settings.png"));
        accountNameLabel = new JLabel();
        accountNameLabel.setFont(Res.getFont(Res.FONT_TYPE.REGULAR_FONT,14f));
        partnerIconPanel = new JImage(Res.getImage("current-user.png"));
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

        myMessageTopPanel = new JImage(Res.getImage("message-out-top.png"));

        myMessageBottomPanel = new JImage(Res.getImage("message-out-bottom.png"));
        myMessageArrowPanel = new JImage(Res.getImage("message-out-right.png"));

        messageButtonPanel = new JImage(Res.getImage("button-send.png"));

        inMessageTopPanel = new JImage(Res.getImage("message-in-top.png"));
        inMessageBottomPanel = new JImage(Res.getImage("message-in-bottom.png"));
        inMessageLeftPanel = new JImage(Res.getImage("message-in-left.png"));

        conversationsList = new JList<ConversationTopic>();
        conversationsList.setCellRenderer(new ConversationTopicRenderer());
        conversationsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        conversationsList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                int index = conversationsList.getSelectedIndex();
                listener.onSelectConversation(index);
            }
        });

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
        //((view.JImage)accountIconPanel).replaceImage(smallPic);
    }

    public void showConversationTopics(ArrayList<ConversationTopic> conversations) {
        DefaultListModel<ConversationTopic> listModel = new DefaultListModel<>();
        listModel.addAll(conversations);
        conversationsList.setModel(listModel);
    }

    public void showChatPartner(User user) {

    }

    public void showConversationMessages(List<Message> messages) {
        DefaultListModel<Message> messageModel = new DefaultListModel<>();
        messageModel.addAll(messages);
        messagesList.setModel(messageModel);
    }

    public void minimiseApp() {
        Gui.getInstance().doMinimize();
    }

    public void closeApp() {
        Gui.getInstance().doClose();
    }

    class PanelClickListener implements MouseListener {
        PanelListener listener;

        public PanelClickListener(PanelListener listener) {
            this.listener = listener;
        }

        @Override
        public void mouseClicked(MouseEvent mouseEvent) {
            if (listener!=null) listener.onPanelClicked();
        }

        @Override
        public void mousePressed(MouseEvent mouseEvent) { }

        @Override
        public void mouseReleased(MouseEvent mouseEvent) { }

        @Override
        public void mouseEntered(MouseEvent mouseEvent) { }

        @Override
        public void mouseExited(MouseEvent mouseEvent) { }


    }

    public interface PanelListener {
        void onPanelClicked();
    }

    public void showProfileEdit(String firstName, String lastName, String phone) {
        JPanel inputPanel = new JPanel();
        //inputPanel.setLayout(new );
        JTextField firstNameTextField = new JTextField(firstName);
        JTextField lastNameTextField = new JTextField(lastName);
        JButton logoffButton = new JButton("Logoff from "+phone);
        logoffButton.setAction(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //TODO: close dialog?
                if (listener!=null) listener.onLogoff();
            }
        });
        inputPanel.add(new JLabel("First name:"));
        inputPanel.add(firstNameTextField);
        inputPanel.add(new JLabel("Last name:"));
        inputPanel.add(lastNameTextField);
        inputPanel.add(logoffButton);

        if (JOptionPane.OK_OPTION == JOptionPane.showConfirmDialog(
                rootPanel, inputPanel, "Enter your data", JOptionPane.OK_CANCEL_OPTION)) {

            String updatedFirstName = firstNameTextField.getText();
            String updatedLastName = lastNameTextField.getText();
            if (listener!=null) listener.onSaveProfileEditor(updatedFirstName, updatedLastName);

        } else {

            //TODO:

        }
        listener.onCloseProfileEditor();
    }

    public void showEditUser(String firstName, String lastName, String userId, String phone) {

    }


}
