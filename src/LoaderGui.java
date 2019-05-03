import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoaderGui {
    public static void main(String[] args) {
        JFrame frame = new JFrame();


        /*frame.setLayout(new FlowLayout());
        JButton button = new JButton();
        button.setText("Click me");
        frame.add(button);
        */

        Form form = new Form();
        frame.setContentPane(form.getRootPanel());

        //form.getClickMeButton().addActionListener(new ActionListener() {
        //    @Override
        //    public void actionPerformed(ActionEvent e) {
        //        frame.getContentPane().setBackground(Color.RED);
        //    }
        //});

        frame.setTitle("GUI basics");
        frame.setSize(800,600);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}