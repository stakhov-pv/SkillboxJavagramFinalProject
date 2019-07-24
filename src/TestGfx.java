import model.ConversationTopic;
import org.javagram.response.object.*;
import org.telegram.api.*;
import provider.Gui;
import provider.Res;
import provider.TelegramProvider;
import view.*;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Date;

public class TestGfx {
    public static void main(String[] params) {
        //testProfileEditor();
        //testAddContact();
        testEditContact();
        //testMessenger();
    }

    private static void testMessenger() {
        MessengerView view = new MessengerView();
        view.show();
        view.showState(MessengerView.MessengerState.Messenger);
        view.showSelectConversation(false);


        User user1 = new UserContact(new TLUserContact(1,"Paul","Stakhov",0,"+79221112233",null,null));
        User user2 = new UserContact(new TLUserContact(2,"Eugene","Stakhov",0,"+79334445566",null,null));

        //TelegramProvider.getInstance().setUser(user1);

        TLMessage msg1_2 = new TLMessage(1,2,new TLPeerUser(1),false,false,0,"some text",null);
        TLMessage msg2_1 = new TLMessage(2,1,new TLPeerUser(2),false,false,0,"some text",null);

        Message m1_2 = new Message(msg1_2);
        Message m2_1 = new Message(msg2_1);

        ArrayList<ConversationTopic> conversations = new ArrayList<>();
        conversations.add(new ConversationTopic(1, user1,m1_2,false));
        conversations.add(new ConversationTopic(2, user2,m2_1, true));
        //view.showConversationTopics(conversations);
        //view.repaintConversationTopics();

        ArrayList<Message> messages = new ArrayList<>();
        messages.add(m1_2);
        messages.add(m2_1);
        //view.showConversationMessages(messages);

    }

    private static void testEditContact() {
        MessengerView view = new MessengerView();
        view.show();
        view.showState(MessengerView.MessengerState.Messenger);

        BufferedImage image = Res.getImage("your-face.png");

        EditContactView editContactView = new EditContactView("Павел Стахов", "9221123344", image);
        editContactView.attachListener(new EditContactView.Listener() {
            @Override
            public void onBackPressed() {
                JOptionPane.showMessageDialog(
                        editContactView.getEditContactPanel(),
                        "Back pressed!",
                        "info",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }

            @Override
            public void onEditContactPressed(String name) {
                JOptionPane.showMessageDialog(
                        editContactView.getEditContactPanel(),
                        "Edit pressed with name="+name,
                        "info",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }

            @Override
            public void onDeleteContactPressed() {
                JOptionPane.showMessageDialog(
                        editContactView.getEditContactPanel(),
                        "Delete contact pressed!",
                        "info",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        });
        Gui.getInstance().showPopup(editContactView.getEditContactPanel());
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