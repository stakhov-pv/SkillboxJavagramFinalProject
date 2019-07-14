package view;

import model.ConversationTopic;
import org.javagram.response.object.Message;
import org.javagram.response.object.User;
import provider.Gui;
import provider.Res;
import provider.TelegramProvider;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
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
    private JLabel searchLabel;
    private JTextArea messageTextField;
    private JPanel messageButtonPanel;
    private JList conversationsList;
    private JButton closeButton;
    private JButton minimiseButton;
    private JList messagesList;
    private JPanel importContactPanel;
    private JPanel layeredPanel;
    private JPanel emptyChatPanel;
    private JPanel messageTextPanel;
    private JPanel messageFieldPanel;


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

        //user1AvatarPanel = new JImage(Res.getImage("user-avatar.png"));

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

        partnerLabel = new JLabel();
        partnerLabel.setFont(Res.getFont(Res.FONT_TYPE.REGULAR_FONT,14f));

        messageButtonPanel = new JImage(Res.getImage("button-send.png"));
        messageButtonPanel.addMouseListener(new PanelClickListener( () -> listener.onSendButtonPressed(messageTextField.getText()) ));

        messageTextPanel = new JPanel();
        messageTextField = new JTextArea();
        messageTextPanel.setBorder(new LineBorder(new Color(224,224,224), 10, true));
        messageTextField.setFont(Res.getFont(Res.FONT_TYPE.REGULAR_FONT,18f));

        //messageFieldPanel.setBorder(messageBorder);
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

        emptyChatPanel = new JPanel();
        emptyChatPanel.setVisible(false);

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
        boolean showSelectConversationMessage = (user==null);
        if (user!=null) {
            partnerLabel.setText(user.getFirstName() + " " + user.getLastName());
        } else {
            partnerLabel.setText("User deleted");
        }

        showSelectConversation(showSelectConversationMessage);

        BufferedImage image = getUserPic(user);
        JImage avatar = (JImage)partnerIconPanel;
        avatar.replaceImage(image, new Dimension(29,29));

    }

    private BufferedImage getUserPic(User user) {
        BufferedImage image = null;
        if (user!=null) {
            image = TelegramProvider.getInstance().getUserPic(user,true);
        }
        if (image==null) {
            image = Res.getImage("your-face.png");
        }
        return image;
    }

    public void showSelectConversation(boolean selectConversation) {
        emptyChatPanel.setVisible(selectConversation);
        chatPartnerPanel.setVisible(!selectConversation);
        chatMessagesPanel.setVisible(!selectConversation);
        messageEditorPanel.setVisible(!selectConversation);
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
                hideProfileEdit();
            }

            @Override
            public void onExitPressed() {
                hideProfileEdit();
                listener.onLogoff();
            }

            @Override
            public void onSaveChangesPressed(String firstName, String lastName) {
                listener.onSaveProfileEditor(firstName, lastName);
            }
        });
        Gui.getInstance().showPopup(editProfileView.getEditProfilePanel());
    }

    public void hideProfileEdit() {
        Gui.getInstance().hidePopup();
        listener.onCloseProfileEditor();
    }

    public void showEditUser(User user, String firstName, String lastName, String phone) {
        if (user==null) return;
        BufferedImage avatar = getUserPic(user);
        EditContactView editContactView = new EditContactView(firstName+" "+lastName,phone,avatar);
        editContactView.attachListener(new EditContactView.Listener() {
            @Override
            public void onBackPressed() {
                hideEditUser();
            }

            @Override
            public void onEditContactPressed(String name, String phone) {
                String[] splittedName = name.trim().split("[ ]+");
                String updatedFirstName="";
                String updatedLastName="";
                if (splittedName.length>0) updatedFirstName=splittedName[0];
                if (splittedName.length>1) updatedLastName=splittedName[splittedName.length-1];
                if (updatedFirstName.length()==0 && updatedLastName.length()==0) {
                    updatedFirstName="John";
                    updatedLastName="Doe";
                }
                if (listener!=null) listener.onSaveUserEditor(user.getId(), phone, updatedFirstName, updatedLastName);
            }

            @Override
            public void onDeleteContactPressed(String phone) {
                if (listener!=null) listener.onDeleteUserPressed(user.getId());
            }
        });
        Gui.getInstance().showPopup(editContactView.getEditContactPanel());
    }

    public void hideEditUser() {
        Gui.getInstance().hidePopup();
    }

    public void showImportUser() {
        AddContactView addContactView = new AddContactView();
        addContactView.attachListener(new AddContactView.Listener() {
            @Override
            public void onBackPressed() {
                hideImportUser();
            }

            @Override
            public void onAddContactPressed(String firstName, String lastName, String phone) {
                if (listener!=null) listener.onImportContactFilled(firstName, lastName, phone);
            }
        });
        Gui.getInstance().showPopup(addContactView.getAddContactPanel());
    }

    public void hideImportUser() {
        Gui.getInstance().hidePopup();
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
