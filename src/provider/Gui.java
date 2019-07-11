package provider;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;

public class Gui {
    private static final int WIDTH = 900;
    private static final int HEIGHT = 630;

    private JFrame frame;
    private static Gui instance;
    private Container popup;
    private Container contentPane;
    //private JLayeredPane layeredPane;

    public static Gui getInstance() {
        if (instance == null) {
            synchronized (Gui.class) {
                instance = new Gui();
            }
        }
        return instance;
    }

    private Gui() {
        initGui();
    }

    private void initGui() {
        frame = new JFrame();
        frame.setUndecorated(true);
        frame.setSize(WIDTH,HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.setVisible(true);
        //layeredPane = new JLayeredPane();
        //layeredPane.setLayout(null);
        //layeredPane.setPreferredSize(new Dimension(600,400));
        //layeredPane.setBounds(0, 0, 600, 400);
        //frame.setContentPane(layeredPane);
        //frame.pack();
    }

    public void changePane(Container container) {
        contentPane = container;
        //layeredPane.setLayer(container,JLayeredPane.DEFAULT_LAYER);
        frame.setContentPane(contentPane);
    }

    public void showPopup(Container newPopup) {
        if (popup!=null) hidePopup();
        //layeredPane.add(newPopup, JLayeredPane.POPUP_LAYER);
        //frame.getLayeredPane().add(newPopup, JLayeredPane.POPUP_LAYER);
        //frame.getLayeredPane().add(newPopup,1);//, JLayeredPane.POPUP_LAYER);
        //frame.getLayeredPane().setEnabled(true);
        //frame.getLayeredPane().add(newPopup, );
        //frame.setContentPane(newPopup);
        frame.setContentPane(newPopup);
        popup = newPopup;
    }

    public void hidePopup() {
        if (popup!=null) frame.setContentPane(contentPane);
        popup = null;
    }

    public void validate() {
        frame.validate();
    }

    public void doMinimize() {
        frame.setState(Frame.ICONIFIED);
    }

    public void doClose() {
        WindowEvent wev = new WindowEvent(frame, WindowEvent.WINDOW_CLOSING);
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(wev);

        // this will hide and dispose the frame, so that the application quits by
        // itself if there is nothing else around.
        frame.setVisible(false);
        frame.dispose();
        // if you have other similar frames around, you should dispose them, too.

        // finally, call this to really exit.
        // i/o libraries such as WiiRemoteJ need this.
        // also, this is what swing does for JFrame.EXIT_ON_CLOSE
        System.exit(0);
    }

}
