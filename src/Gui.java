import javax.swing.*;
import java.awt.*;

public class Gui {
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
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public void changePane(Container container) {
        frame.setContentPane(container);
    }

    public void validate() {
        frame.validate();
    }
}
