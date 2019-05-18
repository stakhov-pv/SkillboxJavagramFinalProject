import javax.swing.*;
import javax.swing.border.Border;
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
    private JLabel user1when;
    private JLabel user1Name;
    private JLabel user1LastMessage;
    private JLabel searchLabel;
    private JTextField messageTextField;
    private JPanel messageButtonPanel;
    private JPanel myMessagePanel;
    private JPanel theyMessagePanel;
    private JPanel myMessageArrowPanel;
    private JPanel myMessageTopPanel;
    private JPanel myMessageBottomPanel;
    private JPanel myMessageTextPanel;


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
        accountNameLabel.setFont(regularFont.deriveFont(14f));
        partnerIconPanel = new JPanelImage("res/current-user.png");
        editPartnerPanel = new JPanelImage("res/icon-edit.png");

        user1AvatarPanel = new JPanelImage("res/user-avatar.png");

        Border conversationsRightBorder = BorderFactory.createMatteBorder(0,0,0,1,new Color(219,219,219));
        conversationsListPanel = new JPanel();
        conversationsListPanel.setBorder(conversationsRightBorder);

        Border searchPanelRightBottomBorder = BorderFactory.createMatteBorder(0,0,1,1,new Color(219,219,219));
        searchPanel = new JPanel();
        searchPanel.setBorder(searchPanelRightBottomBorder);

        searchLabel = new JLabel();
        searchLabel.setFont(regularFont.deriveFont(16.0f));

        Border chatPartnerBottomBorder = BorderFactory.createMatteBorder(0,0,1,0,new Color(237,237,227));
        chatPartnerPanel = new JPanel();
        chatPartnerPanel.setBorder(chatPartnerBottomBorder);


        Border conversationsBottomBorder = BorderFactory.createMatteBorder(0,0,1,0,new Color(219,219,219));
        user1 = new JPanel();
        user1.setBorder(conversationsBottomBorder);
        user2 = new JPanel();
        user2.setBorder(conversationsBottomBorder);

        user1Name = new JLabel();
        user1Name.setFont(boldFont.deriveFont(16.0f));

        user1LastMessage = new JLabel();
        user1LastMessage.setFont(regularFont.deriveFont(12.0f));
        user1when = new JLabel();
        user1when.setFont(regularFont.deriveFont(10.0f));

        partnerLabel = new JLabel();
        partnerLabel.setFont(regularFont.deriveFont(14.0f));

        myMessageTopPanel = new JPanelImage("res/message-out-top.png");
        myMessageBottomPanel = new JPanelImage("res/message-out-bottom.png");
        myMessageArrowPanel = new JPanelImage("res/message-out-right.png");

        messageButtonPanel = new JPanelImage("res/button-send.png");


    }
}
