import provider.Gui;
import view.EditProfileView;
import view.LoginView;
import view.MessengerView;

public class TestGfx {
    public static void main(String[] params) {
        MessengerView view = new MessengerView();
        view.show();
        view.showState(MessengerView.MessengerState.Messenger);

        EditProfileView editProfileView = new EditProfileView();
        //Gui.getInstance().changePane(editProfileView.getEditProfilePanel());
        Gui.getInstance().showPopup(editProfileView.getEditProfilePanel());
        Gui.getInstance().hidePopup();

        /*
        layeredPanel = new JPanel();
        //JLayeredPane jLayeredPane = rootPanel.getRootPane().getLayeredPane();
        EditProfileView editProfileView = new EditProfileView();
        //jLayeredPane.add(editProfileView.getEditProfilePanel(), JLayeredPane.POPUP_LAYER);
        layeredPanel.add(editProfileView.getEditProfilePanel(), JLayeredPane.POPUP_LAYER);

         */
    }
}
