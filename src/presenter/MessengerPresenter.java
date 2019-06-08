package presenter;

import model.ConversationTopic;
import model.MessengerModel;
import org.javagram.response.object.Dialog;
import org.javagram.response.object.Message;
import org.javagram.response.object.User;
import provider.TelegramProvider;
import view.LoginView;
import view.MessengerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MessengerPresenter implements MessengerView.Listener {
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

        loadDialogs();
        view.showConversationTopics(model.getConversations());
    }

    void dispose() {
        view.detachListener();
        view = null;
        model = null;
    }

    void setState(MessengerView.MessengerState newState) {
        model.setState(newState);
        view.showState(model.getState());
    }

    @Override
    public void onSendButtonPressed() {

    }

    @Override
    public void onProfileButtonPressed() {
        view.showProfileEdit(model.getUserFirstName(), model.getUserLastName(), model.getUserPhone());
    }

    @Override
    public void onEditUserButtonPressed() {

    }

    @Override
    public void onAddUserButtonPressed() {

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

    }

    @Override
    public void onSaveUserEditor(String firstName, String lastName) {

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
            view.showChatPartner(conversationTopic.getUser());
            view.showConversationMessages(model.getConversationMessages(conversationTopic));
        }
    }

    public void loadDialogs() {
        List<Dialog> dialogs = model.getDialogs();
        ArrayList<Integer> messageIds = dialogs.stream()
                .map(Dialog::getTopMessage)
                .collect(Collectors.toCollection(ArrayList::new));
        ArrayList<Message> messages = model.getMessages(messageIds);
        ArrayList<Integer> userIds = messages.stream()
                .map(message -> message.getFromId()!=model.getUserId()?message.getFromId():message.getToId())
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
