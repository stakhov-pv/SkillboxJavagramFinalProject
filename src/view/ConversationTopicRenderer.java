package view;

import model.ConversationTopic;
import org.javagram.response.object.Message;
import org.javagram.response.object.User;
import provider.Res;
import provider.TelegramProvider;
import util.DateFormatter;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ConversationTopicRenderer implements ListCellRenderer<ConversationTopic> {
    private ConversationTopicView conversationTopicView;
    private JPanel conversationTopicPanel;

    ConversationTopicRenderer() {
        conversationTopicView = new ConversationTopicView();
        conversationTopicPanel = conversationTopicView.getConversationPanel();
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends ConversationTopic> jList, ConversationTopic conversationTopic,
                                                  int i, boolean isSelected, boolean cellHasFocus) {
        User user = conversationTopic.getUser();
        Message messageData = conversationTopic.getTopMessage();
        String date = DateFormatter.shortRelativeToString(messageData.getDate());

        if (isSelected) {
            conversationTopicPanel.setBackground(new Color(255, 255, 255));
        } else {
            conversationTopicPanel.setBackground(new Color(230, 230, 230));
        }
        BufferedImage image = null;
        if (user!=null) {
            image = TelegramProvider.getInstance().getUserPic(user,true);
        }
        if (image==null) {
            image = Res.getImage("current-user.png");
        }

        JImage avatar = (JImage)conversationTopicView.getConversationAvatarPanel();
        avatar.replaceImage(image, new Dimension(41,41));
        conversationTopicView.getConversationUserName().setText(
                user==null? "User deleted"
                        :user.getFirstName()+" "+user.getLastName()
        );
        conversationTopicView.getConversationLastMessage().setText(
                (messageData.isOut()?"Вы: ":"")+
                conversationTopic.getTopMessage().getMessage());
        conversationTopicView.getConversationLastMessageDate().setText(date);
        return conversationTopicPanel;
    }
}
