package presenter;

import model.ConversationModel;
import model.MessengerModel;
import org.javagram.response.object.Dialog;
import org.javagram.response.object.Message;
import org.javagram.response.object.User;
import view.MessengerView;

import java.util.ArrayList;
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
        view.setConversations(model.getConversations());
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

    }

    void loadDialogs() {
        ArrayList<Dialog> dialogs = model.getDialogs();
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
        ArrayList<ConversationModel> conversations = new ArrayList<>();
        for(int i=0;i<dialogs.size();i++) {
            conversations.add(new ConversationModel(users.get(userIds.get(i)),messages.get(i)));
        }
        model.setConversations(conversations);
    }
}