package model;

import org.javagram.response.object.Dialog;
import org.javagram.response.object.Message;
import org.javagram.response.object.User;
import provider.TelegramProvider;
import view.MessengerView;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class MessengerModel {
    private MessengerView.MessengerState state;
    private ArrayList<ConversationTopic> conversations;

    public MessengerModel() {
    }

    public MessengerView.MessengerState getState() {
        return state;
    }

    public void setState(MessengerView.MessengerState state) {
        this.state = state;
    }

    public String getUserName() {
        return TelegramProvider.getInstance().getUserName();
    }

    public String getUserFirstName() {
        return TelegramProvider.getInstance().getUserFirstName();
    }

    public String getUserLastName() {
        return TelegramProvider.getInstance().getUserLastName();
    }

    public String getUserPhone() {
        return TelegramProvider.getInstance().getUserPhone();
    }

    public void updateUserProfile(String firstName, String lastName) {
        TelegramProvider.getInstance().updateProfile(firstName,lastName);
    }

    public BufferedImage getUserSmallPic() {
        return TelegramProvider.getInstance().getUserSmallPic();
    }

    public int getUserId() {
        return TelegramProvider.getInstance().getUserId();
    }

    public BufferedImage getUserPic() {
        return TelegramProvider.getInstance().getUserPic();
    }

    public List<Dialog> getDialogs() {
        List<Dialog> dialogs = TelegramProvider.getInstance().getDialogs();
        return dialogs;
    }

    public ArrayList<Message> getMessages(ArrayList<Integer> messageIds) {
        ArrayList<Message> messages = TelegramProvider.getInstance().getMessages(messageIds);
        return messages;
    }

    public ArrayList<User> getUsers(ArrayList<Integer> userIds) {
        ArrayList<User> users = TelegramProvider.getInstance().getUsers(userIds);
        return users;
    }

    public ArrayList<ConversationTopic> getConversations() {
        return conversations;
    }

    public void setConversations(ArrayList<ConversationTopic> conversations) {
        this.conversations = conversations;
    }

    public List<Message> getConversationMessages(ConversationTopic conversation) {
        List<Message> messages = TelegramProvider.getInstance().getConversationsMessages(conversation.getUserId());
        return messages;
    }

}
