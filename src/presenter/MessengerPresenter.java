package presenter;

import model.ConversationTopic;
import model.MessengerModel;
import org.javagram.response.MessagesMessages;
import org.javagram.response.object.*;
import org.javagram.response.object.inputs.InputContact;
import org.telegram.api.TLMessage;
import org.telegram.api.TLPeerUser;
import provider.TelegramProvider;
import util.PhoneFormatter;
import view.LoginView;
import view.MessengerView;

import javax.swing.*;
import java.util.*;
import java.util.stream.Collectors;

public class MessengerPresenter implements MessengerView.Listener, TelegramProvider.IncomingMessageListener {
    MessengerModel model;
    MessengerView view;

    public MessengerPresenter(MessengerModel model, MessengerView view) {
        this.model = model;
        this.view = view;

        model.setState(MessengerView.MessengerState.Init);

        view.attachListener(this);
    }

    void start() {
        view.setProfile(model.getUserName(),model.getUserSmallPic());

        view.show();
        setState(MessengerView.MessengerState.Messenger);

        showDialogs();
        TelegramProvider.getInstance().attachIncomingMessageListener(this);
    }

    void dispose() {
        view.detachListener();
        view = null;
        model = null;
        TelegramProvider.getInstance().detachIncomingMessageListener();
    }

    void setState(MessengerView.MessengerState newState) {
        model.setState(newState);
        view.showState(model.getState());
    }

    @Override
    public void onSendButtonPressed(String textMessage) {
        if (textMessage==null || textMessage.length()==0) return;
        ConversationTopic conversation = model.getSelectedConversation();
        int toId = conversation.getUserId();
        if (model.sendMessage(toId,textMessage)) {
            view.emptyMessageTextField();
            Message message = createMessage(model.getUserId(), toId,  textMessage);
            model.addMessageToSelectedConversationMessages(message);
            view.showConversationMessages(model.getSelectedConversationMessages());
            conversation.setTopMessage(message);
            view.repaintConversationTopics();
        } else {
            //some error with message sending
        }
    }

    @Override
    public void onIncomingMessage(int userId, String textMessage) {
        Message message = createMessage(userId, model.getUserId(), textMessage);
        ConversationTopic conversation = model.getSelectedConversation();
        if (conversation!=null && conversation.getUserId()==userId) {
            model.addMessageToSelectedConversationMessages(message);
            view.showConversationMessages(model.getSelectedConversationMessages());
        }

        for (ConversationTopic conversationTopic:model.getConversations()) {
            if (conversationTopic!=null && conversationTopic.getUserId()==userId) {
                conversationTopic.setTopMessage(message);
                view.repaintConversationTopics();
                return;
            }
        }

        //new dialog
        showDialogs();
    }

    private void showDialogs() {
        loadDialogs();
        view.showConversationTopics(model.getConversations());
    }

    private Message createMessage(int fromId, int toId, String textMessage) {
        int created = (int)(System.currentTimeMillis()/1000);
        TLMessage mess = new TLMessage();
        mess.setFromId(fromId);
        mess.setMessage(textMessage);
        mess.setUnread(true);
        TLPeerUser peer = new TLPeerUser();
        peer.setUserId(toId);
        mess.setToId(peer);
        mess.setDate(created);
        Message message = new Message(mess);
        return message;
    }

    @Override
    public void onProfileButtonPressed() {
        view.showProfileEdit(model.getUserFirstName(), model.getUserLastName(), PhoneFormatter.humanReadable(model.getUserPhone()));
    }

    @Override
    public void onEditUserButtonPressed() {
        ConversationTopic conversationTopic = model.getSelectedConversation();
        if (conversationTopic!=null) {
            view.showEditUser(conversationTopic.getUser(),
                    conversationTopic.getUser().getFirstName(), conversationTopic.getUser().getLastName(),
                    conversationTopic.getUser().getPhone());
        }
    }

    @Override
    public void onAddUserButtonPressed() {
        //TODO: make it
        view.showNoRealisation();
    }


    @Override
    public void onCloseProfileEditor() {
        //TODO: realy need this method?
    }

    @Override
    public void onSaveProfileEditor(String firstName, String lastName) {
        AsyncUpdateProfile asyncUpdateProfile = new AsyncUpdateProfile(firstName, lastName);
        asyncUpdateProfile.execute();
    }

    class AsyncUpdateProfile extends SwingWorker<Boolean, Void> {
        String firstName;
        String lastName;

        public AsyncUpdateProfile(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }

        @Override
        protected Boolean doInBackground() {
            model.updateUserProfile(firstName,lastName);
            return true;
        }

        @Override
        protected void done() {
            view.setProfile(model.getUserName(),model.getUserSmallPic());
            view.hideProfileEdit();
        }
    }

    @Override
    public void onCloseUserEditor() {
        //TODO: realy need this method?
    }

    @Override
    public void onSaveUserEditor(int userId, String phone, String firstName, String lastName) {
        AsyncUpdateUser asyncUpdateUser = new AsyncUpdateUser(userId, firstName, lastName, phone);
        asyncUpdateUser.execute();
    }

    private class AsyncUpdateUser extends SwingWorker<User, Void> {
        int userId;
        String phone;
        String firstName;
        String lastName;

        public AsyncUpdateUser(int userId, String firstName, String lastName, String phone) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.userId = userId;
            this.phone = phone;
        }

        @Override
        protected User doInBackground() {
            long client_id = 0;
            InputContact inputContact = new InputContact(client_id, phone, firstName, lastName);
            Integer newUserId = model.contactsImportContact(inputContact,true);
            if (newUserId==null) return null;
            User newUser = TelegramProvider.getInstance().getUser(userId);
            return newUser;
        }

        @Override
        protected void done() {
            view.hideEditUser();
            try {
                User user = get();
                model.getSelectedConversation().setUser(user);
                view.repaintConversationTopics();
                view.showChatPartner(user);
                view.setProfile(model.getUserName(),model.getUserSmallPic());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onDeleteUserPressed(int userId) {
        AsyncDeleteUser asyncDeleteUser = new AsyncDeleteUser(userId);
        asyncDeleteUser.execute();
    }

    private class AsyncDeleteUser extends SwingWorker<Boolean, Void> {
        int userId;

        public AsyncDeleteUser(int userId) {
            this.userId = userId;
        }

        @Override
        protected Boolean doInBackground() {
            if (model.contactsDeleteContact(userId)) {
                if (model.getSelectedConversation().getUserId()==userId) {
                    model.getConversations().remove(model.getSelectedConversation());
                }
                return true;
            }
            return false;
        }

        @Override
        protected void done() {
            view.hideEditUser();
            try {
                if (get()) {
                    //TODO: сменить текущего пользователя
                    view.showConversationTopics(model.getConversations());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onImportContactPressed() {
        view.showImportUser();
    }

    @Override
    public void onImportContactFilled(String firstName, String lastName, String phone) {
        AsyncImportContact asyncImportContact = new AsyncImportContact(firstName,lastName,phone);
        asyncImportContact.execute();
    }

    class AsyncImportContact extends SwingWorker<Boolean, Void> {
        String firstName;
        String lastName;
        String phone;

        public AsyncImportContact(String firstName, String lastName, String phone) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.phone = phone;
        }

        @Override
        protected Boolean doInBackground() {
            long client_id = 0;
            InputContact inputContact = new InputContact(client_id, phone, firstName, lastName);
            Integer newUserId = model.contactsImportContact(inputContact,false);
            if (newUserId==null) return false;
            User newUser = TelegramProvider.getInstance().getUser(newUserId);
            if (newUser==null) return false;
            //model.getSelectedConversation().setUser(newUser);
            model.getConversations().add(0,new ConversationTopic(newUserId,newUser,
                    createMessage(model.getUserId(), newUserId,  "Новый контакт")));
            return true;
        }

        @Override
        protected void done() {
            try {
                if (this.get()) {
                    view.showConversationTopics(model.getConversations());
                    onSelectConversation(0);
                } else {

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            view.hideImportUser();
        }
    }


    @Override
    public void onLogoff() {
        TelegramProvider.getInstance().logoff();

        dispose();

        LoginView.createAndRun();
    }

    @Override
    public void onSearchQueryEntered(String query) {
        if (query==null || query.trim().length()==0) {
            view.setSearchText("");
            showDialogs();
        } else {
            view.setSearchText(query);
            searchContactsAndDialogs(query);
            view.showConversationTopics(model.getConversations());
        }
    }

    @Override
    public void onMinimiseButtonPressed() {
        view.minimiseApp();
    }

    @Override
    public void onCloseButtonPressed() {
        view.closeApp();
    }

    @Override
    public void onSelectConversation(int index) {
        if (model.getConversations()!=null && index<model.getConversations().size()) {
            ConversationTopic conversationTopic = model.getConversations().get(index);
            model.setSelectedConversation(conversationTopic);
            view.showChatPartner(conversationTopic.getUser());
            view.showConversationMessages(model.loadConversationMessages(conversationTopic));
        }
    }

    public void loadDialogs() {
        List<MessagesDialog> dialogs = model.getDialogs();
        ArrayList<MessagesMessage> messages = dialogs.stream()
                .map(MessagesDialog::getTopMessage)
                .collect(Collectors.toCollection(ArrayList::new));
        ArrayList<Integer> userIds = messages.stream()
                .map(message -> message.getFromId()!=model.getUserId()?message.getFromId():message.getToPeerUserId())
                .collect(Collectors.toCollection(ArrayList::new));
        ArrayList<User> usersList = model.getUsers(userIds);
        Map<Integer,User> users = usersList.stream()
                .collect(Collectors.toMap(User::getId, c -> c));
        ArrayList<ConversationTopic> conversations = new ArrayList<>();
        for(int i=0;i<dialogs.size();i++) {
            conversations.add(new ConversationTopic(userIds.get(i), users.get(userIds.get(i)),messages.get(i)));
        }
        model.setConversations(conversations);
    }

    public void searchContactsAndDialogs(String criteria) {
        String upperCaseCriteria = criteria.toUpperCase();
        ArrayList<UserContact> contacts = model.contactsGetContacts();
        ArrayList<UserContact> filteredContacts = contacts.stream()
            .filter(
                userContact -> (userContact.getFirstName()+" "+userContact.getLastName()+" "+userContact.getPhone()).toUpperCase().contains(upperCaseCriteria)
            ).collect(Collectors.toCollection(ArrayList::new));
        ArrayList<ConversationTopic> conversations = new ArrayList<>();
        for(int i=0;i<filteredContacts.size();i++) {
            conversations.add(new ConversationTopic(filteredContacts.get(i).getId(), filteredContacts.get(i),null));
        }

        MessagesMessages messagesMessages = model.messagesSearch(criteria);
        ArrayList<MessagesMessage> messages = messagesMessages.getMessages();
        for (MessagesMessage message:messages) {
            //User userTo = message.getToPeerUser();
            //String text = message.getMessage();
            int userId;
            User user;
            if (message.getFromId()!=model.getUserId()) {
                userId = message.getFromId();
                user = message.getFrom();
            } else {
                userId = message.getToPeerUserId();
                user = message.getToPeerUser();
            }
            conversations.add(new ConversationTopic(userId,user,message));
        }


        model.setConversations(conversations);
    }
}
