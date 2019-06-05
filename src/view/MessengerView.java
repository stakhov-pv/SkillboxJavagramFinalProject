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
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class MessengerView {

    public enum MessengerState {
        Init,
        Messenger,
    }

    private Listener listener;

    public interface Listener {
        void onSendButtonPressed();
        void onProfileButtonPressed();
        void onMinimiseButtonPressed();
        void onCloseButtonPressed();
        void onSelectConversation(int index);
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
        conversationsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        conversationsList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                int index = conversationsList.getSelectedIndex();
                listener.onSelectConversation(index);
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
        conversationsList.setCellRenderer(new ConversationTopicRenderer());
    }

    public void showChatPartner(User user) {

    }

    public void showConversationMessages(ArrayList<Message> messages) {

    }

    public void minimiseApp() {
        Gui.getInstance().doMinimize();
    }

    public void closeApp() {
        Gui.getInstance().doClose();
    }
}
