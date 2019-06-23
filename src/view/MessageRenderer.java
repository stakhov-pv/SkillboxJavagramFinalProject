package view;

import org.javagram.response.object.Message;
import provider.TelegramProvider;

import javax.swing.*;
import java.awt.*;

public class MessageRenderer implements ListCellRenderer<Message> {

    @Override
    public Component getListCellRendererComponent(JList<? extends Message> jList, Message message,
                                                  int i, boolean isSelected, boolean cellHasFocus) {
        boolean messageFromMe = message.getFromId()==TelegramProvider.getInstance().getUserId();
        String textMessage = "<html><p>"+message.getMessage().replace("\n","<br/>")+"</p></html>";

        if (messageFromMe) {
            OutMessageView outMessageView = new OutMessageView();
            outMessageView.getOutMessageLabel().setText(textMessage);
            return outMessageView.getOutMessagePanel();
        } else {
            InMessageView  inMessageView = new InMessageView();
            inMessageView.getInMessageLabel().setText(textMessage);
            return inMessageView.getInMessagePanel();
        }
    }
}
