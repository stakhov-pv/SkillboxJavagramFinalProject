package model;

import org.javagram.response.MessagesMessages;
import org.javagram.response.object.Message;
import org.javagram.response.object.MessagesDialog;
import org.javagram.response.object.User;
import org.javagram.response.object.UserContact;
import org.javagram.response.object.inputs.InputContact;
import provider.TelegramProvider;
import view.MessengerView;

import java.awt.im.InputContext;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class MessengerModel {
    private MessengerView.MessengerState state;
    private ArrayList<ConversationTopic> conversations;

    private ConversationTopic selectedConversation;
    private List<Message> selectedConversationMessages;

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

    public User getUser() {
        return TelegramProvider.getInstance().getUser();
    }

    public BufferedImage getUserPic() {
        return TelegramProvider.getInstance().getUserPic();
    }

    public List<MessagesDialog> getDialogs() {
        List<MessagesDialog> dialogs = TelegramProvider.getInstance().getDialogs();
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

    public List<Message> getSelectedConversationMessages() {
        return selectedConversationMessages;
    }

    public List<Message> loadConversationMessages(ConversationTopic conversation) {
        selectedConversationMessages = TelegramProvider.getInstance().getConversationsMessages(conversation.getUserId());
        return selectedConversationMessages;
    }

    public void addMessageToSelectedConversationMessages(Message message) {
        if (message!=null && selectedConversationMessages!=null) selectedConversationMessages.add(message);
    }

    public ConversationTopic getSelectedConversation() {
        return selectedConversation;
    }

    public void setSelectedConversation(ConversationTopic selectedConversation) {
        this.selectedConversation = selectedConversation;
        selectedConversationMessages = null;
    }

    public boolean sendMessage(int userId, String message) {
        return TelegramProvider.getInstance().sendMessage(userId, message);
    }

    public Integer contactsImportContact(InputContact inputContact, boolean replace) {
        return TelegramProvider.getInstance().contactsImportContact(inputContact, replace);
    }

    public boolean contactsDeleteContact(int userId) {
        return TelegramProvider.getInstance().contactsDeleteContact(userId);
    }

    public ArrayList<UserContact> contactsGetContacts() {
        return TelegramProvider.getInstance().contactsGetContacts();
    }

    public MessagesMessages messagesSearch(String query) {
        return TelegramProvider.getInstance().messagesSearch(query);
    }

}
