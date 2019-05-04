import org.javagram.TelegramApiBridge;
import org.javagram.response.AuthAuthorization;
import org.javagram.response.AuthCheckedPhone;
import org.javagram.response.object.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Loader {
    public static void main(String[] args) throws IOException {
        try {
            String testAddr = "149.154.167.40:443";
            String workAddr = "149.154.167.50:443";
            String hostAddr = testAddr;

            String testPhoneNumber = "+9996624444";
            String realPhoneNumber = "+79024096439";
            String phoneNumber = testPhoneNumber;

            //My data
            //Integer appId = 57190;
            //String appHash = "967ac20acae40702f3c31e5041048f59";

            //From tdlib
            Integer appId = 94575;
            String appHash = "a3406de8d171bb422bb6ddf3bbd800e2";

            TelegramApiBridge bridge = new TelegramApiBridge(hostAddr, appId, appHash);

            AuthCheckedPhone authCheckedPhone = bridge.authCheckPhone(phoneNumber);
            System.out.println("Is invited? "+authCheckedPhone.isInvited());
            System.out.println("Is registered? "+authCheckedPhone.isRegistered());

            if (authCheckedPhone.isRegistered()) {
                System.out.println("Sending request for sms code...");
                bridge.authSendCode(phoneNumber);
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                System.out.print("Enter sms code:");
                String code = br.readLine();
                AuthAuthorization authorization = bridge.authSignIn(code);
                System.out.println("Authorization: "+authorization);
                User user = authorization.getUser();
                System.out.println("UserFirstName: "+user.getFirstName()
                        +", UserLastName: "+user.getLastName()
                        +", UserID: "+user.getId()
                        +", UserPhone: "+user.getPhone());
            }

            System.out.println("===>Wait 100sec and close app");
            try {
                Thread.sleep(10000);
            } catch (Exception e) {

            }

            bridge.authLogOut();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }
}
