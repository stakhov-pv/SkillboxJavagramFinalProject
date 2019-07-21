package model;

import org.javagram.response.object.Message;
import org.javagram.response.object.User;

public class ConversationTopic {
    private int userId;
    private User user;
    private boolean online;
    private Message topMessage;

    public ConversationTopic(int userId, User user, Message topMessage, boolean online) {
        this.userId = userId;
        this.user = user;
        this.topMessage = topMessage;
        this.online = online;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public Message getTopMessage() {
        return topMessage;
    }

    public void setTopMessage(Message topMessage) {
        this.topMessage = topMessage;
    }

    @Override
    public String toString() {
        return (user==null?"Null":(user.getFirstName()+" "+user.getLastName()))
                +"> "
                +topMessage.getMessage();
    }
}
