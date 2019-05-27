import org.javagram.TelegramApiBridge;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {

        //TODO: do it on another thread or in other place (very slow startup)
        TelegramApiBridge bridge = initBridge();

        LoginView loginView = new LoginView();
        LoginModel loginModel = new LoginModel(bridge);
        LoginPresenter loginPresenter = new LoginPresenter(loginView, loginModel);
        loginPresenter.start();
    }

    static TelegramApiBridge initBridge() {

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
}
