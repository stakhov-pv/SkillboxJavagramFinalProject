import org.javagram.response.object.Message;
import org.javagram.response.object.User;

public class ConversationModel {
    private User user;
    private Message topMessage;

    public ConversationModel(User user, Message topMessage) {
        this.user = user;
        this.topMessage = topMessage;
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
