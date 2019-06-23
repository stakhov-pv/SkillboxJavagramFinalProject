package view;

import org.javagram.response.object.Message;
import provider.TelegramProvider;
import util.DateFormatter;

import javax.swing.*;
import java.awt.*;

public class MessageRenderer implements ListCellRenderer<Message> {

    @Override
    public Component getListCellRendererComponent(JList<? extends Message> jList, Message message,
                                                  int i, boolean isSelected, boolean cellHasFocus) {
        boolean messageFromMe = message.getFromId()==TelegramProvider.getInstance().getUserId();
        String textMessage = "<html><p style=\"width:210px\">"+message.getMessage().replace("\n","<br/>")+"</p></html>";
        String date = DateFormatter.relativeToString(message.getDate());
        if (messageFromMe) {
            OutMessageView outMessageView = new OutMessageView();
            outMessageView.getOutMessageLabel().setText(textMessage);
            outMessageView.getOutMessageLabel().setPreferredSize(null);
            outMessageView.getOutMessageDateLabel().setText(date);
            return outMessageView.getOutMessagePanel();
        } else {
            InMessageView  inMessageView = new InMessageView();
            inMessageView.getInMessageLabel().setText(textMessage);
            inMessageView.getInMessageDateLabel().setText(date);
            return inMessageView.getInMessagePanel();
        }
    }
}
