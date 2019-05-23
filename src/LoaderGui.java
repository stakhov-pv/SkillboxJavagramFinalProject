import javax.swing.*;
import java.awt.*;

public class LoaderGui {
    static Form form;
    static LoginView login;

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setUndecorated(true);
        frame.setSize(900,630);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);

        /*frame.setLayout(new FlowLayout());
        JButton button = new JButton();
        button.setText("Click me");
        frame.add(button);
        */

        form = new Form();
        //frame.setContentPane(form.getRootPanel());

        /*
        login = new LoginView(new LoginView.LoginFormListener() {
            @Override
            public void onTestButtonPressed() {
                frame.setContentPane(form.getRootPanel());
                frame.validate();
            }
        });
        frame.setContentPane(login.getRootPanel());
         */

        ConversationsForm conversationsForm = new ConversationsForm();
        //frame.setContentPane(conversationsForm.getRootPanel());
        //frame.getLayeredPane().setLayer(conversationsForm.getRootPanel(),JLayeredPane.FRAME_CONTENT_LAYER);


        JPanel transparentBackground = new JPanel();
        transparentBackground.setLayout(new FlowLayout());
        JLabel lab = new JLabel();
        lab.setText("test text");
        transparentBackground.add(lab);
        transparentBackground.setBackground(new Color(0, 0, 0, 128));

        //transparentBackground.setOpaque(true);

        //layeredPane.add(transparentBackground,JLayeredPane.DEFAULT_LAYER);
        //frame.setContentPane(layeredPane);

        conversationsForm.getRootPanel().setBounds(0,0,frame.getWidth(), frame.getHeight());
        frame.getLayeredPane().setLayer(conversationsForm.getRootPanel(),JLayeredPane.POPUP_LAYER-1);
        frame.getLayeredPane().add(conversationsForm.getRootPanel());

        transparentBackground.setBounds(0,0, frame.getWidth(),frame.getHeight());
        frame.getLayeredPane().setLayer(transparentBackground,JLayeredPane.POPUP_LAYER);
        frame.getLayeredPane().add(transparentBackground);
        transparentBackground.setVisible(false);
        //frame.getLayeredPane().revalidate();
        //frame.setContentPane(transparentBackground);
        frame.validate();

        //form.getClickMeButton().addActionListener(new ActionListener() {
        //    @Override
        //    public void actionPerformed(ActionEvent e) {
        //        frame.getContentPane().setBackground(Color.RED);
        //    }
        //});

        //frame.setTitle("GUI basics");

    }
}