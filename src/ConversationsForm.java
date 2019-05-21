import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

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
    private JPanel inMessageTopPanel;
    private JPanel inMessageBottomPanel;
    private JPanel inMessageTextPanel;
    private JPanel inMessageLeftPanel;


    public JPanel getRootPanel() {
        return rootPanel;
    }

    private void createUIComponents() {

        closePanel = new JPanelImage(Res.getImage("icon-close.png"));
        minimisePanel = new JPanelImage(Res.getImage("icon-hide.png"));

        logoPanel = new JPanelImage(Res.getImage("logo-micro.png"));
        searchIconPanel = new JPanelImage(Res.getImage("icon-search.png"));

        accountIconPanel = new JPanelImage(Res.getImage("your-face.png"));
        accountSettingsIconPanel = new JPanelImage(Res.getImage("icon-settings.png"));
        accountNameLabel = new JLabel();
        accountNameLabel.setFont(Res.getFont(Res.FONT_TYPE.REGULAR_FONT,14f));
        partnerIconPanel = new JPanelImage(Res.getImage("current-user.png"));
        editPartnerPanel = new JPanelImage(Res.getImage("icon-edit.png"));

        user1AvatarPanel = new JPanelImage(Res.getImage("user-avatar.png"));

        Border conversationsRightBorder = BorderFactory.createMatteBorder(0,0,0,1,new Color(219,219,219));
        conversationsListPanel = new JPanel();
        conversationsListPanel.setBorder(conversationsRightBorder);

        Border searchPanelRightBottomBorder = BorderFactory.createMatteBorder(0,0,1,1,new Color(219,219,219));
        searchPanel = new JPanel();
        searchPanel.setBorder(searchPanelRightBottomBorder);

        searchLabel = new JLabel();
        searchLabel.setFont(Res.getFont(Res.FONT_TYPE.REGULAR_FONT,16f));

        Border chatPartnerBottomBorder = BorderFactory.createMatteBorder(0,0,1,0,new Color(237,237,227));
        chatPartnerPanel = new JPanel();
        chatPartnerPanel.setBorder(chatPartnerBottomBorder);


        Border conversationsBottomBorder = BorderFactory.createMatteBorder(0,0,1,0,new Color(219,219,219));
        user1 = new JPanel();
        user1.setBorder(conversationsBottomBorder);
        user2 = new JPanel();
        user2.setBorder(conversationsBottomBorder);

        user1Name = new JLabel();
        user1Name.setFont(Res.getFont(Res.FONT_TYPE.SEMIBOLD_FONT,16f));

        user1LastMessage = new JLabel();
        user1LastMessage.setFont(Res.getFont(Res.FONT_TYPE.REGULAR_FONT,12f));
        user1when = new JLabel();
        user1when.setFont(Res.getFont(Res.FONT_TYPE.REGULAR_FONT,10f));

        partnerLabel = new JLabel();
        partnerLabel.setFont(Res.getFont(Res.FONT_TYPE.REGULAR_FONT,14f));

        myMessageTopPanel = new JPanelImage(Res.getImage("message-out-top.png"));

        myMessageBottomPanel = new JPanelImage(Res.getImage("message-out-bottom.png"));
        myMessageArrowPanel = new JPanelImage(Res.getImage("message-out-right.png"));

        messageButtonPanel = new JPanelImage(Res.getImage("button-send.png"));

        inMessageTopPanel = new JPanelImage(Res.getImage("message-in-top.png"));
        inMessageBottomPanel = new JPanelImage(Res.getImage("message-in-bottom.png"));
        inMessageLeftPanel = new JPanelImage(Res.getImage("message-in-left.png"));


    }
}
