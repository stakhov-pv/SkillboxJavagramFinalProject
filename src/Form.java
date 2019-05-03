import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Form {
    private JButton clickMeButton;
    private JPanel rootPanel;
    private JTextField textField1;
    private JButton button1;

    public Form() {
        clickMeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int random = (int)Math.round(10*Math.random());
                textField1.setText(Integer.toString(random));

                int rez = JOptionPane.showConfirmDialog(
                        rootPanel,
                        "Some question"
                );

                if (rez==JOptionPane.OK_OPTION) {
                    JOptionPane.showMessageDialog(
                            rootPanel,
                            "Some text",
                            "Title hehe",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                }
            }
        });
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }

    public JButton getClickMeButton() {
        return clickMeButton;
    }
}
