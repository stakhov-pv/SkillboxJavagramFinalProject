import org.javagram.TelegramApiBridge;
import org.javagram.response.AuthAuthorization;
import org.javagram.response.AuthCheckedPhone;
import org.javagram.response.object.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) {

        TelegramApiBridge bridge = initBridge();

        LoginView loginView = new LoginView();
        loginView.show();
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
