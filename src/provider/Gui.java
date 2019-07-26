package provider;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;

public class Gui {
    public static final int WINDOW_WIDTH = 900;
    public static final int WINDOW_HEIGHT = 630;
    public static final int HEADER_HEIGHT = 34;
    public static final int POPUP_WIDTH = WINDOW_WIDTH;
    public static final int POPUP_HEIGHT = WINDOW_HEIGHT - HEADER_HEIGHT;


    private JFrame frame;
    private static Gui instance;
    private Container popup;
    private Container contentPane;
    private JLayeredPane layeredPane;

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
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.setVisible(true);
        layeredPane = new JLayeredPane();
        frame.add(layeredPane);
    }

    public void changePane(Container container) {
        contentPane = container;
        contentPane.setBounds(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
        if (layeredPane.getComponentsInLayer(JLayeredPane.DEFAULT_LAYER).length>0) {
            layeredPane.remove(JLayeredPane.DEFAULT_LAYER);
        }
        layeredPane.add(container,JLayeredPane.DEFAULT_LAYER);
    }

    public void showPopup(Container newPopup) {
        if (popup!=null) hidePopup();
        newPopup.setBounds(0, HEADER_HEIGHT, WINDOW_WIDTH, WINDOW_HEIGHT - HEADER_HEIGHT);
        layeredPane.add(newPopup, JLayeredPane.POPUP_LAYER);
        popup = newPopup;
    }

    public void hidePopup() {
        if (popup!=null) layeredPane.remove(popup);
        popup = null;
        layeredPane.repaint();
        //frame.validate();
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
