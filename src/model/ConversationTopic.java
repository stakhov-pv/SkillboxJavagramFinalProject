package model;

import org.javagram.response.object.Message;
import org.javagram.response.object.User;

public class ConversationTopic {
    private int userId;
    private User user;
    private Message topMessage;

    public ConversationTopic(int userId, User user, Message topMessage) {
        this.userId = userId;
        this.user = user;
        this.topMessage = topMessage;
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
