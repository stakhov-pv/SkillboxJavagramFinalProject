package view;

import provider.Res;

import javax.swing.*;

public class OutMessageView {
    private JPanel outMessageTopPanel;
    private JPanel outMessageTextPanel;
    private JPanel outMessageBottomPanel;
    private JPanel outMessageArrowPanel;
    private JPanel outMessagePanel;
    private JLabel outMessageLabel;
    private JLabel outMessageDateLabel;


    private void createUIComponents() {
        outMessageTopPanel = new JImage(Res.getImage("message-out-top.png"));
        outMessageBottomPanel = new JImage(Res.getImage("message-out-bottom.png"));
        outMessageArrowPanel = new JImage(Res.getImage("message-out-right.png"));
    }

    public JPanel getOutMessagePanel() {
        return outMessagePanel;
    }

    public JLabel getOutMessageLabel() {
        return outMessageLabel;
    }

    public JLabel getOutMessageDateLabel() {
        return outMessageDateLabel;
    }
}
