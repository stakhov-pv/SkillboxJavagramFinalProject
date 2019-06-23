package view;

import org.javagram.response.object.Message;
import provider.TelegramProvider;

import javax.swing.*;
import java.awt.*;

public class MessageRenderer implements ListCellRenderer<Message> {

    private InMessageView inMessageView;
    private JPanel inMessagePanel;

    private OutMessageView outMessageView;
    private JPanel outMessagePanel;

    public MessageRenderer() {
        inMessageView = new InMessageView();
        inMessagePanel = inMessageView.getInMessagePanel();

        outMessageView = new OutMessageView();
        outMessagePanel = outMessageView.getOutMessagePanel();

    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Message> jList, Message message,
                                                  int i, boolean isSelected, boolean cellHasFocus) {
        boolean messageFromMe = message.getFromId()==TelegramProvider.getInstance().getUserId();
        String textMessage = "<html><p>"+message.getMessage().replace("\n","<br/>")+"</p></html>";

        if (messageFromMe) {
            outMessageView.getOutMessageLabel().setText(textMessage);
            //outMessageView.getOutMessagePanel().invalidate();
            return outMessagePanel;
        } else {
            inMessageView.getInMessageLabel().setText(textMessage);
            //inMessageView.getInMessagePanel().invalidate();
            //inMessagePanel.setPreferredSize(null);
            //inMessageView.getInMessageLabel().setPreferredSize(null);
            return inMessagePanel;
        }
    }
}
