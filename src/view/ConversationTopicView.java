package view;

import provider.Res;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ConversationTopicView {
    private JPanel conversationPanel;
    private JPanel conversationAvatarPanel;
    private JLabel conversationUserName;
    private JLabel conversationLastMessage;
    private JLabel conversationLastMessageDate;

    public JPanel getConversationPanel() {
        return conversationPanel;
    }

    public JPanel getConversationAvatarPanel() {
        return conversationAvatarPanel;
    }

    public JLabel getConversationUserName() {
        return conversationUserName;
    }

    public JLabel getConversationLastMessage() {
        return conversationLastMessage;
    }

    public JLabel getConversationLastMessageDate() {
        return conversationLastMessageDate;
    }

    private void createUIComponents() {
        replaceAvatarPanel(Res.getImage("user-avatar.png"));


        Border conversationsBottomBorder = BorderFactory.createMatteBorder(0,0,1,0,new Color(219,219,219));
        conversationPanel = new JPanel();
        conversationPanel.setBorder(conversationsBottomBorder);

        conversationUserName = new JLabel();
        conversationUserName.setFont(Res.getFont(Res.FONT_TYPE.SEMIBOLD_FONT,16f));

        conversationLastMessage = new JLabel();
        conversationLastMessage.setFont(Res.getFont(Res.FONT_TYPE.REGULAR_FONT,12f));
        conversationLastMessageDate = new JLabel();
        conversationLastMessageDate.setFont(Res.getFont(Res.FONT_TYPE.REGULAR_FONT,10f));
    }

    public void replaceAvatarPanel(BufferedImage image) {
        conversationAvatarPanel = new JImage(image, new Dimension(41,41));
    }
}
