package view;

import provider.Res;

import javax.swing.*;

public class InMessageView {
    private JPanel inMessagePanel;
    private JPanel inMessageTopPanel;
    private JPanel inMessageTextPanel;
    private JPanel inMessageBottomPanel;
    private JPanel inMessageLeftPanel;
    private JLabel inMessageLabel;
    private JLabel inMessageDateLabel;

    private void createUIComponents() {
        inMessageTopPanel = new JImage(Res.getImage("message-in-top.png"));
        inMessageBottomPanel = new JImage(Res.getImage("message-in-bottom.png"));
        inMessageLeftPanel = new JImage(Res.getImage("message-in-left.png"));
    }

    public JPanel getInMessagePanel() {
        return inMessagePanel;
    }

    public JLabel getInMessageLabel() {
        return inMessageLabel;
    }

    public JLabel getInMessageDateLabel() {
        return inMessageDateLabel;
    }
}
