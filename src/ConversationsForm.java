import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ConversationsForm {
    private JPanel rootPanel;
    private JPanel headerPanel;
    private JPanel logoAccountPanel;
    private JPanel mainPanel;
    private JPanel conversationsPanel;
    private JPanel chatPanel;
    private JPanel searchPanel;
    private JPanel conversationsListPanel;
    private JPanel chatPartnerPanel;
    private JPanel chatMessagesPanel;
    private JPanel messageEditorPanel;
    private JPanel logoPanel;
    private JPanel accountPanel;
    private JPanel closePanel;
    private JPanel minimisePanel;
    private JPanel searchIconPanel;
    private JPanel accountSettingsIconPanel;
    private JLabel accountNameLabel;
    private JPanel accountIconPanel;
    private JPanel partnerIconPanel;
    private JLabel partnerLabel;
    private JPanel editPartnerPanel;
    private JPanel user1;
    private JPanel user1AvatarPanel;
    private JPanel user2;


    public JPanel getRootPanel() {
        return rootPanel;
    }

    private void createUIComponents() {
        Font lightFont = null;
        Font regularFont = null;
        Font boldFont = null;
        try {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

            lightFont = Font.createFont(Font.TRUETYPE_FONT, new File("res/OpenSansLight.ttf"));
            regularFont = Font.createFont(Font.TRUETYPE_FONT, new File("res/OpenSansRegular.ttf"));
            boldFont = Font.createFont(Font.TRUETYPE_FONT, new File("res/OpenSansSemiBold.ttf"));

            ge.registerFont(lightFont);
            ge.registerFont(regularFont);
            ge.registerFont(boldFont);
        } catch (IOException e) {
            e.printStackTrace();
        } catch(FontFormatException e) {
            e.printStackTrace();
        }

        closePanel = new JPanelImage("res/icon-close.png");
        minimisePanel = new JPanelImage("res/icon-hide.png");

        logoPanel = new JPanelImage("res/logo-micro.png");
        searchIconPanel = new JPanelImage("res/icon-search.png");

        accountIconPanel = new JPanelImage("res/your-face.png");
        accountSettingsIconPanel = new JPanelImage("res/icon-settings.png");
        accountNameLabel = new JLabel();
        accountNameLabel.setFont(regularFont.deriveFont(17f));
        partnerIconPanel = new JPanelImage("res/current-user.png");
        editPartnerPanel = new JPanelImage("res/icon-edit.png");

        user1AvatarPanel = new JPanelImage("res/user-avatar.png");
    }
}
