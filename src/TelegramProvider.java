import org.javagram.TelegramApiBridge;
import org.javagram.response.AuthAuthorization;
import org.javagram.response.AuthCheckedPhone;
import org.javagram.response.object.User;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

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

        String testAddr = "149.154.167.40:443";
        String workAddr = "149.154.167.50:443";
        String hostAddr = testAddr;

        //String testPhoneNumber = "+9996624444";
        //String phoneNumber = testPhoneNumber;

        //My data
        //Integer appId = 57190;
        //String appHash = "967ac20acae40702f3c31e5041048f59";

        //From tdlib
        Integer appId = 94575;
        String appHash = "a3406de8d171bb422bb6ddf3bbd800e2";

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

    public String getUserName() {
        return userName;
    }

    public BufferedImage getUserPic() {
        return userPic;
    }

    public BufferedImage getUserSmallPic() {
        return userSmallPic;
    }
}
