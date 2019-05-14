import javax.swing.*;

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
        closePanel = new JPanelImage("res/icon-close.png");
        minimisePanel = new JPanelImage("res/icon-hide.png");

        logoPanel = new JPanelImage("res/logo-micro.png");
        searchIconPanel = new JPanelImage("res/icon-search.png");

        accountIconPanel = new JPanelImage("res/your-face.png");
        accountSettingsIconPanel = new JPanelImage("res/icon-settings.png");
        partnerIconPanel = new JPanelImage("res/current-user.png");
        editPartnerPanel = new JPanelImage("res/icon-edit.png");

        user1AvatarPanel = new JPanelImage("res/user-avatar.png");
    }
}
