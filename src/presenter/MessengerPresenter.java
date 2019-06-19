package presenter;

import model.ConversationTopic;
import model.MessengerModel;
import org.javagram.response.object.Message;
import org.javagram.response.object.MessagesDialog;
import org.javagram.response.object.MessagesMessage;
import org.javagram.response.object.User;
import org.javagram.response.object.inputs.InputContact;
import org.telegram.api.TLMessage;
import org.telegram.api.TLPeerUser;
import provider.TelegramProvider;
import view.LoginView;
import view.MessengerView;

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
        view.showProfileEdit(model.getUserFirstName(), model.getUserLastName(), model.getUserPhone());
    }

    @Override
    public void onEditUserButtonPressed() {
        ConversationTopic conversationTopic = model.getSelectedConversation();
        if (conversationTopic!=null) {
            view.showEditUser(conversationTopic.getUserId(),
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
        model.updateUserProfile(firstName,lastName);
        view.setProfile(model.getUserName(),model.getUserSmallPic());
    }

    @Override
    public void onCloseUserEditor() {
        //TODO: realy need this method?
    }

    @Override
    public void onSaveUserEditor(int userId, String phone, String firstName, String lastName) {
        long client_id = 0;
        InputContact inputContact = new InputContact(client_id, phone, firstName, lastName);
        Integer newUserId = model.contactsImportContact(inputContact,true);
        if (newUserId==null) return;
        User newUser = TelegramProvider.getInstance().getUser(userId);
        if (newUser==null) return;
        model.getSelectedConversation().setUser(newUser);
        view.repaintConversationTopics();
        view.showChatPartner(newUser);
    }

    @Override
    public void onDeleteUserPressed(int userId) {
        if (model.contactsDeleteContact(userId)) {
            if (model.getSelectedConversation().getUserId()==userId) {
                model.getConversations().remove(model.getSelectedConversation());
            }
            view.showConversationTopics(model.getConversations());
            //TODO: сменить текущего пользователя
        }

    }

    @Override
    public void onImportContactPressed() {
        view.showImportUser();
    }

    @Override
    public void onImportContactFilled(String firstName, String lastName, String phone) {
        long client_id = 0;
        InputContact inputContact = new InputContact(client_id, phone, firstName, lastName);
        Integer newUserId = model.contactsImportContact(inputContact,false);
        if (newUserId==null) return;
        User newUser = TelegramProvider.getInstance().getUser(newUserId);
        if (newUser==null) return;
        //model.getSelectedConversation().setUser(newUser);
        model.getConversations().add(0,new ConversationTopic(newUserId,newUser,
                createMessage(model.getUserId(), newUserId,  "Новый контакт")));
        view.showConversationTopics(model.getConversations());
        onSelectConversation(0);
    }

    @Override
    public void onLogoff() {
        TelegramProvider.getInstance().logoff();

        dispose();

        LoginView.createAndRun();
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
}
