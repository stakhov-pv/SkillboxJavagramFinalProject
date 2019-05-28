import org.javagram.TelegramApiBridge;
import org.javagram.response.AuthAuthorization;
import org.javagram.response.AuthCheckedPhone;
import org.javagram.response.object.Dialog;
import org.javagram.response.object.Message;
import org.javagram.response.object.User;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class TelegramProvider {
    private static TelegramProvider instance;
    private TelegramApiBridge bridge;
    private User user;
    private BufferedImage userSmallPic;
    private BufferedImage userPic;
    private String userName;

    public static TelegramProvider getInstance() {
        if (instance==null) {
            synchronized (TelegramProvider.class) {
                instance = new TelegramProvider();
            }
        }
        return instance;
    }

    private TelegramProvider() {
        bridge = initBridge();
    }

    private TelegramApiBridge initBridge() {

        boolean workOnTestServer = true;

        String testAddr = "149.154.167.40:443";
        String workAddr = "149.154.167.50:443";
        String hostAddr = workOnTestServer?testAddr:workAddr;

        //String testPhoneNumber = "+9996624444";
        //String phoneNumber = testPhoneNumber;

        //My data
        Integer myAppId = 57190;
        String myAppHash = "967ac20acae40702f3c31e5041048f59";

        //From tdlib
        Integer testAppId = 94575;
        String testAppHash = "a3406de8d171bb422bb6ddf3bbd800e2";

        Integer appId = workOnTestServer?testAppId:myAppId;
        String appHash = workOnTestServer?testAppHash:myAppHash;


        TelegramApiBridge bridge = null;
        try {
            bridge = new TelegramApiBridge(hostAddr, appId, appHash);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bridge;
    }

    public boolean checkPhoneRegisteredStatus(String phoneNumber) throws IOException {
        AuthCheckedPhone authCheckedPhone = bridge.authCheckPhone(phoneNumber);
        return authCheckedPhone.isRegistered();
    }

    public void requestCode(String phoneNumber) throws IOException {
        bridge.authSendCode(phoneNumber);
    }

    public boolean signIn(String code) throws IOException {
        AuthAuthorization authorization = bridge.authSignIn(code);
        if (authorization==null) return false;
        user = authorization.getUser();
        userSmallPic = ImageIO.read(new ByteArrayInputStream(user.getPhoto(true)));
        userPic = ImageIO.read(new ByteArrayInputStream(user.getPhoto(false)));
        userName = (user.getFirstName()+" "+user.getLastName()).trim();
        return true;
    }

    public int getUserId() {
        return user.getId();
    }

    public String getUserName() {
        return userName;
    }

    public BufferedImage getUserPic() {
        return userPic;
    }

    public BufferedImage getUserSmallPic() {
        return userSmallPic;
    }

    public ArrayList<Dialog> getDialogs() {
        for (;;) {
            try {
                ArrayList<Dialog> dialogs = bridge.messagesGetDialogs(0, Integer.MAX_VALUE, Integer.MAX_VALUE);
                return dialogs;
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
            }
        }
    }

    public ArrayList<Message> getMessages(ArrayList<Integer> messageIds) {
        for (;;) {
            try {
                ArrayList<Message> messages = bridge.messagesGetMessages(messageIds);
                return messages;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<User> getUsers(ArrayList<Integer> userIds) {
        for (;;) {
            try {
                return bridge.usersGetUsers(userIds);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
