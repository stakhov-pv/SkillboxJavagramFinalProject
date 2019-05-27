import java.awt.image.BufferedImage;

public class MessengerModel {
    private MessengerView.MessengerState state;

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

    public BufferedImage getUserSmallPic() {
        return TelegramProvider.getInstance().getUserSmallPic();
    }

    public BufferedImage getUserPic() {
        return TelegramProvider.getInstance().getUserPic();
    }

}
