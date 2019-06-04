package view;

import model.ConversationModel;
import org.javagram.response.object.Message;
import org.javagram.response.object.User;
import provider.Res;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class ConversationRenderer extends JPanel implements ListCellRenderer<ConversationModel> {
    private JLabel iconLabel = new JLabel();
    private JLabel nameLabel = new JLabel();
    private JLabel messageLabel = new JLabel();
    private JLabel dateLabel = new JLabel();

    ConversationRenderer() {
        setLayout(new BorderLayout());

        add(iconLabel, BorderLayout.WEST);
        add(dateLabel, BorderLayout.EAST);
        JPanel panel = new JPanel(new GridLayout(0,1));
        panel.add(nameLabel);
        panel.add(messageLabel);
        add(panel,BorderLayout.CENTER);
        panel.setOpaque(false);
        iconLabel.setOpaque(false);
        nameLabel.setOpaque(false);
        messageLabel.setOpaque(false);
        dateLabel.setOpaque(false);

    }

    @Override
    public Component getListCellRendererComponent(JList<? extends ConversationModel> jList, ConversationModel conversationModel,
                                                  int i, boolean isSelected, boolean cellHasFocus) {
        User user = conversationModel.getUser();
        Message messageData = conversationModel.getTopMessage();

        if (isSelected) {
            setBackground(new Color(255, 255, 255));
        } else {
            setBackground(new Color(230, 230, 230));
        }
        ImageIcon image = null;
        try {
            if (user!=null) {
                byte[] imageArray = conversationModel.getUser().getPhoto(false);
                if (imageArray!=null) image = new ImageIcon(imageArray);
            }

        } catch (IOException e) {
            e.printStackTrace();
            iconLabel.setIcon(new ImageIcon(Res.getImage("current-user.png")));
        }
        if (image==null) {
            image = new ImageIcon(Res.getImage("current-user.png"));
        }
        iconLabel.setIcon(image);
        nameLabel.setText(
                user==null? "User deleted"
                        :user.getFirstName()+" "+user.getFirstName()
        );
        messageLabel.setText(conversationModel.getTopMessage().getMessage());
        dateLabel.setText(String.valueOf(messageData.getDate()));
        return this;
    }
}
