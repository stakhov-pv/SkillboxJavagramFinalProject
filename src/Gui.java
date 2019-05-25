import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class Gui implements WindowListener {
    private static final int WIDTH = 900;
    private static final int HEIGHT = 630;

    private JFrame frame;
    private static Gui instance;

    public static synchronized Gui getInstance() {
        if (instance == null) {
            instance = new Gui();
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
        frame.addWindowListener(this);
        frame.setVisible(true);
    }

    public void changePane(Container container) {
        frame.setContentPane(container);
    }

    public void validate() {
        frame.validate();
    }

    public void doMinimize() {
        frame.setVisible(false);
        frame.setUndecorated(false);
        frame.setState(Frame.ICONIFIED);
        frame.setUndecorated(true);
        frame.setVisible(true);

        //frame.setExtendedState(JFrame.ICONIFIED);
        //frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_ICONIFIED));

        //WindowEvent wev = new WindowEvent(frame, WindowEvent.WINDOW_ICONIFIED);
        //Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(wev);
        //frame.setVisible(false);
        //frame.revalidate();
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

    @Override
    public void windowOpened(WindowEvent windowEvent) {

    }

    @Override
    public void windowClosing(WindowEvent windowEvent) {

    }

    @Override
    public void windowClosed(WindowEvent windowEvent) {

    }

    @Override
    public void windowIconified(WindowEvent windowEvent) {
        //frame.setVisible(false);
    }

    @Override
    public void windowDeiconified(WindowEvent windowEvent) {
        //frame.setVisible(true);
    }

    @Override
    public void windowActivated(WindowEvent windowEvent) {

    }

    @Override
    public void windowDeactivated(WindowEvent windowEvent) {

    }
}
