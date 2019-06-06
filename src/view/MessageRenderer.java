package view;

import org.javagram.response.object.Message;
import provider.Res;
import provider.TelegramProvider;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class MessageRenderer implements ListCellRenderer<Message> {
    private JPanel outPanel = new JPanel();
    private JPanel outSubPanel = new JPanel();
    private JPanel outMessagePanel = new JPanel();
    private JPanel outMessageTextPanel = new JPanel();
    private JPanel outMessageTopPanel = new JImage(Res.getImage("message-out-top.png"));
    private JPanel outMessageBottomPanel = new JImage(Res.getImage("message-out-bottom.png"));
    private JPanel outMessageArrowPanel = new JImage(Res.getImage("message-out-right.png"));

    //myMessageArrowPanel = new JImage(Res.getImage("message-out-right.png"));

    private JLabel outMessageLabel = new JLabel();
    private JLabel outMessageDate = new JLabel();

    private JPanel inMessagePanel = new JPanel();
    private JLabel inMessageLabel = new JLabel();
    private JLabel inMessageDate = new JLabel();

    public MessageRenderer() {
        outPanel.setLayout(new BorderLayout());
        outPanel.setBorder(BorderFactory.createEmptyBorder(0,100,0,0));
        outSubPanel.setLayout(new BorderLayout());
        outMessagePanel.setLayout(new BorderLayout());

        outPanel.add(outSubPanel,BorderLayout.CENTER);
        outPanel.add(new JPanel(),BorderLayout.WEST);
        outSubPanel.add(outMessagePanel,BorderLayout.CENTER);
        outSubPanel.add(outMessageDate,BorderLayout.SOUTH);
        outSubPanel.add(outMessageArrowPanel,BorderLayout.EAST);

        outMessagePanel.add(outMessageTextPanel,BorderLayout.CENTER);
        outMessageTextPanel.add(outMessageLabel, BorderLayout.CENTER);
        outMessageTextPanel.setBackground(new Color(74,68,168));
        Dimension minimum = new Dimension(307,8);
        Dimension maximum = new Dimension(307,300);
        outMessageTextPanel.setMinimumSize(minimum);
        outMessageTextPanel.setMinimumSize(maximum);
        outMessageLabel.setMinimumSize(minimum);
        outMessageLabel.setMaximumSize(maximum);

        outMessagePanel.add(outMessageTopPanel,BorderLayout.NORTH);
        outMessagePanel.add(outMessageBottomPanel,BorderLayout.SOUTH);
        //Dimension d = new Dimension(307,-1);
        //outMessagePanel.setPreferredSize(d);
        //outMessagePanel.setMinimumSize(d);
        //outMessagePanel.setMaximumSize(d);


        inMessagePanel.setLayout(new BorderLayout());
        inMessagePanel.add(inMessageLabel,BorderLayout.CENTER);
        inMessagePanel.add(inMessageDate,BorderLayout.SOUTH);

    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Message> jList, Message message,
                                                  int i, boolean isSelected, boolean cellHasFocus) {
        boolean messageFromMe = message.getFromId()==TelegramProvider.getInstance().getUserId();

        if (messageFromMe) {
            outMessageLabel.setText("<html>"+message.getMessage()+"</html>");
            return outPanel;
        } else {
            inMessageLabel.setText(message.getMessage());
            return inMessagePanel;
        }
    }
}
