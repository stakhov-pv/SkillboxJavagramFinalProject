import provider.Gui;
import view.AddContactView;
import view.EditProfileView;
import view.LoginView;
import view.MessengerView;

import javax.swing.*;

public class TestGfx {
    public static void main(String[] params) {
        //testProfileEditor();
        testAddContact();
    }

    private static void testAddContact() {
        MessengerView view = new MessengerView();
        view.show();
        view.showState(MessengerView.MessengerState.Messenger);

        AddContactView addContactView = new AddContactView();
        addContactView.attachListener(new AddContactView.Listener() {
            @Override
            public void onBackPressed() {
                JOptionPane.showMessageDialog(
                        addContactView.getAddContactPanel(),
                        "Back pressed!",
                        "info",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }

            @Override
            public void onAddContactPressed(String firstName, String lastName, String phone) {
                JOptionPane.showMessageDialog(
                        addContactView.getAddContactPanel(),
                        "Add pressed with phone="+phone+", first name="+firstName+" and last name="+lastName,
                        "info",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        });
        Gui.getInstance().showPopup(addContactView.getAddContactPanel());

    }


    private static void testProfileEditor() {
        MessengerView view = new MessengerView();
        view.show();
        view.showState(MessengerView.MessengerState.Messenger);

        EditProfileView editProfileView = new EditProfileView("Павел", "Стахов", "+7-912-345-66-77");
        editProfileView.attachListener(new EditProfileView.Listener() {
            @Override
            public void onBackPressed() {
                JOptionPane.showMessageDialog(
                        editProfileView.getEditProfilePanel(),
                        "Back pressed!",
                        "info",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }

            @Override
            public void onExitPressed() {
                JOptionPane.showMessageDialog(
                        editProfileView.getEditProfilePanel(),
                        "Exit pressed!",
                        "info",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }

            @Override
            public void onSaveChangesPressed(String firstName, String lastName) {
                JOptionPane.showMessageDialog(
                        editProfileView.getEditProfilePanel(),
                        "Save pressed with first name="+firstName+" and last name="+lastName,
                        "Back",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        });
        //Gui.getInstance().changePane(editProfileView.getEditProfilePanel());
        Gui.getInstance().showPopup(editProfileView.getEditProfilePanel());
        //Gui.getInstance().hidePopup();

        /*
        layeredPanel = new JPanel();
        //JLayeredPane jLayeredPane = rootPanel.getRootPane().getLayeredPane();
        EditProfileView editProfileView = new EditProfileView();
        //jLayeredPane.add(editProfileView.getEditProfilePanel(), JLayeredPane.POPUP_LAYER);
        layeredPanel.add(editProfileView.getEditProfilePanel(), JLayeredPane.POPUP_LAYER);

         */
    }
}
